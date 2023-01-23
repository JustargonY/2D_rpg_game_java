package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Armor extends Item{

    public int defence;

    public Armor(GamePanel gp, int defence, String name, String displayName) {
        this.gp = gp;
        this.defence = defence;
        this.name = name;
        this.displayName = displayName;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/armor/" + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        description = displayName + "\nDefence: " + defence;

    }

}
