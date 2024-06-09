package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.Collection;
import com.google.common.collect.Maps;
import java.util.regex.Pattern;
import java.util.Map;

public enum EnumChatFormatting
{
    HorizonCode_Horizon_È("BLACK", 0, "BLACK", 0, "BLACK", '0', 0), 
    Â("DARK_BLUE", 1, "DARK_BLUE", 1, "DARK_BLUE", '1', 1), 
    Ý("DARK_GREEN", 2, "DARK_GREEN", 2, "DARK_GREEN", '2', 2), 
    Ø­áŒŠá("DARK_AQUA", 3, "DARK_AQUA", 3, "DARK_AQUA", '3', 3), 
    Âµá€("DARK_RED", 4, "DARK_RED", 4, "DARK_RED", '4', 4), 
    Ó("DARK_PURPLE", 5, "DARK_PURPLE", 5, "DARK_PURPLE", '5', 5), 
    à("GOLD", 6, "GOLD", 6, "GOLD", '6', 6), 
    Ø("GRAY", 7, "GRAY", 7, "GRAY", '7', 7), 
    áŒŠÆ("DARK_GRAY", 8, "DARK_GRAY", 8, "DARK_GRAY", '8', 8), 
    áˆºÑ¢Õ("BLUE", 9, "BLUE", 9, "BLUE", '9', 9), 
    ÂµÈ("GREEN", 10, "GREEN", 10, "GREEN", 'a', 10), 
    á("AQUA", 11, "AQUA", 11, "AQUA", 'b', 11), 
    ˆÏ­("RED", 12, "RED", 12, "RED", 'c', 12), 
    £á("LIGHT_PURPLE", 13, "LIGHT_PURPLE", 13, "LIGHT_PURPLE", 'd', 13), 
    Å("YELLOW", 14, "YELLOW", 14, "YELLOW", 'e', 14), 
    £à("WHITE", 15, "WHITE", 15, "WHITE", 'f', 15), 
    µà("OBFUSCATED", 16, "OBFUSCATED", 16, "OBFUSCATED", 'k', true), 
    ˆà("BOLD", 17, "BOLD", 17, "BOLD", 'l', true), 
    ¥Æ("STRIKETHROUGH", 18, "STRIKETHROUGH", 18, "STRIKETHROUGH", 'm', true), 
    Ø­à("UNDERLINE", 19, "UNDERLINE", 19, "UNDERLINE", 'n', true), 
    µÕ("ITALIC", 20, "ITALIC", 20, "ITALIC", 'o', true), 
    Æ("RESET", 21, "RESET", 21, "RESET", 'r', -1);
    
    private static final Map Šáƒ;
    private static final Pattern Ï­Ðƒà;
    private final String áŒŠà;
    private final char ŠÄ;
    private final boolean Ñ¢á;
    private final String ŒÏ;
    private final int Çªà¢;
    private static final EnumChatFormatting[] Ê;
    private static final String ÇŽÉ = "CL_00000342";
    
    static {
        ˆá = new EnumChatFormatting[] { EnumChatFormatting.HorizonCode_Horizon_È, EnumChatFormatting.Â, EnumChatFormatting.Ý, EnumChatFormatting.Ø­áŒŠá, EnumChatFormatting.Âµá€, EnumChatFormatting.Ó, EnumChatFormatting.à, EnumChatFormatting.Ø, EnumChatFormatting.áŒŠÆ, EnumChatFormatting.áˆºÑ¢Õ, EnumChatFormatting.ÂµÈ, EnumChatFormatting.á, EnumChatFormatting.ˆÏ­, EnumChatFormatting.£á, EnumChatFormatting.Å, EnumChatFormatting.£à, EnumChatFormatting.µà, EnumChatFormatting.ˆà, EnumChatFormatting.¥Æ, EnumChatFormatting.Ø­à, EnumChatFormatting.µÕ, EnumChatFormatting.Æ };
        Šáƒ = Maps.newHashMap();
        Ï­Ðƒà = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
        Ê = new EnumChatFormatting[] { EnumChatFormatting.HorizonCode_Horizon_È, EnumChatFormatting.Â, EnumChatFormatting.Ý, EnumChatFormatting.Ø­áŒŠá, EnumChatFormatting.Âµá€, EnumChatFormatting.Ó, EnumChatFormatting.à, EnumChatFormatting.Ø, EnumChatFormatting.áŒŠÆ, EnumChatFormatting.áˆºÑ¢Õ, EnumChatFormatting.ÂµÈ, EnumChatFormatting.á, EnumChatFormatting.ˆÏ­, EnumChatFormatting.£á, EnumChatFormatting.Å, EnumChatFormatting.£à, EnumChatFormatting.µà, EnumChatFormatting.ˆà, EnumChatFormatting.¥Æ, EnumChatFormatting.Ø­à, EnumChatFormatting.µÕ, EnumChatFormatting.Æ };
        for (final EnumChatFormatting var4 : values()) {
            EnumChatFormatting.Šáƒ.put(Ý(var4.áŒŠà), var4);
        }
    }
    
    private static String Ý(final String p_175745_0_) {
        return p_175745_0_.toLowerCase().replaceAll("[^a-z]", "");
    }
    
    private EnumChatFormatting(final String s, final int n, final String p_i46291_1_, final int p_i46291_2_, final String p_i46291_3_, final char p_i46291_4_, final int p_i46291_5_) {
        this(s, n, p_i46291_1_, p_i46291_2_, p_i46291_3_, p_i46291_4_, false, p_i46291_5_);
    }
    
    private EnumChatFormatting(final String s, final int n, final String p_i46292_1_, final int p_i46292_2_, final String p_i46292_3_, final char p_i46292_4_, final boolean p_i46292_5_) {
        this(s, n, p_i46292_1_, p_i46292_2_, p_i46292_3_, p_i46292_4_, p_i46292_5_, -1);
    }
    
    private EnumChatFormatting(final String s, final int n, final String p_i46293_1_, final int p_i46293_2_, final String p_i46293_3_, final char p_i46293_4_, final boolean p_i46293_5_, final int p_i46293_6_) {
        this.áŒŠà = p_i46293_3_;
        this.ŠÄ = p_i46293_4_;
        this.Ñ¢á = p_i46293_5_;
        this.Çªà¢ = p_i46293_6_;
        this.ŒÏ = "§" + p_i46293_4_;
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Çªà¢;
    }
    
    public boolean Â() {
        return this.Ñ¢á;
    }
    
    public boolean Ý() {
        return !this.Ñ¢á && this != EnumChatFormatting.Æ;
    }
    
    public String Ø­áŒŠá() {
        return this.name().toLowerCase();
    }
    
    @Override
    public String toString() {
        return this.ŒÏ;
    }
    
    public static String HorizonCode_Horizon_È(final String p_110646_0_) {
        return (p_110646_0_ == null) ? null : EnumChatFormatting.Ï­Ðƒà.matcher(p_110646_0_).replaceAll("");
    }
    
    public static EnumChatFormatting Â(final String p_96300_0_) {
        return (p_96300_0_ == null) ? null : EnumChatFormatting.Šáƒ.get(Ý(p_96300_0_));
    }
    
    public static EnumChatFormatting HorizonCode_Horizon_È(final int p_175744_0_) {
        if (p_175744_0_ < 0) {
            return EnumChatFormatting.Æ;
        }
        for (final EnumChatFormatting var4 : values()) {
            if (var4.HorizonCode_Horizon_È() == p_175744_0_) {
                return var4;
            }
        }
        return null;
    }
    
    public static Collection HorizonCode_Horizon_È(final boolean p_96296_0_, final boolean p_96296_1_) {
        final ArrayList var2 = Lists.newArrayList();
        for (final EnumChatFormatting var6 : values()) {
            if ((!var6.Ý() || p_96296_0_) && (!var6.Â() || p_96296_1_)) {
                var2.add(var6.Ø­áŒŠá());
            }
        }
        return var2;
    }
}
