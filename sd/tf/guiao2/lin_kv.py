#!/usr/bin/env python

# Simple 'echo' workload in Python for Maelstrom

#run with
# ./maelstrom test --bin ../uni/4ano/sd/tf/guiao2/lin_kv.py --time-limit 10 --node-count 2 -w lin-kv --concurrency 4n

import logging
from ms import receiveAll, reply, exitOnError, send

logging.getLogger().setLevel(logging.DEBUG)
linkv = {}
#msg_id -> [msg, num of timestamps, num of updates, timestamp, writer]
write_requests = {}
#msg_id -> [msg, num of read_quorum_ok, value, timestamp, writer]
read_requests = {}

def broadcast(**kwds):
    for i in node_ids:
        if i != node_id:
            send(node_id, i, **kwds)

for msg in receiveAll():
    if msg.body.type == 'init':
        node_id = msg.body.node_id
        node_ids = msg.body.node_ids
        logging.info('node %s initialized', node_id)

        reply(msg, type='init_ok')
    elif msg.body.type == 'read':
        if msg.body.key in linkv:
            logging.info('read %s', msg.body.key)
            if msg.body.key in linkv:
                read_requests[msg.body.msg_id] = [msg, 0, linkv[msg.body.key][0], linkv[msg.body.key][1], linkv[msg.body.key][2]]   
            else:
                read_requests[msg.body.msg_id] = [msg, 0, None, 0, 'nn']
            broadcast(type='read_quorum', key=msg.body.key, request_id = msg.body.msg_id)
            
        else:
            msg.body.code = '20'
            msg.body.text = 'Key not found'
            reply(msg, type='error')
            logging.warning('value not found for key %s', msg.body.key)

    elif msg.body.type == 'write':
        logging.info('write %s', msg.body.key)
        #get highest timestamp
        if msg.body.key in linkv:
            write_requests[msg.body.msg_id] = [msg, 0, 0, linkv[msg.body.key][1], node_id]
        else:
            write_requests[msg.body.msg_id] = [msg, 0, 0, 0, node_id]
        broadcast(type='get_timestamp', key=msg.body.key, request_id = msg.body.msg_id)

    elif msg.body.type == 'update':
        logging.info('update %s', msg.body.key)
        if msg.body.key in linkv and msg.body.timestamp <= linkv[msg.body.key][1]:
            pass
        else:
            linkv[msg.body.key] = [msg.body.value, msg.body.timestamp, msg.body.writer]
            reply(msg, type='update_ok', request_id = msg.body.request_id)

    elif msg.body.type == 'cas':
        #send error not supported
        #não é um pedido atomico -> envolve leitura e escrita
        msg.body.code = '10'
        msg.body.text = 'CAS not supported'
        reply(msg, type='error')

    elif msg.body.type == 'get_timestamp':
        logging.info('get_timestamp %s', msg.body.key) 
        if msg.body.key in linkv:
            reply(msg, type='timestamp_ok', timestamp=linkv[msg.body.key][1], request_id = msg.body.request_id)
        else:
            reply(msg, type='timestamp_ok', timestamp=0, request_id = msg.body.request_id)

    elif msg.body.type == 'timestamp_ok':
        if msg.body.request_id in write_requests:
            write_requests[msg.body.request_id][1] += 1
            if (msg.body.timestamp > write_requests[msg.body.request_id][3]) or (msg.body.timestamp == write_requests[msg.body.request_id][3] and msg.src > write_requests[msg.body.request_id][4]):
                write_requests[msg.body.request_id][3] = msg.body.timestamp
            if write_requests[msg.body.request_id][1] > ((len(node_ids) / 2) - 1):
                write_requests[msg.body.request_id][1] = -1
                request = write_requests[msg.body.request_id]
                linkv[request[0].body.key] = [request[0].body.value, write_requests[msg.body.request_id][3] + 1, write_requests[msg.body.request_id][4]]
                broadcast(type='update', key=request[0].body.key, value=request[0].body.value, 
                            timestamp= write_requests[msg.body.request_id][3] + 1, writer = write_requests[msg.body.request_id][4],
                            request_id = msg.body.request_id)
    
    elif msg.body.type == 'update_ok':
        if msg.body.request_id in write_requests:
            write_requests[msg.body.request_id][2] += 1
            if write_requests[msg.body.request_id][2] > (len(node_ids) / 2) - 1:
                reply(write_requests[msg.body.request_id][0], type='write_ok')
                write_requests.pop(msg.body.request_id)
        
    elif msg.body.type == 'read_quorum':
        logging.info('read_quorum %s', msg.body.key)
        if msg.body.key in linkv:
            reply(msg, type='read_quorum_ok', key=msg.body.key, value=linkv[msg.body.key][0], 
                  timestamp=linkv[msg.body.key][1], writer = linkv[msg.body.key][2], request_id = msg.body.request_id)
        else:
            reply(msg, type='read_quorum_ok', key=msg.body.key, value=None,
                 timestamp=0, writer = None, request_id = msg.body.request_id)

    elif msg.body.type == 'read_quorum_ok':
        if msg.body.request_id in read_requests:
            request = read_requests[msg.body.request_id]
            request[1] += 1
            if (msg.body.timestamp > request[2]) or (msg.body.timestamp == request[2] and msg.body.writer > request[3]):
                request[2] = msg.body.timestamp
                request[3] = msg.body.writer
                request[4] = msg.body.value
            if request[1] > ((len(node_ids) / 2) - 1):
                if request[4] is None:
                    msg.body.code = '20'
                    msg.body.text = 'Key not found'
                    reply(request[0], type='error')
                    logging.warning('value not found for key %s', msg.body.request_id)
                else:
                    reply(request[0], type='read_ok', value=request[4])
                read_requests.pop(msg.body.request_id)
    else:
        logging.warning('unknown message type %s', msg.body.type)
