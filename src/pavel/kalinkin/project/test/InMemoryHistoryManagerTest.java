package pavel.kalinkin.project.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.manager.HistoryManager;
import pavel.kalinkin.project.manager.InMemoryHistoryManager;
import pavel.kalinkin.project.model.Task;

import java.util.List;


class InMemoryHistoryManagerTest {

    HistoryManager historyManager = new InMemoryHistoryManager();
    Task task = new Task("Test", "Test");

    @Test
    void addTask() {
        historyManager.addTask(task);
        final String taskName = task.getTaskName();
        final String taskDescription = task.getTaskName();
        final List<Task> history = historyManager.getHistory();
        Assertions.assertNotNull(history, "История не пустая.");
        Assertions.assertEquals(1, history.size(), "История не пустая.");

        Task inHistoryTask = history.getFirst();
        Assertions.assertEquals(task, inHistoryTask, "Задачи не совпадают.");
        Assertions.assertEquals(taskName, inHistoryTask.getTaskName(), "Названия не совпадают");
        Assertions.assertEquals(taskDescription, inHistoryTask.getDescription(), "Описание не совпадает");
    }


}