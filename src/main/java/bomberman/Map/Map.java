package bomberman.Map;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import bomberman.Component.Sprite;
import bomberman.Entities.Dynamic.Character.Bomber;
import bomberman.Entities.Dynamic.Character.BomberAI;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.EasyMode;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyBot.HardMode;
import bomberman.Entities.Dynamic.Character.Enemy.EnemyType.*;
import bomberman.Entities.Static.*;
import lombok.Getter;

import static bomberman.BombermanGame.*;

@Getter
public class Map {
    /**
     * note :
     * render : x ----- y |||
     * 2d array : x |||  y ----
     */
    // HEIGHT = 17
    private static int HEIGHT;
    // WIDTH = 25
    private static int WIDTH;
    public static Scanner scanner; // lớp scanner
    public static int [][] gridGameForEnemy_Med = new int[17][25]; // HEIGHT = 17 WIDTH = 25

    public static int [][] gridGameForEnemy_Hard = new int[17][25]; // HEIGHT = 17 WIDTH = 25

    public static int [][] gridGameForBomber = new int[17][25]; // HEIGHT = 17 WIDTH = 25

    public static void printGrid() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(gridGameForBomber[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void MapLoader(int level) {

        try {
            scanner = new Scanner(new FileReader("src/main/resources/level/Level" + level + ".txt"));
        } catch (FileNotFoundException e) {     
            e.printStackTrace();
        }

        // Đọc 3 số đầu file: (level) (chiều cao) (chiều rộng)
        scanner.nextInt();                                     
        HEIGHT = scanner.nextInt();                            
        WIDTH = scanner.nextInt();
        scanner.nextLine();


        // Đọc từng dòng tiếp theo & xác định entity cần dùng.
        for (int i = 0; i < HEIGHT; i++) { 
            String r = scanner.nextLine();
            for (int j = 0; j < WIDTH; j++) {
                // init with 1. assign 0 when can not pass
                gridGameForEnemy_Hard[i][j] = 1;
                gridGameForEnemy_Med[i][j] = 1;
                gridGameForBomber[i][j] = 1;
                if (r.charAt(j) == '#') {
                    gridGameForEnemy_Hard[i][j] = 0;
                    gridGameForEnemy_Med[i][j] = 0;
                    gridGameForBomber[i][j] = 0;
                    staticEntities.add(new Wall(j, i, Sprite.wall.getFxImage()));
                } else {
                    if (r.charAt(j) == '*') {
                        gridGameForEnemy_Hard[i][j] = 0;
                        gridGameForEnemy_Med[i][j] = 1;
                        // với enemy này xuyên brick
                        gridGameForBomber[i][j] = 40;
                        staticEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    }
                    /**
                     * theo thứ tự 1 2 easy 3 4 medium 5 6 hard mode
                     */
                    if (r.charAt(j) == '1') {
                        gridGameForEnemy_Hard[i][j] = 1;
                        gridGameForEnemy_Med[i][j] = 1;
                        enemiesList.add(new Tree(j, i, Sprite.Tree_down_1.getFxImage()));
                    }
                     if (r.charAt(j) == '2') {
                         gridGameForEnemy_Hard[i][j] = 1;
                         gridGameForEnemy_Med[i][j] = 1;
                         enemiesList.add(new Mummy(j, i, Sprite.Mummy_down_1.getFxImage()));
                     }
                     if (r.charAt(j) == '3') {
                         gridGameForEnemy_Hard[i][j] = 1;
                         gridGameForEnemy_Med[i][j] = 1;
                         enemiesList.add(new Monkey(j, i, Sprite.Monkey_down_1.getFxImage()));
                     }
                     if (r.charAt(j) == '4') {
                         gridGameForEnemy_Hard[i][j] = 1;
                         gridGameForEnemy_Med[i][j] = 1;
                         enemiesList.add(new Robot(j, i, Sprite.Robot_down_1.getFxImage()));
                     }
                    if (r.charAt(j) == '5') {
                        gridGameForEnemy_Hard[i][j] = 1;
                        gridGameForEnemy_Med[i][j] = 1;
                        enemiesList.add(new Scorpion(j, i, Sprite.Scorpion_down_1.getFxImage()));
                    }
                    if (r.charAt(j) == '6') {
                        gridGameForEnemy_Hard[i][j] = 1;
                        gridGameForEnemy_Med[i][j] = 1;
                        enemiesList.add(new Ninja(j, i, Sprite.Ninja_down_1.getFxImage()));
                    }
                     if (r.charAt(j) == 'k') {
                         gridGameForEnemy_Hard[i][j] = 1;
                         gridGameForEnemy_Med[i][j] = 1;
                         gridGameForBomber[i][j] = 1;
                         staticEntities.add(new Item(j, i, Sprite.item_key.getFxImage(), "Key"));
                         keyPosX = i;
                         keyPosY = j;
                     }
                    if (r.charAt(j) == 'd') {
                        gridGameForEnemy_Hard[i][j] = 1;
                        gridGameForEnemy_Med[i][j] = 1;
                        gridGameForBomber[i][j] = 1;
                        staticEntities.add(new Item(j, i, Sprite.item_door.getFxImage(), "Door"));
                        gatePosX = i;
                        gatePosY = j;
                    }
                     if (r.charAt(j) == 'f') {
                         gridGameForEnemy_Hard[i][j] = 0;
                         gridGameForEnemy_Med[i][j] = 0;
                         gridGameForBomber[i][j] = 20;
                         // không giấu item vì tìm khó quá :)
                         staticEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                         staticEntities.add(new Item(j, i, Sprite.item_bomb_size.getFxImage(), "BombSizeItem"));
                     }
                     if (r.charAt(j) == 'b') {
                         gridGameForEnemy_Hard[i][j] = 0;
                         gridGameForEnemy_Med[i][j] = 0;
                         gridGameForBomber[i][j] = 20;
                         staticEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                         staticEntities.add(new Item(j, i, Sprite.item_bomb.getFxImage(), "BombItem"));
                     }
                    if (r.charAt(j) == 'u') {
                        gridGameForEnemy_Hard[i][j] = 0;
                        gridGameForEnemy_Med[i][j] = 0;
                        gridGameForBomber[i][j] = 20;
                         staticEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        staticEntities.add(new Item(j, i, Sprite.item_point.getFxImage(), "PointUpItem"));
                    }
                     if (r.charAt(j) == 's') {
                         gridGameForEnemy_Hard[i][j] = 0;
                         gridGameForEnemy_Med[i][j] = 0;
                         gridGameForBomber[i][j] = 20;
                         staticEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                         staticEntities.add(new Item(j, i, Sprite.item_speed.getFxImage(),"SpeedItem"));
                     }
                    if (r.charAt(j) == 'p') {
                        gridGameForEnemy_Hard[i][j] = 1;
                        gridGameForEnemy_Med[i][j] = 1;
                        gridGameForBomber[i][j] = 1;
                        if(GAME_MODE == MODE_AI)
                            bomberman = new BomberAI(j, i, Sprite.bomber_down_1.getFxImage());
                        else if(GAME_MODE == MODE_NORMAL)
                            bomberman = new Bomber(j, i, Sprite.bomber_down_1.getFxImage());
                    }
                    staticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    /**
                     * add vào trước sẽ ở bên dưới, add vào sau sẽ nổi lên trên ( nhìn thấy được )
                     */
                }
            }
        }
    }
}