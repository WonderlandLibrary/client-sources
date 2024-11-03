package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.EntityTypeRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import java.util.List;

public class MetadataRewriter1_13To1_12_2 extends EntityRewriter<ClientboundPackets1_12_1, Protocol1_13To1_12_2> {
   public MetadataRewriter1_13To1_12_2(Protocol1_13To1_12_2 protocol) {
      super(protocol);
   }

   @Override
   protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
      if (metadata.metaType().typeId() > 4) {
         metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId() + 1));
      } else {
         metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
      }

      if (metadata.id() == 2) {
         if (metadata.getValue() != null && !((String)metadata.getValue()).isEmpty()) {
            metadata.setTypeAndValue(Types1_13.META_TYPES.optionalComponentType, ComponentUtil.legacyToJson((String)metadata.getValue()));
         } else {
            metadata.setTypeAndValue(Types1_13.META_TYPES.optionalComponentType, null);
         }
      }

      if (type == EntityTypes1_13.EntityType.ENDERMAN && metadata.id() == 12) {
         int stateId = (Integer)metadata.getValue();
         int id = stateId & 4095;
         int data = stateId >> 12 & 15;
         metadata.setValue(id << 4 | data & 15);
      }

      if (metadata.metaType() == Types1_13.META_TYPES.itemType) {
         metadata.setMetaType(Types1_13.META_TYPES.itemType);
         this.protocol.getItemRewriter().handleItemToClient((Item)metadata.getValue());
      } else if (metadata.metaType() == Types1_13.META_TYPES.blockStateType) {
         metadata.setValue(WorldPackets.toNewId((Integer)metadata.getValue()));
      }

      if (type != null) {
         if (type == EntityTypes1_13.EntityType.WOLF && metadata.id() == 17) {
            metadata.setValue(15 - (Integer)metadata.getValue());
         }

         if (type.isOrHasParent(EntityTypes1_13.EntityType.ZOMBIE) && metadata.id() > 14) {
            metadata.setId(metadata.id() + 1);
         }

         if (type.isOrHasParent(EntityTypes1_13.EntityType.MINECART_ABSTRACT) && metadata.id() == 9) {
            int oldId = (Integer)metadata.getValue();
            int combined = (oldId & 4095) << 4 | oldId >> 12 & 15;
            int newId = WorldPackets.toNewId(combined);
            metadata.setValue(newId);
         }

         if (type == EntityTypes1_13.EntityType.AREA_EFFECT_CLOUD) {
            if (metadata.id() == 9) {
               int particleId = (Integer)metadata.getValue();
               Metadata parameter1Meta = this.metaByIndex(10, metadatas);
               Metadata parameter2Meta = this.metaByIndex(11, metadatas);
               int parameter1 = parameter1Meta != null ? (Integer)parameter1Meta.getValue() : 0;
               int parameter2 = parameter2Meta != null ? (Integer)parameter2Meta.getValue() : 0;
               Particle particle = ParticleRewriter.rewriteParticle(particleId, new Integer[]{parameter1, parameter2});
               if (particle != null && particle.getId() != -1) {
                  metadatas.add(new Metadata(9, Types1_13.META_TYPES.particleType, particle));
               }
            }

            if (metadata.id() >= 9) {
               metadatas.remove(metadata);
            }
         }

         if (metadata.id() == 0) {
            metadata.setValue((byte)((Byte)metadata.getValue() & -17));
         }
      }
   }

   @Override
   public int newEntityId(int id) {
      return EntityTypeRewriter.getNewId(id);
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_13.getTypeFromId(type, false);
   }

   @Override
   public EntityType objectTypeFromId(int type) {
      return EntityTypes1_13.getTypeFromId(type, true);
   }
}
