package HORIZON-6-0-SKIDPROTECTION;

public class BlockJukebox extends BlockContainer
{
    public static final PropertyBool Õ;
    private static final String à¢ = "CL_00000260";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("has_record");
    }
    
    protected BlockJukebox() {
        super(Material.Ø­áŒŠá);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockJukebox.Õ, false));
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (state.HorizonCode_Horizon_È(BlockJukebox.Õ)) {
            this.Âµá€(worldIn, pos, state);
            state = state.HorizonCode_Horizon_È(BlockJukebox.Õ, false);
            worldIn.HorizonCode_Horizon_È(pos, state, 2);
            return true;
        }
        return false;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final ItemStack recordStack) {
        if (!worldIn.ŠÄ) {
            final TileEntity var5 = worldIn.HorizonCode_Horizon_È(pos);
            if (var5 instanceof HorizonCode_Horizon_È) {
                ((HorizonCode_Horizon_È)var5).HorizonCode_Horizon_È(new ItemStack(recordStack.HorizonCode_Horizon_È(), 1, recordStack.Ø()));
                worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockJukebox.Õ, true), 2);
            }
        }
    }
    
    private void Âµá€(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!worldIn.ŠÄ) {
            final TileEntity var4 = worldIn.HorizonCode_Horizon_È(pos);
            if (var4 instanceof HorizonCode_Horizon_È) {
                final HorizonCode_Horizon_È var5 = (HorizonCode_Horizon_È)var4;
                final ItemStack var6 = var5.HorizonCode_Horizon_È();
                if (var6 != null) {
                    worldIn.Â(1005, pos, 0);
                    worldIn.HorizonCode_Horizon_È(pos, (String)null);
                    var5.HorizonCode_Horizon_È((ItemStack)null);
                    final float var7 = 0.7f;
                    final double var8 = worldIn.Å.nextFloat() * var7 + (1.0f - var7) * 0.5;
                    final double var9 = worldIn.Å.nextFloat() * var7 + (1.0f - var7) * 0.2 + 0.6;
                    final double var10 = worldIn.Å.nextFloat() * var7 + (1.0f - var7) * 0.5;
                    final ItemStack var11 = var6.áˆºÑ¢Õ();
                    final EntityItem var12 = new EntityItem(worldIn, pos.HorizonCode_Horizon_È() + var8, pos.Â() + var9, pos.Ý() + var10, var11);
                    var12.ˆà();
                    worldIn.HorizonCode_Horizon_È(var12);
                }
            }
        }
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Âµá€(worldIn, pos, state);
        super.Ø­áŒŠá(worldIn, pos, state);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        if (!worldIn.ŠÄ) {
            super.HorizonCode_Horizon_È(worldIn, pos, state, chance, 0);
        }
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new HorizonCode_Horizon_È();
    }
    
    @Override
    public boolean Õ() {
        return true;
    }
    
    @Override
    public int Ø(final World worldIn, final BlockPos pos) {
        final TileEntity var3 = worldIn.HorizonCode_Horizon_È(pos);
        if (var3 instanceof HorizonCode_Horizon_È) {
            final ItemStack var4 = ((HorizonCode_Horizon_È)var3).HorizonCode_Horizon_È();
            if (var4 != null) {
                return Item_1028566121.HorizonCode_Horizon_È(var4.HorizonCode_Horizon_È()) + 1 - Item_1028566121.HorizonCode_Horizon_È(Items.áˆºÉ);
            }
        }
        return 0;
    }
    
    @Override
    public int ÂµÈ() {
        return 3;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockJukebox.Õ, meta > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((boolean)state.HorizonCode_Horizon_È(BlockJukebox.Õ)) ? 1 : 0;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockJukebox.Õ });
    }
    
    public static class HorizonCode_Horizon_È extends TileEntity
    {
        private ItemStack Âµá€;
        private static final String Ó = "CL_00000261";
        
        @Override
        public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
            super.HorizonCode_Horizon_È(compound);
            if (compound.Â("RecordItem", 10)) {
                this.HorizonCode_Horizon_È(ItemStack.HorizonCode_Horizon_È(compound.ˆÏ­("RecordItem")));
            }
            else if (compound.Ó("Record") > 0) {
                this.HorizonCode_Horizon_È(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(compound.Ó("Record")), 1, 0));
            }
        }
        
        @Override
        public void Â(final NBTTagCompound compound) {
            super.Â(compound);
            if (this.HorizonCode_Horizon_È() != null) {
                compound.HorizonCode_Horizon_È("RecordItem", this.HorizonCode_Horizon_È().Â(new NBTTagCompound()));
            }
        }
        
        public ItemStack HorizonCode_Horizon_È() {
            return this.Âµá€;
        }
        
        public void HorizonCode_Horizon_È(final ItemStack recordStack) {
            this.Âµá€ = recordStack;
            this.ŠÄ();
        }
    }
}
