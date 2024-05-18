class Task{
    private String name;
    private float size;
    private TaskType taskType;
    private int startingTime; 
    private String file="";   
        
    
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
    public void setTaskType(String t) {
        if(t.equals("T0")){
            this.taskType = taskType.T0;
        }
        else if(t.equals("T1")){
            this.taskType = taskType.T1;
        }
        else if(t.equals("T2")){
            this.taskType = taskType.T2;
        }
        else if(t.equals("T3")){
            this.taskType = taskType.T3;
        }
        else if(t.equals("T4")){
            this.taskType = taskType.T4;
        }
        else if(t.equals("T5")){
            this.taskType = taskType.T5;
        }
        else if(t.equals("T6")){
            this.taskType = taskType.T6;
        }
        else if(t.equals("T7")){
            this.taskType = taskType.T7;
        }
        else if(t.equals("T8")){
            this.taskType = taskType.T8;
        }
        else if(t.equals("T9")){
            this.taskType = taskType.T9;
        }
        else if(t.equals("T10")){
            this.taskType = taskType.T10;
        }
        else if(t.equals("T11")){
            this.taskType = taskType.T11;
        }
        else if(t.equals("T12")){
            this.taskType = taskType.T12;
        }
        else if(t.equals("T13")){
            this.taskType = taskType.T13;
        }
        else if(t.equals("T14")){
            this.taskType = taskType.T14;
        }
        else if(t.equals("T15")){
            this.taskType = taskType.T15;
        }
        else if(t.equals("T16")){
            this.taskType = taskType.T16;
        }
        else if(t.equals("T17")){
            this.taskType = taskType.T17;
        }
        else if(t.equals("T18")){
            this.taskType = taskType.T18;
        }
        else if(t.equals("T19")){
            this.taskType = taskType.T19;
        }
        else if(t.equals("T20")){
            this.taskType = taskType.T20;
        }
        else if(t.equals("T21")){
            this.taskType = taskType.T21;
        }
        else if(t.equals("T22")){
            this.taskType = taskType.T22;
        }
        else if(t.equals("T23")){
            this.taskType = taskType.T23;
        }
        else if(t.equals("T24")){
            this.taskType = taskType.T24;
        }
        else if(t.equals("T25")){
            this.taskType = taskType.T25;
        }
        else if(t.equals("T26")){
            this.taskType = taskType.T26;
        }
        else if(t.equals("T27")){
            this.taskType = taskType.T27;
        }
        else if(t.equals("T28")){
            this.taskType = taskType.T28;
        }
        else if(t.equals("T29")){
            this.taskType = taskType.T29;
        }
        else if(t.equals("T30")){
            this.taskType = taskType.T30;
        }
        else{
            System.err.println("Wrong tasktype.\nTerminating...");
            System.out.println(t);
            System.exit(1);
        }
    }
    public Task(String name, float size, TaskType taskType){
        this.name = name;
        this.size = size;
        this.taskType = taskType;
    }public Task(String name, float size){
        this.name = name;
        this.size = size;
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