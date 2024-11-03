package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_16To1_15_2 extends EntityRewriter<ClientboundPackets1_15, Protocol1_16To1_15_2> {
   public MetadataRewriter1_16To1_15_2(Protocol1_16To1_15_2 protocol) {
      super(protocol);
      this.mapEntityType(EntityTypes1_15.ZOMBIE_PIGMAN, EntityTypes1_16.ZOMBIFIED_PIGLIN);
      this.mapTypes(EntityTypes1_15.values(), EntityTypes1_16.class);
   }

   @Override
   public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
      metadata.setMetaType(Types1_16.META_TYPES.byId(metadata.metaType().typeId()));
      if (metadata.metaType() == Types1_16.META_TYPES.itemType) {
         this.protocol.getItemRewriter().handleItemToClient((Item)metadata.getValue());
      } else if (metadata.metaType() == Types1_16.META_TYPES.blockStateType) {
         int data = (Integer)metadata.getValue();
         metadata.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
      } else if (metadata.metaType() == Types1_16.META_TYPES.particleType) {
         this.rewriteParticle((Particle)metadata.getValue());
      }

      if (type != null) {
         if (type.isOrHasParent(EntityTypes1_16.MINECART_ABSTRACT) && metadata.id() == 10) {
            int data = (Integer)metadata.getValue();
            metadata.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
         }

         if (type.isOrHasParent(EntityTypes1_16.ABSTRACT_ARROW)) {
            if (metadata.id() == 8) {
               metadatas.remove(metadata);
            } else if (metadata.id() > 8) {
               metadata.setId(metadata.id() - 1);
            }
         }

         if (type == EntityTypes1_16.WOLF && metadata.id() == 16) {
            byte mask = metadata.<Byte>value();
            int angerTime = (mask & 2) != 0 ? Integer.MAX_VALUE : 0;
            metadatas.add(new Metadata(20, Types1_16.META_TYPES.varIntType, angerTime));
         }
      }
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_16.getTypeFromId(type);
   }
}
