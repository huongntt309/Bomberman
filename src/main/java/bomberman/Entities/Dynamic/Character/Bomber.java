package bomberman.Entities.Dynamic.Character;

import java.util.ArrayList;
import java.util.List;

import bomberman.BombermanGame;
import bomberman.Component.Collision;
import bomberman.Entities.Static.Item;
import bomberman.Map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import bomberman.Component.Sprite;
import bomberman.Entities.Dynamic.Bomb.*;
import lombok.Getter;
import lombok.Setter;

import static bomberman.BombermanGame.*;

@Getter
@Setter
public class Bomber extends Character {
    private boolean hasKey;
    private boolean nextLvl;

    protected boolean inBomb;

    protected int bombCount;                                          // số bom tối đa
    protected int radius;                                             // bán kính nổ
    protected int timeAfterDie = 3;                                   // thời gian sau khi chết
    protected boolean placeBombCommand = false;                       // lệnh đặt bom
    protected boolean bombGhosting = false;                           // đi xuyên qua bom
    public static final List<Bomb> bombs = new ArrayList<>();             // khai báo list quản lý bom

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        setBombCount(2);
        setLayer(1); // chỉ số va chạm của bomber
        setSpeed(4); // tốc độ
        setRadius(1); // bán kính nổ
        setHasKey(false);
        setNextLvl(false);
    }

    //  nhận tín hiệu bàn phím (nhấn phím)
    public void handleKeyPressedEvent(KeyCode keyCode) {
        if (keyCode == KeyCode.LEFT) {
            this.direction = LEFT;
        } else if (keyCode == KeyCode.RIGHT) {
            this.direction = RIGHT;
        } else if (keyCode == KeyCode.UP) {
            this.direction = UP;
        } else if (keyCode == KeyCode.DOWN) {
            this.direction = DOWN;
        }
        if (keyCode == KeyCode.SPACE) {
            placeBombCommand = true;
        }
    }

    //  nhận tín hiệu bàn phím (nhả phím)
    public void handleKeyReleasedEvent(KeyCode keyCode) {
        if (direction == LEFT) {
            img = Sprite.bomber_left.getFxImage();
        } else if (direction == RIGHT) {
            img = Sprite.bomber_right.getFxImage();
        } else if (direction == UP) {
            img = Sprite.bomber_up.getFxImage();
        } else if (direction == DOWN) {
            img = Sprite.bomber_down.getFxImage();
        }
        direction = NODIRECTION;
        if (keyCode == KeyCode.SPACE) {
            placeBombCommand = false;
            placeBomb();
            inBomb = true;
        }
    }


    // Xử lí ảnh hiển thị di chuyển
    public void goLeft() {
        img = Sprite.movingSprite(Sprite.bomber_left, Sprite.bomber_left_1, Sprite.bomber_left_2, left++, 20).getFxImage();
        super.goLeft();
    }

    public void goRight() {
        img = Sprite.movingSprite(Sprite.bomber_right, Sprite.bomber_right_1, Sprite.bomber_right_2, right++, 20).getFxImage();
        super.goRight();
    }

    public void goUp() {
        img = Sprite.movingSprite(Sprite.bomber_up, Sprite.bomber_up_1, Sprite.bomber_up_2, up++, 20).getFxImage();
        super.goUp();
    }

    public void goDown() {
        img = Sprite.movingSprite(Sprite.bomber_down, Sprite.bomber_down_1, Sprite.bomber_down_2, down++, 20).getFxImage();
        super.goDown();
    }


    // phương thức xử lý chức năng đặt bom
    public void placeBomb() {
        if (bombCount > 0) {
            /**
             *  Set toạ đọ x/y của bomb */
            int xB = (int) Math.round((x + 4) / (double) Sprite.SCALED_SIZE);
            int yB = (int) Math.round((y + 4) / (double) Sprite.SCALED_SIZE);

            for (Bomb bomb : bombs) {                                                                   // duyệt list bombs
                if (xB * Sprite.SCALED_SIZE == bomb.getX() && yB * Sprite.SCALED_SIZE == bomb.getY()) return;
            }

            // với hard mode. thấy bomb thì chạy hướng khác
            // med thì ko biết thế :)
            Map.gridGameForEnemy_Hard[yB][xB] = 0;
            // nếu là vị trí block -> giữ im,
            // nếu khác : set = -1;
            Map.gridGameForEnemy_Hard[yB + 1][xB] = Map.gridGameForEnemy_Hard[yB + 1][xB] == 0 ? 0 : -1;
            Map.gridGameForEnemy_Hard[yB][xB + 1] = Map.gridGameForEnemy_Hard[yB][xB + 1] == 0 ? 0 : -1;
            Map.gridGameForEnemy_Hard[yB - 1][xB] = Map.gridGameForEnemy_Hard[yB - 1][xB] == 0 ? 0 : -1;
            Map.gridGameForEnemy_Hard[yB][xB - 1] = Map.gridGameForEnemy_Hard[yB][xB - 1] == 0 ? 0 : -1;

            Map.gridGameForEnemy_Med[yB][xB] = 1;

            Map.gridGameForBomber[yB][xB] = 2000;

            bombs.add(new Bomb(xB, yB, Sprite.bomb_0.getFxImage(), this.radius));                                    // tạo bom và add vào list bomb
            bombCount--;                                                                                // trừ đi lượng bom dữ trự sau khi đã đặt
        }
    }

    @Override
    public void update() {
        super.update();
        if (Collision.enemyCollidePlayer(bomberman, enemiesList)) {
            bomberman.setAlive(false);
        }
        if (inBomb == true && !bombs.isEmpty()) {
            if (Collision.checkColide(BombermanGame.bomberman.getX(), BombermanGame.bomberman.getY(), bombs.get(bombs.size() - 1).getX(), bombs.get(bombs.size() - 1).getY())) {
            } else inBomb = false;
            if (Collision.enemyCollidePlayer(bomberman, enemiesList)) {
                bomberman.setAlive(true);
            }
        }

        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();

            if (bombs.get(i).isAlive() == false) {
                bombs.remove(i);
                bombCount++;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        for (Bomb bomb : bombs) {
            bomb.render(gc);
        }
        super.render(gc);
    }

    public void appyItem(Item item) {
        String itemType = item.getType();
        if (itemType.equals("BombItem")) {
            bombCount++;
            return;
        }
        if (itemType.equals("PointUpItem")) {
            score += 1700;
            return;
        }
        if (itemType.equals("SpeedItem")) {
            if (this.getSpeed() <= 8)
                this.speed *= 2;
            return;
        }
        if (itemType.equals("BombSizeItem")) {
            if (radius <= 3) radius++;
            return;
        }
        if (itemType.equals("Key")) {
            setHasKey(true);
            return;
        }
        if (itemType.equals("Door")) {
            setNextLvl(true);
        }
    }
}

