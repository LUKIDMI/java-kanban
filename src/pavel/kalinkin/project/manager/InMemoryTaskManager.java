package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTaskManager implements TaskManager {
    private final HistoryManager historyManager;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private int idCounter;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
        this.idCounter = 0;
    }

    @Override
    public int generateId() {
        return ++idCounter;
    }

    //
    @Override
    public Integer addTask(Task task) {
        if (task != null && task.getClass() == Task.class && !tasks.containsKey(task.getId())) {
            int newId = generateId();
            task.setId(newId);
            tasks.put(newId, task);
            return newId;
        }

        System.out.println("В методе добавления Task в качестве аргумента передан null или задача другого типа");
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        return tasks.values().stream().toList();
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = tasks.get(taskId);
        if (task == null) {
            System.out.println("Task с таким ID нет в списке задач.");
            return null;
        }
        historyManager.addTask(task);
        return task;
    }


    @Override
    public void updateTask(Task task) {
        if (task != null && tasks.containsKey(task.getId()) && task.getClass() == Task.class) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("В методе обновления Task в качестве аргумента передан null или задачи с таким ID нет.");
        }
    }

    @Override
    public void deleteTaskById(int taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            tasks.remove(taskId);
        } else {
            System.out.println("Task с таким ID нет в списке задач.");
        }
    }

    @Override
    public List<Epic> getAllEpics() {
        return epics.values().stream().toList();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            System.out.println("Epic с таким ID нет в списке задач.");
            return null;
        }
        historyManager.addTask(epic);
        return epic;
    }

    @Override
    public Integer addEpic(Epic epic) {
        if (epic != null && !epics.containsKey(epic.getId())) {
            int newId = generateId();
            epic.setId(newId);
            epics.put(newId, epic);
            return newId;
        }

        System.out.println("В методе добавления Epic в качестве аргумента передан null");
        return -1;

    }

    private void updateEpicStatus(Epic epic) {
        if (epic == null) {
            System.out.println("В метод по обновлению Epic в качестве аргумента null.");
            return;
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
        if (epic != null && epics.containsKey(epic.getId())) {
            Epic updEpic = epics.get(epic.getId());
            updEpic.setTaskName(epic.getTaskName());
            updEpic.setDescription((epic.getDescription()));
        } else {
            System.out.println("В метод обновления Epic передан null или Epic с таким id нет.");
        }
    }

    @Override
    public void deleteEpicById(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            List<Integer> tmpList = epic.getEpicSubTasks();
            for (Integer subTaskId : tmpList) {
                subTasks.remove(subTaskId);
            }
            epics.remove(epicId);
        } else {
            System.out.println("Epic с таким ID нет в списке задач.");
        }
    }


    @Override
    public List<SubTask> getAllEpicSubTasks(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            System.out.println("В метод getEpicSubTasks в качесвте аргумента передан null");
            return new ArrayList<>();
        }
        List<SubTask> tmpSubTasks = new ArrayList<>();
        for (Integer subTaskId : epic.getEpicSubTasks()) {
            SubTask subTask = subTasks.get(subTaskId);
            if (subTask != null) {
                tmpSubTasks.add(subTask);
            } else {
                System.out.println("Subtask с таким ID нет в списке задач.");
                return new ArrayList<>();
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
            epic.deleteAllSubtasksId();
            updateEpicStatus(epic);
        }
    }

    @Override
    public SubTask getSubTaskById(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        if (subTask == null) {
            System.out.println("Subtask с таким ID нет в списке подзадач.");
            return null;
        }
        historyManager.addTask(subTask);
        return subTask;
    }

    @Override
    public Integer addSubTask(SubTask subTask) {
        if (subTask != null && !subTasks.containsKey(subTask.getId())) {
            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                int newId = generateId();
                subTask.setId(newId);
                epic.addSubTaskId(subTask);
                subTasks.put(newId, subTask);
                updateEpicStatus(epic);
                return newId;
            }
        }
        System.out.println("В методе добавления SubTask в качестве аргумента передан null");
        return -1;
    }

    @Override
    public void updateSubTask(SubTask updSubTask) {
        if (updSubTask != null && subTasks.containsKey(updSubTask.getId())) {
            SubTask subTask = subTasks.get(updSubTask.getId());
            if (subTask != null && subTask.getEpicId() == updSubTask.getEpicId()) {
                subTasks.put(updSubTask.getId(), updSubTask);
                Epic epic = epics.get(updSubTask.getEpicId());
                updateEpicStatus(epic);
            } else {
                System.out.println("");
            }
        } else {
            System.out.println("В методе по обновлению Subtask передан null или Subtask с таким ID нет в списке задач.");
        }
    }


    @Override
    public void deleteSubTaskById(int subTaskId) {
        if (subTasks.containsKey(subTaskId)) {
            Epic epic = epics.get(subTasks.get(subTaskId).getEpicId());
            if (epic != null) {
                epic.deleteSubTaskById(subTaskId);
                subTasks.remove(subTaskId);
                updateEpicStatus(epic);
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}
