package entity;

import main.GamePanel;
import objects.Weapon;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WeaponEntity extends Entity{

    // Item parameters
    public String displayName;
    public String name;
    public int damage;

    public WeaponEntity(GamePanel gp, String name, String displayName, int x, int y, int damage){

        this.gp = gp;
        this.name = name;
        this.displayName = displayName;
        this.damage = damage;

        worldX = x;
        worldY = y;
        collision = false;
        collisionArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        dying = false;

        up1 = loadImage("/object/weapon/" + name + ".png");
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

    public Weapon createWeapon() {

        return new Weapon(gp, damage, name, displayName);

    }

}
