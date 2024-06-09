package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Collection;
import java.util.Random;
import java.util.Iterator;
import com.google.common.collect.Sets;
import java.util.Set;

public final class SpawnerAnimals
{
    private static final int HorizonCode_Horizon_È;
    private final Set Â;
    private static final String Ý = "CL_00000152";
    
    static {
        HorizonCode_Horizon_È = (int)Math.pow(17.0, 2.0);
    }
    
    public SpawnerAnimals() {
        this.Â = Sets.newHashSet();
    }
    
    public int HorizonCode_Horizon_È(final WorldServer p_77192_1_, final boolean p_77192_2_, final boolean p_77192_3_, final boolean p_77192_4_) {
        if (!p_77192_2_ && !p_77192_3_) {
            return 0;
        }
        this.Â.clear();
        int var5 = 0;
        for (final EntityPlayer var7 : p_77192_1_.Ó) {
            if (!var7.Ø­áŒŠá()) {
                final int var8 = MathHelper.Ý(var7.ŒÏ / 16.0);
                final int var9 = MathHelper.Ý(var7.Ê / 16.0);
                final byte var10 = 8;
                for (int var11 = -var10; var11 <= var10; ++var11) {
                    for (int var12 = -var10; var12 <= var10; ++var12) {
                        final boolean var13 = var11 == -var10 || var11 == var10 || var12 == -var10 || var12 == var10;
                        final ChunkCoordIntPair var14 = new ChunkCoordIntPair(var11 + var8, var12 + var9);
                        if (!this.Â.contains(var14)) {
                            ++var5;
                            if (!var13 && p_77192_1_.áŠ().HorizonCode_Horizon_È(var14)) {
                                this.Â.add(var14);
                            }
                        }
                    }
                }
            }
        }
        int var15 = 0;
        final BlockPos var16 = p_77192_1_.áŒŠà();
        final EnumCreatureType[] var17 = EnumCreatureType.values();
        final int var9 = var17.length;
        for (final EnumCreatureType var19 : var17) {
            if ((!var19.Ý() || p_77192_3_) && (var19.Ý() || p_77192_2_) && (!var19.Ø­áŒŠá() || p_77192_4_)) {
                final int var12 = p_77192_1_.HorizonCode_Horizon_È(var19.HorizonCode_Horizon_È());
                final int var20 = var19.Â() * var5 / SpawnerAnimals.HorizonCode_Horizon_È;
                if (var12 <= var20) {
                Label_0817:
                    while (true) {
                        for (final ChunkCoordIntPair var22 : this.Â) {
                            final BlockPos var23 = HorizonCode_Horizon_È(p_77192_1_, var22.HorizonCode_Horizon_È, var22.Â);
                            final int var24 = var23.HorizonCode_Horizon_È();
                            final int var25 = var23.Â();
                            final int var26 = var23.Ý();
                            final Block var27 = p_77192_1_.Â(var23).Ý();
                            if (!var27.Ø()) {
                                int var28 = 0;
                                for (int var29 = 0; var29 < 3; ++var29) {
                                    int var30 = var24;
                                    int var31 = var25;
                                    int var32 = var26;
                                    final byte var33 = 6;
                                    BiomeGenBase.Â var34 = null;
                                    IEntityLivingData var35 = null;
                                    for (int var36 = 0; var36 < 4; ++var36) {
                                        var30 += p_77192_1_.Å.nextInt(var33) - p_77192_1_.Å.nextInt(var33);
                                        var31 += p_77192_1_.Å.nextInt(1) - p_77192_1_.Å.nextInt(1);
                                        var32 += p_77192_1_.Å.nextInt(var33) - p_77192_1_.Å.nextInt(var33);
                                        final BlockPos var37 = new BlockPos(var30, var31, var32);
                                        final float var38 = var30 + 0.5f;
                                        final float var39 = var32 + 0.5f;
                                        if (!p_77192_1_.Â(var38, var31, var39, 24.0) && var16.Ý(var38, var31, var39) >= 576.0) {
                                            if (var34 == null) {
                                                var34 = p_77192_1_.HorizonCode_Horizon_È(var19, var37);
                                                if (var34 == null) {
                                                    break;
                                                }
                                            }
                                            if (p_77192_1_.HorizonCode_Horizon_È(var19, var34, var37) && HorizonCode_Horizon_È(EntitySpawnPlacementRegistry.HorizonCode_Horizon_È(var34.HorizonCode_Horizon_È), p_77192_1_, var37)) {
                                                EntityLiving var40;
                                                try {
                                                    var40 = var34.HorizonCode_Horizon_È.getConstructor(World.class).newInstance(p_77192_1_);
                                                }
                                                catch (Exception var41) {
                                                    var41.printStackTrace();
                                                    return var15;
                                                }
                                                var40.Â(var38, var31, var39, p_77192_1_.Å.nextFloat() * 360.0f, 0.0f);
                                                if (var40.µà() && var40.ÐƒÂ()) {
                                                    var35 = var40.HorizonCode_Horizon_È(p_77192_1_.Ê(new BlockPos(var40)), var35);
                                                    if (var40.ÐƒÂ()) {
                                                        ++var28;
                                                        p_77192_1_.HorizonCode_Horizon_È(var40);
                                                    }
                                                    if (var28 >= var40.Ï­áˆºÓ()) {
                                                        continue Label_0817;
                                                    }
                                                }
                                                var15 += var28;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        return var15;
    }
    
    protected static BlockPos HorizonCode_Horizon_È(final World worldIn, final int p_180621_1_, final int p_180621_2_) {
        final Chunk var3 = worldIn.HorizonCode_Horizon_È(p_180621_1_, p_180621_2_);
        final int var4 = p_180621_1_ * 16 + worldIn.Å.nextInt(16);
        final int var5 = p_180621_2_ * 16 + worldIn.Å.nextInt(16);
        final int var6 = MathHelper.Ý(var3.HorizonCode_Horizon_È(new BlockPos(var4, 0, var5)) + 1, 16);
        final int var7 = worldIn.Å.nextInt((var6 > 0) ? var6 : (var3.HorizonCode_Horizon_È() + 16 - 1));
        return new BlockPos(var4, var7, var5);
    }
    
    public static boolean HorizonCode_Horizon_È(final EntityLiving.HorizonCode_Horizon_È p_180267_0_, final World worldIn, final BlockPos p_180267_2_) {
        if (!worldIn.áŠ().HorizonCode_Horizon_È(p_180267_2_)) {
            return false;
        }
        final Block var3 = worldIn.Â(p_180267_2_).Ý();
        if (p_180267_0_ == EntityLiving.HorizonCode_Horizon_È.Ý) {
            return var3.Ó().HorizonCode_Horizon_È() && worldIn.Â(p_180267_2_.Âµá€()).Ý().Ó().HorizonCode_Horizon_È() && !worldIn.Â(p_180267_2_.Ø­áŒŠá()).Ý().Ø();
        }
        final BlockPos var4 = p_180267_2_.Âµá€();
        if (!World.HorizonCode_Horizon_È(worldIn, var4)) {
            return false;
        }
        final Block var5 = worldIn.Â(var4).Ý();
        final boolean var6 = var5 != Blocks.áŒŠÆ && var5 != Blocks.¥ÇªÅ;
        return var6 && !var3.Ø() && !var3.Ó().HorizonCode_Horizon_È() && !worldIn.Â(p_180267_2_.Ø­áŒŠá()).Ý().Ø();
    }
    
    public static void HorizonCode_Horizon_È(final World worldIn, final BiomeGenBase p_77191_1_, final int p_77191_2_, final int p_77191_3_, final int p_77191_4_, final int p_77191_5_, final Random p_77191_6_) {
        final List var7 = p_77191_1_.HorizonCode_Horizon_È(EnumCreatureType.Â);
        if (!var7.isEmpty()) {
            while (p_77191_6_.nextFloat() < p_77191_1_.à()) {
                final BiomeGenBase.Â var8 = (BiomeGenBase.Â)WeightedRandom.HorizonCode_Horizon_È(worldIn.Å, var7);
                final int var9 = var8.Â + p_77191_6_.nextInt(1 + var8.Ø­áŒŠá - var8.Â);
                IEntityLivingData var10 = null;
                int var11 = p_77191_2_ + p_77191_6_.nextInt(p_77191_4_);
                int var12 = p_77191_3_ + p_77191_6_.nextInt(p_77191_5_);
                final int var13 = var11;
                final int var14 = var12;
                for (int var15 = 0; var15 < var9; ++var15) {
                    boolean var16 = false;
                    for (int var17 = 0; !var16 && var17 < 4; ++var17) {
                        final BlockPos var18 = worldIn.ˆà(new BlockPos(var11, 0, var12));
                        if (HorizonCode_Horizon_È(EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È, worldIn, var18)) {
                            EntityLiving var19;
                            try {
                                var19 = var8.HorizonCode_Horizon_È.getConstructor(World.class).newInstance(worldIn);
                            }
                            catch (Exception var20) {
                                var20.printStackTrace();
                                continue;
                            }
                            var19.Â(var11 + 0.5f, var18.Â(), var12 + 0.5f, p_77191_6_.nextFloat() * 360.0f, 0.0f);
                            worldIn.HorizonCode_Horizon_È(var19);
                            var10 = var19.HorizonCode_Horizon_È(worldIn.Ê(new BlockPos(var19)), var10);
                            var16 = true;
                        }
                        for (var11 += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5), var12 += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5); var11 < p_77191_2_ || var11 >= p_77191_2_ + p_77191_4_ || var12 < p_77191_3_ || var12 >= p_77191_3_ + p_77191_4_; var11 = var13 + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5), var12 = var14 + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5)) {}
                    }
                }
            }
        }
    }
}
