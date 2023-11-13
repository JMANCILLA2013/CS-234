import java.util.ArrayList;
import java.util.Scanner;

public class jobPoster implements user {
    private boolean isLoggedIn;
    private String email;
    private String password;
    private ArrayList<Job> jobsPosted;
    private String userType;
    

    public jobPoster() {
        isLoggedIn = false;
        email = "";
        password ="";
        this.jobsPosted = new ArrayList<>();
        userType ="Job_Poster";
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Getter for isLoggedIn
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getUserType() {
        return userType;
    }


    public boolean login(String userInputEmail, String userInputPassword) {
        if (email.equals(userInputEmail) && password.equals(userInputPassword)) {
            isLoggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        isLoggedIn = false;
    }

    @Override
    public void displayMenu(Database database) {
        System.out.println("Job Poster Menu:");
        System.out.println("1. Create Job");
        System.out.println("2. See All Posted Jobs");
        System.out.println("3. Delete a Posted Job");
        System.out.println("4. Update a Posted Job");
        System.out.println("5. Return to Login Screen.");
        
    }

    @Override
    public void processMenuChoice(int choice, Database database) {
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;
    
        while (continueLoop) {
            switch (choice) {
                case 1:
                    createJob(database);
                    break;
                case 2:
                    seeAllJobs();
                    break;
                case 3:
                    deletePostedJob(database);
                    break;
                case 4:
                    updateJob(database);
                    break;
                case 5:
                    System.out.println("Back to main menu.");
                    logout();
                    continueLoop = false; // Exit the loop and return to the main menu
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
    
            if (continueLoop) {
                displayMenu(database);
                choice = scanner.nextInt();
            }
        }
    }

    public void createJob(Database database) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter job position: ");
        String position = scanner.nextLine();

        System.out.print("Enter job description: ");
        String description = scanner.nextLine();

        System.out.print("Enter job salary: ");
        String salary = scanner.nextLine();

        System.out.print("Enter contact person: ");
        String contactPerson = scanner.nextLine();

        System.out.print("Enter contact email: ");
        String contactEmail = scanner.nextLine();

        System.out.print("Enter contact phone: ");
        String contactPhone = scanner.nextLine();

        Job newJob = new Job();
        newJob.setPosition(position);
        newJob.setDescription(description);
        newJob.setSalary(salary);
        newJob.setContactPerson(contactPerson);
        newJob.setContactEmail(contactEmail);
        newJob.setContactPhone(contactPhone);

        // Display the generated jobID
        System.out.println("JobID for the posted job: " + newJob.getJobID());

        jobsPosted.add(newJob);
        database.addPendingJob(newJob);

        System.out.println("Job created successfully.");
    }

    public void seeAllJobs() {
        if (jobsPosted.isEmpty()) {
            System.out.println("You have not posted any jobs.");
        } else {
            for (Job job : jobsPosted) {
                System.out.println("Job ID: " + job.getJobID());
                System.out.println("Position: " + job.getPosition());
                System.out.println("Description: " + job.getDescription());
                System.out.println("Salary: " + job.getSalary());
                System.out.println("Contact Person: " + job.getContactPerson());
                System.out.println("Contact Email: " + job.getContactEmail());
                System.out.println("Contact Phone: " + job.getContactPhone());
                System.out.println("----------------------");
            }
        }
    }

    public void updateJob(Database database) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Enter the JobID of the job you want to update: ");
        String jobIDToUpdate = scanner.nextLine();
    
        Job jobToUpdate = null;
    
        for (Job job : jobsPosted) {
            if (job.getJobID().equals(jobIDToUpdate)) {
                jobToUpdate = job;
                break;
            }
        }
    
        if (jobToUpdate != null) {
            // The job exists in the jobsPosted list
            System.out.println("Enter new position: ");
            String newPosition = scanner.nextLine();
    
            System.out.println("Enter new description: ");
            String newDescription = scanner.nextLine();
    
            System.out.println("Enter new salary: ");
            String newSalary = scanner.nextLine();
    
            System.out.println("Enter new contact person: ");
            String newContactPerson = scanner.nextLine();
    
            System.out.println("Enter new contact email: ");
            String newContactEmail = scanner.nextLine();
    
            System.out.println("Enter new contact phone: ");
            String newContactPhone = scanner.nextLine();
    
            jobToUpdate.setPosition(newPosition);
            jobToUpdate.setDescription(newDescription);
            jobToUpdate.setSalary(newSalary);
            jobToUpdate.setContactPerson(newContactPerson);
            jobToUpdate.setContactEmail(newContactEmail);
            jobToUpdate.setContactPhone(newContactPhone);
    
            System.out.println("Job with ID " + jobIDToUpdate + " has been updated.");
    

            for (Job job : database.getJobs()) {
                if (job.getJobID().equals(jobIDToUpdate)) {
                    // Job found in jobs, remove it
                    database.getJobs().remove(job);
                    // Add the updated job back to pendingJobs
                    database.addPendingJob(jobToUpdate);
                    break;
                }
            }
            
    
            for (Job job : database.getPendingJobs()) {
                if (job.getJobID().equals(jobIDToUpdate)) {
                    // Job found in pendingJobs
                    job.setPosition(newPosition);
                    job.setDescription(newDescription);
                    job.setSalary(newSalary);
                    job.setContactPerson(newContactPerson);
                    job.setContactEmail(newContactEmail);
                    job.setContactPhone(newContactPhone);
                }
            }
        } else {
            // The job does not exist in the jobsPosted list
            System.out.println("Job with ID " + jobIDToUpdate + " not found in your posted jobs.");
        }
    }
    
    public void deletePostedJob( Database database) {
        Job jobToDelete = null;
        Scanner scanner = new Scanner(System.in);
        String jobIDToDelete = scanner.nextLine();

        for (Job job : jobsPosted) {
            if (job.getJobID().equals(jobIDToDelete)) {
                jobToDelete = job;
                break;
            }
        }
    
        if (jobToDelete != null) {
            jobsPosted.remove(jobToDelete);
            System.out.println("Job with ID " + jobIDToDelete + " has been deleted from posted jobs.");
    
            // Check and delete from jobs
            for (Job job : database.getJobs()) {
                if (job.getJobID().equals(jobIDToDelete)) {
                    database.getJobs().remove(job);
                    System.out.println("Job with ID " + jobIDToDelete + " has been deleted from jobs.");
                    break;
                }
            }
    
            // Check and delete from pendingJobs
            for (Job job : database.getPendingJobs()) {
                if (job.getJobID().equals(jobIDToDelete)) {
                    database.getPendingJobs().remove(job);
                    System.out.println("Job with ID " + jobIDToDelete + " has been deleted from pending jobs.");
                    break;
                }
            }
        } else {
            System.out.println("Job with ID " + jobIDToDelete + " not found in posted jobs.");
        }
    }
}
