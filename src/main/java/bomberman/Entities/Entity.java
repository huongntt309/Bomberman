package bomberman.Entities;

import bomberman.Component.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Entity {
    /* Tính toạ độ theo pixel từ góc trên trái */
    protected int x;
    protected int y;

    protected Image img;
    protected int animated = 0;
    protected int layer;
    protected boolean alive;
    protected boolean block;

    
    // Constructor, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.DEFAULT_SIZE;
        this.y = yUnit * Sprite.DEFAULT_SIZE;
        this.img = img;
    }

    public Entity(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

    /**
     * sau nay xoa cai nay di, chi dung collision.java

     */
    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    }

    
}


