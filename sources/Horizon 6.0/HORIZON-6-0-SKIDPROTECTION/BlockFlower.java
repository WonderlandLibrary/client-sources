package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.base.Predicate;
import java.util.List;

public abstract class BlockFlower extends BlockBush
{
    protected PropertyEnum Õ;
    private static final String à¢ = "CL_00000246";
    
    protected BlockFlower() {
        super(Material.ÂµÈ);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(this.áŠ(), (this.È() == HorizonCode_Horizon_È.Â) ? Â.Â : Â.HorizonCode_Horizon_È));
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((Â)state.HorizonCode_Horizon_È(this.áŠ())).Ý();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (final Â var7 : Â.HorizonCode_Horizon_È(this.È())) {
            list.add(new ItemStack(itemIn, 1, var7.Ý()));
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(this.áŠ(), Â.HorizonCode_Horizon_È(this.È(), meta));
    }
    
    public abstract HorizonCode_Horizon_È È();
    
    public IProperty áŠ() {
        if (this.Õ == null) {
            this.Õ = PropertyEnum.HorizonCode_Horizon_È("type", Â.class, (Predicate)new Predicate() {
                private static final String Â = "CL_00002120";
                
                public boolean HorizonCode_Horizon_È(final Â p_180354_1_) {
                    return p_180354_1_.Â() == BlockFlower.this.È();
                }
                
                public boolean apply(final Object p_apply_1_) {
                    return this.HorizonCode_Horizon_È((Â)p_apply_1_);
                }
            });
        }
        return this.Õ;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((Â)state.HorizonCode_Horizon_È(this.áŠ())).Ý();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { this.áŠ() });
    }
    
    @Override
    public Block.HorizonCode_Horizon_È Âµà() {
        return Block.HorizonCode_Horizon_È.Â;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("YELLOW", 0, "YELLOW", 0), 
        Â("RED", 1, "RED", 1);
        
        private static final HorizonCode_Horizon_È[] Ý;
        private static final String Ø­áŒŠá = "CL_00002117";
        
        static {
            Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ý = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45716_1_, final int p_i45716_2_) {
        }
        
        public BlockFlower HorizonCode_Horizon_È() {
            return (this == HorizonCode_Horizon_È.HorizonCode_Horizon_È) ? Blocks.Âµà : Blocks.Ç;
        }
    }
    
    public enum Â implements IStringSerializable
    {
        HorizonCode_Horizon_È("DANDELION", 0, "DANDELION", 0, BlockFlower.HorizonCode_Horizon_È.HorizonCode_Horizon_È, 0, "dandelion"), 
        Â("POPPY", 1, "POPPY", 1, BlockFlower.HorizonCode_Horizon_È.Â, 0, "poppy"), 
        Ý("BLUE_ORCHID", 2, "BLUE_ORCHID", 2, BlockFlower.HorizonCode_Horizon_È.Â, 1, "blue_orchid", "blueOrchid"), 
        Ø­áŒŠá("ALLIUM", 3, "ALLIUM", 3, BlockFlower.HorizonCode_Horizon_È.Â, 2, "allium"), 
        Âµá€("HOUSTONIA", 4, "HOUSTONIA", 4, BlockFlower.HorizonCode_Horizon_È.Â, 3, "houstonia"), 
        Ó("RED_TULIP", 5, "RED_TULIP", 5, BlockFlower.HorizonCode_Horizon_È.Â, 4, "red_tulip", "tulipRed"), 
        à("ORANGE_TULIP", 6, "ORANGE_TULIP", 6, BlockFlower.HorizonCode_Horizon_È.Â, 5, "orange_tulip", "tulipOrange"), 
        Ø("WHITE_TULIP", 7, "WHITE_TULIP", 7, BlockFlower.HorizonCode_Horizon_È.Â, 6, "white_tulip", "tulipWhite"), 
        áŒŠÆ("PINK_TULIP", 8, "PINK_TULIP", 8, BlockFlower.HorizonCode_Horizon_È.Â, 7, "pink_tulip", "tulipPink"), 
        áˆºÑ¢Õ("OXEYE_DAISY", 9, "OXEYE_DAISY", 9, BlockFlower.HorizonCode_Horizon_È.Â, 8, "oxeye_daisy", "oxeyeDaisy");
        
        private static final Â[][] ÂµÈ;
        private final HorizonCode_Horizon_È á;
        private final int ˆÏ­;
        private final String £á;
        private final String Å;
        private static final Â[] £à;
        private static final String µà = "CL_00002119";
        
        static {
            ˆà = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó, Â.à, Â.Ø, Â.áŒŠÆ, Â.áˆºÑ¢Õ };
            ÂµÈ = new Â[BlockFlower.HorizonCode_Horizon_È.values().length][];
            £à = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó, Â.à, Â.Ø, Â.áŒŠÆ, Â.áˆºÑ¢Õ };
            for (final HorizonCode_Horizon_È var4 : BlockFlower.HorizonCode_Horizon_È.values()) {
                final Collection var5 = Collections2.filter((Collection)Lists.newArrayList((Object[])values()), (Predicate)new Predicate() {
                    private static final String HorizonCode_Horizon_È = "CL_00002118";
                    private final /* synthetic */ HorizonCode_Horizon_È Â;
                    
                    public boolean HorizonCode_Horizon_È(final Â p_180350_1_) {
                        return p_180350_1_.Â() == this.Â;
                    }
                    
                    public boolean apply(final Object p_apply_1_) {
                        return this.HorizonCode_Horizon_È((Â)p_apply_1_);
                    }
                });
                Â.ÂµÈ[var4.ordinal()] = var5.toArray(new Â[var5.size()]);
            }
        }
        
        private Â(final String s, final int n, final String p_i45718_1_, final int p_i45718_2_, final HorizonCode_Horizon_È p_i45718_3_, final int p_i45718_4_, final String p_i45718_5_) {
            this(s, n, p_i45718_1_, p_i45718_2_, p_i45718_3_, p_i45718_4_, p_i45718_5_, p_i45718_5_);
        }
        
        private Â(final String s, final int n, final String p_i45719_1_, final int p_i45719_2_, final HorizonCode_Horizon_È p_i45719_3_, final int p_i45719_4_, final String p_i45719_5_, final String p_i45719_6_) {
            this.á = p_i45719_3_;
            this.ˆÏ­ = p_i45719_4_;
            this.£á = p_i45719_5_;
            this.Å = p_i45719_6_;
        }
        
        public HorizonCode_Horizon_È Â() {
            return this.á;
        }
        
        public int Ý() {
            return this.ˆÏ­;
        }
        
        public static Â HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_176967_0_, int p_176967_1_) {
            final Â[] var2 = Â.ÂµÈ[p_176967_0_.ordinal()];
            if (p_176967_1_ < 0 || p_176967_1_ >= var2.length) {
                p_176967_1_ = 0;
            }
            return var2[p_176967_1_];
        }
        
        public static Â[] HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_176966_0_) {
            return Â.ÂµÈ[p_176966_0_.ordinal()];
        }
        
        @Override
        public String toString() {
            return this.£á;
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.£á;
        }
        
        public String Ø­áŒŠá() {
            return this.Å;
        }
    }
}
