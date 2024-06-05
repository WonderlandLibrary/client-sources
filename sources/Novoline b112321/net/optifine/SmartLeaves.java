package net.optifine;

import java.util.ArrayList;
import java.util.List;
import net.acE;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.util.EnumFacing;
import net.optifine.Config;
import net.optifine.MatchBlock;
import net.optifine.ModelUtils;

public class SmartLeaves {
   private static IBakedModel modelLeavesCullAcacia = null;
   private static IBakedModel modelLeavesCullBirch = null;
   private static IBakedModel modelLeavesCullDarkOak = null;
   private static IBakedModel modelLeavesCullJungle = null;
   private static IBakedModel modelLeavesCullOak = null;
   private static IBakedModel modelLeavesCullSpruce = null;
   private static List generalQuadsCullAcacia = null;
   private static List generalQuadsCullBirch = null;
   private static List generalQuadsCullDarkOak = null;
   private static List generalQuadsCullJungle = null;
   private static List generalQuadsCullOak = null;
   private static List generalQuadsCullSpruce = null;
   private static IBakedModel modelLeavesDoubleAcacia = null;
   private static IBakedModel modelLeavesDoubleBirch = null;
   private static IBakedModel modelLeavesDoubleDarkOak = null;
   private static IBakedModel modelLeavesDoubleJungle = null;
   private static IBakedModel modelLeavesDoubleOak = null;
   private static IBakedModel modelLeavesDoubleSpruce = null;

   public static IBakedModel getLeavesModel(IBakedModel var0) {
      acE[] var1 = MatchBlock.b();
      if(!Config.y()) {
         return var0;
      } else {
         List var2 = var0.getGeneralQuads();
         return var2 == generalQuadsCullAcacia?modelLeavesDoubleAcacia:(var2 == generalQuadsCullBirch?modelLeavesDoubleBirch:(var2 == generalQuadsCullDarkOak?modelLeavesDoubleDarkOak:(var2 == generalQuadsCullJungle?modelLeavesDoubleJungle:(var2 == generalQuadsCullOak?modelLeavesDoubleOak:(var2 == generalQuadsCullSpruce?modelLeavesDoubleSpruce:var0)))));
      }
   }

   public static void updateLeavesModels() {
      ArrayList var1 = new ArrayList();
      modelLeavesCullAcacia = a("acacia", var1);
      modelLeavesCullBirch = a("birch", var1);
      modelLeavesCullDarkOak = a("dark_oak", var1);
      modelLeavesCullJungle = a("jungle", var1);
      MatchBlock.b();
      modelLeavesCullOak = a("oak", var1);
      modelLeavesCullSpruce = a("spruce", var1);
      generalQuadsCullAcacia = getGeneralQuadsSafe(modelLeavesCullAcacia);
      generalQuadsCullBirch = getGeneralQuadsSafe(modelLeavesCullBirch);
      generalQuadsCullDarkOak = getGeneralQuadsSafe(modelLeavesCullDarkOak);
      generalQuadsCullJungle = getGeneralQuadsSafe(modelLeavesCullJungle);
      generalQuadsCullOak = getGeneralQuadsSafe(modelLeavesCullOak);
      generalQuadsCullSpruce = getGeneralQuadsSafe(modelLeavesCullSpruce);
      modelLeavesDoubleAcacia = getModelDoubleFace(modelLeavesCullAcacia);
      modelLeavesDoubleBirch = getModelDoubleFace(modelLeavesCullBirch);
      modelLeavesDoubleDarkOak = getModelDoubleFace(modelLeavesCullDarkOak);
      modelLeavesDoubleJungle = getModelDoubleFace(modelLeavesCullJungle);
      modelLeavesDoubleOak = getModelDoubleFace(modelLeavesCullOak);
      modelLeavesDoubleSpruce = getModelDoubleFace(modelLeavesCullSpruce);
      if(var1.size() > 0) {
         Config.dbg("Enable face culling: " + Config.a(var1.toArray()));
      }

   }

   private static List getGeneralQuadsSafe(IBakedModel var0) {
      acE[] var1 = MatchBlock.b();
      return var0 == null?null:var0.getGeneralQuads();
   }

   static IBakedModel a(String var0, List var1) {
      MatchBlock.b();
      ModelManager var3 = Config.getModelManager();
      return null;
   }

   private static IBakedModel getModelDoubleFace(IBakedModel var0) {
      acE[] var1 = MatchBlock.b();
      if(var0 == null) {
         return null;
      } else if(var0.getGeneralQuads().size() > 0) {
         Config.warn("SmartLeaves: Model is not cube, general quads: " + var0.getGeneralQuads().size() + ", model: " + var0);
         return var0;
      } else {
         EnumFacing[] var2 = EnumFacing.VALUES;
         int var3 = 0;
         if(var3 < var2.length) {
            EnumFacing var4 = var2[var3];
            List var5 = var0.getFaceQuads(var4);
            if(var5.size() != 1) {
               Config.warn("SmartLeaves: Model is not cube, side: " + var4 + ", quads: " + var5.size() + ", model: " + var0);
               return var0;
            }

            ++var3;
         }

         IBakedModel var14 = ModelUtils.duplicateModel(var0);
         List[] var15 = new List[var2.length];
         int var16 = 0;
         if(var16 < var2.length) {
            EnumFacing var6 = var2[var16];
            List var7 = var14.getFaceQuads(var6);
            BakedQuad var8 = (BakedQuad)var7.get(0);
            BakedQuad var9 = new BakedQuad((int[])((int[])var8.getVertexData().clone()), var8.getTintIndex(), var8.getFace(), var8.getSprite());
            int[] var10 = var9.getVertexData();
            int[] var11 = (int[])((int[])var10.clone());
            int var12 = var10.length / 4;
            System.arraycopy(var10, 0 * var12, var11, 3 * var12, var12);
            System.arraycopy(var10, 1 * var12, var11, 2 * var12, var12);
            System.arraycopy(var10, 2 * var12, var11, 1 * var12, var12);
            System.arraycopy(var10, 3 * var12, var11, 0 * var12, var12);
            System.arraycopy(var11, 0, var10, 0, var11.length);
            var7.add(var9);
            ++var16;
         }

         return var14;
      }
   }
}
