package pavel.kalinkin.project.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pavel.kalinkin.project.model.Epic;
import pavel.kalinkin.project.model.SubTask;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    @Test
    public void shouldReturnTrueSubTaskEqualsSubTask(){
        Epic epic = new Epic("Test", "Test", 1);
        SubTask subTask1 = new SubTask("Test", "Test", 1);
        SubTask subTask2 = new SubTask("Test", "Test", 2);

        Assertions.assertTrue(subTask1.equals(subTask2));
    }


}