package pavel.kalinkin.project.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.manager.InMemoryTaskManager;
import pavel.kalinkin.project.manager.TaskManager;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;


import java.util.List;


class InMemoryHistoryManagerTest {

    TaskManager manager;
    Task task1;
    Task task2;


    @BeforeEach
    void setData() {
        manager = new InMemoryTaskManager();
        task1 = new Task("Test", "Test", TaskStatus.NEW);
        task2 = new Task("Test", "Test", TaskStatus.NEW);
        manager.addTask(task1);
        manager.addTask(task2);
        manager.getTaskById(task1.getId());
        manager.getTaskById(task2.getId());
    }

    @Test
    void addTask() {
        final String taskName = task1.getTaskName();
        final String taskDescription = task1.getTaskName();
        final List<Task> history = manager.getHistory();
        Assertions.assertNotNull(history, "История не пустая.");
        Assertions.assertEquals(2, history.size(), "История не пустая.");

        Task inHistoryTask = history.getFirst();
        Assertions.assertEquals(task1, inHistoryTask, "Задачи не совпадают.");
        Assertions.assertEquals(taskName, inHistoryTask.getTaskName(), "Названия не совпадают");
        Assertions.assertEquals(taskDescription, inHistoryTask.getDescription(), "Описание не совпадает");
    }

    @Test
    void shouldReturnTrueHistoryContainsNewTask() {
        List<Task> tasks = manager.getHistory();
        Assertions.assertTrue(tasks.contains(task1));
    }

    @Test
    void shouldReturnTrueDeleteTask() {
        List<Task> tasks = manager.getHistory();
        Assertions.assertTrue(tasks.contains(task2));
        manager.deleteTaskById(task2.getId());
        tasks = manager.getHistory();
        Assertions.assertFalse(tasks.contains(task2));
    }
}