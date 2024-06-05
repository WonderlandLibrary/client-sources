package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLever$EnumOrientation;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import optifine.Config;
import optifine.MatchBlock;

public class BetterSnow {
   private static IBakedModel modelSnowLayer = null;

   public static void update() {
      modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(Blocks.snow_layer.getDefaultState());
   }

   public static IBakedModel getModelSnowLayer() {
      return modelSnowLayer;
   }

   public static IBlockState getStateSnowLayer() {
      return Blocks.snow_layer.getDefaultState();
   }

   public static boolean shouldRender(IBlockAccess var0, Block var1, IBlockState var2, BlockPos var3) {
      PacketRemapper[] var4 = MatchBlock.b();
      return !checkBlock(var1, var2)?false:hasSnowNeighbours(var0, var3);
   }

   private static boolean hasSnowNeighbours(IBlockAccess var0, BlockPos var1) {
      MatchBlock.b();
      Block var3 = Blocks.snow_layer;
      return var0.getBlockState(var1.north()).getBlock() != var3 && var0.getBlockState(var1.south()).getBlock() != var3 && var0.getBlockState(var1.west()).getBlock() != var3 && var0.getBlockState(var1.east()).getBlock() != var3?false:var0.getBlockState(var1.down()).getBlock().isOpaqueCube();
   }

   private static boolean checkBlock(Block var0, IBlockState var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var0.isFullCube()) {
         return false;
      } else if(var0.isOpaqueCube()) {
         return false;
      } else if(var0 instanceof BlockSnow) {
         return false;
      } else if(!(var0 instanceof BlockBush) || !(var0 instanceof BlockDoublePlant) && !(var0 instanceof BlockFlower) && !(var0 instanceof BlockMushroom) && !(var0 instanceof BlockSapling) && !(var0 instanceof BlockTallGrass)) {
         if(!(var0 instanceof BlockFence) && !(var0 instanceof BlockFenceGate) && !(var0 instanceof BlockFlowerPot) && !(var0 instanceof BlockPane) && !(var0 instanceof BlockReed) && !(var0 instanceof BlockWall)) {
            if(var0 instanceof BlockRedstoneTorch && var1.getValue(BlockTorch.FACING) == EnumFacing.UP) {
               return true;
            } else {
               if(var0 instanceof BlockLever) {
                  Comparable var3 = var1.getValue(BlockLever.FACING);
                  if(var3 == BlockLever$EnumOrientation.UP_X || var3 == BlockLever$EnumOrientation.UP_Z) {
                     return true;
                  }
               }

               return false;
            }
         } else {
            return true;
         }
      } else {
         return true;
      }
   }
}
