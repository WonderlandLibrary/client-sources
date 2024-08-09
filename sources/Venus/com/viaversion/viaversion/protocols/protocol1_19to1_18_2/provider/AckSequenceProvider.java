/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;

public class AckSequenceProvider
implements Provider {
    public void handleSequence(UserConnection userConnection, int n) throws Exception {
        PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_19.BLOCK_CHANGED_ACK, userConnection);
        packetWrapper.write(Type.VAR_INT, n);
        packetWrapper.scheduleSend(Protocol1_19To1_18_2.class);
    }
}

