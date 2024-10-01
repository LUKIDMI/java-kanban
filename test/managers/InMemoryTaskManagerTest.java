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

    TaskManager taskManager = Managers.getDefault();
    Task task1 = new Task("Test", "Test");
    Task task2 = new Task("Test", "Test");
    Epic epic1 = new Epic("Test", "Test");
    Epic epic2 = new Epic("Test", "Test");
    SubTask subTask1 = new SubTask("Test", "Test", 3);
    SubTask subTask2 = new SubTask("Test", "Test", 3);


    @BeforeEach
    public void resetManager() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
    }

    @Test
    void addTask() {

        Task savedTask = taskManager.getTaskById(task1.getId());

        Assertions.assertNotNull(savedTask, "Задача не найдена.");
        Assertions.assertEquals(task1, savedTask, "Задачи не совпадают");

        final List<Task> tasks = taskManager.getAllTasks();

        Assertions.assertNotNull(tasks, "Задачи не возвращаются.");
        Assertions.assertEquals(2, tasks.size(), "Неверное количество задач.");
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
        Task savedTask = taskManager.getTaskById(task1.getId());

        Assertions.assertNotNull(savedTask, "Задача не найдена");
        Assertions.assertEquals(task1, savedTask, "Задачи не совпадают");
    }

    @Test
    void updateTask() {

        Task tmpTask = new Task("Название", "Описание", TaskStatus.IN_PROGRESS, task1.getId());
        taskManager.updateTask(tmpTask);
        Task updTask = taskManager.getTaskById(task1.getId());

        final String taskName = task1.getTaskName();
        final String updTaskName = updTask.getTaskName();

        Assertions.assertNotEquals(taskName, updTaskName, "Навазние задачи не обновилась.");

        final String taskDescription = task1.getDescription();
        final String updTaskDescription = updTask.getDescription();

        Assertions.assertNotEquals(taskDescription, updTaskDescription, "Описание не обновилось");

        final TaskStatus status = task1.getStatus();
        final TaskStatus updStatus = updTask.getStatus();

        Assertions.assertNotEquals(status, updStatus, "Статус не обновился");

        Assertions.assertEquals(task1.getId(), updTask.getId(), "Задачи не совпадают.");
    }

    @Test
    void deleteTaskById() {
        boolean isInTaskList = taskManager.getAllTasks().contains(task1);

        Assertions.assertTrue(isInTaskList, "Задачи нет в списке задач");

        taskManager.deleteTaskById(task1.getId());
        isInTaskList = taskManager.getAllTasks().contains(task1);

        Assertions.assertEquals(1, taskManager.getAllTasks().size(), "Список задач не упустой");
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
        Epic savedEpic = taskManager.getEpicById(epic1.getId());

        Assertions.assertNotNull(savedEpic, "Эпик не добавился");
        Assertions.assertEquals(epic1, savedEpic, "Эпики не совпадают.");
    }

    @Test
    void addEpic() {
        Epic savedEpic = taskManager.getEpicById(epic1.getId());

        Assertions.assertNotNull(savedEpic, "Задача не найдена.");
        Assertions.assertEquals(epic1, savedEpic, "Задачи не совпадают");

        final List<Epic> epics = taskManager.getAllEpics();

        Assertions.assertNotNull(epics, "Задачи не возвращаются.");
        Assertions.assertEquals(2, epics.size(), "Неверное количество задач.");
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

        boolean isSubTaskInEpicList = epic1.getEpicSubTasks().contains(subTask1.getId());
        boolean isInSubTaskList = taskManager.getAllSubTasks().contains(subTask1);

        Assertions.assertTrue(isSubTaskInEpicList, "Подзадач нет в списке эпика.");
        Assertions.assertTrue(isInSubTaskList, "Подзадачи нет в списке подзадач.");

        Assertions.assertNotNull(taskManager.getAllTasks());

        taskManager.deleteAllSubTasks();
        isSubTaskInEpicList = epic1.getEpicSubTasks().contains(subTask1.getId());
        isInSubTaskList = taskManager.getAllSubTasks().contains(subTask1.getId());

        Assertions.assertFalse(isSubTaskInEpicList, "Подзадача осталась в списке подзадач эпика.");
        Assertions.assertFalse(isInSubTaskList, "Подзадача осталась в списке подзадач");


    }

    @Test
    void getSubTaskById() {
        Task savedSubTask = taskManager.getSubTaskById(subTask1.getId());

        Assertions.assertNotNull(savedSubTask, "Такой подзадачи нет");
        Assertions.assertEquals(subTask1, savedSubTask, "Подзадача добавилась некорректно.");
    }

    @Test
    void addSubTask() {
        SubTask savedSubTask = taskManager.getSubTaskById(subTask1.getId());

        Assertions.assertNotNull(savedSubTask, "Задача не найдена.");
        Assertions.assertEquals(subTask1, savedSubTask, "Задачи не совпадают");
    }

    @Test
    void updateSubTask() {
        SubTask tmpSubTask = new SubTask("Название", "Описание", TaskStatus.IN_PROGRESS, epic1.getId(), subTask1.getId());
        taskManager.updateSubTask(tmpSubTask);
        SubTask updSubTask = taskManager.getSubTaskById(subTask1.getId());

        final String taskName = subTask1.getTaskName();
        final String updTaskSubName = updSubTask.getTaskName();

        Assertions.assertNotEquals(taskName, updTaskSubName, "Название не обновилось");

        final String subTaskDescription = subTask1.getDescription();
        final String updSubTaskDescription = updSubTask.getDescription();

        Assertions.assertNotEquals(subTaskDescription, updSubTaskDescription, "Описание не обновилось.");

        final TaskStatus status = subTask1.getStatus();
        final TaskStatus updStatus = updSubTask.getStatus();

        Assertions.assertNotEquals(status, updStatus, "Статус не обновился.");

        Assertions.assertEquals(subTask1.getId(), updSubTask.getId(), "Id подзадач не совпадает.");
    }

    @Test
    void deleteSubTaskById() {
        boolean isInSubtaskInList = taskManager.getAllSubTasks().contains(subTask1);
        boolean isInEpicList = epic1.getEpicSubTasks().contains(subTask1.getId());

        Assertions.assertTrue(isInSubtaskInList, "Подзадачи нет в списке подзадач");
        Assertions.assertTrue(isInEpicList, "Подзадачи нет в спике эпика.");

        taskManager.deleteSubTaskById(subTask1.getId());
        isInSubtaskInList = taskManager.getAllSubTasks().contains(subTask1);
        isInEpicList = epic1.getEpicSubTasks().contains(subTask1.getId());

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