package com.example.editme.modules.combat;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.Friends;
import com.example.editme.util.setting.SettingsManager;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

@Module.Info(
   name = "AutoArmour",
   category = Module.Category.COMBAT
)
public class AutoArmour extends Module {
   private Setting disableWhenNoCrystals = this.register(SettingsManager.b("Disable when no Crystals", false));
   private Setting disableWhenAlone = this.register(SettingsManager.b("Disable when Alone", false));

   private static EntityEnderCrystal lambda$onUpdate$1(Entity var0) {
      return (EntityEnderCrystal)var0;
   }

   private static Float lambda$onUpdate$2(EntityEnderCrystal var0) {
      return mc.field_71439_g.func_70032_d(var0);
   }

   public void onUpdate() {
      if ((Boolean)this.disableWhenAlone.getValue()) {
         Iterator var1 = mc.field_71441_e.field_72996_f.iterator();

         while(var1.hasNext()) {
            Entity var2 = (Entity)var1.next();
            if (var2 instanceof EntityPlayer && !Friends.isFriend(var2.func_70005_c_()) && var2.func_70032_d(mc.field_71439_g) < 6.0F) {
               return;
            }
         }
      }

      EntityEnderCrystal var9 = (EntityEnderCrystal)mc.field_71441_e.field_72996_f.stream().filter(AutoArmour::lambda$onUpdate$0).map(AutoArmour::lambda$onUpdate$1).min(Comparator.comparing(AutoArmour::lambda$onUpdate$2)).orElse((Object)null);
      if (!(Boolean)this.disableWhenNoCrystals.getValue() || var9 != null) {
         if (mc.field_71439_g.field_70173_aa % 2 != 0) {
            if (!(mc.field_71462_r instanceof GuiContainer) || mc.field_71462_r instanceof InventoryEffectRenderer) {
               int[] var10 = new int[4];
               int[] var3 = new int[4];

               int var4;
               ItemStack var5;
               for(var4 = 0; var4 < 4; ++var4) {
                  var5 = mc.field_71439_g.field_71071_by.func_70440_f(var4);
                  if (var5 != null && var5.func_77973_b() instanceof ItemArmor) {
                     var3[var4] = ((ItemArmor)var5.func_77973_b()).field_77879_b;
                  }

                  var10[var4] = -1;
               }

               for(var4 = 0; var4 < 36; ++var4) {
                  var5 = mc.field_71439_g.field_71071_by.func_70301_a(var4);
                  if (var5.func_190916_E() <= 1 && var5 != null && var5.func_77973_b() instanceof ItemArmor) {
                     ItemArmor var6 = (ItemArmor)var5.func_77973_b();
                     int var7 = var6.field_77881_a.ordinal() - 2;
                     if (var7 != 2 || !mc.field_71439_g.field_71071_by.func_70440_f(var7).func_77973_b().equals(Items.field_185160_cR)) {
                        int var8 = var6.field_77879_b;
                        if (var8 > var3[var7]) {
                           var10[var7] = var4;
                           var3[var7] = var8;
                        }
                     }
                  }
               }

               for(var4 = 0; var4 < 4; ++var4) {
                  int var11 = var10[var4];
                  if (var11 != -1) {
                     ItemStack var12 = mc.field_71439_g.field_71071_by.func_70440_f(var4);
                     if (var12 == null || var12 != ItemStack.field_190927_a || mc.field_71439_g.field_71071_by.func_70447_i() != -1) {
                        if (var11 < 9) {
                           var11 += 36;
                        }

                        mc.field_71442_b.func_187098_a(0, 8 - var4, 0, ClickType.QUICK_MOVE, mc.field_71439_g);
                        mc.field_71442_b.func_187098_a(0, var11, 0, ClickType.QUICK_MOVE, mc.field_71439_g);
                        break;
                     }
                  }
               }

            }
         }
      }
   }

   private static boolean lambda$onUpdate$0(Entity var0) {
      return var0 instanceof EntityEnderCrystal && mc.field_71439_g.func_70032_d(var0) <= 10.0F;
   }
}
