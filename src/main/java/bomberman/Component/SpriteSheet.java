package bomberman.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import static bomberman.BombermanGame.charID;

/**
 * Tất cả sprite (hình ảnh game) được lưu trữ vào một ảnh duy nhất
 * Class này giúp lấy ra các sprite riêng từ 1 ảnh chung duy nhất đó
 */
public class SpriteSheet {

	private String _path;
	public final int WIDTH;
	public final int HEIGHT;
//	public final int SIZE;
	public int[] _pixels;
	public BufferedImage image;


	public static String CHARACTER_PATH;


	public static final String TILES_PATH = "/image/";
	public static final String ENEMY_PATH = "/char/enemy/";

	/**
	 * character - player
	 */
	public static SpriteSheet player = null;


	/**
	 * MAP tiles
	 */

	public static SpriteSheet transparent = new SpriteSheet("/image/transparent.png", 32, 32);
	public static SpriteSheet brick = null;
	public static SpriteSheet grass = null;
	public static SpriteSheet wall = null;

	public static SpriteSheet brick0 = new SpriteSheet("/image/tile/brick-1.png", 32, 32);
	public static SpriteSheet grass0 = new SpriteSheet("/image/tile/grass-0.png", 32 ,32);
	public static SpriteSheet wall0 = new SpriteSheet("/image/tile/wall-0.png", 32, 32);

	public static SpriteSheet brick1 = new SpriteSheet("/image/tile/brick-1.png", 32, 32);
	public static SpriteSheet grass1 = new SpriteSheet("/image/tile/grass-1.png", 32 ,32);
	public static SpriteSheet wall1 = new SpriteSheet("/image/tile/wall-1.png", 32, 32);

	/**
	 * bomb
	 */
	public static SpriteSheet bomb = null;

	public static SpriteSheet bomb_2 = new SpriteSheet("/char/boom/boom_purple.png", 32 * 3, 32);
	public static SpriteSheet bomb_1 = new SpriteSheet("/char/boom/bombs_32.png", 32, 32 * 14);


	/** 
	 * Flame
	 */
	public static SpriteSheet flame_down_2 = new SpriteSheet("/image/flame/flame_down_2.png", 32, 32);
	public static SpriteSheet flame_down_1 = new SpriteSheet("/image/flame/flame_down_1.png", 32, 32);
	public static SpriteSheet flame_up_1 = new SpriteSheet("/image/flame/flame_up_1.png", 32, 32);
	public static SpriteSheet flame_up_2 = new SpriteSheet("/image/flame/flame_up_2.png", 32, 32);
	public static SpriteSheet flame_left_1 = new SpriteSheet("/image/flame/flame_left_1.png", 32, 32);
	public static SpriteSheet flame_left_2 = new SpriteSheet("/image/flame/flame_left_2.png", 32, 32);
	public static SpriteSheet flame_right_1 = new SpriteSheet("/image/flame/flame_right_1.png", 32, 32);
	public static SpriteSheet flame_right_2 = new SpriteSheet("/image/flame/flame_right_2.png", 32, 32);
	public static SpriteSheet flame_mid = new SpriteSheet("/image/flame/flame_mid.png", 32, 32);

	/**
	 * // lay tam sprite cua player cho enemy
	 * */
	public static SpriteSheet enemy_mummy = new SpriteSheet("/char/enemy/mummy/move.png", 32*3, 32*4);
	public static SpriteSheet enemy_tree = new SpriteSheet("/char/enemy/tree/move.png", 32*3, 32*4);
	public static SpriteSheet enemy_monkey = new SpriteSheet("/char/enemy/monkey/move.png", 32*3, 32*4);
	public static SpriteSheet enemy_ninja = new SpriteSheet("/char/enemy/ninja/move.png", 32*3, 32*4);
	public static SpriteSheet enemy_robot = new SpriteSheet("/char/enemy/robot/move.png", 32*3, 32*4);
	public static SpriteSheet enemy_scorpion = new SpriteSheet("/char/enemy/scorpion/move.png", 32*3, 32*4);
	


	/**
	 * item
	 */

	public static SpriteSheet item_speed_up = new SpriteSheet("/image/item/item_shoe.png", 32, 32);
	public static SpriteSheet item_bomb_up = new SpriteSheet("/image/item/item_bomb.png", 32, 32);
	public static SpriteSheet item_bomb_size_up = new SpriteSheet("/image/item/item_bombsize.png", 32, 32);
	public static SpriteSheet item_key_ = new SpriteSheet("/image/item/item_key.png", 32, 32);
	public static SpriteSheet item_door_ = new SpriteSheet("/image/item/item_door.png", 32, 32);
	public static SpriteSheet item_point_ = new SpriteSheet("/image/item/item_point.png", 32, 32);


	public SpriteSheet(String path, int w, int h) {
		_path = path;
		WIDTH = w;
		HEIGHT = h;
		_pixels = new int[WIDTH * HEIGHT];
		load();
	}
	
	private void load() {
		try {
			URL a = SpriteSheet.class.getResource(_path);
			image = ImageIO.read(a);
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, _pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * load from db
	 */
	public static void initCharacterPath() throws SQLException {
		CHARACTER_PATH = "/char/player/" + charID + "-character.png";
		player = new SpriteSheet(CHARACTER_PATH, 32*3, 32*4);
	}


	public static void initMap_tile0() {
		brick = brick0;
		grass = grass0;
		wall = wall0;
	}
	public static void initMap_tile1() {
		brick = brick1;
		grass = grass1;
		wall = wall1;
	}
	public static void initRandomMapTiles() {
		double random = Math.random();
		if(random < 0.5) initMap_tile0();
		else initMap_tile1();
	}

	public static void initBombSkin(SpriteSheet bomb_) {
		bomb = bomb_;
	}
}
