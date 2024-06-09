package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public class BlockStone extends Block
{
    public static final PropertyEnum Õ;
    private static final String à¢ = "CL_00000317";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("variant", HorizonCode_Horizon_È.class);
    }
    
    public BlockStone() {
        super(Material.Âµá€);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockStone.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return (state.HorizonCode_Horizon_È(BlockStone.Õ) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) ? Item_1028566121.HorizonCode_Horizon_È(Blocks.Ó) : Item_1028566121.HorizonCode_Horizon_È(Blocks.Ý);
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockStone.Õ)).Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (final HorizonCode_Horizon_È var7 : HorizonCode_Horizon_È.values()) {
            list.add(new ItemStack(itemIn, 1, var7.Â()));
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockStone.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockStone.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockStone.Õ });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("STONE", 0, "STONE", 0, 0, "stone"), 
        Â("GRANITE", 1, "GRANITE", 1, 1, "granite"), 
        Ý("GRANITE_SMOOTH", 2, "GRANITE_SMOOTH", 2, 2, "smooth_granite", "graniteSmooth"), 
        Ø­áŒŠá("DIORITE", 3, "DIORITE", 3, 3, "diorite"), 
        Âµá€("DIORITE_SMOOTH", 4, "DIORITE_SMOOTH", 4, 4, "smooth_diorite", "dioriteSmooth"), 
        Ó("ANDESITE", 5, "ANDESITE", 5, 5, "andesite"), 
        à("ANDESITE_SMOOTH", 6, "ANDESITE_SMOOTH", 6, 6, "smooth_andesite", "andesiteSmooth");
        
        private static final HorizonCode_Horizon_È[] Ø;
        private final int áŒŠÆ;
        private final String áˆºÑ¢Õ;
        private final String ÂµÈ;
        private static final HorizonCode_Horizon_È[] á;
        private static final String ˆÏ­ = "CL_00002058";
        
        static {
            £á = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à };
            Ø = new HorizonCode_Horizon_È[values().length];
            á = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Ø[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45680_1_, final int p_i45680_2_, final int p_i45680_3_, final String p_i45680_4_) {
            this(s, n, p_i45680_1_, p_i45680_2_, p_i45680_3_, p_i45680_4_, p_i45680_4_);
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45681_1_, final int p_i45681_2_, final int p_i45681_3_, final String p_i45681_4_, final String p_i45681_5_) {
            this.áŒŠÆ = p_i45681_3_;
            this.áˆºÑ¢Õ = p_i45681_4_;
            this.ÂµÈ = p_i45681_5_;
        }
        
        public int Â() {
            return this.áŒŠÆ;
        }
        
        @Override
        public String toString() {
            return this.áˆºÑ¢Õ;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_176643_0_) {
            if (p_176643_0_ < 0 || p_176643_0_ >= HorizonCode_Horizon_È.Ø.length) {
                p_176643_0_ = 0;
            }
            return HorizonCode_Horizon_È.Ø[p_176643_0_];
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.áˆºÑ¢Õ;
        }
        
        public String Ý() {
            return this.ÂµÈ;
        }
    }
}
