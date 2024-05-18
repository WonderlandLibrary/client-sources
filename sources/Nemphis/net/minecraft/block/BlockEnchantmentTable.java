/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockEnchantmentTable
extends BlockContainer {
    private static final String __OBFID = "CL_00000235";

    protected BlockEnchantmentTable() {
        super(Material.rock);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.randomDisplayTick(worldIn, pos, state, rand);
        int var5 = -2;
        while (var5 <= 2) {
            int var6 = -2;
            while (var6 <= 2) {
                if (var5 > -2 && var5 < 2 && var6 == -1) {
                    var6 = 2;
                }
                if (rand.nextInt(16) == 0) {
                    int var7 = 0;
                    while (var7 <= 1) {
                        BlockPos var8 = pos.add(var5, var7, var6);
                        if (worldIn.getBlockState(var8).getBlock() == Blocks.bookshelf) {
                            if (!worldIn.isAirBlock(pos.add(var5 / 2, 0, var6 / 2))) break;
                            worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, (double)pos.getX() + 0.5, (double)pos.getY() + 2.0, (double)pos.getZ() + 0.5, (double)((float)var5 + rand.nextFloat()) - 0.5, (double)((float)var7 - rand.nextFloat() - 1.0f), (double)((float)var6 + rand.nextFloat()) - 0.5, new int[0]);
                        }
                        ++var7;
                    }
                }
                ++var6;
            }
            ++var5;
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 3;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityEnchantmentTable();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }
        TileEntity var9 = worldIn.getTileEntity(pos);
        if (var9 instanceof TileEntityEnchantmentTable) {
            playerIn.displayGui((TileEntityEnchantmentTable)var9);
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity var6;
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (stack.hasDisplayName() && (var6 = worldIn.getTileEntity(pos)) instanceof TileEntityEnchantmentTable) {
            ((TileEntityEnchantmentTable)var6).func_145920_a(stack.getDisplayName());
        }
    }
}

