package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.TripwireConnectionHandler$1;

final class TripwireConnectionHandler$TripwireData {
   private final boolean attached;
   private final boolean disarmed;
   private final boolean powered;

   private TripwireConnectionHandler$TripwireData(boolean var1, boolean var2, boolean var3) {
      this.attached = var1;
      this.disarmed = var2;
      this.powered = var3;
   }

   public boolean isAttached() {
      return this.attached;
   }

   public boolean isDisarmed() {
      return this.disarmed;
   }

   public boolean isPowered() {
      return this.powered;
   }

   TripwireConnectionHandler$TripwireData(boolean var1, boolean var2, boolean var3, TripwireConnectionHandler$1 var4) {
      this(var1, var2, var3);
   }
}
