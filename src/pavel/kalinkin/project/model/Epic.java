package pavel.kalinkin.project.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private final List<Integer> epicSubTasksId = new ArrayList<>();
    private LocalDateTime endTime;

    //Обычный конструктор для эпика
    public Epic(String taskName, String description) {
        super(taskName, description);
        this.status = TaskStatus.NEW;
    }

    //Конструктор с указанием id
    public Epic(int id, String taskName, String description) {
        super(id, taskName, description);
    }

    //Конструктор для выгрузки из файла
    public Epic(int id, String name, String description, TaskStatus status, Duration duration, LocalDateTime startTime, LocalDateTime endTime) {
        super(id, name, description, status, duration, startTime);
        this.endTime = endTime;
    }

    public List<Integer> getEpicSubTasks() {
        return new ArrayList<>(epicSubTasksId);
    }

    public void addSubTaskId(SubTask subTask) {
        if (subTask != null && !epicSubTasksId.contains(subTask.getId())) {
            epicSubTasksId.add(subTask.getId());
        } else {
            throw new IllegalArgumentException("Подзадача с таким ID уже есть или равна null");
        }
    }

    public void deleteAllSubtasksId() {
        epicSubTasksId.clear();
    }

    public void deleteSubTaskById(int subTaskId) {
        if (!epicSubTasksId.remove(Integer.valueOf(subTaskId))) {
            throw new IllegalArgumentException("SubTask с таким ID не найден");
        }
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public Duration getDuration() {
        if (startTime != null && endTime != null)
            return Duration.between(startTime, endTime);
        else
            return null;
    }

    @Override
    public LocalDateTime getEndTime() {
        if (endTime != null)
            return endTime;
        else
            return null;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", duration=" + duration +
                ", epicSubTasksId=" + epicSubTasksId +
                '}';
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }
}
