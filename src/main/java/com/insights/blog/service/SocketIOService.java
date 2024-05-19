package com.insights.blog.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Data
public class SocketIOService {

    private final SocketIOServer socketIOServer;
    private final Map<UUID, SocketIOClient> clients = new ConcurrentHashMap<>();

    public SocketIOService(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
        this.socketIOServer.addConnectListener(this::onClientConnect);
        this.socketIOServer.addDisconnectListener(this::onClientDisconnect);
    }

    private void onClientConnect(SocketIOClient client) {
        clients.put(client.getSessionId(), client);
    }

    private void onClientDisconnect(SocketIOClient client) {
        clients.remove(client.getSessionId());
    }

    public void sendNotification(String event, Object data, UUID socketId) {
        SocketIOClient client = clients.get(socketId);
        if (client != null) {
            client.sendEvent(event, data);
        } else {
            System.out.println("Client not connected: " + socketId);
        }
    }
}
