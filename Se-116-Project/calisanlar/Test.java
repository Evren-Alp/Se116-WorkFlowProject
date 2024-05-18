import java.io.File;
import java.io.FileNotFoundException;
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
        boolean Working=false;
        yaz();
        while (activeTasks.isEmpty()==false||Working==true) {
           
            uyum=false;
            for (Station station : stationList) {
                for (Task task : activeTasks) {
                    if (station.getSupportedTaskTypes().contains(task.getTaskType()) && station.getCurrentTask().getTaskType()==TaskType.T0) {
                        station.work(task);
                        task.setStartingTime(tur);
                        
                        
                        station.setCurrentTask(task);
                        activeTasks.remove(task);
                        station.getCurrentTask().setTaskSize(station.calculateTaskDuration(task));
                        uyum=true;
                        for (Job job : jobList) {
                            if (job.getTasks().contains(station.getCurrentTask())){
                                station.getCurrentTask().setFile("\n"+job.getJobID()+" ");;
                                break;
                            }
                        }
                        station.getCurrentTask().setFile(task.getTaskType()+" "+tur+" ");
                        break;
                    }
                    else {
                        
                        uyum=false;
                        break;
                    }
                }

                
                
            }
            //her tur
            for (Station station : stationList) {
                station.getCurrentTask().setTaskSize(station.getCurrentTask().getTaskSize()-1);
                if (station.getCurrentTask().getTaskSize()==0&&station.getCurrentTask().getTaskType()!=TaskType.T0) {
                
                    station.getCurrentTask().setFile(""+(tur-station.getCurrentTask().getStartingTime()+1));
                    System.out.println("Minute "+tur+": "+station.getName()+" finished "+station.getCurrentTask().getTaskType());
                    
                    station.setCurrentTask(Idle);
                    
                    break;
                }
                if (station.getCurrentTask().getTaskSize()<0&&station.getCurrentTask().getTaskType()!=TaskType.T0) {
                
                    station.getCurrentTask().setFile(""+(tur-station.getCurrentTask().getStartingTime()));
                    System.out.println("Minute "+(tur+1)+": "+station.getName()+" finished "+station.getCurrentTask().getTaskType());
                    
                    station.setCurrentTask(Idle);
                    
                    break;
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
            System.out.println("JobID: " + job.getJobID());
            for(Task ta : job.getTasks())
                System.out.println("--- " + ta.getTaskName());
        }
        allTasks.addAll(activeTasks);
        basla();
        writeToFile(workflowFile, outputFilePath);
        
        
        // ArrayList<Task> taskler1=new ArrayList<>();
        // ArrayList<Task> taskler2=new ArrayList<>(); 
        // Task task1 = new Task("task1", 15, TaskType.T1);
        // Task task2 = new Task("task2", 30, TaskType.T2);
        // Task task3 = new Task("task3", 45, TaskType.T3);
        // Task task4 = new Task("task4", 60, TaskType.T4);
        // taskler1.add(task1);
        // taskler1.add(task2);
        // taskler2.add(task3);
        // taskler2.add(task4);

        /*  jobList=t.getJobList();*/
        // jobList = new ArrayList<>();
    
        // Job job1 = new Job("Job1", new ArrayList<Task>(taskler1));
        // Job job2 = new Job("Job2",new ArrayList<Task>(taskler2));
        // jobList.add(job1);
        // jobList.add(job2);

        // ArrayList<TaskType> stationAblility1 = new ArrayList<>();
        // stationAblility1.add(TaskType.T1);
        // stationAblility1.add(TaskType.T2);
       
        // ArrayList<TaskType> stationAblility2 = new ArrayList<>();
        // stationAblility2.add(TaskType.T3);
        // stationAblility2.add(TaskType.T4);

        
        // Station station1 = new Station("Station1",  3,   true, false);
        // Station station2 = new Station("Station2",  2,   true, false);
        // stationList.add(station1);
        // stationList.add(station2);

       
        // for (Job job : jobList) {
        //     activeTasks.addAll(job.getTasks());
        // }
        // allTasks.addAll(activeTasks);
        // basla();
        // System.out.println(workflowFile);
        // writeToFile(workflowFile, outputFilePath);
       
    }  
}
