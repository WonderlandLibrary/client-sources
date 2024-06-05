package net.minecraft.tileentity;

import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public enum EnumBannerPattern
{
    BASE("BASE", 0, "base", "b"), 
    SQUARE_BOTTOM_LEFT("SQUARE_BOTTOM_LEFT", 1, "square_bottom_left", "bl", "   ", "   ", "#  "), 
    SQUARE_BOTTOM_RIGHT("SQUARE_BOTTOM_RIGHT", 2, "square_bottom_right", "br", "   ", "   ", "  #"), 
    SQUARE_TOP_LEFT("SQUARE_TOP_LEFT", 3, "square_top_left", "tl", "#  ", "   ", "   "), 
    SQUARE_TOP_RIGHT("SQUARE_TOP_RIGHT", 4, "square_top_right", "tr", "  #", "   ", "   "), 
    STRIPE_BOTTOM("STRIPE_BOTTOM", 5, "stripe_bottom", "bs", "   ", "   ", "###"), 
    STRIPE_TOP("STRIPE_TOP", 6, "stripe_top", "ts", "###", "   ", "   "), 
    STRIPE_LEFT("STRIPE_LEFT", 7, "stripe_left", "ls", "#  ", "#  ", "#  "), 
    STRIPE_RIGHT("STRIPE_RIGHT", 8, "stripe_right", "rs", "  #", "  #", "  #"), 
    STRIPE_CENTER("STRIPE_CENTER", 9, "stripe_center", "cs", " # ", " # ", " # "), 
    STRIPE_MIDDLE("STRIPE_MIDDLE", 10, "stripe_middle", "ms", "   ", "###", "   "), 
    STRIPE_DOWNRIGHT("STRIPE_DOWNRIGHT", 11, "stripe_downright", "drs", "#  ", " # ", "  #"), 
    STRIPE_DOWNLEFT("STRIPE_DOWNLEFT", 12, "stripe_downleft", "dls", "  #", " # ", "#  "), 
    STRIPE_SMALL("STRIPE_SMALL", 13, "small_stripes", "ss", "# #", "# #", "   "), 
    CROSS("CROSS", 14, "cross", "cr", "# #", " # ", "# #"), 
    STRAIGHT_CROSS("STRAIGHT_CROSS", 15, "straight_cross", "sc", " # ", "###", " # "), 
    TRIANGLE_BOTTOM("TRIANGLE_BOTTOM", 16, "triangle_bottom", "bt", "   ", " # ", "# #"), 
    TRIANGLE_TOP("TRIANGLE_TOP", 17, "triangle_top", "tt", "# #", " # ", "   "), 
    TRIANGLES_BOTTOM("TRIANGLES_BOTTOM", 18, "triangles_bottom", "bts", "   ", "# #", " # "), 
    TRIANGLES_TOP("TRIANGLES_TOP", 19, "triangles_top", "tts", " # ", "# #", "   "), 
    DIAGONAL_LEFT("DIAGONAL_LEFT", 20, "diagonal_left", "ld", "## ", "#  ", "   "), 
    DIAGONAL_RIGHT("DIAGONAL_RIGHT", 21, "diagonal_up_right", "rd", "   ", "  #", " ##"), 
    DIAGONAL_LEFT_MIRROR("DIAGONAL_LEFT_MIRROR", 22, "diagonal_up_left", "lud", "   ", "#  ", "## "), 
    DIAGONAL_RIGHT_MIRROR("DIAGONAL_RIGHT_MIRROR", 23, "diagonal_right", "rud", " ##", "  #", "   "), 
    CIRCLE_MIDDLE("CIRCLE_MIDDLE", 24, "circle", "mc", "   ", " # ", "   "), 
    RHOMBUS_MIDDLE("RHOMBUS_MIDDLE", 25, "rhombus", "mr", " # ", "# #", " # "), 
    HALF_VERTICAL("HALF_VERTICAL", 26, "half_vertical", "vh", "## ", "## ", "## "), 
    HALF_HORIZONTAL("HALF_HORIZONTAL", 27, "half_horizontal", "hh", "###", "###", "   "), 
    HALF_VERTICAL_MIRROR("HALF_VERTICAL_MIRROR", 28, "half_vertical_right", "vhr", " ##", " ##", " ##"), 
    HALF_HORIZONTAL_MIRROR("HALF_HORIZONTAL_MIRROR", 29, "half_horizontal_bottom", "hhb", "   ", "###", "###"), 
    BORDER("BORDER", 30, "border", "bo", "###", "# #", "###"), 
    CURLY_BORDER("CURLY_BORDER", 31, "curly_border", "cbo", new ItemStack(Blocks.vine)), 
    CREEPER("CREEPER", 32, "creeper", "cre", new ItemStack(Items.skull, 1, 4)), 
    GRADIENT("GRADIENT", 33, "gradient", "gra", "# #", " # ", " # "), 
    GRADIENT_UP("GRADIENT_UP", 34, "gradient_up", "gru", " # ", " # ", "# #"), 
    BRICKS("BRICKS", 35, "bricks", "bri", new ItemStack(Blocks.brick_block)), 
    SKULL("SKULL", 36, "skull", "sku", new ItemStack(Items.skull, 1, 1)), 
    FLOWER("FLOWER", 37, "flower", "flo", new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.func_176968_b())), 
    MOJANG("MOJANG", 38, "mojang", "moj", new ItemStack(Items.golden_apple, 1, 1));
    
    private static final EnumBannerPattern[] $VALUES;
    private String field_177284_N;
    private String field_177285_O;
    private String[] field_177291_P;
    private ItemStack field_177290_Q;
    
    private EnumBannerPattern(final String p_i45670_1_, final int p_i45670_2_, final String p_i45670_3_, final String p_i45670_4_) {
        this.field_177291_P = new String[3];
        this.field_177284_N = p_i45670_3_;
        this.field_177285_O = p_i45670_4_;
    }
    
    private EnumBannerPattern(final String p_i45671_1_, final int p_i45671_2_, final String p_i45671_3_, final String p_i45671_4_, final ItemStack p_i45671_5_) {
        this(p_i45671_1_, p_i45671_2_, p_i45671_3_, p_i45671_4_);
        this.field_177290_Q = p_i45671_5_;
    }
    
    private EnumBannerPattern(final String p_i45672_1_, final int p_i45672_2_, final String p_i45672_3_, final String p_i45672_4_, final String p_i45672_5_, final String p_i45672_6_, final String p_i45672_7_) {
        this(p_i45672_1_, p_i45672_2_, p_i45672_3_, p_i45672_4_);
        this.field_177291_P[0] = p_i45672_5_;
        this.field_177291_P[1] = p_i45672_6_;
        this.field_177291_P[2] = p_i45672_7_;
    }
    
    public static EnumBannerPattern func_177268_a(final String p_177268_0_) {
        for (final EnumBannerPattern var4 : values()) {
            if (var4.field_177285_O.equals(p_177268_0_)) {
                return var4;
            }
        }
        return null;
    }
    
    public String func_177271_a() {
        return this.field_177284_N;
    }
    
    public String func_177273_b() {
        return this.field_177285_O;
    }
    
    public String[] func_177267_c() {
        return this.field_177291_P;
    }
    
    public boolean func_177270_d() {
        return this.field_177290_Q != null || this.field_177291_P[0] != null;
    }
    
    public boolean func_177269_e() {
        return this.field_177290_Q != null;
    }
    
    public ItemStack func_177272_f() {
        return this.field_177290_Q;
    }
    
    static {
        $VALUES = new EnumBannerPattern[] { EnumBannerPattern.BASE, EnumBannerPattern.SQUARE_BOTTOM_LEFT, EnumBannerPattern.SQUARE_BOTTOM_RIGHT, EnumBannerPattern.SQUARE_TOP_LEFT, EnumBannerPattern.SQUARE_TOP_RIGHT, EnumBannerPattern.STRIPE_BOTTOM, EnumBannerPattern.STRIPE_TOP, EnumBannerPattern.STRIPE_LEFT, EnumBannerPattern.STRIPE_RIGHT, EnumBannerPattern.STRIPE_CENTER, EnumBannerPattern.STRIPE_MIDDLE, EnumBannerPattern.STRIPE_DOWNRIGHT, EnumBannerPattern.STRIPE_DOWNLEFT, EnumBannerPattern.STRIPE_SMALL, EnumBannerPattern.CROSS, EnumBannerPattern.STRAIGHT_CROSS, EnumBannerPattern.TRIANGLE_BOTTOM, EnumBannerPattern.TRIANGLE_TOP, EnumBannerPattern.TRIANGLES_BOTTOM, EnumBannerPattern.TRIANGLES_TOP, EnumBannerPattern.DIAGONAL_LEFT, EnumBannerPattern.DIAGONAL_RIGHT, EnumBannerPattern.DIAGONAL_LEFT_MIRROR, EnumBannerPattern.DIAGONAL_RIGHT_MIRROR, EnumBannerPattern.CIRCLE_MIDDLE, EnumBannerPattern.RHOMBUS_MIDDLE, EnumBannerPattern.HALF_VERTICAL, EnumBannerPattern.HALF_HORIZONTAL, EnumBannerPattern.HALF_VERTICAL_MIRROR, EnumBannerPattern.HALF_HORIZONTAL_MIRROR, EnumBannerPattern.BORDER, EnumBannerPattern.CURLY_BORDER, EnumBannerPattern.CREEPER, EnumBannerPattern.GRADIENT, EnumBannerPattern.GRADIENT_UP, EnumBannerPattern.BRICKS, EnumBannerPattern.SKULL, EnumBannerPattern.FLOWER, EnumBannerPattern.MOJANG };
    }
}
