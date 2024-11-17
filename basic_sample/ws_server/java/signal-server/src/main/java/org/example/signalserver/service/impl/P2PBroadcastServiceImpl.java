package org.example.signalserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.signalserver.service.P2PBroadcastService;
import org.example.signalserver.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
@Slf4j
public class P2PBroadcastServiceImpl implements P2PBroadcastService {
    @Autowired
    private WebSocketService webSocketService;

    /**
     * 广播服务 接收到message信息之后向所有连接的webSocket客户端发送消息
     *
     * @param session WebSocket session
     * @param message WebSocket message
     */
    @Override
    public void broadcast(WebSocketSession session, TextMessage message) {

    }
}
