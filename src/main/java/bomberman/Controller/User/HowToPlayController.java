package bomberman.Controller.User;

import bomberman.Controller.Common.CommonController;
import java.io.*;


public class HowToPlayController extends CommonController {
    public void backButtonOnAction() throws IOException {
        super.backButtonOnAction("/scene/User/home.fxml");
    }
}
