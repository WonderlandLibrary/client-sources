package optifine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class SmartLeaves {
   private static IBakedModel modelLeavesDoubleBirch = null;
   private static IBakedModel modelLeavesCullBirch = null;
   private static IBakedModel modelLeavesDoubleAcacia = null;
   private static IBakedModel modelLeavesDoubleJungle = null;
   private static IBakedModel modelLeavesCullJungle = null;
   private static IBakedModel modelLeavesDoubleSpruce = null;
   private static List generalQuadsCullSpruce = null;
   private static IBakedModel modelLeavesCullDarkOak = null;
   private static List generalQuadsCullBirch = null;
   private static IBakedModel modelLeavesCullSpruce = null;
   private static List generalQuadsCullOak = null;
   private static List generalQuadsCullJungle = null;
   private static List generalQuadsCullAcacia = null;
   private static List generalQuadsCullDarkOak = null;
   private static IBakedModel modelLeavesCullOak = null;
   private static IBakedModel modelLeavesCullAcacia = null;
   private static IBakedModel modelLeavesDoubleOak = null;
   private static IBakedModel modelLeavesDoubleDarkOak = null;

   public static void updateLeavesModels() {
      ArrayList var0 = new ArrayList();
      modelLeavesCullAcacia = getModelCull("acacia", var0);
      modelLeavesCullBirch = getModelCull("birch", var0);
      modelLeavesCullDarkOak = getModelCull("dark_oak", var0);
      modelLeavesCullJungle = getModelCull("jungle", var0);
      modelLeavesCullOak = getModelCull("oak", var0);
      modelLeavesCullSpruce = getModelCull("spruce", var0);
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
      if (var0.size() > 0) {
         Config.dbg(String.valueOf((new StringBuilder("Enable face culling: ")).append(Config.arrayToString(var0.toArray()))));
      }

   }

   private static List getGeneralQuadsSafe(IBakedModel var0) {
      return var0 == null ? null : var0.func_177550_a();
   }

   public static IBakedModel getLeavesModel(IBakedModel var0) {
      if (!Config.isTreesSmart()) {
         return var0;
      } else {
         List var1 = var0.func_177550_a();
         return var1 == generalQuadsCullAcacia ? modelLeavesDoubleAcacia : (var1 == generalQuadsCullBirch ? modelLeavesDoubleBirch : (var1 == generalQuadsCullDarkOak ? modelLeavesDoubleDarkOak : (var1 == generalQuadsCullJungle ? modelLeavesDoubleJungle : (var1 == generalQuadsCullOak ? modelLeavesDoubleOak : (var1 == generalQuadsCullSpruce ? modelLeavesDoubleSpruce : var0)))));
      }
   }

   static IBakedModel getModelCull(String var0, List var1) {
      ModelManager var2 = Config.getModelManager();
      if (var2 == null) {
         return null;
      } else {
         ResourceLocation var3 = new ResourceLocation(String.valueOf((new StringBuilder("blockstates/")).append(var0).append("_leaves.json")));
         if (Config.getDefiningResourcePack(var3) != Config.getDefaultResourcePack()) {
            return null;
         } else {
            ResourceLocation var4 = new ResourceLocation(String.valueOf((new StringBuilder("models/block/")).append(var0).append("_leaves.json")));
            if (Config.getDefiningResourcePack(var4) != Config.getDefaultResourcePack()) {
               return null;
            } else {
               ModelResourceLocation var5 = new ModelResourceLocation(String.valueOf((new StringBuilder(String.valueOf(var0))).append("_leaves")), "normal");
               IBakedModel var6 = var2.getModel(var5);
               if (var6 != null && var6 != var2.getMissingModel()) {
                  List var7 = var6.func_177550_a();
                  if (var7.size() == 0) {
                     return var6;
                  } else if (var7.size() != 6) {
                     return null;
                  } else {
                     Iterator var8 = var7.iterator();

                     while(var8.hasNext()) {
                        BakedQuad var9 = (BakedQuad)var8.next();
                        List var10 = var6.func_177551_a(var9.getFace());
                        if (var10.size() > 0) {
                           return null;
                        }

                        var10.add(var9);
                     }

                     var7.clear();
                     var1.add(String.valueOf((new StringBuilder(String.valueOf(var0))).append("_leaves")));
                     return var6;
                  }
               } else {
                  return null;
               }
            }
         }
      }
   }

   private static IBakedModel getModelDoubleFace(IBakedModel var0) {
      if (var0 == null) {
         return null;
      } else if (var0.func_177550_a().size() > 0) {
         Config.warn(String.valueOf((new StringBuilder("SmartLeaves: Model is not cube, general quads: ")).append(var0.func_177550_a().size()).append(", model: ").append(var0)));
         return var0;
      } else {
         EnumFacing[] var1 = EnumFacing.VALUES;

         for(int var2 = 0; var2 < var1.length; ++var2) {
            EnumFacing var3 = var1[var2];
            List var4 = var0.func_177551_a(var3);
            if (var4.size() != 1) {
               Config.warn(String.valueOf((new StringBuilder("SmartLeaves: Model is not cube, side: ")).append(var3).append(", quads: ").append(var4.size()).append(", model: ").append(var0)));
               return var0;
            }
         }

         IBakedModel var12 = ModelUtils.duplicateModel(var0);
         List[] var13 = new List[var1.length];

         for(int var14 = 0; var14 < var1.length; ++var14) {
            EnumFacing var5 = var1[var14];
            List var6 = var12.func_177551_a(var5);
            BakedQuad var7 = (BakedQuad)var6.get(0);
            BakedQuad var8 = new BakedQuad((int[])var7.func_178209_a().clone(), var7.func_178211_c(), var7.getFace(), var7.getSprite());
            int[] var9 = var8.func_178209_a();
            int[] var10 = (int[])var9.clone();
            int var11 = var9.length / 4;
            System.arraycopy(var9, 0 * var11, var10, 3 * var11, var11);
            System.arraycopy(var9, 1 * var11, var10, 2 * var11, var11);
            System.arraycopy(var9, 2 * var11, var10, 1 * var11, var11);
            System.arraycopy(var9, 3 * var11, var10, 0 * var11, var11);
            System.arraycopy(var10, 0, var9, 0, var10.length);
            var6.add(var8);
         }

         return var12;
      }
   }
}
