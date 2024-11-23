package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.model.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    protected final HistoryManager historyManager = new InMemoryHistoryManager();
    private int currentId = 0;

    @Override
    public int generateId() {
        return ++currentId;
    }

    // Работа с обычными задачами
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTasks() {
        for (Integer taskId : tasks.keySet()) {
            historyManager.remove(taskId);
        }
        tasks.clear();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Integer addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Задача не может быть null.");
        }
        int id = generateId();
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    @Override
    public void updateTask(Task task) {
        if (task == null || !tasks.containsKey(task.getId())) {
            throw new IllegalArgumentException("Задача не найдена или равна null.");
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void deleteTaskById(int taskId) {
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
            historyManager.remove(taskId);
        } else {
            throw new IllegalArgumentException("Задача не найдена.");
        }
    }

    // Работа с эпиками
    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllEpics() {
        for (Epic epic : epics.values()) {
            for (Integer subTaskId : epic.getEpicSubTasks()) {
                historyManager.remove(subTaskId);
            }
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Integer addTask(Epic epic) {
        if (epic == null) {
            throw new IllegalArgumentException("Эпик не может быть null.");
        }
        int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic == null || !epics.containsKey(epic.getId())) {
            throw new IllegalArgumentException("Эпик не найден или равен null.");
        }

        Epic existingEpic = epics.get(epic.getId());
        existingEpic.setTaskName(epic.getTaskName());
        existingEpic.setDescription(epic.getDescription());

        updateEpicStatus(existingEpic);
    }

    private void updateEpicStatus(Epic epic) {
        if (epic == null) {
            throw new IllegalArgumentException("Эпик не может быть null.");
        }

        List<Integer> subTaskIds = epic.getEpicSubTasks();

        if (subTaskIds.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Integer subTaskId : subTaskIds) {
            SubTask subTask = subTasks.get(subTaskId);
            if (subTask != null) {
                if (subTask.getStatus() != TaskStatus.NEW) {
                    allNew = false;
                }
                if (subTask.getStatus() != TaskStatus.DONE) {
                    allDone = false;
                }
            }
        }

        if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public void deleteEpicById(int epicId) {
        Epic epic = epics.remove(epicId);
        if (epic != null) {
            for (Integer subTaskId : epic.getEpicSubTasks()) {
                subTasks.remove(subTaskId);
            }
            historyManager.remove(epicId);
        }
    }

    @Override
    public List<SubTask> getAllEpicSubTasks(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            throw new IllegalArgumentException("Эпик не найден.");
        }
        List<SubTask> subTaskList = new ArrayList<>();
        for (Integer subTaskId : epic.getEpicSubTasks()) {
            subTaskList.add(subTasks.get(subTaskId));
        }
        return subTaskList;
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void deleteAllSubTasks() {
        for (Integer subTaskId : subTasks.keySet()) {
            historyManager.remove(subTaskId);
        }
        subTasks.clear();

        for (Epic epic : epics.values()) {
            epic.deleteAllSubtasksId();
        }
    }

    @Override
    public SubTask getSubTaskById(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        if (subTask != null) {
            historyManager.add(subTask);
        }
        return subTask;
    }

    @Override
    public Integer addTask(SubTask subTask) {
        if (subTask == null) {
            throw new IllegalArgumentException("Подзадача не может быть null.");
        }
        Epic epic = epics.get(subTask.getEpicId());
        if (epic == null) {
            throw new IllegalArgumentException("Эпик не найден.");
        }
        int id = generateId();
        subTask.setId(id);
        subTasks.put(id, subTask);
        epic.addSubTaskId(subTask);
        return id;
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTask == null || !subTasks.containsKey(subTask.getId())) {
            throw new IllegalArgumentException("Подзадача не найдена или равна null.");
        }
        subTasks.put(subTask.getId(), subTask);
    }

    @Override
    public void deleteSubTaskById(int subTaskId) {
        SubTask subTask = subTasks.remove(subTaskId);
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                epic.deleteSubTaskById(subTaskId);
            }
            historyManager.remove(subTaskId);
        }
    }

    // История задач
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}

