package entity;

import main.GamePanel;
import main.KeyL;
import objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity{

    // Exp parameters
    public int exp;
    public int neededExp;
    public int lvl;

    // Stat parameters
    public int maxMP;
    public int curMP;
    public int attack;
    public int resist;
    public int strength;
    public int vitality;
    public int defence;
    public int spellPower;
    public int sorcery;
    public int skillPoints;
    public int gold;

    // Equip parameters
    public Weapon weapon;
    public Armor armor;

    // Attack system
    boolean attacking;
    boolean attacked;

    // Key Listener
    KeyL keyL;

    // Collision parameters
    public Rectangle attackCollision;

    // Draw parameters
    public int screenX, screenY;
    boolean dead;

    // Inventory
    public ArrayList<Item> inventory;

    public Player(GamePanel gp, KeyL keyL){
        this.gp = gp;
        this.keyL = keyL;

        worldX = 24 * gp.tileSize;
        worldY = 12 * gp.tileSize;
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        speed = 3;
        direction = Directions.DOWN;

        loadPlayerImage();
        collisionArea = new Rectangle(10, 12, 28, 36);
        collision = true;

        exp = 0;
        neededExp = 5;
        gold = 0;
        skillPoints = 0;

        strength = 5;
        vitality = 5;
        defence = 0;
        spellPower = 1;
        sorcery = 5;

        maxHP = 20 * vitality;
        curHP = maxHP;

        maxMP = 5 * sorcery;
        curMP = maxMP;

        weapon = new Weapon(gp, 0, "simple_sword", "Simple Sword");
        armor = new Armor(gp, 0, "simple_armor", "Simple Armor");
        attack = weapon.damage + strength;
        resist = defence + armor.defence;

        invincibleCounter = 0;
        attacking = false;
        attacked = false;

        attackCollision = new Rectangle();
        dead = false;

        inventory = new ArrayList<>();
        inventory.add(weapon);
        inventory.add(armor);
        inventory.add(new HealthPotion(gp));
        inventory.add(new HealthPotion(gp));
        inventory.add(new ManaPotion(gp));
    }

    public void loadPlayerImage(){

        up1 = loadImage("/player/hero_up_1.png");
        up2 = loadImage("/player/hero_up_2.png");
        down1 = loadImage("/player/hero_down_1.png");
        down2 = loadImage("/player/hero_down_2.png");
        left1 = loadImage("/player/hero_left_1.png");
        left2 = loadImage("/player/hero_left_2.png");
        right1 = loadImage("/player/hero_right_1.png");
        right2 = loadImage("/player/hero_right_2.png");
        attackUp1 = loadImage("/player/hero_attack_up_1.png");
        attackUp2 = loadImage("/player/hero_attack_up_2.png");
        attackDown1 = loadImage("/player/hero_attack_down_1.png");
        attackDown2 = loadImage("/player/hero_attack_down_2.png");
        attackLeft1 = loadImage("/player/hero_attack_left_1.png");
        attackLeft2 = loadImage("/player/hero_attack_left_2.png");
        attackRight1 = loadImage("/player/hero_attack_right_1.png");
        attackRight2 = loadImage("/player/hero_attack_right_2.png");

    }

    public void setAttack() {
        attack = strength + weapon.damage;
    }

    public void setDefence() {
        resist = defence + armor.defence;
    }

    public void setHP() {
        int d = 20 * vitality - maxHP;
        maxHP = 20 * vitality;
        increaseHP(d);
    }

    public void setMP() {
        int d = 5 * sorcery - maxMP;
        maxMP = 5 * sorcery;
        increaseMP(d);
    }

    @Override
    public void update() {


        if (keyL.pressedUp) {
            direction = Directions.UP;
        }
        if (keyL.pressedDown) {
            direction = Directions.DOWN;
        }
        if (keyL.pressedLeft) {
            direction = Directions.LEFT;
        }
        if (keyL.pressedRight) {
            direction = Directions.RIGHT;
        }

        if (attacking) {

            attacking();
            setAttackCollision();
            damageCheck();

        } else {

            attacked = false;
            collisionOn = false;
            collisionCheck();
            entityCollisionCheck(gp.entityList);

            //can be bugged in textures
            if (keyL.pressedUp || keyL.pressedDown || keyL.pressedLeft || keyL.pressedRight) {

                if (!collisionOn) {
                    if (keyL.pressedUp) {
                        worldY -= speed;
                    }
                    if (keyL.pressedDown) {
                        worldY += speed;
                    }
                    if (keyL.pressedLeft) {
                        worldX -= speed;
                    }
                    if (keyL.pressedRight) {
                        worldX += speed;
                    }
                }
            }


            spriteCounter ++;
            if (spriteCounter > 18) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }


        }

        if (invincibleCounter > 0) {

            invincibleCounter --;

        }
    }

    @Override
    public void draw(Graphics2D g2){

        BufferedImage image = getImage();

        if (invincibleCounter != 0) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
        }

        if (!attacking) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        } else {
            switch (direction) {

                case UP -> g2.drawImage(image, screenX, screenY - gp.tileSize, gp.tileSize, 2*gp.tileSize, null);
                case DOWN -> g2.drawImage(image, screenX, screenY, gp.tileSize, 2*gp.tileSize, null);
                case RIGHT -> g2.drawImage(image, screenX, screenY, 2*gp.tileSize, gp.tileSize, null);
                case LEFT -> g2.drawImage(image, screenX - gp.tileSize, screenY, 2*gp.tileSize, gp.tileSize, null);

            }
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

    }

    @Override
    public BufferedImage getImage() {

        BufferedImage image = null;

        if (attacking) {

            switch (direction){
                case UP -> {if (spriteNum == 1){
                        image = attackUp1;
                    }
                    if (spriteNum == 2){
                        image = attackUp2;
                    }
                }
                case DOWN -> {if (spriteNum == 1){
                    image = attackDown1;
                }
                    if (spriteNum == 2){
                        image = attackDown2;
                    }
                }
                case LEFT -> {if (spriteNum == 1){
                    image = attackLeft1;
                }
                    if (spriteNum == 2){
                        image = attackLeft2;
                    }
                }
                case RIGHT -> {if (spriteNum == 1){
                    image = attackRight1;
                }
                    if (spriteNum == 2){
                        image = attackRight2;
                    }
                }
            }

        } else {
            image = super.getImage();
        }

        return image;

    }

    public void interactNPC(NPC npc){

        gp.gameState = GamePanel.GameState.DIALOG;
        npc.speak();

    }

    public void contactMonster(Monster monster) {

        if (invincibleCounter == 0) {
            int dmg;
            if (resist >= monster.attack) {
                dmg = 1;
            } else {
                dmg = monster.attack - resist;
            }
            decreaseHP(dmg);
            invincibleCounter = 30;
            gp.playSoundEffect(5);
        }

    }

    public void attackMonster(Monster monster) {

        if (monster.invincibleCounter == 0) {
            monster.decreaseHP(attack);
        }

    }

    public void attack() {

        if (!attacking) {

            attacking = true;
            spriteCounter = 0;
            gp.playSoundEffect(2);

        }

    }

    public void attacking() {

        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        } else if(spriteCounter <= 20) {
            spriteNum = 2;
        } else {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    public void damageCheck() {

        for (Monster monster: gp.monsterList) {

            Rectangle checkZone = new Rectangle(worldX + attackCollision.x, worldY + attackCollision.y,
                    attackCollision.width, attackCollision.height);

            int defaultX = monster.collisionArea.x;
            int defaultY = monster.collisionArea.y;

            monster.collisionArea.x = monster.worldX + monster.collisionArea.x;
            monster.collisionArea.y = monster.worldY + monster.collisionArea.y;

            if (checkZone.intersects(monster.collisionArea) && !attacked){

                attackMonster(monster);

            }

            monster.collisionArea.x = defaultX;
            monster.collisionArea.y = defaultY;

        }

    }

    private void setAttackCollision() {

        switch (direction) {

            case UP -> {
                attackCollision.x = 21;
                attackCollision.y = -30;
                attackCollision.width = 24;
                attackCollision.height = 30;
            }
            case DOWN -> {
                attackCollision.x = 6;
                attackCollision.y = 45;
                attackCollision.width = 24;
                attackCollision.height = 30;
            }
            case LEFT -> {
                attackCollision.x = -24;
                attackCollision.y = 24;
                attackCollision.width = 30;
                attackCollision.height = 24;
            }
            case RIGHT -> {
                attackCollision.x = 39;
                attackCollision.y = 24;
                attackCollision.width = 30;
                attackCollision.height = 24;
            }

        }

    }

    public void throwProjectile() {

        if (curMP >= 5) {

            int x = worldX, y = worldY;

            switch (direction) {

                case UP -> y = worldY - gp.tileSize;
                case DOWN -> y = worldY + gp.tileSize;
                case LEFT -> x = worldX - gp.tileSize;
                case RIGHT -> x = worldX + gp.tileSize;

            }

            Projectile projectile = new Projectile(gp, x, y);
            gp.projectileList.add(projectile);

            curMP -= 5;

        }

    }

    public void decreaseHP(int value) {

        if (curHP >= value) {
            curHP -= value;
        } else {
            curHP = 0;
        }

        if (curHP == 0) {

            dead = true;
            dying = true;
            dyingFrame = 0;
            gp.gameOver();

        }

    }

    public void increaseHP(int value) {

        curHP += value;

        if (curHP > maxHP) {
            curHP = maxHP;
        }

    }

    public void increaseMP(int value) {

        curMP += value;

        if (curMP > maxMP) {
            curMP = maxMP;
        }

    }

    public void increaseExp(int value) {

        exp += value;

        if (neededExp <= exp) {

            neededExp = neededExp + 5 + 2 * lvl;
            lvl++;
            skillPoints += 3;
            gp.playSoundEffect(8);
            gp.ui.addMessage("Level Up!");

        }

    }

    public void pickUpWeapon(WeaponEntity weaponEnt) {

        weaponEnt.curHP = 0;
        Weapon weapon = weaponEnt.createWeapon();
        gp.ui.addMessage("Picked up " + weapon.displayName);
        inventory.add(weapon);

    }

    public void pickUpArmor(ArmorEntity armorEnt) {

        armorEnt.curHP = 0;
        Armor armor = armorEnt.createArmor();
        gp.ui.addMessage("Picked up " + armor.displayName);
        inventory.add(armor);

    }

    public void pickUpHpPotion(HealthPotionEntity healthPotionEntity) {

        healthPotionEntity.curHP = 0;
        HealthPotion hp = healthPotionEntity.createPotion();
        gp.ui.addMessage("Picked up Health Potion");
        inventory.add(hp);

    }

    public void pickUpMpPotion(ManaPotionEntity manaPotionEntity) {

        manaPotionEntity.curHP = 0;
        ManaPotion mp = manaPotionEntity.createPotion();
        gp.ui.addMessage("Picked up Mana Potion");
        inventory.add(mp);

    }

}
