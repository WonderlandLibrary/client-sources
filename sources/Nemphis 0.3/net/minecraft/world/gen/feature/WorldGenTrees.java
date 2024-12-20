/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenTrees
extends WorldGenAbstractTree {
    private final int minTreeHeight;
    private final boolean vinesGrow;
    private final int metaWood;
    private final int metaLeaves;
    private static final String __OBFID = "CL_00000438";

    public WorldGenTrees(boolean p_i2027_1_) {
        this(p_i2027_1_, 4, 0, 0, false);
    }

    public WorldGenTrees(boolean p_i2028_1_, int p_i2028_2_, int p_i2028_3_, int p_i2028_4_, boolean p_i2028_5_) {
        super(p_i2028_1_);
        this.minTreeHeight = p_i2028_2_;
        this.metaWood = p_i2028_3_;
        this.metaLeaves = p_i2028_4_;
        this.vinesGrow = p_i2028_5_;
    }

    @Override
    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_) {
        int var4 = p_180709_2_.nextInt(3) + this.minTreeHeight;
        boolean var5 = true;
        if (p_180709_3_.getY() >= 1 && p_180709_3_.getY() + var4 + 1 <= 256) {
            int var9;
            int var7;
            int var6 = p_180709_3_.getY();
            while (var6 <= p_180709_3_.getY() + 1 + var4) {
                var7 = 1;
                if (var6 == p_180709_3_.getY()) {
                    var7 = 0;
                }
                if (var6 >= p_180709_3_.getY() + 1 + var4 - 2) {
                    var7 = 2;
                }
                int var8 = p_180709_3_.getX() - var7;
                while (var8 <= p_180709_3_.getX() + var7 && var5) {
                    var9 = p_180709_3_.getZ() - var7;
                    while (var9 <= p_180709_3_.getZ() + var7 && var5) {
                        if (var6 >= 0 && var6 < 256) {
                            if (!this.func_150523_a(worldIn.getBlockState(new BlockPos(var8, var6, var9)).getBlock())) {
                                var5 = false;
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
            Block var19 = worldIn.getBlockState(p_180709_3_.offsetDown()).getBlock();
            if ((var19 == Blocks.grass || var19 == Blocks.dirt || var19 == Blocks.farmland) && p_180709_3_.getY() < 256 - var4 - 1) {
                int var13;
                int var10;
                int var11;
                BlockPos var16;
                int var12;
                this.func_175921_a(worldIn, p_180709_3_.offsetDown());
                var7 = 3;
                int var20 = 0;
                var9 = p_180709_3_.getY() - var7 + var4;
                while (var9 <= p_180709_3_.getY() + var4) {
                    var10 = var9 - (p_180709_3_.getY() + var4);
                    var11 = var20 + 1 - var10 / 2;
                    var12 = p_180709_3_.getX() - var11;
                    while (var12 <= p_180709_3_.getX() + var11) {
                        var13 = var12 - p_180709_3_.getX();
                        int var14 = p_180709_3_.getZ() - var11;
                        while (var14 <= p_180709_3_.getZ() + var11) {
                            Block var17;
                            int var15 = var14 - p_180709_3_.getZ();
                            if ((Math.abs(var13) != var11 || Math.abs(var15) != var11 || p_180709_2_.nextInt(2) != 0 && var10 != 0) && ((var17 = worldIn.getBlockState(var16 = new BlockPos(var12, var9, var14)).getBlock()).getMaterial() == Material.air || var17.getMaterial() == Material.leaves || var17.getMaterial() == Material.vine)) {
                                this.func_175905_a(worldIn, var16, Blocks.leaves, this.metaLeaves);
                            }
                            ++var14;
                        }
                        ++var12;
                    }
                    ++var9;
                }
                var9 = 0;
                while (var9 < var4) {
                    Block var21 = worldIn.getBlockState(p_180709_3_.offsetUp(var9)).getBlock();
                    if (var21.getMaterial() == Material.air || var21.getMaterial() == Material.leaves || var21.getMaterial() == Material.vine) {
                        this.func_175905_a(worldIn, p_180709_3_.offsetUp(var9), Blocks.log, this.metaWood);
                        if (this.vinesGrow && var9 > 0) {
                            if (p_180709_2_.nextInt(3) > 0 && worldIn.isAirBlock(p_180709_3_.add(-1, var9, 0))) {
                                this.func_175905_a(worldIn, p_180709_3_.add(-1, var9, 0), Blocks.vine, BlockVine.field_176275_S);
                            }
                            if (p_180709_2_.nextInt(3) > 0 && worldIn.isAirBlock(p_180709_3_.add(1, var9, 0))) {
                                this.func_175905_a(worldIn, p_180709_3_.add(1, var9, 0), Blocks.vine, BlockVine.field_176271_T);
                            }
                            if (p_180709_2_.nextInt(3) > 0 && worldIn.isAirBlock(p_180709_3_.add(0, var9, -1))) {
                                this.func_175905_a(worldIn, p_180709_3_.add(0, var9, -1), Blocks.vine, BlockVine.field_176272_Q);
                            }
                            if (p_180709_2_.nextInt(3) > 0 && worldIn.isAirBlock(p_180709_3_.add(0, var9, 1))) {
                                this.func_175905_a(worldIn, p_180709_3_.add(0, var9, 1), Blocks.vine, BlockVine.field_176276_R);
                            }
                        }
                    }
                    ++var9;
                }
                if (this.vinesGrow) {
                    var9 = p_180709_3_.getY() - 3 + var4;
                    while (var9 <= p_180709_3_.getY() + var4) {
                        var10 = var9 - (p_180709_3_.getY() + var4);
                        var11 = 2 - var10 / 2;
                        var12 = p_180709_3_.getX() - var11;
                        while (var12 <= p_180709_3_.getX() + var11) {
                            var13 = p_180709_3_.getZ() - var11;
                            while (var13 <= p_180709_3_.getZ() + var11) {
                                BlockPos var23 = new BlockPos(var12, var9, var13);
                                if (worldIn.getBlockState(var23).getBlock().getMaterial() == Material.leaves) {
                                    BlockPos var24 = var23.offsetWest();
                                    var16 = var23.offsetEast();
                                    BlockPos var25 = var23.offsetNorth();
                                    BlockPos var18 = var23.offsetSouth();
                                    if (p_180709_2_.nextInt(4) == 0 && worldIn.getBlockState(var24).getBlock().getMaterial() == Material.air) {
                                        this.func_175923_a(worldIn, var24, BlockVine.field_176275_S);
                                    }
                                    if (p_180709_2_.nextInt(4) == 0 && worldIn.getBlockState(var16).getBlock().getMaterial() == Material.air) {
                                        this.func_175923_a(worldIn, var16, BlockVine.field_176271_T);
                                    }
                                    if (p_180709_2_.nextInt(4) == 0 && worldIn.getBlockState(var25).getBlock().getMaterial() == Material.air) {
                                        this.func_175923_a(worldIn, var25, BlockVine.field_176272_Q);
                                    }
                                    if (p_180709_2_.nextInt(4) == 0 && worldIn.getBlockState(var18).getBlock().getMaterial() == Material.air) {
                                        this.func_175923_a(worldIn, var18, BlockVine.field_176276_R);
                                    }
                                }
                                ++var13;
                            }
                            ++var12;
                        }
                        ++var9;
                    }
                    if (p_180709_2_.nextInt(5) == 0 && var4 > 5) {
                        var9 = 0;
                        while (var9 < 2) {
                            var10 = 0;
                            while (var10 < 4) {
                                if (p_180709_2_.nextInt(4 - var9) == 0) {
                                    var11 = p_180709_2_.nextInt(3);
                                    EnumFacing var22 = EnumFacing.getHorizontal(var10).getOpposite();
                                    this.func_175905_a(worldIn, p_180709_3_.add(var22.getFrontOffsetX(), var4 - 5 + var9, var22.getFrontOffsetZ()), Blocks.cocoa, var11 << 2 | EnumFacing.getHorizontal(var10).getHorizontalIndex());
                                }
                                ++var10;
                            }
                            ++var9;
                        }
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    private void func_175923_a(World worldIn, BlockPos p_175923_2_, int p_175923_3_) {
        this.func_175905_a(worldIn, p_175923_2_, Blocks.vine, p_175923_3_);
        int var4 = 4;
        p_175923_2_ = p_175923_2_.offsetDown();
        while (worldIn.getBlockState(p_175923_2_).getBlock().getMaterial() == Material.air && var4 > 0) {
            this.func_175905_a(worldIn, p_175923_2_, Blocks.vine, p_175923_3_);
            p_175923_2_ = p_175923_2_.offsetDown();
            --var4;
        }
    }
}

