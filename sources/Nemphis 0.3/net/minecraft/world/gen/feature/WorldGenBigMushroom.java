/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBigMushroom
extends WorldGenerator {
    private int mushroomType = -1;
    private static final String __OBFID = "CL_00000415";

    public WorldGenBigMushroom(int p_i2017_1_) {
        super(true);
        this.mushroomType = p_i2017_1_;
    }

    public WorldGenBigMushroom() {
        super(false);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_) {
        var4 = p_180709_2_.nextInt(2);
        if (this.mushroomType >= 0) {
            var4 = this.mushroomType;
        }
        var5 = p_180709_2_.nextInt(3) + 4;
        var6 = true;
        if (p_180709_3_.getY() < 1) return false;
        if (p_180709_3_.getY() + var5 + 1 >= 256) return false;
        var7 = p_180709_3_.getY();
        while (var7 <= p_180709_3_.getY() + 1 + var5) {
            var8 = 3;
            if (var7 <= p_180709_3_.getY() + 3) {
                var8 = 0;
            }
            var9 = p_180709_3_.getX() - var8;
            while (var9 <= p_180709_3_.getX() + var8 && var6) {
                var10 = p_180709_3_.getZ() - var8;
                while (var10 <= p_180709_3_.getZ() + var8 && var6) {
                    if (var7 >= 0 && var7 < 256) {
                        var11 = worldIn.getBlockState(new BlockPos(var9, var7, var10)).getBlock();
                        if (var11.getMaterial() != Material.air && var11.getMaterial() != Material.leaves) {
                            var6 = false;
                        }
                    } else {
                        var6 = false;
                    }
                    ++var10;
                }
                ++var9;
            }
            ++var7;
        }
        if (!var6) {
            return false;
        }
        var15 = worldIn.getBlockState(p_180709_3_.offsetDown()).getBlock();
        if (var15 != Blocks.dirt && var15 != Blocks.grass && var15 != Blocks.mycelium) {
            return false;
        }
        var16 = p_180709_3_.getY() + var5;
        if (var4 == 1) {
            var16 = p_180709_3_.getY() + var5 - 3;
        }
        var9 = var16;
        while (var9 <= p_180709_3_.getY() + var5) {
            var10 = 1;
            if (var9 < p_180709_3_.getY() + var5) {
                ++var10;
            }
            if (var4 == 0) {
                var10 = 3;
            }
            var18 = p_180709_3_.getX() - var10;
            while (var18 <= p_180709_3_.getX() + var10) {
                var12 = p_180709_3_.getZ() - var10;
                while (var12 <= p_180709_3_.getZ() + var10) {
                    var13 = 5;
                    if (var18 == p_180709_3_.getX() - var10) {
                        --var13;
                    }
                    if (var18 == p_180709_3_.getX() + var10) {
                        ++var13;
                    }
                    if (var12 == p_180709_3_.getZ() - var10) {
                        var13 -= 3;
                    }
                    if (var12 == p_180709_3_.getZ() + var10) {
                        var13 += 3;
                    }
                    if (var4 != 0 && var9 >= p_180709_3_.getY() + var5) ** GOTO lbl76
                    if ((var18 == p_180709_3_.getX() - var10 || var18 == p_180709_3_.getX() + var10) && (var12 == p_180709_3_.getZ() - var10 || var12 == p_180709_3_.getZ() + var10)) ** GOTO lbl81
                    if (var18 == p_180709_3_.getX() - (var10 - 1) && var12 == p_180709_3_.getZ() - var10) {
                        var13 = 1;
                    }
                    if (var18 == p_180709_3_.getX() - var10 && var12 == p_180709_3_.getZ() - (var10 - 1)) {
                        var13 = 1;
                    }
                    if (var18 == p_180709_3_.getX() + (var10 - 1) && var12 == p_180709_3_.getZ() - var10) {
                        var13 = 3;
                    }
                    if (var18 == p_180709_3_.getX() + var10 && var12 == p_180709_3_.getZ() - (var10 - 1)) {
                        var13 = 3;
                    }
                    if (var18 == p_180709_3_.getX() - (var10 - 1) && var12 == p_180709_3_.getZ() + var10) {
                        var13 = 7;
                    }
                    if (var18 == p_180709_3_.getX() - var10 && var12 == p_180709_3_.getZ() + (var10 - 1)) {
                        var13 = 7;
                    }
                    if (var18 == p_180709_3_.getX() + (var10 - 1) && var12 == p_180709_3_.getZ() + var10) {
                        var13 = 9;
                    }
                    if (var18 != p_180709_3_.getX() + var10) ** GOTO lbl-1000
                    if (var12 == p_180709_3_.getZ() + (var10 - 1)) {
                        var13 = 9;
                    }
lbl76: // 4 sources:
                    if (var13 == 5 && var9 < p_180709_3_.getY() + var5) {
                        var13 = 0;
                    }
                    if (var13 != 0 || p_180709_3_.getY() >= p_180709_3_.getY() + var5 - 1) lbl-1000: // 2 sources:
                    {
                        if (!worldIn.getBlockState(var14 = new BlockPos(var18, var9, var12)).getBlock().isFullBlock()) {
                            this.func_175905_a(worldIn, var14, Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + var4), var13);
                        }
                    }
lbl81: // 6 sources:
                    ++var12;
                }
                ++var18;
            }
            ++var9;
        }
        var9 = 0;
        while (var9 < var5) {
            var17 = worldIn.getBlockState(p_180709_3_.offsetUp(var9)).getBlock();
            if (!var17.isFullBlock()) {
                this.func_175905_a(worldIn, p_180709_3_.offsetUp(var9), Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + var4), 10);
            }
            ++var9;
        }
        return true;
    }
}

