package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public abstract class CreativeTabs
{
    public static final CreativeTabs[] HorizonCode_Horizon_È;
    public static final CreativeTabs Â;
    public static final CreativeTabs Ý;
    public static final CreativeTabs Ø­áŒŠá;
    public static final CreativeTabs Âµá€;
    public static final CreativeTabs Ó;
    public static final CreativeTabs à;
    public static final CreativeTabs Ø;
    public static final CreativeTabs áŒŠÆ;
    public static final CreativeTabs áˆºÑ¢Õ;
    public static final CreativeTabs ÂµÈ;
    public static final CreativeTabs á;
    public static final CreativeTabs ˆÏ­;
    private final int £á;
    private final String Å;
    private String £à;
    private boolean µà;
    private boolean ˆà;
    private EnumEnchantmentType[] ¥Æ;
    private ItemStack Ø­à;
    private static final String µÕ = "CL_00000005";
    
    static {
        HorizonCode_Horizon_È = new CreativeTabs[12];
        Â = new CreativeTabs("buildingBlocks") {
            private static final String £á = "CL_00000006";
            
            @Override
            public Item_1028566121 Âµá€() {
                return Item_1028566121.HorizonCode_Horizon_È(Blocks.Ä);
            }
        };
        Ý = new CreativeTabs("decorations") {
            private static final String £á = "CL_00000010";
            
            @Override
            public Item_1028566121 Âµá€() {
                return Item_1028566121.HorizonCode_Horizon_È(Blocks.ÇªÇªÉ);
            }
            
            @Override
            public int Ó() {
                return BlockDoublePlant.Â.Ó.Â();
            }
        };
        Ø­áŒŠá = new CreativeTabs("redstone") {
            private static final String £á = "CL_00000011";
            
            @Override
            public Item_1028566121 Âµá€() {
                return Items.ÇŽá;
            }
        };
        Âµá€ = new CreativeTabs("transportation") {
            private static final String £á = "CL_00000012";
            
            @Override
            public Item_1028566121 Âµá€() {
                return Item_1028566121.HorizonCode_Horizon_È(Blocks.ÇŽÉ);
            }
        };
        Ó = new CreativeTabs("misc") {
            private static final String £á = "CL_00000014";
            
            @Override
            public Item_1028566121 Âµá€() {
                return Items.¥Ä;
            }
        }.HorizonCode_Horizon_È(new EnumEnchantmentType[] { EnumEnchantmentType.HorizonCode_Horizon_È });
        à = new CreativeTabs("search") {
            private static final String £á = "CL_00000015";
            
            @Override
            public Item_1028566121 Âµá€() {
                return Items.£ÇªÓ;
            }
        }.HorizonCode_Horizon_È("item_search.png");
        Ø = new CreativeTabs("food") {
            private static final String £á = "CL_00000016";
            
            @Override
            public Item_1028566121 Âµá€() {
                return Items.Âµá€;
            }
        };
        áŒŠÆ = new CreativeTabs("tools") {
            private static final String £á = "CL_00000017";
            
            @Override
            public Item_1028566121 Âµá€() {
                return Items.Ý;
            }
        }.HorizonCode_Horizon_È(EnumEnchantmentType.Ø, EnumEnchantmentType.áŒŠÆ, EnumEnchantmentType.áˆºÑ¢Õ);
        áˆºÑ¢Õ = new CreativeTabs("combat") {
            private static final String £á = "CL_00000018";
            
            @Override
            public Item_1028566121 Âµá€() {
                return Items.ŒÏ;
            }
        }.HorizonCode_Horizon_È(EnumEnchantmentType.Â, EnumEnchantmentType.Ý, EnumEnchantmentType.Ó, EnumEnchantmentType.Ø­áŒŠá, EnumEnchantmentType.Âµá€, EnumEnchantmentType.ÂµÈ, EnumEnchantmentType.à);
        ÂµÈ = new CreativeTabs("brewing") {
            private static final String £á = "CL_00000007";
            
            @Override
            public Item_1028566121 Âµá€() {
                return Items.µÂ;
            }
        };
        á = new CreativeTabs("materials") {
            private static final String £á = "CL_00000008";
            
            @Override
            public Item_1028566121 Âµá€() {
                return Items.áŒŠà;
            }
        };
        ˆÏ­ = new CreativeTabs("inventory") {
            private static final String £á = "CL_00000009";
            
            @Override
            public Item_1028566121 Âµá€() {
                return Item_1028566121.HorizonCode_Horizon_È(Blocks.ˆáƒ);
            }
        }.HorizonCode_Horizon_È("inventory.png").ÂµÈ().áŒŠÆ();
    }
    
    public CreativeTabs(final int index, final String label) {
        this.£à = "items.png";
        this.µà = true;
        this.ˆà = true;
        this.£á = index;
        this.Å = label;
        CreativeTabs.HorizonCode_Horizon_È[index] = this;
    }
    
    public int HorizonCode_Horizon_È() {
        return this.£á;
    }
    
    public String Â() {
        return this.Å;
    }
    
    public String Ý() {
        return "itemGroup." + this.Â();
    }
    
    public ItemStack Ø­áŒŠá() {
        if (this.Ø­à == null) {
            this.Ø­à = new ItemStack(this.Âµá€(), 1, this.Ó());
        }
        return this.Ø­à;
    }
    
    public abstract Item_1028566121 Âµá€();
    
    public int Ó() {
        return 0;
    }
    
    public String à() {
        return this.£à;
    }
    
    public CreativeTabs HorizonCode_Horizon_È(final String texture) {
        this.£à = texture;
        return this;
    }
    
    public boolean Ø() {
        return this.ˆà;
    }
    
    public CreativeTabs áŒŠÆ() {
        this.ˆà = false;
        return this;
    }
    
    public boolean áˆºÑ¢Õ() {
        return this.µà;
    }
    
    public CreativeTabs ÂµÈ() {
        this.µà = false;
        return this;
    }
    
    public int á() {
        return this.£á % 6;
    }
    
    public boolean ˆÏ­() {
        return this.£á < 6;
    }
    
    public EnumEnchantmentType[] £á() {
        return this.¥Æ;
    }
    
    public CreativeTabs HorizonCode_Horizon_È(final EnumEnchantmentType... types) {
        this.¥Æ = types;
        return this;
    }
    
    public boolean HorizonCode_Horizon_È(final EnumEnchantmentType p_111226_1_) {
        if (this.¥Æ == null) {
            return false;
        }
        for (final EnumEnchantmentType var5 : this.¥Æ) {
            if (var5 == p_111226_1_) {
                return true;
            }
        }
        return false;
    }
    
    public void HorizonCode_Horizon_È(final List p_78018_1_) {
        for (final Item_1028566121 var3 : Item_1028566121.HorizonCode_Horizon_È) {
            if (var3 != null && var3.£á() == this) {
                var3.HorizonCode_Horizon_È(var3, this, p_78018_1_);
            }
        }
        if (this.£á() != null) {
            this.HorizonCode_Horizon_È(p_78018_1_, this.£á());
        }
    }
    
    public void HorizonCode_Horizon_È(final List p_92116_1_, final EnumEnchantmentType... p_92116_2_) {
        for (final Enchantment var6 : Enchantment.Â) {
            if (var6 != null && var6.Çªà¢ != null) {
                boolean var7 = false;
                for (int var8 = 0; var8 < p_92116_2_.length && !var7; ++var8) {
                    if (var6.Çªà¢ == p_92116_2_[var8]) {
                        var7 = true;
                    }
                }
                if (var7) {
                    p_92116_1_.add(Items.Çªáˆºá.HorizonCode_Horizon_È(new EnchantmentData(var6, var6.Ø­áŒŠá())));
                }
            }
        }
    }
}
