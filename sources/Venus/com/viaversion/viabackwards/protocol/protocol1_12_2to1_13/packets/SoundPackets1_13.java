/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.NamedSoundMapping;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;

public class SoundPackets1_13
extends RewriterBase<Protocol1_12_2To1_13> {
    private static final String[] SOUND_SOURCES = new String[]{"master", "music", "record", "weather", "block", "hostile", "neutral", "player", "ambient", "voice"};

    public SoundPackets1_13(Protocol1_12_2To1_13 protocol1_12_2To1_13) {
        super(protocol1_12_2To1_13);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.NAMED_SOUND, this::lambda$registerPackets$0);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.STOP_SOUND, ClientboundPackets1_12_1.PLUGIN_MESSAGE, this::lambda$registerPackets$1);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SOUND, new PacketHandlers(this){
            final SoundPackets1_13 this$0;
            {
                this.this$0 = soundPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                int n2 = ((Protocol1_12_2To1_13)SoundPackets1_13.access$000(this.this$0)).getMappingData().getSoundMappings().getNewId(n);
                if (n2 == -1) {
                    packetWrapper.cancel();
                } else {
                    packetWrapper.set(Type.VAR_INT, 0, n2);
                }
            }
        });
    }

    private void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        String string;
        packetWrapper.write(Type.STRING, "MC|StopSound");
        byte by = packetWrapper.read(Type.BYTE);
        String string2 = (by & 1) != 0 ? SOUND_SOURCES[packetWrapper.read(Type.VAR_INT)] : "";
        if ((by & 2) != 0) {
            String string3 = packetWrapper.read(Type.STRING);
            string = ((Protocol1_12_2To1_13)this.protocol).getMappingData().getMappedNamedSound(string3);
            if (string == null) {
                string = "";
            }
        } else {
            string = "";
        }
        packetWrapper.write(Type.STRING, string2);
        packetWrapper.write(Type.STRING, string);
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        String string = packetWrapper.read(Type.STRING);
        String string2 = NamedSoundMapping.getOldId(string);
        if (string2 != null || (string2 = ((Protocol1_12_2To1_13)this.protocol).getMappingData().getMappedNamedSound(string)) != null) {
            packetWrapper.write(Type.STRING, string2);
        } else {
            packetWrapper.write(Type.STRING, string);
        }
    }

    static Protocol access$000(SoundPackets1_13 soundPackets1_13) {
        return soundPackets1_13.protocol;
    }
}

