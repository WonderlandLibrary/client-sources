/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_10to1_11.packets;

import nl.matsv.viabackwards.api.rewriters.LegacySoundRewriter;
import nl.matsv.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;

public class SoundPackets1_11
extends LegacySoundRewriter<Protocol1_10To1_11> {
    public SoundPackets1_11(Protocol1_10To1_11 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_10To1_11)this.protocol).registerOutgoing(ClientboundPackets1_9_3.NAMED_SOUND, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerOutgoing(ClientboundPackets1_9_3.SOUND, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int oldId = wrapper.get(Type.VAR_INT, 0);
                        int newId = SoundPackets1_11.this.handleSounds(oldId);
                        if (newId == -1) {
                            wrapper.cancel();
                        } else {
                            if (SoundPackets1_11.this.hasPitch(oldId)) {
                                wrapper.set(Type.FLOAT, 1, Float.valueOf(SoundPackets1_11.this.handlePitch(oldId)));
                            }
                            wrapper.set(Type.VAR_INT, 0, newId);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.added(85, 121, 0.5f);
        this.added(86, 122, 0.5f);
        this.added(176, 227);
        this.soundRewrites.put(196, new LegacySoundRewriter.SoundData(193, false, -1.0f, false));
        this.added(197, 402, 1.8f);
        this.added(198, 370, 0.4f);
        this.added(199, 255, 1.3f);
        this.added(200, 418, 1.3f);
        this.added(201, 372, 1.3f);
        this.added(202, 137, 0.8f);
        this.added(203, 78, 2.0f);
        this.added(204, 376, 0.6f);
        this.added(279, 230, 1.5f);
        this.added(280, 231, 1.6f);
        this.added(281, 164);
        this.added(282, 165, 1.2f);
        this.added(283, 235, 1.1f);
        this.added(284, 166);
        this.added(285, 323, 1.7f);
        this.added(286, 241, 0.8f);
        this.added(287, 423, 0.5f);
        this.added(296, 164);
        this.added(390, 233, 0.1f);
        this.added(391, 168, 2.0f);
        this.added(392, 144, 0.5f);
        this.added(393, 146, 2.0f);
        this.added(400, 370, 0.7f);
        this.added(401, 371, 0.8f);
        this.added(402, 372, 0.7f);
        this.added(450, 423, 1.1f);
        this.added(455, 427, 1.1f);
        this.added(470, 2, 0.5f);
    }
}

