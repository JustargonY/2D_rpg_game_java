package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class HealthPotion extends Item{

    public HealthPotion(GamePanel gp) {

        this.gp = gp;
        name = "health_potion";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/usable/health_potion.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        description = "Strange red liquid.\nRestores 25 HP";

    }

    public void drink() {

        gp.player.increaseHP(25);
        gp.player.inventory.remove(this);

    }

}
