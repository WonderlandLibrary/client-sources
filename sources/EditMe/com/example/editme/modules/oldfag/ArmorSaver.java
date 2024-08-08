package com.example.editme.modules.oldfag;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

@Module.Info(
   name = "ArmorSaver",
   category = Module.Category.OLDFAG
)
public class ArmorSaver extends Module {
   private Setting armorHealth = this.register(SettingsManager.integerBuilder("Armor %").withValue((int)10).withMinimum(1).withMaximum(100));

   public void onUpdate() {
      if (mc.field_71439_g.field_70173_aa % 2 != 0) {
         if (!(mc.field_71462_r instanceof GuiContainer) || mc.field_71462_r instanceof InventoryEffectRenderer) {
            int[] var1 = new int[4];
            int[] var2 = new int[4];

            int var3;
            ItemStack var4;
            for(var3 = 0; var3 < 4; ++var3) {
               var4 = mc.field_71439_g.field_71071_by.func_70440_f(var3);
               if (var4 != null && var4.func_77973_b() instanceof ItemArmor && 100 * var4.func_77952_i() / var4.func_77958_k() <= reverseNumber((Integer)this.armorHealth.getValue(), 1, 100)) {
                  var2[var3] = ((ItemArmor)var4.func_77973_b()).field_77879_b;
               }

               var1[var3] = -1;
            }

            for(var3 = 0; var3 < 36; ++var3) {
               var4 = mc.field_71439_g.field_71071_by.func_70301_a(var3);
               if (var4.func_190916_E() <= 1 && var4 != null && var4.func_77973_b() instanceof ItemArmor) {
                  ItemArmor var5 = (ItemArmor)var4.func_77973_b();
                  int var6 = var5.field_77881_a.ordinal() - 2;
                  if (var6 != 2 || !mc.field_71439_g.field_71071_by.func_70440_f(var6).func_77973_b().equals(Items.field_185160_cR)) {
                     int var7 = var5.field_77879_b;
                     if (var7 > var2[var6]) {
                        var1[var6] = var3;
                        var2[var6] = var7;
                     }
                  }
               }
            }

            for(var3 = 0; var3 < 4; ++var3) {
               int var8 = var1[var3];
               if (var8 != -1) {
                  ItemStack var9 = mc.field_71439_g.field_71071_by.func_70440_f(var3);
                  if (var9 == null || var9 != ItemStack.field_190927_a || mc.field_71439_g.field_71071_by.func_70447_i() != -1) {
                     if (var8 < 9) {
                        var8 += 36;
                     }

                     mc.field_71442_b.func_187098_a(0, 8 - var3, 0, ClickType.QUICK_MOVE, mc.field_71439_g);
                     mc.field_71442_b.func_187098_a(0, var8, 0, ClickType.QUICK_MOVE, mc.field_71439_g);
                     break;
                  }
               }
            }

         }
      }
   }

   public static int reverseNumber(int var0, int var1, int var2) {
      return var2 + var1 - var0;
   }
}
