package com.example.editme.modules.combat;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.setting.SettingsManager;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.util.math.BlockPos;

@Module.Info(
   name = "AutoHoleCenter",
   category = Module.Category.COMBAT
)
public class AutoHoleCenter extends Module {
   private int cooldown = 0;
   private Setting cooldownValue = this.register(SettingsManager.integerBuilder("Cooldown").withMinimum(5).withMaximum(100).withValue((int)50).build());

   private double getBlockStateBelowPlayer() {
      for(double var1 = mc.field_71439_g.field_70163_u; var1 > 0.0D; var1 -= 0.001D) {
         if (!(mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, var1, mc.field_71439_g.field_70161_v)).func_177230_c() instanceof BlockSlab) && mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, var1, mc.field_71439_g.field_70161_v)).func_177230_c().func_176223_P().func_185890_d(mc.field_71441_e, new BlockPos(0, 0, 0)) != null) {
            return var1;
         }
      }

      return -1.0D;
   }

   public void onUpdate() {
      if (mc.field_71441_e != null && mc.field_71439_g != null) {
         if (!this.isInHole()) {
            --this.cooldown;
         }

         if (!ModuleManager.isModuleEnabled("Surround")) {
            if ((mc.field_71439_g.field_70122_E || !mc.field_71474_y.field_74314_A.func_151470_d()) && (double)mc.field_71439_g.field_70143_R < 0.5D && this.isInHole() && mc.field_71439_g.field_70163_u - this.getBlockStateBelowPlayer() <= 1.125D && mc.field_71439_g.field_70163_u - this.getBlockStateBelowPlayer() <= 0.95D && !mc.field_71439_g.field_70122_E && !mc.field_71439_g.func_70055_a(Material.field_151586_h) && !mc.field_71439_g.func_70055_a(Material.field_151587_i) && !mc.field_71474_y.field_74314_A.func_151470_d() && !mc.field_71439_g.func_70617_f_()) {
               if (this.cooldown > 0) {
                  return;
               }

               BlockPos var1 = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
               double[] var2 = new double[]{0.0D, 42.0D, 0.75D};
               int var3 = var2.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  double var5 = var2[var4];
                  mc.field_71439_g.field_71174_a.func_147297_a(new Position((double)((float)var1.func_177958_n() + 0.5F), mc.field_71439_g.field_70163_u - var5, (double)((float)var1.func_177952_p() + 0.5F), true));
               }

               mc.field_71439_g.func_70107_b((double)((float)var1.func_177958_n() + 0.5F), this.getBlockStateBelowPlayer() + 0.1D, (double)((float)var1.func_177952_p() + 0.5F));
               this.cooldown = (Integer)this.cooldownValue.getValue();
            }

         }
      }
   }

   private boolean isInHole() {
      BlockPos var1 = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
      IBlockState var2 = mc.field_71441_e.func_180495_p(var1);
      if (var2.func_177230_c() != Blocks.field_150350_a) {
         return false;
      } else if (mc.field_71439_g.func_174818_b(var1) < 1.0D) {
         return false;
      } else if (mc.field_71441_e.func_180495_p(var1.func_177984_a()).func_177230_c() != Blocks.field_150350_a) {
         return false;
      } else if (mc.field_71441_e.func_180495_p(var1.func_177981_b(2)).func_177230_c() != Blocks.field_150350_a) {
         return false;
      } else {
         BlockPos[] var3 = new BlockPos[]{var1.func_177978_c(), var1.func_177968_d(), var1.func_177974_f(), var1.func_177976_e(), var1.func_177977_b()};
         BlockPos[] var4 = var3;
         int var5 = var3.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            BlockPos var7 = var4[var6];
            IBlockState var8 = mc.field_71441_e.func_180495_p(var7);
            if (var8.func_177230_c() == Blocks.field_150350_a || !var8.func_185913_b()) {
               return false;
            }
         }

         return true;
      }
   }
}
