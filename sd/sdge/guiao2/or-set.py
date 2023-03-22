#!/usr/bin/env python3.10

import logging
from ms import receiveAll, reply, send
from types import SimpleNamespace

#run with
#java -jar maelstrom.jar test -w or-set --bin or-set.py  --time-limit 5 --node-count 3 
# or
#java -jar maelstrom.jar test -w or-set --bin or-set.py  --time-limit 5 --node-count 3 --latency-dist uniform --latency 500
logging.getLogger().setLevel(logging.DEBUG)

node_id = None
node_ids = None
vv = {}
values = set()
m = dict()
c = 0
#delivered = []
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
        case 'add':
            vv[node_id] += 1
            c += 1
            if msg.body.element not in m:
                m[msg.body.element] = []               
            broadcast(type='fwd_add', element = msg.body.element, id = (node_id, c), r = m[msg.body.element], vv=vv)
            m[msg.body.element] = [(node_id, c)]
        case 'remove':
            vv[node_id] += 1
            if msg.body.element not in m:
                #send error
                reply(msg, type='error', code = 20, message = 'element not in set')
            else:   
                for (i, v) in enumerate(m[msg.body.element]):
                    send(node_id, v, type='fwd_remove', element = msg.body.element, r = m[msg.body.element], vv=vv)

        case 'read':
            reply(msg, type='read_ok', value = list(m.keys()))
            
        case 'fwd_add':
            if verify_delevery(msg.src, (msg.body.vv).__dict__):
                vv[msg.src] += 1
                if msg.body.element not in m:
                    m[msg.body.element] = []
                m[msg.body.element] = [x for x in m[msg.body.element] if x not in msg.body.r]
                m[msg.body.element].append(msg.body.id)
        case 'fwd_remove':
            if verify_delevery(msg.src, (msg.body.vv).__dict__):
                vv[msg.src] += 1
                if msg.body.element not in m:
                    m[msg.body.element] = []
                m[msg.body.element] = [x for x in m[msg.body.element] if x not in msg.body.r]




