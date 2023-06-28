package bomberman.Model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static bomberman.Controller.Common.CommonController.getUserID;

public class User_AvatarModel extends CommonModel{
    public static void createUser_AvatarTable() throws SQLException {
        String create = "CREATE TABLE user_avatar (\n" +
                "  userID INTEGER PRIMARY KEY NOT NULL,\n" +
                "  photo BLOB NOT NULL,\n" +
                "  CONSTRAINT fk_user_char_useraccount FOREIGN KEY (userID) REFERENCES useraccount (userID) ON UPDATE CASCADE ON DELETE CASCADE);";

        PreparedStatement statement = connection.prepareStatement(create);
        System.out.println(statement);
        int insertData = statement.executeUpdate();
        statement.close();
    }
    public static void dropUser_AvatarTable() throws SQLException {
        String create = "DROP TABLE user_avatar;";

        PreparedStatement statement = connection.prepareStatement(create);
        System.out.println(statement);
        int insertData = statement.executeUpdate();
        statement.close();
    }
    public static boolean hasAnAvatar(int userID) throws SQLException {
        String query_ = "SELECT count(*) FROM user_avatar WHERE userID = ? ;" ;

        PreparedStatement query = connection.prepareStatement(query_);
        query.setString(1, String.valueOf(userID));
        ResultSet rs = query.executeQuery();
        boolean hasAvatar = false;
        if(rs.next()) {
            if(rs.getInt(1) == 0) hasAvatar = false;
            else hasAvatar = true;
        }
        rs.close();
        query.close();
        return hasAvatar;
    }
    public static void deleteUser_Avatar(int userID) throws SQLException {
        String insertUser_Avatar = "DELETE FROM user_avatar WHERE userID = ?";
        PreparedStatement statement = connection.prepareStatement(insertUser_Avatar);
        statement.setString(1, String.valueOf(userID));
        int deleteData = statement.executeUpdate();
        statement.close();
    }
    public static void insertUser_Avatar(int userID, File file) throws SQLException, IOException {
        BufferedImage bf;
        bf = ImageIO.read(file);
        FileInputStream fin = new FileInputStream(file);
        int len = (int) file.length();

        String insertUserAvatar = "REPLACE INTO user_avatar (userID, photo) VALUES (? , ?)";
        PreparedStatement statement = connection.prepareStatement(insertUserAvatar);
        statement.setString(1, String.valueOf(userID));
        statement.setBinaryStream(2, fin, len);
        int status = statement.executeUpdate();
        statement.close();
    }
    public static Image queryUser_Avatar(int userID) throws SQLException, IOException {
        String queryAva = "SELECT * FROM user_avatar WHERE userID = ? ;";
        PreparedStatement statement = connection.prepareStatement(queryAva);
        statement.setString(1, String.valueOf(userID));
        ResultSet rs = statement.executeQuery();
        Image image = null;
        if (rs.next()) {
            /////result set loop, iterating over database
            InputStream is = rs.getBinaryStream("photo"); // image from database
            BufferedImage imBuff = ImageIO.read(is);  //converting to buffered image
            image = SwingFXUtils.toFXImage(imBuff, null);
        }
        statement.close();
        rs.close();
        return image;
    }

}
