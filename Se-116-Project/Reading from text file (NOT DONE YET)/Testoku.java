import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Testoku{
    private boolean debugging = true;
    private String txt = "";
    private Task[] tasksList;
    private ArrayList<Job> jobList = new ArrayList<>();
    private ArrayList<Station> stationsList = new ArrayList<>();
    public Testoku(String pathname){
        try {
            File file = new File(pathname);
            Scanner sc = new Scanner(file);
            txt = "";
            while(sc.hasNextLine()){
                txt += sc.nextLine();
                txt += "\n";
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found.\nTerminating...");
            System.exit(1);
        }

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
        else if(debugging) System.out.println("[OK] Tasktype sizes");
        
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
        else if(debugging) System.out.println("[OK] Tasktype IDs");
        
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


        // SAVING TASK ID AND TASK SIZE INTO A TASK LIST ----------------------------------------------------------------------
        tasksList = new Task[tasks.size()];
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
        if(debugging) System.out.println("[OK] No tasktype duplicates.");

        // SELECTING THE JOBTYPES SECTION -------------------------------------------------------------------------------------
        Pattern jobsPattern = Pattern.compile("([(]JOBTYPES(?:\\s+[(][A-z0-9 ]+\\.\\d+[)]|\\s+[(][A-z0-9\\s]+[)])+[)]+)");
        Matcher match1 = jobsPattern.matcher(txt);
        String jobTypes = "";
        while(match1.find()){
            jobTypes += match1.group(1);
        }
        if(jobTypes.equals("")){
            System.err.println("Wrong syntax for the JOBTYPES section.\nTerminating...");
            System.exit(1);
        }
        if(!jobTypes.endsWith("))")){
            System.err.println("The JOBTYPES section is not closed. \")\" missing.\nTerminating...");
            System.exit(1);
        }
        if(jobTypes.endsWith(")))")){
            System.err.println("SyntaxError at the end of the STATIONS section. Extra \")\"s used.\nTerminating...");
            System.exit(1);
        }

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
            for(int j = 0; j<jobList.size();j++){
                if(job.getJobID().equals(jobList.get(j).getJobID())){
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
                        System.err.println("Tasktype \""+ jobTask.getTaskName() +"\" not declared in TASKTYPES.");
                    }
                }
            }
            job.setTasks(jobTasksList);
            jobList.add(job);
        }
        if(debugging){
            System.out.println("[OK] Unique JobIDs");
            System.out.println("[OK] All TaskTypes have size declared");
            System.out.println("[OK] All TaskType are valid");
        }


        // STATIONS SECTION ---------------------------------------------------------------------------------------------------
        // Pattern for all of the stations
        Pattern pattern1 = Pattern.compile("([(]STATIONS\\s+([()A-z0-9.\\s]+)[)])");
        Matcher matcher1 = pattern1.matcher(txt);
        txt = "";
        String syntaxCheck = "";
        if(matcher1.find()){
            syntaxCheck = matcher1.group(1);
            txt = matcher1.group(2);
        }
        if(txt.equals("")){
            System.err.println("Wrong syntax for the STATIONS section.\nTerminating...");
            System.exit(1);
        }
        if(!syntaxCheck.endsWith("))")){
            System.err.println("The STATIONS section is not closed. \")\" missing.\nTerminating...");
            System.exit(1);
        }
        if(syntaxCheck.endsWith(")))")){
            System.err.println("SyntaxError at the end of the STATIONS section. Extra \")\"s used.\nTerminating...");
            System.exit(1);
        }
        
        // Pattern for separating each station in stations
        pattern1 = Pattern.compile("[(]([()A-z0-9 .]+)[)]");
        matcher1 = pattern1.matcher(txt);
        ArrayList<String> stationsString = new ArrayList<>();
        while(matcher1.find()){
            stationsString.add(matcher1.group(1));
        }
        stationsList = new ArrayList<>();
        // Pattern for separating the first 4 sections of each station + all of the tasks (tasks not separated!)
        for(String s : stationsString){
            pattern1 = Pattern.compile("([A-z0-9]+)\\s+(\\d+.\\d+|\\d)*\\s*(N|Y)\\s+(N|Y)\\s*([A-z0-9 .]+)?");
            matcher1 = pattern1.matcher(s);
            while(matcher1.find()){
                boolean maxCapDefined = true;
                int maxCap = 0;
                if(matcher1.group(2) == null){
                    maxCapDefined = false;
                } else{
                    maxCap = Integer.parseInt(matcher1.group(2));
                }
                String name = matcher1.group(1);
                boolean multi = false;
                boolean fifo = true;
                ArrayList<Task> stationTasksList = new ArrayList<>();
                if(matcher1.group(3).equals("N")){
                    multi = false;
                } else if(matcher1.group(3).equals("Y")){
                    multi = true;
                } else{
                    System.err.println("Invalid MULTIFLAG.\nTerminating...");
                    System.exit(1);
                }
                if(matcher1.group(4).equals("N")){
                    fifo = false;
                } else if(matcher1.group(4).equals("Y")){
                    fifo = true;
                } else{
                    System.err.println("Invalid FIFOFLAG.\nTerminating...");
                    System.exit(1);
                }
                // Pattern for serparating tasks.
                Pattern pattern2 = Pattern.compile("([A-z]+\\d+\\s+\\d+\\.\\d+\\s\\d+\\.\\d+|[A-z]+\\d+\\s+\\d+\\s+\\d+\\.\\d+|[A-z]+\\d+\\s+\\d+\\.\\d+|[A-z]+\\d+\\s+\\d+|[A-z]+\\d+)");
                Matcher matcher2 = pattern2.matcher(matcher1.group(5));
                ArrayList<String> tasks2 = new ArrayList<>();
                while(matcher2.find()){
                    tasks2.add(matcher2.group(1));
                }
                ArrayList<String> plusMinusList = new ArrayList<>();
                //Separating TaskID and sizes, checking for error if size is not declared anywhere.
                for(String ss: tasks2){
                    Task stationTask = new Task();
                    String[] splitten = ss.split(" ");
                    if(splitten.length == 2){
                        stationTask.setTaskName(splitten[0]);
                        stationTask.setTaskSize(Float.valueOf(splitten[1]));
                        stationTasksList.add(stationTask);
                    }
                    else if(splitten.length == 3){
                        stationTask.setTaskName(splitten[0]);
                        stationTask.setTaskSize(Float.valueOf(splitten[1]));
                        plusMinusList.add(splitten[0] + " " + splitten[2]);
                        stationTasksList.add(stationTask);
                    }
                    else{
                        stationTask.setTaskName(splitten[0]);
                        boolean isValid = false;
                        for(int i = 0; i<tasksList.length; i++){
                            if(tasksList[i].getTaskName().equals(stationTask.getTaskName())){
                                if(tasksList[i].getTaskSize() == 0.0f){ // Checking if the task size is declared --------------
                                    System.err.println("Task size for \""+ stationTask.getTaskName() +"\" not declared.\nTerminating...");
                                    System.exit(1);
                                }
                                else{
                                    stationTask.setTaskSize(tasksList[i].getTaskSize());
                                    stationTasksList.add(stationTask);
                                }
                                isValid = true;
                            }
                        }
                        if(!isValid){ // Checking if the tasktype is declared in TASKTYPES section ----------------------------
                            System.err.println("Tasktype \""+ stationTask.getTaskName() +"\" not declared in TASKTYPES.");
                        }
                    }
                }
                Station station = new Station();
                if(maxCapDefined){
                    station = new Station(name, maxCap, multi, fifo);
                    station.setTasksList(stationTasksList);
                } else if(!maxCapDefined){
                    station = new Station(name, multi, fifo);
                    station.setTasksList(stationTasksList);
                }
                station.setPlusminusList(plusMinusList);
                stationsList.add(station);
            }
        }
        // This part looks for tasks that are not identified in the stations part ---------------------------------------------
        boolean taskIsIdentified = false;
        for(int i = 0; i<tasksList.length; i++){ // i is for getting each task in the tasksList
            taskIsIdentified = false;
            for(int j = 0; j<stationsList.size(); j++){ // j is for getting each station in the stationsList
                for(int k = 0; k<stationsList.get(j).getTasks().size(); k++){ // k is for getting each task in the stationsList's tasksList
                    if(stationsList.get(j).getTasks().get(k).getTaskName().equals(tasksList[i].getTaskName())){
                        taskIsIdentified = true;
                        break;
                    }
                }
                if(taskIsIdentified){
                    break;
                }
            }
            if(!taskIsIdentified){
                System.err.println("The task \"" + tasksList[i].getTaskName() + "\" is identified in the TASKTYPES section but none of the stations include it.\nTerminating...");
                System.exit(1);
            }
        }
    }
    public void printJobsInfo(){ // getJobID|getTasks
        for(Job j : jobList){
            System.out.println("Job ID: " + j.getJobID());
            for(Task t: j.getTasks()){
                System.out.println("--- TaskID: " + t.getTaskName() + " | " + "Size: " + t.getTaskSize());
            }
        }
    }
    public void printStationsInfo(){
        for(Station s : this.stationsList){
            System.out.println("Station ID: " + s.getName());
            for(Task t : s.getTasks()){
                System.out.println("--- TaskID: " + t.getTaskName() + " | " + "Size: " + t.getTaskSize());
            }
        }
    }
    public void printTasksInfo(){
        for(Task t: tasksList){
            System.out.println("-- TaskID: " + t.getTaskName() + " | " + "Size: " + t.getTaskSize());
        }
    }
    public Task[] getTasksList(){
        return this.tasksList;
    }
    public ArrayList<Job> getJobList(){
        return this.jobList;
    }
    public ArrayList<Station> getStations(){
        return this.stationsList;
    }
}
