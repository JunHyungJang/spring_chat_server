'use strict';

var stompClient = null;

const wsUrl = 'ws://localhost:9123/chatting';
const connectBtn = document.getElementById('connect');
const disconnectBtn = document.getElementById('disconnect');
const sendBtn = document.getElementById('send');
const chatRoomBtn = document.getElementById('sendroomid');
let roomId;

if (sessionStorage.getItem('roomId')) {
    roomId = sessionStorage.getItem('roomId')
}
function setConnected(connected) {
    if (connected === undefined){
        connectBtn.disabled = true;
        disconnectBtn.disabled = false;
        sendBtn.disabled = true;
        return;
    }

    connectBtn.disabled = connected;
    disconnectBtn.disabled = !connected;
    sendBtn.disabled = !connected;

    if (connected) {
        document.getElementById("conversation").classList.remove("invisible");
        console.log("StompJs connected to broker over ws");

    } else {
        document.getElementById("conversation").classList.add("invisible");
    }
    document.getElementById("greetings").innerHTML = '';
}

function connectAndSubscribe() {
    setConnected(undefined);

    const client = new StompJs.Client({
        brokerURL: wsUrl,
        connectHeaders: {
            login: 'user',
            passcode: 'password',
        },
        debug: function (str) {
            console.log(str);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
    });

    client.onConnect = function (frame) {
        // Do something, all subscribes must be done is this callback
        // This is needed because this will be executed after a (re)connect
        console.log("StompJs connected to broker over ws");
        setConnected(true);
        // let roomId = 1
        // console.log(roomId)

        client.subscribe(`/sub/chatroom/${roomId}` , (message) => {
            // showGreeting(JSON.parse(message.body).content);
            console.log("C------ONSOLEMESSAGE------"+ (message.body));
            showGreeting(message.body);
        });
    };

    client.onStompError = function (frame) {
        // Will be invoked in case of error encountered at Broker
        // Bad login/passcode typically will cause an error
        // Complaint brokers will set `message` header with a brief message. Body may contain details.
        // Compliant brokers will terminate the connection after any error
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
    };

    client.activate();

    stompClient = client;
}

function disconnect() {
    if (stompClient) {
        stompClient.deactivate();
        stompClient = null;
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    const contents = document.getElementById('content').value;
    // console.log(JSON.stringify({'name': content, 'test' : "testvalue"}))
    const jsonObject = JSON.parse(contents);
    // jsonObject.roomId = roomId;
    const name = jsonObject.name
    const content = jsonObject.content

    console.log("JSON:" + jsonObject);
    stompClient.publish({
        destination: `/pub/chat/${roomId}`,
        body: JSON.stringify({'content': content, 'roomId' : roomId, 'name': name}),

        skipContentLengthHeader: true,
    });

}

function sendRoomId() {
    const roomId = document.getElementById('roomId').value;
    // console.log(roomId)
    sessionStorage.setItem("roomId",roomId)
    var a = sessionStorage.getItem("roomId")
    console.log(a);

}


function showGreeting(message) {
    const newRow = document.createElement('tr');
    newRow.innerHTML = `<td>${message}</td>`;
    document.getElementById('greetings').appendChild(newRow);
}

if (typeof StompJs === 'undefined') {
    throw new Error('StompJs is not available');
}

for (let i=0; i<document.forms.length; i++) {
    document.forms.item(i).addEventListener('submit', (e) => e.preventDefault());
}

connectBtn.addEventListener('click', (e) => connectAndSubscribe());
disconnectBtn.addEventListener('click', (e) => disconnect());
sendBtn.addEventListener('click', (e) => sendName());
chatRoomBtn.addEventListener('click',(e)=> sendRoomId())
setConnected(false);

