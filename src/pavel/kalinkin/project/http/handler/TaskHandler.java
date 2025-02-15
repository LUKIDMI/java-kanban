package pavel.kalinkin.project.http.handler;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.interfaces.TaskManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {

    private final TaskManager manager;
    private final Gson gson;

    public TaskHandler(TaskManager manager, Gson gson) {
        this.manager = manager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String method = httpExchange.getRequestMethod();

        switch (method) {
            case "GET":
                if (path.equals("/tasks")) {
                    List<Task> tasks = manager.getAllTasks();
                    sendText(httpExchange, gson.toJson(tasks));
                } else if (path.startsWith("/tasks/")) {
                    String[] splitPath = path.split("/");
                    int taskId = Integer.parseInt(splitPath[2]);
                    Task task = manager.getTaskById(taskId);
                    if (task != null) {
                        sendText(httpExchange, gson.toJson(task));
                    } else {
                        sendNotFound(httpExchange);
                    }
                } else {
                    sendNotFound(httpExchange);
                }
                break;
            case "POST":
                if (path.equals("/tasks")) {
                    Task task = gson.fromJson(new InputStreamReader(httpExchange.getRequestBody()), Task.class);
                    if (manager.getTaskById(task.getId()) != null) {
                        sendHasInteractions(httpExchange); // если задача с таким id уже существует
                    } else {
                        manager.addTask(task);
                        sendText(httpExchange, gson.toJson(task));
                    }
                } else {
                    sendNotFound(httpExchange);
                }
                break;
            case "DELETE":
                if (path.startsWith("/tasks/")) {
                    String[] splitPath = path.split("/");
                    int taskId = Integer.parseInt(splitPath[2]);
                    manager.deleteTaskById(taskId);
                    sendText(httpExchange, "Task deleted");
                } else {
                    sendNotFound(httpExchange);
                }
                break;
            default:
                sendNotFound(httpExchange);
                break;
        }
    }
}


