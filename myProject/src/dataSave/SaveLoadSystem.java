package dataSave;

import entity.*;
import main.GamePanel;
import objects.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveLoadSystem {

    GamePanel gp;

    public SaveLoadSystem(GamePanel gp) {
        this.gp = gp;
    }

    public void save() {

        try {

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.sav"));

            DataStorage ds = new DataStorage();

            // Stats
            ds.exp = gp.player.exp;
            ds.neededExp = gp.player.neededExp;
            ds.lvl = gp.player.lvl;
            ds.maxMP = gp.player.maxMP;
            ds.curMP = gp.player.curMP;
            ds.maxHP = gp.player.maxHP;
            ds.curHP = gp.player.curHP;
            ds.defence = gp.player.defence;
            ds.gold = gp.player.gold;
            ds.strength = gp.player.strength;
            ds.sorcery = gp.player.sorcery;
            ds.vitality = gp.player.vitality;
            ds.spellPower = gp.player.spellPower;
            ds.skillPoints = gp.player.skillPoints;

            // Items
            ds.inventoryClasses = new ArrayList<>();
            ds.displayNames = new ArrayList<>();
            ds.names = new ArrayList<>();
            ds.weaponStats = new ArrayList<>();

            for (Item it: gp.player.inventory) {

                ds.inventoryClasses.add(it.getClass());

                if (it instanceof Weapon) {
                        ds.weaponStats.add(((Weapon) it).damage);
                        ds.names.add(it.name);
                        ds.displayNames.add(it.displayName);
                        if (it == gp.player.weapon){
                            ds.weaponIndex = ds.inventoryClasses.size() - 1;
                        }
                } else if (it instanceof Armor) {
                        ds.weaponStats.add(((Armor) it).defence);
                        ds.names.add(it.name);
                        ds.displayNames.add(it.displayName);
                    if (it == gp.player.armor){
                        ds.armorIndex = ds.inventoryClasses.size() - 1;
                    }
                }

            }

            // Player position
            ds.x = gp.player.worldX;
            ds.y = gp.player.worldY;

            // Entities
            for (Entity entity: gp.entityList) {

                if (entity instanceof Monster) {
                    ds.monsterNames.add(((Monster) entity).name);
                    ds.monsterX.add(entity.worldX);
                    ds.monsterY.add(entity.worldY);
                }else if (entity instanceof Merchant) {
                    ds.merchantNames.add(((Merchant) entity).name);
                    ds.merchantX.add(entity.worldX);
                    ds.merchantY.add(entity.worldY);
                } else if (entity instanceof NPC) {
                    ds.npcNames.add(((NPC) entity).name);
                    ds.npcX.add(entity.worldX);
                    ds.npcY.add(entity.worldY);
                } else if (entity instanceof WeaponEntity) {
                    ds.wpEntDisplayNames.add(((WeaponEntity) entity).displayName);
                    ds.wpEntNames.add(((WeaponEntity) entity).name);
                    ds.wpEntDmg.add(((WeaponEntity) entity).damage);
                    ds.wpEntX.add(entity.worldX);
                    ds.wpEntY.add(entity.worldY);
                } else if (entity instanceof ArmorEntity) {
                    ds.armEntDisplayNames.add(((ArmorEntity) entity).displayName);
                    ds.armEntNames.add(((ArmorEntity) entity).name);
                    ds.armEntDef.add(((ArmorEntity) entity).defence);
                    ds.armEntX.add(entity.worldX);
                    ds.armEntY.add(entity.worldY);
                } else if (entity instanceof HealthPotionEntity) {
                    ds.HpEntX.add(entity.worldX);
                    ds.HpEntY.add(entity.worldY);
                } else if (entity instanceof ManaPotionEntity) {
                    ds.MpEntX.add(entity.worldX);
                    ds.MpEntY.add(entity.worldY);
                }

            }

            oos.writeObject(ds);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void load() {

        try {

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.sav"));

            DataStorage ds = (DataStorage) ois.readObject();

            // Stats
            gp.player.lvl = ds.lvl;
            gp.player.exp = ds.exp;
            gp.player.gold = ds.gold;
            gp.player.neededExp = ds.neededExp;
            gp.player.maxMP = ds.maxMP;
            gp.player.curMP = ds.curMP;
            gp.player.maxHP = ds.maxHP;
            gp.player.curHP = ds.curHP;
            gp.player.defence = ds.defence;
            gp.player.setDefence();
            gp.player.strength = ds.strength;
            gp.player.setAttack();
            gp.player.vitality = ds.vitality;
            gp.player.sorcery = ds.sorcery;
            gp.player.spellPower = ds.spellPower;
            gp.player.skillPoints = ds.skillPoints;

            // Items
            gp.player.inventory.clear();
            int j = 0;

            for (Class cl: ds.inventoryClasses) {

                if (cl.equals(HealthPotion.class)) {
                    gp.player.inventory.add(new HealthPotion(gp));
                } else  if (cl.equals(ManaPotion.class)) {
                    gp.player.inventory.add(new ManaPotion(gp));
                } else if (cl.equals(Weapon.class)) {
                    int damage = ds.weaponStats.get(j);
                    String name = ds.names.get(j);
                    String displayName = ds.displayNames.get(j);
                    gp.player.inventory.add(new Weapon(gp, damage, name, displayName));
                    j++;
                } else {
                    int defence = ds.weaponStats.get(j);
                    String name = ds.names.get(j);
                    String displayName = ds.displayNames.get(j);
                    gp.player.inventory.add(new Armor(gp, defence, name, displayName));
                    j++;
                }

            }

            // Equipment
            gp.player.weapon = (Weapon)gp.player.inventory.get(ds.weaponIndex);
            gp.player.armor = (Armor) gp.player.inventory.get(ds.armorIndex);

            // Player position
            gp.player.worldY = ds.y;
            gp.player.worldX = ds.x;

            // Entity lists
            gp.monsterList.clear();
            gp.projectileList.clear();
            gp.createList.clear();
            gp.NPCList.clear();
            gp.entityList.clear();

            for (int i = 0; i < ds.npcNames.size(); i++) {
                gp.objSetter.setNPC(ds.npcNames.get(i), ds.npcX.get(i), ds.npcY.get(i));
            }
            for (int i = 0; i < ds.merchantNames.size(); i++) {
                gp.objSetter.setMerchant(ds.merchantNames.get(i), ds.merchantX.get(i), ds.merchantY.get(i));
            }
            for (int i = 0; i < ds.monsterNames.size(); i++) {
                gp.objSetter.setMonster(ds.monsterNames.get(i), ds.monsterX.get(i), ds.monsterY.get(i));
            }
            for (int i = 0; i < ds.wpEntNames.size(); i++) {
                gp.objSetter.setWeapon(ds.wpEntNames.get(i), ds.wpEntDisplayNames.get(i), ds.wpEntX.get(i),
                        ds.wpEntY.get(i), ds.wpEntDmg.get(i));
            }
            for (int i = 0; i < ds.armEntNames.size(); i++) {
                gp.objSetter.setArmor(ds.armEntNames.get(i), ds.armEntDisplayNames.get(i), ds.armEntX.get(i),
                        ds.armEntY.get(i), ds.armEntDef.get(i));
            }
            for (int i = 0; i < ds.HpEntX.size(); i++) {
                gp.objSetter.setHP(ds.HpEntX.get(i), ds.HpEntY.get(i));
            }
            for (int i = 0; i < ds.MpEntX.size(); i++) {
                gp.objSetter.setMP(ds.MpEntX.get(i), ds.MpEntY.get(i));
            }


        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
