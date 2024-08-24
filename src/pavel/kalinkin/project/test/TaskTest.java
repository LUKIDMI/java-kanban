package pavel.kalinkin.project.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.model.Task;
import pavel.kalinkin.project.model.TaskStatus;


class TaskTest {

    @Test
    public void shouldReturnTrueTaskEqualsTask(){
        Task task1 = new Task("Test", "Test", TaskStatus.NEW, 1);
        Task task2 = new Task("Test", "Test", TaskStatus.NEW, 1);

        Assertions.assertTrue(task1.equals(task2));
    }
}