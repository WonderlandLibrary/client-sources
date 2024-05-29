package dev.client.tenacity.module.impl.misc;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.game.TickEvent;
import dev.utils.network.PacketUtils;
import net.minecraft.network.play.client.C16PacketClientStatus;

public final class AutoRespawn extends Module {

    private final EventListener<TickEvent> tickEventEventListener = event -> {
        if(mc.thePlayer.isDead){
            PacketUtils.sendPacketNoEvent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
        }
    };

    public AutoRespawn() {
        super("AutoRespawn", Category.MISC, "automatically respawn");
    }
}
