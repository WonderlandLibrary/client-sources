package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.List;

public class ItemFishFood extends ItemFood
{
    private final boolean Ø;
    private static final String áŒŠÆ = "CL_00000032";
    
    public ItemFishFood(final boolean p_i45338_1_) {
        super(0, 0.0f, false);
        this.Ø = p_i45338_1_;
    }
    
    @Override
    public int ÂµÈ(final ItemStack itemStackIn) {
        final HorizonCode_Horizon_È var2 = HorizonCode_Horizon_È.HorizonCode_Horizon_È(itemStackIn);
        return (this.Ø && var2.à()) ? var2.Âµá€() : var2.Ý();
    }
    
    @Override
    public float á(final ItemStack itemStackIn) {
        final HorizonCode_Horizon_È var2 = HorizonCode_Horizon_È.HorizonCode_Horizon_È(itemStackIn);
        return (this.Ø && var2.à()) ? var2.Ó() : var2.Ø­áŒŠá();
    }
    
    @Override
    public String Âµá€(final ItemStack stack) {
        return (HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack) == HorizonCode_Horizon_È.Ø­áŒŠá) ? PotionHelper.ˆÏ­ : null;
    }
    
    @Override
    protected void Ø­áŒŠá(final ItemStack p_77849_1_, final World worldIn, final EntityPlayer p_77849_3_) {
        final HorizonCode_Horizon_È var4 = HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_77849_1_);
        if (var4 == HorizonCode_Horizon_È.Ø­áŒŠá) {
            p_77849_3_.HorizonCode_Horizon_È(new PotionEffect(Potion.µÕ.É, 1200, 3));
            p_77849_3_.HorizonCode_Horizon_È(new PotionEffect(Potion.¥Æ.É, 300, 2));
            p_77849_3_.HorizonCode_Horizon_È(new PotionEffect(Potion.ÂµÈ.É, 300, 1));
        }
        super.Ø­áŒŠá(p_77849_1_, worldIn, p_77849_3_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List subItems) {
        for (final HorizonCode_Horizon_È var7 : HorizonCode_Horizon_È.values()) {
            if (!this.Ø || var7.à()) {
                subItems.add(new ItemStack(this, 1, var7.HorizonCode_Horizon_È()));
            }
        }
    }
    
    @Override
    public String Â(final ItemStack stack) {
        final HorizonCode_Horizon_È var2 = HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack);
        return String.valueOf(this.Ø()) + "." + var2.Â() + "." + ((this.Ø && var2.à()) ? "cooked" : "raw");
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("COD", 0, "COD", 0, 0, "cod", 2, 0.1f, 5, 0.6f), 
        Â("SALMON", 1, "SALMON", 1, 1, "salmon", 2, 0.1f, 6, 0.8f), 
        Ý("CLOWNFISH", 2, "CLOWNFISH", 2, 2, "clownfish", 1, 0.1f), 
        Ø­áŒŠá("PUFFERFISH", 3, "PUFFERFISH", 3, 3, "pufferfish", 1, 0.1f);
        
        private static final Map Âµá€;
        private final int Ó;
        private final String à;
        private final int Ø;
        private final float áŒŠÆ;
        private final int áˆºÑ¢Õ;
        private final float ÂµÈ;
        private boolean á;
        private static final HorizonCode_Horizon_È[] ˆÏ­;
        private static final String £á = "CL_00000033";
        
        static {
            Å = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
            Âµá€ = Maps.newHashMap();
            ˆÏ­ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Âµá€.put(var4.HorizonCode_Horizon_È(), var4);
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45336_1_, final int p_i45336_2_, final int p_i45336_3_, final String p_i45336_4_, final int p_i45336_5_, final float p_i45336_6_, final int p_i45336_7_, final float p_i45336_8_) {
            this.á = false;
            this.Ó = p_i45336_3_;
            this.à = p_i45336_4_;
            this.Ø = p_i45336_5_;
            this.áŒŠÆ = p_i45336_6_;
            this.áˆºÑ¢Õ = p_i45336_7_;
            this.ÂµÈ = p_i45336_8_;
            this.á = true;
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45337_1_, final int p_i45337_2_, final int p_i45337_3_, final String p_i45337_4_, final int p_i45337_5_, final float p_i45337_6_) {
            this.á = false;
            this.Ó = p_i45337_3_;
            this.à = p_i45337_4_;
            this.Ø = p_i45337_5_;
            this.áŒŠÆ = p_i45337_6_;
            this.áˆºÑ¢Õ = 0;
            this.ÂµÈ = 0.0f;
            this.á = false;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.Ó;
        }
        
        public String Â() {
            return this.à;
        }
        
        public int Ý() {
            return this.Ø;
        }
        
        public float Ø­áŒŠá() {
            return this.áŒŠÆ;
        }
        
        public int Âµá€() {
            return this.áˆºÑ¢Õ;
        }
        
        public float Ó() {
            return this.ÂµÈ;
        }
        
        public boolean à() {
            return this.á;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final int p_150974_0_) {
            final HorizonCode_Horizon_È var1 = HorizonCode_Horizon_È.Âµá€.get(p_150974_0_);
            return (var1 == null) ? HorizonCode_Horizon_È.HorizonCode_Horizon_È : var1;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final ItemStack p_150978_0_) {
            return (p_150978_0_.HorizonCode_Horizon_È() instanceof ItemFishFood) ? HorizonCode_Horizon_È(p_150978_0_.Ø()) : HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        }
    }
}
