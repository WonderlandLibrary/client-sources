package net.minecraft.src;

public class StatCollector
{
    private static StringTranslate localizedName;
    
    static {
        StatCollector.localizedName = StringTranslate.getInstance();
    }
    
    public static String translateToLocal(final String par0Str) {
        return StatCollector.localizedName.translateKey(par0Str);
    }
    
    public static String translateToLocalFormatted(final String par0Str, final Object... par1ArrayOfObj) {
        return StatCollector.localizedName.translateKeyFormat(par0Str, par1ArrayOfObj);
    }
    
    public static boolean func_94522_b(final String par0Str) {
        return StatCollector.localizedName.containsTranslateKey(par0Str);
    }
}
