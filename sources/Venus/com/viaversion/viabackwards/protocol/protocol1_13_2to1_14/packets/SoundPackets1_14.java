/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.EntityPositionStorage1_14;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;

public class SoundPackets1_14
extends RewriterBase<Protocol1_13_2To1_14> {
    public SoundPackets1_14(Protocol1_13_2To1_14 protocol1_13_2To1_14) {
        super(protocol1_13_2To1_14);
    }

    @Override
    protected void registerPackets() {
        SoundRewriter<ClientboundPackets1_14> soundRewriter = new SoundRewriter<ClientboundPackets1_14>((BackwardsProtocol)this.protocol);
        soundRewriter.registerSound(ClientboundPackets1_14.SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_14.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_14.STOP_SOUND);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ENTITY_SOUND, null, this::lambda$registerPackets$0);
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        EntityPositionStorage1_14 entityPositionStorage1_14;
        packetWrapper.cancel();
        int n = packetWrapper.read(Type.VAR_INT);
        int n2 = ((Protocol1_13_2To1_14)this.protocol).getMappingData().getSoundMappings().getNewId(n);
        if (n2 == -1) {
            return;
        }
        int n3 = packetWrapper.read(Type.VAR_INT);
        int n4 = packetWrapper.read(Type.VAR_INT);
        StoredEntityData storedEntityData = packetWrapper.user().getEntityTracker(((Protocol1_13_2To1_14)this.protocol).getClass()).entityData(n4);
        if (storedEntityData == null || (entityPositionStorage1_14 = storedEntityData.get(EntityPositionStorage1_14.class)) == null) {
            ViaBackwards.getPlatform().getLogger().warning("Untracked entity with id " + n4);
            return;
        }
        float f = packetWrapper.read(Type.FLOAT).floatValue();
        float f2 = packetWrapper.read(Type.FLOAT).floatValue();
        int n5 = (int)(entityPositionStorage1_14.getX() * 8.0);
        int n6 = (int)(entityPositionStorage1_14.getY() * 8.0);
        int n7 = (int)(entityPositionStorage1_14.getZ() * 8.0);
        PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_13.SOUND);
        packetWrapper2.write(Type.VAR_INT, n2);
        packetWrapper2.write(Type.VAR_INT, n3);
        packetWrapper2.write(Type.INT, n5);
        packetWrapper2.write(Type.INT, n6);
        packetWrapper2.write(Type.INT, n7);
        packetWrapper2.write(Type.FLOAT, Float.valueOf(f));
        packetWrapper2.write(Type.FLOAT, Float.valueOf(f2));
        packetWrapper2.send(Protocol1_13_2To1_14.class);
    }
}

