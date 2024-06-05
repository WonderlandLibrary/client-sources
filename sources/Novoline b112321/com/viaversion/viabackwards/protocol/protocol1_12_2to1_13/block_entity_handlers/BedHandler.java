package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import net.bgR;
import net.p9;

public class BedHandler implements p9 {
   public CompoundTag a(bgR var1, int var2, CompoundTag var3) {
      int var4 = var2 - 748;
      int var5 = var4 >> 4;
      var3.put(new IntTag("color", var5));
      return var3;
   }
}
