import java.util.ArrayList;

public class Database {
  private ArrayList<Job> jobs;
  private ArrayList<user> users;
  private ArrayList<Job> pendingJobs;

  public Database() {
      jobs = new ArrayList<>();
      users = new ArrayList<>();
      pendingJobs = new ArrayList<>();
  }

  public void addJob(Job job) {
      jobs.add(job);
  }

  public void removeJob(Job job) {
      jobs.remove(job);
  }

  public ArrayList<Job> getJobs() {
      return jobs;
  }

  public void addUser(user newUser) {
    users.add(newUser);
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

    public void addPendingJob(Job job) {
        pendingJobs.add(job);
    }
    
    public ArrayList<Job> getPendingJobs() {
        return pendingJobs;
    }
}