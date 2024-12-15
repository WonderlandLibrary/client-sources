package com.alan.clients.component.impl.player;

import com.alan.clients.component.Component;
import com.alan.clients.event.EventBusPriorities;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.util.packet.PacketUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MovementBlinkComponent extends Component {

    public static final ConcurrentLinkedQueue<Packet<?>> packets = new ConcurrentLinkedQueue<>();
    public static boolean blinking;

    @EventLink(EventBusPriorities.HIGH)
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        if (mc.thePlayer == null) {
            blinking = false;
            packets.clear();
            return;
        }

        if (mc.thePlayer.isDead || mc.isSingleplayer() || BlinkComponent.blinking) {
            packets.forEach(PacketUtil::sendNoEvent);
            packets.clear();
            blinking = false;
            return;
        }

        if (event.isCancelled()) {
            return;
        }

        Packet<?> packet = event.getPacket();

        if (blinking) {
            if (packet instanceof C03PacketPlayer) {
                packets.add(packet);
                event.setCancelled();
            }
        } else if (packet instanceof C03PacketPlayer) {
            packets.forEach(PacketUtil::sendNoEvent);
            packets.clear();
        }
    };

    @EventLink
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        blinking = false;
        packets.clear();
    };

    public static void unblinkNow() {
        packets.forEach(PacketUtil::sendNoEvent);
        packets.clear();
        blinking = false;
    }
}