import java.util.ArrayList;

public class Station2 {
    private String name;
    private int maxCapacity;
    private boolean multiflag;
    private boolean fifoflag;
    ArrayList<Task> tasks;
    public Station2(String name, int maxCapacity, boolean multiflag, boolean fifoflag){
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.multiflag = multiflag;
        this.fifoflag = fifoflag;
    }
    public Station2(String name, boolean multiflag, boolean fifoflag){
        this.name = name;
        this.multiflag = multiflag;
        this.fifoflag = fifoflag;
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
}
