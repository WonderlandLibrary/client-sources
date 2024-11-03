package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import java.util.List;

public class EntityPackets1_13_1 extends LegacyEntityRewriter<ClientboundPackets1_13, Protocol1_13To1_13_1> {
   public EntityPackets1_13_1(Protocol1_13To1_13_1 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      this.protocol.registerClientbound(ClientboundPackets1_13.SPAWN_ENTITY, new PacketHandlers() {
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
            this.handler(wrapper -> {
               int entityId = wrapper.get(Type.VAR_INT, 0);
               byte type = wrapper.get(Type.BYTE, 0);
               EntityTypes1_13.EntityType entType = EntityTypes1_13.getTypeFromId(type, true);
               if (entType == null) {
                  ViaBackwards.getPlatform().getLogger().warning("Could not find 1.13 entity type " + type);
               } else {
                  if (entType.is(EntityTypes1_13.EntityType.FALLING_BLOCK)) {
                     int data = wrapper.get(Type.INT, 0);
                     wrapper.set(Type.INT, 0, EntityPackets1_13_1.this.protocol.getMappingData().getNewBlockStateId(data));
                  }

                  EntityPackets1_13_1.this.<EntityTracker>tracker(wrapper.user()).addEntity(entityId, entType);
               }
            });
         }
      });
      this.registerTracker(ClientboundPackets1_13.SPAWN_EXPERIENCE_ORB, EntityTypes1_13.EntityType.EXPERIENCE_ORB);
      this.registerTracker(ClientboundPackets1_13.SPAWN_GLOBAL_ENTITY, EntityTypes1_13.EntityType.LIGHTNING_BOLT);
      this.protocol.registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketHandlers() {
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
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_13.METADATA_LIST);
            this.handler(EntityPackets1_13_1.this.getTrackerHandler());
            this.handler(wrapper -> {
               List<Metadata> metadata = wrapper.get(Types1_13.METADATA_LIST, 0);
               EntityPackets1_13_1.this.handleMetadata(wrapper.get(Type.VAR_INT, 0), metadata, wrapper.user());
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Types1_13.METADATA_LIST);
            this.handler(EntityPackets1_13_1.this.getTrackerAndMetaHandler(Types1_13.METADATA_LIST, EntityTypes1_13.EntityType.PLAYER));
         }
      });
      this.registerTracker(ClientboundPackets1_13.SPAWN_PAINTING, EntityTypes1_13.EntityType.PAINTING);
      this.registerJoinGame(ClientboundPackets1_13.JOIN_GAME, EntityTypes1_13.EntityType.PLAYER);
      this.registerRespawn(ClientboundPackets1_13.RESPAWN);
      this.registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
      this.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST);
   }

   @Override
   protected void registerRewrites() {
      this.filter().handler((event, meta) -> {
         if (meta.metaType() == Types1_13.META_TYPES.itemType) {
            this.protocol.getItemRewriter().handleItemToClient((Item)meta.getValue());
         } else if (meta.metaType() == Types1_13.META_TYPES.blockStateType) {
            int data = (Integer)meta.getValue();
            meta.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
         } else if (meta.metaType() == Types1_13.META_TYPES.particleType) {
            this.rewriteParticle((Particle)meta.getValue());
         } else if (meta.metaType() == Types1_13.META_TYPES.optionalComponentType || meta.metaType() == Types1_13.META_TYPES.componentType) {
            JsonElement element = meta.value();
            this.protocol.translatableRewriter().processText(element);
         }
      });
      this.filter().filterFamily(EntityTypes1_13.EntityType.ABSTRACT_ARROW).cancel(7);
      this.filter().type(EntityTypes1_13.EntityType.SPECTRAL_ARROW).index(8).toIndex(7);
      this.filter().type(EntityTypes1_13.EntityType.TRIDENT).index(8).toIndex(7);
      this.filter().filterFamily(EntityTypes1_13.EntityType.MINECART_ABSTRACT).index(9).handler((event, meta) -> {
         int data = (Integer)meta.getValue();
         meta.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
      });
   }

   @Override
   public EntityType typeFromId(int typeId) {
      return EntityTypes1_13.getTypeFromId(typeId, false);
   }

   @Override
   protected EntityType getObjectTypeFromId(int typeId) {
      return EntityTypes1_13.getTypeFromId(typeId, true);
   }
}
