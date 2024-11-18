package org.example.signalserver.entity.po;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class ClassRoomPO {
    private Integer classRoomId;
    public ConcurrentHashMap<String, WebSocketSession> studentSessionPool;
}
