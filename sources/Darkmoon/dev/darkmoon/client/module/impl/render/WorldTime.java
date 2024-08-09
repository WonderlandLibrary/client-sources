package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.network.play.server.SPacketTimeUpdate;

@ModuleAnnotation(name = "WorldTime", category = Category.RENDER)
public class WorldTime extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Day", "Day", "Night", "Sunrise", "Sunset", "Custom");
    private final NumberSetting time = new NumberSetting("Time", 1000, 0, 24000, 100, () -> mode.get().equals("Custom"));

    @EventTarget
    public void onUpdate(EventUpdate event) {
        switch (mode.get()) {
            case "Day":
                 mc.world.setWorldTime(5000);
                 break;
            case "Night":
                mc.world.setWorldTime(17000);
                break;
            case "Sunrise":
                mc.world.setWorldTime(0);
                break;
            case "Sunset":
                mc.world.setWorldTime(13000);
                break;
            case "Custom":
                mc.world.setWorldTime((long) time.get());
                break;
        }
    }

    @EventTarget
    public void onPacket(EventReceivePacket event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            event.setCancelled(true);
        }
    }
}
