/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.data;

import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
import com.viaversion.viaversion.rewriter.CommandRewriter;

public final class CommandRewriter1_19
extends CommandRewriter<ClientboundPackets1_19> {
    public CommandRewriter1_19(Protocol1_18_2To1_19 protocol1_18_2To1_19) {
        super(protocol1_18_2To1_19);
        this.parserHandlers.put("minecraft:template_mirror", CommandRewriter1_19::lambda$new$0);
        this.parserHandlers.put("minecraft:template_rotation", CommandRewriter1_19::lambda$new$1);
    }

    private static void lambda$new$1(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.VAR_INT, 0);
    }

    private static void lambda$new$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.VAR_INT, 0);
    }
}

