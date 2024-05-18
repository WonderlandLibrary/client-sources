/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenSwamp
extends WorldGenAbstractTree {
    private static final String __OBFID = "CL_00000436";

    public WorldGenSwamp() {
        super(false);
    }

    @Override
    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_) {
        int var4 = p_180709_2_.nextInt(4) + 5;
        while (worldIn.getBlockState(p_180709_3_.offsetDown()).getBlock().getMaterial() == Material.water) {
            p_180709_3_ = p_180709_3_.offsetDown();
        }
        boolean var5 = true;
        if (p_180709_3_.getY() >= 1 && p_180709_3_.getY() + var4 + 1 <= 256) {
            int var8;
            int var9;
            int var6 = p_180709_3_.getY();
            while (var6 <= p_180709_3_.getY() + 1 + var4) {
                int var7 = 1;
                if (var6 == p_180709_3_.getY()) {
                    var7 = 0;
                }
                if (var6 >= p_180709_3_.getY() + 1 + var4 - 2) {
                    var7 = 3;
                }
                var8 = p_180709_3_.getX() - var7;
                while (var8 <= p_180709_3_.getX() + var7 && var5) {
                    var9 = p_180709_3_.getZ() - var7;
                    while (var9 <= p_180709_3_.getZ() + var7 && var5) {
                        if (var6 >= 0 && var6 < 256) {
                            Block var10 = worldIn.getBlockState(new BlockPos(var8, var6, var9)).getBlock();
                            if (var10.getMaterial() != Material.air && var10.getMaterial() != Material.leaves) {
                                if (var10 != Blocks.water && var10 != Blocks.flowing_water) {
                                    var5 = false;
                                } else if (var6 > p_180709_3_.getY()) {
                                    var5 = false;
                                }
                            }
                        } else {
                            var5 = false;
                        }
                        ++var9;
                    }
                    ++var8;
                }
                ++var6;
            }
            if (!var5) {
                return false;
            }
            Block var17 = worldIn.getBlockState(p_180709_3_.offsetDown()).getBlock();
            if ((var17 == Blocks.grass || var17 == Blocks.dirt) && p_180709_3_.getY() < 256 - var4 - 1) {
                int var11;
                int var20;
                BlockPos var14;
                this.func_175921_a(worldIn, p_180709_3_.offsetDown());
                int var18 = p_180709_3_.getY() - 3 + var4;
                while (var18 <= p_180709_3_.getY() + var4) {
                    var8 = var18 - (p_180709_3_.getY() + var4);
                    var9 = 2 - var8 / 2;
                    var20 = p_180709_3_.getX() - var9;
                    while (var20 <= p_180709_3_.getX() + var9) {
                        var11 = var20 - p_180709_3_.getX();
                        int var12 = p_180709_3_.getZ() - var9;
                        while (var12 <= p_180709_3_.getZ() + var9) {
                            int var13 = var12 - p_180709_3_.getZ();
                            if ((Math.abs(var11) != var9 || Math.abs(var13) != var9 || p_180709_2_.nextInt(2) != 0 && var8 != 0) && !worldIn.getBlockState(var14 = new BlockPos(var20, var18, var12)).getBlock().isFullBlock()) {
                                this.func_175906_a(worldIn, var14, Blocks.leaves);
                            }
                            ++var12;
                        }
                        ++var20;
                    }
                    ++var18;
                }
                var18 = 0;
                while (var18 < var4) {
                    Block var19 = worldIn.getBlockState(p_180709_3_.offsetUp(var18)).getBlock();
                    if (var19.getMaterial() == Material.air || var19.getMaterial() == Material.leaves || var19 == Blocks.flowing_water || var19 == Blocks.water) {
                        this.func_175906_a(worldIn, p_180709_3_.offsetUp(var18), Blocks.log);
                    }
                    ++var18;
                }
                var18 = p_180709_3_.getY() - 3 + var4;
                while (var18 <= p_180709_3_.getY() + var4) {
                    var8 = var18 - (p_180709_3_.getY() + var4);
                    var9 = 2 - var8 / 2;
                    var20 = p_180709_3_.getX() - var9;
                    while (var20 <= p_180709_3_.getX() + var9) {
                        var11 = p_180709_3_.getZ() - var9;
                        while (var11 <= p_180709_3_.getZ() + var9) {
                            BlockPos var21 = new BlockPos(var20, var18, var11);
                            if (worldIn.getBlockState(var21).getBlock().getMaterial() == Material.leaves) {
                                BlockPos var22 = var21.offsetWest();
                                var14 = var21.offsetEast();
                                BlockPos var15 = var21.offsetNorth();
                                BlockPos var16 = var21.offsetSouth();
                                if (p_180709_2_.nextInt(4) == 0 && worldIn.getBlockState(var22).getBlock().getMaterial() == Material.air) {
                                    this.func_175922_a(worldIn, var22, BlockVine.field_176275_S);
                                }
                                if (p_180709_2_.nextInt(4) == 0 && worldIn.getBlockState(var14).getBlock().getMaterial() == Material.air) {
                                    this.func_175922_a(worldIn, var14, BlockVine.field_176271_T);
                                }
                                if (p_180709_2_.nextInt(4) == 0 && worldIn.getBlockState(var15).getBlock().getMaterial() == Material.air) {
                                    this.func_175922_a(worldIn, var15, BlockVine.field_176272_Q);
                                }
                                if (p_180709_2_.nextInt(4) == 0 && worldIn.getBlockState(var16).getBlock().getMaterial() == Material.air) {
                                    this.func_175922_a(worldIn, var16, BlockVine.field_176276_R);
                                }
                            }
                            ++var11;
                        }
                        ++var20;
                    }
                    ++var18;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    private void func_175922_a(World worldIn, BlockPos p_175922_2_, int p_175922_3_) {
        this.func_175905_a(worldIn, p_175922_2_, Blocks.vine, p_175922_3_);
        int var4 = 4;
        p_175922_2_ = p_175922_2_.offsetDown();
        while (worldIn.getBlockState(p_175922_2_).getBlock().getMaterial() == Material.air && var4 > 0) {
            this.func_175905_a(worldIn, p_175922_2_, Blocks.vine, p_175922_3_);
            p_175922_2_ = p_175922_2_.offsetDown();
            --var4;
        }
    }
}

