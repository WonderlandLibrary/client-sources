package com.viaversion.viaversion.protocols.protocol1_20to1_19_4.packets;

import com.viaversion.viaversion.api.minecraft.Quaternion;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.api.type.types.version.Types1_20;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_20to1_19_4.Protocol1_20To1_19_4;
import com.viaversion.viaversion.rewriter.EntityRewriter;

public final class EntityPackets extends EntityRewriter<ClientboundPackets1_19_4, Protocol1_20To1_19_4> {
   private static final Quaternion Y_FLIPPED_ROTATION = new Quaternion(0.0F, 1.0F, 0.0F, 0.0F);

   public EntityPackets(Protocol1_20To1_19_4 protocol) {
      super(protocol);
   }

   @Override
   public void registerPackets() {
      this.registerTrackerWithData1_19(ClientboundPackets1_19_4.SPAWN_ENTITY, EntityTypes1_19_4.FALLING_BLOCK);
      this.registerMetadataRewriter(ClientboundPackets1_19_4.ENTITY_METADATA, Types1_19_4.METADATA_LIST, Types1_20.METADATA_LIST);
      this.registerRemoveEntities(ClientboundPackets1_19_4.REMOVE_ENTITIES);
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
            this.map(Type.LONG);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.BOOLEAN);
            this.map(Type.BOOLEAN);
            this.map(Type.BOOLEAN);
            this.map(Type.BOOLEAN);
            this.map(Type.OPTIONAL_GLOBAL_POSITION);
            this.create(Type.VAR_INT, Integer.valueOf(0));
            this.handler(EntityPackets.this.dimensionDataHandler());
            this.handler(EntityPackets.this.biomeSizeTracker());
            this.handler(EntityPackets.this.worldDataTrackerHandlerByKey());
            this.handler(wrapper -> {
               CompoundTag registry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
               CompoundTag damageTypeRegistry = registry.get("minecraft:damage_type");
               ListTag damageTypes = damageTypeRegistry.get("value");
               int highestId = -1;

               for (Tag damageType : damageTypes) {
                  IntTag id = ((CompoundTag)damageType).get("id");
                  highestId = Math.max(highestId, id.asInt());
               }

               CompoundTag outsideBorderReason = new CompoundTag();
               CompoundTag outsideBorderElement = new CompoundTag();
               outsideBorderElement.put("scaling", new StringTag("always"));
               outsideBorderElement.put("exhaustion", new FloatTag(0.0F));
               outsideBorderElement.put("message_id", new StringTag("badRespawnPoint"));
               outsideBorderReason.put("id", new IntTag(highestId + 1));
               outsideBorderReason.put("name", new StringTag("minecraft:outside_border"));
               outsideBorderReason.put("element", outsideBorderElement);
               damageTypes.add(outsideBorderReason);
               CompoundTag genericKillReason = new CompoundTag();
               CompoundTag genericKillElement = new CompoundTag();
               genericKillElement.put("scaling", new StringTag("always"));
               genericKillElement.put("exhaustion", new FloatTag(0.0F));
               genericKillElement.put("message_id", new StringTag("badRespawnPoint"));
               genericKillReason.put("id", new IntTag(highestId + 2));
               genericKillReason.put("name", new StringTag("minecraft:generic_kill"));
               genericKillReason.put("element", genericKillElement);
               damageTypes.add(genericKillReason);
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19_4.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.STRING);
            this.map(Type.LONG);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
            this.map(Type.BOOLEAN);
            this.map(Type.BYTE);
            this.map(Type.OPTIONAL_GLOBAL_POSITION);
            this.create(Type.VAR_INT, Integer.valueOf(0));
            this.handler(EntityPackets.this.worldDataTrackerHandlerByKey());
         }
      });
   }

   @Override
   protected void registerRewrites() {
      this.filter().handler((event, meta) -> meta.setMetaType(Types1_20.META_TYPES.byId(meta.metaType().typeId())));
      this.registerMetaTypeHandler(
         Types1_20.META_TYPES.itemType, Types1_20.META_TYPES.blockStateType, Types1_20.META_TYPES.optionalBlockStateType, Types1_20.META_TYPES.particleType
      );
      this.filter().filterFamily(EntityTypes1_19_4.ITEM_DISPLAY).handler((event, meta) -> {
         if (!event.trackedEntity().hasSentMetadata() && !event.hasExtraMeta()) {
            if (event.metaAtIndex(12) == null) {
               event.createExtraMeta(new Metadata(12, Types1_20.META_TYPES.quaternionType, Y_FLIPPED_ROTATION));
            }
         }
      });
      this.filter().filterFamily(EntityTypes1_19_4.ITEM_DISPLAY).index(12).handler((event, meta) -> {
         Quaternion quaternion = meta.value();
         meta.setValue(this.rotateY180(quaternion));
      });
      this.filter().filterFamily(EntityTypes1_19_4.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
         int blockState = meta.<Integer>value();
         meta.setValue(this.protocol.getMappingData().getNewBlockStateId(blockState));
      });
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_19_4.getTypeFromId(type);
   }

   private Quaternion rotateY180(Quaternion quaternion) {
      return new Quaternion(-quaternion.z(), quaternion.w(), quaternion.x(), -quaternion.y());
   }
}
