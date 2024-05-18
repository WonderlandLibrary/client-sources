package HORIZON-6-0-SKIDPROTECTION;

public class RecipesBanners
{
    private static final String HorizonCode_Horizon_È = "CL_00002160";
    
    void HorizonCode_Horizon_È(final CraftingManager p_179534_1_) {
        for (final EnumDyeColor var5 : EnumDyeColor.values()) {
            p_179534_1_.HorizonCode_Horizon_È(new ItemStack(Items.£Ç, 1, var5.Ý()), "###", "###", " | ", '#', new ItemStack(Blocks.ŠÂµà, 1, var5.Â()), '|', Items.áŒŠà);
        }
        p_179534_1_.HorizonCode_Horizon_È(new Â(null));
        p_179534_1_.HorizonCode_Horizon_È(new HorizonCode_Horizon_È(null));
    }
    
    static class HorizonCode_Horizon_È implements IRecipe
    {
        private static final String HorizonCode_Horizon_È = "CL_00002158";
        
        private HorizonCode_Horizon_È() {
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final InventoryCrafting p_77569_1_, final World worldIn) {
            boolean var3 = false;
            for (int var4 = 0; var4 < p_77569_1_.áŒŠÆ(); ++var4) {
                final ItemStack var5 = p_77569_1_.á(var4);
                if (var5 != null && var5.HorizonCode_Horizon_È() == Items.£Ç) {
                    if (var3) {
                        return false;
                    }
                    if (TileEntityBanner.Ý(var5) >= 6) {
                        return false;
                    }
                    var3 = true;
                }
            }
            return var3 && this.Ý(p_77569_1_) != null;
        }
        
        @Override
        public ItemStack HorizonCode_Horizon_È(final InventoryCrafting p_77572_1_) {
            ItemStack var2 = null;
            for (int var3 = 0; var3 < p_77572_1_.áŒŠÆ(); ++var3) {
                final ItemStack var4 = p_77572_1_.á(var3);
                if (var4 != null && var4.HorizonCode_Horizon_È() == Items.£Ç) {
                    var2 = var4.áˆºÑ¢Õ();
                    var2.Â = 1;
                    break;
                }
            }
            final TileEntityBanner.HorizonCode_Horizon_È var5 = this.Ý(p_77572_1_);
            if (var5 != null) {
                int var6 = 0;
                for (int var7 = 0; var7 < p_77572_1_.áŒŠÆ(); ++var7) {
                    final ItemStack var8 = p_77572_1_.á(var7);
                    if (var8 != null && var8.HorizonCode_Horizon_È() == Items.áŒŠÔ) {
                        var6 = var8.Ø();
                        break;
                    }
                }
                final NBTTagCompound var9 = var2.HorizonCode_Horizon_È("BlockEntityTag", true);
                final ItemStack var8 = null;
                NBTTagList var10;
                if (var9.Â("Patterns", 9)) {
                    var10 = var9.Ý("Patterns", 10);
                }
                else {
                    var10 = new NBTTagList();
                    var9.HorizonCode_Horizon_È("Patterns", var10);
                }
                final NBTTagCompound var11 = new NBTTagCompound();
                var11.HorizonCode_Horizon_È("Pattern", var5.Â());
                var11.HorizonCode_Horizon_È("Color", var6);
                var10.HorizonCode_Horizon_È(var11);
            }
            return var2;
        }
        
        @Override
        public int HorizonCode_Horizon_È() {
            return 10;
        }
        
        @Override
        public ItemStack Â() {
            return null;
        }
        
        @Override
        public ItemStack[] Â(final InventoryCrafting p_179532_1_) {
            final ItemStack[] var2 = new ItemStack[p_179532_1_.áŒŠÆ()];
            for (int var3 = 0; var3 < var2.length; ++var3) {
                final ItemStack var4 = p_179532_1_.á(var3);
                if (var4 != null && var4.HorizonCode_Horizon_È().ÂµÈ()) {
                    var2[var3] = new ItemStack(var4.HorizonCode_Horizon_È().áˆºÑ¢Õ());
                }
            }
            return var2;
        }
        
        private TileEntityBanner.HorizonCode_Horizon_È Ý(final InventoryCrafting p_179533_1_) {
            for (final TileEntityBanner.HorizonCode_Horizon_È var5 : TileEntityBanner.HorizonCode_Horizon_È.values()) {
                if (var5.Ø­áŒŠá()) {
                    boolean var6 = true;
                    if (var5.Âµá€()) {
                        boolean var7 = false;
                        boolean var8 = false;
                        for (int var9 = 0; var9 < p_179533_1_.áŒŠÆ() && var6; ++var9) {
                            final ItemStack var10 = p_179533_1_.á(var9);
                            if (var10 != null && var10.HorizonCode_Horizon_È() != Items.£Ç) {
                                if (var10.HorizonCode_Horizon_È() == Items.áŒŠÔ) {
                                    if (var8) {
                                        var6 = false;
                                        break;
                                    }
                                    var8 = true;
                                }
                                else {
                                    if (var7 || !var10.HorizonCode_Horizon_È(var5.Ó())) {
                                        var6 = false;
                                        break;
                                    }
                                    var7 = true;
                                }
                            }
                        }
                        if (!var7) {
                            var6 = false;
                        }
                    }
                    else if (p_179533_1_.áŒŠÆ() != var5.Ý().length * var5.Ý()[0].length()) {
                        var6 = false;
                    }
                    else {
                        int var11 = -1;
                        for (int var12 = 0; var12 < p_179533_1_.áŒŠÆ() && var6; ++var12) {
                            final int var9 = var12 / 3;
                            final int var13 = var12 % 3;
                            final ItemStack var14 = p_179533_1_.á(var12);
                            if (var14 != null && var14.HorizonCode_Horizon_È() != Items.£Ç) {
                                if (var14.HorizonCode_Horizon_È() != Items.áŒŠÔ) {
                                    var6 = false;
                                    break;
                                }
                                if (var11 != -1 && var11 != var14.Ø()) {
                                    var6 = false;
                                    break;
                                }
                                if (var5.Ý()[var9].charAt(var13) == ' ') {
                                    var6 = false;
                                    break;
                                }
                                var11 = var14.Ø();
                            }
                            else if (var5.Ý()[var9].charAt(var13) != ' ') {
                                var6 = false;
                                break;
                            }
                        }
                    }
                    if (var6) {
                        return var5;
                    }
                }
            }
            return null;
        }
        
        HorizonCode_Horizon_È(final Object p_i45780_1_) {
            this();
        }
    }
    
    static class Â implements IRecipe
    {
        private static final String HorizonCode_Horizon_È = "CL_00002157";
        
        private Â() {
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final InventoryCrafting p_77569_1_, final World worldIn) {
            ItemStack var3 = null;
            ItemStack var4 = null;
            for (int var5 = 0; var5 < p_77569_1_.áŒŠÆ(); ++var5) {
                final ItemStack var6 = p_77569_1_.á(var5);
                if (var6 != null) {
                    if (var6.HorizonCode_Horizon_È() != Items.£Ç) {
                        return false;
                    }
                    if (var3 != null && var4 != null) {
                        return false;
                    }
                    final int var7 = TileEntityBanner.Â(var6);
                    final boolean var8 = TileEntityBanner.Ý(var6) > 0;
                    if (var3 != null) {
                        if (var8) {
                            return false;
                        }
                        if (var7 != TileEntityBanner.Â(var3)) {
                            return false;
                        }
                        var4 = var6;
                    }
                    else if (var4 != null) {
                        if (!var8) {
                            return false;
                        }
                        if (var7 != TileEntityBanner.Â(var4)) {
                            return false;
                        }
                        var3 = var6;
                    }
                    else if (var8) {
                        var3 = var6;
                    }
                    else {
                        var4 = var6;
                    }
                }
            }
            return var3 != null && var4 != null;
        }
        
        @Override
        public ItemStack HorizonCode_Horizon_È(final InventoryCrafting p_77572_1_) {
            for (int var2 = 0; var2 < p_77572_1_.áŒŠÆ(); ++var2) {
                final ItemStack var3 = p_77572_1_.á(var2);
                if (var3 != null && TileEntityBanner.Ý(var3) > 0) {
                    final ItemStack var4 = var3.áˆºÑ¢Õ();
                    var4.Â = 1;
                    return var4;
                }
            }
            return null;
        }
        
        @Override
        public int HorizonCode_Horizon_È() {
            return 2;
        }
        
        @Override
        public ItemStack Â() {
            return null;
        }
        
        @Override
        public ItemStack[] Â(final InventoryCrafting p_179532_1_) {
            final ItemStack[] var2 = new ItemStack[p_179532_1_.áŒŠÆ()];
            for (int var3 = 0; var3 < var2.length; ++var3) {
                final ItemStack var4 = p_179532_1_.á(var3);
                if (var4 != null) {
                    if (var4.HorizonCode_Horizon_È().ÂµÈ()) {
                        var2[var3] = new ItemStack(var4.HorizonCode_Horizon_È().áˆºÑ¢Õ());
                    }
                    else if (var4.£á() && TileEntityBanner.Ý(var4) > 0) {
                        var2[var3] = var4.áˆºÑ¢Õ();
                        var2[var3].Â = 1;
                    }
                }
            }
            return var2;
        }
        
        Â(final Object p_i45779_1_) {
            this();
        }
    }
}
