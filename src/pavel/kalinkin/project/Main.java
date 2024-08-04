package pavel.kalinkin.project;


import pavel.kalinkin.project.manager.TaskManager;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();



        Task task1 = new pavel.kalinkin.project.model.Task("Зачада 1", "Описание 1");
        Task task2 = new pavel.kalinkin.project.model.Task("Задача 2", "Описание 2");
        Epic epic1 = new pavel.kalinkin.project.model.Epic("pavel.kalinkin.project.model.Epic 1", "Описание 1");
        Epic epic2 = new pavel.kalinkin.project.model.Epic("pavel.kalinkin.project.model.Epic 2", "Описание 2");
        SubTask subTask1 = new pavel.kalinkin.project.model.SubTask("Подзадача 1", "Описание 1", epic1.getId());
        SubTask subTask2 = new pavel.kalinkin.project.model.SubTask("Подзадача 2", "Описание 2", epic1.getId());
        SubTask subTask3 = new pavel.kalinkin.project.model.SubTask("Подзадача 3", "Описание 3", epic2.getId());

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

        manager.updateTask(task1);
        manager.updateTask(task2);
        manager.updateEpic(epic1);
        manager.updateEpic(epic2);
        manager.updateSubTask(subTask1);
        manager.updateSubTask(subTask2);
        manager.updateSubTask(subTask3);

        System.out.println();
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTasks());

        manager.updateSubTask(subTask1);
        manager.updateSubTask(subTask3);

        System.out.println();
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTasks());

        manager.deleteTaskById(1);
        manager.deleteEpicById(4);

        System.out.println();
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTasks());

        manager.updateTask(task2);
        manager.updateSubTask(subTask1);
        manager.updateSubTask(subTask2);

        System.out.println();
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTasks());
        System.out.println(manager.getAllEpicSubTasks(epic1));
    }
}
