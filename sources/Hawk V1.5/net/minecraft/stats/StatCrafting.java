package net.minecraft.stats;

import net.minecraft.item.Item;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.util.IChatComponent;

public class StatCrafting extends StatBase {
   private final Item field_150960_a;
   private static final String __OBFID = "CL_00001470";

   public Item func_150959_a() {
      return this.field_150960_a;
   }

   public StatCrafting(String var1, String var2, IChatComponent var3, Item var4) {
      super(String.valueOf((new StringBuilder(String.valueOf(var1))).append(var2)), var3);
      this.field_150960_a = var4;
      int var5 = Item.getIdFromItem(var4);
      if (var5 != 0) {
         IScoreObjectiveCriteria.INSTANCES.put(String.valueOf((new StringBuilder(String.valueOf(var1))).append(var5)), this.func_150952_k());
      }

   }
}
