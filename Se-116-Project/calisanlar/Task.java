import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Task{
    private String name;
    private float size;
    private TaskType taskType;
    private int startingTime; 
    private String file="";   
    
    public Task(String name, float size, TaskType taskType){
        this.name = name;
        this.size = size;
        this.taskType = taskType;
    }
    public Task(String name, float size){
        this.name = name;
        this.size = size;
    }
    public Task(Task task){
        this.name = task.getTaskName();
        this.size = task.getTaskSize();
        this.taskType = task.getTaskType();
    }
    public Task(String name){
        this.name = name;
    }
    public Task(){}
    public TaskType getTaskType() {
        return taskType;
    }
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    public void setTaskType(String t) {
        // Tries setting the enum value, if the value doesn't exist in the enum, it will throw an error and terminate the program.
        try{
            this.taskType = TaskType.valueOf(t);
        }
        catch(IllegalArgumentException e){
            Pattern p = Pattern.compile("TaskType\\.([A-z0-9_. ]+)");
            Matcher m = p.matcher(e.getMessage());
            String invalidTaskID = "";
            if(m.find()){
                invalidTaskID = m.group(1);
            }
            System.err.println("Not a valid TaskType: " + invalidTaskID + "\nTerminating...");
            System.exit(1);
        }
    }

    public String getTaskName(){
        return this.name;
    }
    public void setTaskName(String name){
        this.name = name;
    }

    public float getTaskSize(){
        return this.size;
    }
    public void setTaskSize(float size){
        this.size = size;
    }

    public int getStartingTime() {
        return startingTime;
    }
    public void setStartingTime(int startingTime) {
        this.startingTime = startingTime;
    }

    public String getFile() {
        return file;
    }
    public void setFile(String file) {
        this.file += file;
    }
}