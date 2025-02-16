package pavel.kalinkin.project.http.handler;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.interfaces.TaskManager;

import java.io.IOException;
import java.io.InputStreamReader;

public class EpicHandler extends BaseHttpHandler implements HttpHandler {

    private final TaskManager manager;
    private final Gson gson;

    public EpicHandler(TaskManager manager, Gson gson) {
        this.manager = manager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange h) throws IOException {
        String path = h.getRequestURI().getPath();
        String method = h.getRequestMethod();

        switch (method) {
            case "GET":
                if (path.startsWith("/epics/")) {
                    String[] splitPath = path.split("/");
                    int epicId = Integer.parseInt(splitPath[2]);
                    Epic epic = manager.getEpicById(epicId);
                    if (epic != null) {
                        sendText(h, gson.toJson(epic));
                    } else {
                        sendNotFound(h);
                    }
                } else {
                    sendNotFound(h);
                }
                break;
            case "POST":
                if (path.equals("/epics")) {
                    Epic epic = gson.fromJson(new InputStreamReader(h.getRequestBody()), Epic.class);
                    if (manager.getEpicById(epic.getId()) != null) {
                        manager.updateEpic(epic);
                        sendHasInteractions(h); // Если эпик с таким id уже существует
                    } else {
                        manager.addTask(epic);
                        sendText(h, gson.toJson(epic));
                    }
                } else {
                    sendNotFound(h);
                }
                break;
            case "DELETE":
                if (path.startsWith("/epics/")) {
                    String[] splitPath = path.split("/");
                    int epicId = Integer.parseInt(splitPath[2]);
                    manager.deleteEpicById(epicId);
                    sendText(h, "Epic deleted");
                } else {
                    sendNotFound(h);
                }
                break;
            default:
                sendNotFound(h);
                break;
        }
    }
}

