package net.optifine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.acE;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.util.EnumFacing;
import net.optifine.Config;
import net.optifine.MatchBlock;

public class ModelUtils {
   public static void dbgModel(IBakedModel var0) {
      acE[] var1 = MatchBlock.b();
      Config.dbg("Model: " + var0 + ", ao: " + var0.isAmbientOcclusion() + ", gui3d: " + var0.isGui3d() + ", builtIn: " + var0.isBuiltInRenderer() + ", particle: " + var0.getParticleTexture());
      EnumFacing[] var2 = EnumFacing.VALUES;
      int var3 = 0;
      if(var3 < var2.length) {
         EnumFacing var4 = var2[var3];
         List var5 = var0.getFaceQuads(var4);
         dbgQuads(var4.getName(), var5, "  ");
         ++var3;
      }

      List var7 = var0.getGeneralQuads();
      dbgQuads("General", var7, "  ");
   }

   private static void dbgQuads(String var0, List var1, String var2) {
      MatchBlock.b();
      Iterator var4 = var1.iterator();
      if(var4.hasNext()) {
         Object var5 = var4.next();
         dbgQuad(var0, (BakedQuad)var5, var2);
      }

   }

   public static void dbgQuad(String var0, BakedQuad var1, String var2) {
      Config.dbg(var2 + "Quad: " + var1.getClass().getName() + ", type: " + var0 + ", face: " + var1.getFace() + ", tint: " + var1.getTintIndex() + ", sprite: " + var1.getSprite());
      dbgVertexData(var1.getVertexData(), "  " + var2);
   }

   public static void dbgVertexData(int[] var0, String var1) {
      int var3 = var0.length / 4;
      MatchBlock.b();
      Config.dbg(var1 + "Length: " + var0.length + ", step: " + var3);
      int var4 = 0;
      if(var4 < 4) {
         int var5 = var4 * var3;
         float var6 = Float.intBitsToFloat(var0[var5 + 0]);
         float var7 = Float.intBitsToFloat(var0[var5 + 1]);
         float var8 = Float.intBitsToFloat(var0[var5 + 2]);
         int var9 = var0[var5 + 3];
         float var10 = Float.intBitsToFloat(var0[var5 + 4]);
         float var11 = Float.intBitsToFloat(var0[var5 + 5]);
         Config.dbg(var1 + var4 + " xyz: " + var6 + "," + var7 + "," + var8 + " col: " + var9 + " u,v: " + var10 + "," + var11);
         ++var4;
      }

   }

   public static IBakedModel duplicateModel(IBakedModel var0) {
      MatchBlock.b();
      List var2 = duplicateQuadList(var0.getGeneralQuads());
      EnumFacing[] var3 = EnumFacing.VALUES;
      ArrayList var4 = new ArrayList();
      int var5 = 0;
      if(var5 < var3.length) {
         EnumFacing var6 = var3[var5];
         List var7 = var0.getFaceQuads(var6);
         List var8 = duplicateQuadList(var7);
         var4.add(var8);
         ++var5;
      }

      SimpleBakedModel var10 = new SimpleBakedModel(var2, var4, var0.isAmbientOcclusion(), var0.isGui3d(), var0.getParticleTexture(), var0.getItemCameraTransforms());
      return var10;
   }

   public static List duplicateQuadList(List var0) {
      MatchBlock.b();
      ArrayList var2 = new ArrayList();
      Iterator var3 = var0.iterator();
      if(var3.hasNext()) {
         Object var4 = var3.next();
         BakedQuad var5 = duplicateQuad((BakedQuad)var4);
         var2.add(var5);
      }

      return var2;
   }

   public static BakedQuad duplicateQuad(BakedQuad var0) {
      BakedQuad var1 = new BakedQuad((int[])((int[])var0.getVertexData().clone()), var0.getTintIndex(), var0.getFace(), var0.getSprite());
      return var1;
   }
}
