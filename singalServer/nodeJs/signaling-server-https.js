const fs = require('fs');
const https = require('https');
// 忽略SSL证书错误
// https.globalAgent.options.rejectUnauthorized = false;
const WebSocket = require('ws');

// 读取 SSL 证书和密钥
const serverOptions = {
  cert: fs.readFileSync('cert.pem'),
  key: fs.readFileSync('key.pem'),
  passphrase: 'zZ-szshyjbz16D'
};

// 创建 HTTPS 服务器
const server = https.createServer(serverOptions);

// 创建 WebSocket 服务器并绑定到 HTTPS 服务器
const wss = new WebSocket.Server({ server });

wss.on('connection', function connection(ws, req) {
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
      if (client !== ws && client.readyState === WebSocket.OPEN) {
        client.send(JSON.stringify(parsedMessage));
      }
    });
  });

  ws.on('close', function close() {
    console.log('Client disconnected');
  });
});

// 监听指定端口
const PORT = 53378;
const HOST = '0.0.0.0';
server.listen(PORT, HOST, function() {
  console.log(`Signaling server is running on wss://${HOST}:${PORT}`);
});
