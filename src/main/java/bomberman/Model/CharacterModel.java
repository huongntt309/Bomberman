package bomberman.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CharacterModel extends CommonModel{
    public static void createCharacter() throws SQLException {

        String sqlDML = "CREATE TABLE character ( charID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "charName TEXT NOT NULL, " +
                        "charFaceURLLink TEXT UNIQUE NOT NULL, " +
                        "charImageLink  TEXT UNIQUE NOT NULL, " +
                        "charPrice INTEGER NOT NULL)";
        PreparedStatement insertChar_ = connection.prepareStatement(sqlDML);
        int insertChar__ = insertChar_.executeUpdate();
    }
    public static void deleteTable() throws SQLException {
        String deleteTable_ = "DROP TABLE character" ;

        PreparedStatement deleteTable = connection.prepareStatement(deleteTable_);
        int deleteTable__ = deleteTable.executeUpdate();
        deleteTable.close();
    }
    /**
     * after create right table
     */
    public static void initCharacter1() throws SQLException {

                String insertChar1 = "INSERT INTO character (charID, charName, charFaceURLLink, charImageLink, charPrice) VALUES (1 , 'Theodora' , 'char/char-icon/1-face.png', 'char/player/1-character.png', 5000 );" ;
                String insertChar2 = "INSERT INTO character (charID, charName, charFaceURLLink, charImageLink, charPrice) VALUES (2 , 'Ringo' , 'char/char-icon/2-face.png', 'char/player/2-character.png', 5000 );" ;
                String insertChar3 = "INSERT INTO character (charID, charName, charFaceURLLink, charImageLink, charPrice) VALUES (3 , 'Jeniffer' , 'char/char-icon/3-face.png', 'char/player/3-character.png', 10000 );" ;
                String insertChar4 = "INSERT INTO character (charID, charName, charFaceURLLink, charImageLink, charPrice) VALUES (4 , 'Godard' , 'char/char-icon/4-face.png', 'char/player/4-character.png', 10000 );" ;
                String insertChar5 = "INSERT INTO character (charID, charName, charFaceURLLink, charImageLink, charPrice) VALUES (5 , 'Biarid' , 'char/char-icon/5-face.png', 'char/player/5-character.png', 20000 );" ;
                String insertChar6 = "INSERT INTO character (charID, charName, charFaceURLLink, charImageLink, charPrice) VALUES (6 , 'Solia' , 'char/char-icon/6-face.png', 'char/player/6-character.png', 20000 );" ;
                String insertChar7 = "INSERT INTO character (charID, charName, charFaceURLLink, charImageLink, charPrice) VALUES (7 , 'Kedan' , 'char/char-icon/7-face.png', 'char/player/7-character.png', 50000 );" ;
                String insertChar8 = "INSERT INTO character (charID, charName, charFaceURLLink, charImageLink, charPrice) VALUES (8 , 'Nigob' , 'char/char-icon/8-face.png', 'char/player/8-character.png', 50000 );" ;
                String insertChar9 = "INSERT INTO character (charID, charName, charFaceURLLink, charImageLink, charPrice) VALUES (9 , 'Valpo' , 'char/char-icon/9-face.png', 'char/player/9-character.png', 50000 );" ;

        PreparedStatement insertChar_ = connection.prepareStatement(insertChar1);
        int insertChar__ = insertChar_.executeUpdate();
    }

    public static void loadCharacter(String [] charURL, String [] path, String [] charName) throws SQLException {
        String queryChar = "SELECT * FROM character;";

        PreparedStatement statement = connection.prepareStatement(queryChar);

        ResultSet queryCharRes = statement.executeQuery();
        int index = 1;
        while (queryCharRes.next()){
            charURL[index] = queryCharRes.getString("charFaceURLLink");
            path[index] = "-fx-background-image : url(" + charURL[index] + ")";
            charName[index] =  queryCharRes.getString("charName");
            index ++;
        }
        statement.close();
    }
    public static void loadCharacterShop(String [] charURL, String [] path, String [] charName, int [] charBuyPrice, int [] charSellPrice) throws SQLException {
        String queryChar = "SELECT * FROM character;";

        PreparedStatement statement = connection.prepareStatement(queryChar);

        ResultSet queryCharRes = statement.executeQuery();
        int index = 1;
        while (queryCharRes.next()){
            charURL[index] = queryCharRes.getString("charFaceURLLink");
            path[index] = "-fx-background-image : url(" + charURL[index] + ")";
            charName[index] =  queryCharRes.getString("charName");
            charBuyPrice[index] = queryCharRes.getInt("charPrice");
            charSellPrice[index] = (int)(charBuyPrice[index] * 0.8);
            index ++;
        }
        statement.close();
    }
    public static String loadLinkCharImg(int charID) throws SQLException {
        String queryChar = "SELECT * FROM character WHERE charID = ?;";

        PreparedStatement statement = connection.prepareStatement(queryChar);

        statement.setString(1, String.valueOf(charID));
        ResultSet queryCharRes = statement.executeQuery();
        String link = null;
        while (queryCharRes.next()){
             link = queryCharRes.getString("charImageLink");
             System.out.println(link);
        }
        statement.close();
        return link;
    }

}
