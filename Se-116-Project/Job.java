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

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public int getJobDuration() {
        return duration;
    }

    public void setJobDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    
}