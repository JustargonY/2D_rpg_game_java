package main;

import entity.*;

import java.util.Random;

public class ObjectSetter {

    GamePanel gp;

    public ObjectSetter(GamePanel gp) {
        this.gp = gp;

    }

    public void setNPCList() {

        setNPC("hero", 25 * gp.tileSize, 13 * gp.tileSize);
        setMerchant("sprite", 37 * gp.tileSize, 7 * gp.tileSize);

    }

    public void setNPC(String name, int x, int y) {

        NPC npc = new NPC(gp, name, x, y);
        gp.NPCList.add(npc);
        gp.createList.add(npc);

    }

    public void setMerchant(String name, int x, int y) {

        Merchant merchant = new Merchant(gp, name, x, y);
        gp.NPCList.add(merchant);
        gp.createList.add(merchant);

    }

    public void setMonsterList() {

        setMonster("slime", 22 * gp.tileSize, 20 * gp.tileSize);
        setMonster("slime", 22 * gp.tileSize, 25 * gp.tileSize);
        setMonster("slime", 15 * gp.tileSize, 40 * gp.tileSize);
        setMonster("slime", 30 * gp.tileSize, 42 * gp.tileSize);
        setMonster("slime", 26 * gp.tileSize, 35 * gp.tileSize);
        randomMonsterSpawn(2);

    }

    public void setMonster(String name, int x, int y){

        Monster monster = new Monster(gp, name, x, y);
        gp.monsterList.add(monster);
        gp.createList.add(monster);

    }

    public void setItemList(){

        setWeapon("legendary_sword", "Legendary NKVD's Finka", 11 * gp.tileSize, 33 * gp.tileSize, 5);
        setArmor("holy_armor", "Blessed Armor", 12 * gp.tileSize, 30 * gp.tileSize, 3);
        setMP(38* gp.tileSize, 8 * gp.tileSize);
        setHP(9* gp.tileSize, 14 * gp.tileSize);

    }

    public void setWeapon(String name, String displayName, int x, int y, int damage) {

        WeaponEntity weapon = new WeaponEntity(gp, name, displayName, x, y, damage);
        gp.createList.add(weapon);

    }

    public void setArmor(String name, String displayName, int x, int y, int defence) {

        ArmorEntity armor = new ArmorEntity(gp, name, displayName, x, y, defence);
        gp.createList.add(armor);

    }

    public void setMP(int x, int y) {

        ManaPotionEntity mp = new ManaPotionEntity(gp, x, y);
        gp.createList.add(mp);

    }

    public void setHP(int x, int y) {

        HealthPotionEntity hp = new HealthPotionEntity(gp, x, y);
        gp.createList.add(hp);

    }

    public void randomMonsterSpawn(int times) {

        Random r = new Random();

        for (int i = 0; i < times; i++) {

            int type = r.nextInt(2);
            String name = "slime";

            switch (type) {
                case 0 -> name = "slime";
                case 1 -> name = "blue_slime";
            }

            int x = r.nextInt(7, 41) + 1;
            int y = r.nextInt(4, 44) + 1;
            boolean f = true;

            while (gp.tileManager.tiles[gp.tileManager.mapTileNum[x][y]].collision && f) {
                x = r.nextInt(7, 41) + 1;
                y = r.nextInt(4, 44) + 1;

                Monster monster = new Monster(gp, name, x * gp.tileSize, y * gp.tileSize);
                monster.collisionCheck();
                monster.entityCollisionCheck(gp.entityList);
                monster.playerCollisionCheck();
                f = monster.collisionOn;
            }

            setMonster(name, x * gp.tileSize, y * gp.tileSize);

        }

    }
}
