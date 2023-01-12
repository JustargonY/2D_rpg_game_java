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
    public int menuIndex;
    public String currentDialog;

    public UI(GamePanel gp) {
        this.gp = gp;
        titleButtons = new String[]{"New Game", "Load Game", "Exit"};
        titleY = new int[]{5 * gp.tileSize, 6 * gp.tileSize, 7 * gp.tileSize};

        menuButtons = new String[]{"Continue", "Save Game", "Load Game", "Settings", "Exit"};
        int y = gp.screenHeight/2 - 3 * gp.tileSize;
        menuY = new int[]{y + 40, y + 40 + gp.tileSize, y + 40 + 2*gp.tileSize, y + 40 + 3*gp.tileSize, y + 40 + 4*gp.tileSize};

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
        }

    }

    private void drawGameScreen(Graphics2D g){
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

        }

    }

    public void titleAction(){

        switch (menuIndex){

            case 0 -> {

                gp.gameState = GamePanel.GameState.GAME;
                gp.stopTrack();
                gp.playTrack(0);

            }

            case 1 ->{

                // Load Game

            }

            case 2 -> {

                System.exit(0);

            }

        }

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

                // Settings
            }

            case 4 -> {

                System.exit(0);

            }

        }

    }

}
