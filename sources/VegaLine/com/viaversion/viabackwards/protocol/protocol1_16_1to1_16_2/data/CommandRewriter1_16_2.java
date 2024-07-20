/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data;

import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_16_2
extends CommandRewriter<ClientboundPackets1_16_2> {
    public CommandRewriter1_16_2(Protocol1_16_1To1_16_2 protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:angle", wrapper -> wrapper.write(Type.VAR_INT, 0));
    }

    @Override
    public @Nullable String handleArgumentType(String argumentType) {
        if (argumentType.equals("minecraft:angle")) {
            return "brigadier:string";
        }
        return super.handleArgumentType(argumentType);
    }
}

