package bomberman.Model;

import java.sql.*;


public class UserAccountModel extends CommonModel{

    /**
     * SIGN UP CONTROLLER
     */
    public static boolean validateRegister(String username, String password, String firstName , String lastName, String gender, String dob) {

        String insertUser =
                "INSERT INTO useraccount (username, password, firstName, lastName, gender, dob) " +
                "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertUser);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, gender);
            statement.setString(6, dob);

            int insertResult = statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * LOGIN CONTROLLER
     */

    public static int validatelogIn(String username, String password) throws SQLException {
        String verifylogIn =
                "SELECT * FROM useraccount WHERE username = ? AND password = ? ;";
        PreparedStatement statement = connection.prepareStatement(verifylogIn);

        statement.setString(1, username);
        statement.setString(2, password);

        try {
            ResultSet queryUser = statement.executeQuery();

            queryUser.next();
            int userID = queryUser.getInt("userID") ;
            statement.close();
            if (userID > 0) {
                return userID;
            }
            else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static boolean validatelogIn(int userID, String password) throws SQLException {
        String verifylogIn =
                "SELECT count(*) FROM useraccount WHERE userID = ? AND password = ? ;";
        PreparedStatement statement = connection.prepareStatement(verifylogIn);

        statement.setString(1, String.valueOf(userID));
        statement.setString(2, password);

        try {
            ResultSet queryUser = statement.executeQuery();

            queryUser.next();
            int rs = queryUser.getInt(1) ;
            queryUser.close();
            statement.close();
            if (rs > 0) {
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static int queryUserByUserName (String username) throws SQLException {
        String queryUser =
                "SELECT * FROM useraccount WHERE username = ?;";
        PreparedStatement statementQuery = connection.prepareStatement(queryUser);
        statementQuery.setString(1, username);
        ResultSet queryUserRes = statementQuery.executeQuery();
        int userID = queryUserRes.getInt("userID");
        queryUserRes.close();
        return userID;
    }
    public static String queryUserFullnameByUserId (int userId) throws SQLException {
        String queryUser =
                "SELECT * FROM useraccount WHERE userID = ?;";
        PreparedStatement statementQuery = connection.prepareStatement(queryUser);
        statementQuery.setString(1, String.valueOf(userId));
        ResultSet queryUserRes = statementQuery.executeQuery();
        String firstName = queryUserRes.getString("firstName");
        String lastName = queryUserRes.getString("lastName");
        queryUserRes.close();
        return firstName + " " + lastName;
    }
    public static ResultSet  queryUserByName(String username, String fullname) throws SQLException {
        String queryUser =
                "SELECT * FROM useraccount "
                        + " WHERE username LIKE '%" + username + "%' AND " +
                        " (firstName || ' ' || lastName) LIKE '%" + fullname + "%';";
        /**
         * ||  in sqlite == concat (firstName ,' ', lastName) in mysql
         */
        PreparedStatement statementQuery = connection.prepareStatement(queryUser);
        System.out.println(statementQuery);
        ResultSet queryUserRes = statementQuery.executeQuery();
        return queryUserRes;
    }
    public static String queryGenderByUserId (int userId) throws SQLException {
        String queryUser =
                "SELECT * FROM useraccount WHERE userID = ?;";
        PreparedStatement statementQuery = connection.prepareStatement(queryUser);
        statementQuery.setString(1, String.valueOf(userId));
        ResultSet queryUserRes = statementQuery.executeQuery();
        String gender = queryUserRes.getString("gender");
        queryUserRes.close();
        return gender;
    }

    public static void changePassword (int userID, String newpass) throws SQLException {
        String updatepass = "UPDATE useraccount SET password = ? WHERE userID = ?;";

        PreparedStatement statement = connection.prepareStatement(updatepass);
        statement.setString(1, newpass);
        statement.setString(2, String.valueOf(userID));
        System.out.println(statement);
        int updatepass_ = statement.executeUpdate();
    }
    public static void changeName(int userID, String fname, String lname) throws SQLException {
        String updatepass = "UPDATE useraccount SET firstName = ?, lastName = ?  WHERE userID = ?;";

        PreparedStatement statement = connection.prepareStatement(updatepass);
        statement.setString(1, fname);
        statement.setString(2, lname);
        statement.setString(3, String.valueOf(userID));
        System.out.println(statement);
        int updatepass_ = statement.executeUpdate();
    }
    public static boolean deteleUserAccount (int userID) throws SQLException {
        String deleteUA = "DELETE FROM useraccount WHERE userID = ?;";

        PreparedStatement statement = connection.prepareStatement(deleteUA);
        statement.setString(1, String.valueOf(userID));
        System.out.println(statement);
        int deleteUA_ = statement.executeUpdate();
        return true;
    }
}
