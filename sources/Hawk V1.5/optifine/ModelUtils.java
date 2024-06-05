package optifine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.util.EnumFacing;

public class ModelUtils {
   private static void dbgQuads(String var0, List var1, String var2) {
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         BakedQuad var4 = (BakedQuad)var3.next();
         dbgQuad(var0, var4, var2);
      }

   }

   public static void dbgModel(IBakedModel var0) {
      if (var0 != null) {
         Config.dbg(String.valueOf((new StringBuilder("Model: ")).append(var0).append(", ao: ").append(var0.isGui3d()).append(", gui3d: ").append(var0.isAmbientOcclusionEnabled()).append(", builtIn: ").append(var0.isBuiltInRenderer()).append(", particle: ").append(var0.getTexture())));
         EnumFacing[] var1 = EnumFacing.VALUES;

         for(int var2 = 0; var2 < var1.length; ++var2) {
            EnumFacing var3 = var1[var2];
            List var4 = var0.func_177551_a(var3);
            dbgQuads(var3.getName(), var4, "  ");
         }

         List var5 = var0.func_177550_a();
         dbgQuads("General", var5, "  ");
      }

   }

   public static void dbgVertexData(int[] var0, String var1) {
      int var2 = var0.length / 4;
      Config.dbg(String.valueOf((new StringBuilder(String.valueOf(var1))).append("Length: ").append(var0.length).append(", step: ").append(var2)));

      for(int var3 = 0; var3 < 4; ++var3) {
         int var4 = var3 * var2;
         float var5 = Float.intBitsToFloat(var0[var4]);
         float var6 = Float.intBitsToFloat(var0[var4 + 1]);
         float var7 = Float.intBitsToFloat(var0[var4 + 2]);
         int var8 = var0[var4 + 3];
         float var9 = Float.intBitsToFloat(var0[var4 + 4]);
         float var10 = Float.intBitsToFloat(var0[var4 + 5]);
         Config.dbg(String.valueOf((new StringBuilder(String.valueOf(var1))).append(var3).append(" xyz: ").append(var5).append(",").append(var6).append(",").append(var7).append(" col: ").append(var8).append(" u,v: ").append(var9).append(",").append(var10)));
      }

   }

   public static BakedQuad duplicateQuad(BakedQuad var0) {
      BakedQuad var1 = new BakedQuad((int[])var0.func_178209_a().clone(), var0.func_178211_c(), var0.getFace(), var0.getSprite());
      return var1;
   }

   public static IBakedModel duplicateModel(IBakedModel var0) {
      List var1 = duplicateQuadList(var0.func_177550_a());
      EnumFacing[] var2 = EnumFacing.VALUES;
      ArrayList var3 = new ArrayList();

      for(int var4 = 0; var4 < var2.length; ++var4) {
         EnumFacing var5 = var2[var4];
         List var6 = var0.func_177551_a(var5);
         List var7 = duplicateQuadList(var6);
         var3.add(var7);
      }

      SimpleBakedModel var8 = new SimpleBakedModel(var1, var3, var0.isGui3d(), var0.isAmbientOcclusionEnabled(), var0.getTexture(), var0.getItemCameraTransforms());
      return var8;
   }

   public static void dbgQuad(String var0, BakedQuad var1, String var2) {
      Config.dbg(String.valueOf((new StringBuilder(String.valueOf(var2))).append("Quad: ").append(var1.getClass().getName()).append(", type: ").append(var0).append(", face: ").append(var1.getFace()).append(", tint: ").append(var1.func_178211_c()).append(", sprite: ").append(var1.getSprite())));
      dbgVertexData(var1.func_178209_a(), String.valueOf((new StringBuilder("  ")).append(var2)));
   }

   public static List duplicateQuadList(List var0) {
      ArrayList var1 = new ArrayList();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         BakedQuad var3 = (BakedQuad)var2.next();
         BakedQuad var4 = duplicateQuad(var3);
         var1.add(var4);
      }

      return var1;
   }
}
