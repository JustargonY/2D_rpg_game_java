package entity;

import main.GamePanel;
import objects.Armor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ArmorEntity extends Entity{

    // Item stats
    public String displayName;
    public String name;
    public int defence;

    public ArmorEntity(GamePanel gp, String name, String displayName, int x, int y, int defence){

        this.gp = gp;

        this.name = name;
        this.displayName = displayName;
        this.defence = defence;

        worldX = x;
        worldY = y;
        collision = false;
        collisionArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        dying = false;

        up1 = loadImage("/object/armor/" + name + ".png");
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

    public Armor createArmor() {

        return new Armor(gp, defence, name, displayName);

    }

}
