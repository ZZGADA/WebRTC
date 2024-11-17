package org.example.signalserver.handler;


import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.signalserver.service.P2PBroadcastService;
import org.example.signalserver.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Objects;

@Component
@Slf4j
public class WebSocketBroadcastHandler extends TextWebSocketHandler {
    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private P2PBroadcastService p2PBroadcastService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("webSocket 连接建立 ip: {} , port : {}",
                Objects.nonNull(session.getRemoteAddress()) ? session.getRemoteAddress().getHostName() : "address is null",
                session.getRemoteAddress().getPort());

        // 加载session
        webSocketService.add(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        p2PBroadcastService.broadcast(session,message);
    }
}
