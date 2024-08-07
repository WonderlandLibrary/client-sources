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

public class WorldGenTaiga2
extends WorldGenAbstractTree {
    private static final String __OBFID = "CL_00000435";

    public WorldGenTaiga2(boolean p_i2025_1_) {
        super(p_i2025_1_);
    }

    @Override
    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_) {
        int var4 = p_180709_2_.nextInt(4) + 6;
        int var5 = 1 + p_180709_2_.nextInt(2);
        int var6 = var4 - var5;
        int var7 = 2 + p_180709_2_.nextInt(2);
        boolean var8 = true;
        if (p_180709_3_.getY() >= 1 && p_180709_3_.getY() + var4 + 1 <= 256) {
            int var21;
            int var11;
            int var9 = p_180709_3_.getY();
            while (var9 <= p_180709_3_.getY() + 1 + var4 && var8) {
                boolean var10 = true;
                var21 = var9 - p_180709_3_.getY() < var5 ? 0 : var7;
                var11 = p_180709_3_.getX() - var21;
                while (var11 <= p_180709_3_.getX() + var21 && var8) {
                    int var12 = p_180709_3_.getZ() - var21;
                    while (var12 <= p_180709_3_.getZ() + var21 && var8) {
                        if (var9 >= 0 && var9 < 256) {
                            Block var13 = worldIn.getBlockState(new BlockPos(var11, var9, var12)).getBlock();
                            if (var13.getMaterial() != Material.air && var13.getMaterial() != Material.leaves) {
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
            Block var20 = worldIn.getBlockState(p_180709_3_.offsetDown()).getBlock();
            if ((var20 == Blocks.grass || var20 == Blocks.dirt || var20 == Blocks.farmland) && p_180709_3_.getY() < 256 - var4 - 1) {
                int var14;
                this.func_175921_a(worldIn, p_180709_3_.offsetDown());
                var21 = p_180709_2_.nextInt(2);
                var11 = 1;
                int var22 = 0;
                int var23 = 0;
                while (var23 <= var6) {
                    var14 = p_180709_3_.getY() + var4 - var23;
                    int var15 = p_180709_3_.getX() - var21;
                    while (var15 <= p_180709_3_.getX() + var21) {
                        int var16 = var15 - p_180709_3_.getX();
                        int var17 = p_180709_3_.getZ() - var21;
                        while (var17 <= p_180709_3_.getZ() + var21) {
                            BlockPos var19;
                            int var18 = var17 - p_180709_3_.getZ();
                            if (!(Math.abs(var16) == var21 && Math.abs(var18) == var21 && var21 > 0 || worldIn.getBlockState(var19 = new BlockPos(var15, var14, var17)).getBlock().isFullBlock())) {
                                this.func_175905_a(worldIn, var19, Blocks.leaves, BlockPlanks.EnumType.SPRUCE.func_176839_a());
                            }
                            ++var17;
                        }
                        ++var15;
                    }
                    if (var21 >= var11) {
                        var21 = var22;
                        var22 = 1;
                        if (++var11 > var7) {
                            var11 = var7;
                        }
                    } else {
                        ++var21;
                    }
                    ++var23;
                }
                var23 = p_180709_2_.nextInt(3);
                var14 = 0;
                while (var14 < var4 - var23) {
                    Block var24 = worldIn.getBlockState(p_180709_3_.offsetUp(var14)).getBlock();
                    if (var24.getMaterial() == Material.air || var24.getMaterial() == Material.leaves) {
                        this.func_175905_a(worldIn, p_180709_3_.offsetUp(var14), Blocks.log, BlockPlanks.EnumType.SPRUCE.func_176839_a());
                    }
                    ++var14;
                }
                return true;
            }
            return false;
        }
        return false;
    }
}

