/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.data;

import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_13_1
extends CommandRewriter<ClientboundPackets1_13> {
    public CommandRewriter1_13_1(Protocol1_13To1_13_1 protocol1_13To1_13_1) {
        super(protocol1_13To1_13_1);
        this.parserHandlers.put("minecraft:dimension", CommandRewriter1_13_1::lambda$new$0);
    }

    @Override
    public @Nullable String handleArgumentType(String string) {
        if (string.equals("minecraft:column_pos")) {
            return "minecraft:vec2";
        }
        if (string.equals("minecraft:dimension")) {
            return "brigadier:string";
        }
        return super.handleArgumentType(string);
    }

    private static void lambda$new$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.VAR_INT, 0);
    }
}

