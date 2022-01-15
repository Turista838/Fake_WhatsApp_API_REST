package MainSpring.SGBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectorSGBD {

    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String dbUrl = "jdbc:mysql://"+"127.0.0.1"+"/"+"pd_tp_final";
    private Connection conn;
    private Statement stmt;

    public ConnectorSGBD(){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(dbUrl, "root", "123456");
            stmt = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement getStmt() { return stmt; }
}
