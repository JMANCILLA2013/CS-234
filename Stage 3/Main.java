import java.util.Scanner;
public class Main{

    public static void main(String[] args) {
        Job jobTest = new Job("","","","");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        jobTest.setSalary(userInput);
        System.out.println(jobTest.getSalary());
    }
}