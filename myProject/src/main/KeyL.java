package main;

import entity.NPC;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyL implements KeyListener {

    GamePanel gp;
    public boolean pressedUp, pressedDown, pressedLeft, pressedRight = false;

    public KeyL(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_BACK_SPACE) {
            if (gp.isFullScreen) {
                gp.setDefaultScreen();
            } else {
                gp.setFullScreen();
            }
        }

        switch (gp.gameState){

            case GAME -> {

                if (code == KeyEvent.VK_ESCAPE){
                    gp.gameState = GamePanel.GameState.MAIN_MENU;
                }

                if (code == KeyEvent.VK_SLASH){
                    gp.gameState = GamePanel.GameState.PAUSE;
                }

                // Moving part
                if (code == KeyEvent.VK_W){
                    pressedUp = true;
                }

                if (code == KeyEvent.VK_S){
                    pressedDown = true;
                }

                if (code == KeyEvent.VK_A){
                    pressedLeft = true;
                }

                if (code == KeyEvent.VK_D){
                    pressedRight = true;
                }

                // Dialog
                if (code == KeyEvent.VK_E){

                    NPC npcNear = gp.player.npcCollisionCheck(gp.NPCList);

                    if (npcNear != null) {
                        gp.player.interactNPC(npcNear);
                    }

                }

                // Game menus
                if (code == KeyEvent.VK_C){
                    gp.gameState = GamePanel.GameState.INVENTORY;
                }
                if (code == KeyEvent.VK_B){
                    gp.gameState = GamePanel.GameState.LVL_UP;
                }

            }

            case DIALOG -> {

                if (code == KeyEvent.VK_ESCAPE){
                    gp.gameState = GamePanel.GameState.GAME;
                }

                if (code == KeyEvent.VK_E){
                    NPC npcNear = gp.player.npcCollisionCheck(gp.NPCList);
                    npcNear.speak();
                }

            }

            case TRADE -> {

                if (code == KeyEvent.VK_ESCAPE){
                    gp.gameState = GamePanel.GameState.GAME;
                }

                if (code == KeyEvent.VK_W){
                    if (gp.ui.tradeIndex >= 8) {
                        gp.ui.tradeIndex -= 8;
                    }
                }

                if (code == KeyEvent.VK_S){
                    if (gp.ui.tradeIndex <= 39) {
                        gp.ui.tradeIndex += 8;
                    }
                }

                if (code == KeyEvent.VK_A){
                    if (gp.ui.tradeIndex > 0) {
                        gp.ui.tradeIndex--;
                    }
                }

                if (code == KeyEvent.VK_D){

                    if (gp.ui.tradeIndex < 47) {
                        gp.ui.tradeIndex++;
                    }
                }

                if (code == KeyEvent.VK_ENTER){

                    if (gp.ui.tradeIndex < gp.ui.currentMerchant.inventory.size()) {
                        gp.ui.currentMerchant.buy(gp.ui.tradeIndex);
                    }

                }

            }

            case PAUSE -> {

                if (code == KeyEvent.VK_SLASH){
                    gp.gameState = GamePanel.GameState.GAME;
                }

            }

            case MAIN_MENU -> {

                if (code == KeyEvent.VK_ENTER){
                    gp.ui.menuAction();
                }

                if (code == KeyEvent.VK_ESCAPE){
                    gp.gameState = GamePanel.GameState.GAME;
                }

                if (code == KeyEvent.VK_W){
                    gp.ui.menuIndex --;
                    if (gp.ui.menuIndex < 0){
                        gp.ui.menuIndex = 4;
                    }
                }

                if (code == KeyEvent.VK_S){
                    gp.ui.menuIndex ++;
                    if (gp.ui.menuIndex > 4){
                        gp.ui.menuIndex = 0;
                    }
                }

            }

            case TITLE_SCREEN -> {

                if (code == KeyEvent.VK_ENTER){
                    gp.ui.titleAction();
                }

                if (code == KeyEvent.VK_W){
                    gp.ui.menuIndex --;
                    if (gp.ui.menuIndex < 0){
                        gp.ui.menuIndex = 2;
                    }
                }

                if (code == KeyEvent.VK_S){
                    gp.ui.menuIndex ++;
                    if (gp.ui.menuIndex > 2){
                        gp.ui.menuIndex = 0;
                    }
                }

            }

            case GAME_OVER -> {

                if (code == KeyEvent.VK_ENTER){
                    gp.ui.gameOverAction();
                }

                if (code == KeyEvent.VK_W){
                    gp.ui.menuIndex --;
                    if (gp.ui.menuIndex < 0){
                        gp.ui.menuIndex = 2;
                    }
                }

                if (code == KeyEvent.VK_S){
                    gp.ui.menuIndex ++;
                    if (gp.ui.menuIndex > 2){
                        gp.ui.menuIndex = 0;
                    }
                }

            }

            case SETTINGS -> {

                if (code == KeyEvent.VK_ESCAPE){
                    gp.gameState = GamePanel.GameState.MAIN_MENU;
                }

                if (code == KeyEvent.VK_ENTER){
                    gp.ui.settingsAction();
                }

                if (code == KeyEvent.VK_W){
                    gp.ui.menuIndex --;
                    if (gp.ui.menuIndex < 0){
                        gp.ui.menuIndex = 3;
                    }
                }

                if (code == KeyEvent.VK_S){
                    gp.ui.menuIndex ++;
                    if (gp.ui.menuIndex > 3){
                        gp.ui.menuIndex = 0;
                    }
                }

                if (code == KeyEvent.VK_D) {

                    if (gp.ui.menuIndex == 1 && gp.music.volumeScale < 4) {
                        gp.music.volumeScale++;
                        gp.music.getVolume();
                    }
                    if (gp.ui.menuIndex == 2 && gp.se.volumeScale < 4) {
                        gp.se.volumeScale++;
                    }

                }

                if (code == KeyEvent.VK_A) {

                    if (gp.ui.menuIndex == 1 && gp.music.volumeScale > 0) {
                        gp.music.volumeScale--;
                        gp.music.getVolume();
                    }
                    if (gp.ui.menuIndex == 2 && gp.se.volumeScale > 0) {
                        gp.se.volumeScale--;
                    }

                }

            }

            case INVENTORY -> {

                if (code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_C) {
                    gp.gameState = GamePanel.GameState.GAME;
                }

                if (code == KeyEvent.VK_W){
                    if (gp.ui.inventoryIndex >= 8) {
                        gp.ui.inventoryIndex -= 8;
                    }
                }

                if (code == KeyEvent.VK_S){
                    if (gp.ui.inventoryIndex <= 39) {
                        gp.ui.inventoryIndex += 8;
                    }
                }

                if (code == KeyEvent.VK_A){
                    if (gp.ui.inventoryIndex > 0) {
                        gp.ui.inventoryIndex--;
                    }
                }

                if (code == KeyEvent.VK_D){

                    if (gp.ui.inventoryIndex < 47) {
                        gp.ui.inventoryIndex++;
                    }

                }

                if (code == KeyEvent.VK_ENTER){

                    gp.ui.useItem();

                }

            }

            case LVL_UP -> {

                if (code == KeyEvent.VK_B || code == KeyEvent.VK_ESCAPE){
                    gp.gameState = GamePanel.GameState.GAME;
                }

                if (code == KeyEvent.VK_W){
                    gp.ui.menuIndex --;
                    if (gp.ui.menuIndex < 0){
                        gp.ui.menuIndex = 4;
                    }
                }

                if (code == KeyEvent.VK_S){
                    gp.ui.menuIndex ++;
                    if (gp.ui.menuIndex > 4){
                        gp.ui.menuIndex = 0;
                    }
                }

                if (code == KeyEvent.VK_ENTER) {
                    gp.ui.lvlUpAction();
                }

            }

        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (gp.gameState){

            case GAME -> {

                if (code == KeyEvent.VK_W){
                    pressedUp = false;
                }
                if (code == KeyEvent.VK_S){
                    pressedDown = false;
                }
                if (code == KeyEvent.VK_A){
                    pressedLeft = false;
                }
                if (code == KeyEvent.VK_D){
                    pressedRight = false;
                }
                if (code == KeyEvent.VK_O) {
                    gp.player.attack();
                }
                if (code == KeyEvent.VK_L) {
                    gp.player.throwProjectile();
                }

            }

        }

        // Title screen part

    }
}
