package main;

import entity.Entity;
import entity.Monster;
import entity.NPC;
import entity.Player;
import terrain.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    public enum GameState{
        MAIN_MENU,
        GAME,
        PAUSE,
        TITLE_SCREEN,
        DIALOG,
        INVENTORY,
        SETTINGS,
        GAME_OVER
    }

    // Tile parameters
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    // Screen parameters
    public final int maxScreenCol = 21;
    public final int maxScreenRow = 12;

    // Screen parameters
    public final int screenWidth = maxScreenCol * tileSize;
    public final int screenHeight = maxScreenRow * tileSize;

    // World parameters
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRow * tileSize;

    // Full Screen parameters
    public int fullScreenWidth = screenWidth;
    public int fullScreenHeight= screenHeight;
    BufferedImage tmpScreen;
    Graphics2D g2;
    public boolean isFullScreen;

    // Game options
    final int FPS = 60;
    public GameState gameState;

    // Main thread
    Thread gameThread;

    // Listeners
    public KeyL keyL = new KeyL(this);

    // Creating player
    public Player player = new Player(this, keyL);

    // Setting classes
    public TileManager tileManager = new TileManager(this);
    public UI ui = new UI(this);
    Sound sound = new Sound();
    ObjectSetter objSetter = new ObjectSetter(this);

    // Entity lists
    public ArrayList<NPC> NPCList = new ArrayList<>();
    public ArrayList<Monster> monsterList = new ArrayList<>();
    public ArrayList<Entity> entityList = new ArrayList<>();

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyL);
        this.setFocusable(true);

    }

    public void setupGame() {
        gameState = GameState.TITLE_SCREEN;
        objSetter.setNPCList();
        objSetter.setMonsterList();
        playTrack(1);

        tmpScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tmpScreen.getGraphics();

        setFullScreen();
    }

    public void setFullScreen() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        fullScreenWidth = Main.window.getWidth();
        fullScreenHeight = Main.window.getHeight();
        isFullScreen = true;

    }

    public void setDefaultScreen() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(null);
        fullScreenWidth = screenWidth;
        fullScreenHeight = screenHeight;
        isFullScreen = false;

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double)1000/FPS;
        double delta = 0;
        long lastTime = System.currentTimeMillis();

        while (gameThread != null){

            long currentTime = System.currentTimeMillis();
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1) {
                update();
                drawToTempScreen();
                drawToScreen();
                delta -= 1;
            }

        }

    }

    public void update() {

        switch (gameState){
            case GAME, INVENTORY, GAME_OVER -> {
                player.update();
                NPCList.forEach(NPC::update);
                monsterList.removeIf(monster -> monster.curHP == 0 && !monster.dying);
                entityList.removeIf(entity -> entity.curHP == 0 && !entity.dying);
                monsterList.forEach(Monster::update);
            }
            case MAIN_MENU -> {}
        }

    }

    public void drawToTempScreen() {

        g2 = (Graphics2D) tmpScreen.getGraphics();

        switch (gameState){
            case GAME, PAUSE, DIALOG, INVENTORY, GAME_OVER -> {
                tileManager.draw(g2);
                NPCList.forEach(npc -> npc.draw(g2));
                monsterList.forEach(monster -> monster.draw(g2));
                player.draw(g2);
                ui.draw(g2);
                g2.dispose();
            }
            case MAIN_MENU, SETTINGS -> {
                tileManager.draw(g2);
                NPCList.forEach(npc -> npc.draw(g2));
                monsterList.forEach(monster -> monster.draw(g2));
                ui.draw(g2);
                g2.dispose();
            }
            case TITLE_SCREEN -> {
                ui.draw(g2);
                g2.dispose();
            }
        }

    }

    public void drawToScreen() {

        Graphics g = getGraphics();
        g.drawImage(tmpScreen, 0, 0, fullScreenWidth, fullScreenHeight, null);
        g.dispose();

    }

    public void playTrack(int i) {
        sound.load(i);
        sound.play();
        sound.loop();
    }

    public void stopTrack() {
        sound.stop();
    }

    public void playSoundEffect(int i) {
        sound.load(i);
        sound.play();
    }

    public void gameOver() {

        gameState = GameState.GAME_OVER;
        keyL.pressedRight = false;
        keyL.pressedLeft = false;
        keyL.pressedDown = false;
        keyL.pressedUp = false;

    }

    public void reset() {

        player = new Player(this, keyL);
        NPCList.clear();
        monsterList.clear();
        entityList.clear();

        objSetter.setMonsterList();
        objSetter.setNPCList();

    }
}
