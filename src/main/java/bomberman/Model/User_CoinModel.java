package bomberman.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User_CoinModel extends CommonModel{
    public static void createUser_CoinTable() throws SQLException {
        String createUC = "CREATE TABLE user_coin (\n" +
                "  userID INTEGER NOT NULL,\n" +
                "  coin INTEGER NOT NULL DEFAULT '0',\n" +
                "  CONSTRAINT fk_user_coin_useraccount FOREIGN KEY (userID) REFERENCES useraccount (userID) ON UPDATE CASCADE )";
        PreparedStatement statement = connection.prepareStatement(createUC);
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

    public static void insertUser_Coin(int userID) throws SQLException {
        /**
         * cho sẵn 30k coin mua cho thoải mái
         */
        String insertUser_coin = "INSERT INTO user_coin (userID, coin) VALUES (? , 30000)";
        PreparedStatement statement = connection.prepareStatement(insertUser_coin);
        statement.setString(1, String.valueOf(userID));
        int insertData = statement.executeUpdate();
        statement.close();
    }

    public static void updateUser_Coin(int userID, int coin) throws SQLException {
        String user_coinUpdate =
                "UPDATE user_coin SET coin = ? WHERE userID = ?;";

        PreparedStatement statement = connection.prepareStatement(user_coinUpdate);

        statement.setString(1,String.valueOf(coin));

        statement.setString(2,String.valueOf(userID));

        int updateData = statement.executeUpdate();

        /**
         * cerr
         */
        System.out.println(statement);
        statement.close();

    }

    public static int queryCoin (int userID) throws SQLException {
        String queryUser_coin = "SELECT * " +
                "FROM user_coin " +
                "WHERE userID = ?;";
        PreparedStatement statement = connection.prepareStatement(queryUser_coin);
        statement.setString(1,String.valueOf(userID));

        System.out.println(statement);
        ResultSet queryCoin = statement.executeQuery();
        int coin = 0;
        while (queryCoin.next()){
            coin = queryCoin.getInt("coin");
        }
        queryCoin.close();
        statement.close();
        return coin;
    }
    public static void deleteUser_Coin(int userID) throws SQLException {
        String insertUser_Coin = "DELETE FROM user_coin WHERE userID = ?;";
        PreparedStatement statement = connection.prepareStatement(insertUser_Coin);
        statement.setString(1, String.valueOf(userID));
        int deleteData = statement.executeUpdate();
        statement.close();
    }
}
