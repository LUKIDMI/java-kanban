package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.exceptions.ManagerLoadExceptions;
import pavel.kalinkin.project.exceptions.ManagerSaveException;
import pavel.kalinkin.project.model.*;

import java.io.*;


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
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Integer addTask(Epic epic) {
        int id = super.addTask(epic);
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
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public Integer addTask(SubTask subTask) {
        int id = super.addTask(subTask);
        save();
        return id;
    }

    @Override
    public void updateSubTask(SubTask updSubTask) {
        super.updateSubTask(updSubTask);
        save();
    }

    @Override
    public void deleteSubTaskById(int subTaskId) {
        super.deleteSubTaskById(subTaskId);
        save();
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("id,type,name,status,description.epic\n");

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
            throw new ManagerSaveException();
        }
    }

    public static TaskManager loadFromFile(File file) {
        TaskManager manager = new FileBackedTaskManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();

            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                Task task = fromString(line);
                TaskType taskType = task.getType();
                switch (taskType) {
                    case TASK -> manager.addTask(task);
                    case EPIC -> manager.addTask((Epic) task);
                    case SUBTASK -> manager.addTask((SubTask) task);
                }
            }
        } catch (IOException e) {
            throw new ManagerLoadExceptions("Ошибка при загрузке данных из файла" + e.getMessage());
        }
        return manager;
    }

    private static Task fromString(String taskFromFile) {
        String[] contents = taskFromFile.split(",");
        int taskId = Integer.parseInt(contents[0]);
        TaskType taskType = TaskType.valueOf(contents[1]);
        String taskName = contents[2];
        TaskStatus taskStatus = TaskStatus.valueOf(contents[3]);
        String taskDescription = contents[4];
        return switch (taskType) {
            case TASK -> new Task(taskId, taskName, taskDescription, taskStatus);
            case EPIC -> new Epic(taskId, taskName, taskDescription, taskStatus);
            case SUBTASK -> {
                int epicId = Integer.parseInt(contents[5]);
                yield new SubTask(taskId, taskName, taskDescription, taskStatus, epicId);
            }
        };
    }
}
