// File path: mathax/client/systems/modules/movement/BidenZoom.java

package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class BidenZoom extends Module {

    public BidenZoom() {
        super(Categories.Movement, Items.AIR, "Biden Zoom", "A very slow ground speed that should work on Hypixel");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;

        if (mc.player.input.pressingForward && mc.player.isOnGround()) {
            double speedMultiplier = 0.009;  // Adjust this value for the desired speed
            float yaw = mc.player.getYaw();
            double motionX = -Math.sin(Math.toRadians(yaw)) * speedMultiplier;
            double motionZ = Math.cos(Math.toRadians(yaw)) * speedMultiplier;

            mc.player.setVelocity(
                mc.player.getVelocity().x + motionX,
                mc.player.getVelocity().y,
                mc.player.getVelocity().z + motionZ
            );
        }
    }
}



