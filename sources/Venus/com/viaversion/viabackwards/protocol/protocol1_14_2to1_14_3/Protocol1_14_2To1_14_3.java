/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_14_2to1_14_3;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public class Protocol1_14_2To1_14_3
extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14> {
    public Protocol1_14_2To1_14_3() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_14.TRADE_LIST, Protocol1_14_2To1_14_3::lambda$registerPackets$0);
        RecipeRewriter<ClientboundPackets1_14> recipeRewriter = new RecipeRewriter<ClientboundPackets1_14>(this);
        this.registerClientbound(ClientboundPackets1_14.DECLARE_RECIPES, arg_0 -> Protocol1_14_2To1_14_3.lambda$registerPackets$1(recipeRewriter, arg_0));
    }

    private static void lambda$registerPackets$1(RecipeRewriter recipeRewriter, PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            String string = packetWrapper.read(Type.STRING);
            String string2 = string.replace("minecraft:", "");
            String string3 = packetWrapper.read(Type.STRING);
            if (string2.equals("crafting_special_repairitem")) {
                ++n2;
                continue;
            }
            packetWrapper.write(Type.STRING, string);
            packetWrapper.write(Type.STRING, string3);
            recipeRewriter.handleRecipeType(packetWrapper, string2);
        }
        packetWrapper.set(Type.VAR_INT, 0, n - n2);
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
        int n = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
        for (int i = 0; i < n; ++i) {
            packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
            packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
            if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
            }
            packetWrapper.passthrough(Type.BOOLEAN);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.FLOAT);
        }
        packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.passthrough(Type.BOOLEAN);
        packetWrapper.read(Type.BOOLEAN);
    }
}

