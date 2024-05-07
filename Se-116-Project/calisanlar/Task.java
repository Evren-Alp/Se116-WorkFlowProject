class Task extends Job{
    private String name;
    private float size;
    private TaskType taskType;{
        
    }
    public Task(Task task){
        this.name = task.name;
        this.size = task.size;
    }
    public TaskType getTaskType() {
        return taskType;
    }
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    public Task(String name, float size, TaskType taskType){
        this.name = name;
        this.size = size;
        this.taskType = taskType;
    }public Task(String name, float size){
        this.name = name;
        this.size = size;
        this.taskType = taskType;
    }
    public Task(String name){
        this.name = name;
    }
    public Task(){

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
}