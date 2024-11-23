package pavel.kalinkin.project.exceptions;


public class ManagerLoadExceptions extends RuntimeException {

    public ManagerLoadExceptions(String message) {
        super(message);
    }

    public ManagerLoadExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
