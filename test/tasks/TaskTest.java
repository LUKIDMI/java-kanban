package tasks;

import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTaskCreation() {
        Task task = new Task("Задача 1", "Описание задачи", TaskStatus.NEW);

        assertEquals("Задача 1", task.getTaskName(), "Название задачи должно совпадать.");
        assertEquals("Описание задачи", task.getDescription(), "Описание задачи должно совпадать.");
        assertEquals(TaskStatus.NEW, task.getStatus(), "Статус задачи должен быть NEW.");
    }

    @Test
    void testSettersAndGetters() {
        Task task = new Task("Задача 1", "Описание задачи");
        task.setTaskName("Новое название");
        task.setDescription("Новое описание");
        task.setStatus(TaskStatus.DONE);

        assertEquals("Новое название", task.getTaskName(), "Название задачи должно измениться.");
        assertEquals("Новое описание", task.getDescription(), "Описание задачи должно измениться.");
        assertEquals(TaskStatus.DONE, task.getStatus(), "Статус задачи должен измениться.");
    }
}
