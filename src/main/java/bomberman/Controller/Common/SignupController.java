package bomberman.Controller.Common;

import bomberman.Model.UserAccountModel;
import bomberman.Model.User_CoinModel;
import bomberman.Model.User_MusicModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignupController extends CommonController {
    @FXML
    private Button registerButton;
    @FXML
    private Label signUpMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private ToggleGroup genderToggleGroup;
    @FXML
    private RadioButton maleRadioButton, femaleRadioButton;
    @FXML
    private DatePicker dobDatePicker;


    public void backButtonOnAction() throws IOException {
        super.backButtonOnAction("/scene/Common/login.fxml");
    }


    public void registerButtonOnAction() throws IOException, SQLException, InterruptedException {
        if(usernameTextField.getText().isBlank()
            || passwordPasswordField.getText().isBlank()
            || lastNameTextField.getText().isBlank()
            || firstNameTextField.getText().isBlank())
        {
            signUpMessageLabel.setText("Please enter all the fields!");
        }
        else
        {
            boolean canRegister = UserAccountModel.validateRegister(
                                                    usernameTextField.getText(),
                                                    passwordPasswordField.getText(),
                                                    firstNameTextField.getText(),
                                                    lastNameTextField.getText(),
                                                    ((RadioButton)(genderToggleGroup.getSelectedToggle())).getText(),
                                                    String.valueOf(dobDatePicker.getValue()));
            if( canRegister == true) {
                initUserData();

                Stage stage = (Stage) registerButton.getScene().getWindow();
                changeScene(stage, "/scene/Common/login.fxml");
            } else {
                signUpMessageLabel.setText("Username has already existed!");
            }
        }
    }
    public void initUserData() throws SQLException {
        try {
            int userID_Register = UserAccountModel.queryUserByUserName(usernameTextField.getText());
            User_MusicModel.insertUser_MusicData(userID_Register);
            User_CoinModel.insertUser_Coin(userID_Register);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}