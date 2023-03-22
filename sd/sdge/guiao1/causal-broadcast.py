#!/usr/bin/env python3.10

import logging
from ms import receiveAll, reply, send
from types import SimpleNamespace

#run with
#./maelstrom test -w cbcast --bin causal-broadcast.py  --time-limit 5 --node-count 3 --concurrency 1n
# or
#./maelstrom test -w cbcast --bin causal-broadcast.py  --time-limit 5 --node-count 3 --latency-dist uniform --latency 500
logging.getLogger().setLevel(logging.DEBUG)

node_id = None
node_ids = None
vv = {}
delivered = []
buffer = []

def broadcast(**kwds):
    for i in node_ids:
        if i != node_id:
            send(node_id, i, **kwds)

def verify_delevery(src, vv_rec):
    if (vv[src] + 1) == vv_rec[src]:
        for i in node_ids:
            if i != src:
                if vv_rec[i] > vv[i]:
                    return False
    else:
        return False
    return True

def retest_buffer():
    for msg in buffer:
        if verify_delevery(msg.src, (msg.body.vv).__dict__):
            delivered.append(msg.body.message)
            vv[msg.src] += 1
            buffer.remove(msg)
            return True
    return False


for msg in receiveAll():
    match msg.body.type:
        case 'init':
            node_id = msg.body.node_id
            node_ids = msg.body.node_ids
            for i in node_ids:
                vv[i] = 0
            logging.info('node %s initialized', node_id)
            reply(msg, type='init_ok')
        case 'cbcast':
            vv[node_id] += 1
            reply(msg, type='cbcast_ok', messages=delivered)
            delivered = []
            broadcast(type='fwd_msg', vv=vv, message=msg.body.message)
        case 'fwd_msg':
            if verify_delevery(msg.src, (msg.body.vv).__dict__):
                vv[msg.src] += 1
                delivered.append(msg.body.message)
                while retest_buffer():
                    pass
            else:
                buffer.append(msg)




