package com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.Protocol1_14To1_14_1;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import java.util.List;

public class EntityPackets1_14_1 extends LegacyEntityRewriter<ClientboundPackets1_14, Protocol1_14To1_14_1> {
   public EntityPackets1_14_1(Protocol1_14To1_14_1 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      this.registerTracker(ClientboundPackets1_14.SPAWN_EXPERIENCE_ORB, EntityTypes1_14.EXPERIENCE_ORB);
      this.registerTracker(ClientboundPackets1_14.SPAWN_GLOBAL_ENTITY, EntityTypes1_14.LIGHTNING_BOLT);
      this.registerTracker(ClientboundPackets1_14.SPAWN_PAINTING, EntityTypes1_14.PAINTING);
      this.registerTracker(ClientboundPackets1_14.SPAWN_PLAYER, EntityTypes1_14.PLAYER);
      this.registerTracker(ClientboundPackets1_14.JOIN_GAME, EntityTypes1_14.PLAYER, Type.INT);
      this.registerRemoveEntities(ClientboundPackets1_14.DESTROY_ENTITIES);
      this.protocol.registerClientbound(ClientboundPackets1_14.SPAWN_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.VAR_INT);
            this.handler(EntityPackets1_14_1.this.getTrackerHandler());
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_14.SPAWN_MOB, new PacketHandlers() {
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
            this.map(Types1_14.METADATA_LIST);
            this.handler(wrapper -> {
               int entityId = wrapper.get(Type.VAR_INT, 0);
               int type = wrapper.get(Type.VAR_INT, 1);
               EntityPackets1_14_1.this.<EntityTracker>tracker(wrapper.user()).addEntity(entityId, EntityTypes1_14.getTypeFromId(type));
               List<Metadata> metadata = wrapper.get(Types1_14.METADATA_LIST, 0);
               EntityPackets1_14_1.this.handleMetadata(entityId, metadata, wrapper.user());
            });
         }
      });
      this.registerMetadataRewriter(ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST);
   }

   @Override
   protected void registerRewrites() {
      this.filter().type(EntityTypes1_14.VILLAGER).cancel(15);
      this.filter().type(EntityTypes1_14.VILLAGER).index(16).toIndex(15);
      this.filter().type(EntityTypes1_14.WANDERING_TRADER).cancel(15);
   }

   @Override
   public EntityType typeFromId(int typeId) {
      return EntityTypes1_14.getTypeFromId(typeId);
   }
}
