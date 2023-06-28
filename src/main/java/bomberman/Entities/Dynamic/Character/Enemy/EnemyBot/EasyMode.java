package bomberman.Entities.Dynamic.Character.Enemy.EnemyBot;

import bomberman.Component.Sprite;
import bomberman.Entities.Dynamic.Character.Enemy.Enemy;
import javafx.scene.image.Image;

public abstract class EasyMode extends Enemy {
    public EasyMode(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img, 1);
        setSpeed(2); // chuẩn default size để đi đúng path.
        alive = true;
        pathToMoveAround.add(LEFT);
        pathToMoveAround.add(LEFT);
        pathToMoveAround.add(LEFT);
        pathToMoveAround.add(LEFT);
        pathToMoveAround.add(LEFT);
        pathToMoveAround.add(LEFT);
        pathToMoveAround.add(RIGHT);
        pathToMoveAround.add(RIGHT);
        pathToMoveAround.add(RIGHT);
        pathToMoveAround.add(RIGHT);
        pathToMoveAround.add(RIGHT);
        pathToMoveAround.add(RIGHT);
        remainingStep = Sprite.DEFAULT_SIZE / speed;
    }

    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.Mummy_left_1, Sprite.Mummy_left_2, Sprite.Mummy_left_3, left++, 18).getFxImage();
    }

    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.Mummy_right_1, Sprite.Mummy_right_2, Sprite.Mummy_right_3, right++, 18).getFxImage();
    }

    public void goUp() {
        super.goUp();
        img = Sprite.movingSprite(Sprite.Mummy_up_1, Sprite.Mummy_up_2, Sprite.Mummy_up_3, up++, 18).getFxImage();
    }

    public void goDown() {
        super.goDown();
        img = Sprite.movingSprite(Sprite.Mummy_down_1, Sprite.Mummy_down_2, Sprite.Mummy_down_3, down++, 18).getFxImage();
    }

}
