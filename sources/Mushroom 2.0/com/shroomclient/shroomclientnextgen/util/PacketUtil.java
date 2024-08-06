package com.shroomclient.shroomclientnextgen.util;

import com.shroomclient.shroomclientnextgen.mixin.ClientConnectionAccessor;
import com.shroomclient.shroomclientnextgen.mixin.ClientPlayerInteractionManagerAccessor;
import java.util.ArrayList;
import java.util.function.Function;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;

public class PacketUtil {

    public static ArrayList<Integer> proccedPackets = new ArrayList<>();

    public static void sendPacket(Packet<?> packet, boolean immediately) {
        proccedPackets.add(System.identityHashCode(packet));

        if (immediately) {
            ((ClientConnectionAccessor) C.mc
                    .getNetworkHandler()
                    .getConnection()).sendImmediately_(packet, null, true);
        } else {
            C.mc.getNetworkHandler().sendPacket(packet);
        }
    }

    public static void sendSequencedPacket(
        Function<Integer, Packet<ServerPlayPacketListener>> builder
    ) {
        ((ClientPlayerInteractionManagerAccessor) C.mc.interactionManager).sendSequencedPacket_(
                C.w(),
                seq -> {
                    Packet<ServerPlayPacketListener> p = builder.apply(seq);
                    proccedPackets.add(System.identityHashCode(p));
                    return p;
                }
            );
    }
}
