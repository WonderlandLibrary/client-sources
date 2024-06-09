package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class ItemBoat extends Item_1028566121
{
    private static final String à = "CL_00001774";
    
    public ItemBoat() {
        this.Ø­áŒŠá = 1;
        this.HorizonCode_Horizon_È(CreativeTabs.Âµá€);
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        final float var4 = 1.0f;
        final float var5 = playerIn.Õ + (playerIn.áƒ - playerIn.Õ) * var4;
        final float var6 = playerIn.á€ + (playerIn.É - playerIn.á€) * var4;
        final double var7 = playerIn.áŒŠà + (playerIn.ŒÏ - playerIn.áŒŠà) * var4;
        final double var8 = playerIn.ŠÄ + (playerIn.Çªà¢ - playerIn.ŠÄ) * var4 + playerIn.Ðƒáƒ();
        final double var9 = playerIn.Ñ¢á + (playerIn.Ê - playerIn.Ñ¢á) * var4;
        final Vec3 var10 = new Vec3(var7, var8, var9);
        final float var11 = MathHelper.Â(-var6 * 0.017453292f - 3.1415927f);
        final float var12 = MathHelper.HorizonCode_Horizon_È(-var6 * 0.017453292f - 3.1415927f);
        final float var13 = -MathHelper.Â(-var5 * 0.017453292f);
        final float var14 = MathHelper.HorizonCode_Horizon_È(-var5 * 0.017453292f);
        final float var15 = var12 * var13;
        final float var16 = var11 * var13;
        final double var17 = 5.0;
        final Vec3 var18 = var10.Â(var15 * var17, var14 * var17, var16 * var17);
        final MovingObjectPosition var19 = worldIn.HorizonCode_Horizon_È(var10, var18, true);
        if (var19 == null) {
            return itemStackIn;
        }
        final Vec3 var20 = playerIn.Ó(var4);
        boolean var21 = false;
        final float var22 = 1.0f;
        final List var23 = worldIn.Â(playerIn, playerIn.£É().HorizonCode_Horizon_È(var20.HorizonCode_Horizon_È * var17, var20.Â * var17, var20.Ý * var17).Â(var22, var22, var22));
        for (int var24 = 0; var24 < var23.size(); ++var24) {
            final Entity var25 = var23.get(var24);
            if (var25.Ô()) {
                final float var26 = var25.£Ó();
                final AxisAlignedBB var27 = var25.£É().Â(var26, var26, var26);
                if (var27.HorizonCode_Horizon_È(var10)) {
                    var21 = true;
                }
            }
        }
        if (var21) {
            return itemStackIn;
        }
        if (var19.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
            BlockPos var28 = var19.HorizonCode_Horizon_È();
            if (worldIn.Â(var28).Ý() == Blocks.áŒŠá€) {
                var28 = var28.Âµá€();
            }
            final EntityBoat var29 = new EntityBoat(worldIn, var28.HorizonCode_Horizon_È() + 0.5f, var28.Â() + 1.0f, var28.Ý() + 0.5f);
            var29.É = ((MathHelper.Ý(playerIn.É * 4.0f / 360.0f + 0.5) & 0x3) - 1) * 90;
            if (!worldIn.HorizonCode_Horizon_È(var29, var29.£É().Â(-0.1, -0.1, -0.1)).isEmpty()) {
                return itemStackIn;
            }
            if (!worldIn.ŠÄ) {
                worldIn.HorizonCode_Horizon_È(var29);
            }
            if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                --itemStackIn.Â;
            }
            playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
        }
        return itemStackIn;
    }
}
