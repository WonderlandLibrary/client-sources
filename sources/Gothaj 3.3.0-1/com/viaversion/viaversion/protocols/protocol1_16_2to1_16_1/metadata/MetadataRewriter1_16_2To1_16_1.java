package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16_2;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_16_2To1_16_1 extends EntityRewriter<ClientboundPackets1_16, Protocol1_16_2To1_16_1> {
   public MetadataRewriter1_16_2To1_16_1(Protocol1_16_2To1_16_1 protocol) {
      super(protocol);
      this.mapTypes(EntityTypes1_16.values(), EntityTypes1_16_2.class);
   }

   @Override
   public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
      if (metadata.metaType() == Types1_16.META_TYPES.itemType) {
         this.protocol.getItemRewriter().handleItemToClient((Item)metadata.getValue());
      } else if (metadata.metaType() == Types1_16.META_TYPES.blockStateType) {
         int data = (Integer)metadata.getValue();
         metadata.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
      } else if (metadata.metaType() == Types1_16.META_TYPES.particleType) {
         this.rewriteParticle((Particle)metadata.getValue());
      }

      if (type != null) {
         if (type.isOrHasParent(EntityTypes1_16_2.MINECART_ABSTRACT) && metadata.id() == 10) {
            int data = (Integer)metadata.getValue();
            metadata.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
         }

         if (type.isOrHasParent(EntityTypes1_16_2.ABSTRACT_PIGLIN)) {
            if (metadata.id() == 15) {
               metadata.setId(16);
            } else if (metadata.id() == 16) {
               metadata.setId(15);
            }
         }
      }
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_16_2.getTypeFromId(type);
   }
}
