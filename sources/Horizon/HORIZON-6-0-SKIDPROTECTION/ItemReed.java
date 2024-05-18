package HORIZON-6-0-SKIDPROTECTION;

public class ItemReed extends Item_1028566121
{
    private Block à;
    private static final String Ø = "CL_00001773";
    
    public ItemReed(final Block p_i45329_1_) {
        this.à = p_i45329_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final IBlockState var9 = worldIn.Â(pos);
        final Block var10 = var9.Ý();
        if (var10 == Blocks.áŒŠá€ && (int)var9.HorizonCode_Horizon_È(BlockSnow.Õ) < 1) {
            side = EnumFacing.Â;
        }
        else if (!var10.HorizonCode_Horizon_È(worldIn, pos)) {
            pos = pos.HorizonCode_Horizon_È(side);
        }
        if (!playerIn.HorizonCode_Horizon_È(pos, side, stack)) {
            return false;
        }
        if (stack.Â == 0) {
            return false;
        }
        if (worldIn.HorizonCode_Horizon_È(this.à, pos, false, side, null, stack)) {
            IBlockState var11 = this.à.HorizonCode_Horizon_È(worldIn, pos, side, hitX, hitY, hitZ, 0, playerIn);
            if (worldIn.HorizonCode_Horizon_È(pos, var11, 3)) {
                var11 = worldIn.Â(pos);
                if (var11.Ý() == this.à) {
                    ItemBlock.HorizonCode_Horizon_È(worldIn, pos, stack);
                    var11.Ý().HorizonCode_Horizon_È(worldIn, pos, var11, playerIn, stack);
                }
                worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5f, pos.Â() + 0.5f, pos.Ý() + 0.5f, this.à.ˆá.Â(), (this.à.ˆá.Ø­áŒŠá() + 1.0f) / 2.0f, this.à.ˆá.Âµá€() * 0.8f);
                --stack.Â;
                return true;
            }
        }
        return false;
    }
}
