import java.util.List;

public class Station{
    private String stationName;
    private List<String> supportedTaskTypes;
    private int capacity;
    private double speed;

    public Station(String stationName, List<String> supportedTaskTypes, int capacity, double speed) {
        this.stationName = stationName;
        this.supportedTaskTypes = supportedTaskTypes;
        this.capacity = capacity;
        this.speed = speed;
    }

    public int calculateTaskDuration(int taskSize) {
        return (int) Math.ceil(taskSize / speed);
    }
}