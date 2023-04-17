#!/usr/bin/env python3.10

import logging
import math
import random
from ms import receiveAll, reply, send

#run with (in folder guiao1)
#./maelstrom test -w cbcast --bin ../epidemic_multicast/epidemic.py --time-limit 5 --node-count 3 --concurrency 1n --topology total

logging.getLogger().setLevel(logging.DEBUG)

#State
#0 -> Susceptible
#1 -> Infected
#2 -> Recovered
state = None
node_id = None
node_ids = None
f = None
k = None
delivered = []

def sampler():
    return random.sample(node_ids, f)

for msg in receiveAll():
    match msg.body.type:
        case 'init':
            node_id = msg.body.node_id
            node_ids = msg.body.node_ids
            state = 0
            f = 6
            k = 3
            logging.info('node %s initialized', node_id)
            reply(msg, type='init_ok')
        case 'cbcast':
            reply(msg, type='cbcast_ok', messages=delivered)
            delivered = []
            for i in sampler():
                send(node_id, i, type='fwd_msg', message=msg.body.message, count=0)
        case 'fwd_msg':
            if msg.body.message not in delivered:
                delivered.append(msg.body.message)
                if msg.body.count < k:
                    for i in sampler():
                        send(node_id, i, type='fwd_msg', message=msg.body.message, count=msg.body.count+1)
                else:
                    for i in sampler():
                        send(node_id, i, type='have', message=msg.body.message)
        case 'have':
            if msg.body.message in delivered:
                reply(msg, type='ihave')
            else:
                reply(msg, type='iwant')






