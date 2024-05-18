package HORIZON-6-0-SKIDPROTECTION;

public class ItemDoor extends Item_1028566121
{
    private Block à;
    private static final String Ø = "CL_00000020";
    
    public ItemDoor(final Block p_i45788_1_) {
        this.à = p_i45788_1_;
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (side != EnumFacing.Â) {
            return false;
        }
        final IBlockState var9 = worldIn.Â(pos);
        final Block var10 = var9.Ý();
        if (!var10.HorizonCode_Horizon_È(worldIn, pos)) {
            pos = pos.HorizonCode_Horizon_È(side);
        }
        if (!playerIn.HorizonCode_Horizon_È(pos, side, stack)) {
            return false;
        }
        if (!this.à.Ø­áŒŠá(worldIn, pos)) {
            return false;
        }
        HorizonCode_Horizon_È(worldIn, pos, EnumFacing.HorizonCode_Horizon_È(playerIn.É), this.à);
        --stack.Â;
        return true;
    }
    
    public static void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_179235_1_, final EnumFacing p_179235_2_, final Block p_179235_3_) {
        final BlockPos var4 = p_179235_1_.HorizonCode_Horizon_È(p_179235_2_.Ó());
        final BlockPos var5 = p_179235_1_.HorizonCode_Horizon_È(p_179235_2_.à());
        final int var6 = (worldIn.Â(var5).Ý().Ø() + worldIn.Â(var5.Ø­áŒŠá()).Ý().Ø()) ? 1 : 0;
        final int var7 = (worldIn.Â(var4).Ý().Ø() + worldIn.Â(var4.Ø­áŒŠá()).Ý().Ø()) ? 1 : 0;
        final boolean var8 = worldIn.Â(var5).Ý() == p_179235_3_ || worldIn.Â(var5.Ø­áŒŠá()).Ý() == p_179235_3_;
        final boolean var9 = worldIn.Â(var4).Ý() == p_179235_3_ || worldIn.Â(var4.Ø­áŒŠá()).Ý() == p_179235_3_;
        boolean var10 = false;
        if ((var8 && !var9) || var7 > var6) {
            var10 = true;
        }
        final BlockPos var11 = p_179235_1_.Ø­áŒŠá();
        final IBlockState var12 = p_179235_3_.¥à().HorizonCode_Horizon_È(BlockDoor.Õ, p_179235_2_).HorizonCode_Horizon_È(BlockDoor.ŠÂµà, var10 ? BlockDoor.Â.Â : BlockDoor.Â.HorizonCode_Horizon_È);
        worldIn.HorizonCode_Horizon_È(p_179235_1_, var12.HorizonCode_Horizon_È(BlockDoor.Âµà, BlockDoor.HorizonCode_Horizon_È.Â), 2);
        worldIn.HorizonCode_Horizon_È(var11, var12.HorizonCode_Horizon_È(BlockDoor.Âµà, BlockDoor.HorizonCode_Horizon_È.HorizonCode_Horizon_È), 2);
        worldIn.Â(p_179235_1_, p_179235_3_);
        worldIn.Â(var11, p_179235_3_);
    }
}
