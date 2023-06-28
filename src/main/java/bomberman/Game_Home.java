package bomberman;

import bomberman.Controller.Common.CommonController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class Game_Home extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader(Game_Home.class.getResource("/scene/User/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600 );  
        /**
         * khi test game, chỉ chạy từ scene Home ( để init music )
         *
         * khi bắt đầu từ login thì cmt out
         */
        CommonController.setUserID(43);
        stage.getIcons().add(new Image("file:src/main/resources/image/menu/common/bomb.png"));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Bomberman");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
