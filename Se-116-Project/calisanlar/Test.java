import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;




public class Test {
    public static String workflowFile="";
    public static ArrayList<Task> allTasks = new ArrayList<>();
    public static ArrayList<Task> activeTasks = new ArrayList<>();
    public static Task Idle = new Task("IDLE", -20,TaskType.T0);
    public static int tur = 1;
    public static ArrayList<Station> stationList = new ArrayList<>();
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

    



    
    
    public static void yaz(){
        
            System.out.println("State of jobs at minute "+tur+": ");
            for (Job job : jobList) {
                System.out.println(job.getJobID()+"/Remaining: "+job.getJobDuration());
            }    
    }
    public static void basla() {
        tur = 1;
        boolean Working=false;
        yaz();
        while (activeTasks.isEmpty()==false||Working==true) {
           if (tur%5==0) {
            yaz(); 
            }
            
            for (Station station : stationList) {
                for (Task task : activeTasks) {
                    if (station.getSupportedTaskTypes().contains(task.getTaskType()) && station.getCurrentTask().getTaskType()==TaskType.T0) {
                        station.work(task);
                        task.setStartingTime(tur);
                        
                        
                        station.setCurrentTask(task);
                        activeTasks.remove(task);
                        station.getCurrentTask().setTaskSize(station.calculateTaskDuration(task));
                        for (Job job : jobList) {
                            if (job.getTasks().contains(station.getCurrentTask())){
                                station.getCurrentTask().setFile("\n"+job.getJobID()+" ");;
                                break;
                            }
                        }
                        station.getCurrentTask().setFile(task.getTaskType()+" "+tur+" ");
                        break;
                    }
                    
                }

                
                
            }
            //her tur
            for (Station station : stationList) {
                station.getCurrentTask().setTaskSize(station.getCurrentTask().getTaskSize()-1);
                if ((station.getCurrentTask().getTaskSize()==0)&&station.getCurrentTask().getTaskType()!=TaskType.T0) {
                
                    station.getCurrentTask().setFile(""+(tur-station.getCurrentTask().getStartingTime()+1));
                    System.out.println("Minute "+(tur+1)+": "+station.getName()+" finished "+station.getCurrentTask().getTaskType());
                    
                    station.setCurrentTask(Idle);
                    
                   
                }
                else if ((station.getCurrentTask().getTaskSize()>-0.5)&&(station.getCurrentTask().getTaskSize()<=0)&&station.getCurrentTask().getTaskType()!=TaskType.T0) {
                
                    station.getCurrentTask().setFile(""+(tur-station.getCurrentTask().getStartingTime()+1));
                    System.out.println("Minute "+(tur)+": "+station.getName()+" finished "+station.getCurrentTask().getTaskType());
                    
                    station.setCurrentTask(Idle);
                    
                   
                }
                else if ((station.getCurrentTask().getTaskSize()>-1)&&(station.getCurrentTask().getTaskSize()<=-0.5)&&station.getCurrentTask().getTaskType()!=TaskType.T0) {
                
                    station.getCurrentTask().setFile(""+(tur-station.getCurrentTask().getStartingTime()+1));
                    System.out.println("Minute "+(tur+1)+": "+station.getName()+" finished "+station.getCurrentTask().getTaskType());
                    
                    station.setCurrentTask(Idle);
                    
                   
                }
            }
            for(Station station:stationList){
                if (station.getCurrentTask().getTaskType()==TaskType.T0) {
                    Working=false;
                }
                else{
                    Working=true;
                    break;
                }
            }
            tur++;
            if (activeTasks.isEmpty()&&Working==false) {
                break;
                
            }
        } 
        System.out.println("All jobs are done");  
        
        for(Task task:allTasks){
       
            workflowFile+=task.getFile();
        } 
    }
    //write method??
    public static void writeToFile(String content, String outputPath) {
        try {
            File file = new File(outputPath);
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
        // File file = new File("Se-116-Project\\calisanlar\\Workflow.txt");
        // System.out.println(file.getAbsolutePath());
        

        // Func Req. 1: Getting input from user for file paths
        String inputFilePath = "";
        String outputFilePath = "";
        Scanner sc = new Scanner(System.in);
        if(args.length == 2){
            inputFilePath = args[0];
            outputFilePath = args[1];
        }
        else{
            System.out.print("Enter input file path: ");
            inputFilePath = sc.nextLine();
            System.out.print("Enter output file path: ");
            outputFilePath = sc.nextLine();
        }
        sc.close();

        // Func Req. 2: Readin the input file
        Testoku t = new Testoku(inputFilePath);

        stationList.addAll(t.getStations());

        // Listing all of the tasks from all of the jobs
        jobList = t.getJobList();
        for (Job job : jobList) {
            activeTasks.addAll(job.getTasks());
            // Writing jobs and their tasks (ONLY FOR DEBUGGING)
          
        }
        allTasks.addAll(activeTasks);
        
        basla();
        writeToFile(workflowFile, outputFilePath);
        
        
        
       
    }  
}
