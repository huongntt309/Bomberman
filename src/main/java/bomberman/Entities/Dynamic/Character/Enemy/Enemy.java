package bomberman.Entities.Dynamic.Character.Enemy;

import bomberman.Component.Sprite;
import bomberman.Entities.Dynamic.Character.Character;
import bomberman.Map.Map;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter

public abstract class Enemy extends Character {
    protected ArrayList<Integer> pathToMoveAround = new ArrayList<>();
    public int direction;
    public int lives;
    int indexOfMove = 0;
    public Enemy(int xUnit, int yUnit, Image img, int lives) {
        super(xUnit, yUnit, img);
        speed = 4;
        lives = 1;
    }

    @Override
    public void update() {

        if (isAlive()) {
//            gridGameForBomber[y / Sprite.DEFAULT_SIZE][x / Sprite.DEFAULT_SIZE] = 0;
            int yB = Math.round(this.y/Sprite.DEFAULT_SIZE);
            int xB = Math.round(this.x/Sprite.DEFAULT_SIZE);
            if(remainingStep > 0)
            {
                remainingStep--;
                if(direction == LEFT) {
                    goLeft();
                }
                else if(direction == RIGHT) {
                    goRight();
                }
                else if(direction == UP) {
                    goUp();
                }
                else if(direction == DOWN) {
                    goDown();
                }
                Map.gridGameForEnemy_Hard[yB][xB] = 1;
                Map.gridGameForBomber[yB][xB] = 1;
            }
            else {
                /**
                 *  đứng ở đâu thì ở đó vẫn là unblock
                 */
                Map.gridGameForEnemy_Hard[yB][xB] = 1;
                Map.gridGameForBomber[yB][xB] = 500;

                remainingStep = Sprite.DEFAULT_SIZE / speed;
                if(pathToMoveAround != null && indexOfMove < pathToMoveAround.size()) {
                    direction = pathToMoveAround.get(indexOfMove);
                    indexOfMove ++;
                    if(indexOfMove >= pathToMoveAround.size()) indexOfMove = 0;
                }


            }

        }
    }

}
