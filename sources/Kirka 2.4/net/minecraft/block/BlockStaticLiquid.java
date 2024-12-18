/*
 * Decompiled with CFR 0.143.
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

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        block5 : {
            if (this.blockMaterial != Material.lava || !worldIn.getGameRules().getGameRuleBooleanValue("doFireTick")) break block5;
            int var5 = rand.nextInt(3);
            if (var5 > 0) {
                BlockPos var6 = pos;
                for (int var7 = 0; var7 < var5; ++var7) {
                    var6 = var6.add(rand.nextInt(3) - 1, 1, rand.nextInt(3) - 1);
                    Block var8 = worldIn.getBlockState(var6).getBlock();
                    if (var8.blockMaterial == Material.air) {
                        if (!this.isSurroundingBlockFlammable(worldIn, var6)) continue;
                        worldIn.setBlockState(var6, Blocks.fire.getDefaultState());
                        return;
                    }
                    if (!var8.blockMaterial.blocksMovement()) continue;
                    return;
                }
            } else {
                for (int var9 = 0; var9 < 3; ++var9) {
                    BlockPos var10 = pos.add(rand.nextInt(3) - 1, 0, rand.nextInt(3) - 1);
                    if (!worldIn.isAirBlock(var10.offsetUp()) || !this.getCanBlockBurn(worldIn, var10)) continue;
                    worldIn.setBlockState(var10.offsetUp(), Blocks.fire.getDefaultState());
                }
            }
        }
    }

    protected boolean isSurroundingBlockFlammable(World worldIn, BlockPos p_176369_2_) {
        for (EnumFacing var6 : EnumFacing.values()) {
            if (!this.getCanBlockBurn(worldIn, p_176369_2_.offset(var6))) continue;
            return true;
        }
        return false;
    }

    private boolean getCanBlockBurn(World worldIn, BlockPos p_176368_2_) {
        return worldIn.getBlockState(p_176368_2_).getBlock().getMaterial().getCanBurn();
    }
}

