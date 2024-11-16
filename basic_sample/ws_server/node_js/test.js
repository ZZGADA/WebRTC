const WebSocket = require('ws');

const socket = new WebSocket('wss://0.0.0.0:53378', { rejectUnauthorized: false });

socket.on('open', function open() {
    console.log('WebSocket is open now.');
    // 创建一个 JSON 对象
    const message = {
        type: 'greeting',
        content: 'Hello Server!',
        timestamp: new Date().toISOString()
    };

    // 将 JSON 对象序列化为字符串并发送
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