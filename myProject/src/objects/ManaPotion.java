package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ManaPotion extends Item{

    public ManaPotion(GamePanel gp) {

        this.gp = gp;
        name = "mana_potion";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/usable/mana_potion.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        description = "Strange blue liquid.\nRestores 20 MP";

    }

    public void drink() {

        gp.player.increaseMP(20);
        gp.player.inventory.remove(this);

    }

}
