package bomberman.Controller.User;

import bomberman.BombermanGame;
import bomberman.Controller.Common.CommonController;
import bomberman.Model.CharacterModel;
import bomberman.Model.User_CharModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CharacterController extends CommonController {

    public static BombermanGame game;
    @FXML
    private Button charChoosingButton;
    @FXML
    private Button char1Button;
    @FXML
    private Button char2Button;
    @FXML
    private Button char3Button;
    @FXML
    private Button char4Button;
    @FXML
    private Button char5Button;
    @FXML
    private Button char6Button;
    @FXML
    private Button char7Button;
    @FXML
    private Button char8Button;
    @FXML
    private Button char9Button;
    @FXML
    private ImageView lock1Image;
    @FXML
    private ImageView lock2Image;
    @FXML
    private ImageView lock3Image;
    @FXML
    private ImageView lock4Image;
    @FXML
    private ImageView lock5Image;
    @FXML
    private ImageView lock6Image;
    @FXML
    private ImageView lock7Image;
    @FXML
    private ImageView lock8Image;
    @FXML
    private ImageView lock9Image;
    ArrayList<Integer> purchasedChars = new ArrayList<>();
    @FXML
    private Label notiLabel;
    @FXML
    private Button playButton;
    @FXML
    private Button AIplayButton;
    @FXML
    private Label charNameLabel;
    String [] charURL = new String[10];
    String [] path = new String[10];
    String [] charName = new String[10];
    public static int currentChoiceID = 0;

    public void initCharacter() throws SQLException {
       CharacterModel.loadCharacter(charURL, path, charName);
    }


    public void applyChange(int curChoice) {
        notiLabel.setText("");
        for (int charID: purchasedChars) {
            if(curChoice == charID){
                charChoosingButton.setStyle(path[curChoice]);
                charNameLabel.setText(charName[curChoice]);
                return;
            }
        }
        notiLabel.setText("Can not choose " + charName[curChoice]);
    }

    public void char1ButtonOnAction() throws SQLException {
        currentChoiceID = 1;
        applyChange(currentChoiceID);
    }
    public void char2ButtonOnAction(){
        currentChoiceID = 2;
        applyChange(currentChoiceID);
    }
    public void char3ButtonOnAction(){
        currentChoiceID = 3;
        applyChange(currentChoiceID);
    }
    public void char4ButtonOnAction(){
        currentChoiceID = 4;
        applyChange(currentChoiceID);
    }
    public void char5ButtonOnAction(){
        currentChoiceID = 5;
        applyChange(currentChoiceID);
    }
    public void char6ButtonOnAction(){
        currentChoiceID = 6;
        applyChange(currentChoiceID);
    }
    public void char7ButtonOnAction(){
        currentChoiceID = 7;
        applyChange(currentChoiceID);
    }
    public void char8ButtonOnAction(){
        currentChoiceID = 8;
        applyChange(currentChoiceID);
    }
    public void char9ButtonOnAction(){
        currentChoiceID = 9;
        applyChange(currentChoiceID);
    }

    public void AIplayButtonOnAction () throws SQLException {
        Stage stage = (Stage) playButton.getScene().getWindow();
        game = new BombermanGame(BombermanGame.MODE_AI);
        game.start(stage);
    }
    public void playButtonOnAction () throws IOException, SQLException {
        Stage stage = (Stage) playButton.getScene().getWindow();
        game = new BombermanGame(BombermanGame.MODE_NORMAL);
        game.start(stage);
    }

    public void resetCharacterImage() throws IOException, SQLException {
        purchasedChars.clear();
        User_CharModel.queryPurchasedChars(getUserID(), purchasedChars);
        for (int charID : purchasedChars) {
            System.out.println("charID :" + charID);
            if (charID == 1) {
                char1Button.setOpacity(1);
                lock1Image.setVisible(false);
            }

            if (charID == 2) {
                char2Button.setOpacity(1);
                lock2Image.setVisible(false);
            }

            if (charID == 3) {
                char3Button.setOpacity(1);
                lock3Image.setVisible(false);
            }

            if (charID == 4) {
                char4Button.setOpacity(1);
                lock4Image.setVisible(false);
            }
            if (charID == 5) {
                char5Button.setOpacity(1);
                lock5Image.setVisible(false);
            }

            if (charID == 6) {
                char6Button.setOpacity(1);
                lock6Image.setVisible(false);
            }

            if (charID == 7) {
                char7Button.setOpacity(1);
                lock7Image.setVisible(false);
            }

            if (charID == 8) {
                char8Button.setOpacity(1);
                lock8Image.setVisible(false);
            }

            if (charID == 9) {
                char9Button.setOpacity(1);
                lock9Image.setVisible(false);
            }

        }
    }
    public void backButtonOnAction() throws IOException, SQLException {
        super.backButtonOnAction("/scene/User/home.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initCharacter();
            resetCharacterImage();
            currentChoiceID = 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
