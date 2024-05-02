import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test{
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("Workflow.txt");
        Scanner sc = new Scanner(file);
        String txt = "";
        while(sc.hasNextLine()){
            txt += sc.nextLine();
        }
        // System.out.println(txt);
        // Checking for Syntax Errors:
        Pattern negativeNums = Pattern.compile("-\\d");
        Matcher negativeNumMatcher = negativeNums.matcher(txt);
        if(negativeNumMatcher.find()){
            System.out.println("ERROR THERE ARE NEGATIVE NUMS");
        }
        

        Pattern TASKTYPEexpression = Pattern.compile("TASKTYPES(\\s[A-Z]+\\d+(\\s\\d\\.\\d|\\s\\d)?)+");
        Matcher TASKTYPESmatcher = TASKTYPEexpression.matcher(txt);
        while(TASKTYPESmatcher.find()){
            System.out.println(TASKTYPESmatcher.group());
        }
    }
}