import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Station{
    private String stationName;
    private ArrayList<TaskType> supportedTaskTypes;
    private int capacity;
    private double speed;
 
    public Station(String stationName, ArrayList<TaskType> supportedTaskTypes, int capacity, double speed) {
        this.stationName = stationName;
        this.supportedTaskTypes = supportedTaskTypes;
        this.capacity = capacity;
        this.speed = speed;
    }

    public int calculateTaskDuration(Task task) {
        return (int) (task.getTaskSize() / speed);
    }
    public void basla(){
        System.out.println("Station is working");
       for (int i = 0; i < Test.activeTasks.size(); ) {
        if (capacity>0 && supportedTaskTypes.contains(Test.activeTasks.get(i).getTaskType())) {
            work(Test.activeTasks.get(i));
            Test.activeTasks.remove(i);
        }
        else{
            System.out.println("Station is full or task is not supported");
            i++;
        }
        
       }
    }
    public void work(Task task) {
        if (supportedTaskTypes.contains(task.getTaskType())) {
            capacity--;
            System.out.println("doing job: "+task.getTaskType());
            System.out.println("duration: " + calculateTaskDuration(task));
            try {
                TimeUnit.SECONDS.sleep(calculateTaskDuration(task)/5); //remove /5
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("job done");
                capacity++;
            }
        } else {
            System.out.println("Unsupported task type for this station");
        }
    }
}