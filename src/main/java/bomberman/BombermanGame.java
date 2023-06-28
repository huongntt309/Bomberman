package bomberman;

import bomberman.Controller.User.CharacterController;
import bomberman.Controller.Common.CommonController;
import bomberman.Controller.User.SettingController;
import bomberman.Entities.Static.StaticEntity;
import bomberman.Model.GameScoreModel;
import bomberman.Model.UserAccountModel;
import bomberman.Model.User_CoinModel;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import bomberman.Entities.*;
import bomberman.Entities.Dynamic.Character.Bomber;
import bomberman.Entities.Dynamic.Character.Enemy.Enemy;
import bomberman.Component.*;
import bomberman.Map.*;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Thread.sleep;

@Getter
@Setter
public class BombermanGame extends Application implements Initializable {

    // Resolution của cửa sổ game.
    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    public BombermanGame(int GAME_MODE) {
//      with MODE_NORMAL = 0;
//      with MODE_AI = 1;
        this.GAME_MODE = GAME_MODE;
    }

    // Thuộc tính Game
    public int level = 1;                                            // khai báo lv

    public static int score = 0;                                            // khai báo lv
    public static int charID;

    public static Bomber bomberman;

    public static int GAME_MODE;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_AI = 1;

    // List tất cả Entity trong game
    public static final List<StaticEntity> staticEntities = new ArrayList<>();    // static entities
    public static final List<Entity> dynamicEntities = new ArrayList<>();   // dynamic entities
    public static final List<Enemy> enemiesList = new ArrayList<>();            // list enenmy


    public static int lives = 10;                                            // số mạng
    public static int countTime = 0;

    public static int gatePosX;
    public static int gatePosY;
    public static int keyPosX;
    public static int keyPosY;

    public static boolean gameRunning = true;
    public static AnimationTimer timer;

    private GraphicsContext gc;                                       // gọi class GraphicsContext (về đồ họa 2D)
    private Canvas canvas; // tạo bản đồ, set tọa độ trong canva
    private Group root = new Group();

    /**
     * for a small menu in game
     */
    Text levelText, scoreText, notiText, infoText;

    /**
     * img
     */
    ImageView charFace;
    ImageView backdrop_left;
    ImageView backdrop_right;
    Button bgMusicButton = new Button("");
    final boolean[] SOUND_ON = {true};
    Background bgSoundOff;
    Background bgSoundOn;
    Background bgSoundOffHover;
    Background bgSoundOnHover;

    Button pauseButton = new Button("");
    final boolean[] GAME_RUNNING = {true};
    Background pauseOff;
    Background pauseOn;
    Background pauseOffHover;
    Background pauseOnHover;

    /**
     * a img
     */

    Color colorbg = new Color(0, 0, 0, 0.8);
    ImageView afterDeathImage;

    /**
     * animation after death
    =
     */
    boolean ANIMATION_CALL = false;
    TranslateTransition translate1 = new TranslateTransition();
    TranslateTransition translate2 = new TranslateTransition();
    TranslateTransition translate3 = new TranslateTransition();
    TranslateTransition translate4 = new TranslateTransition();
    TranslateTransition translate5 = new TranslateTransition();
    TranslateTransition translate6 = new TranslateTransition();
    FadeTransition fade1 = new FadeTransition();
    FadeTransition fade2 = new FadeTransition();
    FadeTransition fade3 = new FadeTransition();

    @Override
    public void start(Stage stage) throws SQLException {
        initGamePlay();
        SettingController.playSFX(SettingController.SFXMODE_GAME_START);
        // Tạo Canvas
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container - theo thứ tự render, ở dưới add trước.
        root.getChildren().add(canvas);
        root.getChildren().add(afterDeathImage);
        root.getChildren().add(bgMusicButton);
        root.getChildren().add(pauseButton);

        root.getChildren().add(charFace);
        root.getChildren().add(backdrop_left);
        root.getChildren().add(backdrop_right);
        root.getChildren().add(infoText);
        root.getChildren().add(levelText);
        root.getChildren().add(scoreText);
        root.getChildren().add(notiText);
        // Tao scene, thêm scene vào stage


        timer = new AnimationTimer() {           // set ván mới
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        Map.MapLoader(0);
        timer.start();

        Scene scene = new Scene(root, WIDTH, HEIGHT, colorbg);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (bomberman == null || bomberman.isAlive() == false) {
                try {
                    timer.stop();
                    timer = null;
                    GameScoreModel.insertGameScore(CommonController.getUserID(), score, level, charID, "Lose");
                    int coin = User_CoinModel.queryCoin(CommonController.getUserID());
                    User_CoinModel.updateUser_Coin(CommonController.getUserID(), coin + score);
                    resetGamePlay();
                    SettingController.continueBgMusic();
                    CommonController.changeScene(stage, "/scene/User/score-view.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            /**
             * for AIbomber, no keypress, still need to suicide
             */
            else
                    bomberman.handleKeyPressedEvent(event.getCode());
        });
        /**
         * for AIbomber, no keypress
         */
        scene.addEventFilter(KeyEvent.KEY_RELEASED,
                event -> bomberman.handleKeyReleasedEvent(event.getCode())); // xử lý sự kiện nhâp bàn phím

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void update() {
        bomberman.update();
        if (bomberman.isNextLvl() == true) {
            try {
                upLevelGame();
                if (level == 10) {
                    /**
                     * đoạn xử lý khi bomber chơi hết các map và win
                     */
                } else
                    notiText.setText("UP LEVEL " + level);
                sleep(500);
                levelText.setText("LEVEL: " + level);
                return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (bomberman.isAlive() == false && !ANIMATION_CALL) {
            System.out.println(ANIMATION_CALL);
            ANIMATION_CALL = true;
            System.out.println("death");
            SettingController.playSFX(SettingController.SFXMODE_CHARACTER_DEATH);
            translate1.play();
            translate2.play();
            translate3.play();
            translate4.play();
            translate5.play();
            translate6.play();
            fade1.play();
            fade2.play();
            fade3.play();

            timer.stop();
            SettingController.stopBgMusic();
        } else if (countTime == 0) {
            notiText.setText("");
        }
        // chạy các sự kiện của bomber
        for (int i = 0; i < staticEntities.size(); i++) {
            staticEntities.get(i).update();
        }
        for (int i = 0; i < dynamicEntities.size(); i++) {
            dynamicEntities.get(i).update();
        }

        for (int i = 0; i < enemiesList.size(); i++) {
            enemiesList.get(i).update();
        }
    }

    public void render() { // render hình ảnh để hiển thị lên game
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        scoreText.setText("SCORE: " + score);

        for (int i = staticEntities.size() - 1; i >= 0; i--) {
            staticEntities.get(i).render(gc);
        }
        for (int i = 0; i < dynamicEntities.size(); i++)
            dynamicEntities.get(i).render(gc);
        bomberman.render(gc); // chạy các sự kiện của bomber
        enemiesList.forEach(g -> g.render(gc));
    }

    public void resetGamePlay() throws SQLException {
        dynamicEntities.clear();
        enemiesList.clear();
        Bomber.bombs.clear();
        bomberman = null;
        staticEntities.clear();

        SettingController.stopSFX();

        SpriteSheet.initRandomMapTiles();
        SpriteSheet.initCharacterPath();
        SpriteSheet.initBombSkin(SpriteSheet.bomb_2);

        Sprite.resetCharacterSprite();
        Sprite.resetTiles();
        Sprite.resetBomb();
        Sprite.resetFlame();
        Sprite.resetEnemy();
    }

    public void initGamePlay() throws SQLException {
        score = 0;
        level = 1;
        charID = CharacterController.currentChoiceID;

        SettingController.stopBgMusic();

        SpriteSheet.initRandomMapTiles();
        SpriteSheet.initCharacterPath();
        SpriteSheet.initBombSkin(SpriteSheet.bomb_2);

        Sprite.resetCharacterSprite();
        Sprite.resetTiles();
        Sprite.resetBomb();
        Sprite.resetFlame();
        Sprite.resetEnemy();

        String pathFace = "/char/char-icon/" + charID + "-face.png";
        Image img = new Image(getClass().getResourceAsStream(pathFace),
                52, 52, false, true);
        charFace = new ImageView(img);
        charFace.setCache(true);
        charFace.setX(Sprite.DEFAULT_SIZE * 1);
        charFace.setY(HEIGHT - 54);


        String backdrop_ = "/image/menuGameLoop/slot_backdrop.png";
        Image img_backdrop = new Image(getClass().getResourceAsStream(backdrop_),
                Sprite.DEFAULT_SIZE * 9, 52, false, true);
        backdrop_left = new ImageView(img_backdrop);
        backdrop_left.setCache(true);
        backdrop_left.setX(Sprite.DEFAULT_SIZE * 3);
        backdrop_left.setY(HEIGHT - 54);


        backdrop_right = new ImageView(img_backdrop);
        backdrop_right.setCache(true);
        backdrop_right.setX(Sprite.DEFAULT_SIZE * 13);
        backdrop_right.setY(HEIGHT - 54);

        /**
         * TEXT cùng HEIGHT
         */
        String fullName = UserAccountModel.queryUserFullnameByUserId(CommonController.getUserID());
        infoText = new Text(fullName);
        infoText.setFont(Font.font("Berlin Sans FB Demi Bold", FontWeight.BOLD, 20));
        infoText.setFill(Color.WHITE);
        infoText.setX(Sprite.DEFAULT_SIZE * 4);
        infoText.setY(HEIGHT - 20);
        // căn đo may rủi


        levelText = new Text("LEVEL: " + level);
        levelText.setFont(Font.font("Berlin Sans FB Demi Bold", FontWeight.BOLD, 20));
        levelText.setFill(Color.WHITE);
        levelText.setX(Sprite.DEFAULT_SIZE * 14);
        levelText.setY(HEIGHT - 20);


        scoreText = new Text("SCORE: " + score);
        scoreText.setFont(Font.font("Berlin Sans FB Demi Bold", FontWeight.BOLD, 20));
        scoreText.setFill(Color.WHITE);
        scoreText.setX(Sprite.DEFAULT_SIZE * 17);
        scoreText.setY(HEIGHT - 20);


        /**
         * giữa màn hình
         */
        notiText = new Text("");
        notiText.setFont(Font.font("Berlin Sans FB Demi Bold", FontWeight.BOLD, 100));
        notiText.setFill(Color.WHITE);
        notiText.setX(WIDTH / 2 - 230);
        notiText.setY(HEIGHT / 2);

        String afterDeathImageP = "/image/menuGameLoop/loseScene2.png";
        Image img_afterdeath = new Image(getClass().getResourceAsStream(afterDeathImageP),
                WIDTH, HEIGHT, false, true);
        afterDeathImage = new ImageView(img_afterdeath);
        afterDeathImage.setX(0);
        afterDeathImage.setY(0);
        afterDeathImage.setOpacity(0);

        /**
         * button bg music
         */

        /**
         * init bg
         */
        Image i1 = new Image(getClass().getResourceAsStream("/image/menuGameLoop/sound_off.png"),
                36, 36, false, true);

        BackgroundImage bgSoundOff_ =
                new BackgroundImage(i1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bgSoundOff = new Background(bgSoundOff_);

        /**
         * init bg
         */
        Image i2 = new Image(getClass().getResourceAsStream("/image/menuGameLoop/sound_on.png"),
                36, 36, false, true);

        BackgroundImage bgSoundOn_ =
                new BackgroundImage(i2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bgSoundOn = new Background(bgSoundOn_);

        /**
         * init bg
         */
        Image i3 = new Image(getClass().getResourceAsStream("/image/menuGameLoop/sound_off_hover.png"),
                36, 36, false, true);

        BackgroundImage bgSoundOffHover_ =
                new BackgroundImage(i3, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bgSoundOffHover = new Background(bgSoundOffHover_);

        /**
         * init bg
         */
        Image i4 = new Image(getClass().getResourceAsStream("/image/menuGameLoop/sound_on_hover.png"),
                36, 36, false, true);

        BackgroundImage bgSoundOnHover_ =
                new BackgroundImage(i4, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bgSoundOnHover = new Background(bgSoundOnHover_);

        bgMusicButton.setPrefSize(36, 36);

//        bgMusicButton.setTranslateX(Sprite.DEFAULT_SIZE * 24 + 5);
        bgMusicButton.setTranslateY(HEIGHT - 52);
//        bgMusicButton.setTranslateY(HEIGHT/2);/
        bgMusicButton.setTranslateX(Sprite.DEFAULT_SIZE * 22.2);

        bgMusicButton.setBackground(bgSoundOn);
        /**
         * button addEventFilter
         */
        bgMusicButton.addEventFilter(MouseEvent.MOUSE_ENTERED,
                e -> {
                    if (SOUND_ON[0])
                        bgMusicButton.setBackground(bgSoundOnHover);
                    else
                        bgMusicButton.setBackground(bgSoundOffHover);
                });

        bgMusicButton.addEventFilter(MouseEvent.MOUSE_EXITED,
                e -> {
                    if (SOUND_ON[0])
                        bgMusicButton.setBackground(bgSoundOn);
                    else
                        bgMusicButton.setBackground(bgSoundOff);
                });

        bgMusicButton.addEventFilter(MouseEvent.MOUSE_CLICKED,
                e -> {
                    // KHI SOUND ON, CHUYỂN VỀ OFF
                    if (SOUND_ON[0]) {
                        bgMusicButton.setBackground(bgSoundOff);
                        SettingController.stopBgMusic();
                    } else {
                        bgMusicButton.setBackground(bgSoundOn);
                        SettingController.continueBgMusic();
                    }
                    SOUND_ON[0] = SOUND_ON[0] == true ? false : true;
                });

        /**
         * btn stop game
         */

        /**
         * init bg
         */
        Image i10 = new Image(getClass().getResourceAsStream("/image/menuGameLoop/pause.png"),
                36, 36, false, true);

        BackgroundImage pauseOff_ =
                new BackgroundImage(i10, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        pauseOff = new Background(pauseOff_);

        /**
         * init bg
         */
        Image i20 = new Image(getClass().getResourceAsStream("/image/menuGameLoop/pause2.png"),
                36, 36, false, true);

        BackgroundImage pauseOn_ =
                new BackgroundImage(i20, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        pauseOn = new Background(pauseOn_);

        /**
         * init bg
         */
        Image i30 = new Image(getClass().getResourceAsStream("/image/menuGameLoop/pause_hover.png"),
                36, 36, false, true);

        BackgroundImage pauseOffHover_ =
                new BackgroundImage(i30, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        pauseOffHover = new Background(pauseOffHover_);

        /**
         * init bg
         */
        Image i22 = new Image(getClass().getResourceAsStream("/image/menuGameLoop/pause2_hover.png"),
                36, 36, false, true);

        BackgroundImage pauseOnHover_ =
                new BackgroundImage(i22, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        pauseOnHover = new Background(pauseOnHover_);

        pauseButton.setPrefSize(36, 36);

//        pauseButton.setTranslateX(Sprite.DEFAULT_SIZE * 24 + 5);
        pauseButton.setTranslateY(HEIGHT - 52);
//        pauseButton.setTranslateY(HEIGHT/2);/
        pauseButton.setTranslateX(Sprite.DEFAULT_SIZE * 23.5);

        pauseButton.setBackground(pauseOn);
        /**
         * button add event
         */
        pauseButton.addEventFilter(MouseEvent.MOUSE_ENTERED,
                e -> {
                    if (GAME_RUNNING[0])
                        pauseButton.setBackground(pauseOnHover);
                    else
                        pauseButton.setBackground(pauseOffHover);
                });

        pauseButton.addEventFilter(MouseEvent.MOUSE_EXITED,
                e -> {
                    if (GAME_RUNNING[0])
                        pauseButton.setBackground(pauseOn);
                    else
                        pauseButton.setBackground(pauseOff);
                });

        pauseButton.addEventFilter(MouseEvent.MOUSE_CLICKED,
                e -> {
                    // KHI SOUND ON, CHUYỂN VỀ OFF
                    if (GAME_RUNNING[0]) {
                        pauseButton.setBackground(pauseOff);
                        timer.stop();
                    } else {
                        pauseButton.setBackground(pauseOn);
                        timer.start();
                    }
                    GAME_RUNNING[0] = GAME_RUNNING[0] == true ? false : true;
                });

        /**
         * init animation left
         */
        translate1.setFromX(0);
        translate1.setFromY(0);
        translate1.setNode(charFace);
        translate1.setDuration(Duration.millis(4000));
        translate1.setCycleCount(1);
        translate1.setToX(6 * Sprite.DEFAULT_SIZE);
        translate1.setToY(-9 * Sprite.DEFAULT_SIZE);

        translate5.setFromX(0);
        translate5.setFromY(0);
        translate5.setNode(backdrop_left);
        translate5.setDuration(Duration.millis(4000));
        translate5.setCycleCount(1);
        translate5.setToX(6 * Sprite.DEFAULT_SIZE);
        translate5.setToY(-9 * Sprite.DEFAULT_SIZE);

        translate3.setFromX(0);
        translate3.setFromY(0);
        translate3.setNode(infoText);
        translate3.setDuration(Duration.millis(4000));
        translate3.setCycleCount(1);
        translate3.setToX(6 * Sprite.DEFAULT_SIZE);
        translate3.setToY(-9 * Sprite.DEFAULT_SIZE);
        /**
         * init animation right
         */
        translate4.setFromX(0);
        translate4.setFromY(0);
        translate4.setNode(levelText);
        translate4.setDuration(Duration.millis(4000));
        translate4.setCycleCount(1);
        translate4.setToX(-4 * Sprite.DEFAULT_SIZE);
        translate4.setToY(-7 * Sprite.DEFAULT_SIZE);

        translate2.setFromX(0);
        translate2.setFromY(0);
        translate2.setNode(scoreText);
        translate2.setDuration(Duration.millis(4000));
        translate2.setCycleCount(1);
        translate2.setToX(-4 * Sprite.DEFAULT_SIZE);
        translate2.setToY(-7 * Sprite.DEFAULT_SIZE);

        translate6.setFromX(0);
        translate6.setFromY(0);
        translate6.setNode(backdrop_right);
        translate6.setDuration(Duration.millis(4000));
        translate6.setCycleCount(1);
        translate6.setToX(-4 * Sprite.DEFAULT_SIZE);
        translate6.setToY(-7 * Sprite.DEFAULT_SIZE);

        fade1.setNode(afterDeathImage);
        fade1.setDuration(Duration.millis(4000));
        fade1.setCycleCount(1);
        fade1.setInterpolator(Interpolator.LINEAR);
        fade1.setFromValue(0);
        fade1.setToValue(1);

        fade2.setNode(bgMusicButton);
        fade2.setDuration(Duration.millis(4000));
        fade2.setCycleCount(1);
        fade2.setInterpolator(Interpolator.LINEAR);
        fade2.setFromValue(1);
        fade2.setToValue(0);

        fade3.setNode(pauseButton);
        fade3.setDuration(Duration.millis(4000));
        fade3.setCycleCount(1);
        fade3.setInterpolator(Interpolator.LINEAR);
        fade3.setFromValue(1);
        fade3.setToValue(0);

    }


    public void upLevelGame() throws SQLException, InterruptedException {
        level = level + 1;
        score += level * 10000;
        resetGamePlay();
        Map.MapLoader(level);
    }


    public static void main(String[] args) {
        Application.launch(BombermanGame.class); // chạy giao diện đồ họa
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initGamePlay();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("initialize");
    }
}