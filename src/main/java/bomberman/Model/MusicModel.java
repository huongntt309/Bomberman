package bomberman.Model;


import java.sql.*;

public class MusicModel extends CommonModel{
    public static ResultSet queryMusic() throws SQLException {
        String queryMusic = "SELECT * FROM music;";
        PreparedStatement statement = connection.prepareStatement(queryMusic);
        return statement.executeQuery();
    }
    public static void initMusic() throws SQLException {
        /*CREATE TABLE music
                (songID INTEGER PRIMARY KEY AUTOINCREMENT,
                        songName TEXT NOT NULL,
                        songLink TEXT UNIQUE NOT NULL)";
        songLink : path*/
        String insertMusic = "INSERT INTO music (songID, songName, songLink) VALUES (11,'Waiting For You' , 'src/main/resources/music/bgMusic/waiting-for-you.m4a');" ;

        PreparedStatement insertChar_ = connection.prepareStatement(insertMusic);
        int insertChar__ = insertChar_.executeUpdate();
    }
    public static void initForeignkey() throws SQLException {

        String insertMusic = "PRAGMA foreign_keys = ON;" ;

        PreparedStatement insertChar_ = connection.prepareStatement(insertMusic);
        int insertChar__ = insertChar_.executeUpdate();
    }
    public static void pragmaForeignkey() throws SQLException {

        String insertMusic = "PRAGMA foreign_keys ;" ;

        PreparedStatement insertChar_ = connection.prepareStatement(insertMusic);
        ResultSet insertChar__ = insertChar_.executeQuery();
        insertChar__.next();
        System.out.println(insertChar__.getInt(1));

    }

}
