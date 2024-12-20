package com.polarware.module.impl.other;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.network.PacketSendEvent;
import com.polarware.value.impl.ModeValue;
import com.polarware.value.impl.SubMode;
import io.netty.buffer.Unpooled;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

@ModuleInfo(name = "module.other.clientspoofer.name", description = "module.other.clientspoofer.description", category = Category.OTHER)
public final class ClientSpooferModule extends Module  {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("Forge"))
            .add(new SubMode("Lunar"))
            .add(new SubMode("PvP Lounge"))
            .add(new SubMode("CheatBreaker"))
            .add(new SubMode("Geyser"))
            .setDefault("Forge");

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        final Packet<?> packet = event.getPacket();

        if (packet instanceof C17PacketCustomPayload) {
            final C17PacketCustomPayload wrapper = (C17PacketCustomPayload) packet;

            switch (mode.getValue().getName()) {
                case "Forge": {
                    wrapper.setData(createPacketBuffer("FML", true));
                    break;
                }

                case "Lunar": {
                    wrapper.setChannel("REGISTER");
                    wrapper.setData(createPacketBuffer("Lunar-Client", false));
                    break;
                }

                case "LabyMod": {
                    wrapper.setData(createPacketBuffer("LMC", true));
                    break;
                }

                case "PvP Lounge": {
                    wrapper.setData(createPacketBuffer("PLC18", false));
                    break;
                }

                case "CheatBreaker": {
                    wrapper.setData(createPacketBuffer("CB", true));
                    break;
                }

                case "Geyser": {
                    // It's meant to be "eyser" don't change it
                    wrapper.setData(createPacketBuffer("eyser", false));
                    break;
                }
            }
        }
    };

    private PacketBuffer createPacketBuffer(final String data, final boolean string) {
        if (string) {
            return new PacketBuffer(Unpooled.buffer()).writeString(data);
        } else {
            return new PacketBuffer(Unpooled.wrappedBuffer(data.getBytes()));
        }
    }
}
