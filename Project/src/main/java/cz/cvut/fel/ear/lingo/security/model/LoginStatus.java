package cz.cvut.fel.ear.lingo.security.model;

public class LoginStatus {

    private boolean loggedIn;
    private String username;
    private String errorMessage;
    private boolean success;

    public LoginStatus(boolean loggedIn, boolean success, String username, String errorMessage) {
        this.loggedIn = loggedIn;
        this.username = username;
        this.errorMessage = errorMessage;
        this.success = success;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
