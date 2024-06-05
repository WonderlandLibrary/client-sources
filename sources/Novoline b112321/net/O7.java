package net;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.optifine.ConnectedTextures;
import net.optifine.RenderEnv;

public class O7 {
   public static BakedQuad a(IBlockAccess var0, IBlockState var1, BlockPos var2, BakedQuad var3, RenderEnv var4) {
      return ConnectedTextures.a(var0, var1, var2, var3, var4);
   }

   public static void a(TextureMap var0) {
      ConnectedTextures.updateIcons(var0);
   }

   public static int a(EnumFacing var0) {
      return ConnectedTextures.a(var0);
   }
}
