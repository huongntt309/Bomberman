package bomberman.Model;

import bomberman.Controller.User.ProfileController;
import bomberman.Data.Score;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GameScoreModel extends CommonModel {
    public static void createGameScore() throws SQLException {

        String GameScore = " CREATE TABLE gamescore ( " +
                "                userID INTEGER NOT NULL," +
                "                score INTEGER NOT NULL, " +
                "                level INTEGER NOT NULL, " +
                "                statusGame TEXT NOT NULL, " + // just win or lose
                "                createdTime TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "                charID INTEGER NOT NULL, " +
                "                PRIMARY KEY (userID, createdTime), " +
                "                CONSTRAINT fk_gamescore_useraccount FOREIGN KEY (userID) REFERENCES useraccount (userID) ON UPDATE CASCADE ON DELETE CASCADE,  " +
                "                CONSTRAINT fk_gamescore_char FOREIGN KEY (charID) REFERENCES character (charID) ON UPDATE CASCADE ON DELETE CASCADE); ";
        PreparedStatement statement = connection.prepareStatement(GameScore);
        System.out.println(statement);
        int insertData = statement.executeUpdate();
        statement.close();
    }
    public static void dropGameScore() throws SQLException {
        String GameScore_ = "DROP TABLE gamescore;";
        PreparedStatement statement = connection.prepareStatement(GameScore_);
        System.out.println(statement);
        int insertData = statement.executeUpdate();
        statement.close();
    }

    public static void insertGameScore(int userID, int score, int level, int charID, String statusGame) throws SQLException {
        String insertGameScore = "INSERT INTO gamescore (userID, score, level, statusGame, charID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertGameScore);
        statement.setString(1, String.valueOf(userID));
        statement.setString(2, String.valueOf(score));
        statement.setString(3, String.valueOf(level));
        statement.setString(4, statusGame);
        statement.setString(5, String.valueOf(charID));
        System.out.println(statement);
        int insertData = statement.executeUpdate();
        statement.close();
    }

    public static void queryHighestScoreAllTime(ObservableList<Score> gameScoreList) throws SQLException, IOException {
        String queryHighestScore = "SELECT tmpTable.*, uav.photo\n" +
                "FROM \n" +
                "(SELECT gamescore.*, useraccount.firstName, useraccount.lastName \n" +
                "FROM gamescore, useraccount \n" +
                "WHERE useraccount.userID = gamescore.userID) as tmpTable " +
                "LEFT JOIN user_avatar uav ON tmpTable.userID = uav.userID \n " +
                "GROUP BY tmpTable.userID \n" +
                "ORDER BY MAX(score) DESC \n" +
                "LIMIT 10;";
        PreparedStatement statement = connection.prepareStatement(queryHighestScore);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            /**
             * chỉ lấy tên user + score
             */
            InputStream is = resultSet.getBinaryStream("photo"); // image from database
            ImageView uav = null;
            if(is != null){
                BufferedImage imBuff = ImageIO.read(is);  //converting to buffered image
                Image image = SwingFXUtils.toFXImage(imBuff, null);
                uav = new ImageView(image);

            }
            else {
                Image defaultAva;
                String gender = UserAccountModel.queryGenderByUserId(Integer.valueOf(resultSet.getInt("userID")));
                if (gender.equals("Female")) {
                    defaultAva = new Image(ProfileController.class.getResourceAsStream("/image/menu/profile/femaleAva.png"));
                } else {
                    defaultAva = new Image(ProfileController.class.getResourceAsStream("/image/menu/profile/maleAva.png"));
                }
                uav = new ImageView(defaultAva);
            }
            uav.setSmooth(true);
            uav.setCache(true);
            uav.setFitHeight(60);
            uav.setFitWidth(60);

            Score userAccount = new Score(
                    uav,
                    Integer.valueOf(resultSet.getInt("userID")),
                    Integer.valueOf(resultSet.getInt("score")),
                    Integer.valueOf(resultSet.getInt("level")),
                    resultSet.getString("statusGame"),
                    Integer.valueOf(resultSet.getInt("charID")),
                    "Dont need charName",
                    resultSet.getString("createdTime"),
                    resultSet.getString("firstName") + ' '
                            + resultSet.getString("lastName"),
                    null
            );
            gameScoreList.add(userAccount);
        }
        statement.close();
    }
    public static int queryRankingOf1User(int userID) throws SQLException {
        String queryRanking =  "SELECT count(*)  " +
                "FROM (SELECT userID, max(score) maxScore " +
                " FROM gamescore " +
                " GROUP BY userID) as tempT " +
                "WHERE maxScore >  " +
                " (SELECT IIF(max(score) IS NULL, 0, max(score)) " +
                " FROM gamescore " +
                " WHERE userID = ?);";

        PreparedStatement statement = connection.prepareStatement(queryRanking);
        statement.setString(1, String.valueOf(userID));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int ranking = resultSet.getInt(1) + 1;
        resultSet.close();
        statement.close();
        return ranking;

    }
    public static void queryHighestScoreDaily(ObservableList<Score> gameScoreList) throws SQLException, IOException {
        String queryHighestScore = "SELECT tmpTable.*, uav.photo\n" +
                "FROM \n" +
                "(SELECT gamescore.*, useraccount.firstName, useraccount.lastName \n" +
                "FROM gamescore, useraccount \n" +
                "WHERE useraccount.userID = gamescore.userID) as tmpTable " +
                "LEFT JOIN user_avatar uav ON tmpTable.userID = uav.userID \n" +
                "WHERE (createdTime BETWEEN datetime('now', 'start of day') AND datetime('now'))\n" +
                "  " +
                "GROUP BY tmpTable.userID \n" +
                "ORDER BY MAX(score) DESC \n" +
                "LIMIT 10;";

        PreparedStatement statement = connection.prepareStatement(queryHighestScore);
        System.out.println(statement);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            /**
             * chỉ lấy tên user + score
             */
            InputStream is = resultSet.getBinaryStream("photo"); // image from database
            ImageView uav = null;
            if(is != null){
                BufferedImage imBuff = ImageIO.read(is);  //converting to buffered image
                Image image = SwingFXUtils.toFXImage(imBuff, null);
                uav = new ImageView(image);

            }
            else {
                Image defaultAva;
                String gender = UserAccountModel.queryGenderByUserId(Integer.valueOf(resultSet.getInt("userID")));
                if (gender.equals("Female")) {
                    defaultAva = new Image(ProfileController.class.getResourceAsStream("/image/menu/profile/femaleAva.png"));
                } else {
                    defaultAva = new Image(ProfileController.class.getResourceAsStream("/image/menu/profile/maleAva.png"));
                }
                uav = new ImageView(defaultAva);
            }
            uav.setSmooth(true);
            uav.setCache(true);
            uav.setFitHeight(60);
            uav.setFitWidth(60);
            Score userAccount = new Score(
                    uav,
                    Integer.valueOf(resultSet.getInt("userID")),
                    Integer.valueOf(resultSet.getInt("score")),
                    Integer.valueOf(resultSet.getInt("level")),
                    resultSet.getString("statusGame"),
                    Integer.valueOf(resultSet.getInt("charID")),
                    "Dont need charName",
                    resultSet.getString("createdTime"),
                    resultSet.getString("firstName") + ' '
                            + resultSet.getString("lastName"),
                    null
            );
            gameScoreList.add(userAccount);
        }
        statement.close();
    }
    public static void queryAllScoreOf1User(int userID, ObservableList<Score> gameScoreList) throws SQLException {
        String queryHighestScore = "SELECT * " +
                "FROM gamescore, character " +
                "WHERE character.charID = gamescore.charID " +
                "AND userID = ? ;";

        PreparedStatement statement = connection.prepareStatement(queryHighestScore);
        statement.setString(1, String.valueOf(userID));
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String linkFace = "/" + resultSet.getString("charFaceURLLink");
            ImageView imageView = new ImageView(new Image(GameScoreModel.class.getResourceAsStream(linkFace),
                    55, 55, false, true));

            Score allscore = new Score(
                    null, // dont need image
                    Integer.valueOf(resultSet.getInt("userID")),
                    Integer.valueOf(resultSet.getInt("score")),
                    Integer.valueOf(resultSet.getInt("level")),
                    resultSet.getString("statusGame"),
                    Integer.valueOf(resultSet.getInt("charID")),
                    resultSet.getString("charName"),
                    resultSet.getString("createdTime"),
                    "Dont need fullname",
                    imageView
            );
            gameScoreList.add(allscore);
        }
        resultSet.close();
        statement.close();
        return;
    }

    public static int queryHighestScoreOf1User(int userID) throws SQLException {
        String queryHighestScore = "SELECT MAX(score) maxScore " +
                "FROM gamescore " +
                "WHERE userID = ? ;";

        PreparedStatement statement = connection.prepareStatement(queryHighestScore);
        statement.setString(1, String.valueOf(userID));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int maxScore = resultSet.getInt("maxScore");
        resultSet.close();
        statement.close();
        return maxScore;
    }
    public static int queryNumberGamesPlayedOf1User(int userID) throws SQLException {
        String queryHighestScore = "SELECT count(*) as countGames " +
                "FROM gamescore " +
                "WHERE userID = ? ;";

        PreparedStatement statement = connection.prepareStatement(queryHighestScore);
        statement.setString(1, String.valueOf(userID));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int gamesPlayed = resultSet.getInt("countGames");
        resultSet.close();
        statement.close();
        return gamesPlayed;
    }
    public static int queryFavouriteCharacterOf1User(int userID) throws SQLException {
        String queryHighestScore = "SELECT charID, count(charID) " +
                "FROM gamescore " +
                "WHERE userID = ? " +
                "GROUP BY charID " +
                "ORDER BY count(charID) DESC;";

        /**
         * chưa lấy max, đang sort và lấy index 0
         */
        PreparedStatement statement = connection.prepareStatement(queryHighestScore);
        statement.setString(1, String.valueOf(userID));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int mod = resultSet.getInt("charID");
        resultSet.close();
        statement.close();
        return mod;
    }
    public static void deleteGameScoreRecord(int userID) throws SQLException {
        String insertUser_Char = "DELETE FROM gamescore WHERE userID = ?";
        PreparedStatement statement = connection.prepareStatement(insertUser_Char);
        statement.setString(1, String.valueOf(userID));
        int deleteData = statement.executeUpdate();
        statement.close();
    }
}
