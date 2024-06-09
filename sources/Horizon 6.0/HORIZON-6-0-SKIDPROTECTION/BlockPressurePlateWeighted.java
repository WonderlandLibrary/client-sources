package HORIZON-6-0-SKIDPROTECTION;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate
{
    public static final PropertyInteger Õ;
    private final int à¢;
    private static final String ŠÂµà = "CL_00000334";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("power", 0, 15);
    }
    
    protected BlockPressurePlateWeighted(final String p_i45436_1_, final Material p_i45436_2_, final int p_i45436_3_) {
        super(p_i45436_2_);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockPressurePlateWeighted.Õ, 0));
        this.à¢ = p_i45436_3_;
    }
    
    @Override
    protected int áˆºÑ¢Õ(final World worldIn, final BlockPos pos) {
        final int var3 = Math.min(worldIn.HorizonCode_Horizon_È(Entity.class, this.HorizonCode_Horizon_È(pos)).size(), this.à¢);
        if (var3 > 0) {
            final float var4 = Math.min(this.à¢, var3) / this.à¢;
            return MathHelper.Ó(var4 * 15.0f);
        }
        return 0;
    }
    
    @Override
    protected int áˆºÑ¢Õ(final IBlockState p_176576_1_) {
        return (int)p_176576_1_.HorizonCode_Horizon_È(BlockPressurePlateWeighted.Õ);
    }
    
    @Override
    protected IBlockState HorizonCode_Horizon_È(final IBlockState p_176575_1_, final int p_176575_2_) {
        return p_176575_1_.HorizonCode_Horizon_È(BlockPressurePlateWeighted.Õ, p_176575_2_);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final World worldIn) {
        return 10;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockPressurePlateWeighted.Õ, meta);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockPressurePlateWeighted.Õ);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockPressurePlateWeighted.Õ });
    }
}
