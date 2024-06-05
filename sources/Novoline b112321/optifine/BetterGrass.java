package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import optifine.BlockModelUtils;
import optifine.Config;
import optifine.MatchBlock;

public class BetterGrass {
   private static IBakedModel modelEmpty = new SimpleBakedModel(new ArrayList(), new ArrayList(), false, false, (TextureAtlasSprite)null, (ItemCameraTransforms)null);
   private static IBakedModel modelCubeMycelium = null;
   private static IBakedModel modelCubeGrassSnowy = null;
   private static IBakedModel modelCubeGrass = null;

   public static void update() {
      modelCubeGrass = BlockModelUtils.makeModelCube((String)"minecraft:blocks/grass_top", 0);
      modelCubeGrassSnowy = BlockModelUtils.makeModelCube((String)"minecraft:blocks/snow", -1);
      modelCubeMycelium = BlockModelUtils.makeModelCube((String)"minecraft:blocks/mycelium_top", -1);
   }

   public static List getFaceQuads(IBlockAccess var0, Block var1, BlockPos var2, EnumFacing var3, List var4) {
      PacketRemapper[] var5 = MatchBlock.b();
      if(var3 != EnumFacing.UP && var3 != EnumFacing.DOWN) {
         if(var1 instanceof BlockMycelium) {
            return Config.ai()?(getBlockAt(var2.down(), var3, var0) == Blocks.mycelium?modelCubeMycelium.getFaceQuads(var3):var4):modelCubeMycelium.getFaceQuads(var3);
         } else {
            if(var1 instanceof BlockGrass) {
               Block var6 = var0.getBlockState(var2.up()).getBlock();
               boolean var7 = var6 == Blocks.snow || var6 == Blocks.snow_layer;
               if(!Config.ai()) {
                  return modelCubeGrassSnowy.getFaceQuads(var3);
               }

               if(var7) {
                  if(getBlockAt(var2, var3, var0) == Blocks.snow_layer) {
                     return modelCubeGrassSnowy.getFaceQuads(var3);
                  }
               } else if(getBlockAt(var2.down(), var3, var0) == Blocks.grass) {
                  return modelCubeGrass.getFaceQuads(var3);
               }
            }

            return var4;
         }
      } else {
         return var4;
      }
   }

   private static Block getBlockAt(BlockPos var0, EnumFacing var1, IBlockAccess var2) {
      BlockPos var3 = var0.offset(var1);
      Block var4 = var2.getBlockState(var3).getBlock();
      return var4;
   }
}
