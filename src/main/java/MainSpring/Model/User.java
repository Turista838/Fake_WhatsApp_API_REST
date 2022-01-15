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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
