import java.util.ArrayList;

public class TestingTestoku {
    public static void main(String[] args) {
        Testoku t = new Testoku(
                "C:\\Users\\MONSTER\\Desktop\\MyProject\\Se-116-Project\\Se-116-Project\\Reading from text file (NOT DONE YET)\\Workflow.txt");
        t.printJobsInfo();
        t.printStationsInfo();
        t.printTasksInfo();
        Task[] lol = t.getTasksList();
        ArrayList<Job> jobsLol = t.getJobList();
        ArrayList<Station> stationsLOL = t.getStations();
    }
}
