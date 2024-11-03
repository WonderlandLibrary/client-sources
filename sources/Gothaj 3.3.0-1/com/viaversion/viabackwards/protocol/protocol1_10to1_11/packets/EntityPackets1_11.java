package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.PotionSplashHandler;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
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

public class EntityPackets1_11 extends LegacyEntityRewriter<ClientboundPackets1_9_3, Protocol1_10To1_11> {
   public EntityPackets1_11(Protocol1_10To1_11 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      this.protocol.registerClientbound(ClientboundPackets1_9_3.EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.POSITION1_8);
            this.map(Type.INT);
            this.handler(wrapper -> {
               int type = wrapper.get(Type.INT, 0);
               if (type == 2002 || type == 2007) {
                  if (type == 2007) {
                     wrapper.set(Type.INT, 0, 2002);
                  }

                  int mappedData = PotionSplashHandler.getOldData(wrapper.get(Type.INT, 1));
                  if (mappedData != -1) {
                     wrapper.set(Type.INT, 1, mappedData);
                  }
               }
            });
         }
      });
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
            this.handler(EntityPackets1_11.this.getObjectTrackerHandler());
            this.handler(EntityPackets1_11.this.getObjectRewriter(id -> EntityTypes1_11.ObjectType.findById(id).orElse(null)));
            this.handler(wrapper -> {
               Optional<EntityTypes1_12.ObjectType> type = EntityTypes1_12.ObjectType.findById(wrapper.get(Type.BYTE, 0));
               if (type.isPresent() && type.get() == EntityTypes1_12.ObjectType.FALLING_BLOCK) {
                  int objectData = wrapper.get(Type.INT, 0);
                  int objType = objectData & 4095;
                  int data = objectData >> 12 & 15;
                  Block block = EntityPackets1_11.this.protocol.getItemRewriter().handleBlock(objType, data);
                  if (block == null) {
                     return;
                  }

                  wrapper.set(Type.INT, 0, block.getId() | block.getData() << 12);
               }
            });
         }
      });
      this.registerTracker(ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, EntityTypes1_11.EntityType.EXPERIENCE_ORB);
      this.registerTracker(ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, EntityTypes1_11.EntityType.WEATHER);
      this.protocol.registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.VAR_INT, Type.UNSIGNED_BYTE);
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
            this.handler(EntityPackets1_11.this.getTrackerHandler(Type.UNSIGNED_BYTE, 0));
            this.handler(wrapper -> {
               int entityId = wrapper.get(Type.VAR_INT, 0);
               EntityType type = EntityPackets1_11.this.<EntityTracker>tracker(wrapper.user()).entityType(entityId);
               List<Metadata> list = wrapper.get(Types1_9.METADATA_LIST, 0);
               EntityPackets1_11.this.handleMetadata(wrapper.get(Type.VAR_INT, 0), list, wrapper.user());
               EntityData entityData = EntityPackets1_11.this.entityDataForType(type);
               if (entityData != null) {
                  wrapper.set(Type.UNSIGNED_BYTE, 0, (short)entityData.replacementId());
                  if (entityData.hasBaseMeta()) {
                     entityData.defaultMeta().createMeta(new WrappedMetadata(list));
                  }
               }

               if (list.isEmpty()) {
                  list.add(new Metadata(0, MetaType1_9.Byte, (byte)0));
               }
            });
         }
      });
      this.registerTracker(ClientboundPackets1_9_3.SPAWN_PAINTING, EntityTypes1_11.EntityType.PAINTING);
      this.registerJoinGame(ClientboundPackets1_9_3.JOIN_GAME, EntityTypes1_11.EntityType.PLAYER);
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
            this.handler(EntityPackets1_11.this.getTrackerAndMetaHandler(Types1_9.METADATA_LIST, EntityTypes1_11.EntityType.PLAYER));
            this.handler(wrapper -> {
               List<Metadata> metadata = wrapper.get(Types1_9.METADATA_LIST, 0);
               if (metadata.isEmpty()) {
                  metadata.add(new Metadata(0, MetaType1_9.Byte, (byte)0));
               }
            });
         }
      });
      this.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
      this.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
      this.protocol.registerClientbound(ClientboundPackets1_9_3.ENTITY_STATUS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.BYTE);
            this.handler(wrapper -> {
               byte b = wrapper.get(Type.BYTE, 0);
               if (b == 35) {
                  wrapper.clearPacket();
                  wrapper.setPacketType(ClientboundPackets1_9_3.GAME_EVENT);
                  wrapper.write(Type.UNSIGNED_BYTE, (short)10);
                  wrapper.write(Type.FLOAT, 0.0F);
               }
            });
         }
      });
   }

   @Override
   protected void registerRewrites() {
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.ELDER_GUARDIAN, EntityTypes1_11.EntityType.GUARDIAN);
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.WITHER_SKELETON, EntityTypes1_11.EntityType.SKELETON)
         .spawnMetadata(storage -> storage.add(this.getSkeletonTypeMeta(1)));
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.STRAY, EntityTypes1_11.EntityType.SKELETON)
         .plainName()
         .spawnMetadata(storage -> storage.add(this.getSkeletonTypeMeta(2)));
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.HUSK, EntityTypes1_11.EntityType.ZOMBIE)
         .plainName()
         .spawnMetadata(storage -> this.handleZombieType(storage, 6));
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.ZOMBIE_VILLAGER, EntityTypes1_11.EntityType.ZOMBIE)
         .spawnMetadata(storage -> this.handleZombieType(storage, 1));
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.HORSE, EntityTypes1_11.EntityType.HORSE)
         .spawnMetadata(storage -> storage.add(this.getHorseMetaType(0)));
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.DONKEY, EntityTypes1_11.EntityType.HORSE)
         .spawnMetadata(storage -> storage.add(this.getHorseMetaType(1)));
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.MULE, EntityTypes1_11.EntityType.HORSE)
         .spawnMetadata(storage -> storage.add(this.getHorseMetaType(2)));
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.SKELETON_HORSE, EntityTypes1_11.EntityType.HORSE)
         .spawnMetadata(storage -> storage.add(this.getHorseMetaType(4)));
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.ZOMBIE_HORSE, EntityTypes1_11.EntityType.HORSE)
         .spawnMetadata(storage -> storage.add(this.getHorseMetaType(3)));
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.EVOCATION_FANGS, EntityTypes1_11.EntityType.SHULKER);
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.EVOCATION_ILLAGER, EntityTypes1_11.EntityType.VILLAGER).plainName();
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.VEX, EntityTypes1_11.EntityType.BAT).plainName();
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.VINDICATION_ILLAGER, EntityTypes1_11.EntityType.VILLAGER)
         .plainName()
         .spawnMetadata(storage -> storage.add(new Metadata(13, MetaType1_9.VarInt, 4)));
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.LIAMA, EntityTypes1_11.EntityType.HORSE)
         .plainName()
         .spawnMetadata(storage -> storage.add(this.getHorseMetaType(1)));
      this.mapEntityTypeWithData(EntityTypes1_11.EntityType.LIAMA_SPIT, EntityTypes1_11.EntityType.SNOWBALL);
      this.mapObjectType(EntityTypes1_11.ObjectType.LIAMA_SPIT, EntityTypes1_11.ObjectType.SNOWBALL, -1);
      this.mapObjectType(EntityTypes1_11.ObjectType.EVOCATION_FANGS, EntityTypes1_11.ObjectType.FALLING_BLOCK, 4294);
      this.filter().filterFamily(EntityTypes1_11.EntityType.GUARDIAN).index(12).handler((event, meta) -> {
         boolean b = (Boolean)meta.getValue();
         int bitmask = b ? 2 : 0;
         if (event.entityType() == EntityTypes1_11.EntityType.ELDER_GUARDIAN) {
            bitmask |= 4;
         }

         meta.setTypeAndValue(MetaType1_9.Byte, (byte)bitmask);
      });
      this.filter().filterFamily(EntityTypes1_11.EntityType.ABSTRACT_SKELETON).index(12).toIndex(13);
      this.filter().filterFamily(EntityTypes1_11.EntityType.ZOMBIE).handler((event, meta) -> {
         switch (meta.id()) {
            case 13:
               event.cancel();
               return;
            case 14:
               event.setIndex(15);
               break;
            case 15:
               event.setIndex(14);
               break;
            case 16:
               event.setIndex(13);
               meta.setValue(1 + (Integer)meta.getValue());
         }
      });
      this.filter().type(EntityTypes1_11.EntityType.EVOCATION_ILLAGER).index(12).handler((event, meta) -> {
         event.setIndex(13);
         meta.setTypeAndValue(MetaType1_9.VarInt, ((Byte)meta.getValue()).intValue());
      });
      this.filter().type(EntityTypes1_11.EntityType.VEX).index(12).handler((event, meta) -> meta.setValue((byte)0));
      this.filter().type(EntityTypes1_11.EntityType.VINDICATION_ILLAGER).index(12).handler((event, meta) -> {
         event.setIndex(13);
         meta.setTypeAndValue(MetaType1_9.VarInt, ((Number)meta.getValue()).intValue() == 1 ? 2 : 4);
      });
      this.filter().filterFamily(EntityTypes1_11.EntityType.ABSTRACT_HORSE).index(13).handler((event, meta) -> {
         StoredEntityData data = this.storedEntityData(event);
         byte b = (Byte)meta.getValue();
         if (data.has(ChestedHorseStorage.class) && data.get(ChestedHorseStorage.class).isChested()) {
            b = (byte)(b | 8);
            meta.setValue(b);
         }
      });
      this.filter().filterFamily(EntityTypes1_11.EntityType.CHESTED_HORSE).handler((event, meta) -> {
         StoredEntityData data = this.storedEntityData(event);
         if (!data.has(ChestedHorseStorage.class)) {
            data.put(new ChestedHorseStorage());
         }
      });
      this.filter().type(EntityTypes1_11.EntityType.HORSE).index(16).toIndex(17);
      this.filter().filterFamily(EntityTypes1_11.EntityType.CHESTED_HORSE).index(15).handler((event, meta) -> {
         StoredEntityData data = this.storedEntityData(event);
         ChestedHorseStorage storage = data.get(ChestedHorseStorage.class);
         boolean b = (Boolean)meta.getValue();
         storage.setChested(b);
         event.cancel();
      });
      this.filter().type(EntityTypes1_11.EntityType.LIAMA).handler((event, meta) -> {
         StoredEntityData data = this.storedEntityData(event);
         ChestedHorseStorage storage = data.get(ChestedHorseStorage.class);
         int index = event.index();
         switch (index) {
            case 16:
               storage.setLiamaStrength((Integer)meta.getValue());
               event.cancel();
               break;
            case 17:
               storage.setLiamaCarpetColor((Integer)meta.getValue());
               event.cancel();
               break;
            case 18:
               storage.setLiamaVariant((Integer)meta.getValue());
               event.cancel();
         }
      });
      this.filter().filterFamily(EntityTypes1_11.EntityType.ABSTRACT_HORSE).index(14).toIndex(16);
      this.filter().type(EntityTypes1_11.EntityType.VILLAGER).index(13).handler((event, meta) -> {
         if ((Integer)meta.getValue() == 5) {
            meta.setValue(0);
         }
      });
      this.filter().type(EntityTypes1_11.EntityType.SHULKER).cancel(15);
   }

   private Metadata getSkeletonTypeMeta(int type) {
      return new Metadata(12, MetaType1_9.VarInt, type);
   }

   private Metadata getZombieTypeMeta(int type) {
      return new Metadata(13, MetaType1_9.VarInt, type);
   }

   private void handleZombieType(WrappedMetadata storage, int type) {
      Metadata meta = storage.get(13);
      if (meta == null) {
         storage.add(this.getZombieTypeMeta(type));
      }
   }

   private Metadata getHorseMetaType(int type) {
      return new Metadata(14, MetaType1_9.VarInt, type);
   }

   @Override
   public EntityType typeFromId(int typeId) {
      return EntityTypes1_11.getTypeFromId(typeId, false);
   }

   @Override
   protected EntityType getObjectTypeFromId(int typeId) {
      return EntityTypes1_11.getTypeFromId(typeId, true);
   }
}
