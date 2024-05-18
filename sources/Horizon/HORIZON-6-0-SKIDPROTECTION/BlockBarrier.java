package HORIZON-6-0-SKIDPROTECTION;

public class BlockBarrier extends Block
{
    private static final String Õ = "CL_00002139";
    
    protected BlockBarrier() {
        super(Material.áƒ);
        this.á();
        this.Â(6000001.0f);
        this.ÇŽÉ();
        this.ˆà = true;
    }
    
    @Override
    public int ÂµÈ() {
        return -1;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public float ÇŽÕ() {
        return 1.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
    }
}
