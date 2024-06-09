package HORIZON-6-0-SKIDPROTECTION;

public class ItemHoe extends Item_1028566121
{
    protected Item_1028566121.HorizonCode_Horizon_È à;
    private static final String Ø = "CL_00000039";
    
    public ItemHoe(final Item_1028566121.HorizonCode_Horizon_È p_i45343_1_) {
        this.à = p_i45343_1_;
        this.Ø­áŒŠá = 1;
        this.Ø­áŒŠá(p_i45343_1_.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(CreativeTabs.áŒŠÆ);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!playerIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È(side), side, stack)) {
            return false;
        }
        final IBlockState var9 = worldIn.Â(pos);
        final Block var10 = var9.Ý();
        if (side != EnumFacing.HorizonCode_Horizon_È && worldIn.Â(pos.Ø­áŒŠá()).Ý().Ó() == Material.HorizonCode_Horizon_È) {
            if (var10 == Blocks.Ø­áŒŠá) {
                return this.HorizonCode_Horizon_È(stack, playerIn, worldIn, pos, Blocks.£Â.¥à());
            }
            if (var10 == Blocks.Âµá€) {
                switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[((BlockDirt.HorizonCode_Horizon_È)var9.HorizonCode_Horizon_È(BlockDirt.Õ)).ordinal()]) {
                    case 1: {
                        return this.HorizonCode_Horizon_È(stack, playerIn, worldIn, pos, Blocks.£Â.¥à());
                    }
                    case 2: {
                        return this.HorizonCode_Horizon_È(stack, playerIn, worldIn, pos, Blocks.Âµá€.¥à().HorizonCode_Horizon_È(BlockDirt.Õ, BlockDirt.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
                    }
                }
            }
        }
        return false;
    }
    
    protected boolean HorizonCode_Horizon_È(final ItemStack p_179232_1_, final EntityPlayer p_179232_2_, final World worldIn, final BlockPos p_179232_4_, final IBlockState p_179232_5_) {
        worldIn.HorizonCode_Horizon_È(p_179232_4_.HorizonCode_Horizon_È() + 0.5f, p_179232_4_.Â() + 0.5f, p_179232_4_.Ý() + 0.5f, p_179232_5_.Ý().ˆá.Ý(), (p_179232_5_.Ý().ˆá.Ø­áŒŠá() + 1.0f) / 2.0f, p_179232_5_.Ý().ˆá.Âµá€() * 0.8f);
        if (worldIn.ŠÄ) {
            return true;
        }
        worldIn.Â(p_179232_4_, p_179232_5_);
        p_179232_1_.HorizonCode_Horizon_È(1, p_179232_2_);
        return true;
    }
    
    @Override
    public boolean Ó() {
        return true;
    }
    
    public String ˆà() {
        return this.à.toString();
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002179";
        
        static {
            HorizonCode_Horizon_È = new int[BlockDirt.HorizonCode_Horizon_È.values().length];
            try {
                ItemHoe.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockDirt.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                ItemHoe.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockDirt.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
    }
}
