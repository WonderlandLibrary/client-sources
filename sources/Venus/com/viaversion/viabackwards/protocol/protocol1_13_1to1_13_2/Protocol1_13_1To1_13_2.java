/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets.EntityPackets1_13_2;
import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets.InventoryPackets1_13_2;
import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets.WorldPackets1_13_2;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;

public class Protocol1_13_1To1_13_2
extends BackwardsProtocol<ClientboundPackets1_13, ClientboundPackets1_13, ServerboundPackets1_13, ServerboundPackets1_13> {
    public Protocol1_13_1To1_13_2() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
    }

    @Override
    protected void registerPackets() {
        InventoryPackets1_13_2.register(this);
        WorldPackets1_13_2.register(this);
        EntityPackets1_13_2.register(this);
        this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketHandlers(this){
            final Protocol1_13_1To1_13_2 this$0;
            {
                this.this$0 = protocol1_13_1To1_13_2;
            }

            @Override
            public void register() {
                this.map(Type.FLAT_ITEM, Type.FLAT_VAR_INT_ITEM);
            }
        });
        this.registerClientbound(ClientboundPackets1_13.ADVANCEMENTS, Protocol1_13_1To1_13_2::lambda$registerPackets$0);
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.BOOLEAN);
        int n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            int n2;
            packetWrapper.passthrough(Type.STRING);
            if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                packetWrapper.passthrough(Type.STRING);
            }
            if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                packetWrapper.passthrough(Type.COMPONENT);
                packetWrapper.passthrough(Type.COMPONENT);
                Item item = packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                packetWrapper.write(Type.FLAT_ITEM, item);
                packetWrapper.passthrough(Type.VAR_INT);
                n2 = packetWrapper.passthrough(Type.INT);
                if ((n2 & 1) != 0) {
                    packetWrapper.passthrough(Type.STRING);
                }
                packetWrapper.passthrough(Type.FLOAT);
                packetWrapper.passthrough(Type.FLOAT);
            }
            packetWrapper.passthrough(Type.STRING_ARRAY);
            int n3 = packetWrapper.passthrough(Type.VAR_INT);
            for (n2 = 0; n2 < n3; ++n2) {
                packetWrapper.passthrough(Type.STRING_ARRAY);
            }
        }
    }
}

