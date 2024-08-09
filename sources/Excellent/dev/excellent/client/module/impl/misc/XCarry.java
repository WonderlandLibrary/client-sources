package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import net.minecraft.network.play.client.CCloseWindowPacket;

@ModuleInfo(name = "XCarry", description = "Позволяет складывать предметы в крафт слоты", category = Category.MISC)
public class XCarry extends Module {
    private final Listener<PacketEvent> onPacket = event -> {
        if (mc.player == null) return;
        if (event.getPacket() instanceof CCloseWindowPacket) {
            event.cancel();
        }
    };
}
