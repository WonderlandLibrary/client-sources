package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.List;

public class TileEntityBanner extends TileEntity
{
    private int Âµá€;
    private NBTTagList Ó;
    private boolean à;
    private List Ø;
    private List áŒŠÆ;
    private String áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00002044";
    
    public void HorizonCode_Horizon_È(final ItemStack p_175112_1_) {
        this.Ó = null;
        if (p_175112_1_.£á() && p_175112_1_.Å().Â("BlockEntityTag", 10)) {
            final NBTTagCompound var2 = p_175112_1_.Å().ˆÏ­("BlockEntityTag");
            if (var2.Ý("Patterns")) {
                this.Ó = (NBTTagList)var2.Ý("Patterns", 10).Â();
            }
            if (var2.Â("Base", 99)) {
                this.Âµá€ = var2.Ó("Base");
            }
            else {
                this.Âµá€ = (p_175112_1_.Ø() & 0xF);
            }
        }
        else {
            this.Âµá€ = (p_175112_1_.Ø() & 0xF);
        }
        this.Ø = null;
        this.áŒŠÆ = null;
        this.áˆºÑ¢Õ = "";
        this.à = true;
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        compound.HorizonCode_Horizon_È("Base", this.Âµá€);
        if (this.Ó != null) {
            compound.HorizonCode_Horizon_È("Patterns", this.Ó);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        this.Âµá€ = compound.Ó("Base");
        this.Ó = compound.Ý("Patterns", 10);
        this.Ø = null;
        this.áŒŠÆ = null;
        this.áˆºÑ¢Õ = null;
        this.à = true;
    }
    
    @Override
    public Packet £á() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.Â(var1);
        return new S35PacketUpdateTileEntity(this.Â, 6, var1);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    public static int Â(final ItemStack stack) {
        final NBTTagCompound var1 = stack.HorizonCode_Horizon_È("BlockEntityTag", false);
        return (var1 != null && var1.Ý("Base")) ? var1.Ó("Base") : stack.Ø();
    }
    
    public static int Ý(final ItemStack p_175113_0_) {
        final NBTTagCompound var1 = p_175113_0_.HorizonCode_Horizon_È("BlockEntityTag", false);
        return (var1 != null && var1.Ý("Patterns")) ? var1.Ý("Patterns", 10).Âµá€() : 0;
    }
    
    public List Â() {
        this.Âµá€();
        return this.Ø;
    }
    
    public List Ý() {
        this.Âµá€();
        return this.áŒŠÆ;
    }
    
    public String Ø­áŒŠá() {
        this.Âµá€();
        return this.áˆºÑ¢Õ;
    }
    
    private void Âµá€() {
        if (this.Ø == null || this.áŒŠÆ == null || this.áˆºÑ¢Õ == null) {
            if (!this.à) {
                this.áˆºÑ¢Õ = "";
            }
            else {
                this.Ø = Lists.newArrayList();
                this.áŒŠÆ = Lists.newArrayList();
                this.Ø.add(HorizonCode_Horizon_È.HorizonCode_Horizon_È);
                this.áŒŠÆ.add(EnumDyeColor.HorizonCode_Horizon_È(this.Âµá€));
                this.áˆºÑ¢Õ = "b" + this.Âµá€;
                if (this.Ó != null) {
                    for (int var1 = 0; var1 < this.Ó.Âµá€(); ++var1) {
                        final NBTTagCompound var2 = this.Ó.Â(var1);
                        final HorizonCode_Horizon_È var3 = HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2.áˆºÑ¢Õ("Pattern"));
                        if (var3 != null) {
                            this.Ø.add(var3);
                            final int var4 = var2.Ó("Color");
                            this.áŒŠÆ.add(EnumDyeColor.HorizonCode_Horizon_È(var4));
                            this.áˆºÑ¢Õ = String.valueOf(this.áˆºÑ¢Õ) + var3.Â() + var4;
                        }
                    }
                }
            }
        }
    }
    
    public static void Ø­áŒŠá(final ItemStack p_175117_0_) {
        final NBTTagCompound var1 = p_175117_0_.HorizonCode_Horizon_È("BlockEntityTag", false);
        if (var1 != null && var1.Â("Patterns", 9)) {
            final NBTTagList var2 = var1.Ý("Patterns", 10);
            if (var2.Âµá€() > 0) {
                var2.HorizonCode_Horizon_È(var2.Âµá€() - 1);
                if (var2.Ý()) {
                    p_175117_0_.Å().Å("BlockEntityTag");
                    if (p_175117_0_.Å().Ý()) {
                        p_175117_0_.Ø­áŒŠá((NBTTagCompound)null);
                    }
                }
            }
        }
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("BASE", 0, "BASE", 0, "base", "b"), 
        Â("SQUARE_BOTTOM_LEFT", 1, "SQUARE_BOTTOM_LEFT", 1, "square_bottom_left", "bl", "   ", "   ", "#  "), 
        Ý("SQUARE_BOTTOM_RIGHT", 2, "SQUARE_BOTTOM_RIGHT", 2, "square_bottom_right", "br", "   ", "   ", "  #"), 
        Ø­áŒŠá("SQUARE_TOP_LEFT", 3, "SQUARE_TOP_LEFT", 3, "square_top_left", "tl", "#  ", "   ", "   "), 
        Âµá€("SQUARE_TOP_RIGHT", 4, "SQUARE_TOP_RIGHT", 4, "square_top_right", "tr", "  #", "   ", "   "), 
        Ó("STRIPE_BOTTOM", 5, "STRIPE_BOTTOM", 5, "stripe_bottom", "bs", "   ", "   ", "###"), 
        à("STRIPE_TOP", 6, "STRIPE_TOP", 6, "stripe_top", "ts", "###", "   ", "   "), 
        Ø("STRIPE_LEFT", 7, "STRIPE_LEFT", 7, "stripe_left", "ls", "#  ", "#  ", "#  "), 
        áŒŠÆ("STRIPE_RIGHT", 8, "STRIPE_RIGHT", 8, "stripe_right", "rs", "  #", "  #", "  #"), 
        áˆºÑ¢Õ("STRIPE_CENTER", 9, "STRIPE_CENTER", 9, "stripe_center", "cs", " # ", " # ", " # "), 
        ÂµÈ("STRIPE_MIDDLE", 10, "STRIPE_MIDDLE", 10, "stripe_middle", "ms", "   ", "###", "   "), 
        á("STRIPE_DOWNRIGHT", 11, "STRIPE_DOWNRIGHT", 11, "stripe_downright", "drs", "#  ", " # ", "  #"), 
        ˆÏ­("STRIPE_DOWNLEFT", 12, "STRIPE_DOWNLEFT", 12, "stripe_downleft", "dls", "  #", " # ", "#  "), 
        £á("STRIPE_SMALL", 13, "STRIPE_SMALL", 13, "small_stripes", "ss", "# #", "# #", "   "), 
        Å("CROSS", 14, "CROSS", 14, "cross", "cr", "# #", " # ", "# #"), 
        £à("STRAIGHT_CROSS", 15, "STRAIGHT_CROSS", 15, "straight_cross", "sc", " # ", "###", " # "), 
        µà("TRIANGLE_BOTTOM", 16, "TRIANGLE_BOTTOM", 16, "triangle_bottom", "bt", "   ", " # ", "# #"), 
        ˆà("TRIANGLE_TOP", 17, "TRIANGLE_TOP", 17, "triangle_top", "tt", "# #", " # ", "   "), 
        ¥Æ("TRIANGLES_BOTTOM", 18, "TRIANGLES_BOTTOM", 18, "triangles_bottom", "bts", "   ", "# #", " # "), 
        Ø­à("TRIANGLES_TOP", 19, "TRIANGLES_TOP", 19, "triangles_top", "tts", " # ", "# #", "   "), 
        µÕ("DIAGONAL_LEFT", 20, "DIAGONAL_LEFT", 20, "diagonal_left", "ld", "## ", "#  ", "   "), 
        Æ("DIAGONAL_RIGHT", 21, "DIAGONAL_RIGHT", 21, "diagonal_up_right", "rd", "   ", "  #", " ##"), 
        Šáƒ("DIAGONAL_LEFT_MIRROR", 22, "DIAGONAL_LEFT_MIRROR", 22, "diagonal_up_left", "lud", "   ", "#  ", "## "), 
        Ï­Ðƒà("DIAGONAL_RIGHT_MIRROR", 23, "DIAGONAL_RIGHT_MIRROR", 23, "diagonal_right", "rud", " ##", "  #", "   "), 
        áŒŠà("CIRCLE_MIDDLE", 24, "CIRCLE_MIDDLE", 24, "circle", "mc", "   ", " # ", "   "), 
        ŠÄ("RHOMBUS_MIDDLE", 25, "RHOMBUS_MIDDLE", 25, "rhombus", "mr", " # ", "# #", " # "), 
        Ñ¢á("HALF_VERTICAL", 26, "HALF_VERTICAL", 26, "half_vertical", "vh", "## ", "## ", "## "), 
        ŒÏ("HALF_HORIZONTAL", 27, "HALF_HORIZONTAL", 27, "half_horizontal", "hh", "###", "###", "   "), 
        Çªà¢("HALF_VERTICAL_MIRROR", 28, "HALF_VERTICAL_MIRROR", 28, "half_vertical_right", "vhr", " ##", " ##", " ##"), 
        Ê("HALF_HORIZONTAL_MIRROR", 29, "HALF_HORIZONTAL_MIRROR", 29, "half_horizontal_bottom", "hhb", "   ", "###", "###"), 
        ÇŽÉ("BORDER", 30, "BORDER", 30, "border", "bo", "###", "# #", "###"), 
        ˆá("CURLY_BORDER", 31, "CURLY_BORDER", 31, "curly_border", "cbo", new ItemStack(Blocks.ÇŽà)), 
        ÇŽÕ("CREEPER", 32, "CREEPER", 32, "creeper", "cre", new ItemStack(Items.ˆ, 1, 4)), 
        É("GRADIENT", 33, "GRADIENT", 33, "gradient", "gra", "# #", " # ", " # "), 
        áƒ("GRADIENT_UP", 34, "GRADIENT_UP", 34, "gradient_up", "gru", " # ", " # ", "# #"), 
        á€("BRICKS", 35, "BRICKS", 35, "bricks", "bri", new ItemStack(Blocks.Ä)), 
        Õ("SKULL", 36, "SKULL", 36, "skull", "sku", new ItemStack(Items.ˆ, 1, 1)), 
        à¢("FLOWER", 37, "FLOWER", 37, "flower", "flo", new ItemStack(Blocks.Ç, 1, BlockFlower.Â.áˆºÑ¢Õ.Ý())), 
        ŠÂµà("MOJANG", 38, "MOJANG", 38, "mojang", "moj", new ItemStack(Items.£Õ, 1, 1));
        
        private String ¥à;
        private String Âµà;
        private String[] Ç;
        private ItemStack È;
        private static final HorizonCode_Horizon_È[] áŠ;
        private static final String ˆáŠ = "CL_00002043";
        
        static {
            áŒŠ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø, HorizonCode_Horizon_È.áŒŠÆ, HorizonCode_Horizon_È.áˆºÑ¢Õ, HorizonCode_Horizon_È.ÂµÈ, HorizonCode_Horizon_È.á, HorizonCode_Horizon_È.ˆÏ­, HorizonCode_Horizon_È.£á, HorizonCode_Horizon_È.Å, HorizonCode_Horizon_È.£à, HorizonCode_Horizon_È.µà, HorizonCode_Horizon_È.ˆà, HorizonCode_Horizon_È.¥Æ, HorizonCode_Horizon_È.Ø­à, HorizonCode_Horizon_È.µÕ, HorizonCode_Horizon_È.Æ, HorizonCode_Horizon_È.Šáƒ, HorizonCode_Horizon_È.Ï­Ðƒà, HorizonCode_Horizon_È.áŒŠà, HorizonCode_Horizon_È.ŠÄ, HorizonCode_Horizon_È.Ñ¢á, HorizonCode_Horizon_È.ŒÏ, HorizonCode_Horizon_È.Çªà¢, HorizonCode_Horizon_È.Ê, HorizonCode_Horizon_È.ÇŽÉ, HorizonCode_Horizon_È.ˆá, HorizonCode_Horizon_È.ÇŽÕ, HorizonCode_Horizon_È.É, HorizonCode_Horizon_È.áƒ, HorizonCode_Horizon_È.á€, HorizonCode_Horizon_È.Õ, HorizonCode_Horizon_È.à¢, HorizonCode_Horizon_È.ŠÂµà };
            áŠ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø, HorizonCode_Horizon_È.áŒŠÆ, HorizonCode_Horizon_È.áˆºÑ¢Õ, HorizonCode_Horizon_È.ÂµÈ, HorizonCode_Horizon_È.á, HorizonCode_Horizon_È.ˆÏ­, HorizonCode_Horizon_È.£á, HorizonCode_Horizon_È.Å, HorizonCode_Horizon_È.£à, HorizonCode_Horizon_È.µà, HorizonCode_Horizon_È.ˆà, HorizonCode_Horizon_È.¥Æ, HorizonCode_Horizon_È.Ø­à, HorizonCode_Horizon_È.µÕ, HorizonCode_Horizon_È.Æ, HorizonCode_Horizon_È.Šáƒ, HorizonCode_Horizon_È.Ï­Ðƒà, HorizonCode_Horizon_È.áŒŠà, HorizonCode_Horizon_È.ŠÄ, HorizonCode_Horizon_È.Ñ¢á, HorizonCode_Horizon_È.ŒÏ, HorizonCode_Horizon_È.Çªà¢, HorizonCode_Horizon_È.Ê, HorizonCode_Horizon_È.ÇŽÉ, HorizonCode_Horizon_È.ˆá, HorizonCode_Horizon_È.ÇŽÕ, HorizonCode_Horizon_È.É, HorizonCode_Horizon_È.áƒ, HorizonCode_Horizon_È.á€, HorizonCode_Horizon_È.Õ, HorizonCode_Horizon_È.à¢, HorizonCode_Horizon_È.ŠÂµà };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45670_1_, final int p_i45670_2_, final String p_i45670_3_, final String p_i45670_4_) {
            this.Ç = new String[3];
            this.¥à = p_i45670_3_;
            this.Âµà = p_i45670_4_;
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45671_1_, final int p_i45671_2_, final String p_i45671_3_, final String p_i45671_4_, final ItemStack p_i45671_5_) {
            this(s, n, p_i45671_1_, p_i45671_2_, p_i45671_3_, p_i45671_4_);
            this.È = p_i45671_5_;
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45672_1_, final int p_i45672_2_, final String p_i45672_3_, final String p_i45672_4_, final String p_i45672_5_, final String p_i45672_6_, final String p_i45672_7_) {
            this(s, n, p_i45672_1_, p_i45672_2_, p_i45672_3_, p_i45672_4_);
            this.Ç[0] = p_i45672_5_;
            this.Ç[1] = p_i45672_6_;
            this.Ç[2] = p_i45672_7_;
        }
        
        public String HorizonCode_Horizon_È() {
            return this.¥à;
        }
        
        public String Â() {
            return this.Âµà;
        }
        
        public String[] Ý() {
            return this.Ç;
        }
        
        public boolean Ø­áŒŠá() {
            return this.È != null || this.Ç[0] != null;
        }
        
        public boolean Âµá€() {
            return this.È != null;
        }
        
        public ItemStack Ó() {
            return this.È;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_177268_0_) {
            for (final HorizonCode_Horizon_È var4 : values()) {
                if (var4.Âµà.equals(p_177268_0_)) {
                    return var4;
                }
            }
            return null;
        }
    }
}
