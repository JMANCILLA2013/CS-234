import java.util.ArrayList;
import java.util.Scanner;
public class jobPoster implements user{
    private boolean isLoggedIn;
    private String email;
    private String password;
    private ArrayList<Job> jobsPosted;

    public jobPoster(String email, String password){
        isLoggedIn = false;
        this.email = email;
        this.password = password;
    }

    public void createLoginCredentials(){
        Scanner createscanner = new Scanner(System.in);

        System.out.print("Enter your email: ");
        String newEmail = createscanner.nextLine();

        System.out.print("Enter your password: ");
        String newPassword = createscanner.nextLine();
        createscanner.close();
        this.email = newEmail;
        this.password = newPassword;
    }

    public String getEmail(){
        return email;
    }
    
    public void login(String userInputEmail, String userInputPassword){
        boolean validCredentials = userInputEmail.equals(email) && userInputPassword.equals(password);

        if (validCredentials) {
            isLoggedIn = true;
            System.out.println("LOGGED IN!");
        }
    }

    public  void logout(){
        isLoggedIn = false;
    }
    //TODO: Allow jobposter to create a job
    //TODO: Allow jobposter to see all jobs they have posted
}
