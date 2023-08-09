%%%-------------------------------------------------------------------
%%% @author Bashar
%%% @copyright (C) 2022, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 14. Apr 2022 3:18 AM
%%%-------------------------------------------------------------------
-module('earthquake-readings').
-author("Bashar").

%% API
-export([start/3]).

-start(RegisteredServerProcName, ServerNodeName,Period)->
spawn(?MODULE, periodic_readings_loop, [RegisteredServerProcName,ServerNodeName,Period]).
-scale(NewMax,NewMin,OldValue)->
OldRange = 1,
NewRange = (NewMax - NewMin),
NewValue = (((OldValue - OldMin) * NewRange) / OldRange) + NewMin.

-periodic_readings_loop(RegisteredServerProcName,ServerNodeName,Period) ->
  io:format("I am ~w", [self()]),
random:seed(now()),
Magnitude=1+(random:uniform()*10),
Latitude=scale(90,-90,random:uniform()),
Longitude=scale(180,-180,random:uniform()),
Depth=scale(0,700,random:uniform()),
{RegisteredServerName,ServerNodeName} ! {{Magnitude,Latitude,Longitude,Depth}, self()},
  sleep(Period),

  receive   %in case a stop has been received, no further executions are triggered.
    stop -> ok
  after 0 ->
    periodic_task_loop(RegisteredServerProcName, ServerNodeName,Period)
  end.
