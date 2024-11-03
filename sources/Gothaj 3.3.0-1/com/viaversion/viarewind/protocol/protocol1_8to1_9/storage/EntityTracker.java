package com.viaversion.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viarewind.api.minecraft.EntityModel;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class EntityTracker extends StoredObject implements ClientEntityIdChangeListener {
   protected final Protocol1_8To1_9 protocol;
   private final Map<Integer, List<Integer>> vehicleMap = new ConcurrentHashMap<>();
   private final Map<Integer, EntityTypes1_10.EntityType> clientEntityTypes = new ConcurrentHashMap<>();
   private final Map<Integer, List<Metadata>> metadataBuffer = new ConcurrentHashMap<>();
   private final Map<Integer, EntityModel> entityReplacements = new ConcurrentHashMap<>();
   private final Map<Integer, Vector> entityOffsets = new ConcurrentHashMap<>();
   private final Map<Integer, Byte> statusInformation = new ConcurrentHashMap<>();
   private int playerId;
   private int playerGamemode = 0;

   public EntityTracker(UserConnection user, Protocol1_8To1_9 protocol) {
      super(user);
      this.protocol = protocol;
   }

   public void setPlayerId(int entityId) {
      this.playerId = entityId;
   }

   public int getPlayerId() {
      return this.playerId;
   }

   public int getPlayerGamemode() {
      return this.playerGamemode;
   }

   public void setPlayerGamemode(int playerGamemode) {
      this.playerGamemode = playerGamemode;
   }

   public void removeEntity(int entityId) {
      this.vehicleMap.remove(entityId);
      this.vehicleMap.forEach((vehicle, passengers) -> passengers.remove(Integer.valueOf(entityId)));
      this.vehicleMap.entrySet().removeIf(entry -> entry.getValue().isEmpty());
      this.clientEntityTypes.remove(entityId);
      this.entityOffsets.remove(entityId);
      if (this.entityReplacements.containsKey(entityId)) {
         this.entityReplacements.remove(entityId).deleteEntity();
      }
   }

   public void resetEntityOffset(int entityId) {
      this.entityOffsets.remove(entityId);
   }

   public Vector getEntityOffset(int entityId) {
      return this.entityOffsets.get(entityId);
   }

   public void addToEntityOffset(int entityId, short relX, short relY, short relZ) {
      this.entityOffsets
         .compute(
            entityId,
            (key, offset) -> offset == null ? new Vector(relX, relY, relZ) : new Vector(offset.blockX() + relX, offset.blockY() + relY, offset.blockZ() + relZ)
         );
   }

   public void setEntityOffset(int entityId, short relX, short relY, short relZ) {
      this.entityOffsets.compute(entityId, (key, offset) -> new Vector(relX, relY, relZ));
   }

   public void setEntityOffset(int entityId, Vector offset) {
      this.entityOffsets.put(entityId, offset);
   }

   public List<Integer> getPassengers(int entityId) {
      return this.vehicleMap.getOrDefault(entityId, new ArrayList<>());
   }

   public void setPassengers(int entityId, List<Integer> passengers) {
      this.vehicleMap.put(entityId, passengers);
   }

   public void addEntityReplacement(EntityModel entityModel) {
      this.entityReplacements.put(entityModel.getEntityId(), entityModel);
   }

   public EntityModel getEntityReplacement(int entityId) {
      return this.entityReplacements.get(entityId);
   }

   public Map<Integer, EntityTypes1_10.EntityType> getClientEntityTypes() {
      return this.clientEntityTypes;
   }

   public void addMetadataToBuffer(int entityID, List<Metadata> metadataList) {
      if (this.metadataBuffer.containsKey(entityID)) {
         this.metadataBuffer.get(entityID).addAll(metadataList);
      } else if (!metadataList.isEmpty()) {
         this.metadataBuffer.put(entityID, metadataList);
      }
   }

   public List<Metadata> getBufferedMetadata(int entityId) {
      return this.metadataBuffer.get(entityId);
   }

   public boolean isInsideVehicle(int entityId) {
      for (List<Integer> vehicle : this.vehicleMap.values()) {
         if (vehicle.contains(entityId)) {
            return true;
         }
      }

      return false;
   }

   public int getVehicle(int passenger) {
      for (Entry<Integer, List<Integer>> vehicle : this.vehicleMap.entrySet()) {
         if (vehicle.getValue().contains(passenger)) {
            return vehicle.getKey();
         }
      }

      return -1;
   }

   public boolean isPassenger(int vehicle, int passenger) {
      return this.vehicleMap.containsKey(vehicle) && this.vehicleMap.get(vehicle).contains(passenger);
   }

   public void sendMetadataBuffer(int entityId) {
      if (this.metadataBuffer.containsKey(entityId)) {
         if (this.entityReplacements.containsKey(entityId)) {
            this.entityReplacements.get(entityId).updateMetadata(this.metadataBuffer.remove(entityId));
         } else {
            PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_8.ENTITY_METADATA, this.getUser());
            wrapper.write(Type.VAR_INT, entityId);
            wrapper.write(Types1_8.METADATA_LIST, this.metadataBuffer.get(entityId));
            this.protocol.getMetadataRewriter().transform(this, entityId, this.metadataBuffer.get(entityId));
            if (!this.metadataBuffer.get(entityId).isEmpty()) {
               try {
                  wrapper.send(Protocol1_8To1_9.class);
               } catch (Exception var4) {
                  var4.printStackTrace();
               }
            }

            this.metadataBuffer.remove(entityId);
         }
      }
   }

   @Override
   public void setClientEntityId(int playerEntityId) {
      this.clientEntityTypes.remove(this.playerId);
      this.playerId = playerEntityId;
      this.clientEntityTypes.put(this.playerId, EntityTypes1_10.EntityType.ENTITY_HUMAN);
   }

   public Map<Integer, Byte> getStatusInformation() {
      return this.statusInformation;
   }
}
