package bomberman.Component;

import bomberman.BombermanGame;
import bomberman.Controller.User.SettingController;
import bomberman.Entities.Dynamic.Character.Bomber;
import bomberman.Entities.Dynamic.Character.BomberAI;
import bomberman.Entities.Dynamic.Character.Character;
import bomberman.Entities.Dynamic.Character.Enemy.Enemy;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyType.*;
import bomberman.Entities.Static.*;
import bomberman.Map.Map;
import javafx.geometry.Rectangle2D;

import java.util.List;

public class Collision extends BombermanGame {
    /**
     *  A collide B : A tác động lên B
     */
    private static final int DEFAULT_SIZE = 32;
    public Collision(int mode) {
        super(mode);
    }

    public static Rectangle2D rectangle(int positionX, int positionY, int w, int h) {
        return new Rectangle2D(positionX, positionY, w, h);
    }

    public static boolean checkColide(int x1, int y1, int x2, int y2) {
        return rectangle(x1,y1,DEFAULT_SIZE,DEFAULT_SIZE).intersects(rectangle(x2,y2,DEFAULT_SIZE ,DEFAULT_SIZE));
    }


    public static boolean enemyCollidePlayer(Bomber bomberman, List<Enemy> entityList) {
        for(Enemy enemy : entityList) {
            if (checkColide(bomberman.getX(), bomberman.getY(), enemy.getX(), enemy.getY())) {
                return true;
            }
        }
        return false;
    }

    public static boolean characterCollideStatic(Character character, int positionX, int positionY, List<StaticEntity> staticEntities) {
        for(StaticEntity staticE : staticEntities) {
            //NẾU VA CHẠM
            if (!(staticE instanceof Grass))
            {
                if (checkColide(positionX, positionY, staticE.getX(), staticE.getY()))
                {
                    if ((character instanceof Robot || character instanceof Monkey) && staticE instanceof Brick) {
                        return false;
                    }
                    if(character instanceof BomberAI && staticE instanceof Brick) {
                        BomberAI.collideBrick = true;
                    }
                    if(staticE instanceof Item && character instanceof Bomber) {
                        /**
                         * CHỈ CHO PLAYER LẤY ITEM
                         * Riêng với DOOR chỉ lấy được khi có key
                         * */

                        Bomber bomber = (Bomber) character;
                        Item item = (Item) staticE;
                        if((item).getType().equals("Door") && bomber.isHasKey() == false){
                            return true;
                        }else {
                            SettingController.playSFX(SettingController.SFXMODE_ITEM_COLLECTION);
                            bomber.appyItem(item);
                            score += 500;
                            staticEntities.remove(item);
                        }
                        return false;
                    } else if (staticE instanceof Item) {
                        /**
                         * collision is not account for items
                         */
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean AIBomberWillCollideEnemy(Bomber bomberman, List<Enemy> entityList) {
        for(Enemy obj : entityList) {
            if (checkColide(bomberman.getX(), bomberman.getY(), obj.getX(), obj.getY())) {
                return true;
            }
            else if (rectangle(bomberman.getX() - DEFAULT_SIZE * bomberman.getRadius(),bomberman.getY(),DEFAULT_SIZE,DEFAULT_SIZE).intersects(rectangle(obj.getX(), obj.getY(), DEFAULT_SIZE ,DEFAULT_SIZE))) {
                return true;
            }
            else if (rectangle(bomberman.getX() + DEFAULT_SIZE * bomberman.getRadius() ,bomberman.getY(),DEFAULT_SIZE,DEFAULT_SIZE).intersects(rectangle(obj.getX(), obj.getY(), DEFAULT_SIZE ,DEFAULT_SIZE))) {
                return true;
            }
            else if (rectangle(bomberman.getX(),bomberman.getY() + DEFAULT_SIZE * bomberman.getRadius(),DEFAULT_SIZE,DEFAULT_SIZE).intersects(rectangle(obj.getX(), obj.getY(), DEFAULT_SIZE ,DEFAULT_SIZE))) {
                return true;
            }
            else if (rectangle(bomberman.getX(),bomberman.getY() - DEFAULT_SIZE * bomberman.getRadius() ,DEFAULT_SIZE,DEFAULT_SIZE).intersects(rectangle(obj.getX(), obj.getY(), DEFAULT_SIZE ,DEFAULT_SIZE))) {
                return true;
            }

        }
        return false;
    }
    public static boolean flameCollideStatic(int positionX, int positionY,  List<StaticEntity> staticEntities) {
        for(StaticEntity staticE : staticEntities) {
            if (!(staticE instanceof Grass))
            {
                if (staticE.getX() == positionX && (staticE.getY() == positionY)){
                    if(staticE instanceof Wall || staticE instanceof Item)
                            return true;
                    else if(staticE instanceof Brick)
                    {
                        SettingController.playSFX(SettingController.SFXMODE_BRICK_FALLING);
                        Map.gridGameForEnemy_Med[staticE.getY()/Sprite.DEFAULT_SIZE][staticE.getX()/Sprite.DEFAULT_SIZE] = 1;
                        Map.gridGameForEnemy_Hard[staticE.getY()/Sprite.DEFAULT_SIZE][staticE.getX()/Sprite.DEFAULT_SIZE] = 1;
                        Map.gridGameForBomber[staticE.getY()/Sprite.DEFAULT_SIZE][staticE.getX()/Sprite.DEFAULT_SIZE] = 1;
                    }
                    score += 300;
                    staticEntities.remove(staticE);
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean flameCollideEnemy(int positionX, int positionY,  List<Enemy> enemiesList) {
        for(Enemy enemy : enemiesList) {
            if (checkColide(positionX, positionY, enemy.getX(), enemy.getY()))
            {
                enemy.lives = enemy.getLives() - 1;
                if (enemy.getLives() == 0) {
                    score += 1000;
                    enemiesList.remove(enemy);
                }
                return true;
            }
        }
        return false;
    }
    public static boolean flameCollideBomber(int positionX, int positionY, Bomber bomberman) {
        return checkColide(positionX, positionY, bomberman.getX(), bomberman.getY());
    }

}
