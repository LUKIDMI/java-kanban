package tasks;

import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {

    @Test
    void testSubTaskCreation() {

        SubTask subTask = new SubTask("Подзадача 1", "Описание подзадачи", 1);

        assertEquals("Подзадача 1", subTask.getTaskName(), "Название подзадачи должно совпадать.");
        assertEquals("Описание подзадачи", subTask.getDescription(), "Описание подзадачи должно совпадать.");
        assertEquals(1, subTask.getEpicId(), "ID эпика должен совпадать.");
        assertEquals(TaskStatus.NEW, subTask.getStatus(), "Статус подзадачи должен быть NEW.");
    }

    @Test
    void testSettersAndGetters() {
        SubTask subTask = new SubTask("Подзадача 1", "Описание подзадачи", 1);
        subTask.setTaskName("Новое название");
        subTask.setDescription("Новое описание");
        subTask.setStatus(TaskStatus.DONE);

        assertEquals("Новое название", subTask.getTaskName(), "Название подзадачи должно измениться.");
        assertEquals("Новое описание", subTask.getDescription(), "Описание подзадачи должно измениться.");
        assertEquals(TaskStatus.DONE, subTask.getStatus(), "Статус подзадачи должен измениться.");
    }
}