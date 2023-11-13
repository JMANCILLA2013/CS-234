import java.util.ArrayList;

public class Database {
    private ArrayList<Job> jobs;
    private ArrayList<user> users;
    private ArrayList<Job> pendingJobs;
    public Database(){
        this.jobs = new ArrayList<>();
        this.users = new ArrayList<>();
        this.pendingJobs = new ArrayList<>();
    }

    public void addJob(Job job){
        jobs.add(job);
    }
    public void removeJob(Job job){
        jobs.remove(job);
    }
    public void addUser(user user){
        users.add(user);
    }

    //TODO: ADD PENDING JOB

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public ArrayList<Job> getPendingJobs() {
        return pendingJobs;
    }
}
