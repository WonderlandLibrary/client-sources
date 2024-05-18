package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockPortal extends BlockBreakable
{
    public static final PropertyEnum Õ;
    private static final String à¢ = "CL_00000284";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("axis", EnumFacing.HorizonCode_Horizon_È.class, EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È, EnumFacing.HorizonCode_Horizon_È.Ý);
    }
    
    public BlockPortal() {
        super(Material.ÇŽÉ, false);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockPortal.Õ, EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        super.Â(worldIn, pos, state, rand);
        if (worldIn.£à.Ø­áŒŠá() && worldIn.Çªà¢().Â("doMobSpawning") && rand.nextInt(2000) < worldIn.ŠÂµà().HorizonCode_Horizon_È()) {
            final int var5 = pos.Â();
            BlockPos var6;
            for (var6 = pos; !World.HorizonCode_Horizon_È(worldIn, var6) && var6.Â() > 0; var6 = var6.Âµá€()) {}
            if (var5 > 0 && !worldIn.Â(var6.Ø­áŒŠá()).Ý().Ø()) {
                final Entity var7 = ItemMonsterPlacer.HorizonCode_Horizon_È(worldIn, 57, var6.HorizonCode_Horizon_È() + 0.5, var6.Â() + 1.1, var6.Ý() + 0.5);
                if (var7 != null) {
                    var7.áŒŠáŠ = var7.Ï­Ô();
                }
            }
        }
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final EnumFacing.HorizonCode_Horizon_È var3 = (EnumFacing.HorizonCode_Horizon_È)access.Â(pos).HorizonCode_Horizon_È(BlockPortal.Õ);
        float var4 = 0.125f;
        float var5 = 0.125f;
        if (var3 == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            var4 = 0.5f;
        }
        if (var3 == EnumFacing.HorizonCode_Horizon_È.Ý) {
            var5 = 0.5f;
        }
        this.HorizonCode_Horizon_È(0.5f - var4, 0.0f, 0.5f - var5, 0.5f + var4, 1.0f, 0.5f + var5);
    }
    
    public static int HorizonCode_Horizon_È(final EnumFacing.HorizonCode_Horizon_È p_176549_0_) {
        return (p_176549_0_ == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È) ? 1 : ((p_176549_0_ == EnumFacing.HorizonCode_Horizon_È.Ý) ? 2 : 0);
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    public boolean áŒŠÆ(final World worldIn, final BlockPos p_176548_2_) {
        final HorizonCode_Horizon_È var3 = new HorizonCode_Horizon_È(worldIn, p_176548_2_, EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        if (var3.Â() && var3.Âµá€ == 0) {
            var3.Ý();
            return true;
        }
        final HorizonCode_Horizon_È var4 = new HorizonCode_Horizon_È(worldIn, p_176548_2_, EnumFacing.HorizonCode_Horizon_È.Ý);
        if (var4.Â() && var4.Âµá€ == 0) {
            var4.Ý();
            return true;
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        final EnumFacing.HorizonCode_Horizon_È var5 = (EnumFacing.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockPortal.Õ);
        if (var5 == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            final HorizonCode_Horizon_È var6 = new HorizonCode_Horizon_È(worldIn, pos, EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
            if (!var6.Â() || var6.Âµá€ < var6.Ø * var6.à) {
                worldIn.Â(pos, Blocks.Â.¥à());
            }
        }
        else if (var5 == EnumFacing.HorizonCode_Horizon_È.Ý) {
            final HorizonCode_Horizon_È var6 = new HorizonCode_Horizon_È(worldIn, pos, EnumFacing.HorizonCode_Horizon_È.Ý);
            if (!var6.Â() || var6.Âµá€ < var6.Ø * var6.à) {
                worldIn.Â(pos, Blocks.Â.¥à());
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        EnumFacing.HorizonCode_Horizon_È var4 = null;
        final IBlockState var5 = worldIn.Â(pos);
        if (worldIn.Â(pos).Ý() == this) {
            var4 = (EnumFacing.HorizonCode_Horizon_È)var5.HorizonCode_Horizon_È(BlockPortal.Õ);
            if (var4 == null) {
                return false;
            }
            if (var4 == EnumFacing.HorizonCode_Horizon_È.Ý && side != EnumFacing.Ó && side != EnumFacing.Âµá€) {
                return false;
            }
            if (var4 == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È && side != EnumFacing.Ø­áŒŠá && side != EnumFacing.Ý) {
                return false;
            }
        }
        final boolean var6 = worldIn.Â(pos.Ø()).Ý() == this && worldIn.Â(pos.Ó(2)).Ý() != this;
        final boolean var7 = worldIn.Â(pos.áŒŠÆ()).Ý() == this && worldIn.Â(pos.à(2)).Ý() != this;
        final boolean var8 = worldIn.Â(pos.Ó()).Ý() == this && worldIn.Â(pos.Ø­áŒŠá(2)).Ý() != this;
        final boolean var9 = worldIn.Â(pos.à()).Ý() == this && worldIn.Â(pos.Âµá€(2)).Ý() != this;
        final boolean var10 = var6 || var7 || var4 == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        final boolean var11 = var8 || var9 || var4 == EnumFacing.HorizonCode_Horizon_È.Ý;
        return (var10 && side == EnumFacing.Âµá€) || (var10 && side == EnumFacing.Ó) || (var11 && side == EnumFacing.Ý) || (var11 && side == EnumFacing.Ø­áŒŠá);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ø­áŒŠá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        if (entityIn.Æ == null && entityIn.µÕ == null) {
            entityIn.£Õ();
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (rand.nextInt(100) == 0) {
            worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.5, pos.Ý() + 0.5, "portal.portal", 0.5f, rand.nextFloat() * 0.4f + 0.8f, false);
        }
        for (int var5 = 0; var5 < 4; ++var5) {
            double var6 = pos.HorizonCode_Horizon_È() + rand.nextFloat();
            final double var7 = pos.Â() + rand.nextFloat();
            double var8 = pos.Ý() + rand.nextFloat();
            double var9 = (rand.nextFloat() - 0.5) * 0.5;
            final double var10 = (rand.nextFloat() - 0.5) * 0.5;
            double var11 = (rand.nextFloat() - 0.5) * 0.5;
            final int var12 = rand.nextInt(2) * 2 - 1;
            if (worldIn.Â(pos.Ø()).Ý() != this && worldIn.Â(pos.áŒŠÆ()).Ý() != this) {
                var6 = pos.HorizonCode_Horizon_È() + 0.5 + 0.25 * var12;
                var9 = rand.nextFloat() * 2.0f * var12;
            }
            else {
                var8 = pos.Ý() + 0.5 + 0.25 * var12;
                var11 = rand.nextFloat() * 2.0f * var12;
            }
            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.áŒŠà, var6, var7, var8, var9, var10, var11, new int[0]);
        }
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return null;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockPortal.Õ, ((meta & 0x3) == 0x2) ? EnumFacing.HorizonCode_Horizon_È.Ý : EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return HorizonCode_Horizon_È((EnumFacing.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockPortal.Õ));
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockPortal.Õ });
    }
    
    public static class HorizonCode_Horizon_È
    {
        private final World HorizonCode_Horizon_È;
        private final EnumFacing.HorizonCode_Horizon_È Â;
        private final EnumFacing Ý;
        private final EnumFacing Ø­áŒŠá;
        private int Âµá€;
        private BlockPos Ó;
        private int à;
        private int Ø;
        private static final String áŒŠÆ = "CL_00000285";
        
        public HorizonCode_Horizon_È(final World worldIn, BlockPos p_i45694_2_, final EnumFacing.HorizonCode_Horizon_È p_i45694_3_) {
            this.Âµá€ = 0;
            this.HorizonCode_Horizon_È = worldIn;
            this.Â = p_i45694_3_;
            if (p_i45694_3_ == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                this.Ø­áŒŠá = EnumFacing.Ó;
                this.Ý = EnumFacing.Âµá€;
            }
            else {
                this.Ø­áŒŠá = EnumFacing.Ý;
                this.Ý = EnumFacing.Ø­áŒŠá;
            }
            for (BlockPos var4 = p_i45694_2_; p_i45694_2_.Â() > var4.Â() - 21 && p_i45694_2_.Â() > 0 && this.HorizonCode_Horizon_È(worldIn.Â(p_i45694_2_.Âµá€()).Ý()); p_i45694_2_ = p_i45694_2_.Âµá€()) {}
            final int var5 = this.HorizonCode_Horizon_È(p_i45694_2_, this.Ø­áŒŠá) - 1;
            if (var5 >= 0) {
                this.Ó = p_i45694_2_.HorizonCode_Horizon_È(this.Ø­áŒŠá, var5);
                this.Ø = this.HorizonCode_Horizon_È(this.Ó, this.Ý);
                if (this.Ø < 2 || this.Ø > 21) {
                    this.Ó = null;
                    this.Ø = 0;
                }
            }
            if (this.Ó != null) {
                this.à = this.HorizonCode_Horizon_È();
            }
        }
        
        protected int HorizonCode_Horizon_È(final BlockPos p_180120_1_, final EnumFacing p_180120_2_) {
            int var3;
            for (var3 = 0; var3 < 22; ++var3) {
                final BlockPos var4 = p_180120_1_.HorizonCode_Horizon_È(p_180120_2_, var3);
                if (!this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Â(var4).Ý())) {
                    break;
                }
                if (this.HorizonCode_Horizon_È.Â(var4.Âµá€()).Ý() != Blocks.ÇŽá€) {
                    break;
                }
            }
            final Block var5 = this.HorizonCode_Horizon_È.Â(p_180120_1_.HorizonCode_Horizon_È(p_180120_2_, var3)).Ý();
            return (var5 == Blocks.ÇŽá€) ? var3 : 0;
        }
        
        protected int HorizonCode_Horizon_È() {
            this.à = 0;
        Label_0181:
            while (this.à < 21) {
                for (int var1 = 0; var1 < this.Ø; ++var1) {
                    final BlockPos var2 = this.Ó.HorizonCode_Horizon_È(this.Ý, var1).Â(this.à);
                    Block var3 = this.HorizonCode_Horizon_È.Â(var2).Ý();
                    if (!this.HorizonCode_Horizon_È(var3)) {
                        break Label_0181;
                    }
                    if (var3 == Blocks.µÐƒáƒ) {
                        ++this.Âµá€;
                    }
                    if (var1 == 0) {
                        var3 = this.HorizonCode_Horizon_È.Â(var2.HorizonCode_Horizon_È(this.Ø­áŒŠá)).Ý();
                        if (var3 != Blocks.ÇŽá€) {
                            break Label_0181;
                        }
                    }
                    else if (var1 == this.Ø - 1) {
                        var3 = this.HorizonCode_Horizon_È.Â(var2.HorizonCode_Horizon_È(this.Ý)).Ý();
                        if (var3 != Blocks.ÇŽá€) {
                            break Label_0181;
                        }
                    }
                }
                ++this.à;
            }
            for (int var1 = 0; var1 < this.Ø; ++var1) {
                if (this.HorizonCode_Horizon_È.Â(this.Ó.HorizonCode_Horizon_È(this.Ý, var1).Â(this.à)).Ý() != Blocks.ÇŽá€) {
                    this.à = 0;
                    break;
                }
            }
            if (this.à <= 21 && this.à >= 3) {
                return this.à;
            }
            this.Ó = null;
            this.Ø = 0;
            return this.à = 0;
        }
        
        protected boolean HorizonCode_Horizon_È(final Block p_150857_1_) {
            return p_150857_1_.É == Material.HorizonCode_Horizon_È || p_150857_1_ == Blocks.Ô || p_150857_1_ == Blocks.µÐƒáƒ;
        }
        
        public boolean Â() {
            return this.Ó != null && this.Ø >= 2 && this.Ø <= 21 && this.à >= 3 && this.à <= 21;
        }
        
        public void Ý() {
            for (int var1 = 0; var1 < this.Ø; ++var1) {
                final BlockPos var2 = this.Ó.HorizonCode_Horizon_È(this.Ý, var1);
                for (int var3 = 0; var3 < this.à; ++var3) {
                    this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2.Â(var3), Blocks.µÐƒáƒ.¥à().HorizonCode_Horizon_È(BlockPortal.Õ, this.Â), 2);
                }
            }
        }
    }
}
