import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JobManagementGUI {
    private JFrame frame;
    private Database database;

    public JobManagementGUI() {
        database = Database.loadData();
        frame = new JFrame("Job Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton createUserButton = new JButton("Create User");
        JButton loginButton = new JButton("Login");
        JButton showJobsButton = new JButton("Show Jobs");
        JButton searchJob = new JButton("Search specific job");

        centerPanel.add(createUserButton, gbc);
        centerPanel.add(loginButton, gbc);
        centerPanel.add(showJobsButton, gbc);
        centerPanel.add(searchJob, gbc);

        createUserButton.addActionListener(e -> {
            int optionChosen = JOptionPane.showOptionDialog(
                    frame,
                    "Choose User Type to Create:",
                    "User Type",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Create JobManager", "Create JobPoster"},
                    null);

            if (optionChosen == 0) {
                JOptionPane.showMessageDialog(frame, "Creating JobManager...");
                user jobManagerUser = new jobManager();
                if (jobManagerUser instanceof jobManager) {
                    ((jobManager) jobManagerUser).createLoginCredentialsGUI();
                    database.addUser(jobManagerUser);
                }
            } else if (optionChosen == 1) {
                JOptionPane.showMessageDialog(frame, "Creating JobPoster...");
                user jobPosterUser = new jobPoster();
                if (jobPosterUser instanceof jobPoster) {
                    ((jobPoster) jobPosterUser).createLoginCredentialsGUI();
                    database.addUser(jobPosterUser);
                }
            }
        });

        loginButton.addActionListener(e -> {
            String email = JOptionPane.showInputDialog(frame, "Enter your email:");
            String password = JOptionPane.showInputDialog(frame, "Enter your password:");

            boolean loggedIn = false;
            for (user currentUser : database.getUsers()) {
                if (currentUser.getEmail().equals(email)) {
                    loggedIn = currentUser.login(email, password);
                    break;
                }
            }

            if (loggedIn) {
                user loggedInUser = null;
                for (user currentUser : database.getUsers()) {
                    if (currentUser.getEmail().equals(email)) {
                        loggedInUser = currentUser;
                        break;
                    }
                }

                if (loggedInUser != null) {
                    if (loggedInUser instanceof jobManager) {
                        ((jobManager) loggedInUser).setLoggedIn(true);
                        ((jobManager) loggedInUser).displayMenu(database);
                    } else if (loggedInUser instanceof jobPoster) {
                        ((jobPoster) loggedInUser).setLoggedIn(true);
                        ((jobPoster) loggedInUser).displayMenu(database);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid email or password. Please try again.");
            }
        });

        showJobsButton.addActionListener(e -> {
            database.printJobs();
        });

        searchJob.addActionListener(e -> {
            database.getJobByID();
        });

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

        initializeShutdownHook(); // Add the shutdown hook

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initializeShutdownHook() {
        // Add a shutdown hook to save data when the program is terminated
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            database.saveData();
            System.out.println("Data saved during program shutdown.");
        }));

        // Add a window listener to save data when the GUI window is closed
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                database.saveData();
                System.out.println("Data saved on GUI window close.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JobManagementGUI::new);
    }
}
