package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.manager.FileBackedTaskManager;
import pavel.kalinkin.project.manager.TaskManager;
import pavel.kalinkin.project.model.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    private TaskManager manager;
    private File file;

    @BeforeEach
    void setUp() {
        file = new File("test_data.csv");

        manager = new FileBackedTaskManager(file);
    }

    @Test
    void testSaveAndLoadTasks() {
        Task task = new Task("Задача 1", "Описание задачи");
        int taskId = manager.addTask(task);

        manager = FileBackedTaskManager.loadFromFile(file);
        Task loadedTask = manager.getTaskById(taskId);

        assertNotNull(loadedTask, "Задача должна быть загружена.");
        assertEquals(task, loadedTask, "Загруженная задача не совпадает с исходной.");
    }

}

