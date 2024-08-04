package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private static int idCounter;

    public static int setIdCounter() {
        return ++idCounter;
    }

    public List<Task> getAllTasks() {
        return tasks.values().stream().toList();
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(int taskId) {
        return tasks.get(taskId);
    }

    public void addTask(Task task) {
        if (task == null) {
            System.out.println("В методе добавления Task в качестве аргумента передан null");
            return;
        }
        tasks.put(task.getId(), task);
    }

    public void updateTask(Task task, TaskStatus newStatus) {
        if (task == null) {
            System.out.println("В методе обновления Task в качестве аргумента передан null");
            return;
        }

        Task updTask = tasks.get(task.getId());
        updTask.setStatus(newStatus);
    }

    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
    }

    public List<Epic> getAllEpics() {
        return epics.values().stream().toList();
    }

    public void deleteAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public Epic getEpicById(int epicId) {
        return epics.get(epicId);
    }

    public void addEpic(Epic epic) {
        if (epic == null) {
            System.out.println("В методе добавления Epic в качестве аргумента передан null");
            return;
        }
        epics.put(epic.getId(), epic);

    }

    public void updateEpic(Epic epic) {
        if (epic == null) {
            System.out.println("В методе обновления Epic в качестве аргумента передан null");
            return;
        } else if (epic.getEpicSubTasks().isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        if (isEpicNew(epic)) {
            epic.setStatus(TaskStatus.NEW);
        } else if (isEpicDone(epic)) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    public boolean isEpicNew(Epic epic) {
        boolean isNew = true;
        for (Integer subTask : epic.getEpicSubTasks()) {
            if (subTasks.get(subTask).getSubTaskStatus() != TaskStatus.NEW) {
                isNew = false;
            }
        }
        return isNew;
    }

    public boolean isEpicDone(Epic epic) {
        boolean isDone = true;
        for (Integer subTask : epic.getEpicSubTasks()) {
            if (subTasks.get(subTask).getSubTaskStatus() != TaskStatus.DONE) {
                isDone = false;
            }
        }
        return isDone;
    }

    public void deleteEpicById(int epicId) {
        Epic tmpEpic = epics.get(epicId);
        List<Integer> tmpList = tmpEpic.getEpicSubTasks();
        for (int i = 0; i < tmpList.size(); i++) {
            subTasks.remove(tmpList.get(i));
            tmpList.remove(tmpList.get(i));
        }
        epics.remove(epicId);
    }

    public List<SubTask> getAllEpicSubTasks(Epic epic) {
        if (epic == null) {
            System.out.println("В метод getEpicSubTasks в качесвте аргумента передан null");
            return new ArrayList<>();
        }
        List<SubTask> tmpSubTasks = new ArrayList<>();
        Epic tmpEpic = epics.get(epic.getId());
        for (Integer subTaskId : tmpEpic.getEpicSubTasks()) {
            tmpSubTasks.add(subTasks.get(subTaskId));
        }
        return tmpSubTasks;
    }

    public List<SubTask> getAllSubTasks() {
        return subTasks.values().stream().toList();
    }

    public void deleteAllSubTasks() {
        for (Integer subTaskId : subTasks.keySet()) {
            epics.get(subTasks.get(subTaskId).getEpicId()).getEpicSubTasks().remove(subTaskId);
            subTasks.remove(subTaskId);
        }
    }

    public SubTask getSubTaskById(int subTaskId) {
        return subTasks.get(subTaskId);
    }

    public void addSubTask(SubTask subTask) {
        if (subTask == null) {
            System.out.println("В методе добавления SubTask в качестве аргумента передан null");
            return;
        }
        Epic epic = epics.get(subTask.getEpicId());
        epic.addSubTask(subTask);
        subTasks.put(subTask.getId(), subTask);
    }

    public void updateSubTask(SubTask subTask, TaskStatus newStatus) {
        if (subTask == null) {
            System.out.println("В методе обновления SubTask в качестве аргумента передан null");
            return;
        }

        SubTask updSubTask = subTasks.get(subTask.getId());
        updSubTask.setStatus(newStatus);

        updateEpic(epics.get(updSubTask.getEpicId()));
    }

    public void deleteSubTaskById(int subTaskId) {
        epics.get(subTasks.get(subTaskId).getEpicId()).getEpicSubTasks().remove(subTaskId);
        subTasks.remove(subTaskId);
    }
}
