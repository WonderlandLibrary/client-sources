package com.alan.clients.component.impl.viamcp;

import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.packet.custom.impl.PlayPongC2SPacket;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

public final class TransactionFixComponent extends Component {

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        if (!event.isCancelled() && ViaLoadingBase.getInstance()
                .getTargetVersion().newerThanOrEqualTo(ProtocolVersion.v1_17)) {
            if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
                C0FPacketConfirmTransaction transaction = ((C0FPacketConfirmTransaction) event.getPacket());

                if (false)Minecraft.getMinecraft().addScheduledTask(() -> PacketUtil.send(
                        new PlayPongC2SPacket(transaction.getUid())));

                PacketUtil.send(
                        new PlayPongC2SPacket(transaction.getUid()));
                event.setCancelled();
            }
        }
    };

}