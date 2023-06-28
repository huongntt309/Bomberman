package bomberman.Entities.Dynamic.Character.Enemy.EnemyType;


import bomberman.Component.Collision;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.MedMode;
import javafx.scene.image.Image;
import bomberman.BombermanGame;
import bomberman.Component.Sprite;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.EasyMode;

public class Monkey extends MedMode {
    /**
     * xuyên tường
     */
    public Monkey(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setLayer(1); 
        setSpeed(1);
        this.lives = 1;
        alive = true;
    }

    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.Monkey_left_1, Sprite.Monkey_left_2, Sprite.Monkey_left_3, left++, 36).getFxImage();
    }

    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.Monkey_right_1, Sprite.Monkey_right_2, Sprite.Monkey_right_3, right++, 36).getFxImage();
    }

    public void goUp() {
        super.goUp();
        img = Sprite.movingSprite(Sprite.Monkey_up_1, Sprite.Monkey_up_2, Sprite.Monkey_up_3, up++, 36).getFxImage();
    }

    public void goDown() {
        super.goDown();
        img = Sprite.movingSprite(Sprite.Monkey_down_1, Sprite.Monkey_down_2, Sprite.Monkey_down_3, down++, 36).getFxImage();
    }

}
