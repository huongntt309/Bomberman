package bomberman.Data;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Score {
    @FXML
    private ImageView userAvatar;
    private int userID;
    private int score;
    private int level;
    private String statusGame;
    private int charID;
    private String charName;
    private String createdTime;
    private String fullName;
    @FXML
    private ImageView charFaceImage;

}
