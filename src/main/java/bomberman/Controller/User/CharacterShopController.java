package bomberman.Controller.User;

import bomberman.Controller.Common.CommonController;
import bomberman.Model.CharacterModel;
import bomberman.Model.User_CharModel;
import bomberman.Model.User_CoinModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CharacterShopController extends CommonController {

    @FXML
    private Button charChoosingButton;
    @FXML
    private Button buyButton;
    @FXML
    private Button sellButton;
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

    @FXML
    private Label charNameLabel;
    @FXML
    private Label charPriceLabel;
    @FXML
    private Label userCoinLabel;
    @FXML
    private Label notiLabel;

    private final int BUY = 0;
    private final int SELL = 1;
    int userCoin;
    String[] charURL = new String[10];
    String[] path = new String[10];
    String[] charName = new String[10];
    int[] charBuyPrice = new int[10];
    int[] charSellPrice = new int[10];

    public static int currentChoiceID = 0;
    String img = "/image/menu/shop/unlockIcon.png";
    Image unlockImage = new Image(getClass().getResourceAsStream(img));
    String imgL = "/image/menu/shop/lock.png";
    Image lockImage = new Image(getClass().getResourceAsStream(imgL));

    ArrayList<Integer> purchasedChars = new ArrayList<>();

    public void buyButtonOnAction() throws SQLException, IOException {
        if (currentChoiceID == 0) {
            notiLabel.setText("Choose any character!");
            return;
        }

        if (alert(BUY)) {
            /**
             * chưa mua buy(int curChoice)
             * 1. đủ tiền -> mua
             * 2. chưa đủ tiền -> báo nghỉ =))
             */
            if (userCoin >= charBuyPrice[currentChoiceID]) {
                userCoin -= charBuyPrice[currentChoiceID];
                buyCharacter(currentChoiceID);
                notiLabel.setText("You have bought " + charName[currentChoiceID]);
                notiLabel.setStyle("-fx-text-fill: green");
                userCoinLabel.setText(String.valueOf(userCoin));

                // reset purchasedChars;
                resetCharacterImage();
                buyButton.setVisible(false);
            } else {
                notiLabel.setText("You don't have enough coin");
                notiLabel.setStyle("-fx-text-fill: black");
            }
        }
    }

    public void sellButtonOnAction() throws SQLException, IOException {
        if (currentChoiceID == 0) {
            notiLabel.setText("Choose any character!");
            return;
        }

        if (alert(SELL)) {
            userCoin += charSellPrice[currentChoiceID];
            sellCharacter(currentChoiceID);
            notiLabel.setText("You have sold " + charName[currentChoiceID]);
            notiLabel.setStyle("-fx-text-fill: red");
            userCoinLabel.setText(String.valueOf(userCoin));
            sellButton.setVisible(false);
            // reset purchasedChars;
            resetCharacterImage();
        }
    }

    public boolean alert(int choice) {  //alert box

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);  //new alert object
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/Alert.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/image/menu/shop/shop.png").toString()));

        stage.initStyle(StageStyle.UNDECORATED);

        alert.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/" + charURL[currentChoiceID]),
                72, 72, false, true)));
        if(choice == BUY){
            alert.setHeaderText("Buy " + charName[currentChoiceID]);// Header
            alert.setContentText(String.format("Do you wanna buy %s with $ %d ?", charName[currentChoiceID], charBuyPrice[currentChoiceID])); //Discription of warning
        }
        else if(choice == SELL) {
            alert.setTitle("SELL!");  //warning box title
            alert.setHeaderText("Sell " + charName[currentChoiceID]);// Header
            alert.setContentText(String.format("Do you wanna sell %s with $ %d ?", charName[currentChoiceID], charSellPrice[currentChoiceID])); //Discription of warning
        }
        alert.getDialogPane().setPrefSize(300, 100); //sets size of alert box

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            return true;
        } else {
            return false;
        }

    }

    public void buyCharacter(int curChoice) throws SQLException {
        User_CharModel.insertUser_Char(getUserID(), curChoice);
        User_CoinModel.updateUser_Coin(getUserID(), userCoin);
    }

    public void sellCharacter(int curChoice) throws SQLException {
        User_CharModel.deleteUser_CharSell(getUserID(), curChoice);
        User_CoinModel.updateUser_Coin(getUserID(), userCoin);
    }

    public void applyChange(int curChoice) {
        charChoosingButton.setStyle(path[curChoice]);
        charNameLabel.setText(charName[curChoice]);

        for (int charID : purchasedChars) {
            if (currentChoiceID == charID) {
                /**
                 * đã mua -> bán
                 */
                charPriceLabel.setText(String.valueOf(charSellPrice[curChoice]));
                sellButton.setVisible(true);
                buyButton.setVisible(false);
                return;
            }
        }
        /**
         * chưa mua -> mua
         */
        charPriceLabel.setText(String.valueOf(charBuyPrice[curChoice]));
        sellButton.setVisible(false);
        buyButton.setVisible(true);
    }

    /**
     * ButtonOnAction
     */
    public void char1ButtonOnAction() throws SQLException {
        currentChoiceID = 1;
        applyChange(currentChoiceID);
    }

    public void char2ButtonOnAction() {
        currentChoiceID = 2;
        applyChange(currentChoiceID);
    }

    public void char3ButtonOnAction() {
        currentChoiceID = 3;
        applyChange(currentChoiceID);
    }

    public void char4ButtonOnAction() {
        currentChoiceID = 4;
        applyChange(currentChoiceID);
    }

    public void char5ButtonOnAction() {
        currentChoiceID = 5;
        applyChange(currentChoiceID);
    }

    public void char6ButtonOnAction() {
        currentChoiceID = 6;
        applyChange(currentChoiceID);
    }

    public void char7ButtonOnAction() {
        currentChoiceID = 7;
        applyChange(currentChoiceID);
    }

    public void char8ButtonOnAction() {
        currentChoiceID = 8;
        applyChange(currentChoiceID);
    }

    public void char9ButtonOnAction() {
        currentChoiceID = 9;
        applyChange(currentChoiceID);
    }

    /**
     * ButtonOnActionMouseEnter
     */
    public void char1ButtonOnActionMouseEnter() throws SQLException {
        lock1Image.setOpacity(0.8);
        lock1Image.setImage(unlockImage);
    }

    public void char2ButtonOnActionMouseEnter() throws SQLException {
        lock2Image.setOpacity(0.8);
        lock2Image.setImage(unlockImage);
    }

    public void char3ButtonOnActionMouseEnter() throws SQLException {
        lock3Image.setOpacity(0.8);
        lock3Image.setImage(unlockImage);
    }

    public void char4ButtonOnActionMouseEnter() throws SQLException {
        lock4Image.setOpacity(0.8);
        lock4Image.setImage(unlockImage);
    }


    public void char5ButtonOnActionMouseEnter() throws SQLException {
        lock5Image.setOpacity(0.8);
        lock5Image.setImage(unlockImage);
    }

    public void char6ButtonOnActionMouseEnter() throws SQLException {
        lock6Image.setOpacity(0.8);
        lock6Image.setImage(unlockImage);
    }

    public void char7ButtonOnActionMouseEnter() throws SQLException {
        lock7Image.setOpacity(0.8);
        lock7Image.setImage(unlockImage);
    }

    public void char8ButtonOnActionMouseEnter() throws SQLException {
        lock8Image.setOpacity(0.8);
        lock8Image.setImage(unlockImage);
    }

    public void char9ButtonOnActionMouseEnter() throws SQLException {
        lock9Image.setOpacity(0.8);
        lock9Image.setImage(unlockImage);
    }

    /**
     * ButtonOnActionMouseExit
     */

    public void char1ButtonOnActionMouseExit() throws SQLException {
        lock1Image.setOpacity(0.5);

        lock1Image.setImage(lockImage);
    }

    public void char2ButtonOnActionMouseExit() throws SQLException {
        lock2Image.setOpacity(0.5);

        lock2Image.setImage(lockImage);
    }

    public void char3ButtonOnActionMouseExit() throws SQLException {
        lock3Image.setOpacity(0.5);

        lock3Image.setImage(lockImage);
    }

    public void char4ButtonOnActionMouseExit() throws SQLException {
        lock4Image.setOpacity(0.5);

        lock4Image.setImage(lockImage);
    }

    public void char5ButtonOnActionMouseExit() throws SQLException {
        lock5Image.setOpacity(0.5);

        lock5Image.setImage(lockImage);
    }

    public void char6ButtonOnActionMouseExit() throws SQLException {
        lock6Image.setOpacity(0.5);

        lock6Image.setImage(lockImage);
    }

    public void char7ButtonOnActionMouseExit() throws SQLException {
        lock7Image.setOpacity(0.5);

        lock7Image.setImage(lockImage);
    }

    public void char8ButtonOnActionMouseExit() throws SQLException {
        lock8Image.setOpacity(0.5);

        lock8Image.setImage(lockImage);
    }

    public void char9ButtonOnActionMouseExit() throws SQLException {
        lock9Image.setOpacity(0.5);

        lock9Image.setImage(lockImage);
    }


    public void initCharacter() throws SQLException {
        CharacterModel.loadCharacterShop(charURL, path, charName, charBuyPrice, charSellPrice);
    }

    public void backButtonOnAction() throws IOException, SQLException {
        super.backButtonOnAction("/scene/User/home.fxml");
    }

    public void resetCharacterImage() throws IOException, SQLException {
        purchasedChars.clear();
        User_CharModel.queryPurchasedChars(getUserID(), purchasedChars);
        char1Button.setOpacity(0.2);
        lock1Image.setVisible(true);
        char2Button.setOpacity(0.2);
        lock2Image.setVisible(true);
        char3Button.setOpacity(0.2);
        lock3Image.setVisible(true);
        char4Button.setOpacity(0.2);
        lock4Image.setVisible(true);
        char5Button.setOpacity(0.2);
        lock5Image.setVisible(true);
        char6Button.setOpacity(0.2);
        lock6Image.setVisible(true);
        char7Button.setOpacity(0.2);
        lock7Image.setVisible(true);
        char8Button.setOpacity(0.2);
        lock8Image.setVisible(true);
        char9Button.setOpacity(0.2);
        lock9Image.setVisible(true);
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initCharacter();
            currentChoiceID = 0;
            userCoin = User_CoinModel.queryCoin(getUserID());
            userCoinLabel.setText(String.valueOf(userCoin));
            resetCharacterImage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
