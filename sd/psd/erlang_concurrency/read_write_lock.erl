-module(read_write_lock).
-export([create/0,
            acquire/2,
            release/1]).

create() ->
    