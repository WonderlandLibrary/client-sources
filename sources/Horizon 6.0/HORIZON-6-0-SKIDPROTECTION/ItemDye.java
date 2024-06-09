package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class ItemDye extends Item_1028566121
{
    public static final int[] à;
    private static final String Ø = "CL_00000022";
    
    static {
        à = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
    }
    
    public ItemDye() {
        this.HorizonCode_Horizon_È(true);
        this.Ø­áŒŠá(0);
        this.HorizonCode_Horizon_È(CreativeTabs.á);
    }
    
    @Override
    public String Â(final ItemStack stack) {
        final int var2 = stack.Ø();
        return String.valueOf(super.Ø()) + "." + EnumDyeColor.HorizonCode_Horizon_È(var2).Ø­áŒŠá();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!playerIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È(side), side, stack)) {
            return false;
        }
        final EnumDyeColor var9 = EnumDyeColor.HorizonCode_Horizon_È(stack.Ø());
        if (var9 == EnumDyeColor.HorizonCode_Horizon_È) {
            if (HorizonCode_Horizon_È(stack, worldIn, pos)) {
                if (!worldIn.ŠÄ) {
                    worldIn.Â(2005, pos, 0);
                }
                return true;
            }
        }
        else if (var9 == EnumDyeColor.ˆÏ­) {
            final IBlockState var10 = worldIn.Â(pos);
            final Block var11 = var10.Ý();
            if (var11 == Blocks.¥Æ && var10.HorizonCode_Horizon_È(BlockPlanks.Õ) == BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá) {
                if (side == EnumFacing.HorizonCode_Horizon_È) {
                    return false;
                }
                if (side == EnumFacing.Â) {
                    return false;
                }
                pos = pos.HorizonCode_Horizon_È(side);
                if (worldIn.Ø­áŒŠá(pos)) {
                    final IBlockState var12 = Blocks.µ.HorizonCode_Horizon_È(worldIn, pos, side, hitX, hitY, hitZ, 0, playerIn);
                    worldIn.HorizonCode_Horizon_È(pos, var12, 2);
                    if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                        --stack.Â;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    public static boolean HorizonCode_Horizon_È(final ItemStack p_179234_0_, final World worldIn, final BlockPos p_179234_2_) {
        final IBlockState var3 = worldIn.Â(p_179234_2_);
        if (var3.Ý() instanceof IGrowable) {
            final IGrowable var4 = (IGrowable)var3.Ý();
            if (var4.HorizonCode_Horizon_È(worldIn, p_179234_2_, var3, worldIn.ŠÄ)) {
                if (!worldIn.ŠÄ) {
                    if (var4.HorizonCode_Horizon_È(worldIn, worldIn.Å, p_179234_2_, var3)) {
                        var4.Â(worldIn, worldIn.Å, p_179234_2_, var3);
                    }
                    --p_179234_0_.Â;
                }
                return true;
            }
        }
        return false;
    }
    
    public static void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_180617_1_, int p_180617_2_) {
        if (p_180617_2_ == 0) {
            p_180617_2_ = 15;
        }
        final Block var3 = worldIn.Â(p_180617_1_).Ý();
        if (var3.Ó() != Material.HorizonCode_Horizon_È) {
            var3.Ý((IBlockAccess)worldIn, p_180617_1_);
            for (int var4 = 0; var4 < p_180617_2_; ++var4) {
                final double var5 = ItemDye.Ý.nextGaussian() * 0.02;
                final double var6 = ItemDye.Ý.nextGaussian() * 0.02;
                final double var7 = ItemDye.Ý.nextGaussian() * 0.02;
                worldIn.HorizonCode_Horizon_È(EnumParticleTypes.Æ, p_180617_1_.HorizonCode_Horizon_È() + ItemDye.Ý.nextFloat(), p_180617_1_.Â() + ItemDye.Ý.nextFloat() * var3.µÕ(), p_180617_1_.Ý() + ItemDye.Ý.nextFloat(), var5, var6, var7, new int[0]);
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final EntityLivingBase target) {
        if (target instanceof EntitySheep) {
            final EntitySheep var4 = (EntitySheep)target;
            final EnumDyeColor var5 = EnumDyeColor.HorizonCode_Horizon_È(stack.Ø());
            if (!var4.¥Ê() && var4.ÐƒÇŽà() != var5) {
                var4.Â(var5);
                --stack.Â;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List subItems) {
        for (int var4 = 0; var4 < 16; ++var4) {
            subItems.add(new ItemStack(itemIn, 1, var4));
        }
    }
}
