package entity;

import main.GamePanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NPC extends Entity{

    String name;

    // Dialog parameters
    ArrayList<String> dialogPhrases;
    public int dialogStage;

    public NPC(GamePanel gp, String name, int x, int y) {
        this.gp = gp;
        this.name = name;

        worldX = x;
        worldY = y;

        speed = 2;
        direction = Directions.DOWN;
        collision = true;

        collisionArea = new Rectangle(10, 12, 28, 36);

        loadNPCImage();
        loadDialog();

        maxHP = 1;
        curHP = 1;
    }

    private void loadNPCImage() {

        up1 = loadImage("/npc/" + name + "_up_1.png");
        up2 = loadImage("/npc/" + name + "_up_2.png");
        down1 = loadImage("/npc/" + name + "_down_1.png");
        down2 = loadImage("/npc/" + name + "_down_2.png");
        left1 = loadImage("/npc/" + name + "_left_1.png");
        left2 = loadImage("/npc/" + name + "_left_2.png");
        right1 = loadImage("/npc/" + name + "_right_1.png");
        right2 = loadImage("/npc/" + name + "_right_2.png");

    }

    private void loadDialog() {

        InputStream is = getClass().getResourceAsStream("/dialogues/" + name +".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        dialogPhrases = new ArrayList<>();

        for (String s: br.lines().toList()) {

            s = s.replace("\\n", "\n");
            dialogPhrases.add(s);

        }

    }

    public void speak() {

        if (dialogStage >= dialogPhrases.size()) {
            dialogStage = 0;
            gp.gameState = GamePanel.GameState.GAME;
            return;
        }

        gp.ui.currentDialog = dialogPhrases.get(dialogStage);
        dialogStage++;

    }

}
