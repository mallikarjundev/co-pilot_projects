import java.io.Serializable;

public class Task implements Serializable {
    private final String id;
    private final String description;
    private final int durationSecs;

    public Task(String id, String description, int durationSecs) {
        this.id = id;
        this.description = description;
        this.durationSecs = durationSecs;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getDurationSecs() {
        return durationSecs;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", durationSecs=" + durationSecs +
                '}';
    }
}
