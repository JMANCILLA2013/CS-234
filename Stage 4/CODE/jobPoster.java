import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class jobPoster implements user, Serializable {
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

    public void createLoginCredentialsGUI() {
    JFrame loginFrame = new JFrame("Create Login");
    loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    loginFrame.setSize(300, 200);

    JPanel panel = new JPanel(new GridLayout(3, 2));
    JTextField emailField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton submitButton = new JButton("Submit");

    panel.add(new JLabel("Email: "));
    panel.add(emailField);
    panel.add(new JLabel("Password: "));
    panel.add(passwordField);
    panel.add(new JLabel()); // Placeholder
    panel.add(submitButton);

    submitButton.addActionListener(e -> {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        this.email = email;
        this.password = password;
        loginFrame.dispose(); // Close the login frame after submission
    });

    loginFrame.getContentPane().add(panel);
    loginFrame.setLocationRelativeTo(null);
    loginFrame.setVisible(true);
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

    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
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
        JFrame menuFrame = new JFrame("Job Poster Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(300, 200);
    
        JPanel panel = new JPanel(new GridLayout(5, 1));
        JButton createJobButton = new JButton("Create Job");
        JButton seeAllJobsButton = new JButton("See All Posted Jobs");
        JButton deleteJobButton = new JButton("Delete a Posted Job");
        JButton updateJobButton = new JButton("Update a Posted Job");
        JButton returnToLoginButton = new JButton("Return to Login Screen");
    
        panel.add(createJobButton);
        panel.add(seeAllJobsButton);
        panel.add(deleteJobButton);
        panel.add(updateJobButton);
        panel.add(returnToLoginButton);
    
        createJobButton.addActionListener(e -> {
            // Logic for creating a job
            createJob(database);
        });
    
        seeAllJobsButton.addActionListener(e -> {
            // Logic to display all posted jobs
            seeAllJobs(jobsPosted);
        });
    
        deleteJobButton.addActionListener(e -> {
            // Logic to delete a posted job
            deletePostedJob(database);
        });
    
        updateJobButton.addActionListener(e -> {
            // Logic to update a posted job
            updateJob(database);
        });
    
        returnToLoginButton.addActionListener(e -> {
            // Logic to return to login screen
            logout();
            menuFrame.dispose();
        });
    
        menuFrame.getContentPane().add(panel);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);
    }
   
    public void createJob(Database database) {
        JFrame createJobFrame = new JFrame("Create Job");
        createJobFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createJobFrame.setSize(400, 300);
    
        JPanel panel = new JPanel(new GridLayout(7, 2));
        JLabel positionLabel = new JLabel("Position:");
        JTextField positionField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();
        JLabel salaryLabel = new JLabel("Salary:");
        JTextField salaryField = new JTextField();
        JLabel contactPersonLabel = new JLabel("Contact Person:");
        JTextField contactPersonField = new JTextField();
        JLabel contactEmailLabel = new JLabel("Contact Email:");
        JTextField contactEmailField = new JTextField();
        JLabel contactPhoneLabel = new JLabel("Contact Phone:");
        JTextField contactPhoneField = new JTextField();
    
        JButton submitButton = new JButton("Submit");
    
        panel.add(positionLabel);
        panel.add(positionField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(salaryLabel);
        panel.add(salaryField);
        panel.add(contactPersonLabel);
        panel.add(contactPersonField);
        panel.add(contactEmailLabel);
        panel.add(contactEmailField);
        panel.add(contactPhoneLabel);
        panel.add(contactPhoneField);
        panel.add(submitButton);
    
        submitButton.addActionListener(e -> {
            Job newJob = new Job();
            newJob.setPosition(positionField.getText());
            newJob.setDescription(descriptionField.getText());
            newJob.setSalary(salaryField.getText());
            newJob.setContactPerson(contactPersonField.getText());
            newJob.setContactEmail(contactEmailField.getText());
            newJob.setContactPhone(contactPhoneField.getText());
    
            jobsPosted.add(newJob); // Ensure it's added to the posted jobs in the jobPoster
            database.addPendingJob(newJob);// Ensure it's added to the pending jobs in the Database
            JOptionPane.showMessageDialog(createJobFrame, "Job created successfully with an id of:" + newJob.getJobID());
            createJobFrame.dispose();
        });
    
        createJobFrame.getContentPane().add(panel);
        createJobFrame.setLocationRelativeTo(null);
        createJobFrame.setVisible(true);
    }
    
    

    public void seeAllJobs(ArrayList<Job> jobsPosted) {
        JFrame jobsFrame = new JFrame("Posted Jobs");
        jobsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jobsFrame.setSize(400, 300);
    
        JPanel panel = new JPanel(new GridLayout(9, 2)); // Increased rows to accommodate Job ID
        JLabel jobIdLabel = new JLabel("Job ID:");
        JLabel positionLabel = new JLabel("Position:");
        JLabel descriptionLabel = new JLabel("Description:");
        JLabel salaryLabel = new JLabel("Salary:");
        JLabel contactPersonLabel = new JLabel("Contact Person:");
        JLabel contactEmailLabel = new JLabel("Contact Email:");
        JLabel contactPhoneLabel = new JLabel("Contact Phone:");
    
        JLabel jobIdText = new JLabel(); // Display Job ID as a JLabel
        JLabel positionText = new JLabel();
        JLabel descriptionText = new JLabel();
        JLabel salaryText = new JLabel();
        JLabel contactPersonText = new JLabel();
        JLabel contactEmailText = new JLabel();
        JLabel contactPhoneText = new JLabel();
    
        JButton previousButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
    
        final int[] currentJobIndex = {0};
    
        // Display the first job
        updateJobDetails(jobsPosted.get(currentJobIndex[0]), jobIdText, positionText, descriptionText, salaryText, contactPersonText, contactEmailText, contactPhoneText);
    
        // ActionListener for the previous button
        previousButton.addActionListener(e -> {
            if (currentJobIndex[0] > 0) {
                currentJobIndex[0]--;
                updateJobDetails(jobsPosted.get(currentJobIndex[0]), jobIdText, positionText, descriptionText, salaryText, contactPersonText, contactEmailText, contactPhoneText);
            }
        });
    
        // ActionListener for the next button
        nextButton.addActionListener(e -> {
            if (currentJobIndex[0] < jobsPosted.size() - 1) {
                currentJobIndex[0]++;
                updateJobDetails(jobsPosted.get(currentJobIndex[0]), jobIdText, positionText, descriptionText, salaryText, contactPersonText, contactEmailText, contactPhoneText);
            }
        });
    
        panel.add(jobIdLabel);
        panel.add(jobIdText);
        panel.add(positionLabel);
        panel.add(positionText);
        panel.add(descriptionLabel);
        panel.add(descriptionText);
        panel.add(salaryLabel);
        panel.add(salaryText);
        panel.add(contactPersonLabel);
        panel.add(contactPersonText);
        panel.add(contactEmailLabel);
        panel.add(contactEmailText);
        panel.add(contactPhoneLabel);
        panel.add(contactPhoneText);
        panel.add(previousButton);
        panel.add(nextButton);
    
        jobsFrame.getContentPane().add(panel);
        jobsFrame.setLocationRelativeTo(null);
        jobsFrame.setVisible(true);
    }
    
    
    public void updateJobDetails(Job job, JLabel jobIdLabel, JLabel positionLabel, JLabel descriptionLabel,
                             JLabel salaryLabel, JLabel contactPersonLabel, JLabel contactEmailLabel,
                             JLabel contactPhoneLabel) {
    jobIdLabel.setText( job.getJobID());
    positionLabel.setText(job.getPosition());
    descriptionLabel.setText(job.getDescription());
    salaryLabel.setText(job.getSalary());
    contactPersonLabel.setText(job.getContactPerson());
    contactEmailLabel.setText(job.getContactEmail());
    contactPhoneLabel.setText(job.getContactPhone());
}

public void updateJob(Database database) {
    JFrame updateJobFrame = new JFrame("Update Job");
    updateJobFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    updateJobFrame.setSize(400, 300);
    updateJobFrame.setLayout(new GridLayout(0, 2));

    JLabel jobIDLabel = new JLabel("Enter Job ID to update: ");
    JTextField jobIDField = new JTextField();
    JButton updateButton = new JButton("Update");

    JLabel positionLabel = new JLabel("Position: ");
    JLabel descriptionLabel = new JLabel("Description: ");
    JLabel salaryLabel = new JLabel("Salary: ");
    JLabel contactPersonLabel = new JLabel("Contact Person: ");
    JLabel contactEmailLabel = new JLabel("Contact Email: ");
    JLabel contactPhoneLabel = new JLabel("Contact Phone: ");

    JTextField newPositionField = new JTextField();
    JTextField newDescriptionField = new JTextField();
    JTextField newSalaryField = new JTextField();
    JTextField newContactPersonField = new JTextField();
    JTextField newContactEmailField = new JTextField();
    JTextField newContactPhoneField = new JTextField();

    updateButton.addActionListener(e -> {
        String jobIDToUpdate = jobIDField.getText();

        Job jobToUpdate = null;
        // Find the job to update in the database
        for (Job job : jobsPosted) {
            if (job.getJobID().equals(jobIDToUpdate)) {
                jobToUpdate = job;
                break;
            }
        }

        for (Job job : database.getJobs()) {
                if (job.getJobID().equals(jobIDToUpdate)) {
                    // Job found in jobs, remove it
                    database.getJobs().remove(job);
                    // Add the updated job back to pendingJobs
                    database.addPendingJob(jobToUpdate);
                    break;
                }
            }

        if (jobToUpdate != null) {
            // Update job details
            jobToUpdate.setPosition(newPositionField.getText());
            jobToUpdate.setDescription(newDescriptionField.getText());
            jobToUpdate.setSalary(newSalaryField.getText());
            jobToUpdate.setContactPerson(newContactPersonField.getText());
            jobToUpdate.setContactEmail(newContactEmailField.getText());
            jobToUpdate.setContactPhone(newContactPhoneField.getText());

            
            // Update pending jobs
            for (Job pendingJob : database.getPendingJobs()) {
                if (pendingJob.getJobID().equals(jobIDToUpdate)) {
                // Update job details
                    pendingJob.setPosition(newPositionField.getText());
                    pendingJob.setDescription(newDescriptionField.getText());
                    pendingJob.setSalary(newSalaryField.getText());
                    pendingJob.setContactPerson(newContactPersonField.getText());
                    pendingJob.setContactEmail(newContactEmailField.getText());
                    pendingJob.setContactPhone(newContactPhoneField.getText());
                    break;
            }
        }
            // Provide feedback to the user
            JOptionPane.showMessageDialog(null, "Job updated successfully!");
        } else {
            // Handle case where job ID is not found
            JOptionPane.showMessageDialog(null, "Job ID not found!");
        }
    });

    updateJobFrame.add(jobIDLabel);
    updateJobFrame.add(jobIDField);
    updateJobFrame.add(updateButton);
    updateJobFrame.add(new JLabel()); // Empty label for alignment
    updateJobFrame.add(positionLabel);
    updateJobFrame.add(newPositionField);
    updateJobFrame.add(descriptionLabel);
    updateJobFrame.add(newDescriptionField);
    updateJobFrame.add(salaryLabel);
    updateJobFrame.add(newSalaryField);
    updateJobFrame.add(contactPersonLabel);
    updateJobFrame.add(newContactPersonField);
    updateJobFrame.add(contactEmailLabel);
    updateJobFrame.add(newContactEmailField);
    updateJobFrame.add(contactPhoneLabel);
    updateJobFrame.add(newContactPhoneField);

    updateJobFrame.setVisible(true);
}

    
public void deletePostedJob(Database database) {
    JFrame deleteJobFrame = new JFrame("Delete Job");
    deleteJobFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    deleteJobFrame.setSize(400, 200);
    deleteJobFrame.setLayout(new GridLayout(3, 2));

    JLabel jobIDLabel = new JLabel("Enter Job ID to delete: ");
    JTextField jobIDField = new JTextField();
    JButton deleteButton = new JButton("Delete");

    deleteButton.addActionListener(e -> {
        String jobIDToDelete = jobIDField.getText();

        Iterator<Job> iterator = jobsPosted.iterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            if (job.getJobID().equals(jobIDToDelete)) {
                iterator.remove(); // Remove the job using the iterator
                JOptionPane.showMessageDialog(null, "Job deleted successfully from Posted Jobs!");
                break;
            }
        }

        // Check and delete from other lists in the database if needed
        for (Job job : database.getJobs()) {
            if (job.getJobID().equals(jobIDToDelete)) {
                database.getJobs().remove(job);
                JOptionPane.showMessageDialog(null, "Job deleted successfully from Jobs!");
                break;
            }
        }

        for (Job job : database.getPendingJobs()) {
            if (job.getJobID().equals(jobIDToDelete)) {
                database.getPendingJobs().remove(job);
                JOptionPane.showMessageDialog(null, "Job deleted successfully from Pending Jobs!");
                break;
            }
        }
    });

    deleteJobFrame.add(jobIDLabel);
    deleteJobFrame.add(jobIDField);
    deleteJobFrame.add(deleteButton);
    deleteJobFrame.add(new JLabel()); // Empty label for alignment
    deleteJobFrame.setVisible(true);
}
}
