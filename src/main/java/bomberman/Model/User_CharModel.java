package bomberman.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User_CharModel extends CommonModel{
    public static void createUser_CharTable() throws SQLException {
        String createMusicTable = "CREATE TABLE user_char (\n" +
                "  userID INTEGER NOT NULL,\n" +
                "  charID INTEGER NOT NULL,\n" +
                "  CONSTRAINT fk_user_char_useraccount FOREIGN KEY (userID) REFERENCES useraccount (userID) ON UPDATE CASCADE, " +
                "  CONSTRAINT fk_user_char_character FOREIGN KEY (charID) REFERENCES character (charID) ON UPDATE CASCADE )";
        PreparedStatement statement = connection.prepareStatement(createMusicTable);
        System.out.println(statement);
        int insertData = statement.executeUpdate();
        statement.close();
    }
    public static void deleteTable() throws SQLException {
        String deleteTable_ = "DROP TABLE user_coin;" ;

        PreparedStatement deleteTable = connection.prepareStatement(deleteTable_);
        int deleteTable__ = deleteTable.executeUpdate();
        deleteTable.close();
    }

    public static void insertUser_Char(int userID, int charID) throws SQLException {

        String insertUser_Char = "INSERT INTO user_char (userID, charID) VALUES (? , ?)";
        PreparedStatement statement = connection.prepareStatement(insertUser_Char);
        statement.setString(1, String.valueOf(userID));
        statement.setString(2, String.valueOf(charID));
        int insertData = statement.executeUpdate();
        statement.close();
    }
    public static void deleteUser_CharSell(int userID, int charID) throws SQLException {

        String insertUser_Char = "DELETE FROM user_char WHERE userID = ? AND charID = ?";
        PreparedStatement statement = connection.prepareStatement(insertUser_Char);
        statement.setString(1, String.valueOf(userID));
        statement.setString(2, String.valueOf(charID));
        int deleteData = statement.executeUpdate();
        statement.close();
    }


    public static void queryPurchasedChars (int userID, ArrayList<Integer> charIDs) throws SQLException {
        String queryUser_Char = "SELECT * " +
                "FROM user_char " +
                "WHERE userID = ?;";
        PreparedStatement statement = connection.prepareStatement(queryUser_Char);
        statement.setString(1,String.valueOf(userID));

        System.out.println(statement);
        ResultSet queryChars = statement.executeQuery();
        while (queryChars.next()){
            charIDs.add(queryChars.getInt("charID"));
        }
        queryChars.close();
        statement.close();
    }
    public static void deleteUser_Char(int userID) throws SQLException {
        String insertUser_Char = "DELETE FROM user_char WHERE userID = ? ";
//        String insertUser_Char = "DELETE FROM user_char WHERE userID < 53; ";
        PreparedStatement statement = connection.prepareStatement(insertUser_Char);
        statement.setString(1, String.valueOf(userID));
        int deleteData = statement.executeUpdate();
        statement.close();
    }
}
