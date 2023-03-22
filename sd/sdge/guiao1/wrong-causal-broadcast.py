#!/usr/bin/env python3.10

import logging
from ms import receiveAll, reply, send

#run with
#./maelstrom test -w cbcast --bin wrong-causal-broadcast.py  --time-limit 5 --node-count 3 --concurrency 1n

logging.getLogger().setLevel(logging.DEBUG)

node_id = None
node_ids = None
vv = {}
delivered = []

def broadcast(**kwds):
    for i in node_ids:
        if i != node_id:
            send(node_id, i, **kwds)

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
            delivered.append(msg.body.message)




