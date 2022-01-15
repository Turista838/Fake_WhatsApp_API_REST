package MainSpring.Controllers;

import MainSpring.Model.User;
import MainSpring.Model.AuthenticatedUsers;
import MainSpring.SGBD.ConnectorSGBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;

@RestController
public class SessionController {

    @Autowired
    private AuthenticatedUsers authenticatedUsers;

    @PostMapping("session")
    public ResponseEntity login(@RequestBody User user){

        if(user != null){

            ConnectorSGBD connectorSGBD = new ConnectorSGBD();
            Statement stmt = connectorSGBD.getStmt();
            ResultSet rs;

            try{
                rs = stmt.executeQuery("SELECT EXISTS(SELECT * from utilizador WHERE Username = \"" + user.getUsername() + "\" AND Password = \"" + user.getPassword() + "\");");
                rs.next();
                if(rs.getBoolean(1)) {

                    String token = hashPass(user.getPassword());

                    authenticatedUsers.hashMap.put(token, new User(user.getUsername(), user.getPassword(), GregorianCalendar.getInstance()));

                    return ResponseEntity.ok(token);
                }
                else
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong credentials");

            } catch (SQLException | NoSuchAlgorithmException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.CONFLICT).body("SQL query failed");
            }

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No credentials provided");
    }

    private String hashPass(String password) throws NoSuchAlgorithmException {

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        StringBuffer sb = new StringBuffer();

        random.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));


        for (int i = 0; i < hashedPassword.length; i++) { //só para ficar com caracteres legíveis
            sb.append(Integer.toString((hashedPassword[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();

    }

}
