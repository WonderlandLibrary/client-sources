/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data;

import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_16
extends CommandRewriter<ClientboundPackets1_16> {
    public CommandRewriter1_16(Protocol1_15_2To1_16 protocol) {
        super(protocol);
    }

    @Override
    public @Nullable String handleArgumentType(String argumentType) {
        if (argumentType.equals("minecraft:uuid")) {
            return "minecraft:game_profile";
        }
        return super.handleArgumentType(argumentType);
    }
}

