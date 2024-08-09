package mathax.client.systems.modules.combat;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class NoPortalHitbox extends Module {
    public NoPortalHitbox() {
        super(Categories.Combat, Items.BEDROCK, "No-Portal-Hitbox", "Attack and smack ur enemies through portal");
    }
}
