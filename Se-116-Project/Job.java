import java.util.ArrayList;

public class Job {
    private String jobType;
    private int duration;
    private ArrayList<Task> tasks;

    public Job() {
        this.jobType = "";
        this.duration = 0;
        this.tasks = new ArrayList<Task>();
    }
    
    public Job(String jobType, int duration, ArrayList<Task> tasks) {
        this.jobType = jobType;
        this.duration = duration;
        this.tasks = tasks;
    }
}