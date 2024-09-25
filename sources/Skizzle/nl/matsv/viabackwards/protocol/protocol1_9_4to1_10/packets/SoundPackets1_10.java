/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_9_4to1_10.packets;

import nl.matsv.viabackwards.api.rewriters.LegacySoundRewriter;
import nl.matsv.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueTransformer;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;

public class SoundPackets1_10
extends LegacySoundRewriter<Protocol1_9_4To1_10> {
    protected static ValueTransformer<Float, Short> toOldPitch = new ValueTransformer<Float, Short>(Type.UNSIGNED_BYTE){

        @Override
        public Short transform(PacketWrapper packetWrapper, Float inputValue) throws Exception {
            return (short)Math.round(inputValue.floatValue() * 63.5f);
        }
    };

    public SoundPackets1_10(Protocol1_9_4To1_10 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_9_4To1_10)this.protocol).registerOutgoing(ClientboundPackets1_9_3.NAMED_SOUND, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT, toOldPitch);
            }
        });
        ((Protocol1_9_4To1_10)this.protocol).registerOutgoing(ClientboundPackets1_9_3.SOUND, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT, toOldPitch);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int oldId = wrapper.get(Type.VAR_INT, 0);
                        int newId = SoundPackets1_10.this.handleSounds(oldId);
                        if (newId == -1) {
                            wrapper.cancel();
                            return;
                        }
                        if (SoundPackets1_10.this.hasPitch(oldId)) {
                            wrapper.set(Type.UNSIGNED_BYTE, 0, (short)Math.round(SoundPackets1_10.this.handlePitch(oldId) * 63.5f));
                        }
                        wrapper.set(Type.VAR_INT, 0, newId);
                    }
                });
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.added(24, -1);
        this.added(249, 381);
        this.added(250, 385);
        this.added(251, 386);
        this.added(252, 388);
        this.added(301, 381, 0.6f);
        this.added(302, 381, 1.9f);
        this.added(303, 385, 0.7f);
        this.added(304, 309, 0.6f);
        this.added(305, 240, 0.6f);
        this.added(306, 374, 1.2f);
        this.added(365, 320);
        this.added(366, 321);
        this.added(367, 322);
        this.added(368, 324);
        this.added(387, 320);
        this.added(388, 321);
        this.added(389, 322);
        this.added(390, 324);
    }
}

