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

    

    public static void yaz(){
        System.out.println("State of jobs at minute "+tur+": ");
        for (Job job : jobList) {
            System.out.println(job.getJobID()+"/Remaining: "+job.getJobDuration());
        }    
    }
    public static void basla() {
        // The process of the progam is done here
        tur = 1;
        boolean Working=false;
        yaz();
        while (activeTasks.isEmpty()==false||Working==true) {
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
            // Her tur
            for (Station station : stationList) {
                if (station.getCurrentTask().getTaskType()==TaskType.T0) {
                    station.setIdleCount(station.getIdleCount()+1);
                }
            }
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
        System.out.println("All jobs are done\n");  
        for(Task task:allTasks){
            workflowFile+=task.getFile();
        } 
    }
    // Write to file (output) method
    public static void writeToFile(String content, String outputPath) {
        try {
            File file = new File(outputPath);
            FileWriter writer = new FileWriter(file);
            writer.write(content + "\n");
            writer.close();
           // System.out.println("Content written to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.\nTerminating...");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        // Func Req. 1: Getting input from user for file paths
        String inputFilePath = "";
        String outputFilePath = "";
        Scanner sc = new Scanner(System.in);
        if(args.length == 2){
            inputFilePath = args[0];
            outputFilePath = args[1];
        }
        else if(args.length == 1){
            inputFilePath = args[0];
            System.out.print("Enter output file path: ");
            outputFilePath = sc.nextLine();
        }
        else{
            boolean inputPathGiven = false;
            while(!inputPathGiven){
                System.out.print("Enter input file path: ");
                inputFilePath = sc.nextLine();
                if(inputFilePath.equals("")){
                    System.out.println("Not a valid input. Try again.");
                }
                else inputPathGiven = true;
            }
            boolean outputPathGiven = false;
            while(!outputPathGiven){
                System.out.print("Enter output file path: ");
                outputFilePath = sc.nextLine();
                if(outputFilePath.equals("")){
                    System.out.println("Not a valid input. Try again.");
                }
                else outputPathGiven = true;
            }
        }
        sc.close();

        // Func Req. 2: Readin the input file
        inputReader input = new inputReader(inputFilePath);

        stationList.addAll(input.getStations());

        // Listing all of the tasks from all of the jobs
        jobList = input.getJobList();
        for (Job job : jobList) {
            activeTasks.addAll(job.getTasks());
        }
        allTasks.addAll(activeTasks);
        
        basla();
        writeToFile(workflowFile, outputFilePath);
        for (Station station : stationList) {
            System.out.println(station.getName()+" utilization: "+((tur-station.getIdleCount())/tur)*100.0);
        }
    }
}
