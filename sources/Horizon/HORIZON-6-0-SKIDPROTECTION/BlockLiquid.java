package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;

public abstract class BlockLiquid extends Block
{
    public static final PropertyInteger à¢;
    private static final String Õ = "CL_00000265";
    
    static {
        à¢ = PropertyInteger.HorizonCode_Horizon_È("level", 0, 15);
    }
    
    protected BlockLiquid(final Material p_i45413_1_) {
        super(p_i45413_1_);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockLiquid.à¢, 0));
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess blockAccess, final BlockPos pos) {
        return this.É != Material.áŒŠÆ;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        return (this.É == Material.Ø) ? BiomeColorHelper.Ý(worldIn, pos) : 16777215;
    }
    
    public static float Âµá€(int p_149801_0_) {
        if (p_149801_0_ >= 8) {
            p_149801_0_ = 0;
        }
        return (p_149801_0_ + 1) / 9.0f;
    }
    
    protected int Âµá€(final IBlockAccess p_176362_1_, final BlockPos p_176362_2_) {
        return (int)((p_176362_1_.Â(p_176362_2_).Ý().Ó() == this.É) ? p_176362_1_.Â(p_176362_2_).HorizonCode_Horizon_È(BlockLiquid.à¢) : -1);
    }
    
    protected int Ó(final IBlockAccess p_176366_1_, final BlockPos p_176366_2_) {
        final int var3 = this.Âµá€(p_176366_1_, p_176366_2_);
        return (var3 >= 8) ? 0 : var3;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockState state, final boolean p_176209_2_) {
        return p_176209_2_ && (int)state.HorizonCode_Horizon_È(BlockLiquid.à¢) == 0;
    }
    
    @Override
    public boolean Â(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        final Material var4 = worldIn.Â(pos).Ý().Ó();
        return var4 != this.É && (side == EnumFacing.Â || (var4 != Material.Šáƒ && super.Â(worldIn, pos, side)));
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return worldIn.Â(pos).Ý().Ó() != this.É && (side == EnumFacing.Â || super.HorizonCode_Horizon_È(worldIn, pos, side));
    }
    
    public boolean à(final IBlockAccess p_176364_1_, final BlockPos p_176364_2_) {
        for (int var3 = -1; var3 <= 1; ++var3) {
            for (int var4 = -1; var4 <= 1; ++var4) {
                final IBlockState var5 = p_176364_1_.Â(p_176364_2_.Â(var3, 0, var4));
                final Block var6 = var5.Ý();
                final Material var7 = var6.Ó();
                if (var7 != this.É && !var6.HorizonCode_Horizon_È()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
    }
    
    @Override
    public int ÂµÈ() {
        return 1;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return null;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    protected Vec3 Ø(final IBlockAccess p_180687_1_, final BlockPos p_180687_2_) {
        Vec3 var3 = new Vec3(0.0, 0.0, 0.0);
        final int var4 = this.Ó(p_180687_1_, p_180687_2_);
        for (final EnumFacing var6 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            final BlockPos var7 = p_180687_2_.HorizonCode_Horizon_È(var6);
            int var8 = this.Ó(p_180687_1_, var7);
            if (var8 < 0) {
                if (p_180687_1_.Â(var7).Ý().Ó().Ø­áŒŠá()) {
                    continue;
                }
                var8 = this.Ó(p_180687_1_, var7.Âµá€());
                if (var8 < 0) {
                    continue;
                }
                final int var9 = var8 - (var4 - 8);
                var3 = var3.Â((var7.HorizonCode_Horizon_È() - p_180687_2_.HorizonCode_Horizon_È()) * var9, (var7.Â() - p_180687_2_.Â()) * var9, (var7.Ý() - p_180687_2_.Ý()) * var9);
            }
            else {
                if (var8 < 0) {
                    continue;
                }
                final int var9 = var8 - var4;
                var3 = var3.Â((var7.HorizonCode_Horizon_È() - p_180687_2_.HorizonCode_Horizon_È()) * var9, (var7.Â() - p_180687_2_.Â()) * var9, (var7.Ý() - p_180687_2_.Ý()) * var9);
            }
        }
        if ((int)p_180687_1_.Â(p_180687_2_).HorizonCode_Horizon_È(BlockLiquid.à¢) >= 8) {
            for (final EnumFacing var6 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                final BlockPos var7 = p_180687_2_.HorizonCode_Horizon_È(var6);
                if (this.Â(p_180687_1_, var7, var6) || this.Â(p_180687_1_, var7.Ø­áŒŠá(), var6)) {
                    var3 = var3.HorizonCode_Horizon_È().Â(0.0, -6.0, 0.0);
                    break;
                }
            }
        }
        return var3.HorizonCode_Horizon_È();
    }
    
    @Override
    public Vec3 HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Entity entityIn, final Vec3 motion) {
        return motion.Âµá€(this.Ø((IBlockAccess)worldIn, pos));
    }
    
    @Override
    public int HorizonCode_Horizon_È(final World worldIn) {
        return (this.É == Material.Ø) ? 5 : ((this.É == Material.áŒŠÆ) ? (worldIn.£à.Å() ? 10 : 30) : 0);
    }
    
    @Override
    public int Â(final IBlockAccess worldIn, final BlockPos pos) {
        final int var3 = worldIn.HorizonCode_Horizon_È(pos, 0);
        final int var4 = worldIn.HorizonCode_Horizon_È(pos.Ø­áŒŠá(), 0);
        final int var5 = var3 & 0xFF;
        final int var6 = var4 & 0xFF;
        final int var7 = var3 >> 16 & 0xFF;
        final int var8 = var4 >> 16 & 0xFF;
        return ((var5 > var6) ? var5 : var6) | ((var7 > var8) ? var7 : var8) << 16;
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return (this.É == Material.Ø) ? EnumWorldBlockLayer.Ø­áŒŠá : EnumWorldBlockLayer.HorizonCode_Horizon_È;
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        final double var5 = pos.HorizonCode_Horizon_È();
        final double var6 = pos.Â();
        final double var7 = pos.Ý();
        if (this.É == Material.Ø) {
            final int var8 = (int)state.HorizonCode_Horizon_È(BlockLiquid.à¢);
            if (var8 > 0 && var8 < 8) {
                if (rand.nextInt(64) == 0) {
                    worldIn.HorizonCode_Horizon_È(var5 + 0.5, var6 + 0.5, var7 + 0.5, "liquid.water", rand.nextFloat() * 0.25f + 0.75f, rand.nextFloat() * 1.0f + 0.5f, false);
                }
            }
            else if (rand.nextInt(10) == 0) {
                worldIn.HorizonCode_Horizon_È(EnumParticleTypes.Ø, var5 + rand.nextFloat(), var6 + rand.nextFloat(), var7 + rand.nextFloat(), 0.0, 0.0, 0.0, new int[0]);
            }
        }
        if (this.É == Material.áŒŠÆ && worldIn.Â(pos.Ø­áŒŠá()).Ý().Ó() == Material.HorizonCode_Horizon_È && !worldIn.Â(pos.Ø­áŒŠá()).Ý().Å()) {
            if (rand.nextInt(100) == 0) {
                final double var9 = var5 + rand.nextFloat();
                final double var10 = var6 + this.Ê;
                final double var11 = var7 + rand.nextFloat();
                worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ŒÏ, var9, var10, var11, 0.0, 0.0, 0.0, new int[0]);
                worldIn.HorizonCode_Horizon_È(var9, var10, var11, "liquid.lavapop", 0.2f + rand.nextFloat() * 0.2f, 0.9f + rand.nextFloat() * 0.15f, false);
            }
            if (rand.nextInt(200) == 0) {
                worldIn.HorizonCode_Horizon_È(var5, var6, var7, "liquid.lava", 0.2f + rand.nextFloat() * 0.2f, 0.9f + rand.nextFloat() * 0.15f, false);
            }
        }
        if (rand.nextInt(10) == 0 && World.HorizonCode_Horizon_È(worldIn, pos.Âµá€())) {
            final Material var12 = worldIn.Â(pos.Ý(2)).Ý().Ó();
            if (!var12.Ø­áŒŠá() && !var12.HorizonCode_Horizon_È()) {
                final double var13 = var5 + rand.nextFloat();
                final double var14 = var6 - 1.05;
                final double var15 = var7 + rand.nextFloat();
                if (this.É == Material.Ø) {
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.¥Æ, var13, var14, var15, 0.0, 0.0, 0.0, new int[0]);
                }
                else {
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.Ø­à, var13, var14, var15, 0.0, 0.0, 0.0, new int[0]);
                }
            }
        }
    }
    
    public static double HorizonCode_Horizon_È(final IBlockAccess p_180689_0_, final BlockPos p_180689_1_, final Material p_180689_2_) {
        final Vec3 var3 = HorizonCode_Horizon_È(p_180689_2_).Ø(p_180689_0_, p_180689_1_);
        return (var3.HorizonCode_Horizon_È == 0.0 && var3.Ý == 0.0) ? -1000.0 : (Math.atan2(var3.Ý, var3.HorizonCode_Horizon_È) - 1.5707963267948966);
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Âµá€(worldIn, pos, state);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        this.Âµá€(worldIn, pos, state);
    }
    
    public boolean Âµá€(final World worldIn, final BlockPos p_176365_2_, final IBlockState p_176365_3_) {
        if (this.É == Material.áŒŠÆ) {
            boolean var4 = false;
            for (final EnumFacing var8 : EnumFacing.values()) {
                if (var8 != EnumFacing.HorizonCode_Horizon_È && worldIn.Â(p_176365_2_.HorizonCode_Horizon_È(var8)).Ý().Ó() == Material.Ø) {
                    var4 = true;
                    break;
                }
            }
            if (var4) {
                final Integer var9 = (Integer)p_176365_3_.HorizonCode_Horizon_È(BlockLiquid.à¢);
                if (var9 == 0) {
                    worldIn.Â(p_176365_2_, Blocks.ÇŽá€.¥à());
                    this.áŒŠÆ(worldIn, p_176365_2_);
                    return true;
                }
                if (var9 <= 4) {
                    worldIn.Â(p_176365_2_, Blocks.Ó.¥à());
                    this.áŒŠÆ(worldIn, p_176365_2_);
                    return true;
                }
            }
        }
        return false;
    }
    
    protected void áŒŠÆ(final World worldIn, final BlockPos p_180688_2_) {
        final double var3 = p_180688_2_.HorizonCode_Horizon_È();
        final double var4 = p_180688_2_.Â();
        final double var5 = p_180688_2_.Ý();
        worldIn.HorizonCode_Horizon_È(var3 + 0.5, var4 + 0.5, var5 + 0.5, "random.fizz", 0.5f, 2.6f + (worldIn.Å.nextFloat() - worldIn.Å.nextFloat()) * 0.8f);
        for (int var6 = 0; var6 < 8; ++var6) {
            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ˆÏ­, var3 + Math.random(), var4 + 1.2, var5 + Math.random(), 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockLiquid.à¢, meta);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockLiquid.à¢);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockLiquid.à¢ });
    }
    
    public static BlockDynamicLiquid HorizonCode_Horizon_È(final Material p_176361_0_) {
        if (p_176361_0_ == Material.Ø) {
            return Blocks.áˆºÑ¢Õ;
        }
        if (p_176361_0_ == Material.áŒŠÆ) {
            return Blocks.á;
        }
        throw new IllegalArgumentException("Invalid material");
    }
    
    public static BlockStaticLiquid Â(final Material p_176363_0_) {
        if (p_176363_0_ == Material.Ø) {
            return Blocks.ÂµÈ;
        }
        if (p_176363_0_ == Material.áŒŠÆ) {
            return Blocks.ˆÏ­;
        }
        throw new IllegalArgumentException("Invalid material");
    }
}
