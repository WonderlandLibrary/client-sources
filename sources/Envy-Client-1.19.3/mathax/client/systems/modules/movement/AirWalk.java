package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class AirWalk extends Module {

    public AirWalk() {
        super(Categories.Movement, Items.FEATHER, "air-walk", "Allows you to walk on air.");
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player.getVelocity() != null) {
            mc.player.setVelocity(mc.player.getVelocity().x, 0, mc.player.getVelocity().z);
        }
    }
}
