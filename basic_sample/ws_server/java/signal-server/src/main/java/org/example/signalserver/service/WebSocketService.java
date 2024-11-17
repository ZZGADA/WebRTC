package org.example.signalserver.service;

import org.example.signalserver.entity.dto.InitBindDTO;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;


public interface WebSocketService {
    /**
     * 添加 session
     */
    void add(WebSocketSession session, InitBindDTO initBindDTO);

    /**
     * 删除 session,会返回删除的 session
     */
    WebSocketSession remove(String key);

    /**
     * 删除并同步关闭连接
     */
    void removeAndClose(String key);


    /**
     * 获得 session
     */
    WebSocketSession get(String key);

    Map<String,WebSocketSession> getClassRoomAllSessionByClassId(Integer classRoomId);
}
