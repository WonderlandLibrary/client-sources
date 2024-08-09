package mathax.client.systems.modules.render;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class AlwaysSwinging extends Module {

    public AlwaysSwinging() {
        super(Categories.Fun, Items.STONE_BUTTON, "CronicSwinger", "Swings your hand 24/7");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player.handSwingProgress == 0) {
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }
}
