package pavel.kalinkin.project.http;

import com.sun.net.httpserver.HttpServer;
import com.google.gson.Gson;
import pavel.kalinkin.project.http.handler.*;
import pavel.kalinkin.project.interfaces.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private final HttpServer server;

    public HttpTaskServer(TaskManager manager) throws IOException {
        Gson gson = new Gson();
        server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/tasks", new TaskHandler(manager, gson));
        server.createContext("/subtasks", new SubtaskHandler(manager, gson));
        server.createContext("/epics", new EpicHandler(manager, gson));
        server.createContext("/history", new HistoryHandler(manager, gson));
        server.createContext("/prioritized", new PrioritizedHandler(manager, gson));
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

    public static Gson getGson() {
        return new Gson();
    }
}

