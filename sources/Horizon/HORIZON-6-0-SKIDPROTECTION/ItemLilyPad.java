package HORIZON-6-0-SKIDPROTECTION;

public class ItemLilyPad extends ItemColored
{
    private static final String Ø = "CL_00000074";
    
    public ItemLilyPad(final Block p_i45357_1_) {
        super(p_i45357_1_, false);
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        final MovingObjectPosition var4 = this.HorizonCode_Horizon_È(worldIn, playerIn, true);
        if (var4 == null) {
            return itemStackIn;
        }
        if (var4.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
            final BlockPos var5 = var4.HorizonCode_Horizon_È();
            if (!worldIn.HorizonCode_Horizon_È(playerIn, var5)) {
                return itemStackIn;
            }
            if (!playerIn.HorizonCode_Horizon_È(var5.HorizonCode_Horizon_È(var4.Â), var4.Â, itemStackIn)) {
                return itemStackIn;
            }
            final BlockPos var6 = var5.Ø­áŒŠá();
            final IBlockState var7 = worldIn.Â(var5);
            if (var7.Ý().Ó() == Material.Ø && (int)var7.HorizonCode_Horizon_È(BlockLiquid.à¢) == 0 && worldIn.Ø­áŒŠá(var6)) {
                worldIn.Â(var6, Blocks.Œá.¥à());
                if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                    --itemStackIn.Â;
                }
                playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
            }
        }
        return itemStackIn;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final ItemStack stack, final int renderPass) {
        return Blocks.Œá.Âµá€(Blocks.Œá.Ý(stack.Ø()));
    }
}
