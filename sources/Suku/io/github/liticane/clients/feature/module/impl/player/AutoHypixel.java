package io.github.liticane.clients.feature.module.impl.player;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.other.PacketEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.util.misc.ChatUtil;
import net.minecraft.network.play.client.C01PacketChatMessage;

@Module.Info(name = "AutoHypixel", category = Module.Category.PLAYER)
public class AutoHypixel extends Module {

    @SubscribeEvent
    private final EventListener<PacketEvent> onpacketo = e -> {
        if(e.getPacket() instanceof C01PacketChatMessage)  {
            C01PacketChatMessage C01 = (C01PacketChatMessage) e.getPacket();
            ChatUtil.display("test");
        }
    };
}
