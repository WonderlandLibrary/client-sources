/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.data;

import com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.Protocol1_18To1_18_2;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class CommandRewriter1_18_2
extends CommandRewriter<ClientboundPackets1_18> {
    public CommandRewriter1_18_2(Protocol1_18To1_18_2 protocol1_18To1_18_2) {
        super(protocol1_18To1_18_2);
        this.parserHandlers.put("minecraft:resource", CommandRewriter1_18_2::lambda$new$0);
        this.parserHandlers.put("minecraft:resource_or_tag", CommandRewriter1_18_2::lambda$new$1);
    }

    @Override
    public @Nullable String handleArgumentType(String string) {
        if (string.equals("minecraft:resource") || string.equals("minecraft:resource_or_tag")) {
            return "brigadier:string";
        }
        return super.handleArgumentType(string);
    }

    private static void lambda$new$1(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.read(Type.STRING);
        packetWrapper.write(Type.VAR_INT, 1);
    }

    private static void lambda$new$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.read(Type.STRING);
        packetWrapper.write(Type.VAR_INT, 1);
    }
}

