package bomberman.Entities.Dynamic.Character.Enemy.EnemyType;

import bomberman.Component.Collision;
import javafx.scene.image.Image;
import bomberman.BombermanGame;
import bomberman.Component.Sprite;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.EasyMode;

import static bomberman.BombermanGame.staticEntities;

public class Mummy extends EasyMode {

    /**
     * MUMMY has 2 lives
     */
    public Mummy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setSpeed(1);
        this.lives = 2;
        alive = true;
    }

    @Override
    public void update() {
        if(isAlive()) { 
           if (direction == LEFT) goLeft();
           if (direction == RIGHT) goRight();
           if (direction == UP) goUp();
           if (direction == DOWN) goDown();
        } else if (animated < 30){ 
            animated ++;
        }else {
            BombermanGame.enemiesList.remove(this);
        }
    }

    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.Mummy_left_1, Sprite.Mummy_left_2, Sprite.Mummy_left_3, left++, 36).getFxImage();
    }

    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.Mummy_right_1, Sprite.Mummy_right_2, Sprite.Mummy_right_3, right++, 36).getFxImage();
    }

    public void goUp() {
        super.goUp();
        img = Sprite.movingSprite(Sprite.Mummy_up_1, Sprite.Mummy_up_2, Sprite.Mummy_up_3, up++, 36).getFxImage();
    }

    public void goDown() {
        super.goDown();
        img = Sprite.movingSprite(Sprite.Mummy_down_1, Sprite.Mummy_down_2, Sprite.Mummy_down_3, down++, 36).getFxImage();
    }

}
