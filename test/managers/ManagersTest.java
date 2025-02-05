package managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.interfaces.HistoryManager;
import pavel.kalinkin.project.interfaces.TaskManager;
import pavel.kalinkin.project.manager.*;

class ManagersTest {

    @Test
    void shouldReturnDefaultInMemoryTaskManager() {
        TaskManager taskManager = Managers.getDefault();

        Class<? extends TaskManager> managerClass = taskManager.getClass();
        boolean isInMemoryTaskManager = managerClass == InMemoryTaskManager.class;

        Assertions.assertNotNull(taskManager, "Метод getDefault не возвращает менеджера.");
        Assertions.assertTrue(isInMemoryTaskManager, "Метод getDefault возвращает менеджера неправильного класса.");
    }

    @Test
    void shouldReturnDefaultHistory() {
        HistoryManager historyManager = Managers.getDefaultHistory();

        Class<? extends HistoryManager> managerClass = historyManager.getClass();
        boolean isHistoryManager = managerClass == InMemoryHistoryManager.class;

        Assertions.assertNotNull(historyManager, "Метод getDefault не возвращает менеджера.");
        Assertions.assertTrue(isHistoryManager, "Метод getDefault возвращает менеджера неправильного класса.");
    }
}