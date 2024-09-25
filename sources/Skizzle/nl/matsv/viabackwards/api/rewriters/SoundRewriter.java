/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.api.rewriters;

import nl.matsv.viabackwards.api.BackwardsProtocol;
import us.myles.ViaVersion.api.protocol.ClientboundPacketType;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;

public class SoundRewriter
extends us.myles.ViaVersion.api.rewriters.SoundRewriter {
    private final BackwardsProtocol protocol;

    public SoundRewriter(BackwardsProtocol protocol) {
        super(protocol);
        this.protocol = protocol;
    }

    public void registerNamedSound(ClientboundPacketType packetType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(wrapper -> {
                    String mappedId;
                    String soundId = wrapper.get(Type.STRING, 0);
                    if (soundId.startsWith("minecraft:")) {
                        soundId = soundId.substring(10);
                    }
                    if ((mappedId = SoundRewriter.this.protocol.getMappingData().getMappedNamedSound(soundId)) == null) {
                        return;
                    }
                    if (!mappedId.isEmpty()) {
                        wrapper.set(Type.STRING, 0, mappedId);
                    } else {
                        wrapper.cancel();
                    }
                });
            }
        });
    }

    public void registerStopSound(ClientboundPacketType packetType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    String mappedId;
                    String soundId;
                    byte flags = wrapper.passthrough(Type.BYTE);
                    if ((flags & 2) == 0) {
                        return;
                    }
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Type.VAR_INT);
                    }
                    if ((soundId = wrapper.read(Type.STRING)).startsWith("minecraft:")) {
                        soundId = soundId.substring(10);
                    }
                    if ((mappedId = SoundRewriter.this.protocol.getMappingData().getMappedNamedSound(soundId)) == null) {
                        wrapper.write(Type.STRING, soundId);
                        return;
                    }
                    if (!mappedId.isEmpty()) {
                        wrapper.write(Type.STRING, mappedId);
                    } else {
                        wrapper.cancel();
                    }
                });
            }
        });
    }
}

