package bomberman.Entities.Dynamic.Character.Enemy.EnemyBot;

import bomberman.BombermanGame;
import bomberman.Component.AStarAlgo;
import bomberman.Component.Sprite;
import bomberman.Entities.Dynamic.Character.Enemy.Enemy;
import bomberman.Map.Map;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class HardMode extends Enemy {
    ArrayList<Integer> pathToBomber;
    private int indexOfMove = 0;
    int prevXUnit, prevYUnit;
    private static AStarAlgo aStarAlgo = new AStarAlgo();

    public HardMode(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img, 1);
        setLayer(1); // chỉ số va chạm của Mummy
        setSpeed(1); // chuẩn default size để đi đúng path.

        alive = true;
        direction = NODIRECTION;
    }
    @Override
    public void update() {

        if (isAlive()) {
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
                Map.gridGameForBomber[yB][xB] = 400; // 2 easy mode
                Map.gridGameForEnemy_Hard[yB][xB] = 1;
            }
            else {
                Map.gridGameForEnemy_Hard[prevYUnit][prevXUnit] = 1;
                Map.gridGameForBomber[prevYUnit][prevXUnit] = 1;
                prevXUnit = xB;
                prevYUnit = yB;
                this.findPath(Map.gridGameForEnemy_Hard,
                        this.getY() / Sprite.DEFAULT_SIZE, this.getX() / Sprite.DEFAULT_SIZE,
                        BombermanGame.bomberman.getY() / Sprite.DEFAULT_SIZE, BombermanGame.bomberman.getX() / Sprite.DEFAULT_SIZE);

                remainingStep = Sprite.DEFAULT_SIZE / speed;
                if(!pathToBomber.isEmpty()) {
                    direction = pathToBomber.get(0);
                }else if (pathToBomber.isEmpty()) {
                    direction = NODIRECTION;
                    remainingStep = 0;
                }
            }

        } else if (animated < 30) {
            animated++;
        } else
            BombermanGame.enemiesList.remove(this);
    }

    /**
     * tự tìm đường đến vị trí bomber trên map bằng Astar
     */
    public void findPath(int[][] gridGame, int startX, int startY, int desX, int desY) {
        aStarAlgo.aStarSearch(gridGame, startX, startY, desX, desY);
        pathToBomber = aStarAlgo.getPathToDes();
        if (pathToBomber.isEmpty()) {
            // đi về cuối map ->
            // chế độ này thì cực khó cho bomber kill hardmode =))
            aStarAlgo.aStarSearch(gridGame, startX, startY, gridGame.length - 2, gridGame[0].length - 2);
            pathToBomber = aStarAlgo.getPathToDes();
        }
        if (pathToBomber.isEmpty()) {
            // di ve start map
            aStarAlgo.aStarSearch(gridGame, startX, startY, 1, 1);
            pathToBomber = aStarAlgo.getPathToDes();
        }
    }
}
