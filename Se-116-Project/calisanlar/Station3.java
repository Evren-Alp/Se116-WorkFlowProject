import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Station3{
    private String stationName;
    private ArrayList<TaskType> supportedTaskTypes;
    private int startingCapacity;
    private int capacity;
    private double speed;
    private Task currentTask=Test.Idle;
 
    public Station3(String stationName, ArrayList<TaskType> supportedTaskTypes, int capacity, double speed) {
        this.stationName = stationName;
        this.supportedTaskTypes = supportedTaskTypes;
        this.capacity = capacity;
        this.startingCapacity = capacity;
        this.speed = speed;
    }

    public int calculateTaskDuration(Task task) {
        return (int) (task.getTaskSize() / speed);
    }
    
    public void work(Task task) {
        if (supportedTaskTypes.contains(task.getTaskType())) {
            
            System.out.println( stationName +" doing job: " + task.getTaskType().toString().substring(0, 1).toUpperCase() + task.getTaskType().toString().substring(1).toLowerCase());
            System.out.println("duration: " + calculateTaskDuration(task));
           
        } else {
            System.out.println("Unsupported task type for this station");
        }
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public ArrayList<TaskType> getSupportedTaskTypes() {
        return supportedTaskTypes;
    }

    public void setSupportedTaskTypes(ArrayList<TaskType> supportedTaskTypes) {
        this.supportedTaskTypes = supportedTaskTypes;
    }

    public int getStartingCapacity() {
        return startingCapacity;
    }

    public void setStartingCapacity(int startingCapacity) {
        this.startingCapacity = startingCapacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }
    
}