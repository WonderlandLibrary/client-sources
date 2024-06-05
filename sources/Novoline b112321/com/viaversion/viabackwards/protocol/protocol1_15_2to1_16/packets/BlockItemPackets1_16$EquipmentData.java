package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import net.aMz;
import net.anW;

final class BlockItemPackets1_16$EquipmentData {
   private final int slot;
   private final aMz a;

   private BlockItemPackets1_16$EquipmentData(int var1, aMz var2) {
      this.slot = var1;
      this.a = var2;
   }

   BlockItemPackets1_16$EquipmentData(int var1, aMz var2, anW var3) {
      this(var1, var2);
   }

   static int access$100(BlockItemPackets1_16$EquipmentData var0) {
      return var0.slot;
   }

   static aMz b(BlockItemPackets1_16$EquipmentData var0) {
      return var0.a;
   }
}
