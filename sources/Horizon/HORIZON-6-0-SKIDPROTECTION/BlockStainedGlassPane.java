package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockStainedGlassPane extends BlockPane
{
    public static final PropertyEnum Âµà;
    private static final String Ç = "CL_00000313";
    
    static {
        Âµà = PropertyEnum.HorizonCode_Horizon_È("color", EnumDyeColor.class);
    }
    
    public BlockStainedGlassPane() {
        super(Material.¥Æ, false);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockStainedGlassPane.Õ, false).HorizonCode_Horizon_È(BlockStainedGlassPane.à¢, false).HorizonCode_Horizon_È(BlockStainedGlassPane.ŠÂµà, false).HorizonCode_Horizon_È(BlockStainedGlassPane.¥à, false).HorizonCode_Horizon_È(BlockStainedGlassPane.Âµà, EnumDyeColor.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((EnumDyeColor)state.HorizonCode_Horizon_È(BlockStainedGlassPane.Âµà)).Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (int var4 = 0; var4 < EnumDyeColor.values().length; ++var4) {
            list.add(new ItemStack(itemIn, 1, var4));
        }
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ø­áŒŠá;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockStainedGlassPane.Âµà, EnumDyeColor.Â(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((EnumDyeColor)state.HorizonCode_Horizon_È(BlockStainedGlassPane.Âµà)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockStainedGlassPane.Õ, BlockStainedGlassPane.à¢, BlockStainedGlassPane.¥à, BlockStainedGlassPane.ŠÂµà, BlockStainedGlassPane.Âµà });
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
}
