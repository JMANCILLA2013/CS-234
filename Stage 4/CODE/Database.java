import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Database implements Serializable {
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

    public void displayUsers() {
        JFrame usersFrame = new JFrame("User List");
        usersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        usersFrame.setSize(600, 400);

        ArrayList<user> usersCopy = new ArrayList<>(this.users);

        if (usersCopy.isEmpty()) {
            JOptionPane.showMessageDialog(usersFrame, "The user list is empty.");
        } else {
            // Creating a table model
            String[] columnNames = {"Email", "Is Logged In", "User Type"};
            Object[][] rowData = new Object[usersCopy.size()][3];

            // Populating the table data
            for (int i = 0; i < usersCopy.size(); i++) {
                user u = usersCopy.get(i);
                rowData[i][0] = u.getEmail();
                rowData[i][1] = u.isLoggedIn();
                rowData[i][2] = u.getUserType();
            }

            JTable table = new JTable(rowData, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            usersFrame.add(scrollPane);
        }

        usersFrame.setLocationRelativeTo(null);
        usersFrame.setVisible(true);
    }

    public Job getJobByID() {
        JFrame frame = new JFrame("Enter Job ID");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel("Enter Job ID:");
        JTextField jobIdField = new JTextField();
        JButton searchButton = new JButton("Search");

        frame.add(label);
        frame.add(jobIdField);
        frame.add(searchButton);

        searchButton.addActionListener(e -> {
            String jobID = jobIdField.getText();
            Job foundJob = searchJobByID(jobID);

            if (foundJob != null) {
                displayJobData(foundJob);
            } else {
                JOptionPane.showMessageDialog(null, "Job not found.");
            }
            frame.dispose();
        });

        frame.setVisible(true);

        return null;
    }

    private Job searchJobByID(String jobID) {
        // Search for the job in the database
        for (Job job : jobs) {
            if (job.getJobID().equals(jobID)) {
                return job;
            }
        }
        return null; // Job not found
    }

    private void displayJobData(Job job) {
        JFrame jobFrame = new JFrame("Job Details");
        jobFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jobFrame.setSize(400, 300);
        jobFrame.setLayout(new GridLayout(0, 2));

        JLabel positionLabel = new JLabel("Position: " + job.getPosition());
        JLabel descriptionLabel = new JLabel("Description: " + job.getDescription());
        JLabel salaryLabel = new JLabel("Salary: " + job.getSalary());
        JLabel contactPersonLabel = new JLabel("Contact Person: " + job.getContactPerson());
        JLabel contactEmailLabel = new JLabel("Contact Email: " + job.getContactEmail());
        JLabel contactPhoneLabel = new JLabel("Contact Phone: " + job.getContactPhone());

        jobFrame.add(positionLabel);
        jobFrame.add(descriptionLabel);
        jobFrame.add(salaryLabel);
        jobFrame.add(contactPersonLabel);
        jobFrame.add(contactEmailLabel);
        jobFrame.add(contactPhoneLabel);
        jobFrame.setVisible(true);
    }

    public void printJobs() {
        ArrayList<Job> jobsCopy = new ArrayList<>(this.jobs);
        JFrame jobsFrame = new JFrame("Jobs");
        jobsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jobsFrame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Header
        JLabel headerLabel = new JLabel("Job Details");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(headerLabel);

        // Display each job
        for (Job job : jobsCopy) {
            JLabel jobIDLabel = new JLabel("Job ID: " + job.getJobID());
            JLabel positionLabel = new JLabel("Position: " + job.getPosition());
            JLabel descriptionLabel = new JLabel("Description: " + job.getDescription());
            JLabel salaryLabel = new JLabel("Salary: " + job.getSalary());
            JLabel contactPersonLabel = new JLabel("Contact Person: " + job.getContactPerson());
            JLabel contactEmailLabel = new JLabel("Contact Email: " + job.getContactEmail());
            JLabel contactPhoneLabel = new JLabel("Contact Phone: " + job.getContactPhone());

            // Add job details labels to the panel
            panel.add(jobIDLabel);
            panel.add(positionLabel);
            panel.add(descriptionLabel);
            panel.add(salaryLabel);
            panel.add(contactPersonLabel);
            panel.add(contactEmailLabel);
            panel.add(contactPhoneLabel);

            // Add spacing between job details
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        jobsFrame.getContentPane().add(scrollPane);
        jobsFrame.setLocationRelativeTo(null);
        jobsFrame.setVisible(true);
    }

    public void addPendingJob(Job job) {
        pendingJobs.add(job);
    }

    public ArrayList<Job> getPendingJobs() {
        return pendingJobs;
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.ser"))) {
            oos.writeObject(this);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Database loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("database.ser"))) {
            return (Database) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Database(); // Create a new instance if file not found or error occurs
        }
    }
}
