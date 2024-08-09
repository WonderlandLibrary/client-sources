/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;

public class SoundRewriter<C extends ClientboundPacketType> {
    protected final Protocol<C, ?, ?, ?> protocol;
    protected final IdRewriteFunction idRewriter;

    public SoundRewriter(Protocol<C, ?, ?, ?> protocol) {
        this.protocol = protocol;
        this.idRewriter = arg_0 -> SoundRewriter.lambda$new$0(protocol, arg_0);
    }

    public SoundRewriter(Protocol<C, ?, ?, ?> protocol, IdRewriteFunction idRewriteFunction) {
        this.protocol = protocol;
        this.idRewriter = idRewriteFunction;
    }

    public void registerSound(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final SoundRewriter this$0;
            {
                this.this$0 = soundRewriter;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(this.this$0.getSoundHandler());
            }
        });
    }

    public void register1_19_3Sound(C c) {
        this.protocol.registerClientbound(c, this::lambda$register1_19_3Sound$1);
    }

    public PacketHandler getSoundHandler() {
        return this::lambda$getSoundHandler$2;
    }

    private void lambda$getSoundHandler$2(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.get(Type.VAR_INT, 0);
        int n2 = this.idRewriter.rewrite(n);
        if (n2 == -1) {
            packetWrapper.cancel();
        } else if (n != n2) {
            packetWrapper.set(Type.VAR_INT, 0, n2);
        }
    }

    private void lambda$register1_19_3Sound$1(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        if (n == 0) {
            packetWrapper.write(Type.VAR_INT, 0);
            return;
        }
        int n2 = this.idRewriter.rewrite(n - 1);
        if (n2 == -1) {
            packetWrapper.cancel();
            return;
        }
        packetWrapper.write(Type.VAR_INT, n2 + 1);
    }

    private static int lambda$new$0(Protocol protocol, int n) {
        return protocol.getMappingData().getSoundMappings().getNewId(n);
    }
}

