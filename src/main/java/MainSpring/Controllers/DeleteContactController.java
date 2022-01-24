package MainSpring.Controllers;

import MainSpring.Model.AuthenticatedUsers;
import MainSpring.Model.User;
import MainSpring.SGBD.ConnectorSGBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@RestController
@RequestMapping("contact")
public class DeleteContactController {

    @Autowired
    private AuthenticatedUsers authenticatedUsers;

    @DeleteMapping
    public ResponseEntity deleteContact(@RequestHeader("Authorization") String token){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must put contact/{contact to delete} to delete contact");
    }

    @DeleteMapping("{contact}")
    public ResponseEntity deleteContact(@RequestHeader("Authorization") String token, @PathVariable("contact") String contact){

        ArrayList<String> reply = new ArrayList<>();
        ConnectorSGBD connectorSGBD = new ConnectorSGBD();
        Statement stmt = connectorSGBD.getStmt();
        ResultSet rs;

        User user = authenticatedUsers.hashMap.get(token);

        try{
            rs = stmt.executeQuery("SELECT EXISTS(SELECT * from utilizador WHERE Username = \"" + contact + "\");");
            rs.next();
            if (rs.getBoolean(1)) {
                stmt.executeUpdate("DELETE FROM tem_o_contacto WHERE Username = \"" + user.getUsername() + "\" AND Contacto = \"" + contact + "\";");
                stmt.executeUpdate("DELETE FROM tem_o_contacto WHERE Username = \"" + contact + "\" AND Contacto = \"" + user.getUsername() + "\";");
                reply.add("Contact successfully deleted");
                return ResponseEntity.ok(reply);
            }
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contact not found");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("SQL query failed");
        }

    }
}
