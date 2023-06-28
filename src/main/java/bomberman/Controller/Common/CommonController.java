package bomberman.Controller.Common;

import bomberman.Game_Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
abstract public class CommonController implements Initializable {
    protected static int userID;
    protected static int destUserID;
    @FXML
    protected ImageView avatarImageView;

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        CommonController.userID = userID;
    }

    public static int getDestUserID() {
        return destUserID;
    }

    public static void setDestUserID(int destUserID) {
        CommonController.destUserID = destUserID;
    }

    protected static int MODE;
    protected static int MODE_USER = 0;
    protected static int MODE_ADMIN = 1;
    protected static int MODE_USER_LB = 2;
    protected static int MODE_ADMIN_LB = 3;
    @FXML
    protected Button backButton;
    @FXML
    protected Button quitButton;

    public static void changeScene(Stage stage, String path) throws IOException {
        FXMLLoader fxmlLoader_signup = new FXMLLoader(Game_Login.class.getResource(path));
        Scene scene = new Scene(fxmlLoader_signup.load(), 800, 600);
        stage.setScene(scene);
        stage.show();

    }
    public void quitButtonOnAction() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }
    public void backButtonOnAction(String path) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        changeScene(stage, path);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
