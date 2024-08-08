package com.example.editme.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;

public class FakePlayer extends EntityOtherPlayerMP {
   public FakePlayer() {
      super(Minecraft.func_71410_x().field_71441_e, Minecraft.func_71410_x().field_71439_g.func_146103_bH());
   }

   public void clonePlayer(EntityPlayer var1, boolean var2) {
      if (var2) {
         this.field_71071_by.func_70455_b(var1.field_71071_by);
         this.func_70606_j(var1.func_110143_aJ());
         this.field_71100_bB = var1.func_71024_bL();
         this.field_71068_ca = var1.field_71068_ca;
         this.field_71067_cb = var1.field_71067_cb;
         this.field_71106_cc = var1.field_71106_cc;
         this.func_85040_s(var1.func_71037_bA());
         this.field_181018_ap = var1.func_181012_aH();
      } else if (this.field_70170_p.func_82736_K().func_82766_b("keepInventory") || var1.func_175149_v()) {
         this.field_71071_by.func_70455_b(var1.field_71071_by);
         this.field_71068_ca = var1.field_71068_ca;
         this.field_71067_cb = var1.field_71067_cb;
         this.field_71106_cc = var1.field_71106_cc;
         this.func_85040_s(var1.func_71037_bA());
      }

      this.field_175152_f = var1.func_175138_ci();
      this.func_184212_Q().func_187227_b(field_184827_bp, (Byte)var1.func_184212_Q().func_187225_a(field_184827_bp));
   }
}
