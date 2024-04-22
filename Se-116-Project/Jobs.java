import java.util.ArrayList;

public class Jobs {
    private String jobType;
    private int duration;
    private ArrayList<Task> tasks;

    public Jobs() {
        this.jobType = "";
        this.duration = 0;
        this.tasks = new ArrayList<Task>();
    }
    
    public Jobs(String jobType, int duration, ArrayList<Task> tasks) {
        this.jobType = jobType;
        this.duration = duration;
        this.tasks = tasks;
    }
}