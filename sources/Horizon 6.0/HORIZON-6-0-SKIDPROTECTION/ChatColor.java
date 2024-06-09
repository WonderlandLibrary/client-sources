package HORIZON-6-0-SKIDPROTECTION;

import org.apache.commons.lang3.Validate;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.regex.Pattern;

public enum ChatColor
{
    HorizonCode_Horizon_È("BLACK", 0, '0', 0), 
    Â("DARK_BLUE", 1, '1', 1), 
    Ý("DARK_GREEN", 2, '2', 2), 
    Ø­áŒŠá("DARK_AQUA", 3, '3', 3), 
    Âµá€("DARK_RED", 4, '4', 4), 
    Ó("DARK_PURPLE", 5, '5', 5), 
    à("GOLD", 6, '6', 6), 
    Ø("GRAY", 7, '7', 7), 
    áŒŠÆ("DARK_GRAY", 8, '8', 8), 
    áˆºÑ¢Õ("BLUE", 9, '9', 9), 
    ÂµÈ("GREEN", 10, 'a', 10), 
    á("AQUA", 11, 'b', 11), 
    ˆÏ­("RED", 12, 'c', 12), 
    £á("LIGHT_PURPLE", 13, 'd', 13), 
    Å("YELLOW", 14, 'e', 14), 
    £à("WHITE", 15, 'f', 15), 
    µà("MAGIC", 16, 'k', 16, true), 
    ˆà("BOLD", 17, 'l', 17, true), 
    ¥Æ("STRIKETHROUGH", 18, 'm', 18, true), 
    Ø­à("UNDERLINE", 19, 'n', 19, true), 
    µÕ("ITALIC", 20, 'o', 20, true), 
    Æ("RESET", 21, 'r', 21);
    
    public static final char Šáƒ = '§';
    private static final Pattern Ï­Ðƒà;
    private final int áŒŠà;
    private final char ŠÄ;
    private final boolean Ñ¢á;
    private final String ŒÏ;
    private static final Map<Integer, ChatColor> Çªà¢;
    private static final Map<Character, ChatColor> Ê;
    
    static {
        ÇŽÉ = new ChatColor[] { ChatColor.HorizonCode_Horizon_È, ChatColor.Â, ChatColor.Ý, ChatColor.Ø­áŒŠá, ChatColor.Âµá€, ChatColor.Ó, ChatColor.à, ChatColor.Ø, ChatColor.áŒŠÆ, ChatColor.áˆºÑ¢Õ, ChatColor.ÂµÈ, ChatColor.á, ChatColor.ˆÏ­, ChatColor.£á, ChatColor.Å, ChatColor.£à, ChatColor.µà, ChatColor.ˆà, ChatColor.¥Æ, ChatColor.Ø­à, ChatColor.µÕ, ChatColor.Æ };
        Ï­Ðƒà = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
        Çªà¢ = Maps.newHashMap();
        Ê = Maps.newHashMap();
        for (final ChatColor color : values()) {
            ChatColor.Çªà¢.put(color.áŒŠà, color);
            ChatColor.Ê.put(color.ŠÄ, color);
        }
    }
    
    private ChatColor(final String s, final int n, final char code, final int intCode) {
        this(s, n, code, intCode, false);
    }
    
    private ChatColor(final String s, final int n, final char code, final int intCode, final boolean isFormat) {
        this.ŠÄ = code;
        this.áŒŠà = intCode;
        this.Ñ¢á = isFormat;
        this.ŒÏ = new String(new char[] { '§', code });
    }
    
    public char HorizonCode_Horizon_È() {
        return this.ŠÄ;
    }
    
    @Override
    public String toString() {
        return this.ŒÏ;
    }
    
    public boolean Â() {
        return this.Ñ¢á;
    }
    
    public boolean Ý() {
        return !this.Ñ¢á && this != ChatColor.Æ;
    }
    
    public static ChatColor HorizonCode_Horizon_È(final char code) {
        return ChatColor.Ê.get(code);
    }
    
    public static ChatColor HorizonCode_Horizon_È(final String code) {
        Validate.notNull((Object)code, "Code cannot be null", new Object[0]);
        Validate.isTrue(code.length() > 0, "Code must have at least one char", new Object[0]);
        return ChatColor.Ê.get(code.charAt(0));
    }
    
    public static String Â(final String input) {
        return (input == null) ? null : ChatColor.Ï­Ðƒà.matcher(input).replaceAll("");
    }
    
    public static String HorizonCode_Horizon_È(final char altColorChar, final String textToTranslate) {
        final char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; ++i) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = '§';
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
    
    public static String Ý(final String input) {
        String result = "";
        final int length = input.length();
        for (int index = length - 1; index > -1; --index) {
            final char section = input.charAt(index);
            if (section == '§' && index < length - 1) {
                final char c = input.charAt(index + 1);
                final ChatColor color = HorizonCode_Horizon_È(c);
                if (color != null) {
                    result = String.valueOf(color.toString()) + result;
                    if (color.Ý()) {
                        break;
                    }
                    if (color.equals(ChatColor.Æ)) {
                        break;
                    }
                }
            }
        }
        return result;
    }
}
