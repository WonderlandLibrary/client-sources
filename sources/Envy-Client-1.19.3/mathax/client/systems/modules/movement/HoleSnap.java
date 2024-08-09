package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.player.PlayerUtils;
import net.minecraft.item.Items;

public class HoleSnap extends Module {
    public HoleSnap() {
        super(Categories.Movement, Items.AIR, "hole-snap", "Attempts to Snap downwards when outside of a hole || Very Aggressive");
    }

    @EventHandler
    public boolean onTick(TickEvent.Post event) {
        assert mc.player != null;
        if (!PlayerUtils.isInHole(mc.player) && (!mc.player.isOnGround())) {
            assert mc.world != null;

            if (mc.world.getBlockState(mc.player.getBlockPos().down()).isAir()) {
                mc.player.setVelocity(mc.player.getVelocity().x, -50, mc.player.getVelocity().z);
            }
        }
        return false;
    }
}
