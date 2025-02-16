
package pavel.kalinkin.project.http.handler;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.interfaces.TaskManager;

import java.io.IOException;
import java.io.InputStreamReader;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {

    private final TaskManager manager;
    private final Gson gson;

    public SubtaskHandler(TaskManager manager, Gson gson) {
        this.manager = manager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange h) throws IOException {
        String path = h.getRequestURI().getPath();
        String method = h.getRequestMethod();

        switch (method) {
            case "GET":
                if (path.startsWith("/subtasks/")) {
                    String[] splitPath = path.split("/");
                    int subTaskId = Integer.parseInt(splitPath[2]);
                    SubTask subTask = manager.getSubTaskById(subTaskId);
                    if (subTask != null) {
                        sendText(h, gson.toJson(subTask));
                    } else {
                        sendNotFound(h);
                    }
                } else {
                    sendNotFound(h);
                }
                break;
            case "POST":
                if (path.equals("/subtasks")) {
                    SubTask subTask = gson.fromJson(new InputStreamReader(h.getRequestBody()), SubTask.class);
                    if (manager.getSubTaskById(subTask.getId()) != null) {
                        manager.updateTask(subTask);
                        sendHasInteractions(h); // Если подзадача с таким id уже существует
                    } else {
                        manager.addTask(subTask);
                        sendText(h, gson.toJson(subTask));
                    }
                } else {
                    sendNotFound(h);
                }
                break;
            case "DELETE":
                if (path.startsWith("/subtasks/")) {
                    String[] splitPath = path.split("/");
                    int subTaskId = Integer.parseInt(splitPath[2]);
                    manager.deleteSubTaskById(subTaskId);
                    sendText(h, "Subtask deleted");
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

