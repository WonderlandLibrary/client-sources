package net.minecraft.client.renderer;

import net.minecraft.client.renderer.EnumFaceDirection$1;

public class EnumFaceDirection$VertexInformation {
   public final int field_179184_a;
   public final int field_179182_b;
   public final int field_179183_c;

   private EnumFaceDirection$VertexInformation(int var1, int var2, int var3) {
      this.field_179184_a = var1;
      this.field_179182_b = var2;
      this.field_179183_c = var3;
   }

   EnumFaceDirection$VertexInformation(int var1, int var2, int var3, EnumFaceDirection$1 var4) {
      this(var1, var2, var3);
   }
}
