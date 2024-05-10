import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;




public class Test {
    public static String workflowFile="";
    public static ArrayList<Task> activeTasks=new ArrayList<>();
    public static Task Idle=new Task("IDLE", 0,TaskType.T0);
    public static int tur=1;
    public static ArrayList<Station> stationList= new ArrayList<>();
    public static ArrayList<Job> jobList;
    public static void describeJob(Job job) {
        System.out.println("Job ID: " + job.getJobID());
        System.out.println("Job Duration: " + job.getJobDuration() + " minutes");
        System.out.println("Tasks:");
        for (Task task : job.getTasks()) {
            System.out.println("\nTask: " + task.getTaskType());
            System.out.println("Task duration: " + task.getTaskSize()+" minutes");
        }
    }

    



    
    public static void displayActiveTasks(){
        if (jobList.isEmpty()){
            System.out.println("active task is empty");
        }
        if (jobList.isEmpty()==false) {
            for (Job job : jobList) {
                for (int i = 0; i < jobList.size(); i++) {
                    System.out.println(job.getTasks().get(i).getTaskType());
                    
                }
            }
        }
       
    }
    public static void yaz(){
        
            System.out.println("jobs: ");
            for (Job job : jobList) {
                System.out.println(job.getJobID()+"/Remaining: "+job.getJobDuration());
            }
      
                
                
            
    }
    public static void basla() {
        boolean uyum=false;
        tur = 1;
        boolean bothIdle=false;
        yaz();
       while (activeTasks.isEmpty()==false||bothIdle==false) {
        uyum=false;
        
        
        
        for (Station station : stationList) {

            for (Task task : activeTasks) {
                if (station.getSupportedTaskTypes().contains(task.getTaskType()) && station.getCurrentTask().getTaskType()==TaskType.T0) {
                    station.work(task);
                    workflowFile+="\n"+ task.getTaskType()+" "+tur+" ";
                    station.setCurrentTask(task);
                    activeTasks.remove(task);
                    uyum=true;
                    break;
                }
                else {
                    uyum=true;
                    break;
                }
            } 
            if (uyum==true) {
                break;
            }
           
         }
         //her tur
         
         for (Station station : stationList) {
            station.getCurrentTask().setTaskSize(station.getCurrentTask().getTaskSize()-1);
            if (station.getCurrentTask().getTaskSize()==0) {
                workflowFile+=tur;
                System.out.print("Minute "+tur+": "+station.getName()+" finished "+station.getCurrentTask().getTaskType());
                yaz();
                station.setCurrentTask(Idle);
                break;
            }
         }
         for(Station station:stationList){
            if (station.getCurrentTask().getTaskType()==TaskType.T0) {
                bothIdle=true;
            }
            else{
                bothIdle=false;
                break;
            }
            
         }
         tur++;
         
        } 
        System.out.println("All jobs are done");    
        
        
    }
    //write method??
    public static void writeToFile(String content) {
        try {
            File file = new File("Se-116-Project\\calisanlar\\output.txt");
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
        File file = new File("Se-116-Project\\calisanlar\\Workflow.txt");
        System.out.println(file.getAbsolutePath());
        
        ArrayList<Task> taskler1=new ArrayList<>();
        ArrayList<Task> taskler2=new ArrayList<>(); 
        Task task1 = new Task("task1", 15, TaskType.T1);
        Task task2 = new Task("task2", 30, TaskType.T2);
        Task task3 = new Task("task3", 45, TaskType.T3);
        Task task4 = new Task("task4", 60, TaskType.T4);
        
        taskler1.add(task1);
        taskler1.add(task2);
        taskler2.add(task3);
        taskler2.add(task4);

       Job job1 = new Job("job1", new ArrayList<Task>(taskler1) );
     

   
       
        
    
        Testoku t = new Testoku("Se-116-Project\\calisanlar\\Workflow.txt");
       /*  jobList=t.getJobList();*/
       jobList = new ArrayList<>();
    
        Job job2 = new Job("job2",new ArrayList<Task>(taskler2));
        jobList.add(job1);
        jobList.add(job2);


        ArrayList<TaskType> stationAblility1 = new ArrayList<>();
        stationAblility1.add(TaskType.T1);
        stationAblility1.add(TaskType.T2);
       
        
        ArrayList<TaskType> stationAblility2 = new ArrayList<>();
        stationAblility2.add(TaskType.T3);
        stationAblility2.add(TaskType.T4);

        
        Station station1 = new Station("Station1",  3,   true, false);
        Station station2 = new Station("Station2",  2,   true, false);
       
        stationList.add(station1);
        stationList.add(station2);
    
       
        for (Job job : jobList) {
            activeTasks.addAll(job.getTasks());
        }
        basla();
        System.out.println(workflowFile);
        writeToFile(workflowFile);
       
       
       


        // Func Req. 1: getting the name of the workflow and the job file
      // String workflowFile = args[0];
      //  String jobFile = args[1];
    }  
}
