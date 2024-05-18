package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class ItemMonsterPlacer extends Item_1028566121
{
    private static final String à = "CL_00000070";
    
    public ItemMonsterPlacer() {
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(CreativeTabs.Ó);
    }
    
    @Override
    public String à(final ItemStack stack) {
        String var2 = new StringBuilder().append(StatCollector.HorizonCode_Horizon_È(String.valueOf(this.Ø()) + ".name")).toString().trim();
        final String var3 = EntityList.Â(stack.Ø());
        if (var3 != null) {
            var2 = String.valueOf(var2) + " " + StatCollector.HorizonCode_Horizon_È("entity." + var3 + ".name");
        }
        return var2;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final ItemStack stack, final int renderPass) {
        final EntityList.HorizonCode_Horizon_È var3 = EntityList.HorizonCode_Horizon_È.get(stack.Ø());
        return (var3 != null) ? ((renderPass == 0) ? var3.Â : var3.Ý) : 16777215;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        if (!playerIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È(side), side, stack)) {
            return false;
        }
        final IBlockState var9 = worldIn.Â(pos);
        if (var9.Ý() == Blocks.ÇªÓ) {
            final TileEntity var10 = worldIn.HorizonCode_Horizon_È(pos);
            if (var10 instanceof TileEntityMobSpawner) {
                final MobSpawnerBaseLogic var11 = ((TileEntityMobSpawner)var10).Â();
                var11.HorizonCode_Horizon_È(EntityList.Â(stack.Ø()));
                var10.ŠÄ();
                worldIn.áŒŠÆ(pos);
                if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                    --stack.Â;
                }
                return true;
            }
        }
        pos = pos.HorizonCode_Horizon_È(side);
        double var12 = 0.0;
        if (side == EnumFacing.Â && var9 instanceof BlockFence) {
            var12 = 0.5;
        }
        final Entity var13 = HorizonCode_Horizon_È(worldIn, stack.Ø(), pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + var12, pos.Ý() + 0.5);
        if (var13 != null) {
            if (var13 instanceof EntityLivingBase && stack.¥Æ()) {
                var13.à(stack.µà());
            }
            if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                --stack.Â;
            }
        }
        return true;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        if (worldIn.ŠÄ) {
            return itemStackIn;
        }
        final MovingObjectPosition var4 = this.HorizonCode_Horizon_È(worldIn, playerIn, true);
        if (var4 == null) {
            return itemStackIn;
        }
        if (var4.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
            final BlockPos var5 = var4.HorizonCode_Horizon_È();
            if (!worldIn.HorizonCode_Horizon_È(playerIn, var5)) {
                return itemStackIn;
            }
            if (!playerIn.HorizonCode_Horizon_È(var5, var4.Â, itemStackIn)) {
                return itemStackIn;
            }
            if (worldIn.Â(var5).Ý() instanceof BlockLiquid) {
                final Entity var6 = HorizonCode_Horizon_È(worldIn, itemStackIn.Ø(), var5.HorizonCode_Horizon_È() + 0.5, var5.Â() + 0.5, var5.Ý() + 0.5);
                if (var6 != null) {
                    if (var6 instanceof EntityLivingBase && itemStackIn.¥Æ()) {
                        ((EntityLiving)var6).à(itemStackIn.µà());
                    }
                    if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                        --itemStackIn.Â;
                    }
                    playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
                }
            }
        }
        return itemStackIn;
    }
    
    public static Entity HorizonCode_Horizon_È(final World worldIn, final int p_77840_1_, final double p_77840_2_, final double p_77840_4_, final double p_77840_6_) {
        if (!EntityList.HorizonCode_Horizon_È.containsKey(p_77840_1_)) {
            return null;
        }
        Entity var8 = null;
        for (int var9 = 0; var9 < 1; ++var9) {
            var8 = EntityList.HorizonCode_Horizon_È(p_77840_1_, worldIn);
            if (var8 instanceof EntityLivingBase) {
                final EntityLiving var10 = (EntityLiving)var8;
                var8.Â(p_77840_2_, p_77840_4_, p_77840_6_, MathHelper.à(worldIn.Å.nextFloat() * 360.0f), 0.0f);
                var10.ÂµÕ = var10.É;
                var10.¥É = var10.É;
                var10.HorizonCode_Horizon_È(worldIn.Ê(new BlockPos(var10)), null);
                worldIn.HorizonCode_Horizon_È(var8);
                var10.ŠÕ();
            }
        }
        return var8;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List subItems) {
        for (final EntityList.HorizonCode_Horizon_È var5 : EntityList.HorizonCode_Horizon_È.values()) {
            subItems.add(new ItemStack(itemIn, 1, var5.HorizonCode_Horizon_È));
        }
    }
}
