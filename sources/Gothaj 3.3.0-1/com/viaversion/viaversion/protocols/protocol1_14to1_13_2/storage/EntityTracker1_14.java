package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;

public class EntityTracker1_14 extends EntityTrackerBase {
   private final Int2ObjectMap<Byte> insentientData = Int2ObjectSyncMap.hashmap();
   private final Int2ObjectMap<Byte> sleepingAndRiptideData = Int2ObjectSyncMap.hashmap();
   private final Int2ObjectMap<Byte> playerEntityFlags = Int2ObjectSyncMap.hashmap();
   private int latestTradeWindowId;
   private boolean forceSendCenterChunk = true;
   private int chunkCenterX;
   private int chunkCenterZ;

   public EntityTracker1_14(UserConnection user) {
      super(user, EntityTypes1_14.PLAYER);
   }

   @Override
   public void removeEntity(int entityId) {
      super.removeEntity(entityId);
      this.insentientData.remove(entityId);
      this.sleepingAndRiptideData.remove(entityId);
      this.playerEntityFlags.remove(entityId);
   }

   public byte getInsentientData(int entity) {
      Byte val = this.insentientData.get(entity);
      return val == null ? 0 : val;
   }

   public void setInsentientData(int entity, byte value) {
      this.insentientData.put(entity, Byte.valueOf(value));
   }

   private static byte zeroIfNull(Byte val) {
      return val == null ? 0 : val;
   }

   public boolean isSleeping(int player) {
      return (zeroIfNull(this.sleepingAndRiptideData.get(player)) & 1) != 0;
   }

   public void setSleeping(int player, boolean value) {
      byte newValue = (byte)(zeroIfNull(this.sleepingAndRiptideData.get(player)) & -2 | (value ? 1 : 0));
      if (newValue == 0) {
         this.sleepingAndRiptideData.remove(player);
      } else {
         this.sleepingAndRiptideData.put(player, Byte.valueOf(newValue));
      }
   }

   public boolean isRiptide(int player) {
      return (zeroIfNull(this.sleepingAndRiptideData.get(player)) & 2) != 0;
   }

   public void setRiptide(int player, boolean value) {
      byte newValue = (byte)(zeroIfNull(this.sleepingAndRiptideData.get(player)) & -3 | (value ? 2 : 0));
      if (newValue == 0) {
         this.sleepingAndRiptideData.remove(player);
      } else {
         this.sleepingAndRiptideData.put(player, Byte.valueOf(newValue));
      }
   }

   public byte getEntityFlags(int player) {
      return zeroIfNull(this.playerEntityFlags.get(player));
   }

   public void setEntityFlags(int player, byte data) {
      this.playerEntityFlags.put(player, Byte.valueOf(data));
   }

   public int getLatestTradeWindowId() {
      return this.latestTradeWindowId;
   }

   public void setLatestTradeWindowId(int latestTradeWindowId) {
      this.latestTradeWindowId = latestTradeWindowId;
   }

   public boolean isForceSendCenterChunk() {
      return this.forceSendCenterChunk;
   }

   public void setForceSendCenterChunk(boolean forceSendCenterChunk) {
      this.forceSendCenterChunk = forceSendCenterChunk;
   }

   public int getChunkCenterX() {
      return this.chunkCenterX;
   }

   public void setChunkCenterX(int chunkCenterX) {
      this.chunkCenterX = chunkCenterX;
   }

   public int getChunkCenterZ() {
      return this.chunkCenterZ;
   }

   public void setChunkCenterZ(int chunkCenterZ) {
      this.chunkCenterZ = chunkCenterZ;
   }
}
