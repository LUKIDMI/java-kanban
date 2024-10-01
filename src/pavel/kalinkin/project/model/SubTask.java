package pavel.kalinkin.project.model;

public class SubTask extends Task {
    private final int epicId;

    public SubTask(String taskName, String description, int epicId) {
        super(taskName, description);
        this.status = TaskStatus.NEW;
        this.epicId = epicId;
    }

    public SubTask(int id, String taskName, String description, TaskStatus status, int epicId) {
        super(id, taskName, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,%d", getId(), getType(), getTaskName(), getStatus(), getDescription(), getEpicId());
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }
}
