package org.example.signalserver.pool.layer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;


@Data
@AllArgsConstructor
public class ClassRoomLayer extends Layer{
    // session id 和 session的关系 一对一
    private Map<String, WebSocketSession> sessionMap;
}
