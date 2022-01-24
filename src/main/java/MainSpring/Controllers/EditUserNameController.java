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
@RequestMapping("name")
public class EditUserNameController {

    @Autowired
    private AuthenticatedUsers authenticatedUsers;

    @PutMapping
    public ResponseEntity editUsername(@RequestHeader("Authorization") String token){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must put name/{new username} to edit your username");
    }

    @PutMapping("{newUsername}")
    public ResponseEntity editUsername(@RequestHeader("Authorization") String token, @PathVariable("newUsername") String newUsername){

        ArrayList<String> reply = new ArrayList<>();
        ConnectorSGBD connectorSGBD = new ConnectorSGBD();
        Statement stmt = connectorSGBD.getStmt();
        ResultSet rs;

        User user = authenticatedUsers.hashMap.get(token);

        try{
            rs = stmt.executeQuery("SELECT EXISTS(SELECT * from utilizador WHERE Username = \"" + user.getUsername() + "\");");
            rs.next();
            if (rs.getBoolean(1)) {
                stmt.executeUpdate("SET SQL_SAFE_UPDATES = 0;");
                stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;");
                stmt.executeUpdate("UPDATE utilizador SET Username = \"" + newUsername + "\" WHERE Username = \"" + user.getUsername() + "\";");
                stmt.executeUpdate("UPDATE grupo SET User_Admin = \"" + newUsername + "\" WHERE User_Admin = \"" + user.getUsername() + "\";");
                stmt.executeUpdate("UPDATE inclui SET Utilizador_Username = \"" + newUsername + "\" WHERE Utilizador_Username = \"" + user.getUsername() + "\";");
                stmt.executeUpdate("UPDATE mensagem_de_grupo SET Remetente = \"" + newUsername + "\" WHERE Remetente = \"" + user.getUsername() + "\";");
                stmt.executeUpdate("UPDATE mensagem_de_pares SET Destinatario = \"" + newUsername + "\" WHERE Destinatario = \"" + user.getUsername() + "\";");
                stmt.executeUpdate("UPDATE mensagem_de_pares SET Remetente = \"" + newUsername + "\" WHERE Remetente = \"" + user.getUsername() + "\";");
                stmt.executeUpdate("UPDATE tem_o_contacto SET Username = \"" + newUsername + "\" WHERE Username = \"" + user.getUsername() + "\";");
                stmt.executeUpdate("UPDATE tem_o_contacto SET Contacto = \"" + newUsername + "\" WHERE Contacto = \"" + user.getUsername() + "\";");
                stmt.executeUpdate("SET SQL_SAFE_UPDATES = 1;");
                stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1;");
                user.setUsername(newUsername);
                reply.add("Username edited successfully");
                return ResponseEntity.ok(reply);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("SQL query failed");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must put name/{new username} to edit your username");
    }

}


