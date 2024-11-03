package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.api.rewriter.ReplacementEntityTracker;
import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.ServerboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.model.VirtualHologramEntity;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.rewriter.MetadataRewriter;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class EntityTracker1_7_6_10 extends ReplacementEntityTracker {
   protected final MetadataRewriter metadataRewriter;
   private final Map<Integer, Integer> vehicleMap = new ConcurrentHashMap<>();
   private final Map<Integer, VirtualHologramEntity> virtualHologramMap = new HashMap<>();
   private final Map<Integer, UUID> playersByEntityId = new HashMap<>();
   private final Map<UUID, Integer> playersByUniqueId = new HashMap<>();
   public int spectatingPlayerId = -1;

   public EntityTracker1_7_6_10(UserConnection user, MetadataRewriter metadataRewriter) {
      super(user, ProtocolVersion.v1_8);
      this.metadataRewriter = metadataRewriter;
      this.registerEntity(EntityTypes1_10.EntityType.GUARDIAN, EntityTypes1_10.EntityType.SQUID, "Guardian");
      this.registerEntity(EntityTypes1_10.EntityType.ENDERMITE, EntityTypes1_10.EntityType.SQUID, "Endermite");
      this.registerEntity(EntityTypes1_10.EntityType.RABBIT, EntityTypes1_10.EntityType.CHICKEN, "Rabbit");
   }

   public void trackHologram(int entityId, VirtualHologramEntity hologram) {
      this.addEntity(entityId, EntityTypes1_10.EntityType.ARMOR_STAND);
      this.getEntityReplacementMap().put(entityId, EntityTypes1_10.EntityType.ARMOR_STAND);
      this.virtualHologramMap.put(entityId, hologram);
   }

   @Override
   public void updateMetadata(int entityId, List<Metadata> metadata) throws Exception {
      if (this.virtualHologramMap.containsKey(entityId)) {
         this.virtualHologramMap.get(entityId).updateMetadata(metadata);
      } else {
         super.updateMetadata(entityId, metadata);
      }
   }

   @Override
   public void removeEntity(int entityId) {
      super.removeEntity(entityId);
      if (this.playersByEntityId.containsKey(entityId)) {
         UUID playerId = this.playersByEntityId.remove(entityId);
         this.playersByUniqueId.remove(playerId);
         this.getUser().get(PlayerSessionStorage.class).getPlayerEquipment().remove(playerId);
      }
   }

   @Override
   public void clear() {
      super.clear();
      this.vehicleMap.clear();
   }

   @Override
   public void setClientEntityId(int entityId) {
      if (this.spectatingPlayerId == this.getPlayerId()) {
         this.spectatingPlayerId = entityId;
      }

      super.setClientEntityId(entityId);
   }

   public void addPlayer(Integer entityId, UUID uuid) {
      this.playersByUniqueId.put(uuid, entityId);
      this.playersByEntityId.put(entityId, uuid);
   }

   public UUID getPlayerUUID(int entityId) {
      return this.playersByEntityId.get(entityId);
   }

   public int getPlayerEntityId(UUID uuid) {
      return this.playersByUniqueId.getOrDefault(uuid, -1);
   }

   public int getVehicle(int passengerId) {
      for (Entry<Integer, Integer> vehicle : this.vehicleMap.entrySet()) {
         if (vehicle.getValue() == passengerId) {
            return vehicle.getValue();
         }
      }

      return -1;
   }

   public int getPassenger(int vehicleId) {
      return this.vehicleMap.getOrDefault(vehicleId, -1);
   }

   protected void startSneaking() {
      try {
         PacketWrapper entityAction = PacketWrapper.create(ServerboundPackets1_7_2_5.ENTITY_ACTION, this.getUser());
         entityAction.write(Type.VAR_INT, this.getPlayerId());
         entityAction.write(Type.VAR_INT, 0);
         entityAction.write(Type.VAR_INT, 0);
         entityAction.sendToServer(Protocol1_7_6_10To1_8.class, true);
      } catch (Exception var2) {
         ViaRewind.getPlatform().getLogger().log(Level.SEVERE, "Failed to send sneak packet", (Throwable)var2);
      }
   }

   public void setPassenger(int vehicleId, int passengerId) {
      if (vehicleId == this.spectatingPlayerId && this.spectatingPlayerId != this.getPlayerId()) {
         this.startSneaking();
         this.setSpectating(this.getPlayerId());
      }

      if (vehicleId == -1) {
         this.vehicleMap.remove(this.getVehicle(passengerId));
      } else if (passengerId == -1) {
         this.vehicleMap.remove(vehicleId);
      } else {
         this.vehicleMap.put(vehicleId, passengerId);
      }
   }

   protected void attachEntity(int target) {
      try {
         PacketWrapper attachEntity = PacketWrapper.create(ClientboundPackets1_8.ATTACH_ENTITY, this.getUser());
         attachEntity.write(Type.INT, this.getPlayerId());
         attachEntity.write(Type.INT, target);
         attachEntity.write(Type.BOOLEAN, false);
         attachEntity.scheduleSend(Protocol1_7_6_10To1_8.class, true);
      } catch (Exception var3) {
         ViaRewind.getPlatform().getLogger().log(Level.SEVERE, "Failed to send attach packet", (Throwable)var3);
      }
   }

   public void setSpectating(int spectating) {
      if (spectating != this.getPlayerId() && this.getPassenger(spectating) != -1) {
         this.startSneaking();
         this.setSpectating(this.getPlayerId());
      } else {
         if (this.spectatingPlayerId != spectating && this.spectatingPlayerId != this.getPlayerId()) {
            this.attachEntity(-1);
         }

         this.spectatingPlayerId = spectating;
         if (spectating != this.getPlayerId()) {
            this.attachEntity(this.spectatingPlayerId);
         }
      }
   }

   public Map<Integer, VirtualHologramEntity> getVirtualHologramMap() {
      return this.virtualHologramMap;
   }
}
