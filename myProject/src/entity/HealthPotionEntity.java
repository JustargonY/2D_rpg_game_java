package entity;

import main.GamePanel;
import objects.HealthPotion;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthPotionEntity extends Entity{

    public HealthPotionEntity(GamePanel gp, int x, int y){

        this.gp = gp;

        worldX = x;
        worldY = y;
        collision = false;
        collisionArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        dying = false;

        up1 = loadImage("/object/usable/health_potion.png");
        maxHP = 1;
        curHP = 1;
    }

    @Override
    public BufferedImage getImage() {
        return up1;
    }

    @Override
    public void draw(Graphics2D g) {

        boolean IS_ON_THE_SCREEN = worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
                worldY < gp.player.worldY + gp.player.screenY + gp.tileSize;

        if (IS_ON_THE_SCREEN) {

            int x = worldX - gp.player.worldX + gp.player.screenX;
            int y = worldY - gp.player.worldY + gp.player.screenY;

            BufferedImage image = up1;

            g.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

        }

    }

    public HealthPotion createPotion() {

        return new HealthPotion(gp);

    }

}
