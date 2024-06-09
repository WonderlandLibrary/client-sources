package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockPlanks extends Block
{
    public static final PropertyEnum Õ;
    private static final String à¢ = "CL_00002082";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("variant", HorizonCode_Horizon_È.class);
    }
    
    public BlockPlanks() {
        super(Material.Ø­áŒŠá);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockPlanks.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockPlanks.Õ)).Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (final HorizonCode_Horizon_È var7 : HorizonCode_Horizon_È.values()) {
            list.add(new ItemStack(itemIn, 1, var7.Â()));
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockPlanks.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockPlanks.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockPlanks.Õ });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("OAK", 0, "OAK", 0, 0, "oak"), 
        Â("SPRUCE", 1, "SPRUCE", 1, 1, "spruce"), 
        Ý("BIRCH", 2, "BIRCH", 2, 2, "birch"), 
        Ø­áŒŠá("JUNGLE", 3, "JUNGLE", 3, 3, "jungle"), 
        Âµá€("ACACIA", 4, "ACACIA", 4, 4, "acacia"), 
        Ó("DARK_OAK", 5, "DARK_OAK", 5, 5, "dark_oak", "big_oak");
        
        private static final HorizonCode_Horizon_È[] à;
        private final int Ø;
        private final String áŒŠÆ;
        private final String áˆºÑ¢Õ;
        private static final HorizonCode_Horizon_È[] ÂµÈ;
        private static final String á = "CL_00002081";
        
        static {
            ˆÏ­ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó };
            à = new HorizonCode_Horizon_È[values().length];
            ÂµÈ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.à[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45695_1_, final int p_i45695_2_, final int p_i45695_3_, final String p_i45695_4_) {
            this(s, n, p_i45695_1_, p_i45695_2_, p_i45695_3_, p_i45695_4_, p_i45695_4_);
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45696_1_, final int p_i45696_2_, final int p_i45696_3_, final String p_i45696_4_, final String p_i45696_5_) {
            this.Ø = p_i45696_3_;
            this.áŒŠÆ = p_i45696_4_;
            this.áˆºÑ¢Õ = p_i45696_5_;
        }
        
        public int Â() {
            return this.Ø;
        }
        
        @Override
        public String toString() {
            return this.áŒŠÆ;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_176837_0_) {
            if (p_176837_0_ < 0 || p_176837_0_ >= HorizonCode_Horizon_È.à.length) {
                p_176837_0_ = 0;
            }
            return HorizonCode_Horizon_È.à[p_176837_0_];
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.áŒŠÆ;
        }
        
        public String Ý() {
            return this.áˆºÑ¢Õ;
        }
    }
}
