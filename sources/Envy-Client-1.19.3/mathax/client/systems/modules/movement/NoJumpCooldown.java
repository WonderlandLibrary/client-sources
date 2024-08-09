package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.mixin.LivingEntityAccessor;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class NoJumpCooldown extends Module {
    public NoJumpCooldown() {
        super(Categories.Movement, Items.BEEF,   "no-jump-cooldown", "Removes the jump cooldown.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (this.mc.player != null) {
            ((LivingEntityAccessor)this.mc.player).setJumpCooldown(0);
        }
    }
}
