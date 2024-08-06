package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Приветик еще раз, готов к правкам))) 

public class TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private int idCounter;

    private int generateId() {
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
        if (task != null && task.getClass() == Task.class) {
            task.setId(generateId());
            tasks.put(task.getId(), task);
        } else {
            System.out.println("В методе добавления Task в качестве аргумента передан null");
        }
    }

    public void updateTask(Task task) {
        if (task != null && tasks.containsKey(task.getId()) && task.getClass() == Task.class) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("В методе обновления Task в качестве аргумента передан null или задачи с таким id нет.");
        }
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
        if (epic != null) {
            epic.setId(generateId());
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("В методе добавления Epic в качестве аргумента передан null");
        }
    }

    public void updateEpic(Epic epic) {
        if (epic != null && epics.containsKey(epic.getId())) {
            Epic updEpic = epics.get(epic.getId());
            updEpic.setTaskName(epic.getTaskName());
            updEpic.setDescription((epic.getDescription()));
        } else {
            System.out.println("В метод обновления Epic передан null или Epic с таким id нет.");
        }
    }

    private void updateEpicStatus(Epic epic) {
        if (epic == null) {
            System.out.println("В метод по обновлению Epic передан null.");
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

    public void deleteEpicById(int epicId) {
        Epic tmpEpic = epics.get(epicId);
        if (tmpEpic != null) {
            List<Integer> tmpList = tmpEpic.getEpicSubTasks();
            for (Integer subTaskId : tmpList) {
                subTasks.remove(subTaskId);
            }
            epics.remove(epicId);
        }
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
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtasksId();
            updateEpicStatus(epic);
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
        if (epic != null) {
            subTask.setId(generateId());
            epic.addSubTaskId(subTask);
            subTasks.put(subTask.getId(), subTask);
            updateEpicStatus(epic);
        }
    }

    public void updateSubTask(SubTask updSubTask) {
        if (updSubTask != null && subTasks.containsKey(updSubTask.getId())) {
            SubTask subTask = subTasks.get(updSubTask.getId());
            if (subTask.getEpicId() == updSubTask.getEpicId()) {
                subTasks.put(updSubTask.getId(), updSubTask);
                Epic epic = epics.get(updSubTask.getEpicId());
                updateEpicStatus(epic);
            }
        }
    }

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
}
