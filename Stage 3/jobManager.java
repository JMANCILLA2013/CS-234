import java.util.Scanner;
public class jobManager {
    private boolean isLoggedIn;
    private String email;
    private String password;

    public jobManager(String email, String password){
        isLoggedIn = false;
        this.email = email;
        this.password = password;
    }

    public void createLoginCredentials(){
        //TODO: Add functionality listed below
        //prompt user to give a password and email and do error handling
    
    }
    

    public boolean login(String userInputEmail, String userinputPassword){
        //TODO: Add functionality listed below
        // if  userinputEmail == email && userInputPassword == password
        // then set is LoggedIn to true
        // else return error 
        return isLoggedIn;
    }

    
    public boolean logout(){
        isLoggedIn = false;
        return isLoggedIn;
    }

    public void deleteJob(boolean isLoggedIn){
        //If user is loggedIn run code    
    }
}
