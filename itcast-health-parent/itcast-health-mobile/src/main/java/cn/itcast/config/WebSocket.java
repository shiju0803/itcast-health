package cn.itcast.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/pay/status/{orderId}")
@Slf4j
public class WebSocket {

    private Session session;

    private static Map<String, WebSocket> webSocketSet = new ConcurrentHashMap<String, WebSocket>();

    @OnOpen
    public void onOpen(@PathParam("orderId") String orderId, Session session) {
        this.session = session;
        webSocketSet.put(orderId, this);
        log.info("【websocket消息】有新的连接, 总数:{}", webSocketSet.size());
    }

    @OnClose
    public void onClose(@PathParam("orderId") String orderId) {
        webSocketSet.remove(orderId);
        log.info("【websocket消息】连接断开, 总数:{}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(@PathParam("orderId") String orderId, String message) {
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }

    public void sendMessage(String orderId, String message) {
        log.info("【websocket消息】广播消息, message={}", message);
        try {
            WebSocket webSocket = webSocketSet.get(orderId);
            if (webSocket != null && webSocket.session != null) {
                webSocket.session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}