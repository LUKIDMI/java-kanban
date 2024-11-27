package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.manager.InMemoryHistoryManager;
import pavel.kalinkin.project.manager.HistoryManager;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private HistoryManager historyManager;
    private Task task;

    @BeforeEach
    void init() {
        historyManager = new InMemoryHistoryManager();

        task = new Task("Задача 1", "Описание задачи", TaskStatus.NEW);
        task.setId(1);
    }

    @Test
    void testAddTaskToHistory() {
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertTrue(history.contains(task), "Задача должна быть в истории.");
    }

    @Test
    void testRemoveTaskFromHistory() {
        historyManager.add(task);

        historyManager.remove(task.getId());

        List<Task> history = historyManager.getHistory();
        assertFalse(history.contains(task), "Задача должна быть удалена из истории.");
    }
}
