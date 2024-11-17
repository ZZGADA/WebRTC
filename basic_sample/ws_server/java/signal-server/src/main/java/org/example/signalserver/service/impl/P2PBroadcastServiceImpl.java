package org.example.signalserver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.signalserver.entity.po.SpecificLocationPO;
import org.example.signalserver.service.LocationService;
import org.example.signalserver.service.P2PBroadcastService;
import org.example.signalserver.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class P2PBroadcastServiceImpl implements P2PBroadcastService {
    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private LocationService locationService;
    /**
     * 广播服务 接收到message信息之后向所有连接的webSocket客户端发送消息
     *
     * @param session WebSocket session
     * @param message WebSocket message 需要广播出去的信息
     */

//    df71674c-305a-fb88-516c-8e743023958b
    @Override
    public void broadcast(WebSocketSession session, TextMessage message) {
        // 获取sessionId关联的具体地址信息（学校-教学楼-班级）
        SpecificLocationPO locationInfoOnSessionId = locationService.getSpecificLocationBySessionId(session.getId());
        System.out.println("locationInfoOnSessionId is " +locationInfoOnSessionId.toString() );
        Integer classRoomId = locationInfoOnSessionId.getClassRoomId();

        Map<String, WebSocketSession> mapSessionIdToSession = webSocketService.getClassRoomAllSessionByClassId(classRoomId);
        if(Objects.nonNull(mapSessionIdToSession)){
            // 如果教室有其他连接的话
            mapSessionIdToSession.forEach((sessionId, socketSession) -> {
                try {
                    if(!sessionId.equals(session.getId())){
                        socketSession.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
