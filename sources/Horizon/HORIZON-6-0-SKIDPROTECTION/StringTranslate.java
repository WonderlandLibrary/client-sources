package HORIZON-6-0-SKIDPROTECTION;

import java.util.IllegalFormatException;
import java.util.Iterator;
import java.io.InputStream;
import java.io.IOException;
import com.google.common.collect.Iterables;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.Charsets;
import com.google.common.collect.Maps;
import java.util.Map;
import com.google.common.base.Splitter;
import java.util.regex.Pattern;

public class StringTranslate
{
    private static final Pattern HorizonCode_Horizon_È;
    private static final Splitter Â;
    private static StringTranslate Ý;
    private final Map Ø­áŒŠá;
    private long Âµá€;
    private static final String Ó = "CL_00001212";
    
    static {
        HorizonCode_Horizon_È = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
        Â = Splitter.on('=').limit(2);
        StringTranslate.Ý = new StringTranslate();
    }
    
    public StringTranslate() {
        this.Ø­áŒŠá = Maps.newHashMap();
        try {
            final InputStream var1 = StringTranslate.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang");
            for (final String var3 : IOUtils.readLines(var1, Charsets.UTF_8)) {
                if (!var3.isEmpty() && var3.charAt(0) != '#') {
                    final String[] var4 = (String[])Iterables.toArray(StringTranslate.Â.split((CharSequence)var3), (Class)String.class);
                    if (var4 == null || var4.length != 2) {
                        continue;
                    }
                    final String var5 = var4[0];
                    final String var6 = StringTranslate.HorizonCode_Horizon_È.matcher(var4[1]).replaceAll("%$1s");
                    this.Ø­áŒŠá.put(var5, var6);
                }
            }
            this.Âµá€ = System.currentTimeMillis();
        }
        catch (IOException ex) {}
    }
    
    static StringTranslate HorizonCode_Horizon_È() {
        return StringTranslate.Ý;
    }
    
    public static synchronized void HorizonCode_Horizon_È(final Map p_135063_0_) {
        StringTranslate.Ý.Ø­áŒŠá.clear();
        StringTranslate.Ý.Ø­áŒŠá.putAll(p_135063_0_);
        StringTranslate.Ý.Âµá€ = System.currentTimeMillis();
    }
    
    public synchronized String HorizonCode_Horizon_È(final String p_74805_1_) {
        return this.Ý(p_74805_1_);
    }
    
    public synchronized String HorizonCode_Horizon_È(final String p_74803_1_, final Object... p_74803_2_) {
        final String var3 = this.Ý(p_74803_1_);
        try {
            return String.format(var3, p_74803_2_);
        }
        catch (IllegalFormatException var4) {
            return "Format error: " + var3;
        }
    }
    
    private String Ý(final String p_135064_1_) {
        final String var2 = this.Ø­áŒŠá.get(p_135064_1_);
        return (var2 == null) ? p_135064_1_ : var2;
    }
    
    public synchronized boolean Â(final String p_94520_1_) {
        return this.Ø­áŒŠá.containsKey(p_94520_1_);
    }
    
    public long Â() {
        return this.Âµá€;
    }
}
