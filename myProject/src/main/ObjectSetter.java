package main;

import entity.Monster;
import entity.NPC;

public class ObjectSetter {

    GamePanel gp;

    public ObjectSetter(GamePanel gp) {
        this.gp = gp;

    }

    public void setNPCList() {

        setNPC("hero", 25 * gp.tileSize, 13 * gp.tileSize);

    }

    public void setNPC(String name, int x, int y) {
        gp.NPCList.add(new NPC(gp, name, x, y));
    }

    public void setMonsterList() {

        setMonster("slime", 22 * gp.tileSize, 13 * gp.tileSize);
        setMonster("slime", 22 * gp.tileSize, 17 * gp.tileSize);

    }

    public void setMonster(String name, int x, int y){
        gp.monsterList.add(new Monster(gp, name, x, y));
    }

    public void setEntityList() {

        gp.entityList.addAll(gp.NPCList);
        gp.entityList.addAll(gp.monsterList);

    }
}
