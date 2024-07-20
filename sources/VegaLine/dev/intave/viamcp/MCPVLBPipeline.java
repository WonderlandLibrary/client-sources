/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave.viamcp;

import com.viaversion.viaversion.api.connection.UserConnection;
import dev.intave.vialoadingbase.netty.VLBPipeline;

public class MCPVLBPipeline
extends VLBPipeline {
    public MCPVLBPipeline(UserConnection user) {
        super(user);
    }

    @Override
    public String getDecoderHandlerName() {
        return "decoder";
    }

    @Override
    public String getEncoderHandlerName() {
        return "encoder";
    }

    @Override
    public String getDecompressionHandlerName() {
        return "decompress";
    }

    @Override
    public String getCompressionHandlerName() {
        return "compress";
    }
}

