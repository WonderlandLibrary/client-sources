package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16_2;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_17;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.rewriter.EntityRewriter;

public final class EntityPackets extends EntityRewriter<ClientboundPackets1_16_2, Protocol1_17To1_16_4> {
   public EntityPackets(Protocol1_17To1_16_4 protocol) {
      super(protocol);
      this.mapTypes(EntityTypes1_16_2.values(), EntityTypes1_17.class);
   }

   @Override
   public void registerPackets() {
      this.registerTrackerWithData(ClientboundPackets1_16_2.SPAWN_ENTITY, EntityTypes1_17.FALLING_BLOCK);
      this.registerTracker(ClientboundPackets1_16_2.SPAWN_MOB);
      this.registerTracker(ClientboundPackets1_16_2.SPAWN_PLAYER, EntityTypes1_17.PLAYER);
      this.registerMetadataRewriter(ClientboundPackets1_16_2.ENTITY_METADATA, Types1_16.METADATA_LIST, Types1_17.METADATA_LIST);
      this.protocol.registerClientbound(ClientboundPackets1_16_2.DESTROY_ENTITIES, null, wrapper -> {
         int[] entityIds = wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
         wrapper.cancel();
         EntityTracker entityTracker = wrapper.user().getEntityTracker(Protocol1_17To1_16_4.class);

         for (int entityId : entityIds) {
            entityTracker.removeEntity(entityId);
            PacketWrapper newPacket = wrapper.create(ClientboundPackets1_17.REMOVE_ENTITY);
            newPacket.write(Type.VAR_INT, entityId);
            newPacket.send(Protocol1_17To1_16_4.class);
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16_2.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.STRING_ARRAY);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.handler(wrapper -> {
               CompoundTag dimensionRegistry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0).get("minecraft:dimension_type");

               for (Tag dimension : (ListTag)dimensionRegistry.get("value")) {
                  CompoundTag dimensionCompound = ((CompoundTag)dimension).get("element");
                  EntityPackets.addNewDimensionData(dimensionCompound);
               }

               CompoundTag currentDimensionTag = wrapper.get(Type.NAMED_COMPOUND_TAG, 1);
               EntityPackets.addNewDimensionData(currentDimensionTag);
            });
            this.handler(EntityPackets.this.playerTrackerHandler());
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16_2.RESPAWN, wrapper -> {
         CompoundTag dimensionData = wrapper.passthrough(Type.NAMED_COMPOUND_TAG);
         addNewDimensionData(dimensionData);
      });
      this.protocol.registerClientbound(ClientboundPackets1_16_2.ENTITY_PROPERTIES, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> wrapper.write(Type.VAR_INT, wrapper.read(Type.INT)));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16_2.PLAYER_POSITION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.BYTE);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> wrapper.write(Type.BOOLEAN, false));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16_2.COMBAT_EVENT, null, wrapper -> {
         int type = wrapper.read(Type.VAR_INT);
         ClientboundPacketType packetType;
         switch (type) {
            case 0:
               packetType = ClientboundPackets1_17.COMBAT_ENTER;
               break;
            case 1:
               packetType = ClientboundPackets1_17.COMBAT_END;
               break;
            case 2:
               packetType = ClientboundPackets1_17.COMBAT_KILL;
               break;
            default:
               throw new IllegalArgumentException("Invalid combat type received: " + type);
         }

         wrapper.setPacketType(packetType);
      });
      this.protocol.cancelClientbound(ClientboundPackets1_16_2.ENTITY_MOVEMENT);
   }

   @Override
   protected void registerRewrites() {
      this.filter().handler((event, meta) -> {
         meta.setMetaType(Types1_17.META_TYPES.byId(meta.metaType().typeId()));
         if (meta.metaType() == Types1_17.META_TYPES.poseType) {
            int pose = meta.<Integer>value();
            if (pose > 5) {
               meta.setValue(pose + 1);
            }
         }
      });
      this.registerMetaTypeHandler(Types1_17.META_TYPES.itemType, Types1_17.META_TYPES.blockStateType, null, Types1_17.META_TYPES.particleType);
      this.filter().filterFamily(EntityTypes1_17.ENTITY).addIndex(7);
      this.filter().filterFamily(EntityTypes1_17.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
         int data = (Integer)meta.getValue();
         meta.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
      });
      this.filter().type(EntityTypes1_17.SHULKER).removeIndex(17);
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_17.getTypeFromId(type);
   }

   private static void addNewDimensionData(CompoundTag tag) {
      tag.put("min_y", new IntTag(0));
      tag.put("height", new IntTag(256));
   }
}
