package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public abstract class Entity {

    enum Directions{
        DOWN,
        UP,
        LEFT,
        RIGHT
    }

    GamePanel gp;

    // Random parameter for drop
    Random random = new Random();

    // Move parameters
    public int worldX, worldY;
    public int speed;
    Directions direction;

    // Draw parameters
    public int spriteNum = 1;
    public int spriteCounter = 0;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public boolean dying;
    public boolean dead;
    int dyingFrame;

    // Collision parameters
    public Rectangle collisionArea;
    public boolean collisionOn;
    public boolean collision;

    // Fight parameters
    public int maxHP, curHP;
    public int invincibleCounter;

    public BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage getImage() {
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
        return image;
    }

    private void dyingAnimation(Graphics2D g) {

        dyingFrame++;

        if (dyingFrame > 30) {
            dying = false;
            dead = true;
            if (this instanceof Monster) {

                gp.player.increaseExp(((Monster) this).exp);
                gp.player.gold += ((Monster) this).gold;
                gp.ui.addMessage("Killed " + ((Monster) this).name);
                gp.ui.addMessage("+" + ((Monster) this).exp + "Exp");
                gp.ui.addMessage("+" + ((Monster) this).gold + "Gold");

                int drop = random.nextInt(100) + 1;
                if (drop > 75 && drop <= 90){
                    gp.createList.add(new HealthPotionEntity(gp, worldX, worldY));
                } else if (drop > 90) {
                    gp.createList.add(new ManaPotionEntity(gp, worldX, worldY));
                }

            }
        }
        else if ((dyingFrame / 10) % 2 == 0) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0F));
            gp.playSoundEffect(4);
        } else {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        }

    }

    public void draw(Graphics2D g) {

        boolean IS_ON_THE_SCREEN = worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
                worldY < gp.player.worldY + gp.player.screenY + gp.tileSize;

        if (IS_ON_THE_SCREEN) {

            int x = worldX - gp.player.worldX + gp.player.screenX;
            int y = worldY - gp.player.worldY + gp.player.screenY;

            BufferedImage image = getImage();

            if (dying){
                dyingAnimation(g);
            }

            g.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

        }

    }

    public void update() {

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

    public void collisionCheck() {

        int leftX = worldX + collisionArea.x;
        int rightX = worldX + collisionArea.x + collisionArea.width;

        int upperY = worldY + collisionArea.y;
        int lowerY = worldY + gp.tileSize;

        int leftCol = leftX / gp.tileSize;
        int rightCol = rightX / gp.tileSize;
        int topRow = upperY / gp.tileSize;
        int downRow = lowerY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (direction){
            case UP -> {
                topRow = (upperY - speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[leftCol][topRow];
                tileNum2 = gp.tileManager.mapTileNum[rightCol][topRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision){
                    collisionOn = true;
                }
            }
            case DOWN -> {
                downRow = (lowerY + speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[leftCol][downRow];
                tileNum2 = gp.tileManager.mapTileNum[rightCol][downRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision){
                    collisionOn = true;
                }
            }
            case LEFT -> {
                leftCol = (leftX - speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[leftCol][downRow];
                tileNum2 = gp.tileManager.mapTileNum[leftCol][topRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision){
                    collisionOn = true;
                }
            }
            case RIGHT -> {
                rightCol = (rightX + speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[rightCol][downRow];
                tileNum2 = gp.tileManager.mapTileNum[rightCol][topRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision){
                    collisionOn = true;
                }
            }
        }
    }

    public void entityCollisionCheck(ArrayList<Entity> target) {

        for (Entity entity: target) {

            if (entity != this) {

                int defaultX = collisionArea.x;
                int defaultY = collisionArea.y;
                int defaultEntityX = entity.collisionArea.x;
                int defaultEntityY = entity.collisionArea.y;

                collisionArea.x = worldX + collisionArea.x;
                collisionArea.y = worldY + collisionArea.y;

                entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
                entity.collisionArea.y = entity.worldY + entity.collisionArea.y;

                switch (direction) {

                    case UP -> {
                        collisionArea.y -= speed;
                        if (collisionArea.intersects(entity.collisionArea)) {
                            if (entity.collision) {
                                collisionOn = true;
                            }
                            if (this instanceof Player) {

                                if (entity instanceof WeaponEntity) ((Player)this).pickUpWeapon((WeaponEntity)entity);
                                if (entity instanceof ArmorEntity) ((Player)this).pickUpArmor((ArmorEntity)entity);
                                if (entity instanceof ManaPotionEntity) ((Player)this).pickUpMpPotion((ManaPotionEntity) entity);
                                if (entity instanceof HealthPotionEntity) ((Player)this).pickUpHpPotion((HealthPotionEntity) entity);
                            }
                        }
                    }

                    case DOWN -> {
                        collisionArea.y += speed;
                        if (collisionArea.intersects(entity.collisionArea)) {
                            if (entity.collision) {
                                collisionOn = true;
                            }
                            if (this instanceof Player) {

                                if (entity instanceof WeaponEntity) ((Player)this).pickUpWeapon((WeaponEntity)entity);
                                if (entity instanceof ArmorEntity) ((Player)this).pickUpArmor((ArmorEntity)entity);
                                if (entity instanceof ManaPotionEntity) ((Player)this).pickUpMpPotion((ManaPotionEntity) entity);
                                if (entity instanceof HealthPotionEntity) ((Player)this).pickUpHpPotion((HealthPotionEntity) entity);
                            }
                        }
                    }

                    case LEFT -> {
                        collisionArea.x -= speed;
                        if (collisionArea.intersects(entity.collisionArea)) {
                            if (entity.collision) {
                                collisionOn = true;
                            }
                            if (this instanceof Player) {

                                if (entity instanceof WeaponEntity) ((Player)this).pickUpWeapon((WeaponEntity)entity);
                                if (entity instanceof ArmorEntity) ((Player)this).pickUpArmor((ArmorEntity)entity);
                                if (entity instanceof ManaPotionEntity) ((Player)this).pickUpMpPotion((ManaPotionEntity) entity);
                                if (entity instanceof HealthPotionEntity) ((Player)this).pickUpHpPotion((HealthPotionEntity) entity);
                            }
                        }
                    }

                    case RIGHT -> {
                        collisionArea.x += speed;
                        if (collisionArea.intersects(entity.collisionArea)) {
                            if (entity.collision) {
                                collisionOn = true;
                            }
                            if (this instanceof Player) {

                                if (entity instanceof WeaponEntity) ((Player)this).pickUpWeapon((WeaponEntity)entity);
                                if (entity instanceof ArmorEntity) ((Player)this).pickUpArmor((ArmorEntity)entity);
                                if (entity instanceof ManaPotionEntity) ((Player)this).pickUpMpPotion((ManaPotionEntity) entity);
                                if (entity instanceof HealthPotionEntity) ((Player)this).pickUpHpPotion((HealthPotionEntity) entity);
                            }
                        }
                    }
                }

                collisionArea.x = defaultX;
                collisionArea.y = defaultY;
                entity.collisionArea.x = defaultEntityX;
                entity.collisionArea.y = defaultEntityY;
            }
        }

    }

    public NPC npcCollisionCheck(ArrayList<NPC> npcs) {

        NPC npcNear = null;

        for (NPC npc: npcs){

            int defaultX = collisionArea.x;
            int defaultY = collisionArea.y;
            int defaultEntityX = npc.collisionArea.x;
            int defaultEntityY = npc.collisionArea.y;

            collisionArea.x = worldX + collisionArea.x;
            collisionArea.y = worldY + collisionArea.y;

            npc.collisionArea.x = npc.worldX + npc.collisionArea.x;
            npc.collisionArea.y = npc.worldY + npc.collisionArea.y;

            switch (direction){

                case UP -> {
                    collisionArea.y -= speed;
                    if (collisionArea.intersects(npc.collisionArea)){
                        if (npc.collision){
                            npcNear = npc;
                        }
                    }
                }

                case DOWN -> {
                    collisionArea.y += speed;
                    if (collisionArea.intersects(npc.collisionArea)){
                        npcNear = npc;
                    }
                }

                case LEFT -> {
                    collisionArea.x -= speed;
                    if (collisionArea.intersects(npc.collisionArea)){
                        npcNear = npc;
                    }
                }

                case RIGHT -> {
                    collisionArea.x += speed;
                    if (collisionArea.intersects(npc.collisionArea)){
                        npcNear = npc;
                    }
                }
            }

            collisionArea.x = defaultX;
            collisionArea.y = defaultY;
            npc.collisionArea.x = defaultEntityX;
            npc.collisionArea.y = defaultEntityY;

        }

        return npcNear;

    }

}
