/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_15to1_14_4.packets;

import java.util.List;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.entities.Entity1_15Types;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Types1_14;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.metadata.MetadataRewriter1_15To1_14_4;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.storage.EntityTracker1_15;

public class EntityPackets {
    public static void register(Protocol1_15To1_14_4 protocol) {
        final MetadataRewriter1_15To1_14_4 metadataRewriter = protocol.get(MetadataRewriter1_15To1_14_4.class);
        metadataRewriter.registerSpawnTrackerWithData(ClientboundPackets1_14.SPAWN_ENTITY, Entity1_15Types.EntityType.FALLING_BLOCK);
        protocol.registerOutgoing(ClientboundPackets1_14.SPAWN_MOB, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(metadataRewriter.getTracker());
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Type.VAR_INT, 0);
                    List<Metadata> metadata = wrapper.read(Types1_14.METADATA_LIST);
                    metadataRewriter.handleMetadata(entityId, metadata, wrapper.user());
                    PacketWrapper metadataUpdate = wrapper.create(68);
                    metadataUpdate.write(Type.VAR_INT, entityId);
                    metadataUpdate.write(Types1_14.METADATA_LIST, metadata);
                    metadataUpdate.send(Protocol1_15To1_14_4.class);
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_14.SPAWN_PLAYER, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Type.VAR_INT, 0);
                    Entity1_15Types.EntityType entityType = Entity1_15Types.EntityType.PLAYER;
                    wrapper.user().get(EntityTracker1_15.class).addEntity(entityId, entityType);
                    List<Metadata> metadata = wrapper.read(Types1_14.METADATA_LIST);
                    metadataRewriter.handleMetadata(entityId, metadata, wrapper.user());
                    PacketWrapper metadataUpdate = wrapper.create(68);
                    metadataUpdate.write(Type.VAR_INT, entityId);
                    metadataUpdate.write(Types1_14.METADATA_LIST, metadata);
                    metadataUpdate.send(Protocol1_15To1_14_4.class);
                });
            }
        });
        metadataRewriter.registerMetadataRewriter(ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST);
        metadataRewriter.registerEntityDestroy(ClientboundPackets1_14.DESTROY_ENTITIES);
    }

    public static int getNewEntityId(int oldId) {
        return oldId >= 4 ? oldId + 1 : oldId;
    }
}

