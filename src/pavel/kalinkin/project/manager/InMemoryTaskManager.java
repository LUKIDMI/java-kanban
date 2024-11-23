package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

import java.util.*;


public class InMemoryTaskManager implements TaskManager {
    private final HistoryManager historyManager;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private int idCounter;

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
        this.idCounter = 0;
    }

    @Override
    public int generateId() {
        return ++idCounter;
    }

    //
    @Override
    public Integer addTask(Task task) {
        if (task == null || task.getClass() != Task.class || tasks.containsKey(task.getId())) {
            throw new IllegalArgumentException("Некорректный аргумент: task не должен быть null, " +
                    "должен быть экземпляром класса Task и не должен иметь дублирующийся ID.");
        }
        int newId = generateId();
        task.setId(newId);
        tasks.put(newId, task);
        return newId;
    }

    @Override
    public List<Task> getAllTasks() {
        return tasks.values().stream().toList();
    }

    @Override
    public void deleteAllTasks() {
        try {
            for (Task task : getAllTasks()) {
                historyManager.remove(task.getId());
            }
            tasks.clear();
        } catch (NoSuchElementException e) {
            System.out.println("Список задач пуст");
        }
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new NoSuchElementException("Задача с ID " + taskId + " не найдена.");
        }
        historyManager.add(task);
        return task;
    }

    @Override
    public void updateTask(Task task) {
        if (task == null || !tasks.containsKey(task.getId())) {
            throw new IllegalArgumentException("Задача не может быть null и должна существовать");
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void deleteTaskById(int taskId) {
        if (!tasks.containsKey(taskId)) {
            throw new NoSuchElementException("Задача с ID " + taskId + " не найдена");
        }
        tasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public List<Epic> getAllEpics() {
        return epics.values().stream().toList();
    }

    @Override
    public void deleteAllEpics() {
        for (Epic epic : getAllEpics()) {
            historyManager.remove(epic.getId());
        }
        for (SubTask subTask : getAllSubTasks()) {
            historyManager.remove(subTask.getId());
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            throw new NoSuchElementException("Epic с ID " + epicId + " не найден.");
        }
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Integer addTask(Epic epic) {
        if (epic == null || epics.containsKey(epic.getId())) {
            throw new IllegalArgumentException("Epic не может быть null или иметь повторяющийся ID.");
        }
        int newId = generateId();
        epic.setId(newId);
        epics.put(newId, epic);
        return newId;

    }

    private void updateEpicStatus(Epic epic) {
        if (epic == null) {
            throw new IllegalArgumentException("Epic не может быть null.");
        }

        int isNew = 0;
        int isDone = 0;

        if (epic.getEpicSubTasks().isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        List<Integer> subTasksId = epic.getEpicSubTasks();
        for (Integer subTaskId : subTasksId) {
            if (subTasks.get(subTaskId).getStatus() == TaskStatus.NEW) {
                isNew++;
            } else if (subTasks.get(subTaskId).getStatus() == TaskStatus.DONE) {
                isDone++;
            }
        }

        if (isNew == subTasksId.size()) {
            epic.setStatus(TaskStatus.NEW);
        } else if (isDone == subTasksId.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic == null || !epics.containsKey(epic.getId())) {
            throw new IllegalArgumentException("epic не может быть null, epic с таким id должен существовать");
        }
        Epic savedEpic = epics.get(epic.getId());

        savedEpic.setTaskName(epic.getTaskName());
        savedEpic.setDescription(epic.getDescription());
        savedEpic.setStatus(epic.getStatus());

        updateEpicStatus(savedEpic);
    }

    @Override
    public void deleteEpicById(int epicId) {
        if (!epics.containsKey(epicId)) {
            throw new NoSuchElementException("Epic с ID " + epicId + " не найден");
        }
        Epic epic = epics.get(epicId);
        List<Integer> tmpList = epic.getEpicSubTasks();
        for (Integer subTaskId : tmpList) {
            subTasks.remove(subTaskId);
            historyManager.remove(subTaskId);
        }
        historyManager.remove(epicId);
        epics.remove(epicId);
    }


    @Override
    public List<SubTask> getAllEpicSubTasks(int epicId) {
        if (!epics.containsKey(epicId)) {
            throw new NoSuchElementException("Epic с ID " + epicId + " не найден");
        }
        Epic epic = epics.get(epicId);

        List<SubTask> tmpSubTasks = new ArrayList<>();
        for (Integer subTaskId : epic.getEpicSubTasks()) {
            SubTask subTask = subTasks.get(subTaskId);
            if (subTask != null) {
                tmpSubTasks.add(subTask);
            } else {

                throw new NoSuchElementException("Subtask с таким ID не найден");

            }
        }
        return tmpSubTasks;
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return subTasks.values().stream().toList();
    }

    @Override
    public void deleteAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            for (Integer subTaskId : epic.getEpicSubTasks()) {
                historyManager.remove(subTaskId);
            }
            epic.deleteAllSubtasksId();
            updateEpicStatus(epic);
        }
    }

    @Override
    public SubTask getSubTaskById(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        if (subTask == null) {
            throw new NoSuchElementException("Subtask с ID " + subTaskId + " не найден.");
        }
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public Integer addTask(SubTask subTask) {
        if (subTask == null || subTasks.containsKey(subTask.getId()) || !epics.containsKey(subTask.getEpicId())) {
            throw new IllegalArgumentException("Subtask не может быть null, не должен иметь дублирующийся ID и должен принадлежать существующему epic");
        }

        int newId = generateId();
        subTask.setId(newId);
        subTasks.put(newId, subTask);

        Epic epic = epics.get(subTask.getEpicId());
        epic.addSubTaskId(subTask);
        updateEpicStatus(epic);

        return newId;
    }

    @Override
    public void updateSubTask(SubTask updSubTask) {
        if (updSubTask == null || !subTasks.containsKey(updSubTask.getId())) {
            throw new IllegalArgumentException("Subtask не может быть null, задача с таким id должена существовать");
        }
        SubTask savedSubtask = subTasks.get(updSubTask.getId());

        savedSubtask.setStatus(updSubTask.getStatus());
        savedSubtask.setTaskName(updSubTask.getTaskName());
        savedSubtask.setDescription(updSubTask.getDescription());
        Epic epic = epics.get(savedSubtask.getEpicId());
        updateEpicStatus(epic);
    }


    @Override
    public void deleteSubTaskById(int subTaskId) {
        if (!subTasks.containsKey(subTaskId)) {
            throw new NoSuchElementException("Подзадача с ID " + subTaskId + " не найдена");
        }

        SubTask subTask = subTasks.get(subTaskId);
        int epicId = subTask.getEpicId();

        if (!epics.containsKey(epicId)) {
            throw new NoSuchElementException("Эпик с ID " + epicId + " не найден");    }

        subTasks.remove(subTaskId);
        historyManager.remove(subTaskId);

        Epic epic = epics.get(epicId);
        epic.deleteSubTaskById(subTaskId);
        updateEpicStatus(epic);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}
