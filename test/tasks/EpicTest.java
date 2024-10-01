package pavel.kalinkin.project.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pavel.kalinkin.project.model.Epic;


class EpicTest {

    @Test
    public void shouldReturnTrueEpicEqualsEpic() {
        Epic epic1 = new Epic("Test", "Test", 1);
        Epic epic2 = new Epic("Test", "Test", 1);

        Assertions.assertTrue(epic1.equals(epic2));
    }
}