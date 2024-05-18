package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.List;

public class BlockStainedGlass extends BlockBreakable
{
    public static final PropertyEnum Õ;
    private static final String à¢ = "CL_00000312";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("color", EnumDyeColor.class);
    }
    
    public BlockStainedGlass(final Material p_i45427_1_) {
        super(p_i45427_1_, false);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockStainedGlass.Õ, EnumDyeColor.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((EnumDyeColor)state.HorizonCode_Horizon_È(BlockStainedGlass.Õ)).Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (final EnumDyeColor var7 : EnumDyeColor.values()) {
            list.add(new ItemStack(itemIn, 1, var7.Â()));
        }
    }
    
    @Override
    public MapColor Â(final IBlockState state) {
        return ((EnumDyeColor)state.HorizonCode_Horizon_È(BlockStainedGlass.Õ)).Âµá€();
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ø­áŒŠá;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    @Override
    protected boolean Ñ¢á() {
        return true;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockStainedGlass.Õ, EnumDyeColor.Â(meta));
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!worldIn.ŠÄ) {
            BlockBeacon.áŒŠÆ(worldIn, pos);
        }
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!worldIn.ŠÄ) {
            BlockBeacon.áŒŠÆ(worldIn, pos);
        }
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((EnumDyeColor)state.HorizonCode_Horizon_È(BlockStainedGlass.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockStainedGlass.Õ });
    }
}
