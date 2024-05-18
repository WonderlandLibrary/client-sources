package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;

public class BlockCactus extends Block
{
    public static final PropertyInteger Õ;
    private static final String à¢ = "CL_00000210";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("age", 0, 15);
    }
    
    protected BlockCactus() {
        super(Material.Ñ¢á);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockCactus.Õ, 0));
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        final BlockPos var5 = pos.Ø­áŒŠá();
        if (worldIn.Ø­áŒŠá(var5)) {
            int var6;
            for (var6 = 1; worldIn.Â(pos.Ý(var6)).Ý() == this; ++var6) {}
            if (var6 < 3) {
                final int var7 = (int)state.HorizonCode_Horizon_È(BlockCactus.Õ);
                if (var7 == 15) {
                    worldIn.Â(var5, this.¥à());
                    final IBlockState var8 = state.HorizonCode_Horizon_È(BlockCactus.Õ, 0);
                    worldIn.HorizonCode_Horizon_È(pos, var8, 4);
                    this.HorizonCode_Horizon_È(worldIn, var5, var8, this);
                }
                else {
                    worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockCactus.Õ, var7 + 1), 4);
                }
            }
        }
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        final float var4 = 0.0625f;
        return new AxisAlignedBB(pos.HorizonCode_Horizon_È() + var4, pos.Â(), pos.Ý() + var4, pos.HorizonCode_Horizon_È() + 1 - var4, pos.Â() + 1 - var4, pos.Ý() + 1 - var4);
    }
    
    @Override
    public AxisAlignedBB Ý(final World worldIn, final BlockPos pos) {
        final float var3 = 0.0625f;
        return new AxisAlignedBB(pos.HorizonCode_Horizon_È() + var3, pos.Â(), pos.Ý() + var3, pos.HorizonCode_Horizon_È() + 1 - var3, pos.Â() + 1, pos.Ý() + 1 - var3);
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
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return super.Ø­áŒŠá(worldIn, pos) && this.áŒŠÆ(worldIn, pos);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!this.áŒŠÆ(worldIn, pos)) {
            worldIn.Â(pos, true);
        }
    }
    
    public boolean áŒŠÆ(final World worldIn, final BlockPos p_176586_2_) {
        for (final EnumFacing var4 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            if (worldIn.Â(p_176586_2_.HorizonCode_Horizon_È(var4)).Ý().Ó().Â()) {
                return false;
            }
        }
        final Block var5 = worldIn.Â(p_176586_2_.Âµá€()).Ý();
        return var5 == Blocks.Ñ¢Ç || var5 == Blocks.£á;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        entityIn.HorizonCode_Horizon_È(DamageSource.Ø, 1.0f);
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockCactus.Õ, meta);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockCactus.Õ);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockCactus.Õ });
    }
}
