package bomberman.Controller.Admin;

import bomberman.Controller.Common.CommonController;
import bomberman.Data.UserAccount;
import bomberman.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminUserAccountController extends CommonController {
    @FXML
    protected Button searchButton;
    @FXML
    protected Button detailButton;
    @FXML
    protected Button leaderboardButton;

    @FXML
    protected TextField usernameTextField;
    @FXML
    protected TextField fullnameTextField;


    /**
     * table view
     */
    @FXML
    private TableView<UserAccount> userAccountTableView;
    @FXML
    private TableColumn<UserAccount, Integer> userIDColumn;
    @FXML
    private TableColumn<UserAccount, String> usernameColumn;
    @FXML
    private TableColumn<UserAccount, String> passwordColumn;
    @FXML
    private TableColumn<UserAccount, String> createdDateColumn;
    @FXML
    private TableColumn<UserAccount, String> firstNameColumn;
    @FXML
    private TableColumn<UserAccount, String> lastNameColumn;
    @FXML
    private TableColumn<UserAccount, String> genderColumn;
    @FXML
    private TableColumn<UserAccount, String> DOBColumn;
    private ObservableList<UserAccount> userAccountList;

    public void reset() {
        int size = userAccountList.size();
        for (int i = 0; i < size; i++) {
            userAccountList.remove(0);
        }
    }

    public void search() throws SQLException, SQLException {
        reset();
        ResultSet resultSet =
                UserAccountModel.queryUserByName(usernameTextField.getText(), fullnameTextField.getText());

        while (resultSet.next()) {
            UserAccount userAccount = new UserAccount(
                    Integer.valueOf(resultSet.getInt("userID")),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("createdDate"),
                    resultSet.getString("firstname"),
                    resultSet.getString("lastname"),
                    resultSet.getString("gender"),
                    resultSet.getString("dob")
            );
            userAccountList.add(userAccount);
        }
    }

    public void delete(int userID) throws SQLException {
        UserAccountModel.deteleUserAccount(userID);
        GameScoreModel.deleteGameScoreRecord(userID);
        User_MusicModel.deleteUser_Music(userID);
        User_CoinModel.deleteUser_Coin(userID);
        User_CharModel.deleteUser_Char(userID);
    }

    public void detail(int userID) throws SQLException, IOException {
        Stage stage = (Stage) detailButton.getScene().getWindow();
        setDestUserID(userID);
        changeScene(stage, "/scene/Common/history-view.fxml");
    }

    public boolean alertDelete(UserAccount UA) {  //alert box

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);  //new alert object

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/Alert.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.UNDECORATED);

        alert.setHeaderText("DELETE USER ");// Header
        alert.setContentText(String.format("Do you wanna remove %s %s ?", UA.getFirstName(), UA.getLastName())); //Discription of warning

        alert.getDialogPane().setPrefSize(300, 100); //sets size of alert box

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            return true;
        } else {
            return false;
        }

    }


    private void initTable() {
        userAccountList = FXCollections.observableArrayList();

        /**
         * column in Table
         * get value from column in userAccountList, from each UserAccount.
         */
        userIDColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, Integer>("userID"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("password"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("lastName"));
        createdDateColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("createdDate"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("gender"));
        DOBColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("dob"));

        userAccountTableView.setItems(userAccountList);
    }

    public void backButtonOnAction() throws IOException {
        super.backButtonOnAction("/scene/Common/login.fxml");
    }

    public void leaderboardButtonOnAction() throws IOException {
        Stage stage = (Stage) leaderboardButton.getScene().getWindow();
        changeScene(stage, "/scene/Common/leaderboard.fxml");
    }

    public void searchButtonOnAction() throws SQLException {
        search();
    }

    public void deleteButtonOnAction() throws SQLException {
        UserAccount UA = userAccountTableView.getSelectionModel().getSelectedItem();
        if (alertDelete(UA) && !UA.getUsername().equals("admin")) {
            userAccountList.remove(UA);
            delete(UA.getUserID());
        }
    }

    public void detailButtonOnAction() throws SQLException, IOException {
        UserAccount UA = userAccountTableView.getSelectionModel().getSelectedItem();
        detail(UA.getUserID());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        initTable();

        usernameTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    searchButtonOnAction();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        fullnameTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    searchButtonOnAction();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
