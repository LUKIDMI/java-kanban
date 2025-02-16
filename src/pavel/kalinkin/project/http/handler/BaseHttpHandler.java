package pavel.kalinkin.project.http.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {

    public void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    public void sendNotFound(HttpExchange h) throws IOException {
        h.sendResponseHeaders(404, -1);
        h.close();
    }

    public void sendHasInteractions(HttpExchange h) throws IOException {
        h.sendResponseHeaders(406, -1);
        h.close();
    }
}

