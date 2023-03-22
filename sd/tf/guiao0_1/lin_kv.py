#!/usr/bin/env python

# Simple 'echo' workload in Python for Maelstrom

import logging
from ms import receiveAll, reply, exitOnError, send

logging.getLogger().setLevel(logging.DEBUG)
linkv = {}

for msg in receiveAll():
    if msg.body.type == 'init':
        node_id = msg.body.node_id
        node_ids = msg.body.node_ids
        logging.info('node %s initialized', node_id)

        reply(msg, type='init_ok')
    elif msg.body.type == 'read':
        logging.info('read %s', msg.body.key)
        if msg.body.key in linkv:
            reply(msg, type='read_ok', value=linkv[msg.body.key])
            logging.info('value %s found for key %s', linkv[msg.body.key],msg.body.key)
        else:
            msg.body.code = '20'
            msg.body.text = 'Key not found'
            reply(msg, type='error')
            logging.warning('value not found for key %s', msg.body.key)
            
    elif msg.body.type == 'write':
        logging.info('write %s', msg.body.key)
        linkv[msg.body.key] = msg.body.value
        reply(msg, type='write_ok')
        for node in node_ids:
            if node != node_id and node != msg.src:
                send(node_id, node, type='update', key=msg.body.key, value=msg.body.value)

    elif msg.body.type == 'update':
        logging.info('update %s', msg.body.key)
        linkv[msg.body.key] = msg.body.value
        reply(msg, type='update_ok')

    elif msg.body.type == 'cas':
        logging.info('cas %s', msg.body.key)
        if msg.body.key in linkv:
            if getattr(msg.body, "from") == linkv[msg.body.key]:
                linkv[msg.body.key] = msg.body.to
                reply(msg, type='cas_ok')
                for node in node_ids:
                    if node != node_id and node != msg.src:
                        send(node_id, node, type='update', key=msg.body.key, value=msg.body.to)
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
        logging.warning('unknown message type %s', msg.body.type)
