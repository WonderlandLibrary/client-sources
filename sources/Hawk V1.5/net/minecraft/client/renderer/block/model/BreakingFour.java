package net.minecraft.client.renderer.block.model;

import java.util.Arrays;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class BreakingFour extends BakedQuad {
   private static final String __OBFID = "CL_00002492";
   private final TextureAtlasSprite texture;

   public BreakingFour(BakedQuad var1, TextureAtlasSprite var2) {
      super(Arrays.copyOf(var1.func_178209_a(), var1.func_178209_a().length), var1.field_178213_b, FaceBakery.func_178410_a(var1.func_178209_a()));
      this.texture = var2;
      this.func_178217_e();
   }

   private void func_178217_e() {
      for(int var1 = 0; var1 < 4; ++var1) {
         this.func_178216_a(var1);
      }

   }

   private void func_178216_a(int var1) {
      int var2 = this.field_178215_a.length / 4;
      int var3 = var2 * var1;
      float var4 = Float.intBitsToFloat(this.field_178215_a[var3]);
      float var5 = Float.intBitsToFloat(this.field_178215_a[var3 + 1]);
      float var6 = Float.intBitsToFloat(this.field_178215_a[var3 + 2]);
      float var7 = 0.0F;
      float var8 = 0.0F;
      switch(this.face) {
      case DOWN:
         var7 = var4 * 16.0F;
         var8 = (1.0F - var6) * 16.0F;
         break;
      case UP:
         var7 = var4 * 16.0F;
         var8 = var6 * 16.0F;
         break;
      case NORTH:
         var7 = (1.0F - var4) * 16.0F;
         var8 = (1.0F - var5) * 16.0F;
         break;
      case SOUTH:
         var7 = var4 * 16.0F;
         var8 = (1.0F - var5) * 16.0F;
         break;
      case WEST:
         var7 = var6 * 16.0F;
         var8 = (1.0F - var5) * 16.0F;
         break;
      case EAST:
         var7 = (1.0F - var6) * 16.0F;
         var8 = (1.0F - var5) * 16.0F;
      }

      this.field_178215_a[var3 + 4] = Float.floatToRawIntBits(this.texture.getInterpolatedU((double)var7));
      this.field_178215_a[var3 + 4 + 1] = Float.floatToRawIntBits(this.texture.getInterpolatedV((double)var8));
   }

   static final class SwitchEnumFacing {
      private static final String __OBFID = "CL_00002491";
      static final int[] field_178419_a = new int[EnumFacing.values().length];

      static {
         try {
            field_178419_a[EnumFacing.DOWN.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_178419_a[EnumFacing.UP.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_178419_a[EnumFacing.NORTH.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_178419_a[EnumFacing.SOUTH.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_178419_a[EnumFacing.WEST.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178419_a[EnumFacing.EAST.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
