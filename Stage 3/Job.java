public class Job{
    private String JobID;
    private String Position;
    private String Description;
    private String Salary;
    private JobIDGenerator generator = new JobIDGenerator();
    //Constructor
    public Job(){
        this.JobID = generator.generateJobID();
        this.Position = "";
        this.Description = "";
        this.Salary = "";
    }

    public void setPosition(String enteredPosition){
        Position = enteredPosition;
    }

    public void setDescription(String enteredDescription){
        Description = enteredDescription;
    }

    public void setSalary(String enteredSalary){
        Salary = enteredSalary;
    }
    public String getJobID(){    
        return JobID;
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
