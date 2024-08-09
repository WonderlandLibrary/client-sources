package mathax.client.systems.modules.player;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class NoBreakDelay extends Module {
    public NoBreakDelay() {
        super(Categories.Player, Items.STONE, "no-break-delay", "Completely removes the delay between breaking blocks.");
    }
}
