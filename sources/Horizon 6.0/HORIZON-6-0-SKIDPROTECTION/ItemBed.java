package HORIZON-6-0-SKIDPROTECTION;

public class ItemBed extends Item_1028566121
{
    private static final String à = "CL_00001771";
    
    public ItemBed() {
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        if (side != EnumFacing.Â) {
            return false;
        }
        final IBlockState var9 = worldIn.Â(pos);
        final Block var10 = var9.Ý();
        final boolean var11 = var10.HorizonCode_Horizon_È(worldIn, pos);
        if (!var11) {
            pos = pos.Ø­áŒŠá();
        }
        final int var12 = MathHelper.Ý(playerIn.É * 4.0f / 360.0f + 0.5) & 0x3;
        final EnumFacing var13 = EnumFacing.Â(var12);
        final BlockPos var14 = pos.HorizonCode_Horizon_È(var13);
        final boolean var15 = var10.HorizonCode_Horizon_È(worldIn, var14);
        final boolean var16 = worldIn.Ø­áŒŠá(pos) || var11;
        final boolean var17 = worldIn.Ø­áŒŠá(var14) || var15;
        if (!playerIn.HorizonCode_Horizon_È(pos, side, stack) || !playerIn.HorizonCode_Horizon_È(var14, side, stack)) {
            return false;
        }
        if (var16 && var17 && World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()) && World.HorizonCode_Horizon_È(worldIn, var14.Âµá€())) {
            final int var18 = var13.Ý();
            final IBlockState var19 = Blocks.Ê.¥à().HorizonCode_Horizon_È(BlockBed.à¢, false).HorizonCode_Horizon_È(BlockBed.ŠÂµà, var13).HorizonCode_Horizon_È(BlockBed.Õ, BlockBed.HorizonCode_Horizon_È.Â);
            if (worldIn.HorizonCode_Horizon_È(pos, var19, 3)) {
                final IBlockState var20 = var19.HorizonCode_Horizon_È(BlockBed.Õ, BlockBed.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
                worldIn.HorizonCode_Horizon_È(var14, var20, 3);
            }
            --stack.Â;
            return true;
        }
        return false;
    }
}
