package net.minecraft.client.renderer;

import net.minecraft.util.EnumFacing;

public enum EnumFaceing {
   NORTH("NORTH", 2, new EnumFaceing.VertexInformation[]{new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c, EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179177_d, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c, EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179177_d, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f, EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179177_d, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f, EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179177_d, (Object)null)}),
   EAST("EAST", 5, new EnumFaceing.VertexInformation[]{new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c, EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179181_a, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c, EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179181_a, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c, EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179177_d, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c, EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179177_d, (Object)null)}),
   DOWN("DOWN", 0, new EnumFaceing.VertexInformation[]{new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f, EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179181_a, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f, EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179177_d, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c, EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179177_d, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c, EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179181_a, (Object)null)});

   private static final String __OBFID = "CL_00002562";
   private static final EnumFaceing[] $VALUES = new EnumFaceing[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
   private static final EnumFaceing[] ENUM$VALUES = new EnumFaceing[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
   UP("UP", 1, new EnumFaceing.VertexInformation[]{new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f, EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179177_d, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f, EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179181_a, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c, EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179181_a, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c, EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179177_d, (Object)null)}),
   WEST("WEST", 4, new EnumFaceing.VertexInformation[]{new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f, EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179177_d, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f, EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179177_d, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f, EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179181_a, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f, EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179181_a, (Object)null)});

   private final EnumFaceing.VertexInformation[] field_179035_h;
   private static final EnumFaceing[] field_179029_g = new EnumFaceing[6];
   SOUTH("SOUTH", 3, new EnumFaceing.VertexInformation[]{new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f, EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179181_a, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f, EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179181_a, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c, EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179181_a, (Object)null), new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c, EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179181_a, (Object)null)});

   public EnumFaceing.VertexInformation func_179025_a(int var1) {
      return this.field_179035_h[var1];
   }

   static {
      field_179029_g[EnumFaceing.Constants.field_179178_e] = DOWN;
      field_179029_g[EnumFaceing.Constants.field_179179_b] = UP;
      field_179029_g[EnumFaceing.Constants.field_179177_d] = NORTH;
      field_179029_g[EnumFaceing.Constants.field_179181_a] = SOUTH;
      field_179029_g[EnumFaceing.Constants.field_179176_f] = WEST;
      field_179029_g[EnumFaceing.Constants.field_179180_c] = EAST;
   }

   public static EnumFaceing func_179027_a(EnumFacing var0) {
      return field_179029_g[var0.getIndex()];
   }

   private EnumFaceing(String var3, int var4, EnumFaceing.VertexInformation... var5) {
      this.field_179035_h = var5;
   }

   public static final class Constants {
      public static final int field_179177_d;
      private static final String __OBFID = "CL_00002560";
      public static final int field_179180_c;
      public static final int field_179176_f;
      public static final int field_179179_b;
      public static final int field_179181_a;
      public static final int field_179178_e;

      static {
         field_179181_a = EnumFacing.SOUTH.getIndex();
         field_179179_b = EnumFacing.UP.getIndex();
         field_179180_c = EnumFacing.EAST.getIndex();
         field_179177_d = EnumFacing.NORTH.getIndex();
         field_179178_e = EnumFacing.DOWN.getIndex();
         field_179176_f = EnumFacing.WEST.getIndex();
      }
   }

   public static class VertexInformation {
      public final int field_179183_c;
      public final int field_179184_a;
      private static final String __OBFID = "CL_00002559";
      public final int field_179182_b;

      private VertexInformation(int var1, int var2, int var3) {
         this.field_179184_a = var1;
         this.field_179182_b = var2;
         this.field_179183_c = var3;
      }

      VertexInformation(int var1, int var2, int var3, Object var4) {
         this(var1, var2, var3);
      }
   }
}
