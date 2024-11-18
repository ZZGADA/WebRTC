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
    WebSocketSession remove(String sessionId);

    /**
     * 删除并同步关闭连接
     */
    void removeAndClose(String sessionId);


    /**
     * 获得 session
     */
    WebSocketSession get(Integer classRoomId,String sessionId);

    Map<String,WebSocketSession> getClassRoomAllSessionByClassId(Integer classRoomId);

    int getConnectedSessionNumByClassRoomId(Integer classRoomId);
}
