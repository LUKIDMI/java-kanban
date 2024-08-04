package pavel.kalinkin.project.model;

public class SubTask extends Task{
    private final int epicId;

    public SubTask(String taskName, String description, int epicId) {
        super(taskName, description);
        this.epicId = epicId;
    }

    public TaskStatus getSubTaskStatus(){
        return status;
    }

    public int getEpicId(){
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epicId +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
