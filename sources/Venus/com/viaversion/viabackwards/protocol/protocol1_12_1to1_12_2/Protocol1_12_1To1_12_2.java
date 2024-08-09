/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2.KeepAliveTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;

public class Protocol1_12_1To1_12_2
extends BackwardsProtocol<ClientboundPackets1_12_1, ClientboundPackets1_12_1, ServerboundPackets1_12_1, ServerboundPackets1_12_1> {
    public Protocol1_12_1To1_12_2() {
        super(ClientboundPackets1_12_1.class, ClientboundPackets1_12_1.class, ServerboundPackets1_12_1.class, ServerboundPackets1_12_1.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_12_1.KEEP_ALIVE, new PacketHandlers(this){
            final Protocol1_12_1To1_12_2 this$0;
            {
                this.this$0 = protocol1_12_1To1_12_2;
            }

            @Override
            public void register() {
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Long l = packetWrapper.read(Type.LONG);
                packetWrapper.user().get(KeepAliveTracker.class).setKeepAlive(l);
                packetWrapper.write(Type.VAR_INT, l.hashCode());
            }
        });
        this.registerServerbound(ServerboundPackets1_12_1.KEEP_ALIVE, new PacketHandlers(this){
            final Protocol1_12_1To1_12_2 this$0;
            {
                this.this$0 = protocol1_12_1To1_12_2;
            }

            @Override
            public void register() {
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                long l;
                int n = packetWrapper.read(Type.VAR_INT);
                if (n != Long.hashCode(l = packetWrapper.user().get(KeepAliveTracker.class).getKeepAlive())) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.write(Type.LONG, l);
                packetWrapper.user().get(KeepAliveTracker.class).setKeepAlive(Integer.MAX_VALUE);
            }
        });
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new KeepAliveTracker());
    }
}

