import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class jobManager implements user {
    private boolean isLoggedIn;
    private String email;
    private String password;
    private String userType;

    public jobManager() {
        isLoggedIn = false;
        email = "";
        password ="";
        userType ="Job_Manager";
    }

    public void createLoginCredentials() {
        Scanner createscanner = new Scanner(System.in);

        System.out.print("Enter your email: ");
        String newEmail = createscanner.nextLine();

        System.out.print("Enter your password: ");
        String newPassword = createscanner.nextLine();

        this.email = newEmail;
        this.password = newPassword;
    }
    public String getEmail(){
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    // Getter for isLoggedIn
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getUserType() {
        return userType;
    }

    // Setter for isLoggedIn
    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }

    public boolean login(String userInputEmail, String userInputPassword) {
        if (email.equals(userInputEmail) && password.equals(userInputPassword)) {
            isLoggedIn = true;
            return true;
        }
        return false;
    }
    
    // Implement the logout method as required by the user interface
    @Override
    public void logout() {
        isLoggedIn = false;
    }

    @Override
    public void displayMenu(Database database) {
        System.out.println("Job Manager Menu:");
        System.out.println("1. Approve/Reject Pending Job");
        System.out.println("2: Show all Users");
        System.out.println("3. Return to Login Screen.");
    }

    @Override
    public void processMenuChoice(int choice, Database database) {
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;
    
        while (continueLoop) {
            switch (choice) {

                case 1:
                    approveRejectJob(database);
                    break;
                case 2:
                    database.printUsers();
                    break;
                case 3:
                    System.out.println("Back to main menu.");
                    logout();
                    continueLoop = false; // Exit the loop and return to the main menu
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            if (continueLoop) {
                displayMenu(database);
                choice = scanner.nextInt();
            }
        }
    }

    private void approveRejectJob(Database database) {
    // Access the list of pending jobs
    ArrayList<Job> pendingJobs = database.getPendingJobs();
    Scanner scanner = new Scanner(System.in);
    // Display pending jobs for approval or rejection
    if (pendingJobs.isEmpty()) {
        System.out.println("No pending jobs to approve or reject.");
        return;
    }
    for (int i = 0; i < pendingJobs.size(); i++) {
        Job job = pendingJobs.get(i);
        // Display job details
        System.out.println("Job ID: " + job.getJobID());
        System.out.println("Position: " + job.getPosition());
        System.out.println("Description :" + job.getDescription());
        System.out.println("Salary  :" + job.getSalary());
        System.out.println("Contact Person :" + job.getContactPerson());
        System.out.println("Contact Email :" + job.getContactEmail());
        System.out.println("Contact Phone :" + job.getContactPhone());
   
        System.out.println("Pending Jobs for Approval/Rejection:");

        

        // Prompt for approval or rejection
        System.out.print("Approve (A) or Reject (R) job: ");
        String decision = scanner.nextLine();

        if (decision.equalsIgnoreCase("A")) {
            // Approved: Remove from pending jobs and add to jobs list
            pendingJobs.remove(job);
            database.addJob(job);
            System.out.println("Job with ID " + job.getJobID() + " has been approved.");
            removeJobFromPendingJobsFile(job.getJobID());
        } else if (decision.equalsIgnoreCase("R")) {
            // Rejected: Remove from pending jobs
            pendingJobs.remove(job);
            System.out.println("Job with ID " + job.getJobID() + " has been rejected.");
            removeJobFromPendingJobsFile(job.getJobID());
        } else {
            System.out.println("Invalid choice. Skipping job.");
        }
    }
}


    private void removeJobFromPendingJobsFile(String jobID) {
        File pendingJobsFile = new File("pendingJobs.txt");
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(pendingJobsFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] jobData = line.split(",");
                if (jobData.length > 0 && !jobData[0].equals(jobID)) {
                    writer.write(line + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Delete the original pendingJobs.txt file and rename temp.txt to pendingJobs.txt
        if (!pendingJobsFile.delete() || !tempFile.renameTo(pendingJobsFile)) {
            System.out.println("Failed to update pendingJobs.txt file.");
        }
    }

}