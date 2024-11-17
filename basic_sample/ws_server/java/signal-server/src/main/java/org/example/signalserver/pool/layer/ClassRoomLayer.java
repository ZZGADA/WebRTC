package org.example.signalserver.pool.layer;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;



@Data
public class ClassRoomLayer {
    private Integer classRoomId;
    public Map<String, WebSocketSession> studentSessionPool;
}
