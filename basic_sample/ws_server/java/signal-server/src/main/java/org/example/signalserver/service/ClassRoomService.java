package org.example.signalserver.service;

import org.springframework.web.socket.WebSocketSession;

public interface ClassRoomService extends PoolService {
    boolean ifHasClassRoom(int classRoomId);
    void initClassRoomLayer(int classRoomId);
    void bindWebSocketSession(int classRoomId,String sessionId, WebSocketSession session);
}
