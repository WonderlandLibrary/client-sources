/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.Int2IntMapMappings;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.data.entity.DimensionData;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.data.entity.DimensionDataImpl;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.rewriter.meta.MetaFilter;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEventImpl;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class EntityRewriter<C extends ClientboundPacketType, T extends Protocol<C, ?, ?, ?>>
extends RewriterBase<T>
implements com.viaversion.viaversion.api.rewriter.EntityRewriter<T> {
    private static final Metadata[] EMPTY_ARRAY = new Metadata[0];
    protected final List<MetaFilter> metadataFilters = new ArrayList<MetaFilter>();
    protected final boolean trackMappedType;
    protected Mappings typeMappings;

    protected EntityRewriter(T protocol) {
        this(protocol, true);
    }

    protected EntityRewriter(T protocol, boolean trackMappedType) {
        super(protocol);
        this.trackMappedType = trackMappedType;
        protocol.put(this);
    }

    public MetaFilter.Builder filter() {
        return new MetaFilter.Builder(this);
    }

    public void registerFilter(MetaFilter filter) {
        Preconditions.checkArgument(!this.metadataFilters.contains(filter));
        this.metadataFilters.add(filter);
    }

    @Override
    public void handleMetadata(int entityId, List<Metadata> metadataList, UserConnection connection) {
        TrackedEntity entity = this.tracker(connection).entity(entityId);
        EntityType type2 = entity != null ? entity.entityType() : null;
        for (Metadata metadata : metadataList.toArray(EMPTY_ARRAY)) {
            List<Metadata> extraMeta;
            if (!this.callOldMetaHandler(entityId, type2, metadata, metadataList, connection)) {
                metadataList.remove(metadata);
                continue;
            }
            MetaHandlerEvent event = null;
            for (MetaFilter filter : this.metadataFilters) {
                if (!filter.isFiltered(type2, metadata)) continue;
                if (event == null) {
                    event = new MetaHandlerEventImpl(connection, entity, entityId, metadata, metadataList);
                }
                try {
                    filter.handler().handle(event, metadata);
                } catch (Exception e) {
                    this.logException(e, type2, metadataList, metadata);
                    metadataList.remove(metadata);
                    break;
                }
                if (!event.cancelled()) continue;
                metadataList.remove(metadata);
                break;
            }
            List<Metadata> list = extraMeta = event != null ? event.extraMeta() : null;
            if (extraMeta == null) continue;
            metadataList.addAll(extraMeta);
        }
        if (entity != null) {
            entity.sentMetadata(true);
        }
    }

    @Deprecated
    private boolean callOldMetaHandler(int entityId, @Nullable EntityType type2, Metadata metadata, List<Metadata> metadataList, UserConnection connection) {
        try {
            this.handleMetadata(entityId, type2, metadata, metadataList, connection);
            return true;
        } catch (Exception e) {
            this.logException(e, type2, metadataList, metadata);
            return false;
        }
    }

    @Deprecated
    protected void handleMetadata(int entityId, @Nullable EntityType type2, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
    }

    @Override
    public int newEntityId(int id) {
        return this.typeMappings != null ? this.typeMappings.getNewIdOrDefault(id, id) : id;
    }

    public void mapEntityType(EntityType type2, EntityType mappedType) {
        Preconditions.checkArgument(type2.getClass() != mappedType.getClass(), "EntityTypes should not be of the same class/enum");
        this.mapEntityType(type2.getId(), mappedType.getId());
    }

    protected void mapEntityType(int id, int mappedId) {
        if (this.typeMappings == null) {
            this.typeMappings = Int2IntMapMappings.of();
        }
        this.typeMappings.setNewId(id, mappedId);
    }

    public <E extends Enum<E>> void mapTypes(EntityType[] oldTypes, Class<E> newTypeClass) {
        if (this.typeMappings == null) {
            this.typeMappings = Int2IntMapMappings.of();
        }
        for (EntityType oldType : oldTypes) {
            try {
                E newType = Enum.valueOf(newTypeClass, oldType.name());
                this.typeMappings.setNewId(oldType.getId(), ((EntityType)newType).getId());
            } catch (IllegalArgumentException notFound) {
                if (this.typeMappings.contains(oldType.getId())) continue;
                Via.getPlatform().getLogger().warning("Could not find new entity type for " + oldType + "! Old type: " + oldType.getClass().getEnclosingClass().getSimpleName() + ", new type: " + newTypeClass.getEnclosingClass().getSimpleName());
            }
        }
    }

    public void mapTypes() {
        Preconditions.checkArgument(this.typeMappings == null, "Type mappings have already been set - manual type mappings should be set *after* this");
        Preconditions.checkNotNull(this.protocol.getMappingData().getEntityMappings(), "Protocol does not have entity mappings");
        this.typeMappings = this.protocol.getMappingData().getEntityMappings();
    }

    public void registerMetaTypeHandler(@Nullable MetaType itemType, @Nullable MetaType blockStateType, @Nullable MetaType optionalBlockStateType, @Nullable MetaType particleType) {
        this.filter().handler((event, meta) -> {
            MetaType type2 = meta.metaType();
            if (type2 == itemType) {
                this.protocol.getItemRewriter().handleItemToClient((Item)meta.value());
            } else if (type2 == blockStateType) {
                int data = (Integer)meta.value();
                meta.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
            } else if (type2 == optionalBlockStateType) {
                int data = (Integer)meta.value();
                if (data != 0) {
                    meta.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
                }
            } else if (type2 == particleType) {
                this.rewriteParticle((Particle)meta.value());
            }
        });
    }

    public void registerTracker(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(EntityRewriter.this.trackerHandler());
            }
        });
    }

    public void registerTrackerWithData(C packetType, final EntityType fallingBlockType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

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
                this.map(Type.INT);
                this.handler(EntityRewriter.this.trackerHandler());
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Type.VAR_INT, 0);
                    EntityType entityType = EntityRewriter.this.tracker(wrapper.user()).entityType(entityId);
                    if (entityType == fallingBlockType) {
                        wrapper.set(Type.INT, 0, EntityRewriter.this.protocol.getMappingData().getNewBlockStateId(wrapper.get(Type.INT, 0)));
                    }
                });
            }
        });
    }

    public void registerTrackerWithData1_19(C packetType, final EntityType fallingBlockType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

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
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(EntityRewriter.this.trackerHandler());
                this.handler(wrapper -> {
                    if (EntityRewriter.this.protocol.getMappingData() == null) {
                        return;
                    }
                    int entityId = wrapper.get(Type.VAR_INT, 0);
                    EntityType entityType = EntityRewriter.this.tracker(wrapper.user()).entityType(entityId);
                    if (entityType == fallingBlockType) {
                        wrapper.set(Type.VAR_INT, 2, EntityRewriter.this.protocol.getMappingData().getNewBlockStateId(wrapper.get(Type.VAR_INT, 2)));
                    }
                });
            }
        });
    }

    public void registerTracker(C packetType, EntityType entityType, Type<Integer> intType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int entityId = (Integer)wrapper.passthrough(intType);
            this.tracker(wrapper.user()).addEntity(entityId, entityType);
        });
    }

    public void registerTracker(C packetType, EntityType entityType) {
        this.registerTracker(packetType, entityType, Type.VAR_INT);
    }

    public void registerRemoveEntities(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int[] entityIds = wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
            Object entityTracker = this.tracker(wrapper.user());
            for (int entity : entityIds) {
                entityTracker.removeEntity(entity);
            }
        });
    }

    public void registerRemoveEntity(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int entityId = wrapper.passthrough(Type.VAR_INT);
            this.tracker(wrapper.user()).removeEntity(entityId);
        });
    }

    public void registerMetadataRewriter(C packetType, final @Nullable Type<List<Metadata>> oldMetaType, final Type<List<Metadata>> newMetaType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                if (oldMetaType != null) {
                    this.map(oldMetaType, newMetaType);
                } else {
                    this.map(newMetaType);
                }
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Type.VAR_INT, 0);
                    List metadata = (List)wrapper.get(newMetaType, 0);
                    EntityRewriter.this.handleMetadata(entityId, metadata, wrapper.user());
                });
            }
        });
    }

    public void registerMetadataRewriter(C packetType, Type<List<Metadata>> metaType) {
        this.registerMetadataRewriter(packetType, null, metaType);
    }

    public PacketHandler trackerHandler() {
        return this.trackerAndRewriterHandler(null);
    }

    public PacketHandler playerTrackerHandler() {
        return wrapper -> {
            Object tracker = this.tracker(wrapper.user());
            int entityId = wrapper.get(Type.INT, 0);
            tracker.setClientEntityId(entityId);
            tracker.addEntity(entityId, tracker.playerType());
        };
    }

    public PacketHandler worldDataTrackerHandler(int nbtIndex) {
        return wrapper -> {
            Object tracker = this.tracker(wrapper.user());
            CompoundTag registryData = wrapper.get(Type.NBT, nbtIndex);
            Object height = registryData.get("height");
            if (height instanceof IntTag) {
                int blockHeight = ((IntTag)height).asInt();
                tracker.setCurrentWorldSectionHeight(blockHeight >> 4);
            } else {
                Via.getPlatform().getLogger().warning("Height missing in dimension data: " + registryData);
            }
            Object minY = registryData.get("min_y");
            if (minY instanceof IntTag) {
                tracker.setCurrentMinY(((IntTag)minY).asInt());
            } else {
                Via.getPlatform().getLogger().warning("Min Y missing in dimension data: " + registryData);
            }
            String world = wrapper.get(Type.STRING, 0);
            if (tracker.currentWorld() != null && !tracker.currentWorld().equals(world)) {
                tracker.clearEntities();
                tracker.trackClientEntity();
            }
            tracker.setCurrentWorld(world);
        };
    }

    public PacketHandler worldDataTrackerHandlerByKey() {
        return wrapper -> {
            String dimensionKey;
            Object tracker = this.tracker(wrapper.user());
            DimensionData dimensionData = tracker.dimensionData(dimensionKey = wrapper.get(Type.STRING, 0));
            if (dimensionData == null) {
                Via.getPlatform().getLogger().severe("Dimension data missing for dimension: " + dimensionKey + ", falling back to overworld");
                dimensionData = tracker.dimensionData("minecraft:overworld");
                Preconditions.checkNotNull(dimensionData, "Overworld data missing");
            }
            tracker.setCurrentWorldSectionHeight(dimensionData.height() >> 4);
            tracker.setCurrentMinY(dimensionData.minY());
            String world = wrapper.get(Type.STRING, 1);
            if (tracker.currentWorld() != null && !tracker.currentWorld().equals(world)) {
                tracker.clearEntities();
                tracker.trackClientEntity();
            }
            tracker.setCurrentWorld(world);
        };
    }

    public PacketHandler biomeSizeTracker() {
        return wrapper -> this.trackBiomeSize(wrapper.user(), wrapper.get(Type.NBT, 0));
    }

    public void trackBiomeSize(UserConnection connection, CompoundTag registry) {
        CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
        ListTag biomes = (ListTag)biomeRegistry.get("value");
        this.tracker(connection).setBiomesSent(biomes.size());
    }

    public PacketHandler dimensionDataHandler() {
        return wrapper -> this.cacheDimensionData(wrapper.user(), wrapper.get(Type.NBT, 0));
    }

    public void cacheDimensionData(UserConnection connection, CompoundTag registry) {
        ListTag dimensions = (ListTag)((CompoundTag)registry.get("minecraft:dimension_type")).get("value");
        HashMap<String, DimensionData> dimensionDataMap = new HashMap<String, DimensionData>(dimensions.size());
        for (Tag dimension : dimensions) {
            CompoundTag dimensionCompound = (CompoundTag)dimension;
            CompoundTag element = (CompoundTag)dimensionCompound.get("element");
            String name = (String)((Tag)dimensionCompound.get("name")).getValue();
            dimensionDataMap.put(name, new DimensionDataImpl(element));
        }
        this.tracker(connection).setDimensions(dimensionDataMap);
    }

    public PacketHandler trackerAndRewriterHandler(@Nullable Type<List<Metadata>> metaType) {
        return wrapper -> {
            int entityId = wrapper.get(Type.VAR_INT, 0);
            int type2 = wrapper.get(Type.VAR_INT, 1);
            int newType = this.newEntityId(type2);
            if (newType != type2) {
                wrapper.set(Type.VAR_INT, 1, newType);
            }
            EntityType entType = this.typeFromId(this.trackMappedType ? newType : type2);
            this.tracker(wrapper.user()).addEntity(entityId, entType);
            if (metaType != null) {
                this.handleMetadata(entityId, (List)wrapper.get(metaType, 0), wrapper.user());
            }
        };
    }

    public PacketHandler trackerAndRewriterHandler(@Nullable Type<List<Metadata>> metaType, EntityType entityType) {
        return wrapper -> {
            int entityId = wrapper.get(Type.VAR_INT, 0);
            this.tracker(wrapper.user()).addEntity(entityId, entityType);
            if (metaType != null) {
                this.handleMetadata(entityId, (List)wrapper.get(metaType, 0), wrapper.user());
            }
        };
    }

    public PacketHandler objectTrackerHandler() {
        return wrapper -> {
            int entityId = wrapper.get(Type.VAR_INT, 0);
            byte type2 = wrapper.get(Type.BYTE, 0);
            EntityType entType = this.objectTypeFromId(type2);
            this.tracker(wrapper.user()).addEntity(entityId, entType);
        };
    }

    @Deprecated
    protected @Nullable Metadata metaByIndex(int index, List<Metadata> metadataList) {
        for (Metadata metadata : metadataList) {
            if (metadata.id() != index) continue;
            return metadata;
        }
        return null;
    }

    protected void rewriteParticle(Particle particle2) {
        int id;
        ParticleMappings mappings = this.protocol.getMappingData().getParticleMappings();
        if (mappings.isBlockParticle(id = particle2.getId())) {
            Particle.ParticleData data = particle2.getArguments().get(0);
            data.setValue(this.protocol.getMappingData().getNewBlockStateId((Integer)data.get()));
        } else if (mappings.isItemParticle(id) && this.protocol.getItemRewriter() != null) {
            Particle.ParticleData data = particle2.getArguments().get(0);
            Item item = (Item)data.get();
            this.protocol.getItemRewriter().handleItemToClient(item);
        }
        particle2.setId(this.protocol.getMappingData().getNewParticleId(id));
    }

    private void logException(Exception e, @Nullable EntityType type2, List<Metadata> metadataList, Metadata metadata) {
        if (!Via.getConfig().isSuppressMetadataErrors() || Via.getManager().isDebug()) {
            Logger logger = Via.getPlatform().getLogger();
            logger.severe("An error occurred in metadata handler " + this.getClass().getSimpleName() + " for " + (type2 != null ? type2.name() : "untracked") + " entity type: " + metadata);
            logger.severe(metadataList.stream().sorted(Comparator.comparingInt(Metadata::id)).map(Metadata::toString).collect(Collectors.joining("\n", "Full metadata: ", "")));
            e.printStackTrace();
        }
    }
}

