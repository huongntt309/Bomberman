package bomberman.Entities.Static;

import bomberman.Component.Sprite;
import bomberman.Entities.Static.StaticEntity;
import bomberman.Map.Map;
import javafx.scene.image.Image;


public class Item extends StaticEntity {
    private String type;
    public Item(int x, int y, Image img, String type) {
        super(x, y, img);
        this.type = type;
    }

    public String getType(){
        return type;
    }

    @Override
    public void update() {
        int yB = this.y/ Sprite.DEFAULT_SIZE;
        int xB = this.x/Sprite.DEFAULT_SIZE;

        Map.gridGameForEnemy_Hard[yB][xB] = 1;
        Map.gridGameForBomber[yB][xB] = 1;
    }
}