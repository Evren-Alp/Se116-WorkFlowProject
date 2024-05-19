import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Station {
    private String name;
    private int maxCapacity;
    private int capacity;
    private float idleCount=0.0f;
    private boolean multiflag;
    private boolean fifoflag;
    private float speed=1;
    private float plusminus=0.0f;
    private Task currentTask=Test.Idle;
    private ArrayList<TaskType> supportedTaskTypes=new ArrayList<>();
    Random r= new Random();
    ArrayList<Task> tasks;
    ArrayList<String> plusMinusList;
    
    public Station(String name, int maxCapacity, boolean multiflag, boolean fifoflag){
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.capacity = maxCapacity;
        this.multiflag = multiflag;
        this.fifoflag = fifoflag;
    }
    public Station(String name, boolean multiflag, boolean fifoflag){
        this.name = name;
        this.multiflag = multiflag;
        this.fifoflag = fifoflag;
    }
    public Station(){}
    public void work(Task task) {
        if (supportedTaskTypes.contains(task.getTaskType())) {
            System.out.println("Minute "+Test.tur+": "+ name +" doing job: " + task.getTaskType()+" "+"duration: " + calculateTaskDuration(task)+"");           
        } else {
            System.out.println("Unsupported task type for this station");
        }
    }
    public float calculateTaskDuration(Task task){
        // Pattern for finding plusMinus for each tasktype
        Pattern separatePlusMinus = Pattern.compile("([A-z]+\\d+)\\s+(\\d+\\.\\d+)");
        try{
            for(String item : plusMinusList){
                Matcher matcher1 = separatePlusMinus.matcher(item);
                if(matcher1.find()){
                    if(matcher1.group(1).equals(task.getTaskName())){ //If task names are equal, set plusminus value according to the list.
                        plusminus = Float.valueOf(matcher1.group(2));
                        float ran = r.nextFloat(-plusminus, plusminus);
                        if (plusminus>0){
                            return task.getTaskSize()/(speed+ran);
                        }
                    }
                }
            }
        }
        catch(NullPointerException e){
            plusminus = 0.0f;            
        }
        return task.getTaskSize() / speed;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public boolean getMultiflag(){
        return this.multiflag;
    }
    public void setMultiflag(boolean flag){
        this.multiflag = flag;
    }

    public boolean getFifoflag(){
        return this.fifoflag;
    }
    public void setFifoflag(boolean flag){
        this.fifoflag = flag;
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

    public int getMaxCapacity() {
        return maxCapacity;
    }
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed) {
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

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<String> getPlusminusList(){
        return this.plusMinusList;
    }
    public void setPlusminusList(ArrayList<String> list){
        this.plusMinusList = list;
    }

    public float getIdleCount() {
        return idleCount;
    }
    public void setIdleCount(float idleCount) {
        this.idleCount = idleCount;
    }
}
