package com.insights.blog.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
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

    public void sendNotification(String eventName, Object data, UUID clientId) {
        SocketIOClient client = clients.get(clientId);
        if (client != null) {
            client.sendEvent(eventName, data);
        }
    }

    public void broadcastNotification(String eventName, Object data) {
        Collection<SocketIOClient> clientsCollection = clients.values();
        for (SocketIOClient client : clientsCollection) {
            client.sendEvent(eventName, data);
        }
    }
}
