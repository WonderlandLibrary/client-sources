package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockCarpet extends Block
{
    public static final PropertyEnum Õ;
    private static final String à¢ = "CL_00000338";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("color", EnumDyeColor.class);
    }
    
    protected BlockCarpet() {
        super(Material.ˆà);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockCarpet.Õ, EnumDyeColor.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
        this.Âµá€(0);
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
    public void ŠÄ() {
        this.Âµá€(0);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        this.Âµá€(0);
    }
    
    protected void Âµá€(final int meta) {
        final byte var2 = 0;
        final float var3 = 1 * (1 + var2) / 16.0f;
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, var3, 1.0f);
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return super.Ø­áŒŠá(worldIn, pos) && this.áŒŠÆ(worldIn, pos);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        this.Âµá€(worldIn, pos, state);
    }
    
    private boolean Âµá€(final World worldIn, final BlockPos p_176328_2_, final IBlockState p_176328_3_) {
        if (!this.áŒŠÆ(worldIn, p_176328_2_)) {
            this.HorizonCode_Horizon_È(worldIn, p_176328_2_, p_176328_3_, 0);
            worldIn.Ø(p_176328_2_);
            return false;
        }
        return true;
    }
    
    private boolean áŒŠÆ(final World worldIn, final BlockPos p_176329_2_) {
        return !worldIn.Ø­áŒŠá(p_176329_2_.Âµá€());
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return side == EnumFacing.Â || super.HorizonCode_Horizon_È(worldIn, pos, side);
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((EnumDyeColor)state.HorizonCode_Horizon_È(BlockCarpet.Õ)).Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (int var4 = 0; var4 < 16; ++var4) {
            list.add(new ItemStack(itemIn, 1, var4));
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockCarpet.Õ, EnumDyeColor.Â(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((EnumDyeColor)state.HorizonCode_Horizon_È(BlockCarpet.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockCarpet.Õ });
    }
}
