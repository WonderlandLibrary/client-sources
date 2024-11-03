package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.VillagerData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_14To1_13_2 extends EntityRewriter<ClientboundPackets1_13, Protocol1_14To1_13_2> {
   public MetadataRewriter1_14To1_13_2(Protocol1_14To1_13_2 protocol) {
      super(protocol);
      this.mapTypes(EntityTypes1_13.EntityType.values(), EntityTypes1_14.class);
      this.mapEntityType(EntityTypes1_13.EntityType.OCELOT, EntityTypes1_14.CAT);
   }

   @Override
   protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
      metadata.setMetaType(Types1_14.META_TYPES.byId(metadata.metaType().typeId()));
      EntityTracker1_14 tracker = this.tracker(connection);
      if (metadata.metaType() == Types1_14.META_TYPES.itemType) {
         this.protocol.getItemRewriter().handleItemToClient((Item)metadata.getValue());
      } else if (metadata.metaType() == Types1_14.META_TYPES.blockStateType) {
         int data = (Integer)metadata.getValue();
         metadata.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
      } else if (metadata.metaType() == Types1_14.META_TYPES.particleType) {
         this.rewriteParticle((Particle)metadata.getValue());
      }

      if (type != null) {
         if (metadata.id() > 5) {
            metadata.setId(metadata.id() + 1);
         }

         if (metadata.id() == 8 && type.isOrHasParent(EntityTypes1_14.LIVINGENTITY)) {
            float v = ((Number)metadata.getValue()).floatValue();
            if (Float.isNaN(v) && Via.getConfig().is1_14HealthNaNFix()) {
               metadata.setValue(1.0F);
            }
         }

         if (metadata.id() > 11 && type.isOrHasParent(EntityTypes1_14.LIVINGENTITY)) {
            metadata.setId(metadata.id() + 1);
         }

         if (type.isOrHasParent(EntityTypes1_14.ABSTRACT_INSENTIENT) && metadata.id() == 13) {
            tracker.setInsentientData(entityId, (byte)(((Number)metadata.getValue()).byteValue() & -5 | tracker.getInsentientData(entityId) & 4));
            metadata.setValue(tracker.getInsentientData(entityId));
         }

         if (type.isOrHasParent(EntityTypes1_14.PLAYER)) {
            if (entityId != tracker.clientEntityId()) {
               if (metadata.id() == 0) {
                  byte flags = ((Number)metadata.getValue()).byteValue();
                  tracker.setEntityFlags(entityId, flags);
               } else if (metadata.id() == 7) {
                  tracker.setRiptide(entityId, (((Number)metadata.getValue()).byteValue() & 4) != 0);
               }

               if (metadata.id() == 0 || metadata.id() == 7) {
                  metadatas.add(new Metadata(6, Types1_14.META_TYPES.poseType, recalculatePlayerPose(entityId, tracker)));
               }
            }
         } else if (type.isOrHasParent(EntityTypes1_14.ZOMBIE)) {
            if (metadata.id() == 16) {
               tracker.setInsentientData(entityId, (byte)(tracker.getInsentientData(entityId) & -5 | (metadata.getValue() ? 4 : 0)));
               metadatas.remove(metadata);
               metadatas.add(new Metadata(13, Types1_14.META_TYPES.byteType, tracker.getInsentientData(entityId)));
            } else if (metadata.id() > 16) {
               metadata.setId(metadata.id() - 1);
            }
         }

         if (type.isOrHasParent(EntityTypes1_14.MINECART_ABSTRACT)) {
            if (metadata.id() == 10) {
               int data = (Integer)metadata.getValue();
               metadata.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
            }
         } else if (type.is(EntityTypes1_14.HORSE)) {
            if (metadata.id() == 18) {
               metadatas.remove(metadata);
               int armorType = (Integer)metadata.getValue();
               Item armorItem = null;
               if (armorType == 1) {
                  armorItem = new DataItem(this.protocol.getMappingData().getNewItemId(727), (byte)1, (short)0, null);
               } else if (armorType == 2) {
                  armorItem = new DataItem(this.protocol.getMappingData().getNewItemId(728), (byte)1, (short)0, null);
               } else if (armorType == 3) {
                  armorItem = new DataItem(this.protocol.getMappingData().getNewItemId(729), (byte)1, (short)0, null);
               }

               PacketWrapper equipmentPacket = PacketWrapper.create(ClientboundPackets1_14.ENTITY_EQUIPMENT, null, connection);
               equipmentPacket.write(Type.VAR_INT, entityId);
               equipmentPacket.write(Type.VAR_INT, 4);
               equipmentPacket.write(Type.ITEM1_13_2, armorItem);
               equipmentPacket.scheduleSend(Protocol1_14To1_13_2.class);
            }
         } else if (type.is(EntityTypes1_14.VILLAGER)) {
            if (metadata.id() == 15) {
               metadata.setTypeAndValue(Types1_14.META_TYPES.villagerDatatType, new VillagerData(2, getNewProfessionId((Integer)metadata.getValue()), 0));
            }
         } else if (type.is(EntityTypes1_14.ZOMBIE_VILLAGER)) {
            if (metadata.id() == 18) {
               metadata.setTypeAndValue(Types1_14.META_TYPES.villagerDatatType, new VillagerData(2, getNewProfessionId((Integer)metadata.getValue()), 0));
            }
         } else if (type.isOrHasParent(EntityTypes1_14.ABSTRACT_ARROW)) {
            if (metadata.id() >= 9) {
               metadata.setId(metadata.id() + 1);
            }
         } else if (type.is(EntityTypes1_14.FIREWORK_ROCKET)) {
            if (metadata.id() == 8) {
               metadata.setMetaType(Types1_14.META_TYPES.optionalVarIntType);
               if (metadata.getValue().equals(0)) {
                  metadata.setValue(null);
               }
            }
         } else if (type.isOrHasParent(EntityTypes1_14.ABSTRACT_SKELETON) && metadata.id() == 14) {
            tracker.setInsentientData(entityId, (byte)(tracker.getInsentientData(entityId) & -5 | (metadata.getValue() ? 4 : 0)));
            metadatas.remove(metadata);
            metadatas.add(new Metadata(13, Types1_14.META_TYPES.byteType, tracker.getInsentientData(entityId)));
         }

         if (type.isOrHasParent(EntityTypes1_14.ABSTRACT_ILLAGER_BASE) && metadata.id() == 14) {
            tracker.setInsentientData(entityId, (byte)(tracker.getInsentientData(entityId) & -5 | (((Number)metadata.getValue()).byteValue() != 0 ? 4 : 0)));
            metadatas.remove(metadata);
            metadatas.add(new Metadata(13, Types1_14.META_TYPES.byteType, tracker.getInsentientData(entityId)));
         }

         if ((type.is(EntityTypes1_14.WITCH) || type.is(EntityTypes1_14.RAVAGER) || type.isOrHasParent(EntityTypes1_14.ABSTRACT_ILLAGER_BASE))
            && metadata.id() >= 14) {
            metadata.setId(metadata.id() + 1);
         }
      }
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_14.getTypeFromId(type);
   }

   private static boolean isSneaking(byte flags) {
      return (flags & 2) != 0;
   }

   private static boolean isSwimming(byte flags) {
      return (flags & 16) != 0;
   }

   private static int getNewProfessionId(int old) {
      switch (old) {
         case 0:
            return 5;
         case 1:
            return 9;
         case 2:
            return 4;
         case 3:
            return 1;
         case 4:
            return 2;
         case 5:
            return 11;
         default:
            return 0;
      }
   }

   private static boolean isFallFlying(int entityFlags) {
      return (entityFlags & 128) != 0;
   }

   public static int recalculatePlayerPose(int entityId, EntityTracker1_14 tracker) {
      byte flags = tracker.getEntityFlags(entityId);
      int pose = 0;
      if (isFallFlying(flags)) {
         pose = 1;
      } else if (tracker.isSleeping(entityId)) {
         pose = 2;
      } else if (isSwimming(flags)) {
         pose = 3;
      } else if (tracker.isRiptide(entityId)) {
         pose = 4;
      } else if (isSneaking(flags)) {
         pose = 5;
      }

      return pose;
   }
}
