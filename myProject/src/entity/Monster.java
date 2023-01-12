package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class Monster extends Entity {

    String name;

    // Stat parameters
    int attack;

    // Move parameters
    int actionLock;

    public Monster(GamePanel gp, String name, int x, int y) {

        this.gp = gp;
        this.name = name;
        worldX = x;
        worldY = y;
        collision = true;
        collisionArea = new Rectangle();
        direction = Directions.DOWN;

        loadMonsterImage();
        loadStats();

        actionLock = 0;

    }

    private void loadMonsterImage() {

        up1 = loadImage("/monster/" + name + "_up_1.png");
        up2 = loadImage("/monster/" + name + "_up_2.png");
        down1 = loadImage("/monster/" + name + "_down_1.png");
        down2 = loadImage("/monster/" + name + "_down_2.png");
        left1 = loadImage("/monster/" + name + "_left_1.png");
        left2 = loadImage("/monster/" + name + "_left_2.png");
        right1 = loadImage("/monster/" + name + "_right_1.png");
        right2 = loadImage("/monster/" + name + "_right_2.png");

    }

    private void loadStats() {

        InputStream is = getClass().getResourceAsStream("/monster/" + name + ".txt");
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));

        String[] stats = bf.lines().toList().toArray(new String[0]);
        speed = Integer.parseInt(stats[0]);
        collisionArea.x = Integer.parseInt(stats[1]);
        collisionArea.y = Integer.parseInt(stats[2]);
        collisionArea.width = Integer.parseInt(stats[3]);
        collisionArea.height = Integer.parseInt(stats[4]);
        maxHP = Integer.parseInt(stats[5]);
        curHP = maxHP;
        attack = Integer.parseInt(stats[6]);

    }

    @Override
    public void update() {

        updateDirection();
        act();
        super.update();

        if (invincibleCounter > 0) {
            invincibleCounter--;
        }

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

            BufferedImage image = getImage();
            g.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);


            g.setColor(Color.RED);
            g.setStroke(new BasicStroke(2));
            g.drawRoundRect(x, y - 9, 48, 9, 2, 2);

            g.setColor(new Color(250, 54, 54, 100));
            g.fillRoundRect(x, y - 9, 48, 9, 2, 2);

            double k = (double)curHP / (double)maxHP;
            g.setColor(Color.RED);
            g.drawRoundRect(x, y - 9, (int)(48 * k), 9, 2, 2);
            g.fillRoundRect(x, y - 9, (int)(48 * k), 9, 2, 2);
        }

    }

    private void updateDirection() {

        if (actionLock == 100) {

            Random random = new Random();
            int r = random.nextInt(4) + 1;

            switch (r) {
                case 1 -> direction = Directions.UP;
                case 2 -> direction = Directions.DOWN;
                case 3 -> direction = Directions.LEFT;
                case 4 -> direction = Directions.RIGHT;
            }

            actionLock = 0;

        }

        actionLock++;

    }

    private void playerCollisionCheck() {

        int defaultX = collisionArea.x;
        int defaultY = collisionArea.y;
        int defaultEntityX = gp.player.collisionArea.x;
        int defaultEntityY = gp.player.collisionArea.y;

        collisionArea.x = worldX + collisionArea.x;
        collisionArea.y = worldY + collisionArea.y;

        gp.player.collisionArea.x = gp.player.worldX + gp.player.collisionArea.x;
        gp.player.collisionArea.y = gp.player.worldY + gp.player.collisionArea.y;

        switch (direction) {

            case UP -> {
                collisionArea.y -= speed;
                if (collisionArea.intersects(gp.player.collisionArea)) {
                    collisionOn = true;
                    gp.player.contactMonster(this);
                }
            }

            case DOWN -> {
                collisionArea.y += speed;
                if (collisionArea.intersects(gp.player.collisionArea)) {
                    collisionOn = true;
                    gp.player.contactMonster(this);
                }
            }

            case LEFT -> {
                collisionArea.x -= speed;
                if (collisionArea.intersects(gp.player.collisionArea)) {
                    collisionOn = true;
                    gp.player.contactMonster(this);
                }
            }

            case RIGHT -> {
                collisionArea.x += speed;
                if (collisionArea.intersects(gp.player.collisionArea)) {
                    collisionOn = true;
                    gp.player.contactMonster(this);
                }
            }
        }


        collisionArea.x = defaultX;
        collisionArea.y = defaultY;
        gp.player.collisionArea.x = defaultEntityX;
        gp.player.collisionArea.y = defaultEntityY;

    }

    private void act() {

        collisionOn = false;
        collisionCheck();
        entityCollisionCheck(gp.entityList);
        playerCollisionCheck();

        if (!collisionOn) {

            switch (direction) {

                case UP -> worldY -= speed;
                case DOWN -> worldY += speed;
                case LEFT -> worldX -= speed;
                case RIGHT -> worldX += speed;

            }

        }

    }

    public void decreaseHP(int value) {

        if (value >= curHP) {

            curHP = 0;

        } else {

            curHP -= value;
            invincibleCounter = 26;

        }

    }

}
