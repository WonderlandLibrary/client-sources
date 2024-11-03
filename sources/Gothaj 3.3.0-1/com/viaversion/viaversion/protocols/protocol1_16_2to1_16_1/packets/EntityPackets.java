package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16_2;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.metadata.MetadataRewriter1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;

public class EntityPackets {
   public static void register(final Protocol1_16_2To1_16_1 protocol) {
      final MetadataRewriter1_16_2To1_16_1 metadataRewriter = protocol.get(MetadataRewriter1_16_2To1_16_1.class);
      metadataRewriter.registerTrackerWithData(ClientboundPackets1_16.SPAWN_ENTITY, EntityTypes1_16_2.FALLING_BLOCK);
      metadataRewriter.registerTracker(ClientboundPackets1_16.SPAWN_MOB);
      metadataRewriter.registerTracker(ClientboundPackets1_16.SPAWN_PLAYER, EntityTypes1_16_2.PLAYER);
      metadataRewriter.registerMetadataRewriter(ClientboundPackets1_16.ENTITY_METADATA, Types1_16.METADATA_LIST);
      metadataRewriter.registerRemoveEntities(ClientboundPackets1_16.DESTROY_ENTITIES);
      protocol.registerClientbound(ClientboundPackets1_16.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.handler(wrapper -> {
               short gamemode = wrapper.read(Type.UNSIGNED_BYTE);
               wrapper.write(Type.BOOLEAN, (gamemode & 8) != 0);
               wrapper.write(Type.BYTE, (byte)(gamemode & -9));
            });
            this.map(Type.BYTE);
            this.map(Type.STRING_ARRAY);
            this.handler(wrapper -> {
               wrapper.read(Type.NAMED_COMPOUND_TAG);
               wrapper.write(Type.NAMED_COMPOUND_TAG, protocol.getMappingData().getDimensionRegistry());
               String dimensionType = wrapper.read(Type.STRING);
               wrapper.write(Type.NAMED_COMPOUND_TAG, EntityPackets.getDimensionData(dimensionType));
            });
            this.map(Type.STRING);
            this.map(Type.LONG);
            this.map(Type.UNSIGNED_BYTE, Type.VAR_INT);
            this.handler(metadataRewriter.playerTrackerHandler());
         }
      });
      protocol.registerClientbound(ClientboundPackets1_16.RESPAWN, wrapper -> {
         String dimensionType = wrapper.read(Type.STRING);
         wrapper.write(Type.NAMED_COMPOUND_TAG, getDimensionData(dimensionType));
      });
   }

   public static CompoundTag getDimensionData(String dimensionType) {
      CompoundTag tag = Protocol1_16_2To1_16_1.MAPPINGS.getDimensionDataMap().get(dimensionType);
      if (tag == null) {
         Via.getPlatform().getLogger().severe("Could not get dimension data of " + dimensionType);
         throw new NullPointerException("Dimension data for " + dimensionType + " is null!");
      } else {
         return tag.copy();
      }
   }
}
