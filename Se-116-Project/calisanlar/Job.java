import java.util.ArrayList;

class Job{
    private float jobDuration=20;
    private String jobID = "";
    private ArrayList<Task> tasks;
    
    public Job(String jobID, ArrayList<Task> tasks){
        for (Task task : tasks) {
            this.jobDuration+=task.getTaskSize();
        }
        this.jobID = jobID;
        this.tasks = tasks;
    }
    public Job(String jobID){
        this.jobID = jobID;
    }
    public Job(){}
    public void resetJobDuration(){
        this.jobDuration = 0;
        for (Task task : tasks) {
            this.jobDuration+=task.getTaskSize();
        }
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