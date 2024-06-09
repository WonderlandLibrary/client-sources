package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockSandStone extends Block
{
    public static final PropertyEnum Õ;
    private static final String à¢ = "CL_00000304";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("type", HorizonCode_Horizon_È.class);
    }
    
    public BlockSandStone() {
        super(Material.Âµá€);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockSandStone.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockSandStone.Õ)).Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (final HorizonCode_Horizon_È var7 : HorizonCode_Horizon_È.values()) {
            list.add(new ItemStack(itemIn, 1, var7.Â()));
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockSandStone.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockSandStone.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockSandStone.Õ });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("DEFAULT", 0, "DEFAULT", 0, 0, "sandstone", "default"), 
        Â("CHISELED", 1, "CHISELED", 1, 1, "chiseled_sandstone", "chiseled"), 
        Ý("SMOOTH", 2, "SMOOTH", 2, 2, "smooth_sandstone", "smooth");
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private final int Âµá€;
        private final String Ó;
        private final String à;
        private static final HorizonCode_Horizon_È[] Ø;
        private static final String áŒŠÆ = "CL_00002068";
        
        static {
            áˆºÑ¢Õ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[values().length];
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Ø­áŒŠá[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45686_1_, final int p_i45686_2_, final int p_i45686_3_, final String p_i45686_4_, final String p_i45686_5_) {
            this.Âµá€ = p_i45686_3_;
            this.Ó = p_i45686_4_;
            this.à = p_i45686_5_;
        }
        
        public int Â() {
            return this.Âµá€;
        }
        
        @Override
        public String toString() {
            return this.Ó;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_176673_0_) {
            if (p_176673_0_ < 0 || p_176673_0_ >= HorizonCode_Horizon_È.Ø­áŒŠá.length) {
                p_176673_0_ = 0;
            }
            return HorizonCode_Horizon_È.Ø­áŒŠá[p_176673_0_];
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Ó;
        }
        
        public String Ý() {
            return this.à;
        }
    }
}
