package HORIZON-6-0-SKIDPROTECTION;

public class ChatAllowedCharacters
{
    public static final char[] HorizonCode_Horizon_È;
    private static final String Â = "CL_00001606";
    
    static {
        HorizonCode_Horizon_È = new char[] { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
    }
    
    public static boolean HorizonCode_Horizon_È(final char character) {
        return character != '§' && character >= ' ' && character != '\u007f';
    }
    
    public static String HorizonCode_Horizon_È(final String input) {
        final StringBuilder var1 = new StringBuilder();
        for (final char var5 : input.toCharArray()) {
            if (HorizonCode_Horizon_È(var5)) {
                var1.append(var5);
            }
        }
        return var1.toString();
    }
}
