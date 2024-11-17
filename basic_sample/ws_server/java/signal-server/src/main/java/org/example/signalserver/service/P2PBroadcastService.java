package org.example.signalserver.service;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public interface P2PBroadcastService {
    void broadcast(WebSocketSession session, TextMessage message);
}
