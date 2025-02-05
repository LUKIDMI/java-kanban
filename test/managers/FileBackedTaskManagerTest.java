package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.exceptions.ManagerLoadExceptions;
import pavel.kalinkin.project.manager.FileBackedTaskManager;
import pavel.kalinkin.project.model.*;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends AbstractTaskManagerTest<FileBackedTaskManager> {

    File file;
    File noFile;

    @BeforeEach
    public void setUp() {

        file = new File("C:\\Users\\LUKIDMI\\IdeaProjects\\java-kanban\\test\\data\\data.csv");
        noFile = new File("C:\\Users\\LUKIDMI\\IdeaProjects\\java-kanban\\test\\data\\corrupted_file.csv");
        manager = new FileBackedTaskManager(file);
        task1 = new Task("Задача 1", "Описание 1", Duration.ofHours(1), LocalDateTime.of(2024, 1, 1, 1, 0));
        task2 = new Task("Задача 2", "Описание 2", Duration.ofHours(1), LocalDateTime.of(2024, 1, 1, 3, 0));
        epic1 = new Epic("Эпик 1", "Описание эпика 1");
        epic2 = new Epic("Эпик 2", "Описание эпика 2");
        task1Id = manager.addTask(task1);
        manager.addTask(task2);
        epic1Id = manager.addTask(epic1);
        manager.addTask(epic2);
        subtask1 = new SubTask("Подзадача 1", "Описание подзадачи 1", Duration.ofHours(1), LocalDateTime.of(2024, 1, 2, 1, 0), epic1.getId());
        subtask2 = new SubTask("Подзадача 2", "Описание подзадачи 2", Duration.ofHours(1), LocalDateTime.of(2024, 1, 3, 1, 0), epic1.getId());
        subtask3 = new SubTask("Подзадача 3", "Описание подзадачи 3", Duration.ofHours(1), LocalDateTime.of(2024, 1, 4, 1, 0), epic1.getId());
        subtask1Id = manager.addTask(subtask1);
        manager.addTask(subtask2);
        manager.addTask(subtask3);
        savedTask = manager.getTaskById(task1.getId());
        savedEpic = manager.getEpicById(epic1.getId());
        savedSubtask = manager.getSubTaskById(subtask1.getId());
        updatedTask1 = new Task(1, "Обновленная задача 1", "Описание 1", TaskStatus.IN_PROGRESS, Duration.ofHours(1), LocalDateTime.of(2024, 1, 1, 5, 0));
        updatedEpic = new Epic(epic1Id, "Обновленный эпик 1", "Обновленное писание эпика 1");
        updatedSubTask = new SubTask(5, "Подзадача 4", "Описание подзадачи 4", TaskStatus.IN_PROGRESS, Duration.ofHours(1), LocalDateTime.of(2024, 1, 5, 1, 0), epic1.getId());
    }

    @Test
    void loadFormFileTest() {
        FileBackedTaskManager newManager = FileBackedTaskManager.loadFromFile(file);

        assertEquals(manager.getTaskById(task1Id), newManager.getTaskById(task1Id));
        assertEquals(manager.getEpicById(epic1Id), newManager.getEpicById(epic1Id));
        assertEquals(manager.getSubTaskById(subtask1Id), newManager.getSubTaskById(subtask1Id));
    }

    @Test
    void loadNoFileTest() {
        assertThrows(ManagerLoadExceptions.class,
                () -> FileBackedTaskManager.loadFromFile(noFile), "Данные не могли быть загружен.");
    }

}