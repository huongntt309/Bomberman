package bomberman.Entities.Dynamic.Character.Enemy.EnemyType;


import bomberman.Component.Collision;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.HardMode;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.MedMode;
import javafx.scene.image.Image;
import bomberman.BombermanGame;
import bomberman.Component.Sprite;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.EasyMode;

public class Ninja extends HardMode {
    /**
     * tàng hình
     */
    public Ninja(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setLayer(1); 
        setSpeed(2);
        alive = true;
        this.lives = 1;
    }

    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.Ninja_left_1, Sprite.Ninja_left_2, Sprite.transparent, left++, 30).getFxImage();
    }

    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.Ninja_right_1, Sprite.Ninja_right_2, Sprite.transparent, right++, 30).getFxImage();
    }

    public void goUp() {
        super.goUp();
        img = Sprite.movingSprite(Sprite.Ninja_up_1, Sprite.Ninja_up_2, Sprite.transparent, up++, 30).getFxImage();
    }

    public void goDown() {
        super.goDown();
        img = Sprite.movingSprite(Sprite.Ninja_down_1, Sprite.Ninja_down_2, Sprite.transparent, down++, 30).getFxImage();
    }

}
