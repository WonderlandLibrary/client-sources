package mathax.client.systems.modules.world;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class AntiCactus extends Module {
    //how this works, who knows
    public AntiCactus() {
        super(Categories.World, Items.CACTUS, "anti-cactus", "Prevents you from taking damage from cactus.");
    }
}
