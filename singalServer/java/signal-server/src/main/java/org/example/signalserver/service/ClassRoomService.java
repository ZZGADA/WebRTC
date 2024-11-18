package org.example.signalserver.service;

import org.springframework.web.socket.WebSocketSession;

public interface ClassRoomService extends PoolService {
    boolean ifHasClassRoom(int classRoomId);
    void initClassRoomLayer(int classRoomId);
    void bindWebSocketSession(int classRoomId,String sessionId, WebSocketSession session);
    WebSocketSession popWebSocketSession(int classRoomId,String sessionId);
    WebSocketSession getWebSocketSession(int classRoomId,String sessionId);
    void removeWebSocketSession(int classRoomId,String sessionId);
    int getConnectedSessionNumByClassRoomId(Integer classRoomId);
}
