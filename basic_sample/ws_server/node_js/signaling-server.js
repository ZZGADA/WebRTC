const WebSocket = require('ws');

// 创建 WebSocket 服务器并监听 0.0.0.0:53378
const wss = new WebSocket.Server({ host: '0.0.0.0', port: 53378 });

wss.on('connection', function connection(ws,req) {
  console.log('A new client connected');
  const ip = req.socket.remoteAddress;
  const port = req.socket.remotePort;
  console.log(`New client connected from IP: ${ip} PORT:${port}`);

  ws.on('message', function incoming(message) {
    console.log('received: %s', message);

    // 解析接收到的消息
    let parsedMessage;
    try {
      parsedMessage = JSON.parse(message);
    } catch (error) {
      console.error('Error parsing message:', error);
      return;
    }

    // 广播消息给所有连接的客户端
    // 向所有"处于准备" 同时 "不是自己" 的其他客户端发送广播消息
    wss.clients.forEach(function each(client) {
        console.log(client.url)
      if (client !== ws && client.readyState === WebSocket.OPEN) {
        client.send(JSON.stringify(parsedMessage));
      }
    });
  });

  ws.on('close', function close() {
    console.log('Client disconnected');
  });
});

console.log('Signaling server is running on ws://0.0.0.0:53378');