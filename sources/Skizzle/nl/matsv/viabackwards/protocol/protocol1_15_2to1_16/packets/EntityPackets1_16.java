/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_15_2to1_16.packets;

import nl.matsv.viabackwards.api.rewriters.EntityRewriter;
import nl.matsv.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.entities.Entity1_15Types;
import us.myles.ViaVersion.api.entities.Entity1_16Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.MetaType;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_14;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueTransformer;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.Particle;
import us.myles.ViaVersion.api.type.types.version.Types1_14;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import us.myles.viaversion.libs.gson.JsonElement;

public class EntityPackets1_16
extends EntityRewriter<Protocol1_15_2To1_16> {
    private final ValueTransformer<String, Integer> dimensionTransformer = new ValueTransformer<String, Integer>(Type.STRING, Type.INT){

        @Override
        public Integer transform(PacketWrapper wrapper, String input) throws Exception {
            switch (input) {
                case "minecraft:the_nether": {
                    return -1;
                }
                default: {
                    return 0;
                }
                case "minecraft:the_end": 
            }
            return 1;
        }
    };

    public EntityPackets1_16(Protocol1_15_2To1_16 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        this.registerSpawnTrackerWithData(ClientboundPackets1_16.SPAWN_ENTITY, Entity1_16Types.EntityType.FALLING_BLOCK);
        this.registerSpawnTracker(ClientboundPackets1_16.SPAWN_MOB);
        ((Protocol1_15_2To1_16)this.protocol).registerOutgoing(ClientboundPackets1_16.RESPAWN, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(EntityPackets1_16.this.dimensionTransformer);
                this.map(Type.STRING, Type.NOTHING);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE, Type.NOTHING);
                this.handler(wrapper -> {
                    ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                    int dimension = wrapper.get(Type.INT, 0);
                    if (clientWorld.getEnvironment() != null && dimension == clientWorld.getEnvironment().getId()) {
                        PacketWrapper packet = wrapper.create(ClientboundPackets1_15.RESPAWN.ordinal());
                        packet.write(Type.INT, dimension == 0 ? -1 : 0);
                        packet.write(Type.LONG, 0L);
                        packet.write(Type.UNSIGNED_BYTE, (short)0);
                        packet.write(Type.STRING, "default");
                        packet.send(Protocol1_15_2To1_16.class, true, true);
                    }
                    clientWorld.setEnvironment(dimension);
                    wrapper.write(Type.STRING, "default");
                    wrapper.read(Type.BOOLEAN);
                    if (wrapper.read(Type.BOOLEAN).booleanValue()) {
                        wrapper.set(Type.STRING, 0, "flat");
                    }
                    wrapper.read(Type.BOOLEAN);
                });
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerOutgoing(ClientboundPackets1_16.JOIN_GAME, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE, Type.NOTHING);
                this.map(Type.STRING_ARRAY, Type.NOTHING);
                this.map(Type.NBT, Type.NOTHING);
                this.map(EntityPackets1_16.this.dimensionTransformer);
                this.map(Type.STRING, Type.NOTHING);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    ClientWorld clientChunks = wrapper.user().get(ClientWorld.class);
                    clientChunks.setEnvironment(wrapper.get(Type.INT, 1));
                    EntityPackets1_16.this.getEntityTracker(wrapper.user()).trackEntityType(wrapper.get(Type.INT, 0), Entity1_16Types.EntityType.PLAYER);
                    wrapper.write(Type.STRING, "default");
                    wrapper.passthrough(Type.VAR_INT);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.read(Type.BOOLEAN);
                    if (wrapper.read(Type.BOOLEAN).booleanValue()) {
                        wrapper.set(Type.STRING, 0, "flat");
                    }
                });
            }
        });
        this.registerExtraTracker(ClientboundPackets1_16.SPAWN_EXPERIENCE_ORB, Entity1_16Types.EntityType.EXPERIENCE_ORB);
        this.registerExtraTracker(ClientboundPackets1_16.SPAWN_PAINTING, Entity1_16Types.EntityType.PAINTING);
        this.registerExtraTracker(ClientboundPackets1_16.SPAWN_PLAYER, Entity1_16Types.EntityType.PLAYER);
        this.registerEntityDestroy(ClientboundPackets1_16.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_16.ENTITY_METADATA, Types1_14.METADATA_LIST);
        ((Protocol1_15_2To1_16)this.protocol).registerOutgoing(ClientboundPackets1_16.ENTITY_PROPERTIES, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    wrapper.passthrough(Type.VAR_INT);
                    int size = wrapper.passthrough(Type.INT);
                    for (int i = 0; i < size; ++i) {
                        String attributeIdentifier = wrapper.read(Type.STRING);
                        String oldKey = ((Protocol1_15_2To1_16)EntityPackets1_16.this.protocol).getMappingData().getAttributeMappings().get(attributeIdentifier);
                        wrapper.write(Type.STRING, oldKey != null ? oldKey : attributeIdentifier.replace("minecraft:", ""));
                        wrapper.passthrough(Type.DOUBLE);
                        int modifierSize = wrapper.passthrough(Type.VAR_INT);
                        for (int j = 0; j < modifierSize; ++j) {
                            wrapper.passthrough(Type.UUID);
                            wrapper.passthrough(Type.DOUBLE);
                            wrapper.passthrough(Type.BYTE);
                        }
                    }
                });
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerOutgoing(ClientboundPackets1_16.PLAYER_INFO, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(packetWrapper -> {
                    int action = packetWrapper.passthrough(Type.VAR_INT);
                    int playerCount = packetWrapper.passthrough(Type.VAR_INT);
                    for (int i = 0; i < playerCount; ++i) {
                        packetWrapper.passthrough(Type.UUID);
                        if (action == 0) {
                            packetWrapper.passthrough(Type.STRING);
                            int properties = packetWrapper.passthrough(Type.VAR_INT);
                            for (int j = 0; j < properties; ++j) {
                                packetWrapper.passthrough(Type.STRING);
                                packetWrapper.passthrough(Type.STRING);
                                if (!packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) continue;
                                packetWrapper.passthrough(Type.STRING);
                            }
                            packetWrapper.passthrough(Type.VAR_INT);
                            packetWrapper.passthrough(Type.VAR_INT);
                            if (!packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) continue;
                            ((Protocol1_15_2To1_16)EntityPackets1_16.this.protocol).getTranslatableRewriter().processText(packetWrapper.passthrough(Type.COMPONENT));
                            continue;
                        }
                        if (action == 1) {
                            packetWrapper.passthrough(Type.VAR_INT);
                            continue;
                        }
                        if (action == 2) {
                            packetWrapper.passthrough(Type.VAR_INT);
                            continue;
                        }
                        if (action != 3 || !packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) continue;
                        ((Protocol1_15_2To1_16)EntityPackets1_16.this.protocol).getTranslatableRewriter().processText(packetWrapper.passthrough(Type.COMPONENT));
                    }
                });
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.registerMetaHandler().handle(e -> {
            JsonElement text;
            Metadata meta = e.getData();
            MetaType type = meta.getMetaType();
            if (type == MetaType1_14.Slot) {
                meta.setValue(((Protocol1_15_2To1_16)this.protocol).getBlockItemPackets().handleItemToClient((Item)meta.getValue()));
            } else if (type == MetaType1_14.BlockID) {
                meta.setValue(((Protocol1_15_2To1_16)this.protocol).getMappingData().getNewBlockStateId((Integer)meta.getValue()));
            } else if (type == MetaType1_14.PARTICLE) {
                this.rewriteParticle((Particle)meta.getValue());
            } else if (type == MetaType1_14.OptChat && (text = (JsonElement)meta.getCastedValue()) != null) {
                ((Protocol1_15_2To1_16)this.protocol).getTranslatableRewriter().processText(text);
            }
            return meta;
        });
        this.mapEntityDirect(Entity1_16Types.EntityType.ZOMBIFIED_PIGLIN, Entity1_15Types.EntityType.ZOMBIE_PIGMAN);
        this.mapTypes(Entity1_16Types.EntityType.values(), Entity1_15Types.EntityType.class);
        this.mapEntity(Entity1_16Types.EntityType.HOGLIN, Entity1_16Types.EntityType.COW).jsonName("Hoglin");
        this.mapEntity(Entity1_16Types.EntityType.ZOGLIN, Entity1_16Types.EntityType.COW).jsonName("Zoglin");
        this.mapEntity(Entity1_16Types.EntityType.PIGLIN, Entity1_16Types.EntityType.ZOMBIFIED_PIGLIN).jsonName("Piglin");
        this.mapEntity(Entity1_16Types.EntityType.STRIDER, Entity1_16Types.EntityType.MAGMA_CUBE).jsonName("Strider");
        this.registerMetaHandler().filter((EntityType)Entity1_16Types.EntityType.ZOGLIN, 16).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_16Types.EntityType.HOGLIN, 15).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_16Types.EntityType.PIGLIN, 16).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_16Types.EntityType.PIGLIN, 17).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_16Types.EntityType.PIGLIN, 18).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_16Types.EntityType.STRIDER, 15).handle(meta -> {
            boolean baby = (Boolean)meta.getData().getCastedValue();
            meta.getData().setValue(baby ? 1 : 3);
            meta.getData().setMetaType(MetaType1_14.VarInt);
            return meta.getData();
        });
        this.registerMetaHandler().filter((EntityType)Entity1_16Types.EntityType.STRIDER, 16).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_16Types.EntityType.STRIDER, 17).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_16Types.EntityType.STRIDER, 18).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_16Types.EntityType.FISHING_BOBBER, 8).removed();
        this.registerMetaHandler().filter(Entity1_16Types.EntityType.ABSTRACT_ARROW, true, 8).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_16Types.EntityType.ABSTRACT_ARROW, true).handle(meta -> {
            if (meta.getIndex() >= 8) {
                meta.getData().setId(meta.getIndex() + 1);
            }
            return meta.getData();
        });
    }

    @Override
    protected EntityType getTypeFromId(int typeId) {
        return Entity1_16Types.getTypeFromId(typeId);
    }
}

