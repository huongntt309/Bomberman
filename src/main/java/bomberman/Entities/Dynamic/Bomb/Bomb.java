package bomberman.Entities.Dynamic.Bomb;

import bomberman.BombermanGame;
import bomberman.Component.Collision;
import bomberman.Controller.User.SettingController;
import bomberman.Entities.Entity;

import bomberman.Map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


import bomberman.Component.Sprite;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static bomberman.BombermanGame.enemiesList;
import static bomberman.BombermanGame.staticEntities;

@Getter
@Setter
public class Bomb extends Entity {

    public static final int BOMB_DURATION = 180;                    // thời gian nổ, 60 tick = 1s // -- đây là 5s = 600
    private int timeCounter = 0;                                    // biến đếm ngược
    int radius;
    boolean isExplode;
    public List<Flame> flameList = new ArrayList<>();          // flame


    public Bomb(int xUnit, int yUnit, Image img, int radius) {
        super(xUnit, yUnit, img);
        setLayer(2); // chỉ số va chạm của bomb\
        setAlive(true);
        setExplode(false);
        setRadius(radius);

    }

    @Override
    public void update() {
        if (timeCounter++ == BOMB_DURATION) {
            explode();
            isExplode = true;
        }
        if (isExplode == true) {
            for (Flame f : flameList) {
                f.update();
                if (f.isAlive == false) {
                    this.alive = false;
                }
            }
        }
        img = Sprite.movingSprite(Sprite.bomb_0, Sprite.bomb_1, Sprite.bomb_2, timeCounter, 60).getFxImage(); // load ảnh bom trước khi bom nổ
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
        if (isExplode == true) {
            for (Flame f : flameList) {
                f.render(gc);
            }
        }

    }

    public void explode() {
        int yB = this.y/Sprite.DEFAULT_SIZE;
        int xB = this.x/Sprite.DEFAULT_SIZE;
        Map.gridGameForEnemy_Hard[yB][xB] = 1;
        // -1 : vừa có bomb zoom
        Map.gridGameForEnemy_Hard[yB+1][xB] = Map.gridGameForEnemy_Hard[yB+1][xB] != -1 ? Map.gridGameForEnemy_Hard[yB+1][xB] : 1;
        Map.gridGameForEnemy_Hard[yB][xB+1] = Map.gridGameForEnemy_Hard[yB][xB+1] != -1 ? Map.gridGameForEnemy_Hard[yB][xB+1] : 1;
        Map.gridGameForEnemy_Hard[yB-1][xB] = Map.gridGameForEnemy_Hard[yB-1][xB] != -1 ? Map.gridGameForEnemy_Hard[yB-1][xB] : 1;
        Map.gridGameForEnemy_Hard[yB][xB-1] = Map.gridGameForEnemy_Hard[yB][xB-1] != -1 ? Map.gridGameForEnemy_Hard[yB][xB-1] : 1;



        Map.gridGameForEnemy_Med[this.y/Sprite.DEFAULT_SIZE][this.x/Sprite.DEFAULT_SIZE] = 1;

        Map.gridGameForBomber[this.y/Sprite.DEFAULT_SIZE][this.x/Sprite.DEFAULT_SIZE] = 1;

        SettingController.playSFX(SettingController.SFXMODE_BOOM_EXPLOSION);

        // MID
        if (Collision.flameCollideEnemy(x, y, enemiesList)) {

        }
        if (Collision.flameCollideBomber(x, y, BombermanGame.bomberman)) {
            BombermanGame.bomberman.setAlive(false);
        }
        flameList.add(new Flame(x / Sprite.DEFAULT_SIZE, y / Sprite.DEFAULT_SIZE, Sprite.flame_mid.getFxImage()));
        // RIGHT
        for (int i = 1; i <= radius; i++) {
            if (Collision.flameCollideStatic((x + i * Sprite.DEFAULT_SIZE), y, staticEntities)) {
                break;
            }
            if (Collision.flameCollideEnemy((x + i * Sprite.DEFAULT_SIZE), y, enemiesList)) {
                break;
            }
            if (Collision.flameCollideBomber((x + i * Sprite.DEFAULT_SIZE), y, BombermanGame.bomberman)) {
                BombermanGame.bomberman.setAlive(false);
                flameList.add(new Flame(x / Sprite.DEFAULT_SIZE + i, y / Sprite.DEFAULT_SIZE, Sprite.flame_right_1.getFxImage()));
                return;
            }
            flameList.add(new Flame(x / Sprite.DEFAULT_SIZE + i, y / Sprite.DEFAULT_SIZE, Sprite.flame_right_1.getFxImage()));
        }
        // LEFT
        for (int i = 1; i <= radius; i++) {
            if (Collision.flameCollideStatic((x - i * Sprite.DEFAULT_SIZE), y, staticEntities)) {
                break;
            }
            if (Collision.flameCollideEnemy((x - i * Sprite.DEFAULT_SIZE), y, enemiesList)) {
                break;
            }
            if (Collision.flameCollideBomber((x - i * Sprite.DEFAULT_SIZE), y, BombermanGame.bomberman)) {
                BombermanGame.bomberman.setAlive(false);
                flameList.add(new Flame(x / Sprite.DEFAULT_SIZE - i, y / Sprite.DEFAULT_SIZE, Sprite.flame_left_1.getFxImage()));
                return;
            }
            flameList.add(new Flame(x / Sprite.DEFAULT_SIZE - i, y / Sprite.DEFAULT_SIZE, Sprite.flame_left_1.getFxImage()));
        }
        // DOWN
        for (int i = 1; i <= radius; i++) {
            if (Collision.flameCollideStatic(x, y + Sprite.DEFAULT_SIZE * i, staticEntities)) {
                break;
            }
            if (Collision.flameCollideEnemy(x, y + Sprite.DEFAULT_SIZE * i, enemiesList)) {
                break;
            }
            if (Collision.flameCollideBomber(x, y + Sprite.DEFAULT_SIZE * i, BombermanGame.bomberman)) {
                BombermanGame.bomberman.setAlive(false);
                flameList.add(new Flame(x / Sprite.DEFAULT_SIZE, y / Sprite.DEFAULT_SIZE + i, Sprite.flame_down_1.getFxImage()));
                return;
            }
            flameList.add(new Flame(x / Sprite.DEFAULT_SIZE, y / Sprite.DEFAULT_SIZE + i, Sprite.flame_down_1.getFxImage()));
        }
        // UP
        for (int i = 1; i <= radius; i++) {
            if (Collision.flameCollideStatic(x, y - Sprite.DEFAULT_SIZE * i, staticEntities)) {
                break;
            }
            if (Collision.flameCollideEnemy(x, y - Sprite.DEFAULT_SIZE * i, enemiesList)) {
                break;
            }
            if (Collision.flameCollideBomber(x, y - Sprite.DEFAULT_SIZE * i, BombermanGame.bomberman)) {
                BombermanGame.bomberman.setAlive(false);
                flameList.add(new Flame(x / Sprite.DEFAULT_SIZE, y / Sprite.DEFAULT_SIZE - i, Sprite.flame_up_1.getFxImage()));
                return;
            }
            if (BombermanGame.bomberman.isAlive() == false) {
                SettingController.playSFX(SettingController.SFXMODE_CHARACTER_DEATH);
            }
            flameList.add(new Flame(x / Sprite.DEFAULT_SIZE, y / Sprite.DEFAULT_SIZE - i, Sprite.flame_up_1.getFxImage()));
        }
    }
}
