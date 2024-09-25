/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_14to1_14_1.packets;

import nl.matsv.viabackwards.api.entities.storage.MetaStorage;
import nl.matsv.viabackwards.api.rewriters.LegacyEntityRewriter;
import nl.matsv.viabackwards.protocol.protocol1_14to1_14_1.Protocol1_14To1_14_1;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.entities.Entity1_14Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Types1_14;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;

public class EntityPackets1_14_1
extends LegacyEntityRewriter<Protocol1_14To1_14_1> {
    public EntityPackets1_14_1(Protocol1_14To1_14_1 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        this.registerExtraTracker(ClientboundPackets1_14.SPAWN_EXPERIENCE_ORB, Entity1_14Types.EntityType.EXPERIENCE_ORB);
        this.registerExtraTracker(ClientboundPackets1_14.SPAWN_GLOBAL_ENTITY, Entity1_14Types.EntityType.LIGHTNING_BOLT);
        this.registerExtraTracker(ClientboundPackets1_14.SPAWN_PAINTING, Entity1_14Types.EntityType.PAINTING);
        this.registerExtraTracker(ClientboundPackets1_14.SPAWN_PLAYER, Entity1_14Types.EntityType.PLAYER);
        this.registerExtraTracker(ClientboundPackets1_14.JOIN_GAME, Entity1_14Types.EntityType.PLAYER, Type.INT);
        this.registerEntityDestroy(ClientboundPackets1_14.DESTROY_ENTITIES);
        ((Protocol1_14To1_14_1)this.protocol).registerOutgoing(ClientboundPackets1_14.SPAWN_ENTITY, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(EntityPackets1_14_1.this.getTrackerHandler());
            }
        });
        ((Protocol1_14To1_14_1)this.protocol).registerOutgoing(ClientboundPackets1_14.SPAWN_MOB, new PacketRemapper(){

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
                this.map(Types1_14.METADATA_LIST);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = wrapper.get(Type.VAR_INT, 0);
                        int type = wrapper.get(Type.VAR_INT, 1);
                        EntityPackets1_14_1.this.addTrackedEntity(wrapper, entityId, Entity1_14Types.getTypeFromId(type));
                        MetaStorage storage = new MetaStorage(wrapper.get(Types1_14.METADATA_LIST, 0));
                        EntityPackets1_14_1.this.handleMeta(wrapper.user(), entityId, storage);
                    }
                });
            }
        });
        this.registerMetadataRewriter(ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST);
    }

    @Override
    protected void registerRewrites() {
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.VILLAGER, 15).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.VILLAGER, 16).handleIndexChange(15);
        this.registerMetaHandler().filter((EntityType)Entity1_14Types.EntityType.WANDERING_TRADER, 15).removed();
    }

    @Override
    protected EntityType getTypeFromId(int typeId) {
        return Entity1_14Types.getTypeFromId(typeId);
    }
}

