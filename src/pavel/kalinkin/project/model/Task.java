package pavel.kalinkin.project.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected TaskStatus status;
    protected Duration duration;
    protected LocalDateTime startTime;

    public Task(String taskName, String description, TaskStatus status, Duration duration, LocalDateTime startTime) {
        this.name = taskName;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(int id, String name, String description, TaskStatus status, Duration duration, LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String taskName, String description, TaskStatus taskStatus) {
        this.name = taskName;
        this.description = description;
        this.status = taskStatus;
    }

    public Task(int id, String taskName, String description, TaskStatus taskStatus) {
        this.id = id;
        this.name = taskName;
        this.description = description;
        this.status = taskStatus;
    }

    public String getTaskName() {
        return name;
    }

    public void setTaskName(String taskName) {
        this.name = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return getId() == task.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s", getId(), getType(), getTaskName(), getStatus(), getDescription());
    }

    public TaskType getType() {
        return TaskType.TASK;
    }
}
