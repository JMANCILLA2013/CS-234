import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        Scanner scanner = new Scanner(System.in);
        
        boolean exit = false;

        while (!exit) {
            System.out.println("Main Menu");
            System.out.println("1: Create User");
            System.out.println("2: Login User");
            System.out.println("3: Show Jobs");
            System.out.println("4: Exit");

            int mainMenuChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            user loggedInUser = null;

            switch (mainMenuChoice) {
                case 1:
                    createUserInvoker(database, scanner);
                    break;
                case 2:
                    loggedInUser = handleLogin(database, scanner);
                    if (loggedInUser != null) {
                    loggedInUser.displayMenu(database);
                    try {
                        int choice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        loggedInUser.processMenuChoice(choice, database);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.nextLine(); // Clear the invalid input from the buffer
                    }
                    }
                break;

                case 3:
                    database.printJobs();
                    break;

                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static void createUserInvoker(Database database, Scanner scanner){
        System.out.println("Choose User Type to Create:");
        System.out.println("1: Create JobManager");
        System.out.println("2: Create JobPoster");

        
        int createUserChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (createUserChoice) {
            case 1:
                user jobManagerUser = new jobManager();
                if (jobManagerUser instanceof jobManager) {
                    ((jobManager) jobManagerUser).createLoginCredentials(); // Assuming this is necessary for a jobManager to be functional
                    database.addUser(jobManagerUser);
                }
            break;
            case 2:
                user jobPosterUser = new jobPoster();
                if (jobPosterUser instanceof jobPoster) {
                    ((jobPoster) jobPosterUser).createLoginCredentials();
                    database.addUser(jobPosterUser);
                }
            break;
        }
    }

    
    public static user handleLogin(Database database, Scanner scanner) {
        System.out.print("Enter your email: ");
        String inputEmail = scanner.nextLine();
    
        System.out.print("Enter your password: ");
        String inputPassword = scanner.nextLine();
    
        for (user u : database.getUsers()) {
            if (u instanceof jobManager) {
                jobManager manager = (jobManager) u;
                if (manager.login(inputEmail, inputPassword)) {
                    System.out.println("Job Manager LOGGED IN!");
                    return manager;
                }
            } else if (u instanceof jobPoster) {
                jobPoster poster = (jobPoster) u;
                if (poster.login(inputEmail, inputPassword)) {
                    System.out.println("Job Poster LOGGED IN!");
                    return poster;
                }
            }
        }
        System.out.println("Invalid credentials. Login failed.");
        return null;
    }

}