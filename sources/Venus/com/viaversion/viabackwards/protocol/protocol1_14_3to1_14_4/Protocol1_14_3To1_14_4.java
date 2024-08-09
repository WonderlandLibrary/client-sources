/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_14_3to1_14_4;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;

public class Protocol1_14_3To1_14_4
extends BackwardsProtocol<ClientboundPackets1_14_4, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14> {
    public Protocol1_14_3To1_14_4() {
        super(ClientboundPackets1_14_4.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_14_4.ACKNOWLEDGE_PLAYER_DIGGING, ClientboundPackets1_14.BLOCK_CHANGE, new PacketHandlers(this){
            final Protocol1_14_3To1_14_4 this$0;
            {
                this.this$0 = protocol1_14_3To1_14_4;
            }

            @Override
            public void register() {
                this.map(Type.POSITION1_14);
                this.map(Type.VAR_INT);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                boolean bl = packetWrapper.read(Type.BOOLEAN);
                if (bl && n == 0) {
                    packetWrapper.cancel();
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_14_4.TRADE_LIST, Protocol1_14_3To1_14_4::lambda$registerPackets$0);
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
        int n = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
        for (int i = 0; i < n; ++i) {
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
            packetWrapper.read(Type.INT);
        }
    }
}

