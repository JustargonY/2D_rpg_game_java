package entity;

import main.GamePanel;
import main.KeyL;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyL keyL;

    public Player(GamePanel gp, KeyL keyL){
        this.gp = gp;
        this.keyL = keyL;
        worldX = 20 * gp.tileSize;
        worldY = 11 * gp.tileSize;
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;
        speed = 3;
        direction = Directions.DOWN;
        loadPlayerImage();
    }

    public void loadPlayerImage(){
        try{

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/hero_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/hero_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/hero_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/hero_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/hero_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/hero_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/hero_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/hero_right_2.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){


        if (keyL.pressedUp){
            worldY -= speed;
            direction = Directions.UP;
        }
        if (keyL.pressedDown){
            worldY += speed;
            direction = Directions.DOWN;
        }
        if (keyL.pressedLeft){
            worldX -= speed;
            direction = Directions.LEFT;
        }
        if (keyL.pressedRight){
            worldX += speed;
            direction = Directions.RIGHT;
        }


        spriteCounter += 1;
        if (spriteCounter > 18){
            if (spriteNum == 1){
                spriteNum = 2;
            }
            else if (spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch (direction){
            case UP -> {
                if (spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                }
            case DOWN -> {if (spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                }
            case LEFT -> {if (spriteNum == 1){
                    image = left1;
                }
                    if (spriteNum == 2){
                        image = left2;
                    }
                }
                case RIGHT -> {if (spriteNum == 1){
                    image = right1;
                }
                    if (spriteNum == 2){
                        image = right2;
                    }
                }
            }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
