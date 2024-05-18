package HORIZON-6-0-SKIDPROTECTION;

public class ChunkCache implements IBlockAccess
{
    protected int HorizonCode_Horizon_È;
    protected int Â;
    protected Chunk[][] Ý;
    protected boolean Ø­áŒŠá;
    protected World Âµá€;
    private static final String Ó = "CL_00000155";
    
    public ChunkCache(final World worldIn, final BlockPos p_i45746_2_, final BlockPos p_i45746_3_, final int p_i45746_4_) {
        this.Âµá€ = worldIn;
        this.HorizonCode_Horizon_È = p_i45746_2_.HorizonCode_Horizon_È() - p_i45746_4_ >> 4;
        this.Â = p_i45746_2_.Ý() - p_i45746_4_ >> 4;
        final int var5 = p_i45746_3_.HorizonCode_Horizon_È() + p_i45746_4_ >> 4;
        final int var6 = p_i45746_3_.Ý() + p_i45746_4_ >> 4;
        this.Ý = new Chunk[var5 - this.HorizonCode_Horizon_È + 1][var6 - this.Â + 1];
        this.Ø­áŒŠá = true;
        for (int var7 = this.HorizonCode_Horizon_È; var7 <= var5; ++var7) {
            for (int var8 = this.Â; var8 <= var6; ++var8) {
                this.Ý[var7 - this.HorizonCode_Horizon_È][var8 - this.Â] = worldIn.HorizonCode_Horizon_È(var7, var8);
            }
        }
        for (int var7 = p_i45746_2_.HorizonCode_Horizon_È() >> 4; var7 <= p_i45746_3_.HorizonCode_Horizon_È() >> 4; ++var7) {
            for (int var8 = p_i45746_2_.Ý() >> 4; var8 <= p_i45746_3_.Ý() >> 4; ++var8) {
                final Chunk var9 = this.Ý[var7 - this.HorizonCode_Horizon_È][var8 - this.Â];
                if (var9 != null && !var9.Ý(p_i45746_2_.Â(), p_i45746_3_.Â())) {
                    this.Ø­áŒŠá = false;
                }
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final BlockPos pos) {
        final int var2 = (pos.HorizonCode_Horizon_È() >> 4) - this.HorizonCode_Horizon_È;
        final int var3 = (pos.Ý() >> 4) - this.Â;
        return this.Ý[var2][var3].HorizonCode_Horizon_È(pos, Chunk.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final BlockPos p_175626_1_, final int p_175626_2_) {
        final int var3 = this.Â(EnumSkyBlock.HorizonCode_Horizon_È, p_175626_1_);
        int var4 = this.Â(EnumSkyBlock.Â, p_175626_1_);
        if (var4 < p_175626_2_) {
            var4 = p_175626_2_;
        }
        return var3 << 20 | var4 << 4;
    }
    
    @Override
    public IBlockState Â(final BlockPos pos) {
        if (pos.Â() >= 0 && pos.Â() < 256) {
            final int var2 = (pos.HorizonCode_Horizon_È() >> 4) - this.HorizonCode_Horizon_È;
            final int var3 = (pos.Ý() >> 4) - this.Â;
            if (var2 >= 0 && var2 < this.Ý.length && var3 >= 0 && var3 < this.Ý[var2].length) {
                final Chunk var4 = this.Ý[var2][var3];
                if (var4 != null) {
                    return var4.Ø­áŒŠá(pos);
                }
            }
        }
        return Blocks.Â.¥à();
    }
    
    @Override
    public BiomeGenBase Ý(final BlockPos pos) {
        return this.Âµá€.Ý(pos);
    }
    
    private int Â(final EnumSkyBlock p_175629_1_, final BlockPos p_175629_2_) {
        if (p_175629_1_ == EnumSkyBlock.HorizonCode_Horizon_È && this.Âµá€.£à.Å()) {
            return 0;
        }
        if (p_175629_2_.Â() < 0 || p_175629_2_.Â() >= 256) {
            return p_175629_1_.Ý;
        }
        if (this.Â(p_175629_2_).Ý().Âµá€()) {
            int var3 = 0;
            for (final EnumFacing var7 : EnumFacing.values()) {
                final int var8 = this.HorizonCode_Horizon_È(p_175629_1_, p_175629_2_.HorizonCode_Horizon_È(var7));
                if (var8 > var3) {
                    var3 = var8;
                }
                if (var3 >= 15) {
                    return var3;
                }
            }
            return var3;
        }
        int var3 = (p_175629_2_.HorizonCode_Horizon_È() >> 4) - this.HorizonCode_Horizon_È;
        final int var9 = (p_175629_2_.Ý() >> 4) - this.Â;
        return this.Ý[var3][var9].HorizonCode_Horizon_È(p_175629_1_, p_175629_2_);
    }
    
    @Override
    public boolean Ø­áŒŠá(final BlockPos pos) {
        return this.Â(pos).Ý().Ó() == Material.HorizonCode_Horizon_È;
    }
    
    public int HorizonCode_Horizon_È(final EnumSkyBlock p_175628_1_, final BlockPos p_175628_2_) {
        if (p_175628_2_.Â() >= 0 && p_175628_2_.Â() < 256) {
            final int var3 = (p_175628_2_.HorizonCode_Horizon_È() >> 4) - this.HorizonCode_Horizon_È;
            final int var4 = (p_175628_2_.Ý() >> 4) - this.Â;
            return this.Ý[var3][var4].HorizonCode_Horizon_È(p_175628_1_, p_175628_2_);
        }
        return p_175628_1_.Ý;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final BlockPos pos, final EnumFacing direction) {
        final IBlockState var3 = this.Â(pos);
        return var3.Ý().Â(this, pos, var3, direction);
    }
    
    @Override
    public WorldType s_() {
        return this.Âµá€.s_();
    }
}
