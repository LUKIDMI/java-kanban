package pavel.kalinkin.project.exceptions;

public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException() {
        super("Ошибка сохранения");
    }

    public ManagerSaveException(String message) {
        super(message);
    }
}
