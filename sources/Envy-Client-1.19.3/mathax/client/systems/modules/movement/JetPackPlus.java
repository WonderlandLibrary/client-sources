package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class JetPackPlus extends Module {

    public JetPackPlus() {
        super(Categories.Movement, Items.FIREWORK_ROCKET, "JetPack-Plus", "Best Fly Hack 2023");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.options.jumpKey.isPressed())
            mc.player.jump();
        if (mc.options.sneakKey.isPressed())
            mc.player.setVelocity(mc.player.getVelocity().x, -0.5, mc.player.getVelocity().z);
        if (mc.options.sprintKey.isPressed())
            mc.player.setVelocity(mc.player.getVelocity().x, 0, mc.player.getVelocity().z);
    }
}
