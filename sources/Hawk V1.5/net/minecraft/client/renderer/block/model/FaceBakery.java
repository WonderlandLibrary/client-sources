package net.minecraft.client.renderer.block.model;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import net.minecraft.client.renderer.EnumFaceing;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;
import net.minecraftforge.client.model.ITransformation;
import optifine.Config;
import optifine.Reflector;
import shadersmod.client.Shaders;

public class FaceBakery {
   private static final String __OBFID = "CL_00002490";
   private static final double field_178417_b = 1.0D / Math.cos(0.7853981633974483D) - 1.0D;
   private static final double field_178418_a = 1.0D / Math.cos(0.39269908169872414D) - 1.0D;

   private void func_178406_a(Vector3d var1, Vector3d var2, Matrix4d var3, Vector3d var4) {
      var1.sub(var2);
      var3.transform(var1);
      var1.x *= var4.x;
      var1.y *= var4.y;
      var1.z *= var4.z;
      var1.add(var2);
   }

   private Matrix4d func_178416_a(AxisAngle4d var1) {
      Matrix4d var2 = this.func_178411_a();
      var2.setRotation(var1);
      return var2;
   }

   private float func_178412_b(EnumFacing var1) {
      switch(var1) {
      case DOWN:
         if (Config.isShaders()) {
            return Shaders.blockLightLevel05;
         }

         return 0.5F;
      case UP:
         return 1.0F;
      case NORTH:
      case SOUTH:
         if (Config.isShaders()) {
            return Shaders.blockLightLevel08;
         }

         return 0.8F;
      case WEST:
      case EAST:
         if (Config.isShaders()) {
            return Shaders.blockLightLevel06;
         }

         return 0.6F;
      default:
         return 1.0F;
      }
   }

   private void fillVertexData(int[] var1, int var2, EnumFacing var3, BlockPartFace var4, float[] var5, TextureAtlasSprite var6, ITransformation var7, BlockPartRotation var8, boolean var9, boolean var10) {
      EnumFacing var11 = var7.rotate(var3);
      int var12 = var10 ? this.func_178413_a(var11) : -1;
      EnumFaceing.VertexInformation var13 = EnumFaceing.func_179027_a(var3).func_179025_a(var2);
      Vector3d var14 = new Vector3d((double)var5[var13.field_179184_a], (double)var5[var13.field_179182_b], (double)var5[var13.field_179183_c]);
      this.func_178407_a(var14, var8);
      int var15 = this.rotateVertex(var14, var3, var2, var7, var9);
      this.func_178404_a(var1, var15, var2, var14, var12, var6, var4.field_178243_e);
   }

   private float[] func_178403_a(Vector3f var1, Vector3f var2) {
      float[] var3 = new float[EnumFacing.values().length];
      var3[EnumFaceing.Constants.field_179176_f] = var1.x / 16.0F;
      var3[EnumFaceing.Constants.field_179178_e] = var1.y / 16.0F;
      var3[EnumFaceing.Constants.field_179177_d] = var1.z / 16.0F;
      var3[EnumFaceing.Constants.field_179180_c] = var2.x / 16.0F;
      var3[EnumFaceing.Constants.field_179179_b] = var2.y / 16.0F;
      var3[EnumFaceing.Constants.field_179181_a] = var2.z / 16.0F;
      return var3;
   }

   private void func_178401_a(int var1, int[] var2, EnumFacing var3, BlockFaceUV var4, TextureAtlasSprite var5) {
      int var6 = var2.length / 4;
      int var7 = var6 * var1;
      float var8 = Float.intBitsToFloat(var2[var7]);
      float var9 = Float.intBitsToFloat(var2[var7 + 1]);
      float var10 = Float.intBitsToFloat(var2[var7 + 2]);
      if (var8 < -0.1F || var8 >= 1.1F) {
         var8 -= (float)MathHelper.floor_float(var8);
      }

      if (var9 < -0.1F || var9 >= 1.1F) {
         var9 -= (float)MathHelper.floor_float(var9);
      }

      if (var10 < -0.1F || var10 >= 1.1F) {
         var10 -= (float)MathHelper.floor_float(var10);
      }

      float var11 = 0.0F;
      float var12 = 0.0F;
      switch(var3) {
      case DOWN:
         var11 = var8 * 16.0F;
         var12 = (1.0F - var10) * 16.0F;
         break;
      case UP:
         var11 = var8 * 16.0F;
         var12 = var10 * 16.0F;
         break;
      case NORTH:
         var11 = (1.0F - var8) * 16.0F;
         var12 = (1.0F - var9) * 16.0F;
         break;
      case SOUTH:
         var11 = var8 * 16.0F;
         var12 = (1.0F - var9) * 16.0F;
         break;
      case WEST:
         var11 = var10 * 16.0F;
         var12 = (1.0F - var9) * 16.0F;
         break;
      case EAST:
         var11 = (1.0F - var10) * 16.0F;
         var12 = (1.0F - var9) * 16.0F;
      }

      int var13 = var4.func_178345_c(var1) * var6;
      var2[var13 + 4] = Float.floatToRawIntBits(var5.getInterpolatedU((double)var11));
      var2[var13 + 4 + 1] = Float.floatToRawIntBits(var5.getInterpolatedV((double)var12));
   }

   public int rotateVertex(Vector3d var1, EnumFacing var2, int var3, ITransformation var4, boolean var5) {
      if (var4 == ModelRotation.X0_Y0) {
         return var3;
      } else {
         if (Reflector.ForgeHooksClient_transform.exists()) {
            Reflector.call(Reflector.ForgeHooksClient_transform, var1, var4.getMatrix());
         } else {
            this.func_178406_a(var1, new Vector3d(0.5D, 0.5D, 0.5D), new Matrix4d(var4.getMatrix()), new Vector3d(1.0D, 1.0D, 1.0D));
         }

         return var4.rotate(var2, var3);
      }
   }

   private void func_178408_a(int[] var1, EnumFacing var2) {
      int[] var3 = new int[var1.length];
      System.arraycopy(var1, 0, var3, 0, var1.length);
      float[] var4 = new float[EnumFacing.values().length];
      var4[EnumFaceing.Constants.field_179176_f] = 999.0F;
      var4[EnumFaceing.Constants.field_179178_e] = 999.0F;
      var4[EnumFaceing.Constants.field_179177_d] = 999.0F;
      var4[EnumFaceing.Constants.field_179180_c] = -999.0F;
      var4[EnumFaceing.Constants.field_179179_b] = -999.0F;
      var4[EnumFaceing.Constants.field_179181_a] = -999.0F;
      int var5 = var1.length / 4;

      int var6;
      float var7;
      for(int var8 = 0; var8 < 4; ++var8) {
         var6 = var5 * var8;
         float var9 = Float.intBitsToFloat(var3[var6]);
         float var10 = Float.intBitsToFloat(var3[var6 + 1]);
         var7 = Float.intBitsToFloat(var3[var6 + 2]);
         if (var9 < var4[EnumFaceing.Constants.field_179176_f]) {
            var4[EnumFaceing.Constants.field_179176_f] = var9;
         }

         if (var10 < var4[EnumFaceing.Constants.field_179178_e]) {
            var4[EnumFaceing.Constants.field_179178_e] = var10;
         }

         if (var7 < var4[EnumFaceing.Constants.field_179177_d]) {
            var4[EnumFaceing.Constants.field_179177_d] = var7;
         }

         if (var9 > var4[EnumFaceing.Constants.field_179180_c]) {
            var4[EnumFaceing.Constants.field_179180_c] = var9;
         }

         if (var10 > var4[EnumFaceing.Constants.field_179179_b]) {
            var4[EnumFaceing.Constants.field_179179_b] = var10;
         }

         if (var7 > var4[EnumFaceing.Constants.field_179181_a]) {
            var4[EnumFaceing.Constants.field_179181_a] = var7;
         }
      }

      EnumFaceing var18 = EnumFaceing.func_179027_a(var2);

      for(var6 = 0; var6 < 4; ++var6) {
         int var19 = var5 * var6;
         EnumFaceing.VertexInformation var20 = var18.func_179025_a(var6);
         var7 = var4[var20.field_179184_a];
         float var11 = var4[var20.field_179182_b];
         float var12 = var4[var20.field_179183_c];
         var1[var19] = Float.floatToRawIntBits(var7);
         var1[var19 + 1] = Float.floatToRawIntBits(var11);
         var1[var19 + 2] = Float.floatToRawIntBits(var12);

         for(int var13 = 0; var13 < 4; ++var13) {
            int var14 = var5 * var13;
            float var15 = Float.intBitsToFloat(var3[var14]);
            float var16 = Float.intBitsToFloat(var3[var14 + 1]);
            float var17 = Float.intBitsToFloat(var3[var14 + 2]);
            if (MathHelper.func_180185_a(var7, var15) && MathHelper.func_180185_a(var11, var16) && MathHelper.func_180185_a(var12, var17)) {
               var1[var19 + 4] = var3[var14 + 4];
               var1[var19 + 4 + 1] = var3[var14 + 4 + 1];
            }
         }
      }

   }

   public void func_178409_a(int[] var1, EnumFacing var2, BlockFaceUV var3, TextureAtlasSprite var4) {
      for(int var5 = 0; var5 < 4; ++var5) {
         this.func_178401_a(var5, var1, var2, var3, var4);
      }

   }

   public int func_178415_a(Vector3d var1, EnumFacing var2, int var3, ModelRotation var4, boolean var5) {
      return this.rotateVertex(var1, var2, var3, var4, var5);
   }

   private void func_178407_a(Vector3d var1, BlockPartRotation var2) {
      if (var2 != null) {
         Matrix4d var3 = this.func_178411_a();
         Vector3d var4 = new Vector3d(0.0D, 0.0D, 0.0D);
         switch(var2.field_178342_b) {
         case X:
            var3.mul(this.func_178416_a(new AxisAngle4d(1.0D, 0.0D, 0.0D, (double)var2.field_178343_c * 0.017453292519943295D)));
            var4.set(0.0D, 1.0D, 1.0D);
            break;
         case Y:
            var3.mul(this.func_178416_a(new AxisAngle4d(0.0D, 1.0D, 0.0D, (double)var2.field_178343_c * 0.017453292519943295D)));
            var4.set(1.0D, 0.0D, 1.0D);
            break;
         case Z:
            var3.mul(this.func_178416_a(new AxisAngle4d(0.0D, 0.0D, 1.0D, (double)var2.field_178343_c * 0.017453292519943295D)));
            var4.set(1.0D, 1.0D, 0.0D);
         }

         if (var2.field_178341_d) {
            if (Math.abs(var2.field_178343_c) == 22.5F) {
               var4.scale(field_178418_a);
            } else {
               var4.scale(field_178417_b);
            }

            var4.add(new Vector3d(1.0D, 1.0D, 1.0D));
         } else {
            var4.set(new Vector3d(1.0D, 1.0D, 1.0D));
         }

         this.func_178406_a(var1, new Vector3d(var2.field_178344_a), var3, var4);
      }

   }

   private Matrix4d func_178411_a() {
      Matrix4d var1 = new Matrix4d();
      var1.setIdentity();
      return var1;
   }

   public static EnumFacing func_178410_a(int[] var0) {
      int var1 = var0.length / 4;
      int var2 = var1 * 2;
      int var3 = var1 * 3;
      Vector3f var4 = new Vector3f(Float.intBitsToFloat(var0[0]), Float.intBitsToFloat(var0[1]), Float.intBitsToFloat(var0[2]));
      Vector3f var5 = new Vector3f(Float.intBitsToFloat(var0[var1]), Float.intBitsToFloat(var0[var1 + 1]), Float.intBitsToFloat(var0[var1 + 2]));
      Vector3f var6 = new Vector3f(Float.intBitsToFloat(var0[var2]), Float.intBitsToFloat(var0[var2 + 1]), Float.intBitsToFloat(var0[var2 + 2]));
      Vector3f var7 = new Vector3f();
      Vector3f var8 = new Vector3f();
      Vector3f var9 = new Vector3f();
      var7.sub(var4, var5);
      var8.sub(var6, var5);
      var9.cross(var8, var7);
      var9.normalize();
      EnumFacing var10 = null;
      float var11 = 0.0F;
      EnumFacing[] var12 = EnumFacing.values();
      int var13 = var12.length;

      for(int var14 = 0; var14 < var13; ++var14) {
         EnumFacing var15 = var12[var14];
         Vec3i var16 = var15.getDirectionVec();
         Vector3f var17 = new Vector3f((float)var16.getX(), (float)var16.getY(), (float)var16.getZ());
         float var18 = var9.dot(var17);
         if (var18 >= 0.0F && var18 > var11) {
            var11 = var18;
            var10 = var15;
         }
      }

      if (var11 < 0.719F) {
         if (var10 != EnumFacing.EAST && var10 != EnumFacing.WEST && var10 != EnumFacing.NORTH && var10 != EnumFacing.SOUTH) {
            var10 = EnumFacing.UP;
         } else {
            var10 = EnumFacing.NORTH;
         }
      }

      return var10 == null ? EnumFacing.UP : var10;
   }

   public BakedQuad makeBakedQuad(Vector3f var1, Vector3f var2, BlockPartFace var3, TextureAtlasSprite var4, EnumFacing var5, ITransformation var6, BlockPartRotation var7, boolean var8, boolean var9) {
      int[] var10 = this.makeQuadVertexData(var3, var4, var5, this.func_178403_a(var1, var2), var6, var7, var8, var9);
      EnumFacing var11 = func_178410_a(var10);
      if (var8) {
         this.func_178409_a(var10, var11, var3.field_178243_e, var4);
      }

      if (var7 == null) {
         this.func_178408_a(var10, var11);
      }

      if (Reflector.ForgeHooksClient_fillNormal.exists()) {
         Reflector.callVoid(Reflector.ForgeHooksClient_fillNormal, var10, var11);
      }

      return new BakedQuad(var10, var3.field_178245_c, var11, var4);
   }

   public BakedQuad func_178414_a(Vector3f var1, Vector3f var2, BlockPartFace var3, TextureAtlasSprite var4, EnumFacing var5, ModelRotation var6, BlockPartRotation var7, boolean var8, boolean var9) {
      return this.makeBakedQuad(var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   private int func_178413_a(EnumFacing var1) {
      float var2 = this.func_178412_b(var1);
      int var3 = MathHelper.clamp_int((int)(var2 * 255.0F), 0, 255);
      return -16777216 | var3 << 16 | var3 << 8 | var3;
   }

   private void func_178404_a(int[] var1, int var2, int var3, Vector3d var4, int var5, TextureAtlasSprite var6, BlockFaceUV var7) {
      int var8 = var1.length / 4;
      int var9 = var2 * var8;
      var1[var9] = Float.floatToRawIntBits((float)var4.x);
      var1[var9 + 1] = Float.floatToRawIntBits((float)var4.y);
      var1[var9 + 2] = Float.floatToRawIntBits((float)var4.z);
      var1[var9 + 3] = var5;
      var1[var9 + 4] = Float.floatToRawIntBits(var6.getInterpolatedU((double)var7.func_178348_a(var3)));
      var1[var9 + 4 + 1] = Float.floatToRawIntBits(var6.getInterpolatedV((double)var7.func_178346_b(var3)));
   }

   private int[] makeQuadVertexData(BlockPartFace var1, TextureAtlasSprite var2, EnumFacing var3, float[] var4, ITransformation var5, BlockPartRotation var6, boolean var7, boolean var8) {
      byte var9 = 28;
      if (Config.isShaders()) {
         var9 = 56;
      }

      int[] var10 = new int[var9];

      for(int var11 = 0; var11 < 4; ++var11) {
         this.fillVertexData(var10, var11, var3, var1, var4, var2, var5, var6, var7, var8);
      }

      return var10;
   }

   static final class SwitchEnumFacing {
      static final int[] field_178400_a;
      static final int[] field_178399_b = new int[EnumFacing.Axis.values().length];
      private static final String __OBFID = "CL_00002489";

      static {
         try {
            field_178399_b[EnumFacing.Axis.X.ordinal()] = 1;
         } catch (NoSuchFieldError var9) {
         }

         try {
            field_178399_b[EnumFacing.Axis.Y.ordinal()] = 2;
         } catch (NoSuchFieldError var8) {
         }

         try {
            field_178399_b[EnumFacing.Axis.Z.ordinal()] = 3;
         } catch (NoSuchFieldError var7) {
         }

         field_178400_a = new int[EnumFacing.values().length];

         try {
            field_178400_a[EnumFacing.DOWN.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_178400_a[EnumFacing.UP.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_178400_a[EnumFacing.NORTH.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_178400_a[EnumFacing.SOUTH.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_178400_a[EnumFacing.WEST.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178400_a[EnumFacing.EAST.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
