package com.viaversion.viaversion.protocols.protocol1_14_1to1_14.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.Protocol1_14_1To1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_14_1To1_14 extends EntityRewriter<ClientboundPackets1_14, Protocol1_14_1To1_14> {
   public MetadataRewriter1_14_1To1_14(Protocol1_14_1To1_14 protocol) {
      super(protocol);
   }

   @Override
   public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) {
      if (type != null) {
         if ((type == EntityTypes1_14.VILLAGER || type == EntityTypes1_14.WANDERING_TRADER) && metadata.id() >= 15) {
            metadata.setId(metadata.id() + 1);
         }
      }
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_14.getTypeFromId(type);
   }
}
