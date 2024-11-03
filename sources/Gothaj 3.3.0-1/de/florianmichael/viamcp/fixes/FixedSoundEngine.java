package de.florianmichael.viamcp.fixes;

import de.florianmichael.vialoadingbase.ViaLoadingBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class FixedSoundEngine {
   private static final Minecraft mc = Minecraft.getMinecraft();

   public static boolean destroyBlock(World world, BlockPos pos, boolean dropBlock) {
      IBlockState iblockstate = world.getBlockState(pos);
      Block block = iblockstate.getBlock();
      world.playAuxSFX(2001, pos, Block.getStateId(iblockstate));
      if (block.getMaterial() == Material.air) {
         return false;
      } else {
         if (dropBlock) {
            block.dropBlockAsItem(world, pos, iblockstate, 0);
         }

         return world.setBlockState(pos, Blocks.air.getDefaultState(), 3);
      }
   }

   public static boolean onItemUse(
      ItemBlock iblock, ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ
   ) {
      IBlockState iblockstate = worldIn.getBlockState(pos);
      Block block = iblockstate.getBlock();
      if (!block.isReplaceable(worldIn, pos)) {
         pos = pos.offset(side);
      }

      if (stack.stackSize == 0) {
         return false;
      } else if (!playerIn.canPlayerEdit(pos, side, stack)) {
         return false;
      } else if (worldIn.canBlockBePlaced(iblock.getBlock(), pos, false, side, null, stack)) {
         int i = iblock.getMetadata(stack.getMetadata());
         IBlockState iblockstate1 = iblock.getBlock().onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, i, playerIn);
         if (worldIn.setBlockState(pos, iblockstate1, 3)) {
            iblockstate1 = worldIn.getBlockState(pos);
            if (iblockstate1.getBlock() == iblock.getBlock()) {
               ItemBlock.setTileEntityNBT(worldIn, playerIn, pos, stack);
               iblock.getBlock().onBlockPlacedBy(worldIn, pos, iblockstate1, playerIn, stack);
            }

            if (ViaLoadingBase.getInstance().getTargetVersion().getOriginalVersion() != 47) {
               mc.theWorld
                  .playSoundAtPos(
                     pos.add(0.5, 0.5, 0.5),
                     iblock.getBlock().stepSound.getPlaceSound(),
                     (iblock.getBlock().stepSound.getVolume() + 1.0F) / 2.0F,
                     iblock.getBlock().stepSound.getFrequency() * 0.8F,
                     false
                  );
            } else {
               worldIn.playSoundEffect(
                  (double)((float)pos.getX() + 0.5F),
                  (double)((float)pos.getY() + 0.5F),
                  (double)((float)pos.getZ() + 0.5F),
                  iblock.getBlock().stepSound.getPlaceSound(),
                  (iblock.getBlock().stepSound.getVolume() + 1.0F) / 2.0F,
                  iblock.getBlock().stepSound.getFrequency() * 0.8F
               );
            }

            stack.stackSize--;
         }

         return true;
      } else {
         return false;
      }
   }
}
