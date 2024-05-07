import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;



public class Test {
    public static Task Idle=new Task("IDLE", 0,TaskType.T0);
    public static int tur=1;
    public static ArrayList<Station> stationList= new ArrayList<>();
    public static ArrayList<Job> jobList= Testoku.jobList;
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
        System.out.println("\nMinute: "+(tur-1)*15);
            System.out.println("jobs: ");
            for (Job job : jobList) {
                System.out.println(job.getJobID()+"/Remaining: "+job.getJobDuration());
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
        
        boolean dur=false;
        boolean uyum=false;
        while (dur==false) {
            for (Job job : jobList) {
                if (job.getTasks().isEmpty()) {
                    dur=true;
                }
            } 
            if (dur==true) {
                System.out.println("\nAll jobs are done");
                break;
            }
            for (int i = 0; i < jobList.size(); i++) {
                for (int j = 0; j < jobList.get(i).getTasks().size(); j++) {
                    for (int k = 0; k < stationList.size(); k++) {
                        if (stationList.get(k).getSupportedTaskTypes().contains(jobList.get(i).getTasks().get(j).getTaskType())) {
                            if (stationList.get(k).getCapacity() > 0&&stationList.get(k).getCurrentTask()==Idle) {
                                stationList.get(k).work(jobList.get(i).getTasks().get(j));
                                stationList.get(k).setCurrentTask(jobList.get(i).getTasks().get(j));
                                stationList.get(k).setCapacity(stationList.get(k).getCapacity() - 1);
                                jobList.get(i).setJobDuration(jobList.get(i).getJobDuration() - jobList.get(i).getTasks().get(j).getTaskSize());
                                j=0;
                                k=0;
                                i=0;
                                if (jobList.get(i).getJobDuration() <= 0) {
                                    jobList.get(i).getTasks().remove(j);
                                    stationList.get(k).setCurrentTask(Idle);
                                    stationList.get(k).setCapacity(stationList.get(k).getCapacity()+1);
                                    

                                    
                                }
                            }
                            
                        }
                        if (uyum==true){
                            uyum=false;
                            break;
                        }
                        if (uyum==false&&stationList.isEmpty()==false&&jobList.isEmpty()==false) {
                            System.out.println("no station available for task ");
                        }
                    }
                    
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
       Job job2 = new Job("job2",new ArrayList<Task>(taskler2));

   
       for(Job j: jobList){
        System.out.println("jobID: " + j.getJobID());
        for(int i = 0; i<j.getTasks().size(); i++){
            System.out.printf("TaskID: %s | Task Size: %f%n",j.getTasks().get(i).getTaskName(), j.getTasks().get(i).getTaskSize());
        }
        System.out.println();
       
    }
        
    try {
        jobList=Testoku.oku();
    } catch (FileNotFoundException e) {
        System.err.println("annen nerede?");
    }
    
        jobList.add(job1);
        jobList.add(job2);


        ArrayList<TaskType> stationAblility1 = new ArrayList<>();
        stationAblility1.add(TaskType.T1);
        stationAblility1.add(TaskType.T2);
       
        
        ArrayList<TaskType> stationAblility2 = new ArrayList<>();
        stationAblility2.add(TaskType.T3);
        stationAblility2.add(TaskType.T4);

        Station station1 = new Station("station1", stationAblility1, 1,1);
        Station station2 = new Station("station2", stationAblility2, 1, 1);
       
        stationList.add(station1);
        stationList.add(station2);
    
        for(Job j: jobList){
            System.out.println("jobID: " + j.getJobID());
            for(int i = 0; i<j.getTasks().size(); i++){
                System.out.printf("TaskID: %s | Task Size: %f%n",j.getTasks().get(i).getTaskName(), j.getTasks().get(i).getTaskSize());
            }
            System.out.println();
        }

       
       
       


        // Func Req. 1: getting the name of the workflow and the job file
      // String workflowFile = args[0];
      //  String jobFile = args[1];
    }  
}
