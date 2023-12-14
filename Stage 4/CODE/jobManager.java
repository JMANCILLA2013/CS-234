import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class jobManager implements user {
    private boolean isLoggedIn;
    private String email;
    private String password;
    private String userType;

    public jobManager() {
        isLoggedIn = false;
        email = "";
        password ="";
        userType ="Job_Manager";
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
    public String getEmail(){
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

    // Setter for isLoggedIn
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
    
    // Implements the logout method as required by the user interface
    @Override
    public void logout() {
        isLoggedIn = false;
    }

    @Override
    public void displayMenu(Database database) {
        JFrame menuFrame = new JFrame("Job Manager Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(300, 200);
    
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JButton approveRejectButton = new JButton("Approve/Reject Pending Job");
        JButton showUsersButton = new JButton("Show all Users");
        JButton returnToLoginButton = new JButton("Return to Login Screen");
    
        panel.add(approveRejectButton);
        panel.add(showUsersButton);
        panel.add(returnToLoginButton);
    
        approveRejectButton.addActionListener(e -> {
            // Logic for approve/reject pending job
            approveRejectJob(database);
        });
    
        showUsersButton.addActionListener(e -> {
            // Logic to display all users
            database.displayUsers();
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

    private void approveRejectJob(Database database) {
        // Access the list of pending jobs
        ArrayList<Job> pendingJobs = database.getPendingJobs();
        JFrame approvalFrame = new JFrame("Approve/Reject Jobs");
        approvalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        approvalFrame.setSize(600, 400);
        approvalFrame.setLayout(new GridLayout(0, 1));

        for (Job job : pendingJobs) {
            JPanel jobPanel = new JPanel(new GridLayout(0, 2));

            JLabel jobIDLabel = new JLabel("Job ID: " + job.getJobID());
            JLabel positionLabel = new JLabel("Position: " + job.getPosition());
            JLabel descriptionLabel = new JLabel("Description: " + job.getDescription());
            JLabel salaryLabel = new JLabel("Salary: " + job.getSalary());
            JLabel contactPersonLabel = new JLabel("Contact Person: " + job.getContactPerson());
            JLabel contactEmailLabel = new JLabel("Contact Email: " + job.getContactEmail());
            JLabel contactPhoneLabel = new JLabel("Contact Phone: " + job.getContactPhone());

            JButton approveButton = new JButton("Approve");
            JButton rejectButton = new JButton("Reject");

            approveButton.addActionListener(e -> {
                database.addJob(job);
                pendingJobs.remove(job);
                JOptionPane.showMessageDialog(null, "Job with ID " + job.getJobID() + " has been approved.");
                approvalFrame.dispose();
            });

            rejectButton.addActionListener(e -> {
                pendingJobs.remove(job);
                JOptionPane.showMessageDialog(null, "Job with ID " + job.getJobID() + " has been rejected.");
                approvalFrame.dispose();
            });

            jobPanel.add(jobIDLabel);
            jobPanel.add(new JLabel()); // Empty label for spacing
            jobPanel.add(positionLabel);
            jobPanel.add(descriptionLabel);
            jobPanel.add(salaryLabel);
            jobPanel.add(contactPersonLabel);
            jobPanel.add(contactEmailLabel);
            jobPanel.add(contactPhoneLabel);
            jobPanel.add(approveButton);
            jobPanel.add(rejectButton);

            approvalFrame.add(jobPanel);
        }

        approvalFrame.setLocationRelativeTo(null);
        approvalFrame.setVisible(true);
    }

}