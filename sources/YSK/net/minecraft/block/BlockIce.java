package net.minecraft.block;

import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockIce extends BlockBreakable
{
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "".length();
    }
    
    @Override
    public int getMobilityFlag() {
        return "".length();
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockToAir, final IBlockState blockState, final TileEntity tileEntity) {
        entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
        entityPlayer.addExhaustion(0.025f);
        if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(entityPlayer)) {
            final ItemStack stackedBlock = this.createStackedBlock(blockState);
            if (stackedBlock != null) {
                Block.spawnAsEntity(world, blockToAir, stackedBlock);
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
        }
        else {
            if (world.provider.doesWaterVaporize()) {
                world.setBlockToAir(blockToAir);
                return;
            }
            this.dropBlockAsItem(world, blockToAir, blockState, EnchantmentHelper.getFortuneModifier(entityPlayer));
            final Material material = world.getBlockState(blockToAir.down()).getBlock().getMaterial();
            if (material.blocksMovement() || material.isLiquid()) {
                world.setBlockState(blockToAir, Blocks.flowing_water.getDefaultState());
            }
        }
    }
    
    public BlockIce() {
        super(Material.ice, "".length() != 0);
        this.slipperiness = 0.98f;
        this.setTickRandomly(" ".length() != 0);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockToAir, final IBlockState blockState, final Random random) {
        if (world.getLightFor(EnumSkyBlock.BLOCK, blockToAir) > (0xAB ^ 0xA0) - this.getLightOpacity()) {
            if (world.provider.doesWaterVaporize()) {
                world.setBlockToAir(blockToAir);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                this.dropBlockAsItem(world, blockToAir, world.getBlockState(blockToAir), "".length());
                world.setBlockState(blockToAir, Blocks.water.getDefaultState());
            }
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
}
