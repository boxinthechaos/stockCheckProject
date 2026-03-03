package com.example.stock.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocket implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // STOMP WebSocket 엔드포인트를 등록합니다.
        // 클라이언트가 "/ws" 경로로 WebSocket 연결을 시작합니다.
        // .withSockJS()는 WebSocket을 지원하지 않는 브라우저를 위해 SockJS 폴백 옵션을 추가합니다.
        registry.addEndpoint("/ws").withSockJS();
    }
}
