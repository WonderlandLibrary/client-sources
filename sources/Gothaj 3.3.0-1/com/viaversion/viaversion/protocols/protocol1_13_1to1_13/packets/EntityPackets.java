package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata.MetadataRewriter1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;

public class EntityPackets {
   public static void register(final Protocol1_13_1To1_13 protocol) {
      final MetadataRewriter1_13_1To1_13 metadataRewriter = protocol.get(MetadataRewriter1_13_1To1_13.class);
      protocol.registerClientbound(ClientboundPackets1_13.SPAWN_ENTITY, new PacketHandlers() {
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
               if (entType != null) {
                  if (entType.is(EntityTypes1_13.EntityType.FALLING_BLOCK)) {
                     int data = wrapper.get(Type.INT, 0);
                     wrapper.set(Type.INT, 0, protocol.getMappingData().getNewBlockStateId(data));
                  }

                  wrapper.user().<EntityTracker>getEntityTracker(Protocol1_13_1To1_13.class).addEntity(entityId, entType);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketHandlers() {
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
            this.handler(metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST));
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketHandlers() {
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
            this.handler(metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST, EntityTypes1_13.EntityType.PLAYER));
         }
      });
      metadataRewriter.registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
      metadataRewriter.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST);
   }
}
