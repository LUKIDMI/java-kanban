package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.manager.InMemoryTaskManager;
import pavel.kalinkin.project.manager.TaskManager;
import pavel.kalinkin.project.model.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private TaskManager manager;
    private Task task;
    private Epic epic;

    @BeforeEach
    void init() {
        manager = new InMemoryTaskManager();
        task = new Task("Задача 1", "Описание задачи");
        epic = new Epic("Эпик 1", "Описание эпика");
    }

    @Test
    void testAddAndGetTask() {
        int taskId = manager.addTask(task);

        Task savedTask = manager.getTaskById(taskId);
        assertNotNull(savedTask, "Задача должна быть добавлена.");
        assertEquals(task, savedTask, "Сохранённая задача не совпадает с исходной.");
    }

    @Test
    void testDeleteTaskById() {
        int taskId = manager.addTask(task);
        assertNotNull(manager.getTaskById(taskId), "Задача должна быть добавлена.");

        manager.deleteTaskById(taskId);

        assertNull(manager.getTaskById(taskId), "Задача должна быть удалена.");
    }

    @Test
    void testAddAndUpdateEpicStatus() {
        int epicId = manager.addTask(epic);

        SubTask subTask1 = new SubTask("Подзадача 1", "Описание подзадачи", epicId);
        SubTask subTask2 = new SubTask("Подзадача 2", "Описание подзадачи", epicId);

        manager.addTask(subTask1);
        manager.addTask(subTask2);

        assertEquals(TaskStatus.NEW, manager.getEpicById(epicId).getStatus(), "Статус эпика должен быть NEW.");

        subTask1.setStatus(TaskStatus.DONE);
        manager.updateSubTask(subTask1);

        assertEquals(TaskStatus.IN_PROGRESS, manager.getEpicById(epicId).getStatus(), "Статус эпика должен быть IN_PROGRESS.");
    }

    @Test
    void testDeleteAllTasks() {
        manager.addTask(task);

        manager.deleteAllTasks();

        assertTrue(manager.getAllTasks().isEmpty(), "Все задачи должны быть удалены.");
    }
}
