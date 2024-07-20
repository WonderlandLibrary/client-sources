/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.data;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_14
extends CommandRewriter<ClientboundPackets1_14> {
    public CommandRewriter1_14(Protocol1_13_2To1_14 protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:nbt_tag", wrapper -> wrapper.write(Type.VAR_INT, 2));
        this.parserHandlers.put("minecraft:time", wrapper -> {
            wrapper.write(Type.BYTE, (byte)1);
            wrapper.write(Type.INT, 0);
        });
    }

    @Override
    public @Nullable String handleArgumentType(String argumentType) {
        switch (argumentType) {
            case "minecraft:nbt_compound_tag": {
                return "minecraft:nbt";
            }
            case "minecraft:nbt_tag": {
                return "brigadier:string";
            }
            case "minecraft:time": {
                return "brigadier:integer";
            }
        }
        return super.handleArgumentType(argumentType);
    }
}

