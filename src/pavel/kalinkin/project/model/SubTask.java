package pavel.kalinkin.project.model;


import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
    private final int epicId;

    public SubTask(String taskName, String description) {
        super(taskName, description);
        this.status = TaskStatus.NEW;
        this.epicId = 0;
    }

    public SubTask(String name, String description, Duration duration, LocalDateTime startTime) {
        super(name, description, duration, startTime);
        this.status = TaskStatus.NEW;
        epicId = 0;
    }

    public SubTask(String name, String description, Duration duration, LocalDateTime startTime, int epicId) {
        super(name, description, duration, startTime);
        this.epicId = epicId;
    }

    public SubTask(int id, String taskName, String description, TaskStatus status, Duration duration, LocalDateTime startTime, int epicId) {
        super(id, taskName, description, status, duration, startTime);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }


    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", endTime=" + getEndTime() +
                ", epicId=" + epicId +
                '}';
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }
}

