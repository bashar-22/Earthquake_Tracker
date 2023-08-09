package earthServlets;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ServerEndpoint("/MessageSocket1")

public class MessageSocket {

    static List <Session> socketSessions = Collections.synchronizedList(new ArrayList<Session>());

    @OnOpen
    public void open (Session session) throws IOException {
        session.getBasicRemote().sendText("This textbox shows the dangerous earthquake updates!!\n");
        socketSessions.add(session);
    }

    @OnClose
    public void close (Session session){
        socketSessions.remove(session);
    }

    public static void broadcast (String message) throws IOException {
        for (Session session: socketSessions){
            session.getBasicRemote().sendText(message);
        }
    }

}
