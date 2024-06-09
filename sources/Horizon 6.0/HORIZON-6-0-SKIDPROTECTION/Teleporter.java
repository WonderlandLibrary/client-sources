package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;

public class Teleporter
{
    private final WorldServer HorizonCode_Horizon_È;
    private final Random Â;
    private final LongHashMap Ý;
    private final List Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000153";
    
    public Teleporter(final WorldServer worldIn) {
        this.Ý = new LongHashMap();
        this.Ø­áŒŠá = Lists.newArrayList();
        this.HorizonCode_Horizon_È = worldIn;
        this.Â = new Random(worldIn.Æ());
    }
    
    public void HorizonCode_Horizon_È(final Entity p_180266_1_, final float p_180266_2_) {
        if (this.HorizonCode_Horizon_È.£à.µà() != 1) {
            if (!this.Â(p_180266_1_, p_180266_2_)) {
                this.HorizonCode_Horizon_È(p_180266_1_);
                this.Â(p_180266_1_, p_180266_2_);
            }
        }
        else {
            final int var3 = MathHelper.Ý(p_180266_1_.ŒÏ);
            final int var4 = MathHelper.Ý(p_180266_1_.Çªà¢) - 1;
            final int var5 = MathHelper.Ý(p_180266_1_.Ê);
            final byte var6 = 1;
            final byte var7 = 0;
            for (int var8 = -2; var8 <= 2; ++var8) {
                for (int var9 = -2; var9 <= 2; ++var9) {
                    for (int var10 = -1; var10 < 3; ++var10) {
                        final int var11 = var3 + var9 * var6 + var8 * var7;
                        final int var12 = var4 + var10;
                        final int var13 = var5 + var9 * var7 - var8 * var6;
                        final boolean var14 = var10 < 0;
                        this.HorizonCode_Horizon_È.Â(new BlockPos(var11, var12, var13), var14 ? Blocks.ÇŽá€.¥à() : Blocks.Â.¥à());
                    }
                }
            }
            p_180266_1_.Â(var3, var4, var5, p_180266_1_.É, 0.0f);
            final double çžé = 0.0;
            p_180266_1_.ÇŽÕ = çžé;
            p_180266_1_.ˆá = çžé;
            p_180266_1_.ÇŽÉ = çžé;
        }
    }
    
    public boolean Â(final Entity p_180620_1_, final float p_180620_2_) {
        final boolean var3 = true;
        double var4 = -1.0;
        final int var5 = MathHelper.Ý(p_180620_1_.ŒÏ);
        final int var6 = MathHelper.Ý(p_180620_1_.Ê);
        boolean var7 = true;
        Object var8 = BlockPos.HorizonCode_Horizon_È;
        final long var9 = ChunkCoordIntPair.HorizonCode_Horizon_È(var5, var6);
        if (this.Ý.Â(var9)) {
            final HorizonCode_Horizon_È var10 = (HorizonCode_Horizon_È)this.Ý.HorizonCode_Horizon_È(var9);
            var4 = 0.0;
            var8 = var10;
            var10.Â = this.HorizonCode_Horizon_È.Šáƒ();
            var7 = false;
        }
        else {
            final BlockPos var11 = new BlockPos(p_180620_1_);
            for (int var12 = -128; var12 <= 128; ++var12) {
                for (int var13 = -128; var13 <= 128; ++var13) {
                    BlockPos var15;
                    for (BlockPos var14 = var11.Â(var12, this.HorizonCode_Horizon_È.áƒ() - 1 - var11.Â(), var13); var14.Â() >= 0; var14 = var15) {
                        var15 = var14.Âµá€();
                        if (this.HorizonCode_Horizon_È.Â(var14).Ý() == Blocks.µÐƒáƒ) {
                            while (this.HorizonCode_Horizon_È.Â(var15 = var14.Âµá€()).Ý() == Blocks.µÐƒáƒ) {
                                var14 = var15;
                            }
                            final double var16 = var14.Ó(var11);
                            if (var4 < 0.0 || var16 < var4) {
                                var4 = var16;
                                var8 = var14;
                            }
                        }
                    }
                }
            }
        }
        if (var4 >= 0.0) {
            if (var7) {
                this.Ý.HorizonCode_Horizon_È(var9, new HorizonCode_Horizon_È((BlockPos)var8, this.HorizonCode_Horizon_È.Šáƒ()));
                this.Ø­áŒŠá.add(var9);
            }
            double var17 = ((BlockPos)var8).HorizonCode_Horizon_È() + 0.5;
            double var18 = ((BlockPos)var8).Â() + 0.5;
            double var19 = ((BlockPos)var8).Ý() + 0.5;
            EnumFacing var20 = null;
            if (this.HorizonCode_Horizon_È.Â(((BlockPos)var8).Ø()).Ý() == Blocks.µÐƒáƒ) {
                var20 = EnumFacing.Ý;
            }
            if (this.HorizonCode_Horizon_È.Â(((BlockPos)var8).áŒŠÆ()).Ý() == Blocks.µÐƒáƒ) {
                var20 = EnumFacing.Ø­áŒŠá;
            }
            if (this.HorizonCode_Horizon_È.Â(((BlockPos)var8).Ó()).Ý() == Blocks.µÐƒáƒ) {
                var20 = EnumFacing.Ó;
            }
            if (this.HorizonCode_Horizon_È.Â(((BlockPos)var8).à()).Ý() == Blocks.µÐƒáƒ) {
                var20 = EnumFacing.Âµá€;
            }
            final EnumFacing var21 = EnumFacing.Â(p_180620_1_.ÇŽá());
            if (var20 != null) {
                EnumFacing var22 = var20.à();
                final BlockPos var23 = ((BlockPos)var8).HorizonCode_Horizon_È(var20);
                boolean var24 = this.HorizonCode_Horizon_È(var23);
                boolean var25 = this.HorizonCode_Horizon_È(var23.HorizonCode_Horizon_È(var22));
                if (var25 && var24) {
                    var8 = ((BlockPos)var8).HorizonCode_Horizon_È(var22);
                    var20 = var20.Âµá€();
                    var22 = var22.Âµá€();
                    final BlockPos var26 = ((BlockPos)var8).HorizonCode_Horizon_È(var20);
                    var24 = this.HorizonCode_Horizon_È(var26);
                    var25 = this.HorizonCode_Horizon_È(var26.HorizonCode_Horizon_È(var22));
                }
                float var27 = 0.5f;
                float var28 = 0.5f;
                if (!var25 && var24) {
                    var27 = 1.0f;
                }
                else if (var25 && !var24) {
                    var27 = 0.0f;
                }
                else if (var25) {
                    var28 = 0.0f;
                }
                var17 = ((BlockPos)var8).HorizonCode_Horizon_È() + 0.5;
                var18 = ((BlockPos)var8).Â() + 0.5;
                var19 = ((BlockPos)var8).Ý() + 0.5;
                var17 += var22.Ø() * var27 + var20.Ø() * var28;
                var19 += var22.áˆºÑ¢Õ() * var27 + var20.áˆºÑ¢Õ() * var28;
                float var29 = 0.0f;
                float var30 = 0.0f;
                float var31 = 0.0f;
                float var32 = 0.0f;
                if (var20 == var21) {
                    var29 = 1.0f;
                    var30 = 1.0f;
                }
                else if (var20 == var21.Âµá€()) {
                    var29 = -1.0f;
                    var30 = -1.0f;
                }
                else if (var20 == var21.Ó()) {
                    var31 = 1.0f;
                    var32 = -1.0f;
                }
                else {
                    var31 = -1.0f;
                    var32 = 1.0f;
                }
                final double var33 = p_180620_1_.ÇŽÉ;
                final double var34 = p_180620_1_.ÇŽÕ;
                p_180620_1_.ÇŽÉ = var33 * var29 + var34 * var32;
                p_180620_1_.ÇŽÕ = var33 * var31 + var34 * var30;
                p_180620_1_.É = p_180620_2_ - var21.Ý() * 90 + var20.Ý() * 90;
            }
            else {
                final double çžé = 0.0;
                p_180620_1_.ÇŽÕ = çžé;
                p_180620_1_.ˆá = çžé;
                p_180620_1_.ÇŽÉ = çžé;
            }
            p_180620_1_.Â(var17, var18, var19, p_180620_1_.É, p_180620_1_.áƒ);
            return true;
        }
        return false;
    }
    
    private boolean HorizonCode_Horizon_È(final BlockPos p_180265_1_) {
        return !this.HorizonCode_Horizon_È.Ø­áŒŠá(p_180265_1_) || !this.HorizonCode_Horizon_È.Ø­áŒŠá(p_180265_1_.Ø­áŒŠá());
    }
    
    public boolean HorizonCode_Horizon_È(final Entity p_85188_1_) {
        final byte var2 = 16;
        double var3 = -1.0;
        final int var4 = MathHelper.Ý(p_85188_1_.ŒÏ);
        final int var5 = MathHelper.Ý(p_85188_1_.Çªà¢);
        final int var6 = MathHelper.Ý(p_85188_1_.Ê);
        int var7 = var4;
        int var8 = var5;
        int var9 = var6;
        int var10 = 0;
        final int var11 = this.Â.nextInt(4);
        for (int var12 = var4 - var2; var12 <= var4 + var2; ++var12) {
            final double var13 = var12 + 0.5 - p_85188_1_.ŒÏ;
            for (int var14 = var6 - var2; var14 <= var6 + var2; ++var14) {
                final double var15 = var14 + 0.5 - p_85188_1_.Ê;
            Label_0452:
                for (int var16 = this.HorizonCode_Horizon_È.áƒ() - 1; var16 >= 0; --var16) {
                    if (this.HorizonCode_Horizon_È.Ø­áŒŠá(new BlockPos(var12, var16, var14))) {
                        while (var16 > 0 && this.HorizonCode_Horizon_È.Ø­áŒŠá(new BlockPos(var12, var16 - 1, var14))) {
                            --var16;
                        }
                        for (int var17 = var11; var17 < var11 + 4; ++var17) {
                            int var18 = var17 % 2;
                            int var19 = 1 - var18;
                            if (var17 % 4 >= 2) {
                                var18 = -var18;
                                var19 = -var19;
                            }
                            for (int var20 = 0; var20 < 3; ++var20) {
                                for (int var21 = 0; var21 < 4; ++var21) {
                                    for (int var22 = -1; var22 < 4; ++var22) {
                                        final int var23 = var12 + (var21 - 1) * var18 + var20 * var19;
                                        final int var24 = var16 + var22;
                                        final int var25 = var14 + (var21 - 1) * var19 - var20 * var18;
                                        if (var22 < 0 && !this.HorizonCode_Horizon_È.Â(new BlockPos(var23, var24, var25)).Ý().Ó().Â()) {
                                            continue Label_0452;
                                        }
                                        if (var22 >= 0 && !this.HorizonCode_Horizon_È.Ø­áŒŠá(new BlockPos(var23, var24, var25))) {
                                            continue Label_0452;
                                        }
                                    }
                                }
                            }
                            final double var26 = var16 + 0.5 - p_85188_1_.Çªà¢;
                            final double var27 = var13 * var13 + var26 * var26 + var15 * var15;
                            if (var3 < 0.0 || var27 < var3) {
                                var3 = var27;
                                var7 = var12;
                                var8 = var16;
                                var9 = var14;
                                var10 = var17 % 4;
                            }
                        }
                    }
                }
            }
        }
        if (var3 < 0.0) {
            for (int var12 = var4 - var2; var12 <= var4 + var2; ++var12) {
                final double var13 = var12 + 0.5 - p_85188_1_.ŒÏ;
                for (int var14 = var6 - var2; var14 <= var6 + var2; ++var14) {
                    final double var15 = var14 + 0.5 - p_85188_1_.Ê;
                Label_0838:
                    for (int var16 = this.HorizonCode_Horizon_È.áƒ() - 1; var16 >= 0; --var16) {
                        if (this.HorizonCode_Horizon_È.Ø­áŒŠá(new BlockPos(var12, var16, var14))) {
                            while (var16 > 0 && this.HorizonCode_Horizon_È.Ø­áŒŠá(new BlockPos(var12, var16 - 1, var14))) {
                                --var16;
                            }
                            for (int var17 = var11; var17 < var11 + 2; ++var17) {
                                final int var18 = var17 % 2;
                                final int var19 = 1 - var18;
                                for (int var20 = 0; var20 < 4; ++var20) {
                                    for (int var21 = -1; var21 < 4; ++var21) {
                                        final int var22 = var12 + (var20 - 1) * var18;
                                        final int var23 = var16 + var21;
                                        final int var24 = var14 + (var20 - 1) * var19;
                                        if (var21 < 0 && !this.HorizonCode_Horizon_È.Â(new BlockPos(var22, var23, var24)).Ý().Ó().Â()) {
                                            continue Label_0838;
                                        }
                                        if (var21 >= 0 && !this.HorizonCode_Horizon_È.Ø­áŒŠá(new BlockPos(var22, var23, var24))) {
                                            continue Label_0838;
                                        }
                                    }
                                }
                                final double var26 = var16 + 0.5 - p_85188_1_.Çªà¢;
                                final double var27 = var13 * var13 + var26 * var26 + var15 * var15;
                                if (var3 < 0.0 || var27 < var3) {
                                    var3 = var27;
                                    var7 = var12;
                                    var8 = var16;
                                    var9 = var14;
                                    var10 = var17 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }
        final int var28 = var7;
        int var29 = var8;
        int var14 = var9;
        int var30 = var10 % 2;
        int var31 = 1 - var30;
        if (var10 % 4 >= 2) {
            var30 = -var30;
            var31 = -var31;
        }
        if (var3 < 0.0) {
            var8 = (var29 = MathHelper.HorizonCode_Horizon_È(var8, 70, this.HorizonCode_Horizon_È.áƒ() - 10));
            for (int var16 = -1; var16 <= 1; ++var16) {
                for (int var17 = 1; var17 < 3; ++var17) {
                    for (int var18 = -1; var18 < 3; ++var18) {
                        final int var19 = var28 + (var17 - 1) * var30 + var16 * var31;
                        final int var20 = var29 + var18;
                        final int var21 = var14 + (var17 - 1) * var31 - var16 * var30;
                        final boolean var32 = var18 < 0;
                        this.HorizonCode_Horizon_È.Â(new BlockPos(var19, var20, var21), var32 ? Blocks.ÇŽá€.¥à() : Blocks.Â.¥à());
                    }
                }
            }
        }
        final IBlockState var33 = Blocks.µÐƒáƒ.¥à().HorizonCode_Horizon_È(BlockPortal.Õ, (var30 != 0) ? EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È : EnumFacing.HorizonCode_Horizon_È.Ý);
        for (int var17 = 0; var17 < 4; ++var17) {
            for (int var18 = 0; var18 < 4; ++var18) {
                for (int var19 = -1; var19 < 4; ++var19) {
                    final int var20 = var28 + (var18 - 1) * var30;
                    final int var21 = var29 + var19;
                    final int var22 = var14 + (var18 - 1) * var31;
                    final boolean var34 = var18 == 0 || var18 == 3 || var19 == -1 || var19 == 3;
                    this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new BlockPos(var20, var21, var22), var34 ? Blocks.ÇŽá€.¥à() : var33, 2);
                }
            }
            for (int var18 = 0; var18 < 4; ++var18) {
                for (int var19 = -1; var19 < 4; ++var19) {
                    final int var20 = var28 + (var18 - 1) * var30;
                    final int var21 = var29 + var19;
                    final int var22 = var14 + (var18 - 1) * var31;
                    this.HorizonCode_Horizon_È.Â(new BlockPos(var20, var21, var22), this.HorizonCode_Horizon_È.Â(new BlockPos(var20, var21, var22)).Ý());
                }
            }
        }
        return true;
    }
    
    public void HorizonCode_Horizon_È(final long p_85189_1_) {
        if (p_85189_1_ % 100L == 0L) {
            final Iterator var3 = this.Ø­áŒŠá.iterator();
            final long var4 = p_85189_1_ - 600L;
            while (var3.hasNext()) {
                final Long var5 = var3.next();
                final HorizonCode_Horizon_È var6 = (HorizonCode_Horizon_È)this.Ý.HorizonCode_Horizon_È(var5);
                if (var6 == null || var6.Â < var4) {
                    var3.remove();
                    this.Ý.Ø­áŒŠá(var5);
                }
            }
        }
    }
    
    public class HorizonCode_Horizon_È extends BlockPos
    {
        public long Â;
        private static final String Ø­áŒŠá = "CL_00000154";
        
        public HorizonCode_Horizon_È(final BlockPos p_i45747_2_, final long p_i45747_3_) {
            super(p_i45747_2_.HorizonCode_Horizon_È(), p_i45747_2_.Â(), p_i45747_2_.Ý());
            this.Â = p_i45747_3_;
        }
    }
}
