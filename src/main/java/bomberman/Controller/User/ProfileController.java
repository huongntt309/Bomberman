package bomberman.Controller.User;

import bomberman.Controller.Common.CommonController;
import bomberman.Data.DatabaseConnection;
import bomberman.Model.GameScoreModel;
import bomberman.Model.UserAccountModel;
import bomberman.Model.User_AvatarModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static bomberman.Model.User_AvatarModel.hasAnAvatar;
import static bomberman.Model.User_AvatarModel.queryUser_Avatar;


public class ProfileController extends CommonController {
    /**
     * chưa nghĩ ra cách để chuyển query về model
     */
    private final int SUCCESS_PASS = 0;

    private final int FAIL_PASS = 1;

    private final int SUCCESS_NAME = 6;

    private final int FAIL_NAME = 7;
    private final int OLDPASS = 2;

    private final int NEWPASS = 3;
    private final int FIRSTNAME = 4;
    private final int LASTNAME = 5;
    @FXML
    private Button changeAvatarButton;
    @FXML
    private ImageView avatarImageView;
    @FXML
    private Label highestScoreLabel;
    @FXML
    private Label userfullnameLabel;
    @FXML
    private Label gamesPlayedLabel;
    @FXML
    private Label rankLabel;

    private Desktop desktop = Desktop.getDesktop();

    public void backButtonOnAction() throws IOException {
        if (MODE == MODE_USER)
            super.backButtonOnAction("/scene/User/home.fxml");
        else if (MODE == MODE_ADMIN)
            super.backButtonOnAction("/scene/Admin/admin-user-control.fxml");
        else if (MODE == MODE_USER_LB) {
            MODE = MODE_USER;
            super.backButtonOnAction("/scene/Common/leaderboard.fxml");
        } else if (MODE == MODE_ADMIN_LB) {
            MODE = MODE_ADMIN;
            super.backButtonOnAction("/scene/Common/leaderboard.fxml");
        }
    }

    public void historyViewButtonOnAction () throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        /**
         * view từ home
         */
        setDestUserID(getUserID());
        changeScene(stage, "/scene/Common/history-view.fxml");
    }
    public void changeAvatarButtonOnAction() throws IOException {
        Stage stage = (Stage) changeAvatarButton.getScene().getWindow();
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        // FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        //fc.getExtensionFilters().addAll(ext1, ext2);
        fc.getExtensionFilters().addAll(ext2);

        File file = fc.showOpenDialog(stage);
        try {
            User_AvatarModel.insertUser_Avatar(getUserID(), file);
            Image image = queryUser_Avatar(getUserID());
            avatarImageView.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void loadUserAvatar(ImageView avatarImageView, int userID) throws IOException, SQLException {
        if (hasAnAvatar(userID)) {
            try {
                Image image = queryUser_Avatar(userID);
                avatarImageView.setImage(image);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            Image defaultAva;
            String gender = UserAccountModel.queryGenderByUserId(userID);
            if (gender.equals("Female")) {
                defaultAva = new Image(ProfileController.class.getResourceAsStream("/image/menu/profile/femaleAva.png"));
            } else {
                defaultAva = new Image(ProfileController.class.getResourceAsStream("/image/menu/profile/maleAva.png"));
            }
            avatarImageView.setImage(defaultAva);
        }
    }

    public void changeUsernameButtonOnAction() throws SQLException {
        String fname = showTextInputDialog(FIRSTNAME);
        if (!fname.isEmpty()) {
            String lname = showTextInputDialog(LASTNAME);
            if(!lname.isEmpty()){
                UserAccountModel.changeName(getUserID(), fname, lname);
                alertChangePass(SUCCESS_NAME);
                userfullnameLabel.setText(fname + " " + lname);
            }
            else {
                alertChangePass(FAIL_NAME);
            }
        } else {
            alertChangePass(FAIL_NAME);
        }
    }

    public void changePasswordButtonOnAction() throws SQLException {
        String inputPass = showTextInputDialog(OLDPASS);
        if (UserAccountModel.validatelogIn(getUserID(), inputPass)) {
            String inputNewPass = showTextInputDialog(OLDPASS);
            if(!inputNewPass.isEmpty()){
                UserAccountModel.changePassword(getUserID(), inputNewPass);
                alertChangePass(SUCCESS_PASS);
            }
            else {
                alertChangePass(FAIL_PASS);
            }
        } else {
            alertChangePass(FAIL_PASS);
        }
    }


    public String showTextInputDialog(int status){
        TextInputDialog td = new TextInputDialog();

        if(status == OLDPASS)
            td.setHeaderText("ENTER OLD PASSWORD");
        if(status == NEWPASS)
            td.setHeaderText("ENTER NEW PASSWORD");
        if(status == FIRSTNAME)
            td.setHeaderText("ENTER NEW FIRST NAME");
        if(status == LASTNAME)
            td.setHeaderText("ENTER NEW LAST NAME");

        DialogPane dialogPane = td.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/Alert.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        Stage stage = (Stage) td.getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.UNDECORATED);
        Optional<String> result = td.showAndWait();
        String inputPass = result.get();
        return inputPass;
    }
    public void alertChangePass(int status) {  //alert box
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);  //new alert object
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/Alert.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane-pass");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.UNDECORATED);
        if(status == SUCCESS_PASS){
            alert.setHeaderText("SUCCESS");// Header
            alert.setContentText("YOU HAVE CHANGED PASSWORD"); //Discription of warning

        }
        else if((status == FAIL_PASS)){
            alert.setHeaderText("WRONG PASSWORD");// Header
            alert.setContentText("YOU HAVE ENTER UNCORRECTED PASSWORD"); //Discription of warning

        }
        else if(status == SUCCESS_NAME){
            alert.setHeaderText("SUCCESS");// Header
            alert.setContentText("YOU HAVE CHANGED YOUR FULL NAME"); //Discription of warning

        }
        else if((status == FAIL_NAME)){
            alert.setHeaderText("FAIL");// Header
            alert.setContentText("YOU HAVE ENTER UNCORRECTED NAME"); //Discription of warning

        }
        alert.getDialogPane().setPrefSize(400, 100); //sets size of alert box
        Optional<ButtonType> result = alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadUserAvatar(avatarImageView, getUserID());
            userfullnameLabel.setText(UserAccountModel.queryUserFullnameByUserId(getUserID()));
            gamesPlayedLabel.setText(String.valueOf(GameScoreModel.queryNumberGamesPlayedOf1User(getUserID())));
            highestScoreLabel.setText(String.valueOf(GameScoreModel.queryHighestScoreOf1User(getUserID())));
            rankLabel.setText(String.valueOf(GameScoreModel.queryRankingOf1User(getUserID())));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
