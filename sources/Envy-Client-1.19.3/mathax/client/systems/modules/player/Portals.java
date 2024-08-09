package mathax.client.systems.modules.player;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class Portals extends Module {
    public Portals() {
        super(Categories.Player, Items.OBSIDIAN, "Anti-Portal Trap", "Allows you to use GUIs normally while in a Nether Portal.");
    }
}
