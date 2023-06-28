package bomberman.Entities.Dynamic.Character.Enemy.EnemyType;

import bomberman.Component.Collision;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.HardMode;
import javafx.scene.image.Image;
import bomberman.BombermanGame;
import bomberman.Component.Sprite;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.EasyMode;

public class Scorpion extends HardMode {
        
    public Scorpion(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setSpeed(1);
        alive = true;
        this.lives = 1;
    }

    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.Scorpion_left_1, Sprite.Scorpion_left_2, Sprite.Scorpion_left_3, left++, 36).getFxImage();
    }

    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.Scorpion_right_1, Sprite.Scorpion_right_2, Sprite.Scorpion_right_3, right++, 36).getFxImage();
    }

    public void goUp() {
        super.goUp();
        img = Sprite.movingSprite(Sprite.Scorpion_up_1, Sprite.Scorpion_up_2, Sprite.Scorpion_up_3, up++, 36).getFxImage();
    }

    public void goDown() {
        super.goDown();
        img = Sprite.movingSprite(Sprite.Scorpion_down_1, Sprite.Scorpion_down_2, Sprite.Scorpion_down_3, down++, 36).getFxImage();
    }

}
