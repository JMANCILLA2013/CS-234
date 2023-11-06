import java.util.ArrayList;

public class Database {
    private ArrayList<Job> jobs;
    // TODO: Create an arraylist of users
    //TODO: Create an arraylist of pending jobs
    public Database(){
        this.jobs = new ArrayList<>();
    }

    public void addJob(Job job){
        jobs.add(job);
    }

    //TODO: create an addUser
}
