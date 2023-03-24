#!/usr/bin/env python3.10

import logging
from ms import receiveAll, reply, send
from types import SimpleNamespace

#run with
#java -jar ../guiao2/maelstrom.jar test -w or-set --bin or-set.py  --time-limit 5 --node-count 3 
# or
#java -jar maelstrom.jar test -w or-set --bin or-set.py  --time-limit 5 --node-count 3 --latency-dist uniform --latency 500
logging.getLogger().setLevel(logging.DEBUG)

node_id = None
node_ids = None
vv = {}
m = {}

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
            #addi(e,(m, c)) = (m{e → {(i, c[i] + 1)}}, c{i → c[i] + 1})
            vv[node_id] += 1
            if msg.body.element not in m:
                m[msg.body.element] = []
            if node_id not in m[msg.body.element]:
                m[msg.body.element].append(vv.copy())
            else:
                m[msg.body.element][node_id] = c
            random = random.randint(0, 5)
            if random == 0:
                broadcast(type='join', dotmap = m)
        case 'remove':
            if msg.body.element in m:
                m.pop(msg.body.element)

        case 'read':
            reply(msg, type='read_ok', value = list(m.keys()))
            
        case 'join':
            for element in msg.body.dotmap:
                for i in node_ids:
                    vv[i] = max(vv[i], msg.body.dotmap[element][i])
            

                
            


