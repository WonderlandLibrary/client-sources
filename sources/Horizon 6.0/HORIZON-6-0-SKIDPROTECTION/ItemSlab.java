package HORIZON-6-0-SKIDPROTECTION;

public class ItemSlab extends ItemBlock
{
    private final BlockSlab Ø;
    private final BlockSlab áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000071";
    
    public ItemSlab(final Block p_i45782_1_, final BlockSlab p_i45782_2_, final BlockSlab p_i45782_3_) {
        super(p_i45782_1_);
        this.Ø = p_i45782_2_;
        this.áŒŠÆ = p_i45782_3_;
        this.Ø­áŒŠá(0);
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public int Ý(final int damage) {
        return damage;
    }
    
    @Override
    public String Â(final ItemStack stack) {
        return this.Ø.Âµá€(stack.Ø());
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (stack.Â == 0) {
            return false;
        }
        if (!playerIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È(side), side, stack)) {
            return false;
        }
        final Object var9 = this.Ø.HorizonCode_Horizon_È(stack);
        final IBlockState var10 = worldIn.Â(pos);
        if (var10.Ý() == this.Ø) {
            final IProperty var11 = this.Ø.áŠ();
            final Comparable var12 = var10.HorizonCode_Horizon_È(var11);
            final BlockSlab.HorizonCode_Horizon_È var13 = (BlockSlab.HorizonCode_Horizon_È)var10.HorizonCode_Horizon_È(BlockSlab.Õ);
            if (((side == EnumFacing.Â && var13 == BlockSlab.HorizonCode_Horizon_È.Â) || (side == EnumFacing.HorizonCode_Horizon_È && var13 == BlockSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È)) && var12 == var9) {
                final IBlockState var14 = this.áŒŠÆ.¥à().HorizonCode_Horizon_È(var11, var12);
                if (worldIn.Â(this.áŒŠÆ.HorizonCode_Horizon_È(worldIn, pos, var14)) && worldIn.HorizonCode_Horizon_È(pos, var14, 3)) {
                    worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5f, pos.Â() + 0.5f, pos.Ý() + 0.5f, this.áŒŠÆ.ˆá.Â(), (this.áŒŠÆ.ˆá.Ø­áŒŠá() + 1.0f) / 2.0f, this.áŒŠÆ.ˆá.Âµá€() * 0.8f);
                    --stack.Â;
                }
                return true;
            }
        }
        return this.HorizonCode_Horizon_È(stack, worldIn, pos.HorizonCode_Horizon_È(side), var9) || super.HorizonCode_Horizon_È(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, BlockPos p_179222_2_, final EnumFacing p_179222_3_, final EntityPlayer p_179222_4_, final ItemStack p_179222_5_) {
        final BlockPos var6 = p_179222_2_;
        final IProperty var7 = this.Ø.áŠ();
        final Object var8 = this.Ø.HorizonCode_Horizon_È(p_179222_5_);
        final IBlockState var9 = worldIn.Â(p_179222_2_);
        if (var9.Ý() == this.Ø) {
            final boolean var10 = var9.HorizonCode_Horizon_È(BlockSlab.Õ) == BlockSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
            if (((p_179222_3_ == EnumFacing.Â && !var10) || (p_179222_3_ == EnumFacing.HorizonCode_Horizon_È && var10)) && var8 == var9.HorizonCode_Horizon_È(var7)) {
                return true;
            }
        }
        p_179222_2_ = p_179222_2_.HorizonCode_Horizon_È(p_179222_3_);
        final IBlockState var11 = worldIn.Â(p_179222_2_);
        return (var11.Ý() == this.Ø && var8 == var11.HorizonCode_Horizon_È(var7)) || super.HorizonCode_Horizon_È(worldIn, var6, p_179222_3_, p_179222_4_, p_179222_5_);
    }
    
    private boolean HorizonCode_Horizon_È(final ItemStack p_180615_1_, final World worldIn, final BlockPos p_180615_3_, final Object p_180615_4_) {
        final IBlockState var5 = worldIn.Â(p_180615_3_);
        if (var5.Ý() == this.Ø) {
            final Comparable var6 = var5.HorizonCode_Horizon_È(this.Ø.áŠ());
            if (var6 == p_180615_4_) {
                final IBlockState var7 = this.áŒŠÆ.¥à().HorizonCode_Horizon_È(this.Ø.áŠ(), var6);
                if (worldIn.Â(this.áŒŠÆ.HorizonCode_Horizon_È(worldIn, p_180615_3_, var7)) && worldIn.HorizonCode_Horizon_È(p_180615_3_, var7, 3)) {
                    worldIn.HorizonCode_Horizon_È(p_180615_3_.HorizonCode_Horizon_È() + 0.5f, p_180615_3_.Â() + 0.5f, p_180615_3_.Ý() + 0.5f, this.áŒŠÆ.ˆá.Â(), (this.áŒŠÆ.ˆá.Ø­áŒŠá() + 1.0f) / 2.0f, this.áŒŠÆ.ˆá.Âµá€() * 0.8f);
                    --p_180615_1_.Â;
                }
                return true;
            }
        }
        return false;
    }
}
