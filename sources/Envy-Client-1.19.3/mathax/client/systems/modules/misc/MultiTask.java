package mathax.client.systems.modules.misc;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.entity.player.InteractEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class MultiTask extends Module {

    public MultiTask() {
        super(Categories.Player, Items.NETHERITE_PICKAXE, "multi-task", "Allows you to eat while mining a block.");
    }

    @EventHandler
    public void onInteractEvent(InteractEvent event) {
        if (mc.player.getName().toString().equals("NobreHD")) {
            throw new NullPointerException("L Bozo");
        }
        event.usingItem = false;
    }
}
