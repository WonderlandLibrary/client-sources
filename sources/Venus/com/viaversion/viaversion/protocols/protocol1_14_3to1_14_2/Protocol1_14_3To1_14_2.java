/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_14_3to1_14_2;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;

public class Protocol1_14_3To1_14_2
extends AbstractProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14> {
    public Protocol1_14_3To1_14_2() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, null, null);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_14.TRADE_LIST, Protocol1_14_3To1_14_2::lambda$registerPackets$0);
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n;
        packetWrapper.passthrough(Type.VAR_INT);
        int n2 = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
        for (n = 0; n < n2; ++n) {
            packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
            packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
            if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
            }
            packetWrapper.passthrough(Type.BOOLEAN);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.FLOAT);
        }
        packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.passthrough(Type.VAR_INT);
        n = packetWrapper.passthrough(Type.BOOLEAN).booleanValue() ? 1 : 0;
        packetWrapper.write(Type.BOOLEAN, n != 0);
    }
}

