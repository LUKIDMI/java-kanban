package pavel.kalinkin.project.manager;

import org.jetbrains.annotations.NotNull;
import pavel.kalinkin.project.model.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class CSVTaskFormatter {

    private CSVTaskFormatter() {}

    public static Task fromString(String taskLine) {
        String[] attributes = taskLine.split(",");

        int id = Integer.parseInt(attributes[0]);
        TaskType type = TaskType.valueOf(attributes[1]);
        String name = attributes[2];
        TaskStatus status = TaskStatus.valueOf(attributes[3]);
        String description = attributes[4];

        return createTask(id, type, name, status, description, attributes);
    }

    private static Task createTask(int id, TaskType type, String name, TaskStatus status, String description, String[] attributes) {
        return switch (type) {
            case TASK -> createTask(id, name, description, status, attributes);
            case SUBTASK -> createSubTask(id, name, description, status, attributes);
            case EPIC -> new Epic(id, name, description);
        };
    }

    private static Task createTask(int id, String name, String description, TaskStatus status, String[] attributes) {
        Duration duration = Duration.parse(attributes[5]);
        LocalDateTime startTime = LocalDateTime.parse(attributes[6]);
        return new Task(id, name, description, status, duration, startTime);
    }

    private static SubTask createSubTask(int id, String name, String description, TaskStatus status, String[] attributes) {
        Duration duration = Duration.parse(attributes[5]);
        LocalDateTime startTime = LocalDateTime.parse(attributes[6]);
        int epicId = Integer.parseInt(attributes[7]);
        return new SubTask(id, name, description, status, duration, startTime, epicId);
    }

    public static String toString(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getId()).append(",")
                .append(task.getType()).append(",")
                .append(task.getTaskName()).append(",")
                .append(task.getStatus()).append(",")
                .append(task.getDescription()).append(",")
                .append(task.getDuration()).append(",")
                .append(task.getStartTime()).append(",")
                .append(task.getEndTime());

        if (task instanceof SubTask subTask) {
            sb.append(",").append(subTask.getEpicId());
        }

        return sb.toString();
    }
}
