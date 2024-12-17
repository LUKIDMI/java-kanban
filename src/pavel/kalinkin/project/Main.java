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

