package net.minecraft.client.renderer;

import net.minecraft.client.renderer.GlStateManager$TexGen;

final class GlStateManager$GlStateManager$1 {
   static final int[] field_179175_a = new int[GlStateManager$TexGen.values().length];

   static {
      try {
         field_179175_a[GlStateManager$TexGen.S.ordinal()] = 1;
         field_179175_a[GlStateManager$TexGen.T.ordinal()] = 2;
         field_179175_a[GlStateManager$TexGen.R.ordinal()] = 3;
         field_179175_a[GlStateManager$TexGen.Q.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
