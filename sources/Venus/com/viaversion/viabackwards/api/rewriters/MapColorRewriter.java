/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;

public final class MapColorRewriter {
    public static PacketHandler getRewriteHandler(IdRewriteFunction idRewriteFunction) {
        return arg_0 -> MapColorRewriter.lambda$getRewriteHandler$0(idRewriteFunction, arg_0);
    }

    private static void lambda$getRewriteHandler$0(IdRewriteFunction idRewriteFunction, PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2 = packetWrapper.passthrough(Type.VAR_INT);
        for (n = 0; n < n2; ++n) {
            packetWrapper.passthrough(Type.VAR_INT);
            packetWrapper.passthrough(Type.BYTE);
            packetWrapper.passthrough(Type.BYTE);
            packetWrapper.passthrough(Type.BYTE);
            if (!packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) continue;
            packetWrapper.passthrough(Type.COMPONENT);
        }
        n = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
        if (n < 1) {
            return;
        }
        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
        byte[] byArray = packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
        for (int i = 0; i < byArray.length; ++i) {
            int n3 = byArray[i] & 0xFF;
            int n4 = idRewriteFunction.rewrite(n3);
            if (n4 == -1) continue;
            byArray[i] = (byte)n4;
        }
    }
}

