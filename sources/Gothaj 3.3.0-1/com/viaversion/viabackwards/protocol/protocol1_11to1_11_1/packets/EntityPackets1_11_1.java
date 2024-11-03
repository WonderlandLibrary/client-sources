package com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.Protocol1_11To1_11_1;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;

public class EntityPackets1_11_1 extends LegacyEntityRewriter<ClientboundPackets1_9_3, Protocol1_11To1_11_1> {
   public EntityPackets1_11_1(Protocol1_11To1_11_1 protocol) {
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
            this.handler(EntityPackets1_11_1.this.getObjectTrackerHandler());
            this.handler(EntityPackets1_11_1.this.getObjectRewriter(id -> EntityTypes1_11.ObjectType.findById(id).orElse(null)));
         }
      });
      this.registerTracker(ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, EntityTypes1_11.EntityType.EXPERIENCE_ORB);
      this.registerTracker(ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, EntityTypes1_11.EntityType.WEATHER);
      this.protocol.registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketHandlers() {
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
            this.map(Types1_9.METADATA_LIST);
            this.handler(EntityPackets1_11_1.this.getTrackerHandler());
            this.handler(EntityPackets1_11_1.this.getMobSpawnRewriter(Types1_9.METADATA_LIST));
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
            this.handler(EntityPackets1_11_1.this.getTrackerAndMetaHandler(Types1_9.METADATA_LIST, EntityTypes1_11.EntityType.PLAYER));
         }
      });
      this.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
      this.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
   }

   @Override
   protected void registerRewrites() {
      this.filter().type(EntityTypes1_11.EntityType.FIREWORK).cancel(7);
      this.filter().type(EntityTypes1_11.EntityType.PIG).cancel(14);
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
