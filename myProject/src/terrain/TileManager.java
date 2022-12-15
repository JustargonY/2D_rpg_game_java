package terrain;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    public GamePanel gp;
    public Tile[] tiles;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {

        this.gp = gp;
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        loadMap();
        loadImages();

    }

    private void loadMap(){

        InputStream is = getClass().getResourceAsStream("/maps/test_map.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int col = 0;
        int row = 0;

        while (row < gp.maxWorldRow){
            try {
                String line = br.readLine();
                String[] numbers = line.split(" ");

                while (col < gp.maxWorldCol){

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col += 1;

                }

                row += 1;
                col = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void loadImages(){
        tiles = new Tile[10];
        loadTile(0, true, "water");
        loadTile(1, false, "grass");
        loadTile(2, false, "grass2");
        loadTile(3, true, "tree");
        loadTile(4, true, "wall");
        loadTile(5, false, "sand");
    }

    private void loadTile(int tileNum, boolean collision, String name) {
        Tile tile = new Tile();
        try {
            tile.collision = collision;
            tile.image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tiles[tileNum] = tile;
    }

    public void draw(Graphics2D g){
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow){

            int worldX = col * gp.tileSize;
            int worldY = row * gp.tileSize;

            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
            worldY > gp.player.worldY - gp.player.screenY - gp.tileSize && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize){
                int type = mapTileNum[col][row];
                BufferedImage image = tiles[type].image;

                g.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            col += 1;
            if (col == gp.maxWorldCol){
                col = 0;
                row += 1;
            }

        }
    }

}
