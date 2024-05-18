package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public abstract class BlockWoodSlab extends BlockSlab
{
    public static final PropertyEnum à¢;
    private static final String ŠÂµà = "CL_00000337";
    
    static {
        à¢ = PropertyEnum.HorizonCode_Horizon_È("variant", BlockPlanks.HorizonCode_Horizon_È.class);
    }
    
    public BlockWoodSlab() {
        super(Material.Ø­áŒŠá);
        IBlockState var1 = this.á€.Â();
        if (!this.È()) {
            var1 = var1.HorizonCode_Horizon_È(BlockWoodSlab.Õ, HorizonCode_Horizon_È.Â);
        }
        this.Ø(var1.HorizonCode_Horizon_È(BlockWoodSlab.à¢, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.ÇŽÊ);
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.ÇŽÊ);
    }
    
    @Override
    public String Âµá€(final int p_150002_1_) {
        return String.valueOf(super.Çªà¢()) + "." + BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_150002_1_).Ý();
    }
    
    @Override
    public IProperty áŠ() {
        return BlockWoodSlab.à¢;
    }
    
    @Override
    public Object HorizonCode_Horizon_È(final ItemStack p_176553_1_) {
        return BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_176553_1_.Ø() & 0x7);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        if (itemIn != Item_1028566121.HorizonCode_Horizon_È(Blocks.ŒÓ)) {
            for (final BlockPlanks.HorizonCode_Horizon_È var7 : BlockPlanks.HorizonCode_Horizon_È.values()) {
                list.add(new ItemStack(itemIn, 1, var7.Â()));
            }
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        IBlockState var2 = this.¥à().HorizonCode_Horizon_È(BlockWoodSlab.à¢, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta & 0x7));
        if (!this.È()) {
            var2 = var2.HorizonCode_Horizon_È(BlockWoodSlab.Õ, ((meta & 0x8) == 0x0) ? HorizonCode_Horizon_È.Â : HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        }
        return var2;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockWoodSlab.à¢)).Â();
        if (!this.È() && state.HorizonCode_Horizon_È(BlockWoodSlab.Õ) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return this.È() ? new BlockState(this, new IProperty[] { BlockWoodSlab.à¢ }) : new BlockState(this, new IProperty[] { BlockWoodSlab.Õ, BlockWoodSlab.à¢ });
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockWoodSlab.à¢)).Â();
    }
}
