package HORIZON-6-0-SKIDPROTECTION;

public class WalkNodeProcessor extends NodeProcessor
{
    private boolean Ó;
    private boolean à;
    private boolean Ø;
    private boolean áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00001965";
    
    @Override
    public void HorizonCode_Horizon_È(final IBlockAccess p_176162_1_, final Entity p_176162_2_) {
        super.HorizonCode_Horizon_È(p_176162_1_, p_176162_2_);
        this.áˆºÑ¢Õ = this.Ø;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        super.HorizonCode_Horizon_È();
        this.Ø = this.áˆºÑ¢Õ;
    }
    
    @Override
    public PathPoint HorizonCode_Horizon_È(final Entity p_176161_1_) {
        int var2;
        if (this.áŒŠÆ && p_176161_1_.£ÂµÄ()) {
            var2 = (int)p_176161_1_.£É().Â;
            for (Block var3 = this.HorizonCode_Horizon_È.Â(new BlockPos(MathHelper.Ý(p_176161_1_.ŒÏ), var2, MathHelper.Ý(p_176161_1_.Ê))).Ý(); var3 == Blocks.áˆºÑ¢Õ || var3 == Blocks.ÂµÈ; var3 = this.HorizonCode_Horizon_È.Â(new BlockPos(MathHelper.Ý(p_176161_1_.ŒÏ), var2, MathHelper.Ý(p_176161_1_.Ê))).Ý()) {
                ++var2;
            }
            this.Ø = false;
        }
        else {
            var2 = MathHelper.Ý(p_176161_1_.£É().Â + 0.5);
        }
        return this.HorizonCode_Horizon_È(MathHelper.Ý(p_176161_1_.£É().HorizonCode_Horizon_È), var2, MathHelper.Ý(p_176161_1_.£É().Ý));
    }
    
    @Override
    public PathPoint HorizonCode_Horizon_È(final Entity p_176160_1_, final double p_176160_2_, final double p_176160_4_, final double p_176160_6_) {
        return this.HorizonCode_Horizon_È(MathHelper.Ý(p_176160_2_ - p_176160_1_.áŒŠ / 2.0f), MathHelper.Ý(p_176160_4_), MathHelper.Ý(p_176160_6_ - p_176160_1_.áŒŠ / 2.0f));
    }
    
    @Override
    public int HorizonCode_Horizon_È(final PathPoint[] p_176164_1_, final Entity p_176164_2_, final PathPoint p_176164_3_, final PathPoint p_176164_4_, final float p_176164_5_) {
        int var6 = 0;
        byte var7 = 0;
        if (this.HorizonCode_Horizon_È(p_176164_2_, p_176164_3_.HorizonCode_Horizon_È, p_176164_3_.Â + 1, p_176164_3_.Ý) == 1) {
            var7 = 1;
        }
        final PathPoint var8 = this.HorizonCode_Horizon_È(p_176164_2_, p_176164_3_.HorizonCode_Horizon_È, p_176164_3_.Â, p_176164_3_.Ý + 1, var7);
        final PathPoint var9 = this.HorizonCode_Horizon_È(p_176164_2_, p_176164_3_.HorizonCode_Horizon_È - 1, p_176164_3_.Â, p_176164_3_.Ý, var7);
        final PathPoint var10 = this.HorizonCode_Horizon_È(p_176164_2_, p_176164_3_.HorizonCode_Horizon_È + 1, p_176164_3_.Â, p_176164_3_.Ý, var7);
        final PathPoint var11 = this.HorizonCode_Horizon_È(p_176164_2_, p_176164_3_.HorizonCode_Horizon_È, p_176164_3_.Â, p_176164_3_.Ý - 1, var7);
        if (var8 != null && !var8.áŒŠÆ && var8.HorizonCode_Horizon_È(p_176164_4_) < p_176164_5_) {
            p_176164_1_[var6++] = var8;
        }
        if (var9 != null && !var9.áŒŠÆ && var9.HorizonCode_Horizon_È(p_176164_4_) < p_176164_5_) {
            p_176164_1_[var6++] = var9;
        }
        if (var10 != null && !var10.áŒŠÆ && var10.HorizonCode_Horizon_È(p_176164_4_) < p_176164_5_) {
            p_176164_1_[var6++] = var10;
        }
        if (var11 != null && !var11.áŒŠÆ && var11.HorizonCode_Horizon_È(p_176164_4_) < p_176164_5_) {
            p_176164_1_[var6++] = var11;
        }
        return var6;
    }
    
    private PathPoint HorizonCode_Horizon_È(final Entity p_176171_1_, final int p_176171_2_, int p_176171_3_, final int p_176171_4_, final int p_176171_5_) {
        PathPoint var6 = null;
        final int var7 = this.HorizonCode_Horizon_È(p_176171_1_, p_176171_2_, p_176171_3_, p_176171_4_);
        if (var7 == 2) {
            return this.HorizonCode_Horizon_È(p_176171_2_, p_176171_3_, p_176171_4_);
        }
        if (var7 == 1) {
            var6 = this.HorizonCode_Horizon_È(p_176171_2_, p_176171_3_, p_176171_4_);
        }
        if (var6 == null && p_176171_5_ > 0 && var7 != -3 && var7 != -4 && this.HorizonCode_Horizon_È(p_176171_1_, p_176171_2_, p_176171_3_ + p_176171_5_, p_176171_4_) == 1) {
            var6 = this.HorizonCode_Horizon_È(p_176171_2_, p_176171_3_ + p_176171_5_, p_176171_4_);
            p_176171_3_ += p_176171_5_;
        }
        if (var6 != null) {
            int var8 = 0;
            int var9 = 0;
            while (p_176171_3_ > 0) {
                var9 = this.HorizonCode_Horizon_È(p_176171_1_, p_176171_2_, p_176171_3_ - 1, p_176171_4_);
                if (this.Ø && var9 == -1) {
                    return null;
                }
                if (var9 != 1) {
                    break;
                }
                if (var8++ >= p_176171_1_.ŠÓ()) {
                    return null;
                }
                if (--p_176171_3_ <= 0) {
                    return null;
                }
                var6 = this.HorizonCode_Horizon_È(p_176171_2_, p_176171_3_, p_176171_4_);
            }
            if (var9 == -2) {
                return null;
            }
        }
        return var6;
    }
    
    private int HorizonCode_Horizon_È(final Entity p_176177_1_, final int p_176177_2_, final int p_176177_3_, final int p_176177_4_) {
        return HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_176177_1_, p_176177_2_, p_176177_3_, p_176177_4_, this.Ý, this.Ø­áŒŠá, this.Âµá€, this.Ø, this.à, this.Ó);
    }
    
    public static int HorizonCode_Horizon_È(final IBlockAccess p_176170_0_, final Entity p_176170_1_, final int p_176170_2_, final int p_176170_3_, final int p_176170_4_, final int p_176170_5_, final int p_176170_6_, final int p_176170_7_, final boolean p_176170_8_, final boolean p_176170_9_, final boolean p_176170_10_) {
        boolean var11 = false;
        final BlockPos var12 = new BlockPos(p_176170_1_);
        for (int var13 = p_176170_2_; var13 < p_176170_2_ + p_176170_5_; ++var13) {
            for (int var14 = p_176170_3_; var14 < p_176170_3_ + p_176170_6_; ++var14) {
                for (int var15 = p_176170_4_; var15 < p_176170_4_ + p_176170_7_; ++var15) {
                    final BlockPos var16 = new BlockPos(var13, var14, var15);
                    final Block var17 = p_176170_0_.Â(var16).Ý();
                    if (var17.Ó() != Material.HorizonCode_Horizon_È) {
                        if (var17 != Blocks.áˆºà && var17 != Blocks.áˆºÓ) {
                            if (var17 != Blocks.áˆºÑ¢Õ && var17 != Blocks.ÂµÈ) {
                                if (!p_176170_10_ && var17 instanceof BlockDoor && var17.Ó() == Material.Ø­áŒŠá) {
                                    return 0;
                                }
                            }
                            else {
                                if (p_176170_8_) {
                                    return -1;
                                }
                                var11 = true;
                            }
                        }
                        else {
                            var11 = true;
                        }
                        if (p_176170_1_.Ï­Ðƒà.Â(var16).Ý() instanceof BlockRailBase) {
                            if (!(p_176170_1_.Ï­Ðƒà.Â(var12).Ý() instanceof BlockRailBase) && !(p_176170_1_.Ï­Ðƒà.Â(var12.Âµá€()).Ý() instanceof BlockRailBase)) {
                                return -3;
                            }
                        }
                        else if (!var17.HorizonCode_Horizon_È(p_176170_0_, var16) && (!p_176170_9_ || !(var17 instanceof BlockDoor) || var17.Ó() != Material.Ø­áŒŠá)) {
                            if (var17 instanceof BlockFence || var17 instanceof BlockFenceGate || var17 instanceof BlockWall) {
                                return -3;
                            }
                            if (var17 == Blocks.áˆºà || var17 == Blocks.áˆºÓ) {
                                return -4;
                            }
                            final Material var18 = var17.Ó();
                            if (var18 != Material.áŒŠÆ) {
                                return 0;
                            }
                            if (!p_176170_1_.ÇŽá€()) {
                                return -2;
                            }
                        }
                    }
                }
            }
        }
        return var11 ? 2 : 1;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_176175_1_) {
        this.Ó = p_176175_1_;
    }
    
    public void Â(final boolean p_176172_1_) {
        this.à = p_176172_1_;
    }
    
    public void Ý(final boolean p_176176_1_) {
        this.Ø = p_176176_1_;
    }
    
    public void Ø­áŒŠá(final boolean p_176178_1_) {
        this.áŒŠÆ = p_176178_1_;
    }
    
    public boolean Â() {
        return this.Ó;
    }
    
    public boolean Ý() {
        return this.áŒŠÆ;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Ø;
    }
}
