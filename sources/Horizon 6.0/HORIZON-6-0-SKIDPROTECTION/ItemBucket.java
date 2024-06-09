package HORIZON-6-0-SKIDPROTECTION;

public class ItemBucket extends Item_1028566121
{
    private Block à;
    private static final String Ø = "CL_00000000";
    
    public ItemBucket(final Block p_i45331_1_) {
        this.Ø­áŒŠá = 1;
        this.à = p_i45331_1_;
        this.HorizonCode_Horizon_È(CreativeTabs.Ó);
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        final boolean var4 = this.à == Blocks.Â;
        final MovingObjectPosition var5 = this.HorizonCode_Horizon_È(worldIn, playerIn, var4);
        if (var5 == null) {
            return itemStackIn;
        }
        if (var5.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
            final BlockPos var6 = var5.HorizonCode_Horizon_È();
            if (!worldIn.HorizonCode_Horizon_È(playerIn, var6)) {
                return itemStackIn;
            }
            if (var4) {
                if (!playerIn.HorizonCode_Horizon_È(var6.HorizonCode_Horizon_È(var5.Â), var5.Â, itemStackIn)) {
                    return itemStackIn;
                }
                final IBlockState var7 = worldIn.Â(var6);
                final Material var8 = var7.Ý().Ó();
                if (var8 == Material.Ø && (int)var7.HorizonCode_Horizon_È(BlockLiquid.à¢) == 0) {
                    worldIn.Ø(var6);
                    playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
                    return this.HorizonCode_Horizon_È(itemStackIn, playerIn, Items.ˆÓ);
                }
                if (var8 == Material.áŒŠÆ && (int)var7.HorizonCode_Horizon_È(BlockLiquid.à¢) == 0) {
                    worldIn.Ø(var6);
                    playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
                    return this.HorizonCode_Horizon_È(itemStackIn, playerIn, Items.¥Ä);
                }
            }
            else {
                if (this.à == Blocks.Â) {
                    return new ItemStack(Items.áŒŠáŠ);
                }
                final BlockPos var9 = var6.HorizonCode_Horizon_È(var5.Â);
                if (!playerIn.HorizonCode_Horizon_È(var9, var5.Â, itemStackIn)) {
                    return itemStackIn;
                }
                if (this.HorizonCode_Horizon_È(worldIn, var9) && !playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                    playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
                    return new ItemStack(Items.áŒŠáŠ);
                }
            }
        }
        return itemStackIn;
    }
    
    private ItemStack HorizonCode_Horizon_È(final ItemStack p_150910_1_, final EntityPlayer p_150910_2_, final Item_1028566121 p_150910_3_) {
        if (p_150910_2_.áˆºáˆºáŠ.Ø­áŒŠá) {
            return p_150910_1_;
        }
        if (--p_150910_1_.Â <= 0) {
            return new ItemStack(p_150910_3_);
        }
        if (!p_150910_2_.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(new ItemStack(p_150910_3_))) {
            p_150910_2_.HorizonCode_Horizon_È(new ItemStack(p_150910_3_, 1, 0), false);
        }
        return p_150910_1_;
    }
    
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_180616_2_) {
        if (this.à == Blocks.Â) {
            return false;
        }
        final Material var3 = worldIn.Â(p_180616_2_).Ý().Ó();
        final boolean var4 = !var3.Â();
        if (!worldIn.Ø­áŒŠá(p_180616_2_) && !var4) {
            return false;
        }
        if (worldIn.£à.£á() && this.à == Blocks.áˆºÑ¢Õ) {
            final int var5 = p_180616_2_.HorizonCode_Horizon_È();
            final int var6 = p_180616_2_.Â();
            final int var7 = p_180616_2_.Ý();
            worldIn.HorizonCode_Horizon_È(var5 + 0.5f, var6 + 0.5f, var7 + 0.5f, "random.fizz", 0.5f, 2.6f + (worldIn.Å.nextFloat() - worldIn.Å.nextFloat()) * 0.8f);
            for (int var8 = 0; var8 < 8; ++var8) {
                worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ˆÏ­, var5 + Math.random(), var6 + Math.random(), var7 + Math.random(), 0.0, 0.0, 0.0, new int[0]);
            }
        }
        else {
            if (!worldIn.ŠÄ && var4 && !var3.HorizonCode_Horizon_È()) {
                worldIn.Â(p_180616_2_, true);
            }
            worldIn.HorizonCode_Horizon_È(p_180616_2_, this.à.¥à(), 3);
        }
        return true;
    }
}
