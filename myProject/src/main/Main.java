package main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Project");

        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();
        gp.startGameThread();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }

}
