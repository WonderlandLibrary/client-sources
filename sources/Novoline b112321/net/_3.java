package net;

import com.google.common.collect.Lists;
import net._F;
import net.mU;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;

class _3 extends _F {
   final GuiStats D;

   public _3(GuiStats var1, Minecraft var2) {
      super(var1, var2);
      this.D = var1;
      this.z = Lists.newArrayList();

      for(StatCrafting var4 : StatList.objectMineStats) {
         boolean var5 = false;
         int var6 = Item.b(var4.func_150959_a());
         if(GuiStats.access$100(var1).readStat(var4) > 0) {
            var5 = true;
         } else if(StatList.objectUseStats[var6] != null && GuiStats.access$100(var1).readStat(StatList.objectUseStats[var6]) > 0) {
            var5 = true;
         } else if(StatList.objectCraftStats[var6] != null && GuiStats.access$100(var1).readStat(StatList.objectCraftStats[var6]) > 0) {
            var5 = true;
         }

         this.z.add(var4);
      }

      this.B = new mU(this, var1);
   }

   protected void drawListHeader(int var1, int var2, Tessellator var3) {
      super.drawListHeader(var1, var2, var3);
      if(this.A == 0) {
         GuiStats.access$000(this.D, var1 + 115 - 18 + 1, var2 + 1 + 1, 18, 18);
      } else {
         GuiStats.access$000(this.D, var1 + 115 - 18, var2 + 1, 18, 18);
      }

      if(this.A == 1) {
         GuiStats.access$000(this.D, var1 + 165 - 18 + 1, var2 + 1 + 1, 36, 18);
      } else {
         GuiStats.access$000(this.D, var1 + 165 - 18, var2 + 1, 36, 18);
      }

      if(this.A == 2) {
         GuiStats.access$000(this.D, var1 + 215 - 18 + 1, var2 + 1 + 1, 54, 18);
      } else {
         GuiStats.access$000(this.D, var1 + 215 - 18, var2 + 1, 54, 18);
      }

   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      StatCrafting var7 = this.c(var1);
      Item var8 = var7.func_150959_a();
      GuiStats.access$1200(this.D, var2 + 40, var3, var8);
      int var9 = Item.b(var8);
      this.a(StatList.objectCraftStats[var9], var2 + 115, var3, var1 % 2 == 0);
      this.a(StatList.objectUseStats[var9], var2 + 165, var3, var1 % 2 == 0);
      this.a(var7, var2 + 215, var3, var1 % 2 == 0);
   }

   protected String b(int var1) {
      return "stat.crafted";
   }
}
