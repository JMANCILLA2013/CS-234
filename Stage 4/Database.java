import java.io.*;
import java.util.ArrayList;

public class Database {
  private ArrayList<Job> jobs;
  private ArrayList<user> users;
  private ArrayList<Job> pendingJobs;

  public Database() {
    jobs = new ArrayList<>();
    loadJobsFromFileIfExists();
    users = new ArrayList<>();
    loadUsersFromFileIfExists();
    pendingJobs = new ArrayList<>();
    loadPendingJobsFromFileIfExists();
  }

private void loadJobsFromFileIfExists() {
    File jobsFile = new File("jobs.txt");
    if (jobsFile.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(jobsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] jobDetails = line.split(",");
                if (jobDetails.length >= 6) { // Ensure all required fields are present
                    Job job = new Job();
                    job.setPosition(jobDetails[0]);
                    job.setDescription(jobDetails[1]);
                    job.setSalary(jobDetails[2]);
                    job.setContactPerson(jobDetails[3]);
                    job.setContactEmail(jobDetails[4]);
                    job.setContactPhone(jobDetails[5]);
                    jobs.add(job);
                } else {
                    System.out.println("Incomplete job data in file: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

private void loadPendingJobsFromFileIfExists() {
    File pendingJobsFile = new File("pendingJobs.txt");
    if (pendingJobsFile.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(pendingJobsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] jobDetails = line.split(",");
                if (jobDetails.length >= 6) { // Ensure all required fields are present
                    Job pendingJob = new Job();
                    pendingJob.setPosition(jobDetails[0]);
                    pendingJob.setDescription(jobDetails[1]);
                    pendingJob.setSalary(jobDetails[2]);
                    pendingJob.setContactPerson(jobDetails[3]);
                    pendingJob.setContactEmail(jobDetails[4]);
                    pendingJob.setContactPhone(jobDetails[5]);
                    pendingJobs.add(pendingJob);
                } else {
                    System.out.println("Incomplete pending job data in file: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

private void loadUsersFromFileIfExists() {
    File usersFile = new File("users.txt");
    if (usersFile.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userCredentials = line.split(",");
                if (userCredentials.length == 3) { // Ensure all required fields are present
                    String email = userCredentials[0];
                    String password = userCredentials[1];
                    String userType = userCredentials[2];
                    
                    if (userType.equals("Job_Manager")) {
                        jobManager manager = new jobManager();
                        manager.setEmail(email);
                        manager.setPassword(password);
                        users.add(manager);
                    } 
                } 
                else if(userCredentials.length == 5){
                    String email = userCredentials[0];
                    String password = userCredentials[1];
                    String company = userCredentials[2];
                    String phone = userCredentials[3];
                    String userType = userCredentials[4];
                    if (userType.equals("Job_Poster")) {
                        jobPoster poster = new jobPoster();
                        poster.setEmail(email);
                        poster.setPassword(password);
                        poster.setPhone(phone);
                        poster.setCompany(company);
                        users.add(poster);
                    }
                } 
                else {
                    System.out.println("Incomplete user data in file: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


public void addJob(Job newJob) {
    jobs.add(newJob); // Add job to the list

    // Check if jobs.txt exists, if not, create it
    File jobsFile = new File("jobs.txt");
    if (!jobsFile.exists()) {
        try {
            jobsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Append the new job's data to jobs.txt
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(jobsFile, true))) {
        String jobData = newJob.getJobID() + "," + newJob.getPosition() + "," + newJob.getDescription() +
                         "," + newJob.getSalary() + "," + newJob.getContactPerson() + "," +
                         newJob.getContactEmail() + "," + newJob.getContactPhone() + "\n";
        writer.write(jobData);
    } catch (IOException e) {
        e.printStackTrace();
    }
}


  public void removeJob(Job jobToRemove) {
        File inputFile = new File("jobs.txt");
        File tempFile = new File("tempJobs.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] jobDetails = line.split(",");
                // Assuming job ID is at index 0 in the line (adjust as per your file format)
                if (!jobDetails[0].equals(jobToRemove.getJobID())) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Delete the original file and rename the temp file to the original file name
        if (!inputFile.delete()) {
            System.out.println("Could not delete the original file.");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename the temp file.");
        }
    }

  public ArrayList<Job> getJobs() {
      return jobs;
  }

  public void addUser(user newUser) {
    users.add(newUser); // Add user to the list

    // Check if users.txt exists, if not, create it
    File usersFile = new File("users.txt");
    if (!usersFile.exists()) {
        try {
            usersFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Append the new user's data to users.txt
    if(newUser instanceof jobManager){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile, true))) {
            String userData = newUser.getEmail() + "," + newUser.getPassword() + "," + newUser.getUserType() + "\n";
            writer.write(userData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    else if(newUser instanceof jobPoster){
        jobPoster poster = (jobPoster) newUser;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile, true))) {
            String userData = poster.getEmail() + "," + poster.getPassword() + "," +
                    poster.getCompany() + "," + poster.getPhone() + "," + poster.getUserType() + "\n";
            writer.write(userData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 public ArrayList<user> getUsers() {
    return users;
}

 public void printUsers() {
    ArrayList<user> usersCopy = new ArrayList<>(this.users);

    if (usersCopy.isEmpty()) {
        System.out.println("The user list is empty.");
    } else {
        System.out.println("Contents of the user list:");
        for (user u : usersCopy) {
            System.out.println("----------------------------------------");
            System.out.println("User's email:" + u.getEmail());
            System.out.println("Is User Logged In: " + u.isLoggedIn());
            System.out.println("What type of user: " + u.getUserType());
            System.out.println("----------------------------------------");
        }
    }
    }

 public void printJobs() {
    ArrayList<Job> jobsCopy = new ArrayList<>(this.jobs);

    if (jobsCopy.isEmpty()) {
        System.out.println("The job list is empty.");
    } else {
        System.out.println("Current Job Postings:");
        for (Job j : jobsCopy) {
            System.out.println("----------------------------------------");
            System.out.println("Position: " + j.getPosition());
            System.out.println("Description: " + j.getDescription());
            System.out.println("Salary: " + j.getSalary());
            System.out.println("Contact Person: " + j.getContactPerson());
            System.out.println("Contact Email: " + j.getContactEmail());
            System.out.println("Contact Phone: " + j.getContactPhone());
            System.out.println("----------------------------------------");
        }
    }
    }

    public void addPendingJob(Job pendingJob) {
        pendingJobs.add(pendingJob); // Add pending job to the list

        // Check if pendingJobs.txt exists, if not, create it
        File pendingJobsFile = new File("pendingJobs.txt");
        if (!pendingJobsFile.exists()) {
            try {
                pendingJobsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Append the pending job's data to pendingJobs.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pendingJobsFile, true))) {
            String jobData = pendingJob.getJobID() + "," + pendingJob.getPosition() + "," +
                             pendingJob.getDescription() + "," + pendingJob.getSalary() + "," +
                             pendingJob.getContactPerson() + "," + pendingJob.getContactEmail() + "," +
                             pendingJob.getContactPhone() + "\n";
            writer.write(jobData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Job> getPendingJobs() {
        return pendingJobs;
    }
}