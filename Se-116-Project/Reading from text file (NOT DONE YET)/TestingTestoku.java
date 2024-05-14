import java.util.ArrayList;

public class TestingTestoku {
    public static void main(String[] args) {
        Testoku t = new Testoku("C:\\Users\\Ariocodes\\Documents\\GitHub\\Se-116-Project\\Se-116-Project\\calisanlar\\Workflow.txt");
        t.printTasksInfo();
        t.printJobsInfo();
        t.printStationsInfo();
        Task[] lol = t.getTasksList();
        ArrayList<Job> jobsLol = t.getJobList();
        ArrayList<Station> stationsLOL = t.getStations();
        for(Station s:stationsLOL){
            for(Task ta: s.getTasks()){
                System.out.println("TaskID: " + ta.getTaskName());
                System.out.println(s.calculateTaskDuration(ta));
            }
        }
    }
}
