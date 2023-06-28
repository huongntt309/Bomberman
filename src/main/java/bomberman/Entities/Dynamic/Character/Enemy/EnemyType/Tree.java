package bomberman.Entities.Dynamic.Character.Enemy.EnemyType;

import bomberman.Component.Collision;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.HardMode;
import javafx.scene.image.Image;
import bomberman.BombermanGame;
import bomberman.Component.Sprite;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.EasyMode;

public class Tree extends HardMode {
    
    public Tree(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setSpeed(1);
        this.lives = 1;
        alive = true;
    }

    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.Tree_left_1, Sprite.Tree_left_2, Sprite.Tree_left_3, left++, 40).getFxImage();
    }

    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.Tree_right_1, Sprite.Tree_right_2, Sprite.Tree_right_3, right++, 40).getFxImage();
    }

    public void goUp() {
        super.goUp();
        img = Sprite.movingSprite(Sprite.Tree_up_1, Sprite.Tree_up_2, Sprite.Tree_up_3, up++, 40).getFxImage();
    }

    public void goDown() {
        super.goDown();
        img = Sprite.movingSprite(Sprite.Tree_down_1, Sprite.Tree_down_2, Sprite.Tree_down_3, down++, 40).getFxImage();
    }

}
