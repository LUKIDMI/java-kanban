package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.interfaces.HistoryManager;
import pavel.kalinkin.project.interfaces.TaskManager;
import pavel.kalinkin.project.model.*;

import java.time.Duration;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    protected final HistoryManager historyManager = new InMemoryHistoryManager();
    protected final TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    private int currentId = 0;

    //Возвращает новый id
    private int generateId() {
        return ++currentId;
    }

    //Сеттер для обновления id
    protected void setCurrentId(int id) {
        currentId = id;
    }

    //Получаем все задачи
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    //Удаляем все задачи
    @Override
    public void deleteAllTasks() {
        tasks.values().stream()
                .map(Task::getId)
                .forEach(historyManager::remove);

        prioritizedTasks.removeIf(task -> task.getType() == TaskType.TASK);

        tasks.clear();
    }

    //Возвращает задачу по id
    @Override
    public Task getTaskById(int taskId) {
        Task task = tasks.get(taskId);
        if (task == null) {
            System.out.println(("Задача не найдена."));
            return null;
        }
        historyManager.add(task);
        return task;
    }

    //Добавляем задачу в менеджер
    @Override
    public Integer addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Задача не может быть null.");
        }

        int id = generateId();
        task.setId(id);
        tasks.put(id, task);

        addPrioritizedTasks(task);

        return id;
    }

    //Обновляем задачу
    @Override
    public void updateTask(Task task) {
        if (task == null || !tasks.containsKey(task.getId())) {
            throw new IllegalArgumentException("Задача не найдена или равна null.");
        }

        tasks.put(task.getId(), task);

        addPrioritizedTasks(task);
    }

    //Удаляем задачу по id
    @Override
    public void deleteTaskById(int taskId) {
        if (tasks.containsKey(taskId)) {
            prioritizedTasks.remove(tasks.get(taskId));
            tasks.remove(taskId);
            historyManager.remove(taskId);
        } else {
            throw new IllegalArgumentException("Задача не найдена.");
        }
    }

    //Возвращает все эпики
    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    //Удаляет все эпики
    @Override
    public void deleteAllEpics() {
        epics.values().stream()
                .flatMap(epic -> epic.getEpicSubTasks().stream())
                .forEach(historyManager::remove);

        deleteAllSubTasks();
        epics.clear();
    }

    //Возвращает эпик по id
    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            historyManager.add(epic);
        } else {
            throw new IllegalArgumentException("Эпик не может быть null.");
        }
        return epic;
    }

    //Добавляет эпик в менеджер
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

    //Обновление эпика
    @Override
    public void updateEpic(Epic epic) {
        if (epic == null || !epics.containsKey(epic.getId())) {
            throw new IllegalArgumentException("Эпик не найден или равен null.");
        }

        Epic existingEpic = epics.get(epic.getId());
        existingEpic.setTaskName(epic.getTaskName());
        existingEpic.setDescription(epic.getDescription());


    }

    //Обновление startTime и endTime для эпика
    private void updateEpicTime(Epic epic) {
        Objects.requireNonNull(epic, "Эпик не может быть null.");

        epic.setDuration(epic.getEpicSubTasks().stream()
                .map(subTasks::get)
                .map(SubTask::getDuration)
                .filter(Objects::nonNull)
                .reduce(Duration.ZERO, Duration::plus));

        epic.setStartTime(epic.getEpicSubTasks().stream()
                .map(subTasks::get)
                .map(SubTask::getStartTime)
                .min(Comparator.naturalOrder()).orElse(null));

        epic.setEndTime(epic.getEpicSubTasks().stream()
                .map(subTasks::get)
                .map(SubTask::getEndTime)
                .max(Comparator.naturalOrder()).orElse(null));
    }

    //Обновление статуса эпика
    private void updateEpicStatus(Epic epic) {
        if (epic == null) {
            throw new IllegalArgumentException("Эпик не может быть null.");
        }

        List<Integer> subTaskIds = epic.getEpicSubTasks();

        if (subTaskIds.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        long newCount = subTaskIds.stream()
                .map(subTasks::get)
                .filter(Objects::nonNull)
                .filter(subtask -> subtask.getStatus() == TaskStatus.NEW)
                .count();


        long doneCount = subTaskIds.stream()
                .map(subTasks::get)
                .filter(Objects::nonNull)
                .filter(subtask -> subtask.getStatus() == TaskStatus.DONE)
                .count();

        if (newCount == subTaskIds.size()) {
            epic.setStatus(TaskStatus.NEW);
        } else if (doneCount == subTaskIds.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    //Удаление эпика по id
    @Override
    public void deleteEpicById(int epicId) {
        Epic epic = epics.remove(epicId);
        if (epic != null) {
            for (Integer subtaskId : epic.getEpicSubTasks()) {
                Task subtask = subTasks.remove(subtaskId);
                if (subtask != null) {
                    prioritizedTasks.remove(subtask);
                    historyManager.remove(subtaskId);
                }
            }

        } else {
            throw new IllegalArgumentException("Эпика с таким ID нет.");
        }
    }

    //Возвращает все подзадачи эпика
    @Override
    public List<SubTask> getAllEpicSubTasks(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            throw new IllegalArgumentException("Эпик не найден.");
        }
        return epic.getEpicSubTasks().stream()
                .map(subTasks::get)
                .filter(Objects::nonNull)
                .toList();
    }

    //Возвращает все подзадачи
    @Override
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    //Удаляет все подзадачи
    @Override
    public void deleteAllSubTasks() {
        for (SubTask subtask : subTasks.values()) {
            historyManager.remove(subtask.getId());
        }

        prioritizedTasks.removeAll(subTasks.values());
        subTasks.clear();

        for (Epic epic : epics.values()) {
            epic.deleteAllSubtasksId();
            updateEpicStatus(epic);
            updateEpicTime(epic);
        }
    }

    //Возвращает все подзадачи по id
    @Override
    public SubTask getSubTaskById(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        if (subTask != null) {
            historyManager.add(subTask);
        } else {
            throw new IllegalArgumentException("Подзадача не может быть null.");
        }
        return subTask;
    }

    //Добавляет подзачу в менеджер
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

        updateEpicTime(epic);

        addPrioritizedTasks(subTask);

        return id;
    }

    //Обновляет подзадачу
    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTask == null || !subTasks.containsKey(subTask.getId())) {
            throw new IllegalArgumentException("Подзадача не найдена или равна null.");
        }

        addPrioritizedTasks(subTask);

        subTasks.put(subTask.getId(), subTask);

        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            updateEpicStatus(epic);
            updateEpicTime(epic);
        } else {
            throw new IllegalArgumentException("Эпик не может быть null.");
        }

    }

    //Удаляет подзадачу по id
    @Override
    public void deleteSubTaskById(int subTaskId) {
        SubTask subTask = subTasks.remove(subTaskId);
        if (subTask == null) {
            throw new IllegalArgumentException("Подзадача не найдена.");
        }

        Epic epic = epics.get(subTask.getEpicId());

        if (epic != null) {
            epic.deleteSubTaskById(subTaskId);
            updateEpicStatus(epic);
            updateEpicTime(epic);
        }

        historyManager.remove(subTaskId);
        prioritizedTasks.remove(subTask);
    }

    //Возвращает историю просмотра
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    //Возвращает список задач, отсортированных по приоритету
    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    //Добавляет задачу в список отсортированных задач, если задано startTime и нет пересечения по времени
    protected void addPrioritizedTasks(Task task) {
        if (task.getStartTime() == null) {
            return;
        }

        if (isOverlapped(task)) {
            throw new IllegalArgumentException("Задача " + task.getId() + " пересекается с другой задачей.");
        }

        prioritizedTasks.add(task);
    }

    //Проверяет на пересечение
    private boolean isOverlapped(Task task) {
        return getPrioritizedTasks().stream()
                .filter(anotherTask -> !anotherTask.equals(task))
                .anyMatch(anotherTask -> !(task.getStartTime().isAfter(anotherTask.getEndTime()) ||
                        task.getEndTime().isBefore(anotherTask.getStartTime())));
    }
}

