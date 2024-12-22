package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class CSVTaskFormatter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private CSVTaskFormatter() {
    }

    //Метод для выгрузки задачи из файла
    public static Task fromString(String taskLine) {
        String[] attributes = taskLine.split(",");

        int id = Integer.parseInt(attributes[0]);
        TaskType type = TaskType.valueOf(attributes[1]);
        String name = attributes[2];
        TaskStatus status = TaskStatus.valueOf(attributes[3]);
        String description = attributes[4];

        return createTask(id, type, name, status, description, attributes);
    }

    //Метод для перехода в метод создания задачи, в зависимости от ее типа
    private static Task createTask(
            int id,
            TaskType type,
            String name,
            TaskStatus status,
            String description,
            String[] attributes
    ) {
        return switch (type) {
            case TASK -> createTask(id, name, description, status, attributes);
            case SUBTASK -> createSubTask(id, name, description, status, attributes);
            case EPIC -> createEpic(id, name, description, status, attributes);
        };
    }

    //Создание задачи
    private static Task createTask(
            int id,
            String name,
            String description,
            TaskStatus status,
            String[] attributes
    ) {
        Duration duration;
        LocalDateTime startTime;


        if (!attributes[5].equals("null"))
            duration = Duration.ofMinutes(Long.parseLong(attributes[5]));
        else
            duration = null;

        if (!attributes[6].equals("null"))
            startTime = LocalDateTime.parse(attributes[6], formatter);
        else
            startTime = null;

        return new Task(id, name, description, status, duration, startTime);
    }

    //Создание подзадачи
    private static SubTask createSubTask(
            int id,
            String name,
            String description,
            TaskStatus status,
            String[] attributes
    ) {
        Duration duration = Duration.ofMinutes(Long.parseLong(attributes[5]));
        LocalDateTime startTime = LocalDateTime.parse(attributes[6], formatter);
        int epicId = Integer.parseInt(attributes[8]);

        return new SubTask(id, name, description, status, duration, startTime, epicId);
    }

    //Создание эпика
    private static Epic createEpic(
            int id,
            String name,
            String description,
            TaskStatus status,
            String[] attributes
    ) {
        Duration duration;
        LocalDateTime startTime;
        LocalDateTime endTime;

        if (!attributes[5].equals("null"))
            duration = Duration.ofMinutes(Long.parseLong(attributes[5]));
        else
            duration = null;

        if (!attributes[6].equals("null") && !attributes[7].equals("null")) {
            startTime = LocalDateTime.parse(attributes[6], formatter);
            endTime = LocalDateTime.parse(attributes[7], formatter);
        } else {
            startTime = null;
            endTime = null;
        }

        return new Epic(id, name, description, status, duration, startTime, endTime);
    }

    //Метод, конвертирующий задачу в строку для дальнейшего сохранения
    public static String toString(Task task) {
        Objects.requireNonNull(task, "Задача не может быть null");

        StringBuilder sb = new StringBuilder();
        Long duration = Optional.ofNullable(task.getDuration()).map(Duration::toMinutes).orElse(null);

        sb.append(task.getId()).append(",")
                .append(task.getType()).append(",")
                .append(task.getTaskName()).append(",")
                .append(task.getStatus()).append(",")
                .append(task.getDescription()).append(",")
                .append(duration).append(",");

        Optional.ofNullable(task.getStartTime())
                .map(time -> time.format(formatter))
                .ifPresentOrElse(time -> sb.append(time).append(","), () -> sb.append("null").append(","));

        Optional.ofNullable(task.getEndTime())
                .map(time -> time.format(formatter))
                .ifPresentOrElse(time -> sb.append(time).append(","), () -> sb.append("null").append(","));

        if (task instanceof SubTask subTask) {
            sb.append(subTask.getEpicId());
        }

        return sb.toString();
    }
}
