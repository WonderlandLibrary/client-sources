/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_13_2to1_14.packets;

import nl.matsv.viabackwards.ViaBackwards;
import nl.matsv.viabackwards.api.entities.storage.EntityTracker;
import nl.matsv.viabackwards.api.rewriters.Rewriter;
import nl.matsv.viabackwards.api.rewriters.SoundRewriter;
import nl.matsv.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import nl.matsv.viabackwards.protocol.protocol1_13_2to1_14.storage.EntityPositionStorage1_14;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;

public class SoundPackets1_14
extends Rewriter<Protocol1_13_2To1_14> {
    public SoundPackets1_14(Protocol1_13_2To1_14 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        SoundRewriter soundRewriter = new SoundRewriter(this.protocol);
        soundRewriter.registerSound(ClientboundPackets1_14.SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_14.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_14.STOP_SOUND);
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.ENTITY_SOUND, null, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    EntityPositionStorage1_14 entityStorage;
                    wrapper.cancel();
                    int soundId = wrapper.read(Type.VAR_INT);
                    int newId = ((Protocol1_13_2To1_14)SoundPackets1_14.this.protocol).getMappingData().getSoundMappings().getNewId(soundId);
                    if (newId == -1) {
                        return;
                    }
                    int category = wrapper.read(Type.VAR_INT);
                    int entityId = wrapper.read(Type.VAR_INT);
                    EntityTracker.StoredEntity storedEntity = wrapper.user().get(EntityTracker.class).get(SoundPackets1_14.this.protocol).getEntity(entityId);
                    if (storedEntity == null || (entityStorage = storedEntity.get(EntityPositionStorage1_14.class)) == null) {
                        ViaBackwards.getPlatform().getLogger().warning("Untracked entity with id " + entityId);
                        return;
                    }
                    float volume = wrapper.read(Type.FLOAT).floatValue();
                    float pitch = wrapper.read(Type.FLOAT).floatValue();
                    int x = (int)(entityStorage.getX() * 8.0);
                    int y = (int)(entityStorage.getY() * 8.0);
                    int z = (int)(entityStorage.getZ() * 8.0);
                    PacketWrapper soundPacket = wrapper.create(77);
                    soundPacket.write(Type.VAR_INT, newId);
                    soundPacket.write(Type.VAR_INT, category);
                    soundPacket.write(Type.INT, x);
                    soundPacket.write(Type.INT, y);
                    soundPacket.write(Type.INT, z);
                    soundPacket.write(Type.FLOAT, Float.valueOf(volume));
                    soundPacket.write(Type.FLOAT, Float.valueOf(pitch));
                    soundPacket.send(Protocol1_13_2To1_14.class);
                });
            }
        });
    }
}

