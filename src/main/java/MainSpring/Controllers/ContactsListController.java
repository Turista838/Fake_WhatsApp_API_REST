package MainSpring.Controllers;

import MainSpring.Model.AuthenticatedUsers;
import MainSpring.Model.User;
import MainSpring.SGBD.ConnectorSGBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("contacts")
public class ContactsListController {

    @Autowired
    private AuthenticatedUsers authenticatedUsers;

    @GetMapping
    public ResponseEntity getContacts(@RequestHeader("Authorization") String token){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must request 'users' or 'groups'");
    }

    @GetMapping("{type}")
    public ResponseEntity getContacts(@RequestHeader("Authorization") String token, @PathVariable("type") String type){

        ArrayList<String> reply = new ArrayList<>();
        ConnectorSGBD connectorSGBD = new ConnectorSGBD();
        Statement stmt = connectorSGBD.getStmt();
        ResultSet rs;

        User user = authenticatedUsers.hashMap.get(token);

        switch (type){
            case "users" -> {
                try{
                    rs = stmt.executeQuery("SELECT Contacto from tem_o_contacto WHERE Username = \"" + user.getUsername() + "\" AND Adicionado = 1;");
                    while (rs.next()){ //query contactos
                        reply.add(rs.getString(1));
                    }
                    return ResponseEntity.ok(reply);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("SQL query failed");
                }
            }
            case "groups" -> {
                try{
                    rs = stmt.executeQuery("SELECT grupo.Nome FROM grupo, inclui WHERE (inclui.Grupo_ID_Grupo = grupo.ID_Grupo) AND inclui.Utilizador_Username = \"" + user.getUsername() + "\" AND Adicionado = 1;");
                    while (rs.next()){ //query contactos
                        reply.add(rs.getString(1));
                    }
                    return ResponseEntity.ok(reply);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("SQL query failed");
                }
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must request 'users' or 'groups'");
    }



}
