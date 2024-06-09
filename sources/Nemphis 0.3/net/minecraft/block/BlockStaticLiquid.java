/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class BlockStaticLiquid
extends BlockLiquid {
    private static final String __OBFID = "CL_00000315";

    protected BlockStaticLiquid(Material p_i45429_1_) {
        super(p_i45429_1_);
        this.setTickRandomly(false);
        if (p_i45429_1_ == Material.lava) {
            this.setTickRandomly(true);
        }
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        if (!this.func_176365_e(worldIn, pos, state)) {
            this.updateLiquid(worldIn, pos, state);
        }
    }

    private void updateLiquid(World worldIn, BlockPos p_176370_2_, IBlockState p_176370_3_) {
        BlockDynamicLiquid var4 = BlockStaticLiquid.getDynamicLiquidForMaterial(this.blockMaterial);
        worldIn.setBlockState(p_176370_2_, var4.getDefaultState().withProperty(LEVEL, p_176370_3_.getValue(LEVEL)), 2);
        worldIn.scheduleUpdate(p_176370_2_, var4, this.tickRate(worldIn));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (this.blockMaterial != Material.lava || !worldIn.getGameRules().getGameRuleBooleanValue("doFireTick")) return;
        int var5 = rand.nextInt(3);
        if (var5 > 0) {
            BlockPos var6 = pos;
            int var7 = 0;
            while (var7 < var5) {
                var6 = var6.add(rand.nextInt(3) - 1, 1, rand.nextInt(3) - 1);
                Block var8 = worldIn.getBlockState(var6).getBlock();
                if (var8.blockMaterial == Material.air) {
                    if (this.isSurroundingBlockFlammable(worldIn, var6)) {
                        worldIn.setBlockState(var6, Blocks.fire.getDefaultState());
                        return;
                    }
                } else if (var8.blockMaterial.blocksMovement()) {
                    return;
                }
                ++var7;
            }
            return;
        } else {
            int var9 = 0;
            while (var9 < 3) {
                BlockPos var10 = pos.add(rand.nextInt(3) - 1, 0, rand.nextInt(3) - 1);
                if (worldIn.isAirBlock(var10.offsetUp()) && this.getCanBlockBurn(worldIn, var10)) {
                    worldIn.setBlockState(var10.offsetUp(), Blocks.fire.getDefaultState());
                }
                ++var9;
            }
        }
    }

    protected boolean isSurroundingBlockFlammable(World worldIn, BlockPos p_176369_2_) {
        EnumFacing[] var3 = EnumFacing.values();
        int var4 = var3.length;
        int var5 = 0;
        while (var5 < var4) {
            EnumFacing var6 = var3[var5];
            if (this.getCanBlockBurn(worldIn, p_176369_2_.offset(var6))) {
                return true;
            }
            ++var5;
        }
        return false;
    }

    private boolean getCanBlockBurn(World worldIn, BlockPos p_176368_2_) {
        return worldIn.getBlockState(p_176368_2_).getBlock().getMaterial().getCanBurn();
    }
}

