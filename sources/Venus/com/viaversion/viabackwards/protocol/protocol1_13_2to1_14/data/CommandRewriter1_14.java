/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.data;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_14
extends CommandRewriter<ClientboundPackets1_14> {
    public CommandRewriter1_14(Protocol1_13_2To1_14 protocol1_13_2To1_14) {
        super(protocol1_13_2To1_14);
        this.parserHandlers.put("minecraft:nbt_tag", CommandRewriter1_14::lambda$new$0);
        this.parserHandlers.put("minecraft:time", CommandRewriter1_14::lambda$new$1);
    }

    @Override
    public @Nullable String handleArgumentType(String string) {
        switch (string) {
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
        return super.handleArgumentType(string);
    }

    private static void lambda$new$1(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.BYTE, (byte)1);
        packetWrapper.write(Type.INT, 0);
    }

    private static void lambda$new$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.VAR_INT, 2);
    }
}

