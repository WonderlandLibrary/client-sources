package net.minecraft.client.renderer.block.model;

import javax.vecmath.Vector3f;
import net.minecraft.util.EnumFacing;

public class BlockPartRotation {
   private static final String __OBFID = "CL_00002506";
   public final Vector3f field_178344_a;
   public final EnumFacing.Axis field_178342_b;
   public final float field_178343_c;
   public final boolean field_178341_d;

   public BlockPartRotation(Vector3f var1, EnumFacing.Axis var2, float var3, boolean var4) {
      this.field_178344_a = var1;
      this.field_178342_b = var2;
      this.field_178343_c = var3;
      this.field_178341_d = var4;
   }
}
