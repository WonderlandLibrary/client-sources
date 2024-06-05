package net.optifine;

import java.util.ArrayList;
import net.aED;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.BlockPartRotation;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.util.EnumFacing;
import net.optifine.Config;
import net.optifine.MatchBlock;
import org.lwjgl.util.vector.Vector3f;

public class BlockModelUtils {
   public static IBakedModel makeModelCube(String var0, int var1) {
      TextureAtlasSprite var2 = Config.getMinecraft().getTextureMapBlocks().getAtlasSprite(var0);
      return makeModelCube(var2, var1);
   }

   public static IBakedModel makeModelCube(TextureAtlasSprite var0, int var1) {
      ArrayList var3 = new ArrayList();
      MatchBlock.b();
      EnumFacing[] var4 = EnumFacing.VALUES;
      ArrayList var5 = new ArrayList(var4.length);
      int var6 = 0;
      if(var6 < var4.length) {
         EnumFacing var7 = var4[var6];
         ArrayList var8 = new ArrayList();
         var8.add(makeBakedQuad(var7, var0, var1));
         var5.add(var8);
         ++var6;
      }

      SimpleBakedModel var10 = new SimpleBakedModel(var3, var5, true, true, var0, ItemCameraTransforms.DEFAULT);
      return var10;
   }

   private static BakedQuad makeBakedQuad(EnumFacing var0, TextureAtlasSprite var1, int var2) {
      Vector3f var3 = new Vector3f(0.0F, 0.0F, 0.0F);
      Vector3f var4 = new Vector3f(16.0F, 16.0F, 16.0F);
      BlockFaceUV var5 = new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0);
      BlockPartFace var6 = new BlockPartFace(var0, var2, "#" + var0.getName(), var5);
      ModelRotation var7 = ModelRotation.X0_Y0;
      Object var8 = null;
      boolean var9 = false;
      boolean var10 = true;
      FaceBakery var11 = new FaceBakery();
      BakedQuad var12 = aED.a(var11, var3, var4, var6, var1, var0, var7, (BlockPartRotation)var8, var9, var10);
      return var12;
   }
}
