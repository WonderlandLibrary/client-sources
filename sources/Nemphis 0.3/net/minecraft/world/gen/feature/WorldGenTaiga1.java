/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenTaiga1
extends WorldGenAbstractTree {
    private static final String __OBFID = "CL_00000427";

    public WorldGenTaiga1() {
        super(false);
    }

    @Override
    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_) {
        int var4 = p_180709_2_.nextInt(5) + 7;
        int var5 = var4 - p_180709_2_.nextInt(2) - 3;
        int var6 = var4 - var5;
        int var7 = 1 + p_180709_2_.nextInt(var6 + 1);
        boolean var8 = true;
        if (p_180709_3_.getY() >= 1 && p_180709_3_.getY() + var4 + 1 <= 256) {
            int var18;
            int var12;
            int var11;
            int var9 = p_180709_3_.getY();
            while (var9 <= p_180709_3_.getY() + 1 + var4 && var8) {
                boolean var10 = true;
                var18 = var9 - p_180709_3_.getY() < var5 ? 0 : var7;
                var11 = p_180709_3_.getX() - var18;
                while (var11 <= p_180709_3_.getX() + var18 && var8) {
                    var12 = p_180709_3_.getZ() - var18;
                    while (var12 <= p_180709_3_.getZ() + var18 && var8) {
                        if (var9 >= 0 && var9 < 256) {
                            if (!this.func_150523_a(worldIn.getBlockState(new BlockPos(var11, var9, var12)).getBlock())) {
                                var8 = false;
                            }
                        } else {
                            var8 = false;
                        }
                        ++var12;
                    }
                    ++var11;
                }
                ++var9;
            }
            if (!var8) {
                return false;
            }
            Block var17 = worldIn.getBlockState(p_180709_3_.offsetDown()).getBlock();
            if ((var17 == Blocks.grass || var17 == Blocks.dirt) && p_180709_3_.getY() < 256 - var4 - 1) {
                this.func_175921_a(worldIn, p_180709_3_.offsetDown());
                var18 = 0;
                var11 = p_180709_3_.getY() + var4;
                while (var11 >= p_180709_3_.getY() + var5) {
                    var12 = p_180709_3_.getX() - var18;
                    while (var12 <= p_180709_3_.getX() + var18) {
                        int var13 = var12 - p_180709_3_.getX();
                        int var14 = p_180709_3_.getZ() - var18;
                        while (var14 <= p_180709_3_.getZ() + var18) {
                            BlockPos var16;
                            int var15 = var14 - p_180709_3_.getZ();
                            if (!(Math.abs(var13) == var18 && Math.abs(var15) == var18 && var18 > 0 || worldIn.getBlockState(var16 = new BlockPos(var12, var11, var14)).getBlock().isFullBlock())) {
                                this.func_175905_a(worldIn, var16, Blocks.leaves, BlockPlanks.EnumType.SPRUCE.func_176839_a());
                            }
                            ++var14;
                        }
                        ++var12;
                    }
                    if (var18 >= 1 && var11 == p_180709_3_.getY() + var5 + 1) {
                        --var18;
                    } else if (var18 < var7) {
                        ++var18;
                    }
                    --var11;
                }
                var11 = 0;
                while (var11 < var4 - 1) {
                    Block var19 = worldIn.getBlockState(p_180709_3_.offsetUp(var11)).getBlock();
                    if (var19.getMaterial() == Material.air || var19.getMaterial() == Material.leaves) {
                        this.func_175905_a(worldIn, p_180709_3_.offsetUp(var11), Blocks.log, BlockPlanks.EnumType.SPRUCE.func_176839_a());
                    }
                    ++var11;
                }
                return true;
            }
            return false;
        }
        return false;
    }
}

