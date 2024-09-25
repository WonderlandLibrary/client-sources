/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_12_2to1_12_1;

import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import us.myles.ViaVersion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;

public class Protocol1_12_2To1_12_1
extends Protocol {
    public Protocol1_12_2To1_12_1() {
        super(ClientboundPackets1_12_1.class, ClientboundPackets1_12_1.class, ServerboundPackets1_12_1.class, ServerboundPackets1_12_1.class);
    }

    @Override
    protected void registerPackets() {
        this.registerOutgoing(ClientboundPackets1_12_1.KEEP_ALIVE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.LONG);
            }
        });
        this.registerIncoming(ServerboundPackets1_12_1.KEEP_ALIVE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.LONG, Type.VAR_INT);
            }
        });
    }
}

