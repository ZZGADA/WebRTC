const WebSocket = require('ws');

const socket = new WebSocket('ws://0.0.0.0:18081/init');

socket.on('open', function open() {
    console.log('WebSocket is open now.');
    const data = {
        schoolId: 2281,
        buildingId: 22810,
        classRoomId: 228100,
    }

    const message = {
        tyep: 'init',
        data: data
    }

    console.log(JSON.stringify(message));
    socket.send(JSON.stringify(message));
});

socket.on('message', function incoming(data) {
    console.log('Received message from server:', data);
});

socket.on('close', function close() {
    console.log('WebSocket is closed now.');
});

socket.on('error', function error(err) {
    console.error('WebSocket error observed:', err);
});