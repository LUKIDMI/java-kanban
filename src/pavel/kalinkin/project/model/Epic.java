package pavel.kalinkin.project.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> epicSubTasksId = new ArrayList<>();

    public Epic(String taskName, String description) {
        super(taskName, description);
        this.status = TaskStatus.NEW;
    }

    public List<Integer> getEpicSubTasks() {
        return epicSubTasksId;
    }

    public void addSubTask(SubTask subTask) {
        epicSubTasksId.add(subTask.getId());
    }

    @Override
    public String toString() {
        return "Epic{" +
                "epicSubTasksId=" + epicSubTasksId +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
