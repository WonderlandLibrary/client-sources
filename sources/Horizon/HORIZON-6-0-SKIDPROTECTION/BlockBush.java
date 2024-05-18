package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockBush extends Block
{
    private static final String Õ = "CL_00000208";
    
    protected BlockBush(final Material materialIn) {
        super(materialIn);
        this.HorizonCode_Horizon_È(true);
        final float var2 = 0.2f;
        this.HorizonCode_Horizon_È(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, var2 * 3.0f, 0.5f + var2);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    protected BlockBush() {
        this(Material.ÂµÈ);
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return super.Ø­áŒŠá(worldIn, pos) && this.Ý(worldIn.Â(pos.Âµá€()).Ý());
    }
    
    protected boolean Ý(final Block ground) {
        return ground == Blocks.Ø­áŒŠá || ground == Blocks.Âµá€ || ground == Blocks.£Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, neighborBlock);
        this.Âµá€(worldIn, pos, state);
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        this.Âµá€(worldIn, pos, state);
    }
    
    protected void Âµá€(final World worldIn, final BlockPos p_176475_2_, final IBlockState p_176475_3_) {
        if (!this.Ó(worldIn, p_176475_2_, p_176475_3_)) {
            this.HorizonCode_Horizon_È(worldIn, p_176475_2_, p_176475_3_, 0);
            worldIn.HorizonCode_Horizon_È(p_176475_2_, Blocks.Â.¥à(), 3);
        }
    }
    
    public boolean Ó(final World worldIn, final BlockPos p_180671_2_, final IBlockState p_180671_3_) {
        return this.Ý(worldIn.Â(p_180671_2_.Âµá€()).Ý());
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
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
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
}
