package entity;

import java.awt.image.BufferedImage;

public class Entity {

    enum Directions{
        DOWN,
        UP,
        LEFT,
        RIGHT
    }

    public int worldX, worldY;
    public int screenX, screenY;
    public int speed;
    Directions direction;
    public int spriteNum = 1;
    public int spriteCounter = 0;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

}
