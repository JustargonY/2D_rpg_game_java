package objects;


import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Weapon extends Item {

    public int damage;

    public Weapon(GamePanel gp, int damage, String name, String displayName) {
        this.gp = gp;
        this.damage = damage;
        this.name = name;
        this.displayName = displayName;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/weapon/" + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        description = displayName + "\nAttack: " + damage;
    }


}
