package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockColored extends Block
{
    public static final PropertyEnum Õ;
    private static final String à¢ = "CL_00000217";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("color", EnumDyeColor.class);
    }
    
    public BlockColored(final Material p_i45398_1_) {
        super(p_i45398_1_);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockColored.Õ, EnumDyeColor.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((EnumDyeColor)state.HorizonCode_Horizon_È(BlockColored.Õ)).Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (final EnumDyeColor var7 : EnumDyeColor.values()) {
            list.add(new ItemStack(itemIn, 1, var7.Â()));
        }
    }
    
    @Override
    public MapColor Â(final IBlockState state) {
        return ((EnumDyeColor)state.HorizonCode_Horizon_È(BlockColored.Õ)).Âµá€();
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockColored.Õ, EnumDyeColor.Â(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((EnumDyeColor)state.HorizonCode_Horizon_È(BlockColored.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockColored.Õ });
    }
}
