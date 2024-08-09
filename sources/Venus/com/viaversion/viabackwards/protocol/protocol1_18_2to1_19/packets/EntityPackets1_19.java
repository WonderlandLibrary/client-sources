/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.DimensionRegistryStorage;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.StoredPainting;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.api.type.types.version.Types1_19;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;

public final class EntityPackets1_19
extends EntityRewriter<ClientboundPackets1_19, Protocol1_18_2To1_19> {
    public EntityPackets1_19(Protocol1_18_2To1_19 protocol1_18_2To1_19) {
        super(protocol1_18_2To1_19);
    }

    @Override
    protected void registerPackets() {
        this.registerTracker(ClientboundPackets1_19.SPAWN_EXPERIENCE_ORB, Entity1_19Types.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_19.SPAWN_PLAYER, Entity1_19Types.PLAYER);
        this.registerMetadataRewriter(ClientboundPackets1_19.ENTITY_METADATA, Types1_19.METADATA_LIST, Types1_18.METADATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19.REMOVE_ENTITIES);
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.SPAWN_ENTITY, new PacketHandlers(this){
            final EntityPackets1_19 this$0;
            {
                this.this$0 = entityPackets1_19;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte by = packetWrapper.read(Type.BYTE);
                int n = packetWrapper.read(Type.VAR_INT);
                EntityType entityType = EntityPackets1_19.access$000(this.this$0, packetWrapper);
                if (entityType.isOrHasParent(Entity1_19Types.LIVINGENTITY)) {
                    packetWrapper.write(Type.BYTE, by);
                    byte by2 = packetWrapper.get(Type.BYTE, 0);
                    byte by3 = packetWrapper.get(Type.BYTE, 1);
                    packetWrapper.set(Type.BYTE, 0, by3);
                    packetWrapper.set(Type.BYTE, 1, by2);
                    packetWrapper.setPacketType(ClientboundPackets1_18.SPAWN_MOB);
                    return;
                }
                if (entityType == Entity1_19Types.PAINTING) {
                    packetWrapper.cancel();
                    int n2 = packetWrapper.get(Type.VAR_INT, 0);
                    StoredEntityData storedEntityData = this.this$0.tracker(packetWrapper.user()).entityData(n2);
                    Position position = new Position(packetWrapper.get(Type.DOUBLE, 0).intValue(), packetWrapper.get(Type.DOUBLE, 1).intValue(), packetWrapper.get(Type.DOUBLE, 2).intValue());
                    storedEntityData.put(new StoredPainting(n2, packetWrapper.get(Type.UUID, 0), position, n));
                    return;
                }
                if (entityType == Entity1_19Types.FALLING_BLOCK) {
                    n = ((Protocol1_18_2To1_19)EntityPackets1_19.access$100(this.this$0)).getMappingData().getNewBlockStateId(n);
                }
                packetWrapper.write(Type.INT, n);
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.ENTITY_EFFECT, new PacketHandlers(this){
            final EntityPackets1_19 this$0;
            {
                this.this$0 = entityPackets1_19;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                    packetWrapper.read(Type.NBT);
                }
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.JOIN_GAME, new PacketHandlers(this){
            final EntityPackets1_19 this$0;
            {
                this.this$0 = entityPackets1_19;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.handler(this::lambda$register$0);
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.read(Type.OPTIONAL_GLOBAL_POSITION);
                this.handler(this.this$0.worldDataTrackerHandler(1));
                this.handler(this.this$0.playerTrackerHandler());
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Tag tag;
                Object object;
                Object object2;
                Tag tag22;
                DimensionRegistryStorage dimensionRegistryStorage = packetWrapper.user().get(DimensionRegistryStorage.class);
                dimensionRegistryStorage.clear();
                String string = packetWrapper.read(Type.STRING);
                CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                ListTag listTag = (ListTag)((CompoundTag)compoundTag.get("minecraft:dimension_type")).get("value");
                boolean bl = false;
                for (Tag tag22 : listTag) {
                    object2 = (CompoundTag)tag22;
                    object = (StringTag)((CompoundTag)object2).get("name");
                    tag = (CompoundTag)((CompoundTag)object2).get("element");
                    dimensionRegistryStorage.addDimension(((StringTag)object).getValue(), ((CompoundTag)tag).clone());
                    if (bl || !((StringTag)object).getValue().equals(string)) continue;
                    packetWrapper.write(Type.NBT, tag);
                    bl = true;
                }
                if (!bl) {
                    throw new IllegalStateException("Could not find dimension " + string + " in dimension registry");
                }
                CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("minecraft:worldgen/biome");
                tag22 = (ListTag)compoundTag2.get("value");
                object2 = ((ListTag)tag22).getValue().iterator();
                while (object2.hasNext()) {
                    object = (Tag)object2.next();
                    tag = (CompoundTag)((CompoundTag)object).get("element");
                    ((CompoundTag)tag).put("category", new StringTag("none"));
                }
                this.this$0.tracker(packetWrapper.user()).setBiomesSent(((ListTag)tag22).size());
                object2 = (ListTag)((CompoundTag)compoundTag.remove("minecraft:chat_type")).get("value");
                object = ((ListTag)object2).iterator();
                while (object.hasNext()) {
                    Tag tag3 = tag = (Tag)object.next();
                    NumberTag numberTag = (NumberTag)((CompoundTag)tag3).get("id");
                    dimensionRegistryStorage.addChatType(numberTag.asInt(), (CompoundTag)tag3);
                }
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.RESPAWN, new PacketHandlers(this){
            final EntityPackets1_19 this$0;
            {
                this.this$0 = entityPackets1_19;
            }

            @Override
            public void register() {
                this.handler(4::lambda$register$0);
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.read(Type.OPTIONAL_GLOBAL_POSITION);
                this.handler(this.this$0.worldDataTrackerHandler(0));
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.read(Type.STRING);
                CompoundTag compoundTag = packetWrapper.user().get(DimensionRegistryStorage.class).dimension(string);
                if (compoundTag == null) {
                    throw new IllegalArgumentException("Could not find dimension " + string + " in dimension registry");
                }
                packetWrapper.write(Type.NBT, compoundTag);
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.PLAYER_INFO, EntityPackets1_19::lambda$registerPackets$0);
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler(this::lambda$registerRewrites$1);
        this.registerMetaTypeHandler(Types1_18.META_TYPES.itemType, Types1_18.META_TYPES.blockStateType, null, null, Types1_18.META_TYPES.componentType, Types1_18.META_TYPES.optionalComponentType);
        this.filter().filterFamily(Entity1_19Types.MINECART_ABSTRACT).index(11).handler(this::lambda$registerRewrites$2);
        this.filter().type(Entity1_19Types.PAINTING).index(8).handler(this::lambda$registerRewrites$3);
        this.filter().type(Entity1_19Types.CAT).index(19).handler(EntityPackets1_19::lambda$registerRewrites$4);
        this.filter().type(Entity1_19Types.FROG).cancel(16);
        this.filter().type(Entity1_19Types.FROG).cancel(17);
        this.filter().type(Entity1_19Types.FROG).cancel(18);
        this.filter().type(Entity1_19Types.WARDEN).cancel(16);
        this.filter().type(Entity1_19Types.GOAT).cancel(18);
        this.filter().type(Entity1_19Types.GOAT).cancel(19);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
        this.mapEntityTypeWithData(Entity1_19Types.FROG, Entity1_19Types.RABBIT).jsonName();
        this.mapEntityTypeWithData(Entity1_19Types.TADPOLE, Entity1_19Types.PUFFERFISH).jsonName();
        this.mapEntityTypeWithData(Entity1_19Types.CHEST_BOAT, Entity1_19Types.BOAT);
        this.mapEntityTypeWithData(Entity1_19Types.WARDEN, Entity1_19Types.IRON_GOLEM).jsonName();
        this.mapEntityTypeWithData(Entity1_19Types.ALLAY, Entity1_19Types.VEX).jsonName();
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_19Types.getTypeFromId(n);
    }

    private static void lambda$registerRewrites$4(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metadata.setMetaType(Types1_18.META_TYPES.varIntType);
    }

    private void lambda$registerRewrites$3(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metaHandlerEvent.cancel();
        StoredEntityData storedEntityData = this.tracker(metaHandlerEvent.user()).entityDataIfPresent(metaHandlerEvent.entityId());
        StoredPainting storedPainting = storedEntityData.remove(StoredPainting.class);
        if (storedPainting != null) {
            PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_18.SPAWN_PAINTING, metaHandlerEvent.user());
            packetWrapper.write(Type.VAR_INT, storedPainting.entityId());
            packetWrapper.write(Type.UUID, storedPainting.uuid());
            packetWrapper.write(Type.VAR_INT, metadata.value());
            packetWrapper.write(Type.POSITION1_14, storedPainting.position());
            packetWrapper.write(Type.BYTE, storedPainting.direction());
            try {
                packetWrapper.send(Protocol1_18_2To1_19.class);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private void lambda$registerRewrites$2(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.getValue();
        metadata.setValue(((Protocol1_18_2To1_19)this.protocol).getMappingData().getNewBlockStateId(n));
    }

    private void lambda$registerRewrites$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n;
        MetaType metaType;
        if (metadata.metaType().typeId() <= Types1_18.META_TYPES.poseType.typeId()) {
            metadata.setMetaType(Types1_18.META_TYPES.byId(metadata.metaType().typeId()));
        }
        if ((metaType = metadata.metaType()) == Types1_18.META_TYPES.particleType) {
            Particle particle = (Particle)metadata.getValue();
            ParticleMappings particleMappings = ((Protocol1_18_2To1_19)this.protocol).getMappingData().getParticleMappings();
            if (particle.getId() == particleMappings.id("sculk_charge")) {
                metaHandlerEvent.cancel();
                return;
            }
            if (particle.getId() == particleMappings.id("shriek")) {
                metaHandlerEvent.cancel();
                return;
            }
            if (particle.getId() == particleMappings.id("vibration")) {
                metaHandlerEvent.cancel();
                return;
            }
            this.rewriteParticle(particle);
        } else if (metaType == Types1_18.META_TYPES.poseType && (n = ((Integer)metadata.value()).intValue()) >= 8) {
            metadata.setValue(0);
        }
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        int n2 = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n2; ++i) {
            packetWrapper.passthrough(Type.UUID);
            if (n == 0) {
                packetWrapper.passthrough(Type.STRING);
                int n3 = packetWrapper.passthrough(Type.VAR_INT);
                for (int j = 0; j < n3; ++j) {
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.OPTIONAL_STRING);
                }
                packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.passthrough(Type.OPTIONAL_COMPONENT);
                packetWrapper.read(Type.OPTIONAL_PROFILE_KEY);
                continue;
            }
            if (n == 1 || n == 2) {
                packetWrapper.passthrough(Type.VAR_INT);
                continue;
            }
            if (n != 3) continue;
            packetWrapper.passthrough(Type.OPTIONAL_COMPONENT);
        }
    }

    static EntityType access$000(EntityPackets1_19 entityPackets1_19, PacketWrapper packetWrapper) throws Exception {
        return entityPackets1_19.trackAndMapEntity(packetWrapper);
    }

    static Protocol access$100(EntityPackets1_19 entityPackets1_19) {
        return entityPackets1_19.protocol;
    }
}

