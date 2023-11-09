import java.util.Scanner;
public class jobManager implements user{
    private boolean isLoggedIn;
    private String email;
    private String password;

    public jobManager(){
        isLoggedIn = false;
        this.createLoginCredentials();
    }

    public void createLoginCredentials(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your email: ");
        String newEmail = scanner.nextLine();

        System.out.print("Enter your password: ");
        String newPassword = scanner.nextLine();
        scanner.close();
        this.email = newEmail;
        this.password = newPassword;
    }
    public String getEmail(){
        return email;
    }

    public void login(String userInputEmail, String userInputPassword) {
        boolean validCredentials = userInputEmail.equals(email) && userInputPassword.equals(password);

        if (validCredentials) {
            isLoggedIn = true;
            System.out.println("LOGGED IN!");
        }

    }

    public void logout(){
        isLoggedIn = false;
    }

    public void deleteJob(boolean isLoggedIn){
        //If user is loggedIn run code    
    }
}
