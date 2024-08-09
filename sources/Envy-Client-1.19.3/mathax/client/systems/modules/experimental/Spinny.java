package mathax.client.systems.modules.experimental;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
public class Spinny extends Module {
    @EventHandler
    public void onTick(TickEvent.Post event) {
        assert mc.player != null;
        mc.player.headYaw = mc.player.prevHeadYaw + 10f;
        mc.player.bodyYaw = mc.player.prevBodyYaw - 10f;
    }
    public Spinny() {
        super(Categories.Fun, Items.BARRIER, "Spinny", "Vary Spinny.");
    }
}
//lol
