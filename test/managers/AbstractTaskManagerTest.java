package managers;

import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.interfaces.TaskManager;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractTaskManagerTest<T extends TaskManager> {
    T manager;
    Task task1;
    Task task2;
    Task updatedTask1;
    Epic updatedEpic;
    SubTask updatedSubTask;
    Epic epic1;
    Epic epic2;
    SubTask subtask1;
    SubTask subtask2;
    SubTask subtask3;
    Task savedTask;
    Epic savedEpic;
    SubTask savedSubtask;
    int task1Id;
    int epic1Id;
    int subtask1Id;

    @Test
    void getAllTasksTest() {
        final List<Task> tasksFromManager = manager.getAllTasks();
        final List<Task> tempTasks = List.of(task1, task2);

        assertNotNull(tasksFromManager, "Метод getAllTasks не возвращает список задач.");
        assertEquals(tasksFromManager, tempTasks, "Списки задач не совпадают.");
        assertEquals(tasksFromManager.getFirst(), tempTasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void deleteAllTasksTest() {
        manager.deleteAllTasks();

        assertEquals(0, manager.getAllTasks().size(), "Метод deleteAllTasks не удалил задачи.");
    }

    @Test
    void getTaskByIdTest() {
        final Task taskFromManager = manager.getTaskById(task1.getId());

        assertEquals(task1, taskFromManager, "Метод getTaskById некорректно возвращает задачу.");
    }

    @Test
    void addTaskTest() {
        assertNotNull(savedTask, "Задача не сохранилась.");
        assertEquals(task1, savedTask, "Некорректное добавлени задачи в менеджер.");
    }

    @Test
    void updateTaskTest() {
        manager.updateTask(updatedTask1);

        assertEquals(updatedTask1, task1, "Задача 1 не обновилась");
    }

    @Test
    void deleteTaskByIdTest() {
        manager.deleteTaskById(task1.getId());

        assertThrows(IllegalArgumentException.class, () -> manager.deleteTaskById(task1Id), "Метод deleteTaskById не удалил задачу.");
    }

    @Test
    void getAllEpicsTest() {
        final List<Epic> epicsFromManager = manager.getAllEpics();
        final List<Epic> tempEpics = List.of(epic1, epic2);

        assertNotNull(epicsFromManager, "Метод getAllEpics не возвращает список эпиков.");
        assertEquals(epicsFromManager, tempEpics, "Списки эпиков не совпадают.");
        assertEquals(epicsFromManager.getFirst(), tempEpics.getFirst(), "Эпики не совпадают.");
    }

    @Test
    void deleteAllEpicsTest() {
        manager.deleteAllEpics();

        assertEquals(0, manager.getAllEpics().size(), "Метод deleteAllEpics не удалил эпики.");
    }

    @Test
    void getEpicByIdTest() {
        final Epic epicFromManager = manager.getEpicById(epic1Id);

        assertEquals(epic1, epicFromManager, "Метод getEpicById неправильно возвращает эпик");
    }

    @Test
    void addEpicTest() {
        assertNotNull(savedEpic, "Эпик не сохранился.");
        assertEquals(epic1, savedEpic, "Некорректное добавлени эпика в менеджер.");
    }

    @Test
    void updateEpicTest() {
        manager.updateEpic(updatedEpic);

        assertEquals(updatedEpic, epic1, "Эпик не обновился.");
    }

    @Test
    void deleteEpicByIdTest() {
        manager.deleteEpicById(epic1.getId());

        assertThrows(IllegalArgumentException.class, () -> manager.deleteEpicById(epic1Id));
    }

    @Test
    void getAllEpicSubTasksTest() {
        final List<SubTask> epicSubTasks = manager.getAllEpicSubTasks(epic1Id);
        final List<SubTask> tempSubTasks = List.of(subtask1, subtask2, subtask3);

        assertEquals(tempSubTasks, epicSubTasks, "Списки подзадач эпика не совпадают.");
        assertEquals(tempSubTasks.getFirst(), epicSubTasks.getFirst(), "Подзадача 1 эпика не совпадает.");
        assertEquals(tempSubTasks.get(1), epicSubTasks.get(1), "Подзадача 2 эпика не совпадает.");
        assertEquals(tempSubTasks.get(2), epicSubTasks.get(2), "Подзадача 3 эпика не совпадает.");
    }

    @Test
    void getAllSubTasksTest() {
        final List<SubTask> subTasksFromManager = manager.getAllSubTasks();
        final List<Task> tempSubTasks = List.of(subtask1, subtask2, subtask3);

        assertNotNull(subTasksFromManager, "Метод getAllSubTasks не возвращает список подзадач.");
        assertEquals(subTasksFromManager, tempSubTasks, "Списки подзадач не совпадают.");
        assertEquals(subTasksFromManager.getFirst(), tempSubTasks.getFirst(), "Подзадачи 1 не совпадают.");
        assertEquals(subTasksFromManager.get(1), tempSubTasks.get(1), "Подзадачи 2 не совпадает.");
        assertEquals(subTasksFromManager.get(2), tempSubTasks.get(2), "Подзадачи 3 не совпадают.");

    }

    @Test
    void deleteAllSubTasksTest() {
        int subTasksCount = manager.getAllSubTasks().size();

        assertEquals(3, subTasksCount, "Количество подзадач неверное.");

        manager.deleteAllSubTasks();

        assertEquals(0, manager.getAllSubTasks().size(), "Подзадачи не удалились.");
    }

    @Test
    void getSubTaskByIdTest() {
        final SubTask subTaskFromManager = manager.getSubTaskById(subtask1Id);

        assertEquals(subtask1, subTaskFromManager, "Метод getSubTaskById неправильно возвращает подзадачу");
    }

    @Test
    void addSubTaskTest() {
        assertNotNull(savedSubtask, "Подзадача не сохранилась.");
        assertEquals(subtask1, savedSubtask, "Некорректное добавлени подзадачи в менеджер.");
    }

    @Test
    void updateSubTaskTest() {
        manager.updateSubTask(updatedSubTask);

        assertEquals(updatedSubTask, subtask1, "Подзадача не обновилась.");
    }

    @Test
    void deleteSubTaskByIdTest() {
        manager.deleteSubTaskById(subtask1Id);

        assertThrows(IllegalArgumentException.class, () -> manager.deleteSubTaskById(subtask1Id), "Подзадача не удалена.");
    }

    @Test
    void getHistoryTest() {
        manager.getTaskById(task1Id);
        manager.getSubTaskById(subtask1Id);
        manager.getEpicById(epic1Id);

        List<Task> tempHistoryList = new ArrayList<>(List.of(task1, subtask1, epic1));
        List<Task> historyList = manager.getHistory();

        assertEquals(tempHistoryList, historyList, "История задач возвращается неверно.");

        manager.deleteTaskById(task1Id);
        historyList = manager.getHistory();
        tempHistoryList.remove(task1);

        assertEquals(tempHistoryList, historyList, "После удаления задачи, история отображает некорректно.");
    }

    @Test
    void getPrioritizedTasks() {
        Set<Task> prioritizedTasks = manager.getPrioritizedTasks();

        Set<Task> tempPrioritizedTasks = new HashSet<>(Set.of(task1, task2, subtask1, subtask2, subtask3));

        assertEquals(tempPrioritizedTasks, prioritizedTasks, "Задачи неправильно записываются в список приоритетов.");

        manager.deleteTaskById(task1Id);

        prioritizedTasks = manager.getPrioritizedTasks();
        tempPrioritizedTasks.remove(task1);

        assertEquals(tempPrioritizedTasks, prioritizedTasks, "После удаления задачи, она не пропала из списка приоритетов.");

        manager.deleteSubTaskById(subtask1Id);

        prioritizedTasks = manager.getPrioritizedTasks();
        tempPrioritizedTasks.remove(subtask1);

        assertEquals(tempPrioritizedTasks, prioritizedTasks, "После удаления подзадачи, она не пропала из списка приоритетов.");
    }

    @Test
    void updateEpicStatusNewTest() {
        assertEquals(TaskStatus.NEW, epic1.getStatus(), "Статус эпика должен быть NEW");
    }

    @Test
    void updateEpicStatusDoneTest() {

        manager.updateSubTask(new SubTask(
                5,
                "Подзадача 1",
                "Описание подзадачи 1",
                TaskStatus.DONE,
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 5, 1, 0),
                epic1.getId()
        ));

        manager.updateSubTask(new SubTask(
                6,
                "Подзадача 2",
                "Описание подзадачи 2",
                TaskStatus.DONE,
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 6, 1, 0),
                epic1.getId()
        ));

        manager.updateSubTask(new SubTask(
                7,
                "Подзадача 3",
                "Описание подзадачи 3",
                TaskStatus.DONE,
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 7, 1, 0),
                epic1.getId()
        ));

        assertEquals(TaskStatus.DONE, epic1.getStatus(), "Статус эпика должен быть DONE.");
    }

    @Test
    void updateEpicStatusNewAndDoneTest() {

        manager.updateSubTask(new SubTask(
                5,
                "Подзадача 1",
                "Описание подзадачи 1",
                TaskStatus.NEW,
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 5, 1, 0),
                epic1.getId()
        ));

        manager.updateSubTask(new SubTask(
                6,
                "Подзадача 2",
                "Описание подзадачи 2",
                TaskStatus.DONE,
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 6, 1, 0),
                epic1.getId()
        ));

        manager.updateSubTask(new SubTask(
                7,
                "Подзадача 3",
                "Описание подзадачи 3",
                TaskStatus.DONE,
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 7, 1, 0),
                epic1.getId()
        ));

        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatus(), "Статус эпика должен быть IN_PROGRESS.");
    }

    @Test
    void updateEpicStatusInProgressTest() {

        manager.updateSubTask(new SubTask(
                5,
                "Подзадача 1",
                "Описание подзадачи 1",
                TaskStatus.IN_PROGRESS,
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 5, 1, 0),
                epic1.getId()
        ));

        manager.updateSubTask(new SubTask(
                6,
                "Подзадача 2",
                "Описание подзадачи 2",
                TaskStatus.IN_PROGRESS,
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 6, 1, 0),
                epic1.getId()
        ));

        manager.updateSubTask(new SubTask(
                7,
                "Подзадача 3",
                "Описание подзадачи 3",
                TaskStatus.IN_PROGRESS,
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 7, 1, 0),
                epic1.getId()
        ));

        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatus(), "Статус эпика должен быть IN_PROGRESS");
    }

    @Test
    void isEpicSubTaskExistsTest() {
        Epic epic = manager.getEpicById(subtask1.getEpicId());

        assertEquals(epic1, epic, "Эпика подзадачи нет в списке эпика.");
    }

    @Test
    void overlappedPrioritizedTest() {
        Task tempTask = new Task(
                "Обновленная задача 1",
                "Описание 1",
                Duration.ofHours(1),
                LocalDateTime.of(2024, 1, 1, 0, 0));

        assertThrows(IllegalArgumentException.class, () -> manager.addTask(tempTask), "Задачи не пересекаются.");
    }

    @Test
    void epicStartTimeAndEndTimeTest() {
        assertEquals(epic1.getStartTime(), subtask1.getStartTime(),
                "Время начала эпика не равно времени начала самой ранней задачи");
        assertEquals(epic1.getEndTime(), subtask3.getEndTime(),
                "Время завершения эпика не равно времени завершения самой поздней задачи");
    }

}
