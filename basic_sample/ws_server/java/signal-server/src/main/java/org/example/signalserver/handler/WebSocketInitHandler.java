package org.example.signalserver.handler;

import com.alibaba.fastjson2.JSON;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.signalserver.entity.dto.InitBindDTO;
import org.example.signalserver.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Objects;

@Component
@Slf4j
public class WebSocketInitHandler extends TextWebSocketHandler {
    @Autowired
    private WebSocketService webSocketService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("init 端点 连接建立 ip: {} , port : {}",
                Objects.nonNull(session.getRemoteAddress()) ? session.getRemoteAddress().getHostName() : "address is null",
                session.getRemoteAddress().getPort());

    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session,@NonNull TextMessage message) throws Exception {
        InitBindDTO initBindDTO = JSON.parseObject(message.getPayload(), InitBindDTO.class);
        webSocketService.add(session,initBindDTO);
    }


}
