import java.util.ArrayList;

public class Database {
    private ArrayList<Job> jobs;
    private ArrayList<user> users;
    //TODO: Create an arraylist of pending jobs
    public Database(){
        this.jobs = new ArrayList<>();
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
}
