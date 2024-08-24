package pavel.kalinkin.project;


import pavel.kalinkin.project.manager.InMemoryTaskManager;
import pavel.kalinkin.project.manager.Managers;
import pavel.kalinkin.project.manager.TaskManager;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        Task task = new Task("Тест", "тест", TaskStatus.NEW);
        Epic epic  = new Epic("Тест", "Тест");

        manager.addTask(task);
        manager.addEpic(epic);

        SubTask subTask1 = new SubTask("Тест", "Тест", 2);
        SubTask subTask2 = new SubTask("Тест", "Тест", 2);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);

        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTasks());

        System.out.println("-------");
        System.out.println(manager.getEpicById(10));
        System.out.println("-------");

        printAllTasks(manager);
        ;
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.getAllEpicSubTasks(2)) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
