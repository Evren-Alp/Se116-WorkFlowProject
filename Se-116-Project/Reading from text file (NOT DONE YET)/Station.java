import java.util.ArrayList;
import java.util.Random;

public class Station {
    private String name;
    private int maxCapacity;
    private int capacity;

    private boolean multiflag;
    private boolean fifoflag;
    private double speed=1;
    private float plusminus=0.2f;
    private Task currentTask=Test.Idle;
    Random r= new Random();
    private ArrayList<TaskType> supportedTaskTypes=new ArrayList<>();
    ArrayList<Task> tasks;
    public Station(String name, int maxCapacity, boolean multiflag, boolean fifoflag){//remove tasktype
        this.supportedTaskTypes.add(TaskType.T0);
        this.supportedTaskTypes.add(TaskType.T1);
        this.supportedTaskTypes.add(TaskType.T2);
        this.supportedTaskTypes.add(TaskType.T3);
        this.supportedTaskTypes.add(TaskType.T4);
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.capacity = maxCapacity;
        this.multiflag = multiflag;
        this.fifoflag = fifoflag;
    }
    public Station(String name, boolean multiflag, boolean fifoflag){
        this.supportedTaskTypes.add(TaskType.T0);
        this.supportedTaskTypes.add(TaskType.T1);
        this.supportedTaskTypes.add(TaskType.T2);
        this.supportedTaskTypes.add(TaskType.T3);
        this.supportedTaskTypes.add(TaskType.T4);
        this.name = name;
        this.multiflag = multiflag;
        this.fifoflag = fifoflag;
    }
    public void work(Task task) {
        if (supportedTaskTypes.contains(task.getTaskType())) {
            
            System.out.println("Minute "+Test.tur+": "+ name +" doing job: " + task.getTaskType()+" "+"duration: " + calculateTaskDuration(task)+"");           
          
           
        } else {
            System.out.println("Unsupported task type for this station");
        }
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public void setMultiflag(boolean flag){
        this.multiflag = flag;
    }
    public boolean getMultiflag(){
        return this.multiflag;
    }

    public void setFifoflag(boolean flag){
        this.fifoflag = flag;
    }
    public boolean getFifoflag(){
        return this.fifoflag;
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }
    public void setTasksList(ArrayList<Task> tasksList){
        this.tasks = tasksList;
    }
    public ArrayList<Task> getTasks(){
        return this.tasks;
    }
    public int calculateTaskDuration(Task task) {
        float ran=r.nextFloat(-plusminus, plusminus);
        
        if (plusminus>0){
           
                return (int) (task.getTaskSize() / (speed+ran));
            
    }
        if (plusminus==0.0){
            return (int) (task.getTaskSize() / speed);
        }
       return 0;
        
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public Task getCurrentTask() {
        return currentTask;
    }
    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }
    public ArrayList<TaskType> getSupportedTaskTypes() {
        return supportedTaskTypes;
    }
    public void setSupportedTaskTypes(ArrayList<TaskType> supportedTaskTypes) {
        this.supportedTaskTypes = supportedTaskTypes;
    }
    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
