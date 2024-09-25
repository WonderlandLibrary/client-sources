/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.rewriters;

import us.myles.ViaVersion.api.protocol.ClientboundPacketType;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.IdRewriteFunction;
import us.myles.ViaVersion.api.type.Type;

public class SoundRewriter {
    protected final Protocol protocol;
    protected final IdRewriteFunction idRewriter;

    public SoundRewriter(Protocol protocol) {
        this.protocol = protocol;
        this.idRewriter = id -> protocol.getMappingData().getSoundMappings().getNewId(id);
    }

    public SoundRewriter(Protocol protocol, IdRewriteFunction idRewriter) {
        this.protocol = protocol;
        this.idRewriter = idRewriter;
    }

    public void registerSound(ClientboundPacketType packetType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int soundId = wrapper.get(Type.VAR_INT, 0);
                    int mappedId = SoundRewriter.this.idRewriter.rewrite(soundId);
                    if (mappedId == -1) {
                        wrapper.cancel();
                    } else if (soundId != mappedId) {
                        wrapper.set(Type.VAR_INT, 0, mappedId);
                    }
                });
            }
        });
    }
}

