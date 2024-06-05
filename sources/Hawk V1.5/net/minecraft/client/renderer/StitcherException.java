package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.Stitcher;

public class StitcherException extends RuntimeException {
   private static final String __OBFID = "CL_00001057";
   private final Stitcher.Holder field_98149_a;

   public StitcherException(Stitcher.Holder var1, String var2) {
      super(var2);
      this.field_98149_a = var1;
   }
}
