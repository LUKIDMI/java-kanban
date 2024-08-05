package pavel.kalinkin.project;


import pavel.kalinkin.project.manager.TaskManager;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        Epic epic1 = new Epic("Epic 1", "Описание Epic 1");
        Epic epic2 = new Epic("Epic 2", "Описание Epic 2");
        SubTask subTask1 = new SubTask("Подзадача 1", "Описание подзадачи 1", TaskStatus.NEW, 3, 5);
        SubTask subTask2 = new SubTask("Подзадача 2", "Описание подзадачи 2", TaskStatus.NEW, 3, 6);
        SubTask subTask3 = new SubTask("Подзадача 3", "Описание подзадачи 3", TaskStatus.NEW, 4, 7);

        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);

        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTasks());


        manager.updateTask(new Task("Задача 1", "Обновление 1", TaskStatus.IN_PROGRESS, 1));
        manager.updateTask(new Task("Задача 2", "Обновление 1", TaskStatus.IN_PROGRESS, 2));
        manager.updateEpic(new Epic("Эпик 1", "Обновление 1", 3));
        manager.updateEpic(new Epic("Эпик 2", "Обновление 1", 4));
        manager.updateSubTask(new SubTask("Подзадача 1", "Обновление 1", TaskStatus.IN_PROGRESS, 3, 5));
        manager.updateSubTask(new SubTask("Подзадача 2", "Обновление 1", TaskStatus.IN_PROGRESS, 3, 6));
        manager.updateSubTask(new SubTask("Подзадача 3", "Обновление 1", TaskStatus.DONE, 4, 7));

        System.out.println();
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTasks());

        manager.deleteTaskById(1);
        manager.deleteEpicById(3);

        System.out.println();
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTasks());
    }
}
