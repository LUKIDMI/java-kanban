package pavel.kalinkin.project.interfaces;

import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;

import java.util.List;
import java.util.Set;


public interface TaskManager {

    List<Task> getAllTasks();

    void deleteAllTasks();

    Task getTaskById(int taskId);

    Integer addTask(Task task);

    void updateTask(Task task);

    void deleteTaskById(int taskId);

    List<Epic> getAllEpics();

    void deleteAllEpics();

    Epic getEpicById(int epicId);

    Integer addTask(Epic epic);

    void updateEpic(Epic epic);

    void deleteEpicById(int epicId);

    List<SubTask> getAllEpicSubTasks(int epicId);

    List<SubTask> getAllSubTasks();

    void deleteAllSubTasks();

    SubTask getSubTaskById(int subTaskId);

    Integer addTask(SubTask subTask);

    void updateSubTask(SubTask updSubTask);

    void deleteSubTaskById(int subTaskId);

    List<Task> getHistory();

    Set<Task> getPrioritizedTasks();
}
