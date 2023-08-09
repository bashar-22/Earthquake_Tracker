-module(earthquakeReadings).
-export([start/0,periodic_readings_loop/3]).




start()->
  Args = [servermailbox,server101@localhost,40000],
  spawn(?MODULE, periodic_readings_loop,Args ).

scale(NewMax,NewMin,OldValue)->
  OldMin=0,
  OldRange = 1,
  NewRange = NewMax - NewMin,
  NewValue = (((OldValue - OldMin) * NewRange) / OldRange) + NewMin.

periodic_readings_loop(ServerMailBox,ServerNodeName,Period) ->
  io:format("I am ~w", [self()]),
  random:seed(now()),

  Magnitude=1+(rand:uniform()*10),
  Latitude=scale(90,-90,rand:uniform()),
  Longitude=scale(180,-180,rand:uniform()),
  Depth=scale(0,700,rand:uniform()),
  TS = {_,_,Micro} = os:timestamp(),
{{Year,Month,Day},{Hour,Minute,Second}} = calendar:now_to_universal_time(TS),
{ServerMailBox,ServerNodeName} ! {{Magnitude,Latitude,Longitude,Depth,Year,Month,Day,Hour,Minute,Second}, self()},
  timer:sleep(Period),

  receive   %in case a stop has been received, no further executions are triggered.
    stop -> ok;
    hi   -> ok
  after 0 ->
    periodic_readings_loop(ServerMailBox, ServerNodeName,Period)
  end.





