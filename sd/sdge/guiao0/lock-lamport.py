#!/usr/bin/env python3.10


import logging
from ms import receiveAll, reply, send

node_id = None
node_ids = []
acquired = None
requests = []
timestamps = {}

def broadcast(**kwds):
    for i in node_ids:
        if i != node_id:
            send(node_id, i, **kwds)

def handle_init():
    node_id = msg.body.node_id
    node_ids = msg.body.node_ids
    for node in node_ids:
        timestamps[node] = 0
    logging.info('node %s initialized', node_id)
    reply(msg, type='init_ok')

def handle_lock():
    timestamps[node_id] += 1
    requests.append((msg.src, timestamps[node_id]))
    broadcast(type='request_lock', timestamp=timestamps[node_id], client=msg.src)

def handle_unlock():
    timestamps[node_id] += 1
    if acquired and acquired == msg.src:
        lock = True
        my_request = True
        if requests:
            request = requests[0]
            for time in timestamps.values():
                if request[1] > time:
                    lock = False
                    break
        if lock and requests:
            acquired = request[0]
            requests.pop(0)
            send(node_id, acquired, type='lock_ok')
        else:
            acquired = None
            broadcast(type='release_lock', timestamp=timestamps[node_id], request_timestamp=request[1], client=request.src)
        reply(msg, type='unlock_ok')

def handle_request_lock():
    #return timestamp as aknowledgement
    #This acknowledgment message need not be sent if Pj has already
    #sent a message to Pi timestamped later than Tm
    timestamps[node_id] += 1
    if not acquired:
        acquired = msg.body.client
    requests.append((msg.body.client, msg.body.timestamp))
    if timestamps[msg.src] < msg.body.timestamp:
        timestamps[msg.src] = msg.body.timestamp
        reply(msg, type='ack', timestamp=timestamps[node_id])

def handle_ack():
    timestamps[msg.src] = msg.body.timestamp
    timestamps[node_id] += 1
    lock = True
    if not acquired:
        request = requests[0]
        for time in timestamps.values():
            if request[1] > time:
                lock = False
                break
        if lock:
            acquired = request[0]
            requests.pop(0)
            send(node_id, acquired, type='lock_ok')

def handle_release_lock():
    timestamps[msg.src] = msg.body.timestamp
    timestamps[node_id] += 1
    acquired = None
    requests.remove((msg.body.client, msg.body.request_timestamp))
    if requests:
        request = requests[0]
        lock = True
        for time in timestamps.values():
            if request[1] > time:
                lock = False
                break
        if lock:
            acquired = request[0]
            requests.pop(0)
            send(node_id, acquired.src, type='lock_ok')
            broadcast(type='request_lock', timestamp=request[1], client=acquired.src)
            

for msg in receiveAll():
    match msg.body.type:
        case 'init':
            handle_init()
        case 'lock':
            handle_lock()
        case 'unlock':
            handle_unlock()
        case 'request_lock':
            handle_request_lock()
        case 'ack':
            handle_ack()
        case 'release_lock':
            handle_release_lock()
