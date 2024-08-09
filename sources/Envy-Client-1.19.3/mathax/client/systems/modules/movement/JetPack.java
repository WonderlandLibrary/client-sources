package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class JetPack extends Module {


    public JetPack() {
        super(Categories.Movement, Items.FIREWORK_ROCKET, "JetPack", "Hold Space to fly");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.options.jumpKey.isPressed())
            mc.player.jump();
    }
}
