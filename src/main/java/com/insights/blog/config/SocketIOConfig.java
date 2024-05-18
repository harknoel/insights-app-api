package com.insights.blog.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {

    private static final com.corundumstudio.socketio.Configuration CONFIG = new com.corundumstudio.socketio.Configuration();

    @Bean
    public SocketIOServer socketIOServer() {
        CONFIG.setHostname("localhost");
        CONFIG.setPort(9092);

        return new SocketIOServer(CONFIG);
    }
}
