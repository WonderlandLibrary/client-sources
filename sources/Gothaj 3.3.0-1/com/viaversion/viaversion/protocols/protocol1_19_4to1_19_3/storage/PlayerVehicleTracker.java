package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class PlayerVehicleTracker implements StorableObject {
   private int vehicleId = -1;

   public int getVehicleId() {
      return this.vehicleId;
   }

   public void setVehicleId(int vehicleId) {
      this.vehicleId = vehicleId;
   }
}
