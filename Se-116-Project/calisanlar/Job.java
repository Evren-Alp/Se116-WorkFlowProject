import java.util.ArrayList;

class Job{
    private String jobName;
    public String getJobName() {
        return jobName;
    }
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public int getJobDuration() {
        return jobDuration;
    }
    public void setJobDuration(int jobDuration) {
        this.jobDuration = jobDuration;
    }
    private int jobDuration;
    private String jobID = "";
    private ArrayList<Task> tasks;
    public Job(String jobID, ArrayList<Task> tasks){
        this.jobID = jobID;
        this.tasks = tasks;
    }
    public Job(String jobID){
        this.jobID = jobID;
    }
    public Job(){

    }
    public void addTask(Task task){
        this.tasks.add(task);
    }
    public ArrayList<Task> getTasks(){
        return this.tasks;
    }
    public void setTasks(ArrayList<Task> tasks){
        this.tasks = tasks;
    }
    public String getJobID(){
        return this.jobID;
    }
    public void setJobID(String jobID){
        this.jobID = jobID;
    }
}