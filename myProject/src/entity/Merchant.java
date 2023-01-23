package entity;

import main.GamePanel;
import objects.*;

import java.awt.*;
import java.util.ArrayList;

public class Merchant extends NPC{

    // Trade parameters
    public ArrayList<Item> inventory;
    public int[] costs;

    public Merchant(GamePanel gp, String name, int x, int y) {
        this.gp = gp;
        this.name = name;

        worldX = x;
        worldY = y;

        speed = 2;
        direction = Directions.DOWN;
        collision = true;

        collisionArea = new Rectangle(10, 12, 28, 36);


        maxHP = 1;
        curHP = 1;

        loadNPCImage();
        super.loadDialog();
        loadInventory();

    }

    private void loadInventory() {

        inventory = new ArrayList<>();
        inventory.add(new HealthPotion(gp));
        inventory.add(new ManaPotion(gp));
        inventory.add(new Weapon(gp, 10, "harpoon_sword", "Harpoon Sword"));
        inventory.add(new Armor(gp, 10, "fire_armor", "Fire Armor"));

        costs = new int[]{5, 5, 20, 30};

    }

    @Override
    protected void loadNPCImage() {
        down1 = loadImage("/npc/" + name + "_0.png");
        down2 = loadImage("/npc/" + name + "_1.png");
    }

    @Override
    public void speak() {

        if (dialogStage >= dialogPhrases.size()) {
            dialogStage = 0;
            gp.gameState = GamePanel.GameState.TRADE;
            gp.ui.currentMerchant = this;
            return;
        }

        gp.ui.currentDialog = dialogPhrases.get(dialogStage);
        dialogStage++;

    }

    public void buy(int index) {

        if (gp.player.gold >= costs[index]) {

            gp.player.inventory.add(this.inventory.get(index));
            gp.player.gold -= costs[index];

        }

    }
}
