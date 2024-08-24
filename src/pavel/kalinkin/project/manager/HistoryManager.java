package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.model.Task;

import java.util.List;

public interface HistoryManager {
    void addTask(Task task);
    List<Task> getHistory();
}
