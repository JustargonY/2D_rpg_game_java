package dataSave;


import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {

    // Player Stats
    public int exp;
    public int neededExp;
    public int lvl;
    public int maxMP;
    public int curMP;
    public int maxHP;
    public int curHP;
    public int strength;
    public int vitality;
    public int defence;
    public int spellPower;
    public int sorcery;
    public int gold;
    public int skillPoints;

    //Equipment
    public int weaponIndex;
    public int armorIndex;

    // Inventory
    public ArrayList<Class> inventoryClasses;
    public ArrayList<Integer> weaponStats;
    public ArrayList<String> names;
    public ArrayList<String> displayNames;

    // Map position
    public int x;
    public int y;

    // Entity lists
    public ArrayList<String> monsterNames = new ArrayList<>();
    public ArrayList<Integer> monsterX = new ArrayList<>();
    public ArrayList<Integer> monsterY = new ArrayList<>();

    public ArrayList<String> npcNames = new ArrayList<>();
    public ArrayList<Integer> npcX = new ArrayList<>();
    public ArrayList<Integer> npcY = new ArrayList<>();

    public ArrayList<String> merchantNames = new ArrayList<>();
    public ArrayList<Integer> merchantX = new ArrayList<>();
    public ArrayList<Integer> merchantY = new ArrayList<>();

    public ArrayList<String> wpEntNames = new ArrayList<>();
    public ArrayList<String> wpEntDisplayNames = new ArrayList<>();
    public ArrayList<Integer> wpEntX = new ArrayList<>();
    public ArrayList<Integer> wpEntY = new ArrayList<>();
    public ArrayList<Integer> wpEntDmg = new ArrayList<>();

    public ArrayList<String> armEntNames = new ArrayList<>();
    public ArrayList<String> armEntDisplayNames = new ArrayList<>();
    public ArrayList<Integer> armEntX = new ArrayList<>();
    public ArrayList<Integer> armEntY = new ArrayList<>();
    public ArrayList<Integer> armEntDef = new ArrayList<>();

    public ArrayList<Integer> HpEntX = new ArrayList<>();
    public ArrayList<Integer> HpEntY = new ArrayList<>();
    public ArrayList<Integer> MpEntX = new ArrayList<>();
    public ArrayList<Integer> MpEntY = new ArrayList<>();

}
