package bomberman.Model;

import bomberman.Data.DatabaseConnection;

import java.sql.Connection;

public class CommonModel {
    protected static DatabaseConnection connectLink = new DatabaseConnection();
    protected static Connection connection = connectLink.getConnection();
}
