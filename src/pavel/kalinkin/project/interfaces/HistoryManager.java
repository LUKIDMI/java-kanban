package pavel.kalinkin.project.interfaces;

import pavel.kalinkin.project.model.Task;

import java.util.List;


public interface HistoryManager {

    void add(Task task);

    void remove(int id);

    List<Task> getHistory();
}
