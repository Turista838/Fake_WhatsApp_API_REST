package MainSpring.Model;

import java.util.Calendar;

public class User {

    private String username;
    private String password;
    private Calendar loggedIn;

    public User(String username, String password, Calendar loggedIn) {
        this.username = username;
        this.password = password;
        this.loggedIn = loggedIn;
    }

    public void setUsername(String username) { this.username = username; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Calendar getLoggedIn() { return loggedIn; }
}
