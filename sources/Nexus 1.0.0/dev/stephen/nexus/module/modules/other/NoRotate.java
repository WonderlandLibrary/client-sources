package dev.stephen.nexus.module.modules.other;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.network.EventPacket;
import dev.stephen.nexus.mixin.accesors.PlayerPositionLookS2CPacketAccessor;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

import java.lang.reflect.Field;

public class NoRotate extends Module {

    public NoRotate() {
        super("NoRotate", "Prevents the server from rotating your head", 0, ModuleCategory.OTHER);
    }

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }

        if (event.getPacket() instanceof PlayerPositionLookS2CPacket packet) {
            ((PlayerPositionLookS2CPacketAccessor) packet).setPitch(mc.player.getPitch());
            ((PlayerPositionLookS2CPacketAccessor) packet).setYaw(mc.player.getYaw());
        }
    };
}
