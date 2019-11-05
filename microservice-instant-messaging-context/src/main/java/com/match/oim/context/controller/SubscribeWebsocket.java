package com.match.oim.context.controller;


import com.match.common.utils.JsonUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/webSocket/{token}")
@Component
public class SubscribeWebsocket {

    private static int onlineCount = 0;

    private static Map<String, SubscribeWebsocket> clients = new ConcurrentHashMap<String, SubscribeWebsocket>();

    private Session session;

    private String username;

    @OnOpen
    public void onOpen(@PathParam("token") String username, Session session) throws IOException {
        this.username = username;
        this.session = session;
        addOnlineCount();
        clients.put(username, this);
        System.out.println("已连接");
    }


    @OnClose
    public void onClose() throws IOException {
        clients.remove(username);
        subOnlineCount();
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        Map<String, Object> jsonTo = JsonUtils.json2map(message);
        String mes = (String) jsonTo.get("message");
        if (!jsonTo.get("To").equals("All")) {
            sendMessageTo(mes, jsonTo.get("To").toString());
        } else {
            sendMessageAll("给所有人");
        }
    }


    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }


    public void sendMessageTo(String message, String To) throws IOException {
        for (SubscribeWebsocket item : clients.values()) {
            if (item.username.equals(To)) {
                item.session.getAsyncRemote().sendText(message);
            }
        }
    }


    public void sendMessageAll(String message) throws IOException {
        for (SubscribeWebsocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }


    public static synchronized void addOnlineCount() {
        SubscribeWebsocket.onlineCount++;
    }


    public static synchronized void subOnlineCount() {
        SubscribeWebsocket.onlineCount--;
    }

    public static synchronized Map<String, SubscribeWebsocket> getClients() {
        return clients;
    }

}
