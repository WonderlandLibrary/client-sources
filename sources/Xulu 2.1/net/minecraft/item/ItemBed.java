package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemBed extends Item
{
    public ItemBed()
    {
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY)
    {
        if (playerIn.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }
        else if (hand != EnumFacing.UP)
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            IBlockState iblockstate = playerIn.getBlockState(worldIn);
            Block block = iblockstate.getBlock();
            boolean flag = block.isReplaceable(playerIn, worldIn);

            if (!flag)
            {
                worldIn = worldIn.up();
            }

            int i = MathHelper.floor((double)(stack.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EnumFacing enumfacing = EnumFacing.getHorizontal(i);
            BlockPos blockpos = worldIn.offset(enumfacing);
            ItemStack itemstack = stack.getHeldItem(pos);

            if (stack.canPlayerEdit(worldIn, hand, itemstack) && stack.canPlayerEdit(blockpos, hand, itemstack))
            {
                IBlockState iblockstate1 = playerIn.getBlockState(blockpos);
                boolean flag1 = iblockstate1.getBlock().isReplaceable(playerIn, blockpos);
                boolean flag2 = flag || playerIn.isAirBlock(worldIn);
                boolean flag3 = flag1 || playerIn.isAirBlock(blockpos);

                if (flag2 && flag3 && playerIn.getBlockState(worldIn.down()).isFullyOpaque() && playerIn.getBlockState(blockpos.down()).isFullyOpaque())
                {
                    IBlockState iblockstate2 = Blocks.BED.getDefaultState().withProperty(BlockBed.OCCUPIED, Boolean.valueOf(false)).withProperty(BlockBed.FACING, enumfacing).withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
                    playerIn.setBlockState(worldIn, iblockstate2, 10);
                    playerIn.setBlockState(blockpos, iblockstate2.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), 10);
                    playerIn.notifyNeighborsRespectDebug(worldIn, block, false);
                    playerIn.notifyNeighborsRespectDebug(blockpos, iblockstate1.getBlock(), false);
                    SoundType soundtype = iblockstate2.getBlock().getSoundType();
                    playerIn.playSound((EntityPlayer)null, worldIn, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    itemstack.func_190918_g(1);
                    return EnumActionResult.SUCCESS;
                }
                else
                {
                    return EnumActionResult.FAIL;
                }
            }
            else
            {
                return EnumActionResult.FAIL;
            }
        }
    }
}
