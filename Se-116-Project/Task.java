public class Task{
    private TaskType taskType;
    private int taskSize;
    
    public Task(TaskType taskType, int taskSize) {
        this.taskType = taskType;
        this.taskSize = taskSize;
    }
    // Setters
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    
    public void setTaskSize(int taskSize) {
        this.taskSize = taskSize;
    }
    
    // Getters
    public TaskType getTaskType() {
        return taskType;
    }
    
    public int getTaskSize() {
        return taskSize;
    }
    
}