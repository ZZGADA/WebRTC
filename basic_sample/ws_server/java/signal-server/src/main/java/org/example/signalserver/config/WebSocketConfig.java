package org.example.signalserver.config;

import org.example.signalserver.handler.WebSocketBroadcastHandler;
import org.example.signalserver.handler.WebSocketInitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketBroadcastHandler webSocketBroadcastHandler;

    @Autowired
    private WebSocketInitHandler webSocketInitHandler;

    /**
     * 注册一个 WebSocket 处理程序
     * 添加处理的handler
     *
     * @param registry 注册
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 添加广播socket
        registry.addHandler(webSocketBroadcastHandler, "/signal").
                setAllowedOrigins("*");

        // 添加初始化socket
        registry.addHandler(webSocketInitHandler,"/init").
                setAllowedOrigins("*");
    }
}
