package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.manager.InMemoryHistoryManager;
import pavel.kalinkin.project.interfaces.HistoryManager;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    HistoryManager manager;
    Task task;
    Epic epic;
    SubTask subtask;


    @BeforeEach
    void init() {
        manager = new InMemoryHistoryManager();
        task = new Task(
                1,
                "Задача 1",
                "Описание 1",
                TaskStatus.IN_PROGRESS,
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 1, 1, 0)
        );
        epic = new Epic(2, "Эпик 1", "Описание эпика 1");
        subtask = new SubTask(
                3,
                "Подзадача 1",
                "Описание подзадачи 1",
                TaskStatus.IN_PROGRESS,
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 2, 1, 0),
                epic.getId()
        );
    }

    @Test
    void addTest() {
        manager.add(task);

        List<Task> history = manager.getHistory();
        assertTrue(history.contains(task), "Задача должна быть в истории.");
    }

    @Test
    void removeTaskByIdTest() {
        manager.add(task);
        manager.add(task);

        manager.remove(task.getId());

        List<Task> history = manager.getHistory();

        assertFalse(history.contains(task), "Задача должна быть удалена из истории.");

        manager.add(task);

        history = manager.getHistory();
        assertEquals(1, history.size(), "Задача дублируется в истории.");
    }


}
