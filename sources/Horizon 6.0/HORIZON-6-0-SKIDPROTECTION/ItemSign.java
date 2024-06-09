package HORIZON-6-0-SKIDPROTECTION;

public class ItemSign extends Item_1028566121
{
    private static final String à = "CL_00000064";
    
    public ItemSign() {
        this.Ø­áŒŠá = 16;
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (side == EnumFacing.HorizonCode_Horizon_È) {
            return false;
        }
        if (!worldIn.Â(pos).Ý().Ó().Â()) {
            return false;
        }
        pos = pos.HorizonCode_Horizon_È(side);
        if (!playerIn.HorizonCode_Horizon_È(pos, side, stack)) {
            return false;
        }
        if (!Blocks.£Õ.Ø­áŒŠá(worldIn, pos)) {
            return false;
        }
        if (worldIn.ŠÄ) {
            return true;
        }
        if (side == EnumFacing.Â) {
            final int var9 = MathHelper.Ý((playerIn.É + 180.0f) * 16.0f / 360.0f + 0.5) & 0xF;
            worldIn.HorizonCode_Horizon_È(pos, Blocks.£Õ.¥à().HorizonCode_Horizon_È(BlockStandingSign.Õ, var9), 3);
        }
        else {
            worldIn.HorizonCode_Horizon_È(pos, Blocks.¥Ä.¥à().HorizonCode_Horizon_È(BlockWallSign.Õ, side), 3);
        }
        --stack.Â;
        final TileEntity var10 = worldIn.HorizonCode_Horizon_È(pos);
        if (var10 instanceof TileEntitySign && !ItemBlock.HorizonCode_Horizon_È(worldIn, pos, stack)) {
            playerIn.HorizonCode_Horizon_È((TileEntitySign)var10);
        }
        return true;
    }
}
