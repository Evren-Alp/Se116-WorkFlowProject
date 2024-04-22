import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
    ArrayList<Task> nejat = new ArrayList<>();
    Task task1 = new Task("pisirme", 15);
    Task task2 = new Task("dograma", 15);
    Task task3 = new Task("kesme", 15);
    nejat.add(task1);
    nejat.add(task2);
    nejat.add(task3);
    Job jobs = new Job("dikis", 60, nejat);
    }
}
