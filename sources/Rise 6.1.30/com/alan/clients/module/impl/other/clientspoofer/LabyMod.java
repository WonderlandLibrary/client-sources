package com.alan.clients.module.impl.other.clientspoofer;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.other.ClientSpoofer;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.tuples.Triple;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.StringValue;
import io.netty.buffer.Unpooled;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

import java.util.ArrayList;

public class LabyMod extends Mode<ClientSpoofer> {

    public LabyMod(String name, ClientSpoofer parent) {
        super(name, parent);
    }

    private final StringValue version = new StringValue("Spoofed Version, Latest would be preferred",
            this, "4.2.31");

    @EventLink
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof C17PacketCustomPayload) {
            ArrayList<Triple<String, String, Boolean>> payloads = new ArrayList<Triple<String, String, Boolean>>() {{
                add(new Triple<>("MC|Brand", "labymod",true));
                add(new Triple<>("REGISTER", "labymod:neominecraft:intavelabymod3:main",false));
                add(new Triple<>("labymod:neo", "{\"version\":\"" + version.getValue() + "\"}",false));
                add(new Triple<>("labymod3:main", "INFO{\"version\":\"" + version.getValue() + "\"}",false));
                add(new Triple<>("minecraft:intave", "L{\"legacySneakHeight\":false,\"legacyOldRange\":false,\"legacyOldSlowdown\":false}",false));
                add(new Triple<>("minecraft:intave", "\n" +
                        "clientconfigL{\"legacySneakHeight\":false,\"legacyOldRange\":false,\"legacyOldSlowdown\":false}",false));
            }};

            event.setCancelled();

            for (Triple<String, String, Boolean> payload : payloads) {
                PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());

                if (payload.getThird()) {
                    packetbuffer.writeString(payload.getSecond());
                } else {
                    packetbuffer.writeBytes(payload.getSecond().getBytes());
                }

                C17PacketCustomPayload newPayload = new C17PacketCustomPayload(payload.getFirst(), packetbuffer);

                PacketUtil.sendNoEvent(newPayload);
            }
        }
    };
}