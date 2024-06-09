package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockPistonMoving extends BlockContainer
{
    public static final PropertyDirection Õ;
    public static final PropertyEnum à¢;
    private static final String ŠÂµà = "CL_00000368";
    
    static {
        Õ = BlockPistonExtension.Õ;
        à¢ = BlockPistonExtension.à¢;
    }
    
    public BlockPistonMoving() {
        super(Material.É);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockPistonMoving.Õ, EnumFacing.Ý).HorizonCode_Horizon_È(BlockPistonMoving.à¢, BlockPistonExtension.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.Ý(-1.0f);
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return null;
    }
    
    public static TileEntity HorizonCode_Horizon_È(final IBlockState p_176423_0_, final EnumFacing p_176423_1_, final boolean p_176423_2_, final boolean p_176423_3_) {
        return new TileEntityPiston(p_176423_0_, p_176423_1_, p_176423_2_, p_176423_3_);
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity var4 = worldIn.HorizonCode_Horizon_È(pos);
        if (var4 instanceof TileEntityPiston) {
            ((TileEntityPiston)var4).Ó();
        }
        else {
            super.Ø­áŒŠá(worldIn, pos, state);
        }
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing side) {
        return false;
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state) {
        final BlockPos var4 = pos.HorizonCode_Horizon_È(((EnumFacing)state.HorizonCode_Horizon_È(BlockPistonMoving.Õ)).Âµá€());
        final IBlockState var5 = worldIn.Â(var4);
        if (var5.Ý() instanceof BlockPistonBase && (boolean)var5.HorizonCode_Horizon_È(BlockPistonBase.à¢)) {
            worldIn.Ø(var4);
        }
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!worldIn.ŠÄ && worldIn.HorizonCode_Horizon_È(pos) == null) {
            worldIn.Ø(pos);
            return true;
        }
        return false;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return null;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        if (!worldIn.ŠÄ) {
            final TileEntityPiston var6 = this.Âµá€((IBlockAccess)worldIn, pos);
            if (var6 != null) {
                final IBlockState var7 = var6.Â();
                var7.Ý().HorizonCode_Horizon_È(worldIn, pos, var7, 0);
            }
        }
    }
    
    @Override
    public MovingObjectPosition HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Vec3 start, final Vec3 end) {
        return null;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!worldIn.ŠÄ) {
            worldIn.HorizonCode_Horizon_È(pos);
        }
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntityPiston var4 = this.Âµá€((IBlockAccess)worldIn, pos);
        if (var4 == null) {
            return null;
        }
        float var5 = var4.HorizonCode_Horizon_È(0.0f);
        if (var4.Ý()) {
            var5 = 1.0f - var5;
        }
        return this.HorizonCode_Horizon_È(worldIn, pos, var4.Â(), var5, var4.Ø­áŒŠá());
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final TileEntityPiston var3 = this.Âµá€(access, pos);
        if (var3 != null) {
            final IBlockState var4 = var3.Â();
            final Block var5 = var4.Ý();
            if (var5 == this || var5.Ó() == Material.HorizonCode_Horizon_È) {
                return;
            }
            float var6 = var3.HorizonCode_Horizon_È(0.0f);
            if (var3.Ý()) {
                var6 = 1.0f - var6;
            }
            var5.Ý(access, pos);
            if (var5 == Blocks.Õ || var5 == Blocks.ÇŽÕ) {
                var6 = 0.0f;
            }
            final EnumFacing var7 = var3.Ø­áŒŠá();
            this.ŠÄ = var5.ˆà() - var7.Ø() * var6;
            this.Ñ¢á = var5.Ø­à() - var7.áŒŠÆ() * var6;
            this.ŒÏ = var5.Æ() - var7.áˆºÑ¢Õ() * var6;
            this.Çªà¢ = var5.¥Æ() - var7.Ø() * var6;
            this.Ê = var5.µÕ() - var7.áŒŠÆ() * var6;
            this.ÇŽÉ = var5.Šáƒ() - var7.áˆºÑ¢Õ() * var6;
        }
    }
    
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176424_2_, final IBlockState p_176424_3_, final float p_176424_4_, final EnumFacing p_176424_5_) {
        if (p_176424_3_.Ý() == this || p_176424_3_.Ý().Ó() == Material.HorizonCode_Horizon_È) {
            return null;
        }
        final AxisAlignedBB var6 = p_176424_3_.Ý().HorizonCode_Horizon_È(worldIn, p_176424_2_, p_176424_3_);
        if (var6 == null) {
            return null;
        }
        double var7 = var6.HorizonCode_Horizon_È;
        double var8 = var6.Â;
        double var9 = var6.Ý;
        double var10 = var6.Ø­áŒŠá;
        double var11 = var6.Âµá€;
        double var12 = var6.Ó;
        if (p_176424_5_.Ø() < 0) {
            var7 -= p_176424_5_.Ø() * p_176424_4_;
        }
        else {
            var10 -= p_176424_5_.Ø() * p_176424_4_;
        }
        if (p_176424_5_.áŒŠÆ() < 0) {
            var8 -= p_176424_5_.áŒŠÆ() * p_176424_4_;
        }
        else {
            var11 -= p_176424_5_.áŒŠÆ() * p_176424_4_;
        }
        if (p_176424_5_.áˆºÑ¢Õ() < 0) {
            var9 -= p_176424_5_.áˆºÑ¢Õ() * p_176424_4_;
        }
        else {
            var12 -= p_176424_5_.áˆºÑ¢Õ() * p_176424_4_;
        }
        return new AxisAlignedBB(var7, var8, var9, var10, var11, var12);
    }
    
    private TileEntityPiston Âµá€(final IBlockAccess p_176422_1_, final BlockPos p_176422_2_) {
        final TileEntity var3 = p_176422_1_.HorizonCode_Horizon_È(p_176422_2_);
        return (var3 instanceof TileEntityPiston) ? ((TileEntityPiston)var3) : null;
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return null;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockPistonMoving.Õ, BlockPistonExtension.Âµá€(meta)).HorizonCode_Horizon_È(BlockPistonMoving.à¢, ((meta & 0x8) > 0) ? BlockPistonExtension.HorizonCode_Horizon_È.Â : BlockPistonExtension.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockPistonMoving.Õ)).Â();
        if (state.HorizonCode_Horizon_È(BlockPistonMoving.à¢) == BlockPistonExtension.HorizonCode_Horizon_È.Â) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockPistonMoving.Õ, BlockPistonMoving.à¢ });
    }
}
