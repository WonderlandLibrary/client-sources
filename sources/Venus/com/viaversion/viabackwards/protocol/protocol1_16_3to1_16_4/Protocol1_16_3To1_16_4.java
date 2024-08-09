/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_16_3to1_16_4;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_16_3to1_16_4.storage.PlayerHandStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;

public class Protocol1_16_3To1_16_4
extends BackwardsProtocol<ClientboundPackets1_16_2, ClientboundPackets1_16_2, ServerboundPackets1_16_2, ServerboundPackets1_16_2> {
    public Protocol1_16_3To1_16_4() {
        super(ClientboundPackets1_16_2.class, ClientboundPackets1_16_2.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16_2.class);
    }

    @Override
    protected void registerPackets() {
        this.registerServerbound(ServerboundPackets1_16_2.EDIT_BOOK, new PacketHandlers(this){
            final Protocol1_16_3To1_16_4 this$0;
            {
                this.this$0 = protocol1_16_3To1_16_4;
            }

            @Override
            public void register() {
                this.map(Type.FLAT_VAR_INT_ITEM);
                this.map(Type.BOOLEAN);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                if (n == 1) {
                    packetWrapper.write(Type.VAR_INT, 40);
                } else {
                    packetWrapper.write(Type.VAR_INT, packetWrapper.user().get(PlayerHandStorage.class).getCurrentHand());
                }
            }
        });
        this.registerServerbound(ServerboundPackets1_16_2.HELD_ITEM_CHANGE, Protocol1_16_3To1_16_4::lambda$registerPackets$0);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PlayerHandStorage());
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        short s = packetWrapper.passthrough(Type.SHORT);
        packetWrapper.user().get(PlayerHandStorage.class).setCurrentHand(s);
    }
}

