package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.exceptions.ManagerLoadExceptions;
import pavel.kalinkin.project.exceptions.ManagerSaveException;
import pavel.kalinkin.project.model.*;

import java.io.*;
import java.util.List;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    @Override
    public Integer addTask(Task task) {
        Integer id = super.addTask(task);
        save();
        return id;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteTaskById(int taskId) {
        super.deleteTaskById(taskId);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Integer addTask(Epic epic) {
        Integer id = super.addTask(epic);
        save();
        return id;
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteEpicById(int epicId) {
        super.deleteEpicById(epicId);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Integer addTask(SubTask subTask) {
        Integer id = super.addTask(subTask);
        save();
        return id;
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void deleteSubTaskById(int subTaskId) {
        super.deleteSubTaskById(subTaskId);
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("id,type,name,status,description,epic\n");

            for (Task task : getAllTasks()) {
                bw.write(task.toString() + "\n");
            }

            for (Epic epic : getAllEpics()) {
                bw.write(epic.toString() + "\n");
            }

            for (SubTask subTask : getAllSubTasks()) {
                bw.write(subTask.toString() + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении данных.");
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();

            String line;
            while ((line = br.readLine()) != null && !line.isBlank()) {
                Task task = fromString(line);
                maxId = Math.max(maxId, task.getId());
                manager.restoreTask(task);
            }

            for (SubTask subTask : manager.getAllSubTasks()) {
                Map<Integer, Epic> epics = manager.getEpics();
                Epic epic = epics.get(subTask.getEpicId());
                if (epic != null) {
                    epic.addSubTaskId(subTask);
                }
            }
        } catch (IOException e) {
            throw new ManagerLoadExceptions("Ошибка при загрузке данных из файла.");
        }
        manager.setCurrentId(maxId);
        return manager;
    }

    private void restoreTask(Task task) {
        switch (task.getType()) {
            case TASK -> tasks.put(task.getId(), task);
            case EPIC -> epics.put(task.getId(), (Epic) task);
            case SUBTASK -> subTasks.put(task.getId(), (SubTask) task);
        }
    }

    private static Task fromString(String taskLine) {
        String[] attributes = taskLine.split(",");
        int id = Integer.parseInt(attributes[0]);
        TaskType type = TaskType.valueOf(attributes[1]);
        String name = attributes[2];
        TaskStatus status = TaskStatus.valueOf(attributes[3]);
        String description = attributes[4];

        return switch (type) {
            case TASK -> new Task(id, name, description, status);
            case EPIC -> new Epic(id, name, description, status);
            case SUBTASK -> {
                int epicId = Integer.parseInt(attributes[5]);
                yield new SubTask(id, name, description, status, epicId);
            }
        };
    }
}
