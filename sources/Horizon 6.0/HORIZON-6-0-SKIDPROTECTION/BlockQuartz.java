package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockQuartz extends Block
{
    public static final PropertyEnum Õ;
    private static final String à¢ = "CL_00000292";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("variant", HorizonCode_Horizon_È.class);
    }
    
    public BlockQuartz() {
        super(Material.Âµá€);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockQuartz.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        if (meta != HorizonCode_Horizon_È.Ý.Â()) {
            return (meta == HorizonCode_Horizon_È.Â.Â()) ? this.¥à().HorizonCode_Horizon_È(BlockQuartz.Õ, HorizonCode_Horizon_È.Â) : this.¥à().HorizonCode_Horizon_È(BlockQuartz.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        }
        switch (Â.HorizonCode_Horizon_È[facing.á().ordinal()]) {
            case 1: {
                return this.¥à().HorizonCode_Horizon_È(BlockQuartz.Õ, HorizonCode_Horizon_È.Âµá€);
            }
            case 2: {
                return this.¥à().HorizonCode_Horizon_È(BlockQuartz.Õ, HorizonCode_Horizon_È.Ø­áŒŠá);
            }
            default: {
                return this.¥à().HorizonCode_Horizon_È(BlockQuartz.Õ, HorizonCode_Horizon_È.Ý);
            }
        }
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        final HorizonCode_Horizon_È var2 = (HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockQuartz.Õ);
        return (var2 != HorizonCode_Horizon_È.Ø­áŒŠá && var2 != HorizonCode_Horizon_È.Âµá€) ? var2.Â() : HorizonCode_Horizon_È.Ý.Â();
    }
    
    @Override
    protected ItemStack Ó(final IBlockState state) {
        final HorizonCode_Horizon_È var2 = (HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockQuartz.Õ);
        return (var2 != HorizonCode_Horizon_È.Ø­áŒŠá && var2 != HorizonCode_Horizon_È.Âµá€) ? super.Ó(state) : new ItemStack(Item_1028566121.HorizonCode_Horizon_È(this), 1, HorizonCode_Horizon_È.Ý.Â());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        list.add(new ItemStack(itemIn, 1, HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        list.add(new ItemStack(itemIn, 1, HorizonCode_Horizon_È.Â.Â()));
        list.add(new ItemStack(itemIn, 1, HorizonCode_Horizon_È.Ý.Â()));
    }
    
    @Override
    public MapColor Â(final IBlockState state) {
        return MapColor.£à;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockQuartz.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockQuartz.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockQuartz.Õ });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("DEFAULT", 0, "DEFAULT", 0, 0, "default", "default"), 
        Â("CHISELED", 1, "CHISELED", 1, 1, "chiseled", "chiseled"), 
        Ý("LINES_Y", 2, "LINES_Y", 2, 2, "lines_y", "lines"), 
        Ø­áŒŠá("LINES_X", 3, "LINES_X", 3, 3, "lines_x", "lines"), 
        Âµá€("LINES_Z", 4, "LINES_Z", 4, 4, "lines_z", "lines");
        
        private static final HorizonCode_Horizon_È[] Ó;
        private final int à;
        private final String Ø;
        private final String áŒŠÆ;
        private static final HorizonCode_Horizon_È[] áˆºÑ¢Õ;
        private static final String ÂµÈ = "CL_00002074";
        
        static {
            á = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
            Ó = new HorizonCode_Horizon_È[values().length];
            áˆºÑ¢Õ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Ó[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45691_1_, final int p_i45691_2_, final int p_i45691_3_, final String p_i45691_4_, final String p_i45691_5_) {
            this.à = p_i45691_3_;
            this.Ø = p_i45691_4_;
            this.áŒŠÆ = p_i45691_5_;
        }
        
        public int Â() {
            return this.à;
        }
        
        @Override
        public String toString() {
            return this.áŒŠÆ;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_176794_0_) {
            if (p_176794_0_ < 0 || p_176794_0_ >= HorizonCode_Horizon_È.Ó.length) {
                p_176794_0_ = 0;
            }
            return HorizonCode_Horizon_È.Ó[p_176794_0_];
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Ø;
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002075";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.HorizonCode_Horizon_È.values().length];
            try {
                BlockQuartz.Â.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockQuartz.Â.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockQuartz.Â.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.Â.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
        }
    }
}
