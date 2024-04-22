import java.util.ArrayList;

public class Test {
    public static void describeJob(Job job) {
        System.out.println("Job Title: " + job.getJobType());
        System.out.println("Job Duration: " + job.getJobDuration() + " minutes");
        System.out.println("Tasks:");
        for (Task task : job.getTasks()) {
            System.out.println("\nTask: " + task.getTaskType());
            System.out.println("Task duration: " + task.getTaskSize()+" minutes");
        }
    }
    public static void main(String[] args) {
        ArrayList<TaskType> garsonAblility = new ArrayList<>();
        garsonAblility.add(TaskType.SIPARISALMA);
        garsonAblility.add(TaskType.SIPARISGOTURME);
        garsonAblility.add(TaskType.BEKLEME);
        garsonAblility.add(TaskType.HESAPGOTURME);
    ArrayList<Task> siparisAlma = new ArrayList<>();
    Task siparis = new Task(TaskType.SIPARISALMA, 5);
    Task getir = new Task(TaskType.SIPARISGOTURME,15 );
    Task bekle = new Task(TaskType.BEKLEME, 30);
    Task hesap = new Task(TaskType.HESAPGOTURME, 5);
    siparisAlma.add(siparis);
    siparisAlma.add(getir);
    siparisAlma.add(bekle);
    siparisAlma.add(hesap);
    Station Garson=new Station("Asli", garsonAblility, 3, 1.2);
    Job Garsonluk = new Job("Yeni musteri", 60, siparisAlma);
    describeJob(Garsonluk);
    Garson.work(siparis);
    }
}
