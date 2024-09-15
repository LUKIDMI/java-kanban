package pavel.kalinkin.project;

import pavel.kalinkin.project.manager.Managers;
import pavel.kalinkin.project.manager.TaskManager;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = getTaskManager();


        manager.getEpicById(1);
        manager.getSubTaskById(2);
        manager.getSubTaskById(3);
        manager.getSubTaskById(4);
        manager.getEpicById(5);
        manager.getTaskById(6);
        manager.getTaskById(7);

        printHistory(manager);
        System.out.println();

        manager.getEpicById(5);
        manager.getTaskById(6);
        manager.getTaskById(7);
        manager.getEpicById(1);
        manager.getSubTaskById(2);
        manager.getSubTaskById(3);
        manager.getSubTaskById(4);

        printHistory(manager);
        System.out.println();

        manager.getTaskById(7);
        manager.getEpicById(1);
        manager.getEpicById(5);
        manager.getSubTaskById(4);
        manager.getSubTaskById(2);
        manager.getSubTaskById(3);
        manager.getTaskById(6);

        printHistory(manager);
        System.out.println();

        manager.deleteTaskById(6);

        printHistory(manager);
        System.out.println();

        manager.deleteEpicById(1);

        printHistory(manager);
        System.out.println();
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

        manager.addEpic(epic1);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);
        manager.addEpic(epic2);
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
