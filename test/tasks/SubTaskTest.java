package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    Epic epic;
    SubTask subTask;

    @BeforeEach
    void setUp() {
        epic = new Epic(1, "Эпик", "Описание эпика");
        subTask = new SubTask(
                2,
                "Подзадача 1",
                "Описание подзадачи",
                TaskStatus.NEW,
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 2, 1, 0),
                epic.getId());
    }

    @Test
    void testSubTaskCreation() {
        assertEquals("Подзадача 1", subTask.getTaskName(), "Название подзадачи должно совпадать.");
        assertEquals("Описание подзадачи", subTask.getDescription(), "Описание подзадачи должно совпадать.");
        assertEquals(1, subTask.getEpicId(), "ID эпика должен совпадать.");
        assertEquals(TaskStatus.NEW, subTask.getStatus(), "Статус подзадачи должен быть NEW.");
    }

    @Test
    void testSettersAndGetters() {

        subTask.setTaskName("Новое название");
        subTask.setDescription("Новое описание");
        subTask.setStatus(TaskStatus.DONE);

        assertEquals("Новое название", subTask.getTaskName(), "Название подзадачи должно измениться.");
        assertEquals("Новое описание", subTask.getDescription(), "Описание подзадачи должно измениться.");
        assertEquals(TaskStatus.DONE, subTask.getStatus(), "Статус подзадачи должен измениться.");
    }
}