package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockSand extends BlockFalling
{
    public static final PropertyEnum Õ;
    private static final String à¢ = "CL_00000303";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("variant", HorizonCode_Horizon_È.class);
    }
    
    public BlockSand() {
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockSand.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockSand.Õ)).Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (final HorizonCode_Horizon_È var7 : HorizonCode_Horizon_È.values()) {
            list.add(new ItemStack(itemIn, 1, var7.Â()));
        }
    }
    
    @Override
    public MapColor Â(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockSand.Õ)).Ý();
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockSand.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockSand.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockSand.Õ });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("SAND", 0, "SAND", 0, 0, "sand", "default", MapColor.Ø­áŒŠá), 
        Â("RED_SAND", 1, "RED_SAND", 1, 1, "red_sand", "red", MapColor.á);
        
        private static final HorizonCode_Horizon_È[] Ý;
        private final int Ø­áŒŠá;
        private final String Âµá€;
        private final MapColor Ó;
        private final String à;
        private static final HorizonCode_Horizon_È[] Ø;
        private static final String áŒŠÆ = "CL_00002069";
        
        static {
            áˆºÑ¢Õ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ý = new HorizonCode_Horizon_È[values().length];
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Ý[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45687_1_, final int p_i45687_2_, final int p_i45687_3_, final String p_i45687_4_, final String p_i45687_5_, final MapColor p_i45687_6_) {
            this.Ø­áŒŠá = p_i45687_3_;
            this.Âµá€ = p_i45687_4_;
            this.Ó = p_i45687_6_;
            this.à = p_i45687_5_;
        }
        
        public int Â() {
            return this.Ø­áŒŠá;
        }
        
        @Override
        public String toString() {
            return this.Âµá€;
        }
        
        public MapColor Ý() {
            return this.Ó;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_176686_0_) {
            if (p_176686_0_ < 0 || p_176686_0_ >= HorizonCode_Horizon_È.Ý.length) {
                p_176686_0_ = 0;
            }
            return HorizonCode_Horizon_È.Ý[p_176686_0_];
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Âµá€;
        }
        
        public String Ø­áŒŠá() {
            return this.à;
        }
    }
}
