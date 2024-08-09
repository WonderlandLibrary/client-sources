/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.CommandRewriter;

public class CommandRewriter1_19_4<C extends ClientboundPacketType>
extends CommandRewriter<C> {
    public CommandRewriter1_19_4(Protocol<C, ?, ?, ?> protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:time", CommandRewriter1_19_4::lambda$new$0);
    }

    private static void lambda$new$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.INT);
    }
}

