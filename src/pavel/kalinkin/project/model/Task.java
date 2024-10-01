package pavel.kalinkin.project.model;

import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected TaskStatus status;

    public Task(String taskName, String description) {
        this.name = taskName;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    public Task(String taskName, String description, TaskStatus status) {
        this.name = taskName;
        this.description = description;
        this.status = status;
    }

    public Task(int id, String taskName, String description, TaskStatus status) {
        this.name = taskName;
        this.description = description;
        this.status = status;
        this.id = id;
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

    public Task fromString(String task){
        String[] taskElements = task.split(",");
        int taskId = Integer.parseInt(taskElements[0]);
        String taskName = taskElements[2];
        TaskStatus taskStatus = TaskStatus.valueOf(taskElements[3]);
        String taskDescription = taskElements[4];
        return new Task(taskId, taskName, taskDescription, taskStatus);
    }

    public TaskType getType() {
        return TaskType.TASK;
    }
}
