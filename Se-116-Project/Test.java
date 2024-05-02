import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class Test {
    public static int tur;
    public static ArrayList<Task> activeTasks= new ArrayList<>();
    public static ArrayList<Station> stationList= new ArrayList<>();
    public static ArrayList<Job> jobList= new ArrayList<>();
    public static void describeJob(Job job) {
        System.out.println("Job Title: " + job.getjobName());
        System.out.println("Job Duration: " + job.getJobDuration() + " minutes");
        System.out.println("Tasks:");
        for (Task task : job.getTasks()) {
            System.out.println("\nTask: " + task.getTaskType());
            System.out.println("Task duration: " + task.getTaskSize()+" minutes");
        }
    }

    public static void ekle(Job job){
        ArrayList<Task> is=job.getTasks();
        activeTasks.addAll(is);



    }
    public static void displayActiveTasks(){
        if (activeTasks.size()<1){
            System.out.println("active task is empty");
        }
        if (activeTasks.size()>1) {
            for (int i = 0; i < activeTasks.size(); i++) {
                System.out.println(activeTasks.get(i).getTaskType());
                
            }
        }
       
    }
    public static void yaz(){
        System.out.println("\nMinute: "+tur);
            System.out.println("jobs: ");
            for (Job job : jobList) {
                System.out.println(job.getjobName()+"/"+job.getStatus()+"/Remaining: "+job.getJobDuration());
            }
            System.out.print("\nStations: ");
            for (Station station : stationList) {
                String status="";
                if (station.getCapacity()==station.getStartingCapacity()) {
                    status="Idle";
                }
                else{
                    status="Working";
                    
                }
                System.out.print("  "+station.getStationName()+"/"+status+"   ");
            }
    }
    public static void basla(){ //evren
        tur=0;
        while(activeTasks.size()>0){
            
            



            tur++;
        }
    }
    //write method??
    public static void writeToFile(String content) {
        try {
            File file = new File("output.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(content + "\n");
            writer.close();
            System.out.println("Content written to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        
        Task dograma = new Task(TaskType.DOGRAMA, 3); 
        Task firinlama = new Task(TaskType.FIRINLAMA, 20); 
        Task haslama = new Task(TaskType.HASLAMA, 20); 
        Task kizartma = new Task(TaskType.KIZARTMA, 10);
        Task tabaklama = new Task(TaskType.TABAKLAMA, 5); 
        Task siparis = new Task(TaskType.SIPARISALMA, 5);
        Task getir = new Task(TaskType.SIPARISGOTURME,15 );
        Task bekle = new Task(TaskType.BEKLEME, 30);
        Task hesap = new Task(TaskType.HESAPGOTURME, 5);

        ArrayList<TaskType> garsonAblility = new ArrayList<>();
        garsonAblility.add(TaskType.SIPARISALMA);
        garsonAblility.add(TaskType.SIPARISGOTURME);
        garsonAblility.add(TaskType.BEKLEME);
        garsonAblility.add(TaskType.HESAPGOTURME);
        garsonAblility.add(TaskType.MASASILME);
        garsonAblility.add(TaskType.ICECEKGETIRME);

    ArrayList<Task> siparisAlmaTasks = new ArrayList<>();
        siparisAlmaTasks.add(siparis);
        siparisAlmaTasks.add(getir);
        siparisAlmaTasks.add(bekle);
        siparisAlmaTasks.add(hesap);
    
    ArrayList<Task> kizartmaTasks = new ArrayList<>();
        kizartmaTasks.add(dograma);
        kizartmaTasks.add(kizartma);
        kizartmaTasks.add(tabaklama);

    Station Garson=new Station("Asli", garsonAblility, 3, 1.0);
    Station Mutfak=new Station("Mutfak", garsonAblility, 3, 1.0);
    Job YeniMusteri = new Job("Yeni musteri", 60, siparisAlmaTasks);
    Job PatatesKizartmasi = new Job("Patates Kizartmasi", 35, kizartmaTasks);
    ekle(PatatesKizartmasi);
    ekle(YeniMusteri);
    displayActiveTasks();
    jobList.add(YeniMusteri);
    jobList.add(PatatesKizartmasi);
    stationList.add(Garson);
    stationList.add(Mutfak);
    basla();
    }  
}
