package net.minecraft.src;

import java.util.regex.*;
import java.util.*;

public enum EnumChatFormatting
{
    BLACK("BLACK", 0, '0'), 
    DARK_BLUE("DARK_BLUE", 1, '1'), 
    DARK_GREEN("DARK_GREEN", 2, '2'), 
    DARK_AQUA("DARK_AQUA", 3, '3'), 
    DARK_RED("DARK_RED", 4, '4'), 
    DARK_PURPLE("DARK_PURPLE", 5, '5'), 
    GOLD("GOLD", 6, '6'), 
    GRAY("GRAY", 7, '7'), 
    DARK_GRAY("DARK_GRAY", 8, '8'), 
    BLUE("BLUE", 9, '9'), 
    GREEN("GREEN", 10, 'a'), 
    AQUA("AQUA", 11, 'b'), 
    RED("RED", 12, 'c'), 
    LIGHT_PURPLE("LIGHT_PURPLE", 13, 'd'), 
    YELLOW("YELLOW", 14, 'e'), 
    WHITE("WHITE", 15, 'f'), 
    OBFUSCATED("OBFUSCATED", 16, 'k', true), 
    BOLD("BOLD", 17, 'l', true), 
    STRIKETHROUGH("STRIKETHROUGH", 18, 'm', true), 
    UNDERLINE("UNDERLINE", 19, 'n', true), 
    ITALIC("ITALIC", 20, 'o', true), 
    RESET("RESET", 21, 'r');
    
    private static final Map field_96321_w;
    private static final Map field_96331_x;
    private static final Pattern field_96330_y;
    private final char field_96329_z;
    private final boolean field_96303_A;
    private final String field_96304_B;
    
    static {
        field_96321_w = new HashMap();
        field_96331_x = new HashMap();
        field_96330_y = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
        for (final EnumChatFormatting var4 : values()) {
            EnumChatFormatting.field_96321_w.put(var4.func_96298_a(), var4);
            EnumChatFormatting.field_96331_x.put(var4.func_96297_d(), var4);
        }
    }
    
    private EnumChatFormatting(final String s, final int n, final char par3) {
        this(s, n, par3, false);
    }
    
    private EnumChatFormatting(final String s, final int n, final char par3, final boolean par4) {
        this.field_96329_z = par3;
        this.field_96303_A = par4;
        this.field_96304_B = "§" + par3;
    }
    
    public char func_96298_a() {
        return this.field_96329_z;
    }
    
    public boolean func_96301_b() {
        return this.field_96303_A;
    }
    
    public boolean func_96302_c() {
        return !this.field_96303_A && this != EnumChatFormatting.RESET;
    }
    
    public String func_96297_d() {
        return this.name().toLowerCase();
    }
    
    @Override
    public String toString() {
        return this.field_96304_B;
    }
    
    public static EnumChatFormatting func_96300_b(final String par0Str) {
        return (par0Str == null) ? null : EnumChatFormatting.field_96331_x.get(par0Str.toLowerCase());
    }
    
    public static Collection func_96296_a(final boolean par0, final boolean par1) {
        final ArrayList var2 = new ArrayList();
        for (final EnumChatFormatting var6 : values()) {
            if ((!var6.func_96302_c() || par0) && (!var6.func_96301_b() || par1)) {
                var2.add(var6.func_96297_d());
            }
        }
        return var2;
    }
}
