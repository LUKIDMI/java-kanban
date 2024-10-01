package pavel.kalinkin.project;

import pavel.kalinkin.project.manager.FileBackedTaskManager;
import pavel.kalinkin.project.manager.Managers;
import pavel.kalinkin.project.manager.TaskManager;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("src/pavel/kalinkin/project/data/data.csv");
        TaskManager manager = FileBackedTaskManager.loadFromFile(file);

        printAllTasks(manager);

    }

    private static TaskManager getTaskManager() {
        TaskManager manager = Managers.getDefault();

        Epic epic1 = new Epic("Тест", "Тест");
        Epic epic2 = new Epic("Тест", "Test");
        Task task1 = new Task("Test", "Test", TaskStatus.NEW);
        Task task2 = new Task("Test", "Test", TaskStatus.NEW);
        SubTask subTask1 = new SubTask("Test", "Test", 1);
        SubTask subTask2 = new SubTask("Test", "Test", 1);
        SubTask subTask3 = new SubTask("Test", "Test", 1);

        manager.addTask(epic1);
        manager.addTask(subTask1);
        manager.addTask(subTask2);
        manager.addTask(subTask3);
        manager.addTask(epic2);
        manager.addTask(task1);
        manager.addTask(task2);
        return manager;
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.getAllEpicSubTasks(epic.getId())) {
                System.out.println("--> " + task);
            }
        }

        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubTasks()) {
            System.out.println(subtask);
        }
    }

    public static void printHistory(TaskManager manager) {
        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
