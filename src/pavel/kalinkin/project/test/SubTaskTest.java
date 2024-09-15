package pavel.kalinkin.project.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;

class SubTaskTest {
    @Test
    public void shouldReturnTrueSubTaskEqualsSubTask() {
        SubTask subTask1 = new SubTask("Test", "Test", 1);
        SubTask subTask2 = new SubTask("Test", "Test", 2);

        Assertions.assertEquals(subTask1, subTask2);
    }
}