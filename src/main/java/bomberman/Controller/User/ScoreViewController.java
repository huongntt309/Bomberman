package bomberman.Controller.User;

import bomberman.Controller.Common.CommonController;
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

import static bomberman.BombermanGame.charID;
import static bomberman.BombermanGame.score;

public class ScoreViewController extends CommonController {
    @FXML
    private Label curScoreLabel;
    @FXML
    private Label highestScoreLabel;
    @FXML
    private Label userfullnameLabel;
    @FXML
    private Label rankLabel;
    @FXML
    private ImageView characterImage;

    @FXML
    private TableView<Score> rankingAllTimeTableView;

    @FXML
    private TableColumn<Score, Integer> highestScoreAllTimeColumn;

    @FXML
    private TableColumn<Score, String> usernameAllTimeColumn;
    @FXML
    private TableColumn<Score, ImageView> userAvatarAllTimeColumn;
    private ObservableList<Score> rankingAllTimeList;

    @FXML
    private TableView<Score> rankingDailyTableView;

    @FXML
    private TableColumn<Score, Integer> highestScoreDailyColumn;

    @FXML
    private TableColumn<Score, String> usernameDailyColumn;
    @FXML
    private TableColumn<Score, ImageView> userAvatarDailyColumn;
    private ObservableList<Score> rankingDailyList;

    public void backButtonOnAction() throws IOException, SQLException {
        super.backButtonOnAction("/scene/User/home.fxml");
    }

    public void initAllTimeRankingTable() throws SQLException, IOException {
        rankingAllTimeList = FXCollections.observableArrayList();

        GameScoreModel.queryHighestScoreAllTime(rankingAllTimeList);

        /**
         * column in Table
         * get value from column in gameScoreList, from each Score.
         */
        userAvatarAllTimeColumn.setCellValueFactory(new PropertyValueFactory<Score, ImageView>("userAvatar"));
        usernameAllTimeColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("fullName"));

        highestScoreAllTimeColumn.setCellValueFactory(new PropertyValueFactory<Score, Integer>("score"));

        rankingAllTimeTableView.setItems(rankingAllTimeList);
    }

    public void initDailyRankingTable() throws SQLException, IOException {
        rankingDailyList = FXCollections.observableArrayList();

        GameScoreModel.queryHighestScoreDaily(rankingDailyList);

        /**
         * column in Table
         * get value from column in rankingList, from each Score.
         */
        userAvatarDailyColumn.setCellValueFactory(new PropertyValueFactory<Score, ImageView>("userAvatar"));

        usernameDailyColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("fullName"));

        highestScoreDailyColumn.setCellValueFactory(new PropertyValueFactory<Score, Integer>("score"));

        rankingDailyTableView.setItems(rankingDailyList);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initAllTimeRankingTable();
            initDailyRankingTable();
            String pathFace = "/char/char-icon/" + charID + "-face.png";
            Image img = new Image(getClass().getResourceAsStream(pathFace));
            characterImage.setImage(img);
            userfullnameLabel.setText(UserAccountModel.queryUserFullnameByUserId(getUserID()));
            highestScoreLabel.setText(String.valueOf(GameScoreModel.queryHighestScoreOf1User(getUserID())));
            curScoreLabel.setText(String.valueOf(score));
            rankLabel.setText(String.valueOf(GameScoreModel.queryRankingOf1User(getUserID())));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
