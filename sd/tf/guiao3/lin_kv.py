#!/usr/bin/env python

# Simple 'echo' workload in Python for Maelstrom

#run with
# ./maelstrom test --bin ../uni/4ano/sd/tf/guiao3/lin_kv.py --time-limit 10 --node-count 4 -w lin-kv --concurrency 8n


#Log replication
#1.The leader appends the command to its log as a new entry
#2.The leader sends AppendEntries RPCs to each of the other servers to replicate the entry
#3.When the entry has been safely replicated, the leader increments commitIndex and applies the command to its state machine
#4.Send response to client
#5 If followers crash, leader retries AppendEntries RPCs indefinitely

#Each log entry stores a state machine command along with the term
#number when the entry was received by the leade
import logging
import random
from threading import Thread
from ms import receiveAll, reply, exitOnError, send

import enum

class State(enum.Enum):
    FOLLOWER = 1
    CANDIDATE = 2
    LEADER = 3

logging.getLogger().setLevel(logging.DEBUG)
linkv = {}

state = None
currentTerm = 0
votedFor = None
log = []

index = 1
commitIndex = 0
lastApplied = 0

nextIndex = []
matchIndex = []

vote_count = 0

class Command:
    def __init__(self, type, key, value, term, index, src, resp=0):
        self.type = type
        self.key = key
        self.value = value
        self.term = term
        self.index = index
        self.src = src
        self.resp = resp

    def __eq__(self, __o: object) -> bool:
        if isinstance(__o, Command):
            return self.type == __o.type and self.key == __o.key and self.value == __o.value
        return False

def send_heartbeat():
    while state == State.LEADER:
        Thread.sleep(0.08)
    
def request_vote():
    term = currentTerm + 1
    broadcast(type='request_vote', term=term, candidate_id=node_id, lastLogIndex=len(log)-1, lastLogTerm=log[len(log)-1].term)

# Reply false if term < currentTerm
def verify_term(msg):
    if msg.body.term < currentTerm:
        return False
    return True

#Reply false if log doesn’t contain an entry at prevLogIndex
#whose term matches prevLogTerm
def verify_prevLogIndex(msg):
    if len(log) == 0:
        return True
    if msg.body.prevLogIndex < len(log):
        if log[msg.body.prevLogIndex].term == msg.body.prevLogTerm:
            return True
    return False


def broadcast(**kwds):
    for i in node_ids:
        if i != node_id:
            send(node_id, i, **kwds)

for msg in receiveAll():
    if msg.body.type == 'init':
        node_id = msg.body.node_id
        node_ids = msg.body.node_ids
        logging.info('node %s initialized', node_id)
        state = State.FOLLOWER
        if node_id == node_ids[0]:
            state = State.LEADER

        reply(msg, type='init_ok')
    elif msg.body.type == 'read':
        if state == State.LEADER:
            if msg.body.key in linkv:
                logging.info('read %s', msg.body.key)
                reply(msg, type='read_ok', value=linkv[msg.body.key])

            else:
                msg.body.code = '20'
                msg.body.text = 'Key not found'
                reply(msg, type='error')
                logging.warning('value not found for key %s', msg.body.key)

    elif msg.body.type == 'write':
        logging.info('write %s', msg.body.key)
        if state == State.LEADER:
            c = Command('write', msg.body.key, msg.body.value, currentTerm, index, msg)
            index += 1
            log.append(c)
            if len(log) == 1:
                prevLogTerm = 0
            else:
                prevLogTerm = log[commitIndex].term
            broadcast(type='log_replication', term=currentTerm, leader_id=node_id, 
                      prevLogIndex=commitIndex, prevLogTerm = prevLogTerm,
                      entries=[c], leadercommit=commitIndex)
            commitIndex += 1

    elif msg.body.type == 'update':
        logging.info('update %s', msg.body.key)
        linkv[msg.body.key] = msg.body.value
        
        
    elif msg.body.type == 'cas':
        #send error not supported
        #não é um pedido atomico -> envolve leitura e escrita
        msg.body.code = '10'
        msg.body.text = 'CAS not supported'
        reply(msg, type='error')

    elif msg.body.type == 'log_replication':
        logging.info('log replication')
        if verify_term(msg) and verify_prevLogIndex(msg):
            # If an existing entry conflicts with a new one (same index
            #but different terms), delete the existing entry and all that
            #follow it 
            for i in range(msg.body.prevLogIndex + 1, len(log)):
                if i < len(log) and log[i].term != msg.body.term:
                    log = log[:i]
                    break
            # Append any new entries not already in the log
            for entry in msg.body.entries:
                if entry not in log:
                    log.append(entry)
            #If leaderCommit > commitIndex, set commitIndex =
            #min(leaderCommit, index of last new entry)
            if msg.body.leadercommit > commitIndex:
                commitIndex = min(msg.body.leadercommit, len(log)-1)
            reply(msg, type='log_replication_resp', success=True, term=currentTerm)
        else:
            reply(msg, type='log_replication_resp', success=False, term=currentTerm)


    elif msg.body.type == 'log_replication_resp':
        logging.info('log replication response')
        if msg.body.success == True:
            request = log[len(log)-1]
            request.resp += 1
            #If there a majority of replicas responses, apply the command to the state machine
            if request.resp > (len(node_ids)//2) - 1:
                linkv[request.key] = request.value
                reply(request.src, type='write_ok')
                broadcast(type='update', key=request.key, value=request.value)
                lastApplied += 1
                request.resp = 0

    elif msg.body.type == 'request_vote':
        logging.info('request vote')
        #Reply false if term < currentTerm
        if verify_term(msg):
            if votedFor == None or votedFor == msg.body.candidate_id:
                if msg.body.lastLogIndex >= len(log)-1:
                    votedFor = msg.body.candidate_id
                    currentTerm = msg.body.term
                    reply(msg, type='request_vote_resp', vote_granted=True, term=currentTerm)

        reply(msg, type='request_vote_resp', vote_granted=False, term=currentTerm)

    elif msg.body.type == 'request_vote_resp':
        if msg.body.vote_granted == True:
            vote_count += 1
            if vote_count > (len(node_ids)//2) - 1:
                state = State.LEADER
                votedFor = None
                vote_count = 0
                Thread(target=send_heartbeat).start()
                logging.info('leader elected')