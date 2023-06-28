package bomberman.Controller.Common;

import bomberman.Controller.User.ProfileController;
import bomberman.Data.Score;
import bomberman.Model.GameScoreModel;
import bomberman.Model.UserAccountModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static bomberman.Model.User_AvatarModel.queryUser_Avatar;

public class HistoryViewController extends CommonController {
    @FXML
    private Label highestScoreLabel;
    @FXML
    private Label userfullnameLabel;
    @FXML
    private Label gamesPlayedLabel;
    @FXML
    private Label rankLabel;
    @FXML
    private ImageView characterImage;
    @FXML
    private ImageView avatarImageView;
    @FXML
    private TableView<Score> historyScoreTableView;

    @FXML
    private TableColumn<Score, String> statusColumn;

    @FXML
    private TableColumn<Score, Integer> scoreColumn;
    @FXML
    private TableColumn<Score, Integer> levelColumn;

    @FXML
    private TableColumn<Score, String> createdTimeColumn;
    @FXML
    private TableColumn<Score, String> charNameColumn;
    @FXML
    private TableColumn<Score, ImageView> charFaceColumn;

    private ObservableList<Score> historyScoreList;


    public void backButtonOnAction() throws IOException, SQLException {
        if(MODE == MODE_USER)
            super.backButtonOnAction("/scene/User/home.fxml");
        else if (MODE == MODE_ADMIN)
            super.backButtonOnAction("/scene/Admin/admin-user-control.fxml");
        else if (MODE == MODE_USER_LB){
            MODE = MODE_USER;
            super.backButtonOnAction("/scene/Common/leaderboard.fxml");
        }
        else if (MODE == MODE_ADMIN_LB){
            MODE = MODE_ADMIN;
            super.backButtonOnAction("/scene/Common/leaderboard.fxml");
        }

    }

    public void initHistoryScoreTable() throws SQLException {
        historyScoreList = FXCollections.observableArrayList();

        GameScoreModel.queryAllScoreOf1User(getDestUserID(), historyScoreList);


        statusColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("statusGame"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<Score, Integer>("score"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<Score, Integer>("level"));
        createdTimeColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("createdTime"));
        charNameColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("charName"));
        charFaceColumn.setCellValueFactory(new PropertyValueFactory<Score, ImageView>("charFaceImage"));
        historyScoreTableView.setItems(historyScoreList);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ProfileController.loadUserAvatar(avatarImageView, getDestUserID());
            initHistoryScoreTable();
            int mostFvrChar = GameScoreModel.queryFavouriteCharacterOf1User(CommonController.getDestUserID());
            String pathFace = "/char/char-icon/" + mostFvrChar + "-face.png";
            Image img = new Image(getClass().getResourceAsStream(pathFace));
            characterImage.setImage(img);
            userfullnameLabel.setText(UserAccountModel.queryUserFullnameByUserId(getDestUserID()));
            gamesPlayedLabel.setText(String.valueOf(GameScoreModel.queryNumberGamesPlayedOf1User(getDestUserID())));
            highestScoreLabel.setText(String.valueOf(GameScoreModel.queryHighestScoreOf1User(getDestUserID())));
            rankLabel.setText(String.valueOf(GameScoreModel.queryRankingOf1User(getDestUserID())));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
