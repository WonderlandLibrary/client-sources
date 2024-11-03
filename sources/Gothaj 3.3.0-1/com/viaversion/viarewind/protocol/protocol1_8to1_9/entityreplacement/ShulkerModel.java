package com.viaversion.viarewind.protocol.protocol1_8to1_9.entityreplacement;

import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.ArrayList;
import java.util.List;

public class ShulkerModel extends EntityModel1_8To1_9 {
   private final int entityId;
   private final List<Metadata> datawatcher = new ArrayList<>();
   private double locX;
   private double locY;
   private double locZ;

   public ShulkerModel(UserConnection user, Protocol1_8To1_9 protocol, int entityId) {
      super(user, protocol);
      this.entityId = entityId;
      this.sendSpawnPacket();
   }

   @Override
   public void updateReplacementPosition(double x, double y, double z) {
      this.locX = x;
      this.locY = y;
      this.locZ = z;
      this.updateLocation();
   }

   @Override
   public void handleOriginalMovementPacket(double x, double y, double z) {
      this.locX += x;
      this.locY += y;
      this.locZ += z;
      this.updateLocation();
   }

   @Override
   public void setYawPitch(float yaw, float pitch) {
   }

   @Override
   public void setHeadYaw(float yaw) {
   }

   @Override
   public void updateMetadata(List<Metadata> metadataList) {
      for (Metadata metadata : metadataList) {
         this.datawatcher.removeIf(m -> m.id() == metadata.id());
         this.datawatcher.add(metadata);
      }

      this.updateMetadata();
   }

   public void updateLocation() {
      this.teleportEntity(this.entityId, this.locX, this.locY, this.locZ, 0.0F, 0.0F);
   }

   public void updateMetadata() {
      PacketWrapper metadataPacket = PacketWrapper.create(ClientboundPackets1_8.ENTITY_METADATA, null, this.user);
      metadataPacket.write(Type.VAR_INT, this.entityId);
      List<Metadata> metadataList = new ArrayList<>();

      for (Metadata metadata : this.datawatcher) {
         if (metadata.id() != 11 && metadata.id() != 12 && metadata.id() != 13) {
            metadataList.add(new Metadata(metadata.id(), metadata.metaType(), metadata.getValue()));
         }
      }

      metadataList.add(new Metadata(11, MetaType1_9.VarInt, 2));
      this.getProtocol()
         .getMetadataRewriter()
         .transform(this.user.get(EntityTracker.class), this.entityId, metadataList, EntityTypes1_10.EntityType.MAGMA_CUBE);
      metadataPacket.write(Types1_8.METADATA_LIST, metadataList);
      PacketUtil.sendPacket(metadataPacket, Protocol1_8To1_9.class);
   }

   @Override
   public void sendSpawnPacket() {
      this.sendSpawn(this.entityId, 62);
   }

   @Override
   public void deleteEntity() {
      PacketWrapper despawn = PacketWrapper.create(ClientboundPackets1_7_2_5.DESTROY_ENTITIES, null, this.user);
      despawn.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{this.entityId});
      PacketUtil.sendPacket(despawn, Protocol1_8To1_9.class, true, true);
   }

   @Override
   public int getEntityId() {
      return this.entityId;
   }
}
