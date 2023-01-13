package main;

import entity.Monster;
import entity.NPC;

import java.util.Random;

public class ObjectSetter {

    GamePanel gp;

    public ObjectSetter(GamePanel gp) {
        this.gp = gp;

    }

    public void setNPCList() {

        setNPC("hero", 25 * gp.tileSize, 13 * gp.tileSize);

    }

    public void setNPC(String name, int x, int y) {

        NPC npc = new NPC(gp, name, x, y);
        gp.NPCList.add(npc);
        gp.entityList.add(npc);

    }

    public void setMonsterList() {

        setMonster("slime", 22 * gp.tileSize, 13 * gp.tileSize);
        setMonster("slime", 22 * gp.tileSize, 17 * gp.tileSize);
        setMonster("slime", 15 * gp.tileSize, 40 * gp.tileSize);
        setMonster("slime", 30 * gp.tileSize, 42 * gp.tileSize);
        setMonster("slime", 26 * gp.tileSize, 35 * gp.tileSize);
        randomMonsterSpawn(2);

    }

    public void setMonster(String name, int x, int y){

        Monster monster = new Monster(gp, name, x, y);
        gp.monsterList.add(monster);
        gp.entityList.add(monster);

    }

    public void randomMonsterSpawn(int times) {

        Random r = new Random();

        for (int i = 0; i < times; i++) {

            int x = r.nextInt(7, 41) + 1;
            int y = r.nextInt(4, 44) + 1;

            while (gp.tileManager.tiles[gp.tileManager.mapTileNum[x][y]].collision) {
                x = r.nextInt(7, 41) + 1;
                y = r.nextInt(4, 44) + 1;
            }

            setMonster("slime", x * gp.tileSize, y * gp.tileSize);

        }

    }
}
