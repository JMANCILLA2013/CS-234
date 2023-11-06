public class Job{
    private String JobID;
    private String Position;
    private String Description;
    private String Salary;

    //Constructor
    public Job(String JobID, String Position, String Description, String Salary){
        this.JobID = JobID;
        this.Position = "";
        this.Description = "";
        this.Salary = "";
    }

    //TODO: RandomlyGenerateJobID

    public void setPosition(String enteredPosition){
        Position = enteredPosition;
    }

    public void setDescription(String enteredDescription){
        Description = enteredDescription;
    }

    public void setSalary(String enteredSalary){
        Salary = enteredSalary;
    }

    public String getSalary(){
        return Salary;
    }

    public String getDescription(){
        return Description;
    }

    public String getPosition(){
        return Position;
    }
}
