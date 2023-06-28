package bomberman.Entities.Static;

import bomberman.Entities.Entity;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class StaticEntity extends Entity {
    public StaticEntity(int x, int y, Image image) {
        super(x, y, image);
    }

    protected boolean canPass;
    @Override
    public void update() {

    }

}
