package bomberman.Controller.User;

import bomberman.Controller.Common.CommonController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController extends CommonController {
    @FXML
    private Button playButton;
    @FXML
    private Button howToPlayButton;
    @FXML
    private Button historyViewButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button settingButton;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Button userHomeButton1;

    public void playButtonOnAction () throws IOException, SQLException {
        Stage stage = (Stage) playButton.getScene().getWindow();
        changeScene(stage, "/scene/User/character.fxml");
    }

    public void logOutButtonOnAction () throws IOException {
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        SettingController.stopBgMusic();
        changeScene(stage, "/scene/Common/login.fxml");
    }
    public void settingButtonOnAction () throws IOException {
        Stage stage = (Stage) settingButton.getScene().getWindow();
        changeScene(stage, "/scene/User/setting.fxml");
    }

    public void howToPlayButtonOnAction () throws IOException {
        Stage stage = (Stage) howToPlayButton.getScene().getWindow();
        changeScene(stage, "/scene/User/howtoplay.fxml");
    }

    public void shopButtonOnAction () throws IOException {
        Stage stage = (Stage) settingButton.getScene().getWindow();
        changeScene(stage, "/scene/User/character-shop.fxml");
    }

    public void leaderboardButtonOnAction () throws IOException {
        Stage stage = (Stage) settingButton.getScene().getWindow();
        changeScene(stage, "/scene/Common/leaderboard.fxml");
    }
    public void userHomeButtonOnAction () throws IOException {
        Stage stage = (Stage) settingButton.getScene().getWindow();
        changeScene(stage, "/scene/User/profile.fxml");
    }

    public void  userHomeButtonOnActionEnter (){
        profileImageView.setOpacity(0.5);
        userHomeButton1.setVisible(true);
    }
    public void  userHomeButtonOnActionExit (){
        profileImageView.setOpacity(0.8);
        userHomeButton1.setVisible(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CharacterController.game = null;
        System.gc();
    }
}
