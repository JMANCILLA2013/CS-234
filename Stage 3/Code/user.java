public interface user {
    boolean login(String userInputEmail, String userInputPassword);
    void logout();
    void displayMenu(Database database);
    void processMenuChoice(int choice, Database database);
    String getEmail();
    boolean isLoggedIn();
    String getUserType();
}