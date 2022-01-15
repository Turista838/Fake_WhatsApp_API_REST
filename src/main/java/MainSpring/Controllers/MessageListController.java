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
@RequestMapping("messages")
public class MessageListController {

    @Autowired
    private AuthenticatedUsers authenticatedUsers;

    @GetMapping
    public ResponseEntity getMessages(@RequestHeader("Authorization") String token){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must request messages/user/{contact name} or messages/group/{group name}");
    }

    @GetMapping("{string}")
    public ResponseEntity getMessagesFailed(@RequestHeader("Authorization") String token, @PathVariable("string") String string){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must request messages/user/{contact name} or messages/group/{group name}");
    }

    @GetMapping("/user/{contact}")
    public ResponseEntity getMessagesUser(@RequestHeader("Authorization") String token, @PathVariable("contact") String contact){

        ArrayList<String> reply = new ArrayList<>();
        ConnectorSGBD connectorSGBD = new ConnectorSGBD();
        Statement stmt = connectorSGBD.getStmt();
        ResultSet rs;

        User user = authenticatedUsers.hashMap.get(token);

        try{
            rs = stmt.executeQuery("SELECT EXISTS(SELECT * from mensagem_de_pares WHERE Remetente = \"" + contact + "\" AND Destinatario = \"" + user.getUsername() + "\");");
            rs.next();
            if(rs.getBoolean(1)) {
                rs = stmt.executeQuery("SELECT * from mensagem_de_pares WHERE Ficheiro = 0 AND Remetente = \"" + contact + "\" AND Destinatario = \"" + user.getUsername() + "\" ORDER BY Data;");
                while (rs.next()) {
                    reply.add(rs.getString("Texto"));
                }
                return ResponseEntity.ok(reply);
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There are no messages exchanged with this contact, or user does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("SQL query failed");
        }

    }

    @GetMapping("/group/{group}")
    public ResponseEntity getMessagesGroup(@RequestHeader("Authorization") String token, @PathVariable("group") String group){

        ArrayList<String> reply = new ArrayList<>();
        ConnectorSGBD connectorSGBD = new ConnectorSGBD();
        Statement stmt = connectorSGBD.getStmt();
        ResultSet rs;

        User user = authenticatedUsers.hashMap.get(token);

        try{
            rs = stmt.executeQuery("SELECT EXISTS(SELECT * FROM mensagem_de_grupo WHERE Grupo = \"" + group + "\");");
            rs.next();
            if(rs.getBoolean(1)) {
                rs = stmt.executeQuery("SELECT * FROM mensagem_de_grupo WHERE Ficheiro = 0 AND Grupo = \"" + group + "\" AND NOT Remetente = \"" + user.getUsername() + "\";");
                while (rs.next()) {
                    reply.add(rs.getString("Texto"));
                }
                return ResponseEntity.ok(reply);
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There are no messages exchanged with this group, or group does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("SQL query failed");
        }

    }

}
