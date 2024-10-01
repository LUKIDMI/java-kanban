package pavel.kalinkin.project.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> epicSubTasksId = new ArrayList<>();

    public Epic(String taskName, String description) {
        super(taskName, description, TaskStatus.NEW);

    }

    public Epic(int id, String taskName, String description, TaskStatus taskStatus) {
        super(id, taskName, description, taskStatus);
        this.id = id;
    }


    public List<Integer> getEpicSubTasks() {
        return new ArrayList<>(epicSubTasksId);
    }

    public void addSubTaskId(SubTask subTask) {
        if (subTask != null && !epicSubTasksId.contains(subTask.getId()))
            epicSubTasksId.add(subTask.getId());
        else
            System.out.println("SubTask с таким ID уже есть в списке эпика или передан null");
    }

    public void deleteAllSubtasksId() {
        epicSubTasksId.clear();
    }


    public void deleteSubTaskById(int subTaskId) {
        epicSubTasksId.remove(Integer.valueOf(subTaskId));
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s", getId(), getType(), getTaskName(), getStatus(), getDescription());
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }
}
