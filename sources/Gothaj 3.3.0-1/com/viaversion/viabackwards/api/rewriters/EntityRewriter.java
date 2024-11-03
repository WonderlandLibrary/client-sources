package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;

public abstract class EntityRewriter<C extends ClientboundPacketType, T extends BackwardsProtocol<C, ?, ?, ?>> extends EntityRewriterBase<C, T> {
   protected EntityRewriter(T protocol) {
      this(protocol, Types1_14.META_TYPES.optionalComponentType, Types1_14.META_TYPES.booleanType);
   }

   protected EntityRewriter(T protocol, MetaType displayType, MetaType displayVisibilityType) {
      super(protocol, displayType, 2, displayVisibilityType, 3);
   }

   @Override
   public void registerTrackerWithData(C packetType, final EntityType fallingBlockType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map((Type<T>)Type.UUID);
            this.map(Type.VAR_INT);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.INT);
            this.handler(EntityRewriter.this.getSpawnTrackerWithDataHandler(fallingBlockType));
         }
      });
   }

   @Override
   public void registerTrackerWithData1_19(C packetType, final EntityType fallingBlockType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map((Type<T>)Type.UUID);
            this.map(Type.VAR_INT);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.VAR_INT);
            this.handler(EntityRewriter.this.getSpawnTrackerWithDataHandler1_19(fallingBlockType));
         }
      });
   }

   public PacketHandler getSpawnTrackerWithDataHandler(EntityType fallingBlockType) {
      return wrapper -> {
         EntityType entityType = this.trackAndMapEntity(wrapper);
         if (entityType == fallingBlockType) {
            int blockState = wrapper.get(Type.INT, 0);
            wrapper.set(Type.INT, 0, this.protocol.getMappingData().getNewBlockStateId(blockState));
         }
      };
   }

   public PacketHandler getSpawnTrackerWithDataHandler1_19(EntityType fallingBlockType) {
      return wrapper -> {
         if (this.protocol.getMappingData() != null) {
            EntityType entityType = this.trackAndMapEntity(wrapper);
            if (entityType == fallingBlockType) {
               int blockState = wrapper.get(Type.VAR_INT, 2);
               wrapper.set(Type.VAR_INT, 2, this.protocol.getMappingData().getNewBlockStateId(blockState));
            }
         }
      };
   }

   public void registerSpawnTracker(C packetType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map((Type<T>)Type.UUID);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> EntityRewriter.this.trackAndMapEntity(wrapper));
         }
      });
   }

   public PacketHandler worldTrackerHandlerByKey() {
      return wrapper -> {
         EntityTracker tracker = this.tracker(wrapper.user());
         String world = wrapper.get(Type.STRING, 1);
         if (tracker.currentWorld() != null && !tracker.currentWorld().equals(world)) {
            tracker.clearEntities();
            tracker.trackClientEntity();
         }

         tracker.setCurrentWorld(world);
      };
   }

   protected EntityType trackAndMapEntity(PacketWrapper wrapper) throws Exception {
      int typeId = wrapper.get(Type.VAR_INT, 1);
      EntityType entityType = this.typeFromId(typeId);
      this.<EntityTracker>tracker(wrapper.user()).addEntity(wrapper.get(Type.VAR_INT, 0), entityType);
      int mappedTypeId = this.newEntityId(entityType.getId());
      if (typeId != mappedTypeId) {
         wrapper.set(Type.VAR_INT, 1, mappedTypeId);
      }

      return entityType;
   }
}
