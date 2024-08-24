package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.model.Task;

import java.util.ArrayList;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new ArrayList<>(10);


    @Override
    public void addTask(Task task) {
        if (history.size() < 10) {
            history.add(task);
        } else {
            history.removeFirst();
            history.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
