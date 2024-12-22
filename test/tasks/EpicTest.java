package tasks;

import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void testEpicCreation() {
        Epic epic = new Epic("Эпик 1", "Описание эпика");

        assertEquals("Эпик 1", epic.getTaskName(), "Название эпика должно совпадать.");
        assertEquals("Описание эпика", epic.getDescription(), "Описание эпика должно совпадать.");
        assertEquals(TaskStatus.NEW, epic.getStatus(), "Статус эпика должен быть NEW.");
    }

    @Test
    void testAddAndRemoveSubTaskIds() {
        Epic epic = new Epic("Эпик 1", "Описание эпика");
        SubTask subTask1 = new SubTask("Подзадача 1", "Описание подзадачи", Duration.ofHours(1), LocalDateTime.of(2024, 5, 5, 10, 10), 1);
        subTask1.setId(1);
        SubTask subTask2 = new SubTask("Подзадача 2", "Описание подзадачи", Duration.ofHours(1), LocalDateTime.of(2024, 5, 5, 12, 10), 1);

        subTask2.setId(2);

        epic.addSubTaskId(subTask1);
        epic.addSubTaskId(subTask2);

        assertEquals(2, epic.getEpicSubTasks().size(), "В эпике должно быть 2 подзадачи.");

        epic.deleteSubTaskById(subTask1.getId());
        assertEquals(1, epic.getEpicSubTasks().size(), "В эпике должна остаться 1 подзадача.");
    }
}