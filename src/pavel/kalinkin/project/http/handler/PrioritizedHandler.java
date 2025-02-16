package pavel.kalinkin.project.http.handler;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import pavel.kalinkin.project.interfaces.TaskManager;
import pavel.kalinkin.project.model.Task;

import java.io.IOException;
import java.util.Set;

public class PrioritizedHandler extends BaseHttpHandler implements HttpHandler {

    private final TaskManager manager;
    private final Gson gson;

    public PrioritizedHandler(TaskManager manager, Gson gson) {
        this.manager = manager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange h) throws IOException {
        Set<Task> prioritizedTasks = manager.getPrioritizedTasks();
        sendText(h, gson.toJson(prioritizedTasks));
    }
}

