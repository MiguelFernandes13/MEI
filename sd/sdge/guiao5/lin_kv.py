#!/usr/bin/env python

# Simple 'echo' workload in Python for Maelstrom

#run with
# ./maelstrom test --bin ../uni/4ano/sd/sdge/guiao5/lin_kv.py --time-limit 10 --node-count 4 -w lin-kv --concurrency 8n

from collections import OrderedDict
import logging
import random

from ms import receiveAll, reply, send

logging.getLogger().setLevel(logging.DEBUG)
linkv = {}

static_routing = OrderedDict()
m = 3

def get_server(key):
    hash_value = key % (2**m)
    for n in static_routing.keys():
        if static_routing[n] >= 


def broadcast(**body):
    for n in node_ids:
        if n != node_id:
            send(node_id, n, type=type, **body)

for msg in receiveAll():
    if msg.body.type == 'init':
        node_id = msg.body.node_id
        node_ids = msg.body.node_ids
        random_id = random.randint(0, (2**m)-1)
        random_id = random_id % (2**m)
        static_routing[node_id] = random_id
        broadcast(type='table', random_id=random_id)
        logging.info('node %s initialized', node_id)

        reply(msg, type='init_ok')
    elif msg.body.type == 'read':
        logging.info('read %s', msg.body.key)
        if static_routing[node_id] >= msg.body.key: 
            if msg.body.key in linkv:
                if hasattr(msg.body, 'client'):
                    send(node_id, msg.body.client, type='read_ok', value=linkv[msg.body.key])
                else:
                    reply(msg, type='read_ok', value=linkv[msg.body.key])
                logging.info('value %s found for key %s', linkv[msg.body.key],msg.body.key)
            else:
                msg.body.code = '20'
                msg.body.text = 'Key not found'
                reply(msg, type='error')
                logging.warning('value not found for key %s', msg.body.key)
        else:
            for 
            send(node_id, node_ids[0], type='read', key=msg.body.key, client=msg.src)
            
    elif msg.body.type == 'write':
        logging.info('write %s', msg.body.key)
        if node_id == node_ids[0]:
            linkv[msg.body.key] = msg.body.value
            if hasattr(msg.body, 'client'):
                send(node_id, msg.body.client, type='write_ok')
            else:
                reply(msg, type='write_ok')
        else:
            send(node_id, node_ids[0], type='write', key=msg.body.key, value=msg.body.value, client=msg.src)

    elif msg.body.type == 'update':
        logging.info('update %s', msg.body.key)
        linkv[msg.body.key] = msg.body.value
        reply(msg, type='update_ok')

    elif msg.body.type == 'cas':
        logging.info('cas %s', msg.body.key)
        if node_id == node_ids[0]:
            if msg.body.key in linkv:
                if hasattr(msg.body, "client") and msg.body.old_value == linkv[msg.body.key]:
                    linkv[msg.body.key] = msg.body.to
                    send(node_id, msg.body.client, type='cas_ok')
                elif getattr(msg.body, "from") == linkv[msg.body.key]:
                    linkv[msg.body.key] = msg.body.to
                    reply(msg, type='cas_ok')                     
                else:
                    msg.body.code = '22'
                    msg.body.text = 'From value does not match key'
                    reply(msg, type='error')
                    logging.error('22')
            else:
                msg.body.code = '20'
                msg.body.text = 'Key not found'
                reply(msg, type='error')
                logging.error('20')
        else:
            send(node_id, node_ids[0], type='cas', key=msg.body.key, old_value=getattr(msg.body, "from"), to=msg.body.to, client=msg.src)
    elif msg.body.type == 'table':
        static_routing[msg.src] = msg.body.random_id

    else:
        logging.warning('unknown message type %s', msg.body.type)

