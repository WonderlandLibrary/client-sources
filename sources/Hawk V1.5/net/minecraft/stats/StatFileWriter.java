package net.minecraft.stats;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.TupleIntJsonSerializable;

public class StatFileWriter {
   protected final Map field_150875_a = Maps.newConcurrentMap();
   private static final String __OBFID = "CL_00001481";

   public boolean hasAchievementUnlocked(Achievement var1) {
      return this.writeStat(var1) > 0;
   }

   public int writeStat(StatBase var1) {
      TupleIntJsonSerializable var2 = (TupleIntJsonSerializable)this.field_150875_a.get(var1);
      return var2 == null ? 0 : var2.getIntegerValue();
   }

   public int func_150874_c(Achievement var1) {
      if (this.hasAchievementUnlocked(var1)) {
         return 0;
      } else {
         int var2 = 0;

         for(Achievement var3 = var1.parentAchievement; var3 != null && !this.hasAchievementUnlocked(var3); ++var2) {
            var3 = var3.parentAchievement;
         }

         return var2;
      }
   }

   public void func_150871_b(EntityPlayer var1, StatBase var2, int var3) {
      if (!var2.isAchievement() || this.canUnlockAchievement((Achievement)var2)) {
         this.func_150873_a(var1, var2, this.writeStat(var2) + var3);
      }

   }

   public IJsonSerializable func_150870_b(StatBase var1) {
      TupleIntJsonSerializable var2 = (TupleIntJsonSerializable)this.field_150875_a.get(var1);
      return var2 != null ? var2.getJsonSerializableValue() : null;
   }

   public IJsonSerializable func_150872_a(StatBase var1, IJsonSerializable var2) {
      TupleIntJsonSerializable var3 = (TupleIntJsonSerializable)this.field_150875_a.get(var1);
      if (var3 == null) {
         var3 = new TupleIntJsonSerializable();
         this.field_150875_a.put(var1, var3);
      }

      var3.setJsonSerializableValue(var2);
      return var2;
   }

   public void func_150873_a(EntityPlayer var1, StatBase var2, int var3) {
      TupleIntJsonSerializable var4 = (TupleIntJsonSerializable)this.field_150875_a.get(var2);
      if (var4 == null) {
         var4 = new TupleIntJsonSerializable();
         this.field_150875_a.put(var2, var4);
      }

      var4.setIntegerValue(var3);
   }

   public boolean canUnlockAchievement(Achievement var1) {
      return var1.parentAchievement == null || this.hasAchievementUnlocked(var1.parentAchievement);
   }
}
