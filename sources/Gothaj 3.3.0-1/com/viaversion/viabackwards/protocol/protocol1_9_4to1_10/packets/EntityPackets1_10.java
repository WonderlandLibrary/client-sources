package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets;

import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_12;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import java.util.List;
import java.util.Optional;

public class EntityPackets1_10 extends LegacyEntityRewriter<ClientboundPackets1_9_3, Protocol1_9_4To1_10> {
   public EntityPackets1_10(Protocol1_9_4To1_10 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      this.protocol.registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.BYTE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.INT);
            this.handler(EntityPackets1_10.this.getObjectTrackerHandler());
            this.handler(EntityPackets1_10.this.getObjectRewriter(id -> EntityTypes1_11.ObjectType.findById(id).orElse(null)));
            this.handler(wrapper -> {
               Optional<EntityTypes1_12.ObjectType> type = EntityTypes1_12.ObjectType.findById(wrapper.get(Type.BYTE, 0));
               if (type.isPresent() && type.get() == EntityTypes1_12.ObjectType.FALLING_BLOCK) {
                  int objectData = wrapper.get(Type.INT, 0);
                  int objType = objectData & 4095;
                  int data = objectData >> 12 & 15;
                  Block block = EntityPackets1_10.this.protocol.getItemRewriter().handleBlock(objType, data);
                  if (block == null) {
                     return;
                  }

                  wrapper.set(Type.INT, 0, block.getId() | block.getData() << 12);
               }
            });
         }
      });
      this.registerTracker(ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, EntityTypes1_10.EntityType.EXPERIENCE_ORB);
      this.registerTracker(ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, EntityTypes1_10.EntityType.WEATHER);
      this.protocol.registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_9.METADATA_LIST);
            this.handler(EntityPackets1_10.this.getTrackerHandler(Type.UNSIGNED_BYTE, 0));
            this.handler(wrapper -> {
               int entityId = wrapper.get(Type.VAR_INT, 0);
               EntityType type = EntityPackets1_10.this.<EntityTracker>tracker(wrapper.user()).entityType(entityId);
               List<Metadata> metadata = wrapper.get(Types1_9.METADATA_LIST, 0);
               EntityPackets1_10.this.handleMetadata(wrapper.get(Type.VAR_INT, 0), metadata, wrapper.user());
               EntityData entityData = EntityPackets1_10.this.entityDataForType(type);
               if (entityData != null) {
                  WrappedMetadata storage = new WrappedMetadata(metadata);
                  wrapper.set(Type.UNSIGNED_BYTE, 0, (short)entityData.replacementId());
                  if (entityData.hasBaseMeta()) {
                     entityData.defaultMeta().createMeta(storage);
                  }
               }
            });
         }
      });
      this.registerTracker(ClientboundPackets1_9_3.SPAWN_PAINTING, EntityTypes1_10.EntityType.PAINTING);
      this.registerJoinGame(ClientboundPackets1_9_3.JOIN_GAME, EntityTypes1_10.EntityType.PLAYER);
      this.registerRespawn(ClientboundPackets1_9_3.RESPAWN);
      this.protocol.registerClientbound(ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Types1_9.METADATA_LIST);
            this.handler(EntityPackets1_10.this.getTrackerAndMetaHandler(Types1_9.METADATA_LIST, EntityTypes1_11.EntityType.PLAYER));
         }
      });
      this.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
      this.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
   }

   @Override
   protected void registerRewrites() {
      this.mapEntityTypeWithData(EntityTypes1_10.EntityType.POLAR_BEAR, EntityTypes1_10.EntityType.SHEEP).plainName();
      this.filter().type(EntityTypes1_10.EntityType.POLAR_BEAR).index(13).handler((event, meta) -> {
         boolean b = (Boolean)meta.getValue();
         meta.setTypeAndValue(MetaType1_9.Byte, Byte.valueOf((byte)(b ? 14 : 0)));
      });
      this.filter().type(EntityTypes1_10.EntityType.ZOMBIE).index(13).handler((event, meta) -> {
         if ((Integer)meta.getValue() == 6) {
            meta.setValue(0);
         }
      });
      this.filter().type(EntityTypes1_10.EntityType.SKELETON).index(12).handler((event, meta) -> {
         if ((Integer)meta.getValue() == 2) {
            meta.setValue(0);
         }
      });
      this.filter().removeIndex(5);
   }

   @Override
   public EntityType typeFromId(int typeId) {
      return EntityTypes1_10.getTypeFromId(typeId, false);
   }

   @Override
   protected EntityType getObjectTypeFromId(int typeId) {
      return EntityTypes1_10.getTypeFromId(typeId, true);
   }
}
