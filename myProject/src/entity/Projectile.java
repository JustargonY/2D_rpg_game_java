package entity;

import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class Projectile extends Entity {

    public int damage;
    public boolean remove;

    public Projectile(GamePanel gp, int x, int y) {

        this.gp = gp;
        worldX = x;
        worldY = y;
        collisionArea = new Rectangle(3, 6, 42, 36);
        speed = 6;
        direction = gp.player.direction;
        damage = 10 * gp.player.spellPower;
        loadProjectileImage();
        collisionOn = false;
        remove = false;

    }

    private void loadProjectileImage() {

        up1 = loadImage("/projectile/up_1.png");
        up2 = loadImage("/projectile/up_2.png");
        down1 = loadImage("/projectile/down_1.png");
        down2 = loadImage("/projectile/down_2.png");
        left1 = loadImage("/projectile/left_1.png");
        left2 = loadImage("/projectile/left_2.png");
        right1 = loadImage("/projectile/right_1.png");
        right2 = loadImage("/projectile/right_2.png");

    }

    @Override
    public void update() {

        super.update();

        collisionCheck();
        entityCollisionCheck(gp.entityList);

        if (!collisionOn) {
            move();
        } else {
            gp.playSoundEffect(7);
            remove = true;
        }

    }

    @Override
    public void entityCollisionCheck(ArrayList<Entity> target) {

        for (Entity entity: target){

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
                        if (entity instanceof Monster) {
                            ((Monster) entity).decreaseHP(damage);
                        }
                    }
                }

                case DOWN -> {
                    collisionArea.y += speed;
                    if (collisionArea.intersects(entity.collisionArea)) {
                        if (entity.collision) {
                            collisionOn = true;
                        }
                        if (entity instanceof Monster) {
                            ((Monster) entity).decreaseHP(damage);
                        }
                    }
                }

                case LEFT -> {
                    collisionArea.x -= speed;
                    if (collisionArea.intersects(entity.collisionArea)) {
                        if (entity.collision) {
                            collisionOn = true;
                        }
                        if (entity instanceof Monster) {
                            ((Monster) entity).decreaseHP(damage);
                        }
                    }
                }

                case RIGHT -> {
                    collisionArea.x += speed;
                    if (collisionArea.intersects(entity.collisionArea)) {
                        if (entity.collision) {
                            collisionOn = true;
                        }
                        if (entity instanceof Monster) {
                            ((Monster) entity).decreaseHP(damage);
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

    private void move() {

        switch (direction) {

            case UP -> worldY -= speed;
            case DOWN -> worldY += speed;
            case LEFT -> worldX -= speed;
            case RIGHT -> worldX += speed;

        }

    }
}
