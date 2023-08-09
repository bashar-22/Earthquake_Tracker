var ws;
var host = document.location.host;
var pathname = document.location.pathname;
const url = "ws://" + host + pathname + "/MessageSocket1";

ws = new WebSocket(url);

ws.onmessage = function(event) {
    console.log(event.data);
};

