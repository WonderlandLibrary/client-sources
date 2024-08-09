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
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
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

    protected EntityRewriter(T t) {
        this(t, true);
    }

    protected EntityRewriter(T t, boolean bl) {
        super(t);
        this.trackMappedType = bl;
        t.put(this);
    }

    public MetaFilter.Builder filter() {
        return new MetaFilter.Builder(this);
    }

    public void registerFilter(MetaFilter metaFilter) {
        Preconditions.checkArgument(!this.metadataFilters.contains(metaFilter));
        this.metadataFilters.add(metaFilter);
    }

    @Override
    public void handleMetadata(int n, List<Metadata> list, UserConnection userConnection) {
        TrackedEntity trackedEntity = this.tracker(userConnection).entity(n);
        EntityType entityType = trackedEntity != null ? trackedEntity.entityType() : null;
        int n2 = 0;
        for (Metadata metadata : list.toArray(EMPTY_ARRAY)) {
            if (!this.callOldMetaHandler(n, entityType, metadata, list, userConnection)) {
                list.remove(n2--);
                continue;
            }
            MetaHandlerEvent metaHandlerEvent = null;
            for (MetaFilter metaFilter : this.metadataFilters) {
                if (!metaFilter.isFiltered(entityType, metadata)) continue;
                if (metaHandlerEvent == null) {
                    metaHandlerEvent = new MetaHandlerEventImpl(userConnection, trackedEntity, n, metadata, list);
                }
                try {
                    metaFilter.handler().handle(metaHandlerEvent, metadata);
                } catch (Exception exception) {
                    this.logException(exception, entityType, list, metadata);
                    list.remove(n2--);
                    break;
                }
                if (!metaHandlerEvent.cancelled()) continue;
                list.remove(n2--);
                break;
            }
            if (metaHandlerEvent != null && metaHandlerEvent.extraMeta() != null) {
                list.addAll(metaHandlerEvent.extraMeta());
            }
            ++n2;
        }
        if (trackedEntity != null) {
            trackedEntity.sentMetadata(true);
        }
    }

    @Deprecated
    private boolean callOldMetaHandler(int n, @Nullable EntityType entityType, Metadata metadata, List<Metadata> list, UserConnection userConnection) {
        try {
            this.handleMetadata(n, entityType, metadata, list, userConnection);
            return true;
        } catch (Exception exception) {
            this.logException(exception, entityType, list, metadata);
            return true;
        }
    }

    @Deprecated
    protected void handleMetadata(int n, @Nullable EntityType entityType, Metadata metadata, List<Metadata> list, UserConnection userConnection) throws Exception {
    }

    @Override
    public int newEntityId(int n) {
        return this.typeMappings != null ? this.typeMappings.getNewIdOrDefault(n, n) : n;
    }

    public void mapEntityType(EntityType entityType, EntityType entityType2) {
        Preconditions.checkArgument(entityType.getClass() != entityType2.getClass(), "EntityTypes should not be of the same class/enum");
        this.mapEntityType(entityType.getId(), entityType2.getId());
    }

    protected void mapEntityType(int n, int n2) {
        if (this.typeMappings == null) {
            this.typeMappings = Int2IntMapMappings.of();
        }
        this.typeMappings.setNewId(n, n2);
    }

    public <E extends Enum<E>> void mapTypes(EntityType[] entityTypeArray, Class<E> clazz) {
        if (this.typeMappings == null) {
            this.typeMappings = Int2IntMapMappings.of();
        }
        for (EntityType entityType : entityTypeArray) {
            try {
                E e = Enum.valueOf(clazz, entityType.name());
                this.typeMappings.setNewId(entityType.getId(), ((EntityType)e).getId());
            } catch (IllegalArgumentException illegalArgumentException) {
                if (this.typeMappings.contains(entityType.getId())) continue;
                Via.getPlatform().getLogger().warning("Could not find new entity type for " + entityType + "! Old type: " + entityType.getClass().getEnclosingClass().getSimpleName() + ", new type: " + clazz.getEnclosingClass().getSimpleName());
            }
        }
    }

    public void mapTypes() {
        Preconditions.checkArgument(this.typeMappings == null, "Type mappings have already been set - manual type mappings should be set *after* this");
        Preconditions.checkNotNull(this.protocol.getMappingData().getEntityMappings(), "Protocol does not have entity mappings");
        this.typeMappings = this.protocol.getMappingData().getEntityMappings();
    }

    public void registerMetaTypeHandler(@Nullable MetaType metaType, @Nullable MetaType metaType2, @Nullable MetaType metaType3, @Nullable MetaType metaType4) {
        this.filter().handler((arg_0, arg_1) -> this.lambda$registerMetaTypeHandler$0(metaType, metaType2, metaType3, metaType4, arg_0, arg_1));
    }

    public void registerTracker(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final EntityRewriter this$0;
            {
                this.this$0 = entityRewriter;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(this.this$0.trackerHandler());
            }
        });
    }

    public void registerTrackerWithData(C c, EntityType entityType) {
        this.protocol.registerClientbound(c, new PacketHandlers(this, entityType){
            final EntityType val$fallingBlockType;
            final EntityRewriter this$0;
            {
                this.this$0 = entityRewriter;
                this.val$fallingBlockType = entityType;
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
                this.map(Type.INT);
                this.handler(this.this$0.trackerHandler());
                this.handler(arg_0 -> this.lambda$register$0(this.val$fallingBlockType, arg_0));
            }

            private void lambda$register$0(EntityType entityType, PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityType entityType2 = this.this$0.tracker(packetWrapper.user()).entityType(n);
                if (entityType2 == entityType) {
                    packetWrapper.set(Type.INT, 0, EntityRewriter.access$000(this.this$0).getMappingData().getNewBlockStateId(packetWrapper.get(Type.INT, 0)));
                }
            }
        });
    }

    public void registerTrackerWithData1_19(C c, EntityType entityType) {
        this.protocol.registerClientbound(c, new PacketHandlers(this, entityType){
            final EntityType val$fallingBlockType;
            final EntityRewriter this$0;
            {
                this.this$0 = entityRewriter;
                this.val$fallingBlockType = entityType;
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
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(this.this$0.trackerHandler());
                this.handler(arg_0 -> this.lambda$register$0(this.val$fallingBlockType, arg_0));
            }

            private void lambda$register$0(EntityType entityType, PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityType entityType2 = this.this$0.tracker(packetWrapper.user()).entityType(n);
                if (entityType2 == entityType) {
                    packetWrapper.set(Type.VAR_INT, 2, EntityRewriter.access$100(this.this$0).getMappingData().getNewBlockStateId(packetWrapper.get(Type.VAR_INT, 2)));
                }
            }
        });
    }

    public void registerTracker(C c, EntityType entityType, Type<Integer> type) {
        this.protocol.registerClientbound(c, arg_0 -> this.lambda$registerTracker$1(type, entityType, arg_0));
    }

    public void registerTracker(C c, EntityType entityType) {
        this.registerTracker(c, entityType, Type.VAR_INT);
    }

    public void registerRemoveEntities(C c) {
        this.protocol.registerClientbound(c, this::lambda$registerRemoveEntities$2);
    }

    public void registerRemoveEntity(C c) {
        this.protocol.registerClientbound(c, this::lambda$registerRemoveEntity$3);
    }

    public void registerMetadataRewriter(C c, @Nullable Type<List<Metadata>> type, Type<List<Metadata>> type2) {
        this.protocol.registerClientbound(c, new PacketHandlers(this, type, type2){
            final Type val$oldMetaType;
            final Type val$newMetaType;
            final EntityRewriter this$0;
            {
                this.this$0 = entityRewriter;
                this.val$oldMetaType = type;
                this.val$newMetaType = type2;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                if (this.val$oldMetaType != null) {
                    this.map(this.val$oldMetaType, this.val$newMetaType);
                } else {
                    this.map(this.val$newMetaType);
                }
                this.handler(arg_0 -> this.lambda$register$0(this.val$newMetaType, arg_0));
            }

            private void lambda$register$0(Type type, PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                List list = (List)packetWrapper.get(type, 0);
                this.this$0.handleMetadata(n, list, packetWrapper.user());
            }
        });
    }

    public void registerMetadataRewriter(C c, Type<List<Metadata>> type) {
        this.registerMetadataRewriter(c, null, type);
    }

    public PacketHandler trackerHandler() {
        return this.trackerAndRewriterHandler(null);
    }

    public PacketHandler playerTrackerHandler() {
        return this::lambda$playerTrackerHandler$4;
    }

    public PacketHandler worldDataTrackerHandler(int n) {
        return arg_0 -> this.lambda$worldDataTrackerHandler$5(n, arg_0);
    }

    public PacketHandler worldDataTrackerHandlerByKey() {
        return this::lambda$worldDataTrackerHandlerByKey$6;
    }

    public PacketHandler biomeSizeTracker() {
        return this::lambda$biomeSizeTracker$7;
    }

    public PacketHandler dimensionDataHandler() {
        return this::lambda$dimensionDataHandler$8;
    }

    public PacketHandler trackerAndRewriterHandler(@Nullable Type<List<Metadata>> type) {
        return arg_0 -> this.lambda$trackerAndRewriterHandler$9(type, arg_0);
    }

    public PacketHandler trackerAndRewriterHandler(@Nullable Type<List<Metadata>> type, EntityType entityType) {
        return arg_0 -> this.lambda$trackerAndRewriterHandler$10(entityType, type, arg_0);
    }

    public PacketHandler objectTrackerHandler() {
        return this::lambda$objectTrackerHandler$11;
    }

    @Deprecated
    protected @Nullable Metadata metaByIndex(int n, List<Metadata> list) {
        for (Metadata metadata : list) {
            if (metadata.id() != n) continue;
            return metadata;
        }
        return null;
    }

    protected void rewriteParticle(Particle particle) {
        int n;
        ParticleMappings particleMappings = this.protocol.getMappingData().getParticleMappings();
        if (particleMappings.isBlockParticle(n = particle.getId())) {
            Particle.ParticleData particleData = particle.getArguments().get(0);
            particleData.setValue(this.protocol.getMappingData().getNewBlockStateId((Integer)particleData.get()));
        } else if (particleMappings.isItemParticle(n) && this.protocol.getItemRewriter() != null) {
            Particle.ParticleData particleData = particle.getArguments().get(0);
            Item item = (Item)particleData.get();
            this.protocol.getItemRewriter().handleItemToClient(item);
        }
        particle.setId(this.protocol.getMappingData().getNewParticleId(n));
    }

    private void logException(Exception exception, @Nullable EntityType entityType, List<Metadata> list, Metadata metadata) {
        if (!Via.getConfig().isSuppressMetadataErrors() || Via.getManager().isDebug()) {
            Logger logger = Via.getPlatform().getLogger();
            logger.severe("An error occurred in metadata handler " + this.getClass().getSimpleName() + " for " + (entityType != null ? entityType.name() : "untracked") + " entity type: " + metadata);
            logger.severe(list.stream().sorted(Comparator.comparingInt(Metadata::id)).map(Metadata::toString).collect(Collectors.joining("\n", "Full metadata: ", "")));
            exception.printStackTrace();
        }
    }

    private void lambda$objectTrackerHandler$11(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.get(Type.VAR_INT, 0);
        byte by = packetWrapper.get(Type.BYTE, 0);
        EntityType entityType = this.objectTypeFromId(by);
        this.tracker(packetWrapper.user()).addEntity(n, entityType);
    }

    private void lambda$trackerAndRewriterHandler$10(EntityType entityType, Type type, PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.get(Type.VAR_INT, 0);
        this.tracker(packetWrapper.user()).addEntity(n, entityType);
        if (type != null) {
            this.handleMetadata(n, (List)packetWrapper.get(type, 0), packetWrapper.user());
        }
    }

    private void lambda$trackerAndRewriterHandler$9(Type type, PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.get(Type.VAR_INT, 0);
        int n2 = packetWrapper.get(Type.VAR_INT, 1);
        int n3 = this.newEntityId(n2);
        if (n3 != n2) {
            packetWrapper.set(Type.VAR_INT, 1, n3);
        }
        EntityType entityType = this.typeFromId(this.trackMappedType ? n3 : n2);
        this.tracker(packetWrapper.user()).addEntity(n, entityType);
        if (type != null) {
            this.handleMetadata(n, (List)packetWrapper.get(type, 0), packetWrapper.user());
        }
    }

    private void lambda$dimensionDataHandler$8(PacketWrapper packetWrapper) throws Exception {
        CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
        ListTag listTag = (ListTag)((CompoundTag)compoundTag.get("minecraft:dimension_type")).get("value");
        HashMap<String, DimensionData> hashMap = new HashMap<String, DimensionData>(listTag.size());
        for (Tag tag : listTag) {
            CompoundTag compoundTag2 = (CompoundTag)tag;
            CompoundTag compoundTag3 = (CompoundTag)compoundTag2.get("element");
            String string = (String)((Tag)compoundTag2.get("name")).getValue();
            hashMap.put(string, new DimensionDataImpl(compoundTag3));
        }
        this.tracker(packetWrapper.user()).setDimensions(hashMap);
    }

    private void lambda$biomeSizeTracker$7(PacketWrapper packetWrapper) throws Exception {
        CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
        CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("minecraft:worldgen/biome");
        ListTag listTag = (ListTag)compoundTag2.get("value");
        this.tracker(packetWrapper.user()).setBiomesSent(listTag.size());
    }

    private void lambda$worldDataTrackerHandlerByKey$6(PacketWrapper packetWrapper) throws Exception {
        String string;
        Object e = this.tracker(packetWrapper.user());
        DimensionData dimensionData = e.dimensionData(string = packetWrapper.get(Type.STRING, 0));
        if (dimensionData == null) {
            Via.getPlatform().getLogger().severe("Dimension data missing for dimension: " + string + ", falling back to overworld");
            dimensionData = e.dimensionData("minecraft:overworld");
            Preconditions.checkNotNull(dimensionData, "Overworld data missing");
        }
        e.setCurrentWorldSectionHeight(dimensionData.height() >> 4);
        e.setCurrentMinY(dimensionData.minY());
        String string2 = packetWrapper.get(Type.STRING, 1);
        if (e.currentWorld() != null && !e.currentWorld().equals(string2)) {
            e.clearEntities();
            e.trackClientEntity();
        }
        e.setCurrentWorld(string2);
    }

    private void lambda$worldDataTrackerHandler$5(int n, PacketWrapper packetWrapper) throws Exception {
        Object e = this.tracker(packetWrapper.user());
        CompoundTag compoundTag = packetWrapper.get(Type.NBT, n);
        Object t = compoundTag.get("height");
        if (t instanceof IntTag) {
            int n2 = ((IntTag)t).asInt();
            e.setCurrentWorldSectionHeight(n2 >> 4);
        } else {
            Via.getPlatform().getLogger().warning("Height missing in dimension data: " + compoundTag);
        }
        Object t2 = compoundTag.get("min_y");
        if (t2 instanceof IntTag) {
            e.setCurrentMinY(((IntTag)t2).asInt());
        } else {
            Via.getPlatform().getLogger().warning("Min Y missing in dimension data: " + compoundTag);
        }
        String string = packetWrapper.get(Type.STRING, 0);
        if (e.currentWorld() != null && !e.currentWorld().equals(string)) {
            e.clearEntities();
            e.trackClientEntity();
        }
        e.setCurrentWorld(string);
    }

    private void lambda$playerTrackerHandler$4(PacketWrapper packetWrapper) throws Exception {
        Object e = this.tracker(packetWrapper.user());
        int n = packetWrapper.get(Type.INT, 0);
        e.setClientEntityId(n);
        e.addEntity(n, e.playerType());
    }

    private void lambda$registerRemoveEntity$3(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        this.tracker(packetWrapper.user()).removeEntity(n);
    }

    private void lambda$registerRemoveEntities$2(PacketWrapper packetWrapper) throws Exception {
        int[] nArray = packetWrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
        Object e = this.tracker(packetWrapper.user());
        for (int n : nArray) {
            e.removeEntity(n);
        }
    }

    private void lambda$registerTracker$1(Type type, EntityType entityType, PacketWrapper packetWrapper) throws Exception {
        int n = (Integer)packetWrapper.passthrough(type);
        this.tracker(packetWrapper.user()).addEntity(n, entityType);
    }

    private void lambda$registerMetaTypeHandler$0(MetaType metaType, MetaType metaType2, MetaType metaType3, MetaType metaType4, MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        MetaType metaType5 = metadata.metaType();
        if (metaType5 == metaType) {
            this.protocol.getItemRewriter().handleItemToClient((Item)metadata.value());
        } else if (metaType5 == metaType2) {
            int n = (Integer)metadata.value();
            metadata.setValue(this.protocol.getMappingData().getNewBlockStateId(n));
        } else if (metaType5 == metaType3) {
            int n = (Integer)metadata.value();
            if (n != 0) {
                metadata.setValue(this.protocol.getMappingData().getNewBlockStateId(n));
            }
        } else if (metaType5 == metaType4) {
            this.rewriteParticle((Particle)metadata.value());
        }
    }

    static Protocol access$000(EntityRewriter entityRewriter) {
        return entityRewriter.protocol;
    }

    static Protocol access$100(EntityRewriter entityRewriter) {
        return entityRewriter.protocol;
    }
}

