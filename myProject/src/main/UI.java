package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;
    Font workingFont;
    String[] titleButtons;
    int[] titleY;
    String[] menuButtons;
    int[] menuY;
    String[] gameOverButtons;
    int[] gameOverY;
    String[] characterParams;
    public int menuIndex;
    public String currentDialog;

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

        menuIndex = 0;

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

    }

    private void drawInventoryScreen(Graphics2D g) {

        drawGameScreen(g);

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(30, 20, 320, 10*gp.tileSize, 10, 10);

        g.setColor(Color.BLACK);
        g.fillRoundRect(30, 20, 320, 10*gp.tileSize, 10, 10);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 28F));

        int y = 50;
//        g.drawString(String.valueOf(gp.player.lvl), x, y);

        for (String text: characterParams) {

            g.drawString(text, 40, y);
            y += 37;

        }

        drawParams(g);

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

                // Load Game

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

                // Save Game
            }

            case 2 -> {

                // Load Game
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

                gp.gameState = GamePanel.GameState.GAME;
                gp.reset();
//                gp.playTrack(6);

            }

            case 1 ->{

                // Load Game

            }

            case 2 -> {

                System.exit(0);

            }

        }

    }

}
