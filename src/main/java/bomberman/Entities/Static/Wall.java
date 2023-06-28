package bomberman.Entities.Static;

import bomberman.Component.Sprite;
import bomberman.Map.Map;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class Wall extends StaticEntity {

    public Wall(int x, int y, Image img) {
        super(x, y, img);
        setLayer(5);
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
        Map.gridGameForBomber[yB][xB] = 0;
    }

}