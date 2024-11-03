package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata.MetadataRewriter1_15To1_14_4;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public final class EntityPackets {
   public static void register(Protocol1_15To1_14_4 protocol) {
      final MetadataRewriter1_15To1_14_4 metadataRewriter = protocol.get(MetadataRewriter1_15To1_14_4.class);
      metadataRewriter.registerTrackerWithData(ClientboundPackets1_14_4.SPAWN_ENTITY, EntityTypes1_15.FALLING_BLOCK);
      protocol.registerClientbound(ClientboundPackets1_14_4.SPAWN_MOB, new PacketHandlers() {
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
            this.handler(metadataRewriter.trackerHandler());
            this.handler(wrapper -> EntityPackets.sendMetadataPacket(wrapper, wrapper.get(Type.VAR_INT, 0), metadataRewriter));
         }
      });
      protocol.registerClientbound(ClientboundPackets1_14_4.SPAWN_PLAYER, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.handler(wrapper -> {
               int entityId = wrapper.get(Type.VAR_INT, 0);
               wrapper.user().<EntityTracker>getEntityTracker(Protocol1_15To1_14_4.class).addEntity(entityId, EntityTypes1_15.PLAYER);
               EntityPackets.sendMetadataPacket(wrapper, entityId, metadataRewriter);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_14_4.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.handler(wrapper -> wrapper.write(Type.LONG, 0L));
         }
      });
      protocol.registerClientbound(ClientboundPackets1_14_4.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.INT);
            this.handler(metadataRewriter.playerTrackerHandler());
            this.handler(wrapper -> wrapper.write(Type.LONG, 0L));
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.STRING);
            this.map(Type.VAR_INT);
            this.map(Type.BOOLEAN);
            this.handler(wrapper -> wrapper.write(Type.BOOLEAN, !Via.getConfig().is1_15InstantRespawn()));
         }
      });
      metadataRewriter.registerMetadataRewriter(ClientboundPackets1_14_4.ENTITY_METADATA, Types1_14.METADATA_LIST);
      metadataRewriter.registerRemoveEntities(ClientboundPackets1_14_4.DESTROY_ENTITIES);
   }

   private static void sendMetadataPacket(PacketWrapper wrapper, int entityId, EntityRewriter<?, ?> rewriter) throws Exception {
      List<Metadata> metadata = wrapper.read(Types1_14.METADATA_LIST);
      if (!metadata.isEmpty()) {
         wrapper.send(Protocol1_15To1_14_4.class);
         wrapper.cancel();
         rewriter.handleMetadata(entityId, metadata, wrapper.user());
         PacketWrapper metadataPacket = PacketWrapper.create(ClientboundPackets1_15.ENTITY_METADATA, wrapper.user());
         metadataPacket.write(Type.VAR_INT, entityId);
         metadataPacket.write(Types1_14.METADATA_LIST, metadata);
         metadataPacket.send(Protocol1_15To1_14_4.class);
      }
   }

   public static int getNewEntityId(int oldId) {
      return oldId >= 4 ? oldId + 1 : oldId;
   }
}
