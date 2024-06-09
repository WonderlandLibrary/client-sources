package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.base.Predicate;

public class BlockBanner extends BlockContainer
{
    public static final PropertyDirection Õ;
    public static final PropertyInteger à¢;
    private static final String ŠÂµà = "CL_00002143";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)EnumFacing.Ý.HorizonCode_Horizon_È);
        à¢ = PropertyInteger.HorizonCode_Horizon_È("rotation", 0, 15);
    }
    
    protected BlockBanner() {
        super(Material.Ø­áŒŠá);
        final float var1 = 0.25f;
        final float var2 = 1.0f;
        this.HorizonCode_Horizon_È(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, var2, 0.5f + var1);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
    }
    
    @Override
    public AxisAlignedBB Ý(final World worldIn, final BlockPos pos) {
        this.Ý((IBlockAccess)worldIn, pos);
        return super.Ý(worldIn, pos);
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess blockAccess, final BlockPos pos) {
        return true;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityBanner();
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.£Ç;
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Items.£Ç;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        final TileEntity var6 = worldIn.HorizonCode_Horizon_È(pos);
        if (var6 instanceof TileEntityBanner) {
            final ItemStack var7 = new ItemStack(Items.£Ç, 1, ((TileEntityBanner)var6).HorizonCode_Horizon_È());
            final NBTTagCompound var8 = new NBTTagCompound();
            var6.Â(var8);
            var8.Å("x");
            var8.Å("y");
            var8.Å("z");
            var8.Å("id");
            var7.HorizonCode_Horizon_È("BlockEntityTag", var8);
            Block.HorizonCode_Horizon_È(worldIn, pos, var7);
        }
        else {
            super.HorizonCode_Horizon_È(worldIn, pos, state, chance, fortune);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final EntityPlayer playerIn, final BlockPos pos, final IBlockState state, final TileEntity te) {
        if (te instanceof TileEntityBanner) {
            final ItemStack var6 = new ItemStack(Items.£Ç, 1, ((TileEntityBanner)te).HorizonCode_Horizon_È());
            final NBTTagCompound var7 = new NBTTagCompound();
            te.Â(var7);
            var7.Å("x");
            var7.Å("y");
            var7.Å("z");
            var7.Å("id");
            var6.HorizonCode_Horizon_È("BlockEntityTag", var7);
            Block.HorizonCode_Horizon_È(worldIn, pos, var6);
        }
        else {
            super.HorizonCode_Horizon_È(worldIn, playerIn, pos, state, null);
        }
    }
    
    public static class HorizonCode_Horizon_È extends BlockBanner
    {
        private static final String ŠÂµà = "CL_00002140";
        
        public HorizonCode_Horizon_È() {
            this.Ø(this.á€.Â().HorizonCode_Horizon_È(HorizonCode_Horizon_È.Õ, EnumFacing.Ý));
        }
        
        @Override
        public void Ý(final IBlockAccess access, final BlockPos pos) {
            final EnumFacing var3 = (EnumFacing)access.Â(pos).HorizonCode_Horizon_È(HorizonCode_Horizon_È.Õ);
            final float var4 = 0.0f;
            final float var5 = 0.78125f;
            final float var6 = 0.0f;
            final float var7 = 1.0f;
            final float var8 = 0.125f;
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            switch (Ý.HorizonCode_Horizon_È[var3.ordinal()]) {
                default: {
                    this.HorizonCode_Horizon_È(var6, var4, 1.0f - var8, var7, var5, 1.0f);
                    break;
                }
                case 2: {
                    this.HorizonCode_Horizon_È(var6, var4, 0.0f, var7, var5, var8);
                    break;
                }
                case 3: {
                    this.HorizonCode_Horizon_È(1.0f - var8, var4, var6, 1.0f, var5, var7);
                    break;
                }
                case 4: {
                    this.HorizonCode_Horizon_È(0.0f, var4, var6, var8, var5, var7);
                    break;
                }
            }
        }
        
        @Override
        public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
            final EnumFacing var5 = (EnumFacing)state.HorizonCode_Horizon_È(HorizonCode_Horizon_È.Õ);
            if (!worldIn.Â(pos.HorizonCode_Horizon_È(var5.Âµá€())).Ý().Ó().Â()) {
                this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
                worldIn.Ø(pos);
            }
            super.HorizonCode_Horizon_È(worldIn, pos, state, neighborBlock);
        }
        
        @Override
        public IBlockState Ý(final int meta) {
            EnumFacing var2 = EnumFacing.HorizonCode_Horizon_È(meta);
            if (var2.á() == EnumFacing.HorizonCode_Horizon_È.Â) {
                var2 = EnumFacing.Ý;
            }
            return this.¥à().HorizonCode_Horizon_È(HorizonCode_Horizon_È.Õ, var2);
        }
        
        @Override
        public int Ý(final IBlockState state) {
            return ((EnumFacing)state.HorizonCode_Horizon_È(HorizonCode_Horizon_È.Õ)).Â();
        }
        
        @Override
        protected BlockState à¢() {
            return new BlockState(this, new IProperty[] { HorizonCode_Horizon_È.Õ });
        }
    }
    
    public static class Â extends BlockBanner
    {
        private static final String ŠÂµà = "CL_00002141";
        
        public Â() {
            this.Ø(this.á€.Â().HorizonCode_Horizon_È(Â.à¢, 0));
        }
        
        @Override
        public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
            if (!worldIn.Â(pos.Âµá€()).Ý().Ó().Â()) {
                this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
                worldIn.Ø(pos);
            }
            super.HorizonCode_Horizon_È(worldIn, pos, state, neighborBlock);
        }
        
        @Override
        public IBlockState Ý(final int meta) {
            return this.¥à().HorizonCode_Horizon_È(Â.à¢, meta);
        }
        
        @Override
        public int Ý(final IBlockState state) {
            return (int)state.HorizonCode_Horizon_È(Â.à¢);
        }
        
        @Override
        protected BlockState à¢() {
            return new BlockState(this, new IProperty[] { Â.à¢ });
        }
    }
    
    static final class Ý
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002142";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                Ý.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Ý.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                Ý.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                Ý.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
