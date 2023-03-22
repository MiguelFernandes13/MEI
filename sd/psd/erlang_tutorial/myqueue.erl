-module(myqueue).
-export([create/0, enqueue/2, dequeue/1, test/0]).

create() ->
    [].

enqueue([], Item) ->
    [Item];
enqueue([H|T], Item) ->
    [H|enqueue(T, Item)].

dequeue([]) ->
    empty;
dequeue([Item|Queue]) ->
    {Queue, Item}.

test() ->
    L = create(),
    L1 = enqueue(L, 1),
    L2 = enqueue(L1, 2),
    {L3, 1} = dequeue(L2),
    {L4, 2} = dequeue(L3),
    empty = dequeue(L4),
    ok.


    