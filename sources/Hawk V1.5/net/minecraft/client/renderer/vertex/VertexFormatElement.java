package net.minecraft.client.renderer.vertex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormatElement {
   private int field_177378_e;
   private static final Logger field_177381_a = LogManager.getLogger();
   private int field_177376_f;
   private final VertexFormatElement.EnumUseage field_177380_c;
   private int field_177377_d;
   private final VertexFormatElement.EnumType field_177379_b;
   private static final String __OBFID = "CL_00002399";

   public final boolean func_177374_g() {
      return this.field_177380_c == VertexFormatElement.EnumUseage.POSITION;
   }

   public final int func_177369_e() {
      return this.field_177377_d;
   }

   public void func_177371_a(int var1) {
      this.field_177376_f = var1;
   }

   public final int func_177368_f() {
      return this.field_177379_b.func_177395_a() * this.field_177378_e;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         VertexFormatElement var2 = (VertexFormatElement)var1;
         return this.field_177378_e != var2.field_177378_e ? false : (this.field_177377_d != var2.field_177377_d ? false : (this.field_177376_f != var2.field_177376_f ? false : (this.field_177379_b != var2.field_177379_b ? false : this.field_177380_c == var2.field_177380_c)));
      } else {
         return false;
      }
   }

   public final VertexFormatElement.EnumType func_177367_b() {
      return this.field_177379_b;
   }

   public int hashCode() {
      int var1 = this.field_177379_b.hashCode();
      var1 = 31 * var1 + this.field_177380_c.hashCode();
      var1 = 31 * var1 + this.field_177377_d;
      var1 = 31 * var1 + this.field_177378_e;
      var1 = 31 * var1 + this.field_177376_f;
      return var1;
   }

   public final int func_177370_d() {
      return this.field_177378_e;
   }

   private final boolean func_177372_a(int var1, VertexFormatElement.EnumUseage var2) {
      return var1 == 0 || var2 == VertexFormatElement.EnumUseage.UV;
   }

   public String toString() {
      return String.valueOf((new StringBuilder(String.valueOf(this.field_177378_e))).append(",").append(this.field_177380_c.func_177384_a()).append(",").append(this.field_177379_b.func_177396_b()));
   }

   public int func_177373_a() {
      return this.field_177376_f;
   }

   public VertexFormatElement(int var1, VertexFormatElement.EnumType var2, VertexFormatElement.EnumUseage var3, int var4) {
      if (!this.func_177372_a(var1, var3)) {
         field_177381_a.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
         this.field_177380_c = VertexFormatElement.EnumUseage.UV;
      } else {
         this.field_177380_c = var3;
      }

      this.field_177379_b = var2;
      this.field_177377_d = var1;
      this.field_177378_e = var4;
      this.field_177376_f = 0;
   }

   public final VertexFormatElement.EnumUseage func_177375_c() {
      return this.field_177380_c;
   }

   public static enum EnumUseage {
      COLOR("COLOR", 2, "Vertex Color");

      private static final VertexFormatElement.EnumUseage[] $VALUES = new VertexFormatElement.EnumUseage[]{POSITION, NORMAL, COLOR, UV, MATRIX, BLEND_WEIGHT, PADDING};
      private static final VertexFormatElement.EnumUseage[] ENUM$VALUES = new VertexFormatElement.EnumUseage[]{POSITION, NORMAL, COLOR, UV, MATRIX, BLEND_WEIGHT, PADDING};
      private static final String __OBFID = "CL_00002397";
      POSITION("POSITION", 0, "Position"),
      NORMAL("NORMAL", 1, "Normal"),
      MATRIX("MATRIX", 4, "Bone Matrix"),
      PADDING("PADDING", 6, "Padding"),
      BLEND_WEIGHT("BLEND_WEIGHT", 5, "Blend Weight");

      private final String field_177392_h;
      UV("UV", 3, "UV");

      private EnumUseage(String var3, int var4, String var5) {
         this.field_177392_h = var5;
      }

      public String func_177384_a() {
         return this.field_177392_h;
      }
   }

   public static enum EnumType {
      UINT("UINT", 5, 4, "Unsigned Int", 5125);

      private final int field_177407_h;
      INT("INT", 6, 4, "Int", 5124),
      USHORT("USHORT", 3, 2, "Unsigned Short", 5123);

      private static final VertexFormatElement.EnumType[] ENUM$VALUES = new VertexFormatElement.EnumType[]{FLOAT, UBYTE, BYTE, USHORT, SHORT, UINT, INT};
      private final int field_177405_j;
      SHORT("SHORT", 4, 2, "Short", 5122);

      private static final String __OBFID = "CL_00002398";
      private static final VertexFormatElement.EnumType[] $VALUES = new VertexFormatElement.EnumType[]{FLOAT, UBYTE, BYTE, USHORT, SHORT, UINT, INT};
      BYTE("BYTE", 2, 1, "Byte", 5120),
      FLOAT("FLOAT", 0, 4, "Float", 5126);

      private final String field_177408_i;
      UBYTE("UBYTE", 1, 1, "Unsigned Byte", 5121);

      public int func_177397_c() {
         return this.field_177405_j;
      }

      public String func_177396_b() {
         return this.field_177408_i;
      }

      public int func_177395_a() {
         return this.field_177407_h;
      }

      private EnumType(String var3, int var4, int var5, String var6, int var7) {
         this.field_177407_h = var5;
         this.field_177408_i = var6;
         this.field_177405_j = var7;
      }
   }
}
