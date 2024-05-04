import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Task{
    private String name;
    private float size;
    public Task(String name, float size){
        this.name = name;
        this.size = size;
    }
    public Task(String name){
        this.name = name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSize(float size){
        this.size = size;
    }
    public String getName(){
        return this.name;
    }
    public float getSize(){
        return this.size;
    }
}
class Job{
    private String jobName = "";
    private ArrayList<Task> tasks;
    public Job(String jobName, ArrayList<Task> tasks){
        this.jobName = jobName;
        this.tasks = tasks;
    }
    public Job(String jobName){
        this.jobName = jobName;
    }
    public void addTask(Task task){
        this.tasks.add(task);
    }
    public ArrayList<Task> getTasks(){
        return this.tasks;
    }
    public void setTasks(ArrayList<Task> tasks){
        this.tasks = tasks;
    }
    public String getJobName(){
        return this.jobName;
    }
    public void setJobName(String jobName){
        this.jobName = jobName;
    }
}

public class Test{
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("Workflow.txt");
        Scanner sc = new Scanner(file);
        String txt = "";
        while(sc.hasNextLine()){
            txt += sc.nextLine();
            txt += "\n";
        }
        // System.out.println(txt);

        // Checking for Syntax Errors: ----------------------------------------------------------------------------------------
        // negative task size:
        boolean found1 = false;
        Pattern negativeNums = Pattern.compile("[A-z]+\\d+\\s-\\d");
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
        
        // Invalid tasktypeID : SHOULD BE DEPENDANT ON TASK NAMES NEEDS TO BE CHANGED LATER!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        boolean found3 = false;
        Pattern invalidTaskTypeID = Pattern.compile("\\d+[A-z]");
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
        Pattern tasktypeExpressionFinder = Pattern.compile("TASKTYPES((\\s[A-Z]+\\d+|\\s[A-Z]+_\\d+)(\\s\\d\\.\\d|\\s\\d)?)+");
        Matcher tasktypeMatcher = tasktypeExpressionFinder.matcher(txt);
        String taskTypes = "";
        if(tasktypeMatcher.find())
            taskTypes = tasktypeMatcher.group();
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
                if(tasksList[i].getName().equals(tasksList[j].getName())){
                    amount++;
                }
                // System.out.println(tasksList[j].getName() + "  amount: " + amount);
            }
            if(amount > 1){
                System.err.println("TaskType ID \"" + tasksList[i].getName() + "\" is listed twice.\nTerminating...");
                System.exit(1);
                break;
            }
            amount = 0;
        }
        System.out.println("[OK] No tasktype duplicates.");







        // JOBTYPES SECTION ---------------------------------------------------------------------------------------------------
        Pattern jobsPattern = Pattern.compile("[(]JOBTYPES(?:\\s*[(][a-zA-Z0-9 ]+[)])+[)]");
        Matcher match1 = jobsPattern.matcher(txt);
        String jobTypes = "";
        while(match1.find()) jobTypes += match1.group();

        jobsPattern = Pattern.compile("[(][a-zA-Z0-9 ]+[)]");
        match1 = jobsPattern.matcher(jobTypes);
        jobTypes = "";
        while(match1.find()) jobTypes += match1.group();

        jobsPattern = Pattern.compile("[a-zA-Z0-9 ]+");
        match1 = jobsPattern.matcher(jobTypes);
        jobTypes = "";
        ArrayList<String> jobs = new ArrayList<>();
        while(match1.find()){
            String s = match1.group();
            jobTypes += s ;
            jobs.add(s);
        }

        System.out.print("tasksList: ");
        for(Task t : tasksList){
            System.out.print(t.getName() + " ");
        }
        System.out.println();
        boolean isValid = false;
        // Checking whether all tasktype IDs are declared in the TASKTYPES section --------------------------------------------
        for(int i = 0; i<jobs.size(); i++){
            jobsPattern = Pattern.compile("[A-z0-9]+\\s([A-z0-9 ]*)");
            match1 = jobsPattern.matcher(jobs.get(i));
            String jobTasks = "";
            if(match1.find()){
                jobTasks = match1.group(1);
            }
            System.out.println("+++++++++++++++++++++++++++++" + jobTasks);
            jobsPattern = Pattern.compile("([A-z0-9]+\\s\\d)|([A-z0-9]+)");
            match1 = jobsPattern.matcher(jobTasks);
            jobTasks = "";
            ArrayList<String> jobTasksList = new ArrayList<>();
            while(match1.find()){
                jobTasksList.add(match1.group());
            }

            for(int j = 0; j<jobTasksList.size(); j++){        // T1, T2, T3
                String[] task = new String[2];
                try{
                    System.out.println(jobTasksList.get(j));
                    task = jobTasksList.get(j).split(" ");
                }catch(ArrayIndexOutOfBoundsException e){
                    task[0] = jobTasksList.get(j);
                    task[1] = "0";
                }catch(NullPointerException e){
                    task[0] = jobTasksList.get(j);
                    task[1] = "0";
                }finally{
                    
                }
                for(int k = 0; k<tasksList.length; k++){       // TASKTYPES T1, T2, T3, T4, ...
                    if(task[0].equals(tasksList[k].getName())){
                        if(tasksList[k].getSize() == 0.0){
                            if(task[1] == "0"){
                                isValid = false;
                            }
                            else{
                                isValid = true;
                            }
                        }
                        else{
                            isValid = true;
                            break;
                        }
                    }
                }
                if(isValid){
                    System.out.println("Was valid");
                }
                if(!isValid){
                    System.err.println("TaskType ID \"" + jobTasksList.get(j) + "\" is not declared in TASKTYPES.\nTerminating...");
                    System.exit(1);
                }
                isValid = false;
            }
        }
        System.out.println("[OK] All declared TaskType ID's");

        //([A-z0-9]+\s\d)|([A-z0-9]+)
        // Job[] jobsList = new Job[jobs.size()];
        // for(int i = 0; i<jobs.size(); i++){
        //     match1 = jobsPattern.matcher(jobs.get(i));
        //     if(match1.find()){
        //         jobsList[i].setJobName(match1.group(1));
        //     }
        //     String[] jobsDivided = jobs.get(i).split(" ");
        //     for(String job : jobsDivided){
        //         for(int j = 0; j<tasksList.length; j++){
        //             if(tasksList[j].getName() == job){
        //                 jobsList[i].addTask();
        //             }
        //         }
        //     }
        // }
        // match1 = jobsPattern.matcher()
    }
}