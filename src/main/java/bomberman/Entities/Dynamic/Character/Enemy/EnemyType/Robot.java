package bomberman.Entities.Dynamic.Character.Enemy.EnemyType;

import bomberman.Component.Collision;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.MedMode;
import javafx.scene.image.Image;
import bomberman.BombermanGame;
import bomberman.Component.Sprite;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.EasyMode;




public class Robot extends MedMode {
    /**
     * xuyên tường
     */
    public Robot(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setLayer(1); 
        setSpeed(1);
        this.lives = 1;
        alive = true;
    }

    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.Robot_left_1, Sprite.Robot_left_2, Sprite.transparent, left++, 36).getFxImage();
    }

    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.Robot_right_1, Sprite.Robot_right_2, Sprite.transparent, right++, 36).getFxImage();
    }

    public void goUp() {
        super.goUp();
        img = Sprite.movingSprite(Sprite.Robot_up_1, Sprite.Robot_up_2, Sprite.transparent, up++, 36).getFxImage();
    }

    public void goDown() {
        super.goDown();
        img = Sprite.movingSprite(Sprite.Robot_down_1, Sprite.Robot_down_2, Sprite.transparent, down++, 36).getFxImage();
    }

}
