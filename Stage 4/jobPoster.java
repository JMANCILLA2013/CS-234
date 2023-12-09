import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class jobPoster implements user {
    private boolean isLoggedIn;
    private String email;
    private String password;
    private String company;
    private String phone;
    private ArrayList<Job> jobsPosted;
    private String userType;
    

    public jobPoster() {
        isLoggedIn = false;
        email = "";
        password = "";
        company = "";
        phone = "";
        jobsPosted = new ArrayList<>();
        userType = "Job_Poster";
    }

    private void loadJobsPostedFromFile() {
        File jobsPostedFile = new File("jobsPosted.txt");
        if (jobsPostedFile.exists()) {
            try (Scanner fileScanner = new Scanner(jobsPostedFile)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] jobDetails = line.split(",");

                    // Assuming the structure of data in jobsPosted.txt is comma-separated
                    if (jobDetails.length >= 6 && jobDetails[5].equals(email)) { // Ensure all job fields are present
                        // Extract job details and create a new Job object
                        Job newJob = new Job();
                        newJob.setJobID(jobDetails[0]);
                        newJob.setPosition(jobDetails[1]);
                        newJob.setDescription(jobDetails[2]);
                        newJob.setSalary(jobDetails[3]);
                        newJob.setContactPerson(jobDetails[4]);
                        newJob.setContactEmail(jobDetails[5]);
                        newJob.setContactPhone(jobDetails[6]);

                        // Add the job to the jobsPosted ArrayList
                        jobsPosted.add(newJob);
                    } else {
                        System.out.println("Incomplete job data in file: " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createLoginCredentials() {
        Scanner createscanner = new Scanner(System.in);
    
        System.out.print("Enter your email: ");
        String newEmail = createscanner.nextLine();
    
        System.out.print("Enter your password: ");
        String newPassword = createscanner.nextLine();
        
        System.out.print("Enter your company name: ");
        String newCompany = createscanner.nextLine();
        
        System.out.print("Enter your phone number: ");
        String newPhone = createscanner.nextLine();
    
        this.email = newEmail;
        this.password = newPassword;
        this.company = newCompany;
        this.phone = newPhone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCompany(String comapny) {
        this.company = comapny;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getUserType() {
        return userType;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany(){
        return company;
    }

    public String getPhone(){
        return phone;
    }

    public boolean login(String userInputEmail, String userInputPassword) {
        if (email.equals(userInputEmail) && password.equals(userInputPassword)) {
            isLoggedIn = true;
            loadJobsPostedFromFile();
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
                    jobsPosted.clear();
                    loadJobsPostedFromFile();
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
    
        // Prompt for job details
        System.out.print("Enter job position: ");
        String position = scanner.nextLine();
    
        System.out.print("Enter job description: ");
        String description = scanner.nextLine();

        System.out.print("Enter job salary: ");
        String salary = scanner.nextLine();
    
        // Autofill with job poster's details
        String contactPerson = this.company; // Using job poster's company as contact person
        String contactEmail = this.email; // Using job poster's email as contact email
        String contactPhone = this.phone; // Using job poster's phone as contact phone
    
        // Creating a new job instance
        Job newJob = new Job();
        String jobID  = newJob.getJobID();
        newJob.setPosition(position);
        newJob.setDescription(description);
        newJob.setSalary(salary);
        newJob.setContactPerson(contactPerson);
        newJob.setContactEmail(contactEmail);
        newJob.setContactPhone(contactPhone);
        
 // Append the job details to jobsPosted.txt
 File file = new File("jobsPosted.txt");
try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
    if (!file.exists()) {
        file.createNewFile();
    }
    String jobDetailsString = jobID + "," + position + "," + description + "," + salary + "," + contactPerson + "," + contactEmail + "," + contactPhone;
    writer.println(jobDetailsString);
    writer.flush(); // Ensure data is written immediately
} catch (IOException e) {
    e.printStackTrace();
}

        // Display the generated jobID
        System.out.println("JobID for the posted job: " + newJob.getJobID());
    
        // Adding the new job to the list of jobsPosted and pendingJobs in the database
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
    
        // Check and update in jobsPosted list
        for (Job job : jobsPosted) {
            if (job.getJobID().equals(jobIDToUpdate)) {
                jobToUpdate = job;
                break;
            }
        }
    
        if (jobToUpdate != null) {
            // The job exists in the jobsPosted list
    
            System.out.println("Enter new description: ");
            String newDescription = scanner.nextLine();
    
            System.out.println("Enter new salary: ");
            String newSalary = scanner.nextLine();
    
            jobToUpdate.setDescription(newDescription);
            jobToUpdate.setSalary(newSalary);
    
            // Update the job in jobsPosted list
            for (Job job : jobsPosted) {
                if (job.getJobID().equals(jobIDToUpdate)) {
                    job.setDescription(newDescription);
                    job.setSalary(newSalary);
                    break;
                }
            }
    
            System.out.println("Job with ID " + jobIDToUpdate + " has been updated.");
    
            // Remove the job from database's jobs list
            Iterator<Job> iterator = database.getJobs().iterator();
            while (iterator.hasNext()) {
                Job job = iterator.next();
                if (job.getJobID().equals(jobIDToUpdate)) {
                    iterator.remove();
                    System.out.println("Job with ID " + jobIDToUpdate + " has been removed from the database jobs.");
                    break;
                }
            }
    
            // Re-add the job to database's pendingJobs list if not found
            boolean jobFoundInPendingJobs = false;
            for (Job job : database.getPendingJobs()) {
                if (job.getJobID().equals(jobIDToUpdate)) {
                    job.setDescription(newDescription);
                    job.setSalary(newSalary);
                    jobFoundInPendingJobs = true;
                    break;
                }
            }
            if (!jobFoundInPendingJobs) {
                database.addPendingJob(jobToUpdate);
                System.out.println("Job with ID " + jobIDToUpdate + " has been added to pending jobs.");
            }

            updateJobsPostedFile();
            // Update the jobs.txt file
            updateJobsFile();
        } else {
            // The job does not exist in the jobsPosted list
            System.out.println("Job with ID " + jobIDToUpdate + " not found in your posted jobs.");
        }
    }
    
    public void updateJobsFile() {
        File file = new File("jobs.txt");
    
        // Load existing jobs from the file into a list
        List<String> fileContent = new ArrayList<>();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Job job : jobsPosted) {
                String jobDetailsString = job.getJobID() + "," + job.getPosition() + "," + job.getDescription() + "," + job.getSalary() + "," + job.getContactPerson() + "," + job.getContactEmail() + "," + job.getContactPhone();
    
                // Remove existing entry if job ID exists and update the entry
                boolean found = false;
                for (int i = 0; i < fileContent.size(); i++) {
                    String[] parts = fileContent.get(i).split(",");
                    if (parts.length > 0 && parts[0].equals(job.getJobID())) {
                        fileContent.remove(i);
                        fileContent.add(jobDetailsString);
                        found = true;
                        break;
                    }
                }
    
                // If job ID not found, append the new entry
                if (!found) {
                    fileContent.add(jobDetailsString);
                }
            }
    
            // Write all contents back to the file
            for (String line : fileContent) {
                writer.println(line);
            }
            System.out.println("jobs.txt has been updated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateJobsPostedFile() {
    File file = new File("jobsPosted.txt");

    // Load existing jobs from the file into a list
    List<String> fileContent = new ArrayList<>();
    if (file.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
        for (Job job : jobsPosted) {
            String jobDetailsString = job.getJobID() + "," + job.getPosition() + "," + job.getDescription() + "," + job.getSalary() + "," + job.getContactPerson() + "," + job.getContactEmail() + "," + job.getContactPhone();

            // Check if job ID exists in the file, update if found
            boolean found = false;
            for (int i = 0; i < fileContent.size(); i++) {
                String[] parts = fileContent.get(i).split(",");
                if (parts.length > 0 && parts[0].equals(job.getJobID())) {
                    fileContent.set(i, jobDetailsString);
                    found = true;
                    break;
                }
            }

            // If job ID not found, append the new entry
            if (!found) {
                fileContent.add(jobDetailsString);
            }
        }

        // Write all contents back to the file
        for (String line : fileContent) {
            writer.println(line);
        }
        System.out.println("jobsPosted.txt has been updated.");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    

    public void deleteJobById( Database database) {
        // Remove the job from jobsPosted list
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
            System.out.println("Job with ID " + jobIDToDelete + " has been deleted from jobsPosted list.");
    
            // Remove the job from database's jobs list
            Iterator<Job> iterator = database.getJobs().iterator();
            while (iterator.hasNext()) {
                Job job = iterator.next();
                if (job.getJobID().equals(jobIDToDelete)) {
                    iterator.remove();
                    System.out.println("Job with ID " + jobIDToDelete + " has been deleted from the database jobs list.");
                    break;
                }
            }
    
            // Remove the job from database's pendingJobs list
            iterator = database.getPendingJobs().iterator();
            while (iterator.hasNext()) {
                Job job = iterator.next();
                if (job.getJobID().equals(jobIDToDelete)) {
                    iterator.remove();
                    System.out.println("Job with ID " + jobIDToDelete + " has been deleted from the database pending jobs list.");
                    break;
                }
            }
    
            // Remove the job from jobsPosted.txt file
            updateJobsPostedFile();
    
            // Remove the job from jobs.txt file
            updateJobsFile();
    
            // Remove the job from pendingJobs.txt file
            removeJobFromPendingJobsFile(jobIDToDelete);
        } else {
            System.out.println("Job with ID " + jobIDToDelete + " not found.");
        }
    }
    
    public void removeJobFromPendingJobsFile(String jobIdToDelete) {
        File file = new File("pendingJobs.txt");
    
        List<String> fileContent = new ArrayList<>();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 0 && parts[0].equals(jobIdToDelete)) {
                        continue; // Skip the line with the job ID to delete
                    }
                    fileContent.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (String line : fileContent) {
                writer.println(line);
            }
            System.out.println("Job with ID " + jobIdToDelete + " has been deleted from pendingJobs.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
