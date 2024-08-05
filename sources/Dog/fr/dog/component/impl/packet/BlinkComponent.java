package fr.dog.component.impl.packet;

import fr.dog.component.Component;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.network.PacketSendEvent;
import fr.dog.event.impl.network.PacketSendEventFinal;
import fr.dog.util.packet.PacketUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlinkComponent extends Component {

    private static final List<Packet<?>> packetList = new ArrayList<>();
    private static final Set<Class<?>> nonCancelablePackets = Set.of(
            C01PacketChatMessage.class,
            C14PacketTabComplete.class,
            C01PacketEncryptionResponse.class,
            C01PacketPing.class,
            C00PacketLoginStart.class,
            C00PacketServerQuery.class,
            C00Handshake.class,
            C00PacketKeepAlive.class
    );
    private static boolean isBlinking = false;
    public static boolean isResetBlink = false;

    public static void onEnable() {
        if (mc.isIntegratedServerRunning())
            return;

        isBlinking = true;
    }

    public static void onDisable() {
        if (mc.isIntegratedServerRunning())
            return;
        isBlinking = false;
        isResetBlink = true;
        packetList.forEach(PacketUtil::sendPacketNoEvent);
        isResetBlink = false;
        packetList.clear();
    }

    @SubscribeEvent
    private void onPacketSendEvent(PacketSendEventFinal event) {
        if (mc.isIntegratedServerRunning())
            return;

        if (isBlinking && !event.isCancelled()) {
            if(nonCancelablePackets.contains(event.getPacket().getClass())){
                return;
            }
            event.setCancelled(true);
            packetList.add(event.getPacket());
        }

        if (mc.thePlayer == null) {
            onDisable();
        }
    }
}