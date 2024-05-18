package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockPrismarine extends Block
{
    public static final PropertyEnum Õ;
    public static final int à¢;
    public static final int ŠÂµà;
    public static final int ¥à;
    private static final String Âµà = "CL_00002077";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("variant", HorizonCode_Horizon_È.class);
        à¢ = HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â();
        ŠÂµà = HorizonCode_Horizon_È.Â.Â();
        ¥à = HorizonCode_Horizon_È.Ý.Â();
    }
    
    public BlockPrismarine() {
        super(Material.Âµá€);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockPrismarine.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockPrismarine.Õ)).Â();
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockPrismarine.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockPrismarine.Õ });
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockPrismarine.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        list.add(new ItemStack(itemIn, 1, BlockPrismarine.à¢));
        list.add(new ItemStack(itemIn, 1, BlockPrismarine.ŠÂµà));
        list.add(new ItemStack(itemIn, 1, BlockPrismarine.¥à));
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("ROUGH", 0, "ROUGH", 0, 0, "prismarine", "rough"), 
        Â("BRICKS", 1, "BRICKS", 1, 1, "prismarine_bricks", "bricks"), 
        Ý("DARK", 2, "DARK", 2, 2, "dark_prismarine", "dark");
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private final int Âµá€;
        private final String Ó;
        private final String à;
        private static final HorizonCode_Horizon_È[] Ø;
        private static final String áŒŠÆ = "CL_00002076";
        
        static {
            áˆºÑ¢Õ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[values().length];
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Ø­áŒŠá[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45692_1_, final int p_i45692_2_, final int p_i45692_3_, final String p_i45692_4_, final String p_i45692_5_) {
            this.Âµá€ = p_i45692_3_;
            this.Ó = p_i45692_4_;
            this.à = p_i45692_5_;
        }
        
        public int Â() {
            return this.Âµá€;
        }
        
        @Override
        public String toString() {
            return this.Ó;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_176810_0_) {
            if (p_176810_0_ < 0 || p_176810_0_ >= HorizonCode_Horizon_È.Ø­áŒŠá.length) {
                p_176810_0_ = 0;
            }
            return HorizonCode_Horizon_È.Ø­áŒŠá[p_176810_0_];
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
