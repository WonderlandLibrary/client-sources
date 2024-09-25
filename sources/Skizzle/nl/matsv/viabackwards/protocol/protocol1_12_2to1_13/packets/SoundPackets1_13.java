/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.packets;

import nl.matsv.viabackwards.ViaBackwards;
import nl.matsv.viabackwards.api.rewriters.Rewriter;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.data.NamedSoundMapping;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;

public class SoundPackets1_13
extends Rewriter<Protocol1_12_2To1_13> {
    private static final String[] SOUND_SOURCES = new String[]{"master", "music", "record", "weather", "block", "hostile", "neutral", "player", "ambient", "voice"};

    public SoundPackets1_13(Protocol1_12_2To1_13 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.NAMED_SOUND, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(wrapper -> {
                    String oldSound;
                    String newSound = wrapper.get(Type.STRING, 0);
                    if (newSound.startsWith("minecraft:")) {
                        newSound = newSound.substring(10);
                    }
                    if ((oldSound = NamedSoundMapping.getOldId(newSound)) != null || (oldSound = ((Protocol1_12_2To1_13)SoundPackets1_13.this.protocol).getMappingData().getMappedNamedSound(newSound)) != null) {
                        wrapper.set(Type.STRING, 0, oldSound);
                    } else if (!Via.getConfig().isSuppressConversionWarnings()) {
                        ViaBackwards.getPlatform().getLogger().warning("Unknown named sound in 1.13->1.12 protocol: " + newSound);
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.STOP_SOUND, ClientboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    String sound;
                    wrapper.write(Type.STRING, "MC|StopSound");
                    byte flags = wrapper.read(Type.BYTE);
                    String source = (flags & 1) != 0 ? SOUND_SOURCES[wrapper.read(Type.VAR_INT)] : "";
                    if ((flags & 2) != 0) {
                        String newSound = wrapper.read(Type.STRING);
                        if (newSound.startsWith("minecraft:")) {
                            newSound = newSound.substring(10);
                        }
                        if ((sound = ((Protocol1_12_2To1_13)SoundPackets1_13.this.protocol).getMappingData().getMappedNamedSound(newSound)) == null) {
                            sound = "";
                        }
                    } else {
                        sound = "";
                    }
                    wrapper.write(Type.STRING, source);
                    wrapper.write(Type.STRING, sound);
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.SOUND, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int newSound = wrapper.get(Type.VAR_INT, 0);
                    int oldSound = ((Protocol1_12_2To1_13)SoundPackets1_13.this.protocol).getMappingData().getSoundMappings().getNewId(newSound);
                    if (oldSound == -1) {
                        wrapper.cancel();
                    } else {
                        wrapper.set(Type.VAR_INT, 0, oldSound);
                    }
                });
            }
        });
    }
}

