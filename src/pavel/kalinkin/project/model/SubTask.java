package pavel.kalinkin.project.model;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String taskName, String description) {
        super(taskName, description);
        this.status = TaskStatus.NEW;
    }

    public SubTask(String taskName, String description, TaskStatus status, int epicId, int id) {
        super(taskName, description, status);
        this.id = id;
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epicId +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
