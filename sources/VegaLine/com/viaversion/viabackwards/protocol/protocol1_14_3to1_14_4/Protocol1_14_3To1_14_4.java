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
        this.registerClientbound(ClientboundPackets1_14_4.ACKNOWLEDGE_PLAYER_DIGGING, ClientboundPackets1_14.BLOCK_CHANGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION1_14);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int status = wrapper.read(Type.VAR_INT);
                    boolean allGood = wrapper.read(Type.BOOLEAN);
                    if (allGood && status == 0) {
                        wrapper.cancel();
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_14_4.TRADE_LIST, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Type.VAR_INT);
            int size = wrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                }
                wrapper.passthrough(Type.BOOLEAN);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.FLOAT);
                wrapper.read(Type.INT);
            }
        });
    }
}

