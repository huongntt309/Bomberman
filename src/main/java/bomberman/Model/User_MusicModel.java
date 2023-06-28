package bomberman.Model;

import java.sql.*;

public class User_MusicModel extends CommonModel{
    public static void createMusicTable() throws SQLException {
        String createMusicTable = "CREATE TABLE user_music (\n" +
                "  userID INTEGER PRIMARY KEY NOT NULL,\n" +
                "  songID INTEGER NOT NULL DEFAULT '11',\n" +
                "  bgMusicVolume double NOT NULL DEFAULT '0.5',\n" +
                "  sfxVolume double NOT NULL DEFAULT '0.5',\n" +
                "  CONSTRAINT fk_user_music_music FOREIGN KEY (songID) REFERENCES music (songID) ON UPDATE CASCADE ON DELETE CASCADE,\n" +
                "  CONSTRAINT fk_user_music_useraccount FOREIGN KEY (userID) REFERENCES useraccount (userID) ON UPDATE CASCADE ON DELETE CASCADE )";
        PreparedStatement statement = connection.prepareStatement(createMusicTable);
        System.out.println(statement);
        int insertData = statement.executeUpdate();
        statement.close();
    }
    public static void dropTableUser_Music() throws SQLException {
        String deleteMusic = "DROP TABLE user_music;";
        PreparedStatement statement = connection.prepareStatement(deleteMusic);
        System.out.println(statement);
        int insertData = statement.executeUpdate();
        statement.close();
    }
    public static void insertUser_MusicData(int userID) throws SQLException {
        String insertUser_music = "INSERT INTO user_music (userID) VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(insertUser_music);
        statement.setString(1, String.valueOf(userID));
        int insertData = statement.executeUpdate();
        statement.close();
    }

    public static void updateUser_Music(int userID, int curSongID, double bgMusicVolume , double sfxVolume) throws SQLException {
        String user_musicUpdate =
                "UPDATE user_music SET songID = ?, bgMusicVolume = ?, sfxVolume = ? WHERE userID = ?; ";

        PreparedStatement statement = connection.prepareStatement(user_musicUpdate);

        statement.setString(1,String.valueOf(curSongID));
        statement.setString(2,String.valueOf(bgMusicVolume));
        statement.setString(3,String.valueOf(sfxVolume));
        statement.setString(4,String.valueOf(userID));

        int updateData = statement.executeUpdate();

        /**
         * cerr
         */
        System.out.println(statement);
        statement.close();

    }

    public static ResultSet queryUser_Music(int userID) throws SQLException {
        String queryUser_music = "SELECT * " +
                                "FROM user_music " +
                                "INNER JOIN music " +
                                "ON user_music.songID = music.songID " +
                                "WHERE userID = ?;";
        ;
        PreparedStatement statement = connection.prepareStatement(queryUser_music);
        statement.setString(1,String.valueOf(userID));

        System.out.println(statement);

        return statement.executeQuery();
    }
    public static void deleteUser_Music(int userID) throws SQLException {
        String insertUser_Music = "DELETE FROM user_music WHERE userID = ?";

        PreparedStatement statement = connection.prepareStatement(insertUser_Music);
        statement.setString(1, String.valueOf(userID));
        int deleteData = statement.executeUpdate();
        statement.close();
    }
}













