import java.util.Scanner;
import java.util.Iterator;
public class jobManager implements user{
    private static boolean isLoggedIn;
    private String email;
    private String password;

    public jobManager(){
        isLoggedIn = false;
        this.createLoginCredentials();
    }

    public void createLoginCredentials(){
        Scanner createscanner = new Scanner(System.in);

        System.out.print("Enter your email: ");
        String newEmail = createscanner.nextLine();

        System.out.print("Enter your password: ");
        String newPassword = createscanner.nextLine();
        createscanner.close();
        this.email = newEmail;
        this.password = newPassword;
    }

    public String getEmail(){
        return email;
    }

    public void login(String userInputEmail, String userInputPassword) {
        boolean validCredentials = userInputEmail.equals(email) && userInputPassword.equals(password);

        if (validCredentials) {
            isLoggedIn = true;
            System.out.println("LOGGED IN!");
        }

    }

    public void logout(){
        isLoggedIn = false;
    }

    
    public static void deleteJob(Database database, String jobIDToDelete) {
        if (!isLoggedIn) {
            System.out.println("You must be logged in to delete jobs");
            return;
        }
        
        Iterator<Job> iterator = database.getJobs().iterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            if (job.getJobID().equals(jobIDToDelete)) {
                iterator.remove();
                System.out.println("Job with JobID " + jobIDToDelete + " deleted successfully.");
                return;
            }
        }
        System.out.println("Job with JobID " + jobIDToDelete + " not found in the list.");
    }

    public void approveJob(Database database, String jobIDToApprove, boolean isApproved) {
        if (!isLoggedIn) {
            System.out.println("You must be logged in to approve or reject jobs.");
            return;
        }
        
        Iterator<Job> iterator = database.getPendingJobs().iterator();
        while (iterator.hasNext()) {
            Job pendingJob = iterator.next();
            if (pendingJob.getJobID().equals(jobIDToApprove)) {
                //TODO: DISPLAY THE DATA OF THE JOB FOR THE MANAGER TO LOOK AT BEFORE HE DECIDES
                if (isApproved) {
                    // Move the job from pendingJobs to jobs
                    database.addJob(pendingJob);
                    System.out.println("Job with JobID " + jobIDToApprove + " approved and moved to the jobs list.");
                } else {
                    // Remove the job from pendingJobs
                    iterator.remove();
                    System.out.println("Job with JobID " + jobIDToApprove + " not approved and removed from pending jobs list.");
                }
                return;
            }
        }
        System.out.println("Job with JobID " + jobIDToApprove + " not found in the pending jobs list.");
    }
}

