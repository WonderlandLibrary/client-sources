package dev.stephen.nexus.module.modules.render;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.network.EventPacket;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.event.types.TransferOrder;
import dev.stephen.nexus.mixin.accesors.WorldTimeUpdateS2CPacketAccesor;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.setting.impl.ModeSetting;
import dev.stephen.nexus.module.setting.impl.NumberSetting;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.world.World;

public class Ambience extends Module {
    public static final NumberSetting timeOfDay = new NumberSetting("Time", World.field_30969 / 2, World.field_30969, 0, 1);

    public Ambience() {
        super("Ambience", "Changes the game", 0, ModuleCategory.RENDER);
        this.addSettings(timeOfDay);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        mc.world.setTimeOfDay(timeOfDay.getValueInt());
    };

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }
        if (event.getOrder() == TransferOrder.SEND) {
            return;
        }
        if (event.getPacket() instanceof WorldTimeUpdateS2CPacket a) {
            ((WorldTimeUpdateS2CPacketAccesor) a).setTimeOfDay(timeOfDay.getValueInt());
        }
    };
}
