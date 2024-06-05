package net.minecraft.client.renderer.vertex;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public class VertexBuffer {
   private int field_177364_c;
   private int field_177365_a;
   private final VertexFormat field_177363_b;
   private static final String __OBFID = "CL_00002402";

   public VertexBuffer(VertexFormat var1) {
      this.field_177363_b = var1;
      this.field_177365_a = OpenGlHelper.func_176073_e();
   }

   public void func_177360_a(ByteBuffer var1, int var2) {
      this.func_177359_a();
      OpenGlHelper.func_176071_a(OpenGlHelper.field_176089_P, var1, 35044);
      this.func_177361_b();
      this.field_177364_c = var2 / this.field_177363_b.func_177338_f();
   }

   public void func_177359_a() {
      OpenGlHelper.func_176072_g(OpenGlHelper.field_176089_P, this.field_177365_a);
   }

   public void func_177361_b() {
      OpenGlHelper.func_176072_g(OpenGlHelper.field_176089_P, 0);
   }

   public void func_177362_c() {
      if (this.field_177365_a >= 0) {
         OpenGlHelper.func_176074_g(this.field_177365_a);
         this.field_177365_a = -1;
      }

   }

   public void func_177358_a(int var1) {
      GL11.glDrawArrays(var1, 0, this.field_177364_c);
   }
}
