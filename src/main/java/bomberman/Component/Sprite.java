package bomberman.Component;

import javafx.scene.image.*;

/**
 * Lưu trữ thông tin các pixel của 1 sprite (hình ảnh game)
 */
public class Sprite {
	public static final int WHITE       = 0xFFFFFFFF;
	public static final int DEFAULT_SIZE = 32;
	public static final int SCALED_SIZE = DEFAULT_SIZE;
    private static final int TRANSPARENT_COLOR = WHITE;
	public final int HEIGHT;
	public final int WIDTH;
//	public final int SIZE;
	private int _x, _y;
	public int[] _pixels;
	protected int _realWidth;
	protected int _realHeight;
	private SpriteSheet _sheet;

	/** Default Sprites.  */
	public static Sprite transparent = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.transparent, 32, 32);

	// PLAYER
	public static Sprite bomber_up ;
	public static Sprite bomber_up_1;
	public static Sprite bomber_up_2;

	public static Sprite bomber_down;
	public static Sprite bomber_down_1;
	public static Sprite bomber_down_2;

	public static Sprite bomber_left;
	public static Sprite bomber_left_1;
	public static Sprite bomber_left_2;

	public static Sprite bomber_right ;
	public static Sprite bomber_right_1 ;
	public static Sprite bomber_right_2 ;


	// BOMB
	public static Sprite bomb_0;
	public static Sprite bomb_1;
	public static Sprite bomb_2;
	
	// TILES
	public static Sprite grass;
	public static Sprite brick;
	public static Sprite wall;

	// FLAME
	public static Sprite flame_down_2; 
	public static Sprite flame_down_1; 
	public static Sprite flame_up_1;
	public static Sprite flame_up_2;
	public static Sprite flame_left_1; 
	public static Sprite flame_left_2; 
	public static Sprite flame_mid;
	public static Sprite flame_right_1; 
	public static Sprite flame_right_2;
	
	// ENEMY
	public static Sprite Mummy_left_3;
	public static Sprite Mummy_left_1;
	public static Sprite Mummy_left_2;
	public static Sprite Mummy_right_3 ;
	public static Sprite Mummy_right_1 ;
	public static Sprite Mummy_right_2 ;
	public static Sprite Mummy_up_3 ;
	public static Sprite Mummy_up_1;
	public static Sprite Mummy_up_2;
	public static Sprite Mummy_down_3;
	public static Sprite Mummy_down_1;
	public static Sprite Mummy_down_2;

	public static Sprite Tree_left_3;
	public static Sprite Tree_left_1;
	public static Sprite Tree_left_2;
	public static Sprite Tree_right_3 ;
	public static Sprite Tree_right_1 ;
	public static Sprite Tree_right_2 ;
	public static Sprite Tree_up_3 ;
	public static Sprite Tree_up_1;
	public static Sprite Tree_up_2;
	public static Sprite Tree_down_3;
	public static Sprite Tree_down_1;
	public static Sprite Tree_down_2;

	public static Sprite Monkey_left_3;
	public static Sprite Monkey_left_1;
	public static Sprite Monkey_left_2;
	public static Sprite Monkey_right_3 ;
	public static Sprite Monkey_right_1 ;
	public static Sprite Monkey_right_2 ;
	public static Sprite Monkey_up_3 ;
	public static Sprite Monkey_up_1;
	public static Sprite Monkey_up_2;
	public static Sprite Monkey_down_3;
	public static Sprite Monkey_down_1;
	public static Sprite Monkey_down_2;

	
	public static Sprite Ninja_up_3   ;
	public static Sprite Ninja_up_1   ;
	public static Sprite Ninja_up_2   ;
	public static Sprite Ninja_down_3 ;
	public static Sprite Ninja_down_1 ;
	public static Sprite Ninja_down_2 ;
	public static Sprite Ninja_left_3 ;
	public static Sprite Ninja_left_1 ;
	public static Sprite Ninja_left_2 ;
	public static Sprite Ninja_right_3;
	public static Sprite Ninja_right_1;
	public static Sprite Ninja_right_2;
	
	public static Sprite Scorpion_up_3   ;
	public static Sprite Scorpion_up_1   ;
	public static Sprite Scorpion_up_2   ;
	public static Sprite Scorpion_down_3 ;
	public static Sprite Scorpion_down_1 ;
	public static Sprite Scorpion_down_2 ;
	public static Sprite Scorpion_left_3 ;
	public static Sprite Scorpion_left_1 ;
	public static Sprite Scorpion_left_2 ;
	public static Sprite Scorpion_right_3;
	public static Sprite Scorpion_right_1;
	public static Sprite Scorpion_right_2;

	
	public static Sprite Robot_up_3   ;
	public static Sprite Robot_up_1   ;
	public static Sprite Robot_up_2   ;
	public static Sprite Robot_down_3 ;
	public static Sprite Robot_down_1 ;
	public static Sprite Robot_down_2 ;
	public static Sprite Robot_left_3 ;
	public static Sprite Robot_left_1 ;
	public static Sprite Robot_left_2 ;
	public static Sprite Robot_right_3;
	public static Sprite Robot_right_1;
	public static Sprite Robot_right_2;

	

	public static Sprite item_speed = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.item_speed_up, 32, 32);
	public static Sprite item_bomb = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.item_bomb_up, 32, 32);
	public static Sprite item_bomb_size = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.item_bomb_size_up, 32, 32);
	public static Sprite item_key = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.item_key_, 32, 32);
	public static Sprite item_door = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.item_door_, 32, 32);
	public static Sprite item_point = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.item_point_, 32, 32);

	public Sprite(int size, int x, int y, SpriteSheet sheet, int rw, int rh) {
		HEIGHT = size;
		WIDTH = size;
//		SIZE = size;
		_pixels = new int[HEIGHT * WIDTH];
		_x = x * WIDTH;
		_y = y * HEIGHT;
		_sheet = sheet;
		_realWidth = rw;
		_realHeight = rh;
		load();
	}
	
	public Sprite(int size, int color) {
		HEIGHT = size;
		WIDTH = size;


//		SIZE = size;
		_pixels = new int[HEIGHT * WIDTH];
		setColor(color);
	}
	
	private void setColor(int color) {
		for (int i = 0; i < _pixels.length; i++) {
			_pixels[i] = color;
		}
	}

	private void load() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				_pixels[x + y * WIDTH] = _sheet._pixels[(x + _x) + (y + _y) * _sheet.WIDTH];
			}
		}
	}
	
	public static Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2, int animate, int time) {
		int calc = animate % time;
		int diff = time / 3;
		
		if(calc < diff) {
			return normal;
		}
			
		if(calc < diff * 2) {
			return x1;
		}
			
		return x2;
	}
	
	public static Sprite movingSprite(Sprite x1, Sprite x2, int animate, int time) {
		int diff = time / 2;
		return (animate % time > diff) ? x1 : x2; 
	}
	
//	public int getSize() {
//		return SIZE;
//	}

	public int getPixel(int i) {
		return _pixels[i];
	}

	public Image getFxImage() {
        WritableImage wr = new WritableImage(WIDTH, HEIGHT);
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if ( _pixels[x + y * WIDTH] == TRANSPARENT_COLOR) {
                    pw.setArgb(x, y, 0);
                }
                else {
                    pw.setArgb(x, y, _pixels[x + y * WIDTH]);
                }
            }
        }
        Image input = new ImageView(wr).getImage();
        return resample(input, SCALED_SIZE / DEFAULT_SIZE);
    }

	private Image resample(Image input, int scaleFactor) {
		final int W = (int) input.getWidth();
		final int H = (int) input.getHeight();
		final int S = scaleFactor;

		WritableImage output = new WritableImage(
				W * S,
				H * S
		);

		PixelReader reader = input.getPixelReader();
		PixelWriter writer = output.getPixelWriter();

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				final int argb = reader.getArgb(x, y);
				for (int dy = 0; dy < S; dy++) {
					for (int dx = 0; dx < S; dx++) {
						writer.setArgb(x * S + dx, y * S + dy, argb);
					}
				}
			}
		}

		return output;
	}

	public static void resetCharacterSprite() {
		bomber_up   =    new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.player, 32, 32);
		bomber_up_1 =    new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.player, 32, 32);
		bomber_up_2 =    new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.player, 32, 32);

		bomber_down   =  new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.player, 32, 32);
		bomber_down_1 =  new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.player, 32, 32);
		bomber_down_2 =  new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.player, 32, 32);

		bomber_left   =  new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.player, 32, 32);
		bomber_left_1 =  new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.player, 32, 32);
		bomber_left_2 =  new Sprite(DEFAULT_SIZE, 2, 1, SpriteSheet.player, 32 ,32);

		bomber_right =   new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.player, 32, 32);
		bomber_right_1 = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.player, 32, 32);
		bomber_right_2 = new Sprite(DEFAULT_SIZE, 2, 2, SpriteSheet.player, 32, 32);
	}

	/**
	 * LEAK MEMORY
	 */
	public static void resetTiles() {
		grass =  new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.grass, 32, 32);
		brick =  new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.brick, 32, 32);
		wall  =  new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.wall, 32, 32);
	}

	public static void resetBomb() {
		bomb_0 = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.bomb, 32, 32);
		bomb_1 = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.bomb, 32, 32);
		bomb_2 = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.bomb, 32, 32);
	}
	public static void resetFlame() {
		flame_down_2   = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.flame_down_2, 32, 32);
		flame_down_1   = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.flame_down_1, 32, 32);
		flame_up_1     = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.flame_up_1, 32, 32);
		flame_up_2     = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.flame_up_2, 32, 32);
		flame_left_1   = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.flame_left_1, 32, 32);
		flame_left_2   = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.flame_left_2, 32, 32);
		flame_right_1  = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.flame_right_1, 32, 32);
		flame_right_2  = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.flame_right_2, 32, 32);
		flame_mid      = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.flame_mid, 32, 32);
	}
	public static void resetEnemy() {
		Mummy_up_3     = new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.enemy_mummy, 32, 32);
		Mummy_up_1     = new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.enemy_mummy, 32, 32);
		Mummy_up_2     = new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.enemy_mummy, 32, 32);
		Mummy_down_3   = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.enemy_mummy, 32, 32);
		Mummy_down_1   = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.enemy_mummy, 32, 32);
		Mummy_down_2   = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.enemy_mummy, 32, 32);
		Mummy_left_3   = new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.enemy_mummy, 32, 32);
		Mummy_left_1   = new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.enemy_mummy, 32, 32);
		Mummy_left_2   = new Sprite(DEFAULT_SIZE, 2, 1, SpriteSheet.enemy_mummy, 32, 32);
		Mummy_right_3  = new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.enemy_mummy, 32, 32);
		Mummy_right_1  = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.enemy_mummy, 32, 32);
		Mummy_right_2  = new Sprite(DEFAULT_SIZE, 2, 2, SpriteSheet.enemy_mummy, 32, 32);

		Tree_up_3     = new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.enemy_tree, 32, 32);
		Tree_up_1     = new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.enemy_tree, 32, 32);
		Tree_up_2     = new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.enemy_tree, 32, 32);
		Tree_down_3   = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.enemy_tree, 32, 32);
		Tree_down_1   = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.enemy_tree, 32, 32);
		Tree_down_2   = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.enemy_tree, 32, 32);
		Tree_left_3   = new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.enemy_tree, 32, 32);
		Tree_left_1   = new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.enemy_tree, 32, 32);
		Tree_left_2   = new Sprite(DEFAULT_SIZE, 2, 1, SpriteSheet.enemy_tree, 32, 32);
		Tree_right_3  = new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.enemy_tree, 32, 32);
		Tree_right_1  = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.enemy_tree, 32, 32);
		Tree_right_2  = new Sprite(DEFAULT_SIZE, 2, 2, SpriteSheet.enemy_tree, 32, 32);
		
		Monkey_up_3     = new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.enemy_monkey, 32, 32);
		Monkey_up_1     = new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.enemy_monkey, 32, 32);
		Monkey_up_2     = new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.enemy_monkey, 32, 32);
		Monkey_down_3   = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.enemy_monkey, 32, 32);
		Monkey_down_1   = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.enemy_monkey, 32, 32);
		Monkey_down_2   = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.enemy_monkey, 32, 32);
		Monkey_left_3   = new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.enemy_monkey, 32, 32);
		Monkey_left_1   = new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.enemy_monkey, 32, 32);
		Monkey_left_2   = new Sprite(DEFAULT_SIZE, 2, 1, SpriteSheet.enemy_monkey, 32, 32);
		Monkey_right_3  = new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.enemy_monkey, 32, 32);
		Monkey_right_1  = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.enemy_monkey, 32, 32);
		Monkey_right_2  = new Sprite(DEFAULT_SIZE, 2, 2, SpriteSheet.enemy_monkey, 32, 32);
		
		Ninja_up_3     = new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.enemy_ninja, 32, 32);
		Ninja_up_1     = new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.enemy_ninja, 32, 32);
		Ninja_up_2     = new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.enemy_ninja, 32, 32);
		Ninja_down_3   = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.enemy_ninja, 32, 32);
		Ninja_down_1   = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.enemy_ninja, 32, 32);
		Ninja_down_2   = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.enemy_ninja, 32, 32);
		Ninja_left_3   = new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.enemy_ninja, 32, 32);
		Ninja_left_1   = new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.enemy_ninja, 32, 32);
		Ninja_left_2   = new Sprite(DEFAULT_SIZE, 2, 1, SpriteSheet.enemy_ninja, 32, 32);
		Ninja_right_3  = new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.enemy_ninja, 32, 32);
		Ninja_right_1  = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.enemy_ninja, 32, 32);
		Ninja_right_2  = new Sprite(DEFAULT_SIZE, 2, 2, SpriteSheet.enemy_ninja, 32, 32);
		
		Scorpion_up_3     = new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.enemy_scorpion, 32, 32);
		Scorpion_up_1     = new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.enemy_scorpion, 32, 32);
		Scorpion_up_2     = new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.enemy_scorpion, 32, 32);
		Scorpion_down_3   = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.enemy_scorpion, 32, 32);
		Scorpion_down_1   = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.enemy_scorpion, 32, 32);
		Scorpion_down_2   = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.enemy_scorpion, 32, 32);
		Scorpion_left_3   = new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.enemy_scorpion, 32, 32);
		Scorpion_left_1   = new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.enemy_scorpion, 32, 32);
		Scorpion_left_2   = new Sprite(DEFAULT_SIZE, 2, 1, SpriteSheet.enemy_scorpion, 32, 32);
		Scorpion_right_3  = new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.enemy_scorpion, 32, 32);
		Scorpion_right_1  = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.enemy_scorpion, 32, 32);
		Scorpion_right_2  = new Sprite(DEFAULT_SIZE, 2, 2, SpriteSheet.enemy_scorpion, 32, 32);

		
		Robot_up_3     = new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.enemy_robot, 32, 32);
		Robot_up_1     = new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.enemy_robot, 32, 32);
		Robot_up_2     = new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.enemy_robot, 32, 32);
		Robot_down_3   = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.enemy_robot, 32, 32);
		Robot_down_1   = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.enemy_robot, 32, 32);
		Robot_down_2   = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.enemy_robot, 32, 32);
		Robot_left_3   = new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.enemy_robot, 32, 32);
		Robot_left_1   = new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.enemy_robot, 32, 32);
		Robot_left_2   = new Sprite(DEFAULT_SIZE, 2, 1, SpriteSheet.enemy_robot, 32, 32);
		Robot_right_3  = new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.enemy_robot, 32, 32);
		Robot_right_1  = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.enemy_robot, 32, 32);
		Robot_right_2  = new Sprite(DEFAULT_SIZE, 2, 2, SpriteSheet.enemy_robot, 32, 32);

		
		transparent   = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.transparent, 32, 32);

	}
}


