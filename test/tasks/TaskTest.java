package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task;

    @BeforeEach
    void setUp() {
        task = new Task(
                "Задача",
                "Описание задачи",
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 1, 1, 0)
        );
    }

    @Test
    void testTaskCreation() {
        assertEquals("Задача", task.getTaskName(), "Название задачи должно совпадать.");
        assertEquals("Описание задачи", task.getDescription(), "Описание задачи должно совпадать.");
        assertEquals(TaskStatus.NEW, task.getStatus(), "Статус задачи должен быть NEW.");
    }

    @Test
    void testSettersAndGetters() {
        task.setTaskName("Новое название");
        task.setDescription("Новое описание");
        task.setStatus(TaskStatus.DONE);

        assertEquals("Новое название", task.getTaskName(), "Название задачи должно измениться.");
        assertEquals("Новое описание", task.getDescription(), "Описание задачи должно измениться.");
        assertEquals(TaskStatus.DONE, task.getStatus(), "Статус задачи должен измениться.");
    }
}
