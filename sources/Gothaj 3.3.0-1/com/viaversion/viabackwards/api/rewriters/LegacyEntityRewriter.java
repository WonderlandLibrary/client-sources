package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.EntityObjectData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class LegacyEntityRewriter<C extends ClientboundPacketType, T extends BackwardsProtocol<C, ?, ?, ?>> extends EntityRewriterBase<C, T> {
   private final Map<ObjectType, EntityData> objectTypes = new HashMap<>();

   protected LegacyEntityRewriter(T protocol) {
      this(protocol, MetaType1_9.String, MetaType1_9.Boolean);
   }

   protected LegacyEntityRewriter(T protocol, MetaType displayType, MetaType displayVisibilityType) {
      super(protocol, displayType, 2, displayVisibilityType, 3);
   }

   protected EntityObjectData mapObjectType(ObjectType oldObjectType, ObjectType replacement, int data) {
      EntityObjectData entData = new EntityObjectData(this.protocol, oldObjectType.getType().name(), oldObjectType.getId(), replacement.getId(), data);
      this.objectTypes.put(oldObjectType, entData);
      return entData;
   }

   @Nullable
   protected EntityData getObjectData(ObjectType type) {
      return this.objectTypes.get(type);
   }

   protected void registerRespawn(C packetType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.handler(wrapper -> {
               ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
               clientWorld.setEnvironment(wrapper.get(Type.INT, 0));
            });
         }
      });
   }

   protected void registerJoinGame(C packetType, final EntityType playerType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.INT);
            this.handler(wrapper -> {
               ClientWorld clientChunks = wrapper.user().get(ClientWorld.class);
               clientChunks.setEnvironment(wrapper.get(Type.INT, 1));
               LegacyEntityRewriter.this.addTrackedEntity(wrapper, wrapper.get(Type.INT, 0), playerType);
            });
         }
      });
   }

   @Override
   public void registerMetadataRewriter(C packetType, final Type<List<Metadata>> oldMetaType, final Type<List<Metadata>> newMetaType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            if (oldMetaType != null) {
               this.map(oldMetaType, newMetaType);
            } else {
               this.map(newMetaType);
            }

            this.handler(wrapper -> {
               List<Metadata> metadata = wrapper.get(newMetaType, 0);
               LegacyEntityRewriter.this.handleMetadata(wrapper.get(Type.VAR_INT, 0), metadata, wrapper.user());
            });
         }
      });
   }

   @Override
   public void registerMetadataRewriter(C packetType, Type<List<Metadata>> metaType) {
      this.registerMetadataRewriter(packetType, null, metaType);
   }

   protected PacketHandler getMobSpawnRewriter(Type<List<Metadata>> metaType) {
      return wrapper -> {
         int entityId = wrapper.get(Type.VAR_INT, 0);
         EntityType type = this.<EntityTracker>tracker(wrapper.user()).entityType(entityId);
         List<Metadata> metadata = wrapper.get(metaType, 0);
         this.handleMetadata(entityId, metadata, wrapper.user());
         EntityData entityData = this.entityDataForType(type);
         if (entityData != null) {
            wrapper.set(Type.VAR_INT, 1, entityData.replacementId());
            if (entityData.hasBaseMeta()) {
               entityData.defaultMeta().createMeta(new WrappedMetadata(metadata));
            }
         }
      };
   }

   protected PacketHandler getObjectTrackerHandler() {
      return wrapper -> this.addTrackedEntity(wrapper, wrapper.get(Type.VAR_INT, 0), this.getObjectTypeFromId(wrapper.get(Type.BYTE, 0)));
   }

   protected PacketHandler getTrackerAndMetaHandler(Type<List<Metadata>> metaType, EntityType entityType) {
      return wrapper -> {
         this.addTrackedEntity(wrapper, wrapper.get(Type.VAR_INT, 0), entityType);
         List<Metadata> metadata = wrapper.get(metaType, 0);
         this.handleMetadata(wrapper.get(Type.VAR_INT, 0), metadata, wrapper.user());
      };
   }

   protected PacketHandler getObjectRewriter(Function<Byte, ObjectType> objectGetter) {
      return wrapper -> {
         ObjectType type = objectGetter.apply(wrapper.get(Type.BYTE, 0));
         if (type == null) {
            ViaBackwards.getPlatform().getLogger().warning("Could not find Entity Type" + wrapper.get(Type.BYTE, 0));
         } else {
            EntityData data = this.getObjectData(type);
            if (data != null) {
               wrapper.set(Type.BYTE, 0, (byte)data.replacementId());
               if (data.objectData() != -1) {
                  wrapper.set(Type.INT, 0, data.objectData());
               }
            }
         }
      };
   }

   protected EntityType getObjectTypeFromId(int typeId) {
      return this.typeFromId(typeId);
   }

   @Deprecated
   protected void addTrackedEntity(PacketWrapper wrapper, int entityId, EntityType type) throws Exception {
      this.<EntityTracker>tracker(wrapper.user()).addEntity(entityId, type);
   }
}
