package bomberman.Entities.Static;

import bomberman.Map.Map;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import bomberman.Component.Sprite;

public class Brick extends StaticEntity {
    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setLayer(4);
        alive = true;
        block = true;
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    }

    @Override
    public void update() {
        int yB = this.y/Sprite.DEFAULT_SIZE;
        int xB = this.x/Sprite.DEFAULT_SIZE;

        Map.gridGameForEnemy_Hard[yB][xB] = 0;
        Map.gridGameForBomber[yB][xB] = 40;
    }
}


