package net.shadersmod.client;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.shadersmod.client.IteratorRenderChunks;
import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.Shaders;

public class ShadowUtils {
   public static Iterator makeShadowChunkIterator(WorldClient var0, double var1, Entity var3, int var4, ViewFrustum var5) {
      ShaderOption.p();
      float var7 = Shaders.ak();
      if(var7 > 0.0F && var7 < (float)((var4 - 1) * 16)) {
         int var19 = MathHelper.ceiling_float_int(var7 / 16.0F) + 1;
         float var20 = var0.getCelestialAngleRadians((float)var1);
         float var10 = Shaders.sunPathRotation * 0.017453292F;
         float var11 = var20 > 1.5707964F && var20 < 4.712389F?var20 + 3.1415927F:var20;
         float var12 = -MathHelper.sin(var11);
         float var13 = MathHelper.cos(var11) * MathHelper.cos(var10);
         float var14 = -MathHelper.cos(var11) * MathHelper.sin(var10);
         BlockPos var15 = new BlockPos(MathHelper.floor_double(var3.posX) >> 4, MathHelper.floor_double(var3.posY) >> 4, MathHelper.floor_double(var3.posZ) >> 4);
         BlockPos var16 = var15.add((double)(-var12 * (float)var19), (double)(-var13 * (float)var19), (double)(-var14 * (float)var19));
         BlockPos var17 = var15.add((double)(var12 * (float)var4), (double)(var13 * (float)var4), (double)(var14 * (float)var4));
         IteratorRenderChunks var18 = new IteratorRenderChunks(var5, var16, var17, var19, var19);
         return var18;
      } else {
         List var8 = Arrays.asList(var5.renderChunks);
         Iterator var9 = var8.iterator();
         return var9;
      }
   }
}
