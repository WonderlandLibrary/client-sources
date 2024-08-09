package mathax.client.systems.modules.render;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Category;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class NoVisualCooldown extends Module {

    public NoVisualCooldown() {
        super(Categories.Render, Items.AIR, "no-visual-cooldown", "Removes the visual effect of your weapon lowering after attacking.");
    }

    //Mixing :D
}
