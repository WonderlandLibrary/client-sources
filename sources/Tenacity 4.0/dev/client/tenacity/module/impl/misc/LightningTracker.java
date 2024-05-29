package dev.client.tenacity.module.impl.misc;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.utils.player.ChatUtils;
import dev.event.EventListener;
import dev.event.impl.network.PacketReceiveEvent;
import net.minecraft.network.play.server.S29PacketSoundEffect;

public final class LightningTracker extends Module {

    public LightningTracker() {
        super("LightningTracker", Category.MISC, "detects lightning");
    }

    private final EventListener<PacketReceiveEvent> onPacketReceive = e -> {
        if (e.getPacket() instanceof S29PacketSoundEffect) {
            S29PacketSoundEffect soundPacket = ((S29PacketSoundEffect) e.getPacket());
            if (soundPacket.getSoundName().equals("ambient.weather.thunder")) {
                ChatUtils.print(String.format("Lightning detected at (%s, %s, %s)", (int) soundPacket.getX(), (int) soundPacket.getY(), (int) soundPacket.getZ()));
            }
        }
    };

}
