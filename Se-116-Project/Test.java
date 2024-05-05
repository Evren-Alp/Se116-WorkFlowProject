import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class Test {
    public static Task Idle=new Task(TaskType.IDLE, 0);
    public static int tur=1;
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
        System.out.println("\nMinute: "+(tur-1)*15);
            System.out.println("jobs: ");
            for (Job job : jobList) {
                System.out.println(job.getjobName()+"/"+job.getStatus()+"/Remaining: "+job.getJobDuration());
            }
            System.out.print("\nStations: ");
            for (Station station : stationList) {
                String status="";
                if (station.getCurrentTask()==null) {
                    status="Idle";
                 }
                else if (station.getCapacity()==station.getStartingCapacity()) {
                    status="Idle"+"/finished"+station.getCurrentTask().getTaskType();
                }
                
                else if (station.getCurrentTask()!=null) status=("Working on " + station.getCurrentTask().getTaskType()+"/"+station.getCurrentTask().getTaskSize()+" minutes remaining");
                    
                
                System.out.print("  "+station.getStationName()+"/"+status+"   ");
            }
    }
    public static void basla() {
        int turcapacity = 0;
        yaz();
        tur = 1;
        int uyum=0;
        while (activeTasks.isEmpty()==false) {
            turcapacity = 0;
            for (Station station : stationList) {
                turcapacity += station.getCapacity();
            }
            for (int i = 0; i < turcapacity; ) {
                for (int j = 0; j < stationList.size();) {
                    if (activeTasks.isEmpty()==true) {
                        break;
                        
                    }
                    if (stationList.get(j).getSupportedTaskTypes().contains(activeTasks.get(i).getTaskType()) && stationList.get(j).getCapacity()+1 > 0) {
                        stationList.get(j).setCapacity(stationList.get(j).getCapacity()-1);
                        stationList.get(j).setCurrentTask(new Task (activeTasks.get(i)));
                        
                        activeTasks.remove(i);
                        i=0;
                        j=0;
                        uyum=1;
                        turcapacity--;
                        break;
                    }
                    else{
                        j++;
                    } 
                }
                if (uyum==0) {
                    System.out.println("No station available for the task");
                    i++;
            
                }
                if (uyum==1) {
                    uyum=0;
                }
            }
            for (Station station : stationList) {
                if (station.getCurrentTask().getTaskSize()<=15) {
                    station.setCapacity(station.getCapacity()+1);
                   

                }
                else{
                    station.getCurrentTask().setTaskSize(station.getCurrentTask().getTaskSize()-15);
                }
            }
            tur++;
            yaz();
            
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
        Task Idle = new Task(TaskType.IDLE, 0);
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
        
        ArrayList<TaskType> mutfakAblility = new ArrayList<>();
        mutfakAblility.add(TaskType.DOGRAMA);
        mutfakAblility.add(TaskType.FIRINLAMA);
        mutfakAblility.add(TaskType.HASLAMA);
        mutfakAblility.add(TaskType.KIZARTMA);
        mutfakAblility.add(TaskType.TABAKLAMA);

        ArrayList<Task> siparisAlmaTasks = new ArrayList<>();
        siparisAlmaTasks.add(siparis);
        siparisAlmaTasks.add(getir);
        siparisAlmaTasks.add(bekle);
        siparisAlmaTasks.add(hesap);
    
        ArrayList<Task> kizartmaTasks = new ArrayList<>();
        kizartmaTasks.add(dograma);
        kizartmaTasks.add(kizartma);
        kizartmaTasks.add(tabaklama);

        Station Garson=new Station("Asli", garsonAblility, 1, 1.0);
        Station Mutfak=new Station("Mutfak", mutfakAblility, 1, 1.0);
        Job YeniMusteri = new Job("Yeni musteri", 60, siparisAlmaTasks);
        Job PatatesKizartmasi = new Job("Patates Kizartmasi", 35, kizartmaTasks);
        ekle(PatatesKizartmasi);
        ekle(YeniMusteri);
    
        jobList.add(YeniMusteri);
        jobList.add(PatatesKizartmasi);
        stationList.add(Garson);
        stationList.add(Mutfak);
        basla();


        // Func Req. 1: getting the name of the workflow and the job file
      //  String workflowFile = args[0];
        //String jobFile = args[1];
    }  
}
