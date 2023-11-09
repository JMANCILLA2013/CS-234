import java.util.Scanner;




public class Main{

    public static void main(String[] args) {
        //Create Database
        Database database = new Database();
        //Create Job Manager
        jobManager Bill = new jobManager();
        Bill.login("palejrey@gmail.com", "2001Qazwsx!");
        //Job
        Job job = new Job();

        job.setPosition("Position");
        job.setDescription("Description");
        job.setSalary("100");
        System.out.println(job.getJobID());
        database.addJob(job);
        database.addUser(Bill);
    }
}