package HORIZON-6-0-SKIDPROTECTION;

public class BlockSourceImpl implements IBlockSource
{
    private final World HorizonCode_Horizon_È;
    private final BlockPos Â;
    private static final String Ý = "CL_00001194";
    
    public BlockSourceImpl(final World worldIn, final BlockPos p_i46023_2_) {
        this.HorizonCode_Horizon_È = worldIn;
        this.Â = p_i46023_2_;
    }
    
    @Override
    public World HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public double Â() {
        return this.Â.HorizonCode_Horizon_È() + 0.5;
    }
    
    @Override
    public double Ý() {
        return this.Â.Â() + 0.5;
    }
    
    @Override
    public double Ø­áŒŠá() {
        return this.Â.Ý() + 0.5;
    }
    
    @Override
    public BlockPos Âµá€() {
        return this.Â;
    }
    
    @Override
    public Block Ó() {
        return this.HorizonCode_Horizon_È.Â(this.Â).Ý();
    }
    
    @Override
    public int à() {
        final IBlockState var1 = this.HorizonCode_Horizon_È.Â(this.Â);
        return var1.Ý().Ý(var1);
    }
    
    @Override
    public TileEntity Ø() {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â);
    }
}
