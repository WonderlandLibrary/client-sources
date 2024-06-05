/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.data;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_13_1
extends CommandRewriter {
    public CommandRewriter1_13_1(Protocol protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:dimension", wrapper -> wrapper.write(Type.VAR_INT, 0));
    }

    @Override
    protected @Nullable String handleArgumentType(String argumentType) {
        if (argumentType.equals("minecraft:column_pos")) {
            return "minecraft:vec2";
        }
        if (!argumentType.equals("minecraft:dimension")) return super.handleArgumentType(argumentType);
        return "brigadier:string";
    }
}

