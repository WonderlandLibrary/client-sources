package net.minecraft.entity;

import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

public class EntityList$EntityEggInfo {
   public final int spawnedID;
   public final int primaryColor;
   public final int d;
   public final StatBase field_151512_d;
   public final StatBase field_151513_e;

   public EntityList$EntityEggInfo(int var1, int var2, int var3) {
      this.spawnedID = var1;
      this.primaryColor = var2;
      this.d = var3;
      this.field_151512_d = StatList.a(this);
      this.field_151513_e = StatList.b(this);
   }
}
