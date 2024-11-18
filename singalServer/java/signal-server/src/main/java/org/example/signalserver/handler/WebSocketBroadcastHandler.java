package org.example.signalserver.handler;

import com.alibaba.fastjson2.JSON;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.signalserver.entity.dto.InitBindDTO;
import org.example.signalserver.pool.ClassRoomPool;
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
    private P2PBroadcastService p2PBroadcastService;

    @Autowired
    private WebSocketService webSocketService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("broadcast端点 连接建立 ip: {} , port : {}",
                Objects.nonNull(session.getRemoteAddress()) ? session.getRemoteAddress().getHostName() : "address is null",
                session.getRemoteAddress().getPort());
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message){
        try{
            InitBindDTO initBindDTO = JSON.parseObject(message.getPayload(), InitBindDTO.class);
            if(initBindDTO.getType().equals("ready")) {
                webSocketService.add(session, initBindDTO);
            }
            p2PBroadcastService.broadcast(session,message);
        }catch (Exception e){
            log.error("enter broadcast: session id is {}" ,session.getId());
            e.printStackTrace();
        }

    }

    /**
     * WebSocket断开
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        session.sendMessage(new TextMessage(JSON.toJSONString("close")));
        webSocketService.removeAndClose(session.getId());
        log.info("session id {} close", session.getId());
    }
}
