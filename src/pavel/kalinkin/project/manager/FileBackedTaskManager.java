package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.exceptions.ManagerLoadExceptions;
import pavel.kalinkin.project.exceptions.ManagerSaveException;
import pavel.kalinkin.project.model.*;

import java.io.*;
import java.util.Collection;
import java.util.stream.Stream;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    //Добавление задачи в менеджер
    @Override
    public Integer addTask(Task task) {
        Integer id = super.addTask(task);
        save();
        return id;
    }

    //Обновление задачи
    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    //Удалине задачи по id
    @Override
    public void deleteTaskById(int taskId) {
        super.deleteTaskById(taskId);
        save();
    }

    //Удаление всех задач
    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    //Добавление эпика в менеджер
    @Override
    public Integer addTask(Epic epic) {
        Integer id = super.addTask(epic);
        save();
        return id;
    }

    //Обновление эпика
    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    //Удаление эпика по id
    @Override
    public void deleteEpicById(int epicId) {
        super.deleteEpicById(epicId);
        save();
    }

    //Удаление всех эпиков
    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    //Добавление подзадачи в менеджер
    @Override
    public Integer addTask(SubTask subTask) {
        Integer id = super.addTask(subTask);
        save();
        return id;
    }

    //Обновление подзадачи
    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    //Удаление подзадачи по id
    @Override
    public void deleteSubTaskById(int subTaskId) {
        super.deleteSubTaskById(subTaskId);
        save();
    }

    //Удаление всех подзадач
    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    //Метод сохранение задачи в файл
    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("id,type,name,status,description,duration,startTime,endTime,epicId\n");

            Stream.of(getAllTasks(), getAllEpics(), getAllSubTasks()).flatMap(Collection::stream).map(CSVTaskFormatter::toString).forEach(line -> {
                try {
                    bw.write(line + "\n");
                } catch (IOException e) {
                    throw new ManagerSaveException("Ошибка при сохранении данных.");
                }
            });

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении данных.");
        }
    }

    //Выгрузка задач из файла
    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();

            String line;
            while ((line = br.readLine()) != null && !line.isBlank()) {
                Task task = CSVTaskFormatter.fromString(line);
                manager.restoreTask(task);

                maxId = Math.max(maxId, task.getId());
            }

            for (SubTask subTask : manager.getAllSubTasks()) {
                Epic epic = manager.epics.get(subTask.getEpicId());
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

    //Добавление задач по id в менеджер и в список, отсортированный по приоритету
    private void restoreTask(Task task) {
        switch (task.getType()) {
            case TASK -> {
                tasks.put(task.getId(), task);
                if (task.getStartTime() != null) prioritizedTasks.add(task);
            }
            case EPIC -> epics.put(task.getId(), (Epic) task);
            case SUBTASK -> {
                subTasks.put(task.getId(), (SubTask) task);
                prioritizedTasks.add(task);
            }
        }
    }
}
