package bomberman.Entities.Dynamic.Character.Enemy.EnemyBot;

import bomberman.BombermanGame;
import bomberman.Component.AStarAlgo;
import bomberman.Component.Sprite;
import bomberman.Entities.Dynamic.Character.Enemy.Enemy;
import bomberman.Map.Map;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class MedMode extends Enemy {
    ArrayList<Integer> pathToBomber;
    int prevXUnit, prevYUnit;
    private int indexOfMove = 0;
    private static AStarAlgo aStarAlgo = new AStarAlgo();

    public MedMode(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img, 1);
        setLayer(1); // chỉ số va chạm của Mummy
        setSpeed(4); // chuẩn default size để đi đúng path.

        alive = true;
        direction = NODIRECTION;
        prevXUnit = prevYUnit = 1;
    }
    @Override
    public void update() {
        if (isAlive()) {
            /**
             * chưa set lại chuẩn được sau khi enemy rời vị trí
             */
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
                Map.gridGameForBomber[yB][xB] = 300; // = 1.5 easy mode
                Map.gridGameForEnemy_Hard[yB][xB] = 1;
            }
            else {
                Map.gridGameForEnemy_Hard[prevYUnit][prevXUnit] = 1;
                Map.gridGameForBomber[prevYUnit][prevXUnit] = 1;
                prevXUnit = xB;
                prevYUnit = yB;
                this.findPath(Map.gridGameForEnemy_Med,
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
        /**
         * không tìm đc bomber thì đứng yên
         */
//        if (pathToBomber.isEmpty()) {
//            // đi về cuối map ->
//            // chế độ này thì cực khó cho bomber kill hardmode =))
//            aStarAlgo.aStarSearch(gridGame, startX, startY, gridGame.length - 2, gridGame[0].length - 2);
//            pathToBomber = aStarAlgo.getPathToDes();
//        }
//        if (pathToBomber.isEmpty()) {
//            // di ve start map
//            aStarAlgo.aStarSearch(gridGame, startX, startY, 1, 1);
//            pathToBomber = aStarAlgo.getPathToDes();
//        }
    }
}
