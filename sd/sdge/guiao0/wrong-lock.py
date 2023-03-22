#!/usr/bin/env python3.10

"""Obviously wrong implementation for 'lock' workload for Maelstrom"""

import logging
from ms import receiveAll, reply

logging.getLogger().setLevel(logging.DEBUG)

for msg in receiveAll():
    match msg.body.type:
        case 'init':
            node_id = msg.body.node_id
            node_ids = msg.body.node_ids
            logging.info('node %s initialized', node_id)
            reply(msg, type='init_ok')
        case 'lock':
            reply(msg, type='lock_ok')
        case 'unlock':
            reply(msg, type='unlock_ok')

