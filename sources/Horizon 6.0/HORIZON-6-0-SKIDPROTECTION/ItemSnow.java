package HORIZON-6-0-SKIDPROTECTION;

public class ItemSnow extends ItemBlock
{
    private static final String Ø = "CL_00000068";
    
    public ItemSnow(final Block p_i45781_1_) {
        super(p_i45781_1_);
        this.Ø­áŒŠá(0);
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (stack.Â == 0) {
            return false;
        }
        if (!playerIn.HorizonCode_Horizon_È(pos, side, stack)) {
            return false;
        }
        IBlockState var9 = worldIn.Â(pos);
        Block var10 = var9.Ý();
        if (var10 != this.à && side != EnumFacing.Â) {
            pos = pos.HorizonCode_Horizon_È(side);
            var9 = worldIn.Â(pos);
            var10 = var9.Ý();
        }
        if (var10 == this.à) {
            final int var11 = (int)var9.HorizonCode_Horizon_È(BlockSnow.Õ);
            if (var11 <= 7) {
                final IBlockState var12 = var9.HorizonCode_Horizon_È(BlockSnow.Õ, var11 + 1);
                if (worldIn.Â(this.à.HorizonCode_Horizon_È(worldIn, pos, var12)) && worldIn.HorizonCode_Horizon_È(pos, var12, 2)) {
                    worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5f, pos.Â() + 0.5f, pos.Ý() + 0.5f, this.à.ˆá.Â(), (this.à.ˆá.Ø­áŒŠá() + 1.0f) / 2.0f, this.à.ˆá.Âµá€() * 0.8f);
                    --stack.Â;
                    return true;
                }
            }
        }
        return super.HorizonCode_Horizon_È(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
    }
    
    @Override
    public int Ý(final int damage) {
        return damage;
    }
}
