package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockStoneBrick extends Block
{
    public static final PropertyEnum Õ;
    public static final int à¢;
    public static final int ŠÂµà;
    public static final int ¥à;
    public static final int Âµà;
    private static final String Ç = "CL_00000318";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("variant", HorizonCode_Horizon_È.class);
        à¢ = HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â();
        ŠÂµà = HorizonCode_Horizon_È.Â.Â();
        ¥à = HorizonCode_Horizon_È.Ý.Â();
        Âµà = HorizonCode_Horizon_È.Ø­áŒŠá.Â();
    }
    
    public BlockStoneBrick() {
        super(Material.Âµá€);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockStoneBrick.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockStoneBrick.Õ)).Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (final HorizonCode_Horizon_È var7 : HorizonCode_Horizon_È.values()) {
            list.add(new ItemStack(itemIn, 1, var7.Â()));
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockStoneBrick.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockStoneBrick.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockStoneBrick.Õ });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("DEFAULT", 0, "DEFAULT", 0, 0, "stonebrick", "default"), 
        Â("MOSSY", 1, "MOSSY", 1, 1, "mossy_stonebrick", "mossy"), 
        Ý("CRACKED", 2, "CRACKED", 2, 2, "cracked_stonebrick", "cracked"), 
        Ø­áŒŠá("CHISELED", 3, "CHISELED", 3, 3, "chiseled_stonebrick", "chiseled");
        
        private static final HorizonCode_Horizon_È[] Âµá€;
        private final int Ó;
        private final String à;
        private final String Ø;
        private static final HorizonCode_Horizon_È[] áŒŠÆ;
        private static final String áˆºÑ¢Õ = "CL_00002057";
        
        static {
            ÂµÈ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
            Âµá€ = new HorizonCode_Horizon_È[values().length];
            áŒŠÆ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Âµá€[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45679_1_, final int p_i45679_2_, final int p_i45679_3_, final String p_i45679_4_, final String p_i45679_5_) {
            this.Ó = p_i45679_3_;
            this.à = p_i45679_4_;
            this.Ø = p_i45679_5_;
        }
        
        public int Â() {
            return this.Ó;
        }
        
        @Override
        public String toString() {
            return this.à;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_176613_0_) {
            if (p_176613_0_ < 0 || p_176613_0_ >= HorizonCode_Horizon_È.Âµá€.length) {
                p_176613_0_ = 0;
            }
            return HorizonCode_Horizon_È.Âµá€[p_176613_0_];
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.à;
        }
        
        public String Ý() {
            return this.Ø;
        }
    }
}
