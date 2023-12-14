import java.io.Serializable;

public class Job implements Serializable {
    private String jobID;
    private String position;
    private String description;
    private String salary;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
    private transient JobIDGenerator generator = new JobIDGenerator();

    public Job() {
        this.jobID = generator.generateJobID();
        this.position = "";
        this.description = "";
        this.salary = "";
        this.contactPerson = "";
        this.contactEmail = "";
        this.contactPhone = "";
    }

    public void setPosition(String enteredPosition) {
        position = enteredPosition;
    }

    public void setDescription(String enteredDescription) {
        description = enteredDescription;
    }

    public void setSalary(String enteredSalary) {
        salary = enteredSalary;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getJobID() {
        return jobID;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    public String getSalary() {
        return salary;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }
}


