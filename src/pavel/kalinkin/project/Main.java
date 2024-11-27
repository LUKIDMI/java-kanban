package pavel.kalinkin.project;

import pavel.kalinkin.project.manager.FileBackedTaskManager;
import pavel.kalinkin.project.manager.TaskManager;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("data.csv");
        TaskManager manager = new FileBackedTaskManager(file);

        Task task1 = new Task("Task 1", "Description 1", TaskStatus.NEW);
        Task task2 = new Task("Task 2", "Description 2", TaskStatus.IN_PROGRESS);
        Epic epic = new Epic("Epic 1", "Epic Description");
        SubTask subTask1 = new SubTask("SubTask 1", "SubDescription", 3);

        manager.addTask(task1);
        manager.addTask(task2);
        int epicId = manager.addTask(epic);
        subTask1.setId(manager.addTask(subTask1));

        printAllTasks(manager);

        System.out.println("История задач:");
        printHistory(manager);
        manager.deleteTaskById(1);

        System.out.println("--------------------------------------------------------");
        System.out.println("Ресет.");
        System.out.println("--------------------------------------------------------");
        manager = FileBackedTaskManager.loadFromFile(file);
        Task task3 = new Task("Task 3", "Description 3", TaskStatus.NEW);
        manager.addTask(task3);
        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Все задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Все эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);
            for (SubTask subTask : manager.getAllEpicSubTasks(epic.getId())) {
                System.out.println("  - " + subTask);
            }
        }

        System.out.println("Все подзадачи:");
        for (SubTask subTask : manager.getAllSubTasks()) {
            System.out.println(subTask);
        }
    }

    private static void printHistory(TaskManager manager) {
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}

