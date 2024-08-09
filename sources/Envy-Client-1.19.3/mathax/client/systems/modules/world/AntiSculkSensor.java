package mathax.client.systems.modules.world;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.EventMotionUpdate;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class AntiSculkSensor extends Module {

    public AntiSculkSensor() {
        super(Categories.World, Items.SCULK_SENSOR, "anti-sculk-sensor", "Prevents the sculk sensor from detecting you.");
    }

    @EventHandler
    public void onMotionUpdate(EventMotionUpdate event) {
        if (event.isPre()) mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
    }

    @EventHandler
    public void onDisable() {
        if (!mc.options.sneakKey.isPressed()) mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
    }
}
