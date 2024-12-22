package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.interfaces.HistoryManager;
import pavel.kalinkin.project.interfaces.TaskManager;

import java.io.File;

public class Managers {
    private Managers() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefaultFileBacked(File file) {
        return new FileBackedTaskManager(file);
    }
}
