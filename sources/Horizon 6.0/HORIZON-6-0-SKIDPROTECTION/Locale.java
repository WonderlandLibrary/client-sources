package HORIZON-6-0-SKIDPROTECTION;

import java.util.IllegalFormatException;
import com.google.common.collect.Iterables;
import org.apache.commons.io.Charsets;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import java.util.Iterator;
import java.io.IOException;
import java.util.List;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.regex.Pattern;
import com.google.common.base.Splitter;

public class Locale
{
    private static final Splitter Â;
    private static final Pattern Ý;
    Map HorizonCode_Horizon_È;
    private boolean Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001097";
    
    static {
        Â = Splitter.on('=').limit(2);
        Ý = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    }
    
    public Locale() {
        this.HorizonCode_Horizon_È = Maps.newHashMap();
    }
    
    public synchronized void HorizonCode_Horizon_È(final IResourceManager p_135022_1_, final List p_135022_2_) {
        this.HorizonCode_Horizon_È.clear();
        for (final String var4 : p_135022_2_) {
            final String var5 = String.format("lang/%s.lang", var4);
            for (final String var7 : p_135022_1_.HorizonCode_Horizon_È()) {
                try {
                    this.HorizonCode_Horizon_È(p_135022_1_.Â(new ResourceLocation_1975012498(var7, var5)));
                }
                catch (IOException ex) {}
            }
        }
        this.Â();
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    private void Â() {
        this.Ø­áŒŠá = false;
        int var1 = 0;
        int var2 = 0;
        for (final String var4 : this.HorizonCode_Horizon_È.values()) {
            final int var5 = var4.length();
            var2 += var5;
            for (int var6 = 0; var6 < var5; ++var6) {
                if (var4.charAt(var6) >= 'Ā') {
                    ++var1;
                }
            }
        }
        final float var7 = var1 / var2;
        this.Ø­áŒŠá = (var7 > 0.1);
    }
    
    private void HorizonCode_Horizon_È(final List p_135028_1_) throws IOException {
        for (final IResource var3 : p_135028_1_) {
            final InputStream var4 = var3.Â();
            try {
                this.HorizonCode_Horizon_È(var4);
            }
            finally {
                IOUtils.closeQuietly(var4);
            }
            IOUtils.closeQuietly(var4);
        }
    }
    
    private void HorizonCode_Horizon_È(final InputStream p_135021_1_) throws IOException {
        for (final String var3 : IOUtils.readLines(p_135021_1_, Charsets.UTF_8)) {
            if (!var3.isEmpty() && var3.charAt(0) != '#') {
                final String[] var4 = (String[])Iterables.toArray(Locale.Â.split((CharSequence)var3), (Class)String.class);
                if (var4 == null || var4.length != 2) {
                    continue;
                }
                final String var5 = var4[0];
                final String var6 = Locale.Ý.matcher(var4[1]).replaceAll("%$1s");
                this.HorizonCode_Horizon_È.put(var5, var6);
            }
        }
    }
    
    private String HorizonCode_Horizon_È(final String p_135026_1_) {
        final String var2 = this.HorizonCode_Horizon_È.get(p_135026_1_);
        return (var2 == null) ? p_135026_1_ : var2;
    }
    
    public String HorizonCode_Horizon_È(final String p_135023_1_, final Object[] p_135023_2_) {
        final String var3 = this.HorizonCode_Horizon_È(p_135023_1_);
        try {
            return String.format(var3, p_135023_2_);
        }
        catch (IllegalFormatException var4) {
            return "Format error: " + var3;
        }
    }
}
