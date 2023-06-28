package bomberman.Controller.Common;

import bomberman.Data.Score;
import bomberman.Data.UserAccount;
import bomberman.Model.GameScoreModel;
import bomberman.Model.UserAccountModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LeaderboardController extends CommonController {
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
    @FXML
    private Button detailButtonAllTime;
    @FXML
    private Button detailButtonDaily;
    private ObservableList<Score> rankingDailyList;

    public void backButtonOnAction() throws IOException, SQLException {
        if(MODE == MODE_USER)
            super.backButtonOnAction("/scene/User/home.fxml");
        else if (MODE == MODE_ADMIN)
            super.backButtonOnAction("/scene/Admin/admin-user-control.fxml");
    }

    public void detailButtonAllTimeOnAction() throws SQLException, IOException {
        Score UA = rankingAllTimeTableView.getSelectionModel().getSelectedItem();

        setDestUserID(UA.getUserID());
        detail();
    }
    public void detailButtonDailyOnAction() throws SQLException, IOException {
        Score UA = rankingDailyTableView.getSelectionModel().getSelectedItem();
        setDestUserID(UA.getUserID());
        detail();
    }
    public void detail() throws IOException {
        if(MODE == MODE_ADMIN) MODE = MODE_ADMIN_LB;
        else if(MODE == MODE_USER) MODE = MODE_USER_LB;

        Stage stage = (Stage) detailButtonAllTime.getScene().getWindow();
        changeScene(stage, "/scene/Common/history-view.fxml");
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
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
