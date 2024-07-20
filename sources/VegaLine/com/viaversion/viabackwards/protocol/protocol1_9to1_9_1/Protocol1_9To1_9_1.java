/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_9to1_9_1;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;

public class Protocol1_9To1_9_1
extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_9, ServerboundPackets1_9, ServerboundPackets1_9> {
    public Protocol1_9To1_9_1() {
        super(ClientboundPackets1_9.class, ClientboundPackets1_9.class, ServerboundPackets1_9.class, ServerboundPackets1_9.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_9.JOIN_GAME, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map((Type)Type.INT, Type.BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.BOOLEAN);
            }
        });
        this.registerClientbound(ClientboundPackets1_9.SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int sound = wrapper.get(Type.VAR_INT, 0);
                    if (sound == 415) {
                        wrapper.cancel();
                    } else if (sound >= 416) {
                        wrapper.set(Type.VAR_INT, 0, sound - 1);
                    }
                });
            }
        });
    }
}

