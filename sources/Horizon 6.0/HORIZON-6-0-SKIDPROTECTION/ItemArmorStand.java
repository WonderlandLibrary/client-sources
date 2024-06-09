package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.List;

public class ItemArmorStand extends Item_1028566121
{
    private static final String à = "CL_00002182";
    
    public ItemArmorStand() {
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (side == EnumFacing.HorizonCode_Horizon_È) {
            return false;
        }
        final boolean var9 = worldIn.Â(pos).Ý().HorizonCode_Horizon_È(worldIn, pos);
        final BlockPos var10 = var9 ? pos : pos.HorizonCode_Horizon_È(side);
        if (!playerIn.HorizonCode_Horizon_È(var10, side, stack)) {
            return false;
        }
        final BlockPos var11 = var10.Ø­áŒŠá();
        boolean var12 = !worldIn.Ø­áŒŠá(var10) && !worldIn.Â(var10).Ý().HorizonCode_Horizon_È(worldIn, var10);
        var12 |= (!worldIn.Ø­áŒŠá(var11) && !worldIn.Â(var11).Ý().HorizonCode_Horizon_È(worldIn, var11));
        if (var12) {
            return false;
        }
        final double var13 = var10.HorizonCode_Horizon_È();
        final double var14 = var10.Â();
        final double var15 = var10.Ý();
        final List var16 = worldIn.Â(null, AxisAlignedBB.HorizonCode_Horizon_È(var13, var14, var15, var13 + 1.0, var14 + 2.0, var15 + 1.0));
        if (var16.size() > 0) {
            return false;
        }
        if (!worldIn.ŠÄ) {
            worldIn.Ø(var10);
            worldIn.Ø(var11);
            final EntityArmorStand var17 = new EntityArmorStand(worldIn, var13 + 0.5, var14, var15 + 0.5);
            final float var18 = MathHelper.Ø­áŒŠá((MathHelper.à(playerIn.É - 180.0f) + 22.5f) / 45.0f) * 45.0f;
            var17.Â(var13 + 0.5, var14, var15 + 0.5, var18, 0.0f);
            this.HorizonCode_Horizon_È(var17, worldIn.Å);
            final NBTTagCompound var19 = stack.Å();
            if (var19 != null && var19.Â("EntityTag", 10)) {
                final NBTTagCompound var20 = new NBTTagCompound();
                var17.Ø­áŒŠá(var20);
                var20.HorizonCode_Horizon_È(var19.ˆÏ­("EntityTag"));
                var17.Ó(var20);
            }
            worldIn.HorizonCode_Horizon_È(var17);
        }
        --stack.Â;
        return true;
    }
    
    private void HorizonCode_Horizon_È(final EntityArmorStand p_179221_1_, final Random p_179221_2_) {
        Rotations var3 = p_179221_1_.Ø­à();
        float var4 = p_179221_2_.nextFloat() * 5.0f;
        final float var5 = p_179221_2_.nextFloat() * 20.0f - 10.0f;
        Rotations var6 = new Rotations(var3.Â() + var4, var3.Ý() + var5, var3.Ø­áŒŠá());
        p_179221_1_.HorizonCode_Horizon_È(var6);
        var3 = p_179221_1_.µÕ();
        var4 = p_179221_2_.nextFloat() * 10.0f - 5.0f;
        var6 = new Rotations(var3.Â(), var3.Ý() + var4, var3.Ø­áŒŠá());
        p_179221_1_.Â(var6);
    }
}
