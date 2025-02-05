package pavel.kalinkin.project;

import pavel.kalinkin.project.manager.FileBackedTaskManager;
import pavel.kalinkin.project.interfaces.TaskManager;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\LUKIDMI\\IdeaProjects\\java-kanban\\src\\pavel\\kalinkin\\project\\data\\data.csv");
        TaskManager manager = new FileBackedTaskManager(file);

        manager.addTask(new Task("Задача", "Описание", Duration.ofHours(1), LocalDateTime.of(2022, 10, 5, 12, 30)));
        manager.addTask(new Epic("Эпик 1", "Описание"));
        manager.addTask(new SubTask("Подзадача 1", "Описание", Duration.ofHours(1), LocalDateTime.of(2022, 10, 5, 5, 30), 2));
        manager.addTask(new SubTask("Подзадача 2", "Описание", Duration.ofHours(1), LocalDateTime.of(2022, 10, 5, 7, 30), 2));
        manager.addTask(new Epic("Эпик 2", "Описание"));
        manager.addTask(new Task("Задача", "Описание"));

        printAllTasks(manager);

        System.out.println("-----------------------------------");
        System.out.println("После загрузки из файла");
        System.out.println("-----------------------------------");

        manager = FileBackedTaskManager.loadFromFile(file);

        printAllTasks(manager);

        System.out.println("-----------------------------------");
        System.out.println("Список задач по приоритету");
        System.out.println("-----------------------------------");
        manager.getPrioritizedTasks().forEach(System.out::println);

        manager.deleteSubTaskById(4);

        System.out.println("-----------------------------------");
        System.out.println("После удаления подзадачи");
        System.out.println("-----------------------------------");

        printAllTasks(manager);
        System.out.println("-----------------------------------");
        manager.getPrioritizedTasks().forEach(System.out::println);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Все задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println();

        System.out.println("Все эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);
            for (SubTask subTask : manager.getAllEpicSubTasks(epic.getId())) {
                System.out.println("  - " + subTask);
            }
        }

        System.out.println();

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

