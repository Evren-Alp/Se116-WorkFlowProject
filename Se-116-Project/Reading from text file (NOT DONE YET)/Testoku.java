import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Testoku{
    public static String txt = "";
    public static void main(String[] args) throws FileNotFoundException {
       try {
        File file = new File("Workflow.txt");
        Scanner sc = new Scanner(file);
        txt = "";
        while(sc.hasNextLine()){
            txt += sc.nextLine();
            txt += "\n";
        }
        
       } catch (FileNotFoundException e) {
        System.err.println("File not found.");
       }
        
        // System.out.println(txt);

        // Checking for Syntax Errors: ----------------------------------------------------------------------------------------
        // Pattern for checking negative task sizes
        boolean found1 = false;
        Pattern negativeNums = Pattern.compile("[A-z]+\\d+\\s-\\d+\\.\\d+|[A-z]+\\d+\\s-\\d+");
        Matcher negativeNumMatcher = negativeNums.matcher(txt);
        while(negativeNumMatcher.find()){
            found1 = true;
            System.err.println("Syntax Error: There is a negative task size \"" + negativeNumMatcher.group() + 
                            "\" at indexes: " + negativeNumMatcher.start() + " - " + negativeNumMatcher.end());
        }
        if(found1){
            System.out.println("task size should be a positive integer.");
            System.out.println("Terminating...");
            System.exit(1);
        }
        else System.out.println("[OK] Tasktype sizes");
        
        // Pattern to check for invalid tasktypeIDs ------ SHOULD BE DEPENDANT ON TASK NAMES NEEDS TO BE CHANGED LATER!! ------
        boolean found3 = false;
        Pattern invalidTaskTypeID = Pattern.compile("\\d+[A-z]+\\d+"); // if starts with a number
        Matcher invalidTaskTypeIDMatcher = invalidTaskTypeID.matcher(txt);
        while(invalidTaskTypeIDMatcher.find()){
            found3 = true;
            System.err.println("Syntax Error: There is a invalid tasktypeID \"" + invalidTaskTypeIDMatcher.group() +
                             "\" at indexes: " + invalidTaskTypeIDMatcher.start() + " - " + invalidTaskTypeIDMatcher.end());
        }
        if(found3){
            System.out.println("tasktypeID should not start with a number.");
            System.out.println("Terminating...");
            System.exit(1);
        }
        else System.out.println("[OK] Tasktype IDs");
        
        // TASKTYPES SECTION --------------------------------------------------------------------------------------------------
        // Pattern for taking the tasks from the TASKTYPES section from the text file
        Pattern tasktypeExpressionFinder = Pattern.compile("TASKTYPES((\\s[A-Z]+\\d+|\\s[A-Z]+_\\d+)(\\s\\d\\.\\d|\\s\\d)?)+");
        Matcher tasktypeMatcher = tasktypeExpressionFinder.matcher(txt);
        String taskTypes = "";
        if(tasktypeMatcher.find())
            taskTypes = tasktypeMatcher.group();
        // Pattern to separate all tasks
        Pattern tasktypesExpressions = Pattern.compile("((\\s[A-Z]+\\d+|\\s[A-Z]+_\\d+)(\\s\\d\\.\\d|\\s\\d)?)");
        Matcher foundTaskTypes = tasktypesExpressions.matcher(taskTypes);
        // The list of each tasktypeID and it's value:
        ArrayList<String> tasks = new ArrayList<>();
        while(foundTaskTypes.find()){
            String find = foundTaskTypes.group();
            // System.out.println(find);
            tasks.add(find);
        }


        // SAVING TASK ID AND TASK SIZE INTO TASK A LIST ----------------------------------------------------------------------
        Task[] tasksList = new Task[tasks.size()];
        for(int i = 0; i<tasks.size(); i++){
            String[] keyVal = tasks.get(i).split(" ");
            if(keyVal.length == 2){
                // System.out.println(keyVal[1]);
                tasksList[i] = new Task(keyVal[1]);
            }
            else{
                // System.out.println(keyVal[1] + " " + keyVal[2]);
                tasksList[i] = new Task(keyVal[1], Float.valueOf(keyVal[2]));
            }
        }

        int amount = 0;
        for(int i = 0; i<tasksList.length; i++){
            // System.out.println(tasksList[i].getName());
            for(int j = 0; j<tasksList.length; j++){
                if(tasksList[i].getTaskName().equals(tasksList[j].getTaskName())){
                    amount++;
                }
                // System.out.println(tasksList[j].getName() + "  amount: " + amount);
            }
            if(amount > 1){
                System.err.println("TaskType ID \"" + tasksList[i].getTaskName() + "\" is listed twice.\nTerminating...");
                System.exit(1);
            }
            amount = 0;
        }
        System.out.println("[OK] No tasktype duplicates.");







        // SELECTING THE JOBTYPES SECTION -------------------------------------------------------------------------------------
        //Pattern for separating the whole JOBTYPES section from the text file
        Pattern jobsPattern = Pattern.compile("[(]JOBTYPES(?:\\s+[(][a-zA-Z0-9 ]+\\.\\d+[)]|\\s+[(][a-zA-Z0-9 ]+[)])+[)]");
        Matcher match1 = jobsPattern.matcher(txt);
        String jobTypes = "";
        while(match1.find()) jobTypes += match1.group();

        jobsPattern = Pattern.compile("[(][a-zA-Z0-9 ]+\\.\\d+[)]|[(][a-zA-Z0-9 ]+[)]");
        match1 = jobsPattern.matcher(jobTypes);
        jobTypes = "";
        while(match1.find()) jobTypes += match1.group();

        jobsPattern = Pattern.compile("[a-zA-Z0-9 ]+\\.\\d+|[a-zA-Z0-9 ]+");
        match1 = jobsPattern.matcher(jobTypes);
        jobTypes = "";
        ArrayList<String> jobs = new ArrayList<>();
        while(match1.find()){
            String s = match1.group();
            jobTypes += s ;
            jobs.add(s);
        }

        // CHECKING THE TASKSLIST
        // System.out.print("tasksList: "); 
        // for(Task t : tasksList){
        //     System.out.print(t.getName() + " ");
        // }
        // System.out.println();

        // Checking whether all tasktype IDs are declared in the TASKTYPES section --------------------------------------------
        ArrayList<Job> jobsList = new ArrayList<>();
        for(int i = 0; i<jobs.size(); i++){
            ArrayList<Task> jobTasksList = new ArrayList<>();
            //Pattern for separating JobID and tasks
            jobsPattern = Pattern.compile("([A-z0-9]+)\\s([A-z0-9 ]*\\.\\d+|[A-z0-9 ]*)");
            match1 = jobsPattern.matcher(jobs.get(i));
            String jobTasks = "";
            Job job = new Job();
            if(match1.find()){
                job.setJobID(match1.group(1));
                jobTasks = match1.group(2);
            }

            // Checking for duplicate JobIDs ----------------------------------------------------------------------------------
            for(int j = 0; j<jobsList.size();j++){
                if(job.getJobID().equals(jobsList.get(j).getJobID())){
                    System.err.println("JobID \""+job.getJobID()+"\" is used twice.\nTerminting...");
                    System.exit(1);
                }
            }

            // CHECKING JOBID AND JOB'S TASKS FOR EACH JOB
            // System.out.println("-----------------------------" + job.getJobID());
            // System.out.println("+++++++++++++++++++++++++++++" + jobTasks);

            //Pattern for separating tasks
            jobsPattern = Pattern.compile("([A-z0-9]+\\s+\\d+\\.\\d+)|([A-z0-9]+\\s+\\d+)|([A-z0-9]+)");
            match1 = jobsPattern.matcher(jobTasks);
            jobTasks = "";
            ArrayList<String> tasksSeparated = new ArrayList<>();
            while(match1.find()){
                tasksSeparated.add(match1.group());
            }

            // CHECKING EACH TASK SEPARATED (with or without size)
            // for(String s: tasksSeparated){
            //     System.out.println("." + s + ".");
            // }

            for(String s: tasksSeparated){
                Task jobTask = new Task();
                String[] splitten = s.split(" "); 
                if(splitten.length == 2){
                    jobTask.setTaskName(splitten[0]);
                    jobTask.setTaskSize(Float.valueOf(splitten[1]));
                    jobTasksList.add(jobTask);
                }
                else{
                    jobTask.setTaskName(splitten[0]);
                    boolean validTask = false;
                    for(int j = 0; j<tasksList.length; j++){
                        if(tasksList[j].getTaskName().equals(jobTask.getTaskName())){
                            if(tasksList[j].getTaskSize() == 0.0f){ // Checking if the task size is declared ----------------------
                                System.err.println("Task size for \""+ jobTask.getTaskName() +"\" not declared.\nTerminating...");
                                System.exit(1);
                            }
                            else{
                                jobTask.setTaskSize(tasksList[j].getTaskSize());
                                jobTasksList.add(jobTask);
                            }
                            validTask = true;
                        }
                    }
                    if(!validTask){ // Checking if the tasktype is declared in TASKTYPES section ------------------------------
                        System.err.println("Tasktype \""+ jobTask.getTaskName() +"\" not declared int TASKTYPES.");
                    }
                }
            }
            job.setTasks(jobTasksList);
            jobsList.add(job);
        }
        System.out.println("[OK] Unique JobIDs");
        System.out.println("[OK] All TaskTypes have size declared");
        System.out.println("[OK] All TaskType are valid");

        for(Job j: jobsList){
            System.out.println("jobID: " + j.getJobID());
            for(int i = 0; i<j.getTasks().size(); i++){
                System.out.printf("TaskID: %s | Task Size: %f%n",j.getTasks().get(i).getTaskName(), j.getTasks().get(i).getTaskSize());
            }
            System.out.println();
        }
    }
}