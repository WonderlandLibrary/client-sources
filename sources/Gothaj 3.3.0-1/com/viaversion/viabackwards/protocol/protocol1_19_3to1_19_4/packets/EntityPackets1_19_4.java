package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets;

import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.Protocol1_19_3To1_19_4;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;

public final class EntityPackets1_19_4 extends EntityRewriter<ClientboundPackets1_19_4, Protocol1_19_3To1_19_4> {
   public EntityPackets1_19_4(Protocol1_19_3To1_19_4 protocol) {
      super(protocol, Types1_19_3.META_TYPES.optionalComponentType, Types1_19_3.META_TYPES.booleanType);
   }

   @Override
   public void registerPackets() {
      this.registerTrackerWithData1_19(ClientboundPackets1_19_4.SPAWN_ENTITY, EntityTypes1_19_4.FALLING_BLOCK);
      this.registerRemoveEntities(ClientboundPackets1_19_4.REMOVE_ENTITIES);
      this.registerMetadataRewriter(ClientboundPackets1_19_4.ENTITY_METADATA, Types1_19_4.METADATA_LIST, Types1_19_3.METADATA_LIST);
      this.protocol.registerClientbound(ClientboundPackets1_19_4.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.STRING_ARRAY);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.map(Type.STRING);
            this.map(Type.STRING);
            this.handler(EntityPackets1_19_4.this.dimensionDataHandler());
            this.handler(EntityPackets1_19_4.this.biomeSizeTracker());
            this.handler(EntityPackets1_19_4.this.worldDataTrackerHandlerByKey());
            this.handler(wrapper -> {
               CompoundTag registry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
               registry.remove("minecraft:trim_pattern");
               registry.remove("minecraft:trim_material");
               registry.remove("minecraft:damage_type");
               CompoundTag biomeRegistry = registry.get("minecraft:worldgen/biome");

               for (Tag biomeTag : (ListTag)biomeRegistry.get("value")) {
                  CompoundTag biomeData = ((CompoundTag)biomeTag).get("element");
                  NumberTag hasPrecipitation = biomeData.get("has_precipitation");
                  biomeData.put("precipitation", new StringTag(hasPrecipitation.asByte() == 1 ? "rain" : "none"));
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19_4.PLAYER_POSITION, new PacketHandlers() {
         @Override
         protected void register() {
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.BYTE);
            this.map(Type.VAR_INT);
            this.create(Type.BOOLEAN, Boolean.valueOf(false));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19_4.DAMAGE_EVENT, ClientboundPackets1_19_3.ENTITY_STATUS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT, Type.INT);
            this.read(Type.VAR_INT);
            this.read(Type.VAR_INT);
            this.read(Type.VAR_INT);
            this.handler(wrapper -> {
               if (wrapper.read(Type.BOOLEAN)) {
                  wrapper.read(Type.DOUBLE);
                  wrapper.read(Type.DOUBLE);
                  wrapper.read(Type.DOUBLE);
               }
            });
            this.create(Type.BYTE, Byte.valueOf((byte)2));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19_4.HIT_ANIMATION, ClientboundPackets1_19_3.ENTITY_ANIMATION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.read(Type.FLOAT);
            this.create(Type.UNSIGNED_BYTE, Short.valueOf((short)1));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19_4.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.STRING);
            this.handler(EntityPackets1_19_4.this.worldDataTrackerHandlerByKey());
         }
      });
   }

   @Override
   public void registerRewrites() {
      this.filter().handler((event, meta) -> {
         int id = meta.metaType().typeId();
         if (id < 25) {
            if (id >= 15) {
               id--;
            }

            meta.setMetaType(Types1_19_3.META_TYPES.byId(id));
         }
      });
      this.registerMetaTypeHandler(
         Types1_19_3.META_TYPES.itemType,
         Types1_19_3.META_TYPES.blockStateType,
         null,
         Types1_19_3.META_TYPES.particleType,
         Types1_19_3.META_TYPES.componentType,
         Types1_19_3.META_TYPES.optionalComponentType
      );
      this.filter().filterFamily(EntityTypes1_19_4.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
         int blockState = meta.<Integer>value();
         meta.setValue(this.protocol.getMappingData().getNewBlockStateId(blockState));
      });
      this.filter().filterFamily(EntityTypes1_19_4.BOAT).index(11).handler((event, meta) -> {
         int boatType = meta.<Integer>value();
         if (boatType > 4) {
            meta.setValue(boatType - 1);
         }
      });
      this.filter().type(EntityTypes1_19_4.TEXT_DISPLAY).index(22).handler((event, meta) -> {
         event.setIndex(2);
         meta.setMetaType(Types1_19_3.META_TYPES.optionalComponentType);
         event.createExtraMeta(new Metadata(3, Types1_19_3.META_TYPES.booleanType, true));
         JsonElement element = meta.value();
         this.protocol.getTranslatableRewriter().processText(element);
      });
      this.filter().filterFamily(EntityTypes1_19_4.DISPLAY).handler((event, meta) -> {
         if (event.index() > 7) {
            event.cancel();
         }
      });
      this.filter().type(EntityTypes1_19_4.INTERACTION).removeIndex(8);
      this.filter().type(EntityTypes1_19_4.INTERACTION).removeIndex(9);
      this.filter().type(EntityTypes1_19_4.INTERACTION).removeIndex(10);
      this.filter().type(EntityTypes1_19_4.SNIFFER).removeIndex(17);
      this.filter().type(EntityTypes1_19_4.SNIFFER).removeIndex(18);
      this.filter().filterFamily(EntityTypes1_19_4.ABSTRACT_HORSE).addIndex(18);
   }

   @Override
   public void onMappingDataLoaded() {
      this.mapTypes();
      EntityData.MetaCreator displayMetaCreator = storage -> {
         storage.add(new Metadata(0, Types1_19_3.META_TYPES.byteType, (byte)32));
         storage.add(new Metadata(5, Types1_19_3.META_TYPES.booleanType, true));
         storage.add(new Metadata(15, Types1_19_3.META_TYPES.byteType, (byte)17));
      };
      this.mapEntityTypeWithData(EntityTypes1_19_4.TEXT_DISPLAY, EntityTypes1_19_4.ARMOR_STAND).spawnMetadata(displayMetaCreator);
      this.mapEntityTypeWithData(EntityTypes1_19_4.ITEM_DISPLAY, EntityTypes1_19_4.ARMOR_STAND).spawnMetadata(displayMetaCreator);
      this.mapEntityTypeWithData(EntityTypes1_19_4.BLOCK_DISPLAY, EntityTypes1_19_4.ARMOR_STAND).spawnMetadata(displayMetaCreator);
      this.mapEntityTypeWithData(EntityTypes1_19_4.INTERACTION, EntityTypes1_19_4.ARMOR_STAND).spawnMetadata(displayMetaCreator);
      this.mapEntityTypeWithData(EntityTypes1_19_4.SNIFFER, EntityTypes1_19_4.RAVAGER).jsonName();
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_19_4.getTypeFromId(type);
   }
}
