package main;

import entity.Merchant;
import objects.*;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

// Class is not optimized and very hard to read
public class UI {

    GamePanel gp;

    // Game Font
    Font workingFont;

    // Menu displays and controls parameters
    String[] titleButtons;
    int[] titleY;
    String[] menuButtons;
    int[] menuY;
    String[] gameOverButtons;
    int[] gameOverY;
    String[] characterParams;
    String[] settingsButtons;
    int[] settingsY;
    String[] lvlUpButtons;
    int[] lvlUpY;
    String[] lvlUpCostsDisplay;
    int[] lvlUpCosts;

    // Indexes for different game states
    public int menuIndex;
    public int inventoryIndex;
    public int tradeIndex;

    // Information to display during some game states
    public String currentDialog;
    public Merchant currentMerchant;

    // Message system
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public UI(GamePanel gp) {
        this.gp = gp;

        titleButtons = new String[]{"New Game", "Load Game", "Exit"};
        titleY = new int[]{5 * gp.tileSize, 6 * gp.tileSize, 7 * gp.tileSize};

        menuButtons = new String[]{"Continue", "Save Game", "Load Game", "Settings", "Exit"};
        int y = gp.screenHeight/2 - 3 * gp.tileSize;
        menuY = new int[]{y + 40, y + 40 + gp.tileSize, y + 40 + 2*gp.tileSize, y + 40 + 3*gp.tileSize, y + 40 + 4*gp.tileSize};

        gameOverButtons = new String[]{"New Game", "Load Game", "Exit"};
        gameOverY = new int[]{340, 340 + gp.tileSize, 340 + 2 * gp.tileSize};

        characterParams = new String[]{"Level", "Health", "Mana", "Exp", "Next Level Exp", "Skill Points", "Strength",
                "Vitality", "Defence", "Spell Power", "Sorcery", "Gold"};

        settingsButtons = new String[]{"Full Screen", "Music", "Sound Effects", "Back"};
        settingsY = new int[]{y + 90, y + 120, y + 150, y + 250};

        y = gp.screenHeight/2 - 4 * gp.tileSize;

        lvlUpButtons = new String[]{"Strength", "Vitality", "Defence", "Spell Power", "Sorcery"};
        lvlUpY = new int[]{y + 90, y + 120, y + 150, y + 180, y + 210};

        String s = "It will cost ";
        lvlUpCosts = new int[]{1, 1, 5, 3, 1};
        lvlUpCostsDisplay = new String[]{s + lvlUpCosts[0] + " SP.", s + lvlUpCosts[1] + " SP.", s + lvlUpCosts[2] + " SP.",
                s + lvlUpCosts[3] + " SP.", s + lvlUpCosts[4] + " SP."};

        menuIndex = 0;
        inventoryIndex = 0;

        currentDialog = "";

        try {
            InputStream is = getClass().getResourceAsStream("/font/MP16REG.ttf");
            workingFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g){

        g.setFont(workingFont);

        switch (gp.gameState){
            case GAME -> drawGameScreen(g);
            case PAUSE -> drawPauseScreen(g);
            case MAIN_MENU -> drawMenuScreen(g);
            case TITLE_SCREEN -> drawTitleScreen(g);
            case DIALOG -> drawDialogScreen(g);
            case SETTINGS -> drawSettingsScreen(g);
            case INVENTORY -> drawInventoryScreen(g);
            case GAME_OVER -> drawGameOverScreen(g);
            case LVL_UP -> drawLvlUpScreen(g);
            case TRADE -> drawTradeScreen(g);
        }

    }

    private void drawGameScreen(Graphics2D g){

        drawHPBar(g);
        drawMPBar(g);
    }

    private void drawHPBar(Graphics2D g) {

        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));
        g.drawRoundRect(50, 40, 130, 20, 5, 5);

        g.setColor(new Color(250, 54, 54, 100));
        g.fillRoundRect(50, 40, 130, 20, 5, 5);

        double k = (double)gp.player.curHP / (double)gp.player.maxHP;
        g.setColor(Color.RED);
        g.drawRoundRect(50, 40, (int)(130 * k), 20, 5, 5);
        g.fillRoundRect(50, 40, (int)(130 * k), 20, 5, 5);

    }

    private void drawMPBar(Graphics2D g) {

        g.setColor(new Color(0, 102, 204));
        g.setStroke(new BasicStroke(1));
        g.drawRoundRect(50, 65, 130, 10, 3, 3);

        g.setColor(new Color(0, 102, 204, 100));
        g.fillRoundRect(50, 65, 130, 10, 3, 3);

        double k = (double)gp.player.curMP / (double)gp.player.maxMP;
        g.setColor(new Color(0, 102, 204));
        g.drawRoundRect(50, 65, (int)(130 * k), 10, 3, 3);
        g.fillRoundRect(50, 65, (int)(130 * k), 10, 3, 3);

    }

    private void drawPauseScreen(Graphics2D g){
        drawGameScreen(g);

        g.setFont(g.getFont().deriveFont(Font.BOLD, 32F));
        g.setColor(Color.WHITE);

        drawCentralizedString("PAUSED", gp.screenHeight/2, g);
    }

    private void drawMenuScreen(Graphics2D g){
        drawGameScreen(g);

        int x = gp.screenWidth/2 - 150;
        int y = gp.screenHeight/2 - 3 * gp.tileSize;

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(x, y, 300, 5 * gp.tileSize + 30, 10, 10);

        g.setColor(Color.BLACK);
        g.fillRoundRect(x, y, 300, 5 * gp.tileSize + 30, 10, 10);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 24F));
        drawCentralizedString("Continue", y + 40, g);
        drawCentralizedString("Save Game", y + 40 + gp.tileSize, g);
        drawCentralizedString("Load Game", y + 40 + 2 * gp.tileSize, g);
        drawCentralizedString("Settings", y + 40 + 3 * gp.tileSize, g);
        drawCentralizedString("Exit", y + 40 + 4 * gp.tileSize, g);
        drawMenuMarker(g);
    }

    private void drawTitleScreen(Graphics2D g){
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, gp.screenWidth, gp.screenHeight);
        g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g.setFont(g.getFont().deriveFont(Font.BOLD, 32F));
        g.setColor(Color.WHITE);

        drawCentralizedString("Rolandskvadet", 2 * gp.tileSize, g);

        g.setFont(g.getFont().deriveFont(Font.PLAIN, 24F));
        drawCentralizedString("New Game", 5 * gp.tileSize, g);
        drawCentralizedString("Load Game", 6 * gp.tileSize, g);
        drawCentralizedString("Exit", 7 * gp.tileSize, g);
        drawMenuMarker(g);

    }

    private void drawDialogScreen(Graphics2D g){
        drawGameScreen(g);

        int DIALOG_SCREEN_WIDTH = 600;
        int DIALOG_SCREEN_HEIGHT = 150;
        int x = gp.screenWidth/2 - DIALOG_SCREEN_WIDTH / 2;
        int y = 25;

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(x, y, DIALOG_SCREEN_WIDTH, DIALOG_SCREEN_HEIGHT, 10, 10);

        g.setColor(new Color(0, 0, 0, 220));
        g.fillRoundRect(x, y, DIALOG_SCREEN_WIDTH, DIALOG_SCREEN_HEIGHT, 10, 10);

        g.setFont(g.getFont().deriveFont(Font.PLAIN, 22F));
        g.setColor(Color.WHITE);
        y += 28;
        x += 10;
        for (String s: currentDialog.split("\n")) {
            g.drawString(s, x, y);
            y += 22;
        }
    }

    private void drawSettingsScreen(Graphics2D g) {

        drawGameScreen(g);

        int x = gp.screenWidth/2 - 150;
        int y = gp.screenHeight/2 - 3 * gp.tileSize;

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(x, y, 300, 5 * gp.tileSize + 30, 10, 10);

        g.setColor(Color.BLACK);
        g.fillRoundRect(x, y, 300, 5 * gp.tileSize + 30, 10, 10);

        g.setColor(Color.WHITE);
        g.setFont(workingFont.deriveFont(Font.PLAIN, 24F));
        drawCentralizedString("Settings", y + 30, g);

        g.setFont(workingFont.deriveFont(Font.PLAIN, 22F));

        x += 30;
        y += 90;
        g.drawString("Full Screen", x, y);
        g.setStroke(new BasicStroke(3));
        int h = g.getFontMetrics().getHeight();
        g.drawRect(x + 180, y - h + 3, h - 5, h - 5);
        if (gp.isFullScreen) {
            g.fillRect(x + 180, y - h + 2, h - 5, h - 5);
        }
        y += 30;
        g.drawString("Music", x, y);
        g.drawRect(x + 180, y - h + 3, 4 * (h - 5), h - 5);
        g.fillRect(x + 180, y - h + 3, gp.music.volumeScale * (h - 5), h - 5);
        y += 30;
        g.drawString("Sound Effects", x, y);
        g.drawRect(x + 180, y - h + 3, 4 * (h - 5), h - 5);
        g.fillRect(x + 180, y - h + 3, gp.se.volumeScale * (h - 5), h - 5);
        y += 100;
        g.drawString("Back", x, y);

        drawMenuMarker(g);

    }

    private void drawInventoryScreen(Graphics2D g) {

        drawGameScreen(g);

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(30, 20, 320, 10*gp.tileSize, 10, 10);

        g.setColor(Color.BLACK);
        g.fillRoundRect(30, 20, 320, 10*gp.tileSize, 10, 10);

        g.setColor(Color.WHITE);
        g.setFont(workingFont.deriveFont(Font.PLAIN, 28F));

        int y = 50;

        for (String text: characterParams) {

            g.drawString(text, 40, y);
            y += 37;

        }

        drawParams(g);

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(12 * gp.tileSize, 20, 8 * gp.tileSize + 10, 6 * gp.tileSize + 10, 5, 10);

        g.setColor(Color.BLACK);
        g.fillRoundRect(12 * gp.tileSize, 20, 8 * gp.tileSize + 10, 6 * gp.tileSize + 10, 5, 10);

        drawItems(g);

        g.setColor(Color.WHITE);
        g.drawRoundRect(12*gp.tileSize + gp.tileSize * (inventoryIndex % 8) + 5, 25 + gp.tileSize * (inventoryIndex / 8),
                gp.tileSize, gp.tileSize, 5, 5);

        g.drawRoundRect(12 * gp.tileSize, 8 * gp.tileSize, 8 * gp.tileSize + 10, 2 * gp.tileSize + 20, 10, 10);
        g.setColor(Color.BLACK);
        g.fillRoundRect(12 * gp.tileSize, 8 * gp.tileSize, 8 * gp.tileSize + 10, 2 * gp.tileSize + 20, 10, 10);

        if (inventoryIndex < gp.player.inventory.size()) {

            g.setColor(Color.WHITE);
            g.setFont(workingFont.deriveFont(Font.PLAIN, 28F));
            Item it = gp.player.inventory.get(inventoryIndex);
            int j = 0;

            for (String s: it.description.split("\n")){
                g.drawString(s, 12 * gp.tileSize + 10, 8 * gp.tileSize + 35 + 28 * j);
                j++;
            }

        }

    }

    private void drawItems(Graphics2D g) {

        for (int i = 0; i < gp.player.inventory.size() && i < 48; i++) {

            Item it = gp.player.inventory.get(i);

            if (it == gp.player.weapon || it == gp.player.armor) {

                g.setColor(new Color(232, 217, 102));
                g.fillRoundRect(12*gp.tileSize + gp.tileSize * (i % 8) + 5, 25 + gp.tileSize * (i / 8),
                        gp.tileSize, gp.tileSize, 5, 5);

            }

            g.drawImage(it.image, 12*gp.tileSize + gp.tileSize * (i % 8) + 5, 25 + gp.tileSize * (i / 8),
                    gp.tileSize, gp.tileSize, null);
        }

    }

    public void drawTradeScreen(Graphics2D g) {

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(30, 20, 8 * gp.tileSize + 10, 6 * gp.tileSize + 10, 10, 10);
        g.drawRoundRect(30, 8 * gp.tileSize, 8 * gp.tileSize + 10, 2 * gp.tileSize + 20, 10, 10);

        g.setColor(Color.BLACK);
        g.fillRoundRect(30, 20, 8 * gp.tileSize + 10, 6 * gp.tileSize + 10, 10, 10);
        g.fillRoundRect(30, 8 * gp.tileSize, 8 * gp.tileSize + 10, 2 * gp.tileSize + 20, 10, 10);

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(12 * gp.tileSize, 20, 8 * gp.tileSize + 10, 6 * gp.tileSize + 10, 5, 10);

        g.setColor(Color.BLACK);
        g.fillRoundRect(12 * gp.tileSize, 20, 8 * gp.tileSize + 10, 6 * gp.tileSize + 10, 5, 10);

        g.setColor(Color.WHITE);
        g.setFont(workingFont.deriveFont(Font.PLAIN, 28F));
        g.drawString("Gold: " + gp.player.gold, 7 * gp.tileSize - 20, 6 * gp.tileSize + 20);

        drawItems(g);

        for (int i = 0; i < currentMerchant.inventory.size() && i < 48; i++) {

            Item it = currentMerchant.inventory.get(i);

            g.drawImage(it.image, 30 + gp.tileSize * (i % 8) + 5, 25 + gp.tileSize * (i / 8),
                    gp.tileSize, gp.tileSize, null);
        }

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(30 + gp.tileSize * (tradeIndex % 8) + 5, 25 + gp.tileSize * (tradeIndex / 8),
                gp.tileSize, gp.tileSize, 5, 5);

        if (tradeIndex < currentMerchant.inventory.size()) {

            g.setColor(Color.WHITE);
            g.setFont(workingFont.deriveFont(Font.PLAIN, 28F));
            Item it = currentMerchant.inventory.get(tradeIndex);
            int j = 0;

            for (String s: it.description.split("\n")){
                g.drawString(s, 40, 8 * gp.tileSize + 35 + 28 * j);
                j++;
            }
            String text = "Costs " + currentMerchant.costs[tradeIndex] + " gold.";
            g.drawString(text, 40, 8 * gp.tileSize + 35 + 28 * j);

        }

    }

    public void useItem(){

        if (inventoryIndex < gp.player.inventory.size()) {

            Item it = gp.player.inventory.get(inventoryIndex);

            switch (it.getClass().getSimpleName()){

                case "Armor" -> {
                    gp.player.armor = (Armor)it;
                    gp.player.setDefence();
                }

                case "Weapon" -> {
                    gp.player.weapon = (Weapon)it;
                    gp.player.setAttack();
                }

                case "ManaPotion" -> ((ManaPotion)it).drink();

                case "HealthPotion" -> ((HealthPotion)it).drink();

            }

        }

    }

    private void drawParams(Graphics2D g){

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 28F));

        String text = String.valueOf(gp.player.lvl);
        int y = 50;
        int x = 340 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = gp.player.curHP + "/" + gp.player.maxHP;
        y += 37;
        x = 340 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = gp.player.curMP + "/" + gp.player.maxMP;
        y += 37;
        x = 340 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = String.valueOf(gp.player.exp);
        y += 37;
        x = 340 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = String.valueOf(gp.player.neededExp);
        y += 37;
        x = 340 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = String.valueOf(gp.player.skillPoints);
        y += 37;
        x = 340 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = String.valueOf(gp.player.strength);
        y += 37;
        x = 340 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = String.valueOf(gp.player.vitality);
        y += 37;
        x = 340 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = String.valueOf(gp.player.defence);
        y += 37;
        x = 340 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = String.valueOf(gp.player.spellPower);
        y += 37;
        x = 340 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = String.valueOf(gp.player.sorcery);
        y += 37;
        x = 340 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = String.valueOf(gp.player.gold);
        y += 37;
        x = 340 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

    }

    private void drawLvlUpScreen(Graphics2D g) {

        drawGameScreen(g);

        int x = gp.screenWidth/2 - 150;
        int y = gp.screenHeight/2 - 4 * gp.tileSize;

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(x, y, 300, 5 * gp.tileSize + 50, 10, 10);

        g.setColor(Color.BLACK);
        g.fillRoundRect(x, y, 300, 5 * gp.tileSize + 50, 10, 10);

        g.setColor(Color.WHITE);
        g.setFont(workingFont.deriveFont(Font.PLAIN, 28F));
        drawCentralizedString("Lvl Up", y + 30, g);

        for (int i = 0; i < lvlUpButtons.length; i++){

            g.drawString(lvlUpButtons[i], x + 30, lvlUpY[i]);

        }

        g.drawString("Skill Points: " + gp.player.skillPoints, x + 30, y + 270);

        drawLvlParams(g);
        drawMenuMarker(g);


        y = gp.screenHeight/2 + gp.tileSize + 60;

        g.setColor(Color.BLACK);
        g.fillRoundRect(x, y, 300, gp.tileSize, 10, 10);

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(4));
        g.drawRoundRect(x, y, 300, gp.tileSize, 10, 10);

        g.setFont(workingFont.deriveFont(Font.PLAIN, 26F));
        g.drawString(lvlUpCostsDisplay[menuIndex], x + 10, y + gp.tileSize - 15);

    }

    private void drawLvlParams(Graphics2D g){

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 28F));

        String text = String.valueOf(gp.player.strength);
        int y = gp.screenHeight/2 - 4 * gp.tileSize + 90;
        int x = gp.screenWidth/2 + 140 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = String.valueOf(gp.player.vitality);
        y += 30;
        x = gp.screenWidth/2 + 140 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = String.valueOf(gp.player.defence);
        y += 30;
        x = gp.screenWidth/2 + 140 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = String.valueOf(gp.player.spellPower);
        y += 30;
        x = gp.screenWidth/2 + 140 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

        text = String.valueOf(gp.player.sorcery);
        y += 30;
        x = gp.screenWidth/2 + 140 - (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, x, y);

    }

    private void drawGameOverScreen(Graphics2D g) {

        g.setColor(new Color(0, 0, 0, 100));
        g.drawRect(0, 0, gp.screenWidth, gp.screenHeight);
        g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 40F));
        drawCentralizedString("Game Over", 200, g);

        g.setFont(g.getFont().deriveFont(Font.PLAIN, 28F));
        drawCentralizedString("New Game", 340, g);
        drawCentralizedString("Load Game", 340 + gp.tileSize, g);
        drawCentralizedString("Exit", 340 + 2 * gp.tileSize, g);

        drawMenuMarker(g);

    }

    public int getCentralizedX(String text, Graphics2D g){
        int textLength = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        return gp.screenWidth/2 - textLength/2;
    }

    public void drawCentralizedString(String text, int y, Graphics2D g){
        int textLength = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, gp.screenWidth/2 - textLength/2, y);
    }

    public void drawMenuMarker(Graphics2D g){

        String text;
        int x;
        int y;

        switch (gp.gameState){

            case TITLE_SCREEN -> {

                text = titleButtons[menuIndex];
                y = titleY[menuIndex];
                x = getCentralizedX(text, g) - gp.tileSize / 2;
                g.drawString(">", x, y);

            }

            case MAIN_MENU -> {

                text = menuButtons[menuIndex];
                y = menuY[menuIndex];
                x = getCentralizedX(text, g) - gp.tileSize / 2;
                g.drawString(">", x, y);

            }

            case GAME_OVER -> {

                text = gameOverButtons[menuIndex];
                y = gameOverY[menuIndex];
                x = getCentralizedX(text, g) - gp.tileSize / 2;
                g.drawString(">", x, y);

            }

            case SETTINGS -> {

                y = settingsY[menuIndex];
                x = gp.screenWidth/2 - 140;
                g.drawString(">", x, y);

            }

            case LVL_UP -> {

                y = lvlUpY[menuIndex];
                x = gp.screenWidth/2 - 140;
                g.drawString(">", x, y);

            }

        }

    }

    public void lvlUpAction(){

        switch (menuIndex) {

            case 0 -> {

                if (gp.player.skillPoints >= lvlUpCosts[menuIndex]) {
                    gp.player.skillPoints -= lvlUpCosts[menuIndex];
                    gp.player.strength++;
                    gp.player.setAttack();
                }

            }

            case 1 -> {

                if (gp.player.skillPoints >= lvlUpCosts[menuIndex]) {
                    gp.player.skillPoints -= lvlUpCosts[menuIndex];
                    gp.player.vitality++;
                    gp.player.setHP();
                }

            }

            case 2 -> {

                if (gp.player.skillPoints >= lvlUpCosts[menuIndex]) {
                    gp.player.skillPoints -= lvlUpCosts[menuIndex];
                    gp.player.defence++;
                    gp.player.setDefence();
                }

            }

            case 3 -> {

                if (gp.player.skillPoints >= lvlUpCosts[menuIndex]) {
                    gp.player.skillPoints -= lvlUpCosts[menuIndex];
                    gp.player.spellPower++;
                }

            }

            case 4 -> {

                if (gp.player.skillPoints >= lvlUpCosts[menuIndex]) {
                    gp.player.skillPoints -= lvlUpCosts[menuIndex];
                    gp.player.sorcery++;
                    gp.player.setMP();
                }

            }

        }

    }

    public void titleAction(){

        switch (menuIndex){

            case 0 -> {

                gp.gameState = GamePanel.GameState.GAME;
                gp.stopTrack();
                gp.playTrack(6);

            }

            case 1 ->{

                gp.sls.load();
                gp.gameState = GamePanel.GameState.GAME;
                gp.stopTrack();
                gp.playTrack(6);

            }

            case 2 -> {

                System.exit(0);

            }

        }

        menuIndex = 0;

    }

    public void menuAction(){

        switch (menuIndex){

            case 0 -> {

                gp.gameState = GamePanel.GameState.GAME;

            }

            case 1 -> {

                gp.sls.save();
                gp.gameState = GamePanel.GameState.GAME;
            }

            case 2 -> {

                gp.sls.load();
                gp.gameState = GamePanel.GameState.GAME;
            }

            case 3 -> {

                gp.gameState = GamePanel.GameState.SETTINGS;
            }

            case 4 -> {

                System.exit(0);

            }

        }

        menuIndex = 0;

    }

    public void gameOverAction() {

        switch (menuIndex) {

            case 0 -> {

                gp.reset();
                gp.gameState = GamePanel.GameState.GAME;
//                gp.playTrack(6);

            }

            case 1 ->{

                gp.sls.load();
                gp.gameState = GamePanel.GameState.GAME;

            }

            case 2 -> {

                System.exit(0);

            }

        }

    }

    public void settingsAction() {

        switch (menuIndex) {

            case 0 -> {

                if (gp.isFullScreen) {
                    gp.setDefaultScreen();
                } else {
                    gp.setFullScreen();
                }

            }

            case 1 -> {

                // Music

            }

            case 2 -> {

                // Sound Effects

            }

            case 3 -> {

                gp.gameState = GamePanel.GameState.MAIN_MENU;

            }

        }

        menuIndex = 0;

    }

    public void addMessage(String text) {

        message.add(text);
        messageCounter.add(0);

    }

    public void drawMessage(Graphics2D g) {

        int messageX = 30, messageY = 4 * gp.tileSize;
        g.setFont(workingFont.deriveFont(Font.PLAIN, 24F));
        g.setColor(Color.WHITE);

        for (int i = 0; i < message.size(); i++) {

            g.drawString(message.get(i), messageX, messageY);
            int counter = messageCounter.get(i) + 1;
            messageCounter.set(i, counter);
            messageY += 50;

            if (messageCounter.get(i) > 180) {
                if (message.iterator().hasNext()) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }

        }

    }

}
