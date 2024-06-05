package net.minecraft.stats;

import net.minecraft.stats.IStatType;
import net.minecraft.stats.StatBase;

final class StatBase$4 implements IStatType {
   public String format(int var1) {
      return StatBase.access$100().format((double)var1 * 0.1D);
   }
}
