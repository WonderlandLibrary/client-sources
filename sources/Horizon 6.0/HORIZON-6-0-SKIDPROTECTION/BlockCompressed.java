package HORIZON-6-0-SKIDPROTECTION;

public class BlockCompressed extends Block
{
    private final MapColor Õ;
    private static final String à¢ = "CL_00000268";
    
    public BlockCompressed(final MapColor p_i45414_1_) {
        super(Material.Ó);
        this.Õ = p_i45414_1_;
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public MapColor Â(final IBlockState state) {
        return this.Õ;
    }
}
