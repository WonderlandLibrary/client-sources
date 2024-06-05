package net.minecraft.client.renderer.block.model;

import java.util.Arrays;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BreakingFour$BreakingFour$1;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class BreakingFour extends BakedQuad {
   private final TextureAtlasSprite texture;
   private static final String g = "CL_00002492";

   public BreakingFour(BakedQuad var1, TextureAtlasSprite var2) {
      super(Arrays.copyOf(var1.getVertexData(), var1.getVertexData().length), var1.tintIndex, FaceBakery.getFacingFromVertexData(var1.getVertexData()));
      this.texture = var2;
      this.func_178217_e();
   }

   private void func_178217_e() {
      for(int var1 = 0; var1 < 4; ++var1) {
         this.func_178216_a(var1);
      }

   }

   private void func_178216_a(int var1) {
      int var2 = this.vertexData.length / 4;
      int var3 = var2 * var1;
      float var4 = Float.intBitsToFloat(this.vertexData[var3]);
      float var5 = Float.intBitsToFloat(this.vertexData[var3 + 1]);
      float var6 = Float.intBitsToFloat(this.vertexData[var3 + 2]);
      float var7 = 0.0F;
      float var8 = 0.0F;
      switch(BreakingFour$BreakingFour$1.field_178419_a[this.face.ordinal()]) {
      case 1:
         var7 = var4 * 16.0F;
         var8 = (1.0F - var6) * 16.0F;
         break;
      case 2:
         var7 = var4 * 16.0F;
         var8 = var6 * 16.0F;
         break;
      case 3:
         var7 = (1.0F - var4) * 16.0F;
         var8 = (1.0F - var5) * 16.0F;
         break;
      case 4:
         var7 = var4 * 16.0F;
         var8 = (1.0F - var5) * 16.0F;
         break;
      case 5:
         var7 = var6 * 16.0F;
         var8 = (1.0F - var5) * 16.0F;
         break;
      case 6:
         var7 = (1.0F - var6) * 16.0F;
         var8 = (1.0F - var5) * 16.0F;
      }

      this.vertexData[var3 + 4] = Float.floatToRawIntBits(this.texture.getInterpolatedU((double)var7));
      this.vertexData[var3 + 4 + 1] = Float.floatToRawIntBits(this.texture.getInterpolatedV((double)var8));
   }
}
