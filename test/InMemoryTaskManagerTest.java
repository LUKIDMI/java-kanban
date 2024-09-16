package pavel.kalinkin.project.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.manager.*;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

import java.util.ArrayList;
import java.util.List;

class InMemoryTaskManagerTest {

    TaskManager taskManager;
    Task task1;
    Task task2;
    Epic epic1;
    Epic epic2;
    SubTask subTask1;
    SubTask subTask2;


    @BeforeEach
    public void resetManager() {
        taskManager = Managers.getDefault();
        taskManager = Managers.getDefault();
        task1 = new Task("Test", "Test");
        task2 = new Task("Test", "Test");
        epic1 = new Epic("Test", "Test");
        epic2 = new Epic("Test", "Test");
        subTask1 = new SubTask("Test", "Test", 1);
        subTask2 = new SubTask("Test", "Test", 1);
    }

    @Test
    void addTask() {
        final int taskId = taskManager.addTask(task1);
        Task savedTask = taskManager.getTaskById(taskId);

        Assertions.assertNotNull(savedTask, "Задача не найдена.");
        Assertions.assertEquals(task1, savedTask, "Задачи не совпадают");

        final List<Task> tasks = taskManager.getAllTasks();

        Assertions.assertNotNull(tasks, "Задачи не возвращаются.");
        Assertions.assertEquals(1, tasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(task1, tasks.getFirst(), "Задачи не совпадают.");

    }

    @Test
    void getAllTasks() {
        final List<Task> testTasks = new ArrayList<>();
        testTasks.add(task1);
        testTasks.add(task2);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        final List<Task> tasks = taskManager.getAllTasks();

        Assertions.assertEquals(tasks, testTasks, "Задачи не совпадают");
    }

    @Test
    void deleteAllTasks() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        boolean isEmpty = taskManager.getAllTasks().isEmpty();

        Assertions.assertFalse(isEmpty, "Список задач пуст");

        taskManager.deleteAllTasks();
        isEmpty = taskManager.getAllTasks().isEmpty();

        Assertions.assertTrue(isEmpty, "Список задач не пустой.");

    }

    @Test
    void getTaskById() {
        final int taskId = taskManager.addTask(task1);
        Task savedTask = taskManager.getTaskById(taskId);

        Assertions.assertNotNull(savedTask, "Задача не найдена");
        Assertions.assertEquals(task1, savedTask, "Задачи не совпадают");
    }

    @Test
    void updateTask() {
        final int taskId = taskManager.addTask(task1);

        Task tmpTask = new Task("Название", "Описание", TaskStatus.IN_PROGRESS, taskId);
        taskManager.updateTask(tmpTask);
        Task updTask = taskManager.getTaskById(taskId);

        final String taskName = task1.getTaskName();
        final String updTaskName = updTask.getTaskName();

        Assertions.assertNotEquals(taskName, updTaskName, "Навазние задачи не обновилась.");

        final String taskDescription = task1.getDescription();
        final String updTaskDescription = updTask.getDescription();

        Assertions.assertNotEquals(taskDescription, updTaskDescription, "Описание не обновилось");

        final TaskStatus status = task1.getStatus();
        final TaskStatus updStatus = updTask.getStatus();

        Assertions.assertNotEquals(status, updStatus, "Статус не обновился");

        Assertions.assertEquals(taskId, updTask.getId(), "Задачи не совпадают.");
    }

    @Test
    void deleteTaskById() {
        final int taskId = taskManager.addTask(task1);
        boolean isInTaskList = taskManager.getAllTasks().contains(task1);

        Assertions.assertTrue(isInTaskList, "Задачи нет в списке задач");

        taskManager.deleteTaskById(taskId);
        isInTaskList = taskManager.getAllTasks().contains(task1);

        Assertions.assertEquals(0, taskManager.getAllTasks().size(), "Список задач не упустой");
        Assertions.assertFalse(isInTaskList, "Задача не удалилась.");
    }

    @Test
    void getAllEpics() {
        final List<Epic> epicList = new ArrayList<>();
        epicList.add(epic1);
        epicList.add(epic2);

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        final List<Epic> epics = taskManager.getAllEpics();

        Assertions.assertEquals(epics, epicList, "Список задач возвращается неверно.");
    }

    @Test
    void deleteAllEpics() {
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1);
        boolean isEmpty = taskManager.getAllEpics().isEmpty();
        boolean isSubTaskIdInEpicList = epic1.getEpicSubTasks().contains(subTask1.getId());
        boolean isSubTaskInSubTasks = taskManager.getAllSubTasks().contains(subTask1.getId());
        boolean isSubTaskExist = isSubTaskInSubTasks && isSubTaskIdInEpicList;

        Assertions.assertFalse(isEmpty, "Список задач не пуст.");
        Assertions.assertTrue(isSubTaskIdInEpicList, "Подзадачи нет в списке ID подзадач эпика.");

        taskManager.deleteAllEpics();
        isEmpty = taskManager.getAllEpics().isEmpty();

        Assertions.assertTrue(isEmpty, "Список задач не очистился");
        Assertions.assertFalse(isSubTaskExist, "После удаления всех эпиков подзадача не удалилась.");

    }

    @Test
    void getEpicById() {
        final int epicId = taskManager.addEpic(epic1);
        Epic savedEpic = taskManager.getEpicById(epicId);

        Assertions.assertNotNull(savedEpic, "Эпик не добавился");
        Assertions.assertEquals(epic1, savedEpic, "Эпики не совпадают.");
    }

    @Test
    void addEpic() {
        final int epicId = taskManager.addEpic(epic1);

        Epic savedEpic = taskManager.getEpicById(epicId);

        Assertions.assertNotNull(savedEpic, "Задача не найдена.");
        Assertions.assertEquals(epic1, savedEpic, "Задачи не совпадают");

        final List<Epic> epics = taskManager.getAllEpics();

        Assertions.assertNotNull(epics, "Задачи не возвращаются.");
        Assertions.assertEquals(1, epics.size(), "Неверное количество задач.");
        Assertions.assertEquals(epic1, epics.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void deleteEpicById() {
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1);
        boolean isInEpicList = taskManager.getAllEpics().contains(epic1);
        boolean isInSubTaskList = taskManager.getAllSubTasks().contains(subTask1);

        Assertions.assertTrue(isInEpicList, "Эпика нет в списке эпиков");
        Assertions.assertTrue(isInSubTaskList, "Подзадачи нет в списке подзадач.");

        taskManager.deleteEpicById(epic1.getId());
        isInEpicList = taskManager.getAllEpics().contains(epic1);
        isInSubTaskList = taskManager.getAllSubTasks().contains(subTask1);

        Assertions.assertFalse(isInEpicList, "Эпики не удалились.");
        Assertions.assertFalse(isInSubTaskList, "Подзадача не удалились из списка подзадач.");
    }

    @Test
    void getAllEpicSubTasks() {
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);

        final List<Integer> subTasksId = new ArrayList<>();
        subTasksId.add(subTask1.getId());
        subTasksId.add(subTask2.getId());
        final List<Integer> epicSubTasksId = epic1.getEpicSubTasks();

        Assertions.assertEquals(subTasksId, epicSubTasksId, "Список подзадач вернулся неверно.");
    }

    @Test
    void getAllSubTasks() {
        final List<SubTask> testTasks = new ArrayList<>();
        taskManager.addEpic(epic1);
        testTasks.add(subTask1);
        testTasks.add(subTask2);

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);

        final List<SubTask> subTasks = taskManager.getAllSubTasks();

        Assertions.assertEquals(subTasks, testTasks, "Список подзадач вернулся неверно.");
    }

    @Test
    void deleteAllSubTasks() {
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1);


        boolean isSubTaskInEpicList = epic1.getEpicSubTasks().contains(subTask1.getId());
        boolean isInSubTaskList = taskManager.getAllSubTasks().contains(subTask1.getId());

        Assertions.assertTrue(isSubTaskInEpicList, "Подзадач нет в списке эпика.");
        Assertions.assertFalse(isInSubTaskList, "Подзадачи нет в списке подзадач.");

        Assertions.assertNotNull(taskManager.getAllTasks());

        taskManager.deleteAllSubTasks();
        isSubTaskInEpicList = epic1.getEpicSubTasks().contains(subTask1.getId());
        isInSubTaskList = taskManager.getAllSubTasks().contains(subTask1.getId());

        Assertions.assertFalse(isSubTaskInEpicList, "Подзадача осталась в списке подзадач эпика.");
        Assertions.assertFalse(isInSubTaskList, "Подзадача осталась в списке подзадач");


    }

    @Test
    void getSubTaskById() {
        taskManager.addEpic(epic1);
        final int subTaskId = taskManager.addSubTask(subTask1);
        Task savedSubTask = taskManager.getSubTaskById(subTaskId);

        Assertions.assertNotNull(savedSubTask, "Такой подзадачи нет");
        Assertions.assertEquals(subTask1, savedSubTask, "Подзадача добавилась некорректно.");
    }

    @Test
    void addSubTask() {
        taskManager.addEpic(epic1);
        final int subTaskId = taskManager.addSubTask(subTask1);
        SubTask savedSubTask = taskManager.getSubTaskById(subTaskId);

        Assertions.assertNotNull(savedSubTask, "Задача не найдена.");
        Assertions.assertEquals(subTask1, savedSubTask, "Задачи не совпадают");
    }

    @Test
    void updateSubTask() {
        taskManager.addEpic(epic1);
        final int subTaskId = taskManager.addSubTask(subTask1);

        SubTask tmpSubTask = new SubTask("Название", "Описание", TaskStatus.IN_PROGRESS, epic1.getId(), subTaskId);
        taskManager.updateSubTask(tmpSubTask);
        SubTask updSubTask = taskManager.getSubTaskById(subTaskId);

        final String taskName = subTask1.getTaskName();
        final String updTaskSubName = updSubTask.getTaskName();

        Assertions.assertNotEquals(taskName, updTaskSubName, "Название не обновилось");

        final String subTaskDescription = subTask1.getDescription();
        final String updSubTaskDescription = updSubTask.getDescription();

        Assertions.assertNotEquals(subTaskDescription, updSubTaskDescription, "Описание не обновилось.");

        final TaskStatus status = subTask1.getStatus();
        final TaskStatus updStatus = updSubTask.getStatus();

        Assertions.assertNotEquals(status, updStatus, "Статус не обновился.");

        Assertions.assertEquals(subTaskId, updSubTask.getId(), "Id подзадач не совпадает.");
    }

    @Test
    void deleteSubTaskById() {
        taskManager.addEpic(epic1);
        final int subTaskId = taskManager.addSubTask(subTask1);

        boolean isInSubtaskInList = taskManager.getAllSubTasks().contains(subTask1);
        boolean isInEpicList = epic1.getEpicSubTasks().contains(subTaskId);


        Assertions.assertTrue(isInSubtaskInList, "Подзадачи нет в списке подзадач");
        Assertions.assertTrue(isInEpicList, "Подзадачи нет в спике эпика.");

        taskManager.deleteSubTaskById(subTaskId);
        isInSubtaskInList = taskManager.getAllSubTasks().contains(subTask1);
        isInEpicList = epic1.getEpicSubTasks().contains(subTaskId);

        Assertions.assertFalse(isInSubtaskInList, "Подзадача осталась в спике подзадач.");
        Assertions.assertFalse(isInEpicList, "Подзадача осталась в списке эпика.");
    }

    @Test
    public void shouldNotConflictTaskWithSetIdAndGeneratedId() {
        Task task1 = new Task("Test1", "Test1", TaskStatus.NEW);
        Task task2 = new Task("Test2", "Test2", TaskStatus.NEW, 1);

        final Integer task1Id = taskManager.addTask(task1);
        final Integer task2Id = taskManager.addTask(task2);

        Assertions.assertEquals(taskManager.getTaskById(task1Id), task1);
        Assertions.assertNull(task2Id);
    }
}