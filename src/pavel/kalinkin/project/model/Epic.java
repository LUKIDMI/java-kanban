package pavel.kalinkin.project.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> epicSubTasksId = new ArrayList<>();

    public Epic(String taskName, String description) {
        super(taskName, description, TaskStatus.NEW);

    }

    public Epic(String taskName, String description, int id) {
        super(taskName, description, TaskStatus.NEW, id);
        this.id = id;
    }

    public List<Integer> getEpicSubTasks() {
        return new ArrayList<>(epicSubTasksId);
    }

    public void addSubTaskId(SubTask subTask) {
        epicSubTasksId.add(subTask.getId());
    }

    public void deleteAllSubtasksId() {
        epicSubTasksId.clear();
    }


    public void deleteSubTaskById(int subTaskId) {
        epicSubTasksId.remove(Integer.valueOf(subTaskId));
    }

    @Override
    public String toString() {
        return "Epic{" +
                "epicSubTasksId=" + epicSubTasksId +
                ", taskName='" + taskName +
                '\'' + ", description='" + description +
                '\'' + ", id=" + id +
                ", status=" + status + '}';
    }
}
