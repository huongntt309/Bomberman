package bomberman.Controller.Common;

import bomberman.Controller.User.SettingController;
import bomberman.Model.UserAccountModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static bomberman.Model.GameScoreModel.createGameScore;
import static bomberman.Model.GameScoreModel.dropGameScore;
import static bomberman.Model.MusicModel.*;
import static bomberman.Model.User_AvatarModel.createUser_AvatarTable;
import static bomberman.Model.User_AvatarModel.dropUser_AvatarTable;
import static bomberman.Model.User_MusicModel.createMusicTable;
import static bomberman.Model.User_MusicModel.dropTableUser_Music;

public class LoginController extends CommonController {
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Label logInMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;

    public void signUpButtonOnAction() throws IOException {
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        changeScene(stage, "/scene/Common/signup.fxml");
    }

    public void logInButtonOnAction() throws SQLException, IOException {
//        dropUser_AvatarTable();
//        createUser_AvatarTable();

        if(usernameTextField.getText().isBlank() || passwordPasswordField.getText().isBlank()) {
            logInMessageLabel.setText("Please enter username and password");
        } else {
            int userID_ = UserAccountModel.validatelogIn(usernameTextField.getText(), passwordPasswordField.getText());
            if(userID_ > 0){
                setUserID(userID_);
                setDestUserID(userID_);
                Stage stage = (Stage) logInButton.getScene().getWindow();
                if(usernameTextField.getText().equals("admin")){
                    MODE = MODE_ADMIN;
                    changeScene(stage, "/scene/Admin/admin-user-control.fxml");
                } else {
                    SettingController.initUserMusic();
                    SettingController.initMediaPlayer();
                    MODE = MODE_USER;
                    changeScene(stage, "/scene/User/home.fxml");
                }
            } else {
                logInMessageLabel.setText("Invalid login. Please try again");
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * KeyPressed.
         */

        usernameTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    logInButtonOnAction();
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        passwordPasswordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    logInButtonOnAction();
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}