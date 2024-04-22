import java.util.ArrayList;

public class Station{
    private String stationName;
    private ArrayList<TaskType> supportedTaskTypes;
    private int capacity;
    private double speed;
    private boolean canDoMultipleActions;
 
    public Station(String stationName, ArrayList<TaskType> supportedTaskTypes, int capacity, double speed) {
        this.stationName = stationName;
        this.supportedTaskTypes = supportedTaskTypes;
        this.capacity = capacity;
        this.speed = speed;
    }

    public int calculateTaskDuration(Task task) {
        return (int) (task.getTaskSize() / speed);
    }
    public void work(Task task){
        if (supportedTaskTypes.contains(task.getTaskType())) {
            // Perform the task
            System.out.println("doing job");
            System.out.println("duration: "+calculateTaskDuration(task));
        
        } else {
            System.out.println("Unsupported task type for this station");
        }

    }
}