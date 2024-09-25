/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.rewriters;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.ParticleMappings;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.protocol.ClientboundPacketType;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.storage.EntityTracker;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.Particle;
import us.myles.viaversion.libs.fastutil.ints.Int2IntMap;
import us.myles.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;

public abstract class MetadataRewriter {
    private final Class<? extends EntityTracker> entityTrackerClass;
    protected final Protocol protocol;
    private Int2IntMap typeMapping;

    protected MetadataRewriter(Protocol protocol, Class<? extends EntityTracker> entityTrackerClass) {
        this.protocol = protocol;
        this.entityTrackerClass = entityTrackerClass;
        protocol.put(this);
    }

    public final void handleMetadata(int entityId, List<Metadata> metadatas, UserConnection connection) {
        EntityType type = connection.get(this.entityTrackerClass).getEntity(entityId);
        for (Metadata metadata : new ArrayList<Metadata>(metadatas)) {
            try {
                this.handleMetadata(entityId, type, metadata, metadatas, connection);
            }
            catch (Exception e) {
                metadatas.remove(metadata);
                if (Via.getConfig().isSuppressMetadataErrors() && !Via.getManager().isDebug()) continue;
                Logger logger = Via.getPlatform().getLogger();
                logger.warning("An error occurred with entity metadata handler");
                logger.warning("This is most likely down to one of your plugins sending bad datawatchers. Please test if this occurs without any plugins except ViaVersion before reporting it on GitHub");
                logger.warning("Also make sure that all your plugins are compatible with your server version.");
                logger.warning("Entity type: " + type);
                logger.warning("Metadata: " + metadata);
                e.printStackTrace();
            }
        }
    }

    protected void rewriteParticle(Particle particle) {
        ParticleMappings mappings = this.protocol.getMappingData().getParticleMappings();
        int id = particle.getId();
        if (id == mappings.getBlockId() || id == mappings.getFallingDustId()) {
            Particle.ParticleData data = particle.getArguments().get(0);
            data.setValue(this.protocol.getMappingData().getNewBlockStateId((Integer)data.get()));
        } else if (id == mappings.getItemId()) {
            Particle.ParticleData data = particle.getArguments().get(0);
            data.setValue(this.protocol.getMappingData().getNewItemId((Integer)data.get()));
        }
        particle.setId(this.protocol.getMappingData().getNewParticleId(id));
    }

    public void registerTracker(ClientboundPacketType packetType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(MetadataRewriter.this.getTracker());
            }
        });
    }

    public void registerSpawnTrackerWithData(ClientboundPacketType packetType, final EntityType fallingBlockType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

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
                this.map(Type.INT);
                this.handler(MetadataRewriter.this.getTracker());
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Type.VAR_INT, 0);
                    EntityType entityType = ((EntityTracker)wrapper.user().get(MetadataRewriter.this.entityTrackerClass)).getEntity(entityId);
                    if (entityType == fallingBlockType) {
                        wrapper.set(Type.INT, 0, MetadataRewriter.this.protocol.getMappingData().getNewBlockStateId(wrapper.get(Type.INT, 0)));
                    }
                });
            }
        });
    }

    public void registerTracker(ClientboundPacketType packetType, final EntityType entityType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Type.VAR_INT, 0);
                    ((EntityTracker)wrapper.user().get(MetadataRewriter.this.entityTrackerClass)).addEntity(entityId, entityType);
                });
            }
        });
    }

    public void registerEntityDestroy(ClientboundPacketType packetType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT_ARRAY_PRIMITIVE);
                this.handler(wrapper -> {
                    EntityTracker entityTracker = (EntityTracker)wrapper.user().get(MetadataRewriter.this.entityTrackerClass);
                    for (int entity : wrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0)) {
                        entityTracker.removeEntity(entity);
                    }
                });
            }
        });
    }

    public void registerMetadataRewriter(ClientboundPacketType packetType, final @Nullable Type<List<Metadata>> oldMetaType, final Type<List<Metadata>> newMetaType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                if (oldMetaType != null) {
                    this.map(oldMetaType, newMetaType);
                } else {
                    this.map(newMetaType);
                }
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Type.VAR_INT, 0);
                    List metadata = (List)wrapper.get(newMetaType, 0);
                    MetadataRewriter.this.handleMetadata(entityId, metadata, wrapper.user());
                });
            }
        });
    }

    public void registerMetadataRewriter(ClientboundPacketType packetType, Type<List<Metadata>> metaType) {
        this.registerMetadataRewriter(packetType, null, metaType);
    }

    public <T extends Enum<T>> void mapTypes(EntityType[] oldTypes, Class<T> newTypeClass) {
        if (this.typeMapping == null) {
            this.typeMapping = new Int2IntOpenHashMap(oldTypes.length, 1.0f);
            this.typeMapping.defaultReturnValue(-1);
        }
        for (EntityType oldType : oldTypes) {
            try {
                T newType = Enum.valueOf(newTypeClass, oldType.name());
                this.typeMapping.put(oldType.getId(), ((EntityType)newType).getId());
            }
            catch (IllegalArgumentException notFound) {
                if (this.typeMapping.containsKey(oldType.getId())) continue;
                Via.getPlatform().getLogger().warning("Could not find new entity type for " + oldType + "! Old type: " + oldType.getClass().getEnclosingClass().getSimpleName() + ", new type: " + newTypeClass.getEnclosingClass().getSimpleName());
            }
        }
    }

    public void mapType(EntityType oldType, EntityType newType) {
        if (this.typeMapping == null) {
            this.typeMapping = new Int2IntOpenHashMap();
            this.typeMapping.defaultReturnValue(-1);
        }
        this.typeMapping.put(oldType.getId(), newType.getId());
    }

    public PacketHandler getTracker() {
        return this.getTrackerAndRewriter(null);
    }

    public PacketHandler getTrackerAndRewriter(@Nullable Type<List<Metadata>> metaType) {
        return wrapper -> {
            int entityId = wrapper.get(Type.VAR_INT, 0);
            int type = wrapper.get(Type.VAR_INT, 1);
            int newType = this.getNewEntityId(type);
            if (newType != type) {
                wrapper.set(Type.VAR_INT, 1, newType);
            }
            EntityType entType = this.getTypeFromId(newType);
            wrapper.user().get(this.entityTrackerClass).addEntity(entityId, entType);
            if (metaType != null) {
                this.handleMetadata(entityId, (List)wrapper.get(metaType, 0), wrapper.user());
            }
        };
    }

    public PacketHandler getTrackerAndRewriter(@Nullable Type<List<Metadata>> metaType, EntityType entityType) {
        return wrapper -> {
            int entityId = wrapper.get(Type.VAR_INT, 0);
            wrapper.user().get(this.entityTrackerClass).addEntity(entityId, entityType);
            if (metaType != null) {
                this.handleMetadata(entityId, (List)wrapper.get(metaType, 0), wrapper.user());
            }
        };
    }

    public PacketHandler getObjectTracker() {
        return wrapper -> {
            int entityId = wrapper.get(Type.VAR_INT, 0);
            byte type = wrapper.get(Type.BYTE, 0);
            EntityType entType = this.getObjectTypeFromId(type);
            wrapper.user().get(this.entityTrackerClass).addEntity(entityId, entType);
        };
    }

    protected abstract EntityType getTypeFromId(int var1);

    protected EntityType getObjectTypeFromId(int type) {
        return this.getTypeFromId(type);
    }

    public int getNewEntityId(int oldId) {
        return this.typeMapping != null ? this.typeMapping.getOrDefault(oldId, oldId) : oldId;
    }

    protected abstract void handleMetadata(int var1, @Nullable EntityType var2, Metadata var3, List<Metadata> var4, UserConnection var5) throws Exception;

    @Nullable
    protected Metadata getMetaByIndex(int index, List<Metadata> metadataList) {
        for (Metadata metadata : metadataList) {
            if (metadata.getId() != index) continue;
            return metadata;
        }
        return null;
    }
}

