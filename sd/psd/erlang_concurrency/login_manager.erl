-module(login_manager).
-export([start/0,
        create_account/2,
        close_account/2,
        login/2,
        logout/1,
        online/0]).

start() ->
    %PID = spawn(fun() -> loop([], []) end),
    %register(login_manager, PID).
    %OU
    register(?MODULE, spawn(fun() -> loop(#{}) end)).

loop(Map) ->
    receive
        {{create_account, Username, Passwd}, From} ->
            case maps:find(Username, Map) of
                {ok, _} ->
                    From ! {user_exists, ?MODULE},
                    loop(Map);
                error ->
                    From ! {ok, ?MODULE},
                    loop(maps:put(Username, {Passwd, true}, Map))
            end;
        {{close_account, Username, Passwd}, From} ->
            case maps:find(Username, Map) of
                {Ok, {Passwd, _}} ->
                    From ! {ok, ?MODULE},
                    loop(maps:remove(Username, Map));
                _ ->
                    From ! {invalid, ?MODULE},
                    loop(Map)
            end;
        {{login, Username, Passwd}, From} ->
            case maps:find(Username, Passwd) of
                {ok, {Passwd, false}} ->
                    From ! {ok, ?MODULE},
                    loop(maps:put(Username, {Passwd, true}, Map));
                _ ->
                    From ! {invalid, ?MODULE},
                    loop(Map)
            end;
        {{logout, Username}, From} ->
            case maps:find(Username, Passwd) of
                {ok, {Passwd, true}} ->
                    From ! {ok, ?MODULE},
                    loop(maps:put(Username, {Passwd, false}, Map));
                _ ->
                    From ! {invalid, ?MODULE},
                    loop(Map)
            end;
        {online, From} ->
            Pred = fun(_, {_, true}, Acc) -> Acc ++ [User];
                        (_, _, Acc) -> Acc
                     end,  
            From ! {maps:fold(Pred, Map, [])},
            loop(Map)
    end.


rpc(Req) ->
    ?MODULE ! {Req, self()},
    receive
        {Res, ?MODULE} ->
            Res
    end.

create_account(User, Passwd) ->
    rpc({create_account, User, Passwd}).

close_account(User, Passwd) ->
    rpc({close_account, User, Passwd}).

login(User, Passwd) ->
    rpc({login, User, Passwd}).

logout(User) ->
    rpc({logout, User}).

online() ->
    rpc(online).
