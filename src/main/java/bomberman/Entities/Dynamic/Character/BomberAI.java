package bomberman.Entities.Dynamic.Character;

import bomberman.BombermanGame;
import bomberman.Component.AStarAlgo;
import bomberman.Component.Collision;
import bomberman.Component.Sprite;
import bomberman.Map.Map;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static bomberman.BombermanGame.*;
import static bomberman.BombermanGame.bomberman;

@Getter
@Setter
public class BomberAI extends Bomber{
    public AStarAlgo aStarAlgo = new AStarAlgo();
    ArrayList<Integer> pathToGate;
    public static boolean collideBrick;
    public static boolean willCollideEnemy;

    private boolean stopPoint;
    int realSpeed;
    public BomberAI(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        System.out.println("new constructor");
        speed = 4;
        realSpeed = speed;
        pathToGate = null;
        direction = NODIRECTION;
        remainingStep = 0;
        collideBrick = false;
        willCollideEnemy = false;
        stopPoint = false;
    }
    public void findPath(int[][] gridGame, int startX, int startY, int desX, int desY) {
        aStarAlgo.aStarSearch(gridGame, startX, startY, desX, desY);
        pathToGate = aStarAlgo.getPathToDes();
    }

    @Override
    public void update() {

        if (collideBrick == true) {
            System.out.println("collideBrick == true");
            speed = realSpeed / 2;
            if(bombs.isEmpty())
                placeBomb();
            inBomb = true;
            collideBrick = false;
        }
        if (Collision.AIBomberWillCollideEnemy(bomberman, enemiesList)) {
            willCollideEnemy = true;
        }


        if(remainingStep > 0 && direction != NODIRECTION)
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
        }
        else {
            Map.printGrid();
            if (willCollideEnemy == true) {
                System.out.println("willCollideEnemy == true");
                if(bombs.isEmpty()) placeBomb();
                inBomb = true;
                willCollideEnemy = false;
            }
            if(bombs.isEmpty()){
                speed = realSpeed;
            }
            if (!bombs.isEmpty()) {
                setSpeed(speed);

                if (stopPoint == false)
                    this.findPath(Map.gridGameForBomber,
                            this.getY() / Sprite.DEFAULT_SIZE, this.getX() / Sprite.DEFAULT_SIZE,
                            1, 1);
                else
                    this.findPath(Map.gridGameForBomber,
                            this.getY() / Sprite.DEFAULT_SIZE, this.getX() / Sprite.DEFAULT_SIZE,
                            15, 1);
                if(pathToGate.isEmpty())
                    stopPoint = !stopPoint;

            }
            else if(isHasKey())
                this.findPath(Map.gridGameForBomber,
                        this.getY() / Sprite.DEFAULT_SIZE, this.getX() / Sprite.DEFAULT_SIZE,
                        gatePosX, gatePosY);
            else
                this.findPath(Map.gridGameForBomber,
                        this.getY() / Sprite.DEFAULT_SIZE, this.getX() / Sprite.DEFAULT_SIZE,
                        keyPosX, keyPosY);
            remainingStep = Sprite.DEFAULT_SIZE / speed;
            if(!pathToGate.isEmpty()) {
                direction = pathToGate.get(0);
            }else if (pathToGate.isEmpty()) {
                direction = NODIRECTION;
                remainingStep = 0;
            }
        }


        if (Collision.enemyCollidePlayer(bomberman, enemiesList)) {
            bomberman.setAlive(false);
        }


        if(inBomb == true && !bombs.isEmpty()) {

            if (Collision.checkColide(BombermanGame.bomberman.getX(), BombermanGame.bomberman.getY(), bombs.get(bombs.size() - 1).getX(), bombs.get(bombs.size() - 1).getY())) {
            }
            else inBomb = false;
            if (Collision.enemyCollidePlayer(bomberman, enemiesList)) {
                bomberman.setAlive(true);
            }
        }

        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();

            if(bombs.get(i).isAlive() == false){
                bombs.remove(i);
                bombCount ++;
            }
        }

    }

}
