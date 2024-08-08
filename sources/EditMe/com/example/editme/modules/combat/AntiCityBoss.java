package com.example.editme.modules.combat;

import com.example.editme.modules.Module;
import com.example.editme.util.client.BlockInteractionHelper;
import com.example.editme.util.client.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@Module.Info(
   name = "AntiCityBoss",
   description = "Places blocks in front of surround to prevent citybossing",
   category = Module.Category.COMBAT
)
public class AntiCityBoss extends Module {
   public void onUpdate() {
      int var1 = this.findObiInHotbar();
      if (var1 != -1) {
         BlockPos var2 = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
         int var4 = MathHelper.func_76128_c((double)(mc.field_71439_g.field_70177_z * 8.0F / 360.0F) + 0.5D) & 7;
         BlockPos var3;
         if (var4 == 1) {
            var3 = var2.func_177968_d().func_177968_d();
         } else if (var4 == 2) {
            var3 = var2.func_177976_e().func_177976_e();
         } else if (var4 == 3) {
            var3 = var2.func_177978_c().func_177978_c();
         } else {
            if (var4 != 4) {
               return;
            }

            var3 = var2.func_177974_f().func_177974_f();
         }

         if (mc.field_71441_e.func_175623_d(var3)) {
            int var5 = mc.field_71439_g.field_71071_by.field_70461_c;
            mc.field_71439_g.field_71071_by.field_70461_c = var1;
            mc.field_71442_b.func_78765_e();
            float[] var6 = BlockInteractionHelper.getLegitRotations(new Vec3d((double)var3.func_177958_n(), (double)var3.func_177956_o(), (double)var3.func_177952_p()));
            PlayerUtil.PacketFacePitchAndYaw(var6[1], var6[0]);
            BlockInteractionHelper.place(var3, 5.0F, false, false);
            if (!(mc.field_71439_g.field_71071_by.func_70301_a(var1).func_77973_b() instanceof ItemBlock)) {
               mc.field_71439_g.field_71071_by.field_70461_c = var5;
            }

            mc.field_71442_b.func_78765_e();
         }

      }
   }

   private int findObiInHotbar() {
      int var1 = -1;

      for(int var2 = 0; var2 < 9; ++var2) {
         ItemStack var3 = mc.field_71439_g.field_71071_by.func_70301_a(var2);
         if (var3 != ItemStack.field_190927_a && var3.func_77973_b() instanceof ItemBlock) {
            Block var4 = ((ItemBlock)var3.func_77973_b()).func_179223_d();
            if (var4 instanceof BlockObsidian) {
               var1 = var2;
               break;
            }
         }
      }

      return var1;
   }
}
