package bomberman.Controller.User;

import bomberman.Controller.Common.CommonController;
import bomberman.Data.Song;
import bomberman.Model.MusicModel;
import bomberman.Model.User_MusicModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import java.io.File;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class SettingController extends CommonController {

    public static final int SFXMODE_BOOM_EXPLOSION = 0;
    public static final int SFXMODE_BOOM_WAITING = 1;
    public static final int SFXMODE_ITEM_COLLECTION = 2;

    public static final int SFXMODE_BRICK_FALLING = 3;
    public static final int SFXMODE_GAME_START = 4;

    public static final int SFXMODE_CHARACTER_DEATH = 5;

    public static final int SFXMODE_ENEMY_DEATH_1 = 6;
    public static final int SFXMODE_ENEMY_DEATH_2 = 7;
    public static final int SFXMODE_ENEMY_DEATH_3 = 8;
    public static final int SFXMODE_TOTAL = 9;

    @FXML
    private ImageView bgMusicImage;
    @FXML
    private ImageView muteBgMusicImage;
    @FXML
    private ImageView SFXImage;
    @FXML
    private ImageView muteSFXImage;
    @FXML
    private Slider SFXSlider;
    @FXML
    private Slider bgMusicSlider;
    @FXML
    private Label songLabel;
    @FXML
    private ComboBox<String> songComboBox;
    ObservableList<Song> songList = FXCollections.observableArrayList();;
    ObservableList<String> songNameList = FXCollections.observableArrayList();

    private static String curSongName;
    private static String curSongLink;

    private static int curSongID;

    static boolean runningBgMusic = false;
    static boolean runningSFXMusic = false;

    private static double bgMusicVolume;
    private static double sfxVolume;

    public static Media bgMusicMedia;
    private static Media SFXMedia;
    public static MediaPlayer bgMusicMediaPlayer;
    public static MediaPlayer SFXMediaPlayer;
    private static Media [] SFXMedia_ = new Media[SFXMODE_TOTAL];
    public static MediaPlayer [] SFXMediaPlayer_ = new MediaPlayer[SFXMODE_TOTAL];



    public void backButtonOnAction() throws IOException, SQLException {
        User_MusicModel.updateUser_Music(
                getUserID(), curSongID, bgMusicMediaPlayer.getVolume(), sfxVolume);

        super.backButtonOnAction("/scene/User/home.fxml");
    }

    public void applyButtonOnAction(){
        bgMusicMediaPlayer.stop();

        songList.forEach((song)->{
            if(song.getSongName().equals(songComboBox.getValue())) {
                songLabel.setText(song.getSongName());
                playBgMusic(song.getSongLink());
                curSongName = song.getSongName();
                curSongLink = song.getSongLink();
                curSongID = song.getSongID();
            }
        });
    }

    public static void initUserMusic() throws SQLException {
        initBgMusic();
        bgMusicMediaPlayer.stop();

        initSFX();
        SFXMediaPlayer.stop();

        ResultSet queryUser_musicRes = User_MusicModel.queryUser_Music(getUserID());

        bgMusicVolume = queryUser_musicRes.getDouble("bgMusicVolume");

        sfxVolume = queryUser_musicRes.getDouble("sfxVolume");

        playBgMusic(queryUser_musicRes.getString("songLink"));

        curSongID = queryUser_musicRes.getInt("songID");
        curSongName = queryUser_musicRes.getString("songName");
        curSongLink = queryUser_musicRes.getString("songLink");

        queryUser_musicRes.close();
    }

    public static void initMediaPlayer () {
        SFXMedia_[SFXMODE_BOOM_EXPLOSION] = new Media(new File("src/main/resources/music/SFXMusic/boom-explosion.wav").toURI().toString());
        SFXMedia_[SFXMODE_BOOM_WAITING] = new Media(new File("src/main/resources/music/SFXMusic/timer-clock-05s.mp3").toURI().toString());
        SFXMedia_[SFXMODE_ITEM_COLLECTION] = new Media(new File("src/main/resources/music/SFXMusic/collectcoin-6075.mp3").toURI().toString());
        SFXMedia_[SFXMODE_BRICK_FALLING] = new Media(new File("src/main/resources/music/SFXMusic/falling-brick.wav").toURI().toString());
        SFXMedia_[SFXMODE_GAME_START] = new Media(new File("src/main/resources/music/SFXMusic/game-start-6104.mp3").toURI().toString());
        SFXMedia_[SFXMODE_CHARACTER_DEATH] = new Media(new File("src/main/resources/music/SFXMusic/videogame-death-sound-43894.mp3").toURI().toString());
        SFXMedia_[SFXMODE_ENEMY_DEATH_1] = new Media(new File("src/main/resources/music/SFXMusic/game-start-6104.mp3").toURI().toString());

        SFXMediaPlayer_[SFXMODE_BOOM_EXPLOSION] = new MediaPlayer(SFXMedia_[SFXMODE_BOOM_EXPLOSION]);
        SFXMediaPlayer_[SFXMODE_BOOM_WAITING] = new MediaPlayer(SFXMedia_[SFXMODE_BOOM_WAITING]);
        SFXMediaPlayer_[SFXMODE_ITEM_COLLECTION] = new MediaPlayer(SFXMedia_[SFXMODE_ITEM_COLLECTION]);
        SFXMediaPlayer_[SFXMODE_BRICK_FALLING] = new MediaPlayer(SFXMedia_[SFXMODE_BRICK_FALLING]);
        SFXMediaPlayer_[SFXMODE_GAME_START] = new MediaPlayer(SFXMedia_[SFXMODE_GAME_START]);
        SFXMediaPlayer_[SFXMODE_CHARACTER_DEATH] = new MediaPlayer(SFXMedia_[SFXMODE_CHARACTER_DEATH]);
        SFXMediaPlayer_[SFXMODE_ENEMY_DEATH_1] = new MediaPlayer(SFXMedia_[SFXMODE_ENEMY_DEATH_1]);

        SFXMediaPlayer_[SFXMODE_BOOM_EXPLOSION].setVolume(sfxVolume);
        SFXMediaPlayer_[SFXMODE_BOOM_WAITING].setVolume(sfxVolume);
        SFXMediaPlayer_[SFXMODE_ITEM_COLLECTION].setVolume(sfxVolume);
        SFXMediaPlayer_[SFXMODE_BRICK_FALLING].setVolume(sfxVolume);
        SFXMediaPlayer_[SFXMODE_GAME_START].setVolume(sfxVolume);
        SFXMediaPlayer_[SFXMODE_CHARACTER_DEATH].setVolume(sfxVolume);
        SFXMediaPlayer_[SFXMODE_ENEMY_DEATH_1].setVolume(sfxVolume);

        SFXMedia = new Media(new File("src/main/resources/music/SFXMusic/game-start-6104.mp3").toURI().toString());
        SFXMediaPlayer = new MediaPlayer(SFXMedia);
    }
    /**
     * bgmusic
     */
    public static void initBgMusic() {
        playBgMusic("src/main/resources/music/bgMusic/let-the-games-begin-21858.mp3");
    }
    public static void playBgMusic(String path) {
        bgMusicMedia = null;
        bgMusicMediaPlayer = null;
        System.gc();

        bgMusicMedia = new Media(new File(path).toURI().toString());
        bgMusicMediaPlayer = new MediaPlayer(bgMusicMedia);
        bgMusicMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgMusicMediaPlayer.play();
        bgMusicMediaPlayer.setVolume(bgMusicVolume);
        runningBgMusic = true;

    }
    public static void stopBgMusic() {
        bgMusicMediaPlayer.stop();
    }
    public static void continueBgMusic() {
        bgMusicMediaPlayer.play();
    }
    /**
     * SFX
     */
    public static void initSFX() {
        initMediaPlayer();
        playSFX(SFXMODE_GAME_START);
    }
    public static void playSFX(int SFX_mode) {
        SFXMedia = null;
        SFXMediaPlayer_[SFX_mode].seek(Duration.ZERO);
        SFXMediaPlayer_[SFX_mode].setCycleCount(1);
        SFXMediaPlayer_[SFX_mode].play();
        runningSFXMusic = true;
    }
    public static void stopSFX(){
        SFXMediaPlayer_[SFXMODE_BOOM_EXPLOSION].stop();
        SFXMediaPlayer_[SFXMODE_BOOM_WAITING].stop();
        SFXMediaPlayer_[SFXMODE_ITEM_COLLECTION].stop();
        SFXMediaPlayer_[SFXMODE_BRICK_FALLING].stop();
        SFXMediaPlayer_[SFXMODE_GAME_START].stop();
        SFXMediaPlayer_[SFXMODE_CHARACTER_DEATH].stop();
        SFXMediaPlayer_[SFXMODE_ENEMY_DEATH_1].stop();
    }
    public void initSongComboBox() throws SQLException {
        ResultSet queryMusicRes = MusicModel.queryMusic();
        while (queryMusicRes.next()) {
            Song song = new Song(
                    queryMusicRes.getInt("songID"),
                    queryMusicRes.getString("songName"),
                    queryMusicRes.getString("songLink")
                    );
            songList.add(song);
            songNameList.add(queryMusicRes.getString("songName"));
        }
        songComboBox.setItems(songNameList);
        queryMusicRes.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bgMusicSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                bgMusicMediaPlayer.setVolume(bgMusicSlider.getValue() * 0.01);
                if(bgMusicSlider.getValue() == 0) {
                    muteBgMusicImage.setVisible(TRUE);
                    bgMusicImage.setVisible(FALSE);
                } else {
                    muteBgMusicImage.setVisible(FALSE);
                    bgMusicImage.setVisible(TRUE);
                }
            }
        });
        SFXSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sfxVolume = SFXSlider.getValue() * 0.01;
                if(SFXSlider.getValue() == 0) {
                    muteSFXImage.setVisible(TRUE);
                    SFXImage.setVisible(FALSE);
                } else {
                    muteSFXImage.setVisible(FALSE);
                    SFXImage.setVisible(TRUE);
                }
            }
        });
        try {
            initSongComboBox();
            songLabel.setText(curSongName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
