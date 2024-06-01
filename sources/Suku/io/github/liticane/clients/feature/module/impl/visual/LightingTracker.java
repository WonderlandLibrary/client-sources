package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.other.PacketEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.util.misc.ChatUtil;
import net.minecraft.network.play.server.S29PacketSoundEffect;

@Module.Info(name = "LightningTracker", category = Module.Category.VISUAL)
public class LightingTracker extends Module{
    @SubscribeEvent
    private final EventListener<PacketEvent> ppacktoevento = event -> {
        if (event.getPacket() instanceof S29PacketSoundEffect) {
            S29PacketSoundEffect soundPacket = ((S29PacketSoundEffect) event.getPacket());
            if (soundPacket.getSoundName().equals("ambient.weather.thunder")) {
                ChatUtil.display(String.format("Lightning at (%s, %s, %s)", (int) soundPacket.getX(), (int) soundPacket.getY(), (int) soundPacket.getZ()));
            }
        }

    };
}
