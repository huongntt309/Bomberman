package bomberman.Entities.Dynamic.Character;

import bomberman.BombermanGame;
import bomberman.Component.AStarAlgo;
import bomberman.Component.Collision;
import bomberman.Entities.Dynamic.Bomb.Bomb;
import javafx.scene.image.Image;
import bomberman.Component.Sprite;
import bomberman.Entities.*;
import lombok.Getter;
import lombok.Setter;

import static bomberman.BombermanGame.staticEntities;
@Getter
@Setter
public abstract class Character extends Entity {
    protected static final int LEFT = 0;
    protected static final int RIGHT = 1;
    protected static final int UP = 2;
    protected static final int DOWN = 3;
    protected static final int NODIRECTION = 4;
    public static AStarAlgo aStarAlgo = new AStarAlgo();

    protected int remainingStep; // đi đủ bước để cho hết 1 tile
    protected int timeToMove; // thời gian để update

    protected int speed;
    protected int direction;
    protected int prev_direction;

    protected int left = 0;
    protected int right = 0;
    protected int up = 0;
    protected int down = 0;

    public Character(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setAlive(true);
        remainingStep = 0;
        timeToMove = 0;
        direction = NODIRECTION;
    }

    @Override
    public void update() {
        if(remainingStep > 0)
        {
            remainingStep--;
            if(prev_direction == LEFT) {
                goLeft();
            }
            if(prev_direction == RIGHT) {
                goRight();
            }
            if(prev_direction == UP) {
                goUp();
            }
            if(prev_direction == DOWN) {
                goDown();
            }
        }
        else {
            remainingStep = Sprite.DEFAULT_SIZE / speed;
            prev_direction = direction;
        }
    }


    //Các phương thức di chuyển của "Character"

    public void goLeft() {
        for (Bomb bomb : Bomber.bombs) {
            if(BombermanGame.bomberman.isInBomb() && this instanceof Bomber) {
            }
            else if (Collision.checkColide(this.x - this.speed, this.y, bomb.getX(), bomb.getY())) {
                return;
            }
        }
        if (Collision.characterCollideStatic(this, this.x - speed, this.y, staticEntities)) return;
        x = x - speed;
    }

    public void goRight() {
        for (Bomb bomb : Bomber.bombs) {
            if( this instanceof Bomber && BombermanGame.bomberman.isInBomb()) {
            }
            else if (Collision.checkColide(this.x + this.speed, this.y, bomb.getX(), bomb.getY())) {
                return;
            }
        }
        if (Collision.characterCollideStatic(this, this.x + speed, this.y, staticEntities)) return;
         x = x + speed;
    }

    public void goUp() {
        for (Bomb bomb : Bomber.bombs) {
            if( this instanceof Bomber && BombermanGame.bomberman.isInBomb()) {
            }
            else if (Collision.checkColide(this.x, this.y - this.speed, bomb.getX(), bomb.getY())) {
                return;
            }
        }
        if (Collision.characterCollideStatic(this, this.x, this.y - this.speed, staticEntities)) return;
         y = y - speed;
    }

    public void goDown() {
        for (Bomb bomb : Bomber.bombs) {
            if( this instanceof Bomber && BombermanGame.bomberman.isInBomb()) {

            }
            else if (Collision.checkColide(this.x, this.y + this.speed, bomb.getX(), bomb.getY())) {
                return;
            }
        }
        if (Collision.characterCollideStatic(this, this.x, this.y + this.speed, staticEntities)) return;
        y = y + speed;
    }

}
