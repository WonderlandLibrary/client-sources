package dev.client.tenacity.module.impl.render;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.game.TickEvent;
import dev.event.impl.network.PacketReceiveEvent;
import dev.settings.impl.NumberSetting;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class Ambience extends Module {

    private final NumberSetting time = new NumberSetting("Time", 12000, 24000, 0, 1000);

    public Ambience() {
        super("Ambience", Category.RENDER, "world time");
        this.addSettings(time);
    }

    private final EventListener<TickEvent> onTick = e -> {
        if (mc.theWorld != null) {
            mc.theWorld.setWorldTime(time.getValue().longValue());
        }
    };

    private final EventListener<PacketReceiveEvent> onPacketReceive = e -> {
        if (e.getPacket() instanceof S03PacketTimeUpdate) {
            e.cancel();
        }
    };

}
