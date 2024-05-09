import java.util.ArrayList;

class Job{
    private String jobName;
    private float jobDuration=20;
    private String jobID = "";
    private ArrayList<Task> tasks;
    public String getJobName() {
        return jobName;
    }
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public float getJobDuration() {
        this.jobDuration = 0;
        for (Task task : tasks) {
            this.jobDuration+=task.getTaskSize();
        }
        return jobDuration;
    }
    public void setJobDuration(float jobDuration) {
        this.jobDuration = jobDuration;
    }
    public Job(String jobID, ArrayList<Task> tasks){
        for (Task task : tasks) {
            this.jobDuration+=task.getTaskSize();
        }
        this.jobID = jobID;
        this.tasks = tasks;
    }
    public void resetJobDuration(){
        this.jobDuration = 0;
        for (Task task : tasks) {
            this.jobDuration+=task.getTaskSize();
        }
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