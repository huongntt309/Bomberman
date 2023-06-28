package bomberman.Entities.Dynamic.Bomb;

import bomberman.BombermanGame;
import bomberman.Component.Sprite;
import bomberman.Entities.Entity;
import bomberman.Entities.Static.Brick;
import bomberman.Entities.Static.StaticEntity;
import bomberman.Entities.Static.Wall;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Flame extends StaticEntity {
    private int left, right, top, down;                                     // 4 chiều của Flame
                                                 // Bán kính nổ
    private int size = Sprite.DEFAULT_SIZE;                                 // size của sprite
    private int time = 0;                                                   // đếm ngược tgian Flame
    public static final int FLAME_DURATION = 20;                            // thời gian Flame tồn tại
    boolean isAlive = false;
    public Flame(int x, int y, Image image){
       super(x, y, image);
        isAlive = true;
    }


    @Override
    public void update() {// phương thức kết thúc vụ nổ
        if (isAlive && time < FLAME_DURATION){                                             // thời gian flame kéo dài
            time ++;
        } else {
            isAlive = false;
        }
    }
}
