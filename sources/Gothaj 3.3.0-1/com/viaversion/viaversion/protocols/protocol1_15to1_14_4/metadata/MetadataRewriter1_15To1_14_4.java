package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.EntityPackets;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_15To1_14_4 extends EntityRewriter<ClientboundPackets1_14_4, Protocol1_15To1_14_4> {
   public MetadataRewriter1_15To1_14_4(Protocol1_15To1_14_4 protocol) {
      super(protocol);
   }

   @Override
   public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
      if (metadata.metaType() == Types1_14.META_TYPES.itemType) {
         this.protocol.getItemRewriter().handleItemToClient((Item)metadata.getValue());
      } else if (metadata.metaType() == Types1_14.META_TYPES.blockStateType) {
         int data = (Integer)metadata.getValue();
         metadata.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
      } else if (metadata.metaType() == Types1_14.META_TYPES.particleType) {
         this.rewriteParticle((Particle)metadata.getValue());
      }

      if (type != null) {
         if (type.isOrHasParent(EntityTypes1_15.MINECART_ABSTRACT) && metadata.id() == 10) {
            int data = (Integer)metadata.getValue();
            metadata.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
         }

         if (metadata.id() > 11 && type.isOrHasParent(EntityTypes1_15.LIVINGENTITY)) {
            metadata.setId(metadata.id() + 1);
         }

         if (type.isOrHasParent(EntityTypes1_15.WOLF)) {
            if (metadata.id() == 18) {
               metadatas.remove(metadata);
            } else if (metadata.id() > 18) {
               metadata.setId(metadata.id() - 1);
            }
         }
      }
   }

   @Override
   public int newEntityId(int id) {
      return EntityPackets.getNewEntityId(id);
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_15.getTypeFromId(type);
   }
}
