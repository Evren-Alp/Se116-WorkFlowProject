import java.util.ArrayList;

public class Job {
    private String jobName;
    private int duration;
    private ArrayList<Task> tasks;

    public Job() {
        this.jobName = "";
        this.duration = 0;
        this.tasks = new ArrayList<Task>();
    }
    
    public Job(String jobName, int duration, ArrayList<Task> tasks) {
        this.jobName = jobName;
        this.duration = duration;
        this.tasks = tasks;
    }

    public String getjobName() {
        return jobName;
    }

    public void setjobName(String jobName) {
        this.jobName = jobName;
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