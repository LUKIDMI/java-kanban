package pavel.kalinkin.project.model;


import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
    private final int epicId;

    public SubTask(int id, String taskName, String description, TaskStatus status, Duration duration, LocalDateTime startTime, int epicId) {
        super(id, taskName, description, status, duration, startTime);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,%s,%s,%d", getId(), getType(), getTaskName(), getStatus(), getDescription(), getDuration(), getStartTime(), getEpicId());
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }
}

