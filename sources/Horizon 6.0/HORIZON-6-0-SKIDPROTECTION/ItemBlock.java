package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class ItemBlock extends Item_1028566121
{
    protected final Block à;
    private static final String Ø = "CL_00001772";
    
    public ItemBlock(final Block block) {
        this.à = block;
    }
    
    public ItemBlock Ø­áŒŠá(final String unlocalizedName) {
        super.Â(unlocalizedName);
        return this;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final IBlockState var9 = worldIn.Â(pos);
        final Block var10 = var9.Ý();
        if (var10 == Blocks.áŒŠá€ && (int)var9.HorizonCode_Horizon_È(BlockSnow.Õ) < 1) {
            side = EnumFacing.Â;
        }
        else if (!var10.HorizonCode_Horizon_È(worldIn, pos)) {
            pos = pos.HorizonCode_Horizon_È(side);
        }
        if (stack.Â == 0) {
            return false;
        }
        if (!playerIn.HorizonCode_Horizon_È(pos, side, stack)) {
            return false;
        }
        if (pos.Â() == 255 && this.à.Ó().Â()) {
            return false;
        }
        if (worldIn.HorizonCode_Horizon_È(this.à, pos, false, side, null, stack)) {
            final int var11 = this.Ý(stack.Ø());
            IBlockState var12 = this.à.HorizonCode_Horizon_È(worldIn, pos, side, hitX, hitY, hitZ, var11, playerIn);
            if (worldIn.HorizonCode_Horizon_È(pos, var12, 3)) {
                var12 = worldIn.Â(pos);
                if (var12.Ý() == this.à) {
                    HorizonCode_Horizon_È(worldIn, pos, stack);
                    this.à.HorizonCode_Horizon_È(worldIn, pos, var12, playerIn, stack);
                }
                worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5f, pos.Â() + 0.5f, pos.Ý() + 0.5f, this.à.ˆá.Â(), (this.à.ˆá.Ø­áŒŠá() + 1.0f) / 2.0f, this.à.ˆá.Âµá€() * 0.8f);
                --stack.Â;
            }
            return true;
        }
        return false;
    }
    
    public static boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_179224_1_, final ItemStack p_179224_2_) {
        if (p_179224_2_.£á() && p_179224_2_.Å().Â("BlockEntityTag", 10)) {
            final TileEntity var3 = worldIn.HorizonCode_Horizon_È(p_179224_1_);
            if (var3 != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                final NBTTagCompound var5 = (NBTTagCompound)var4.Â();
                var3.Â(var4);
                final NBTTagCompound var6 = (NBTTagCompound)p_179224_2_.Å().HorizonCode_Horizon_È("BlockEntityTag");
                var4.HorizonCode_Horizon_È(var6);
                var4.HorizonCode_Horizon_È("x", p_179224_1_.HorizonCode_Horizon_È());
                var4.HorizonCode_Horizon_È("y", p_179224_1_.Â());
                var4.HorizonCode_Horizon_È("z", p_179224_1_.Ý());
                if (!var4.equals(var5)) {
                    var3.HorizonCode_Horizon_È(var4);
                    var3.ŠÄ();
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean HorizonCode_Horizon_È(final World worldIn, BlockPos p_179222_2_, EnumFacing p_179222_3_, final EntityPlayer p_179222_4_, final ItemStack p_179222_5_) {
        final Block var6 = worldIn.Â(p_179222_2_).Ý();
        if (var6 == Blocks.áŒŠá€) {
            p_179222_3_ = EnumFacing.Â;
        }
        else if (!var6.HorizonCode_Horizon_È(worldIn, p_179222_2_)) {
            p_179222_2_ = p_179222_2_.HorizonCode_Horizon_È(p_179222_3_);
        }
        return worldIn.HorizonCode_Horizon_È(this.à, p_179222_2_, false, p_179222_3_, null, p_179222_5_);
    }
    
    @Override
    public String Â(final ItemStack stack) {
        return this.à.Çªà¢();
    }
    
    @Override
    public String Ø() {
        return this.à.Çªà¢();
    }
    
    @Override
    public CreativeTabs £á() {
        return this.à.É();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List subItems) {
        this.à.HorizonCode_Horizon_È(itemIn, tab, subItems);
    }
    
    public Block ˆà() {
        return this.à;
    }
}
