package pavel.kalinkin.project.model;

import pavel.kalinkin.project.manager.TaskManager;

import java.util.Objects;

public class Task {
    protected String taskName;
    protected String description;
    protected final int id;
    protected TaskStatus status;

    public Task(String taskName, String description) {
        this.taskName = taskName;
        this.description = description;
        this.id = TaskManager.setIdCounter();
        this.status = TaskStatus.NEW;
    }

    public int getId(){
        return this.id;
    }

    public TaskStatus getStatus(){
        return this.status;
    }

    public void setStatus(TaskStatus status){
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
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
