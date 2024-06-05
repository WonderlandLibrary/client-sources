package net.minecraft.client.resources;

import com.google.common.base.*;
import java.util.regex.*;
import net.minecraft.util.*;
import java.io.*;
import org.apache.commons.io.*;
import com.google.common.collect.*;
import java.util.*;

public class Locale
{
    private static final Splitter splitter;
    private static final Pattern field_135031_c;
    Map field_135032_a;
    private boolean field_135029_d;
    
    public Locale() {
        this.field_135032_a = Maps.newHashMap();
    }
    
    public synchronized void loadLocaleDataFiles(final IResourceManager p_135022_1_, final List p_135022_2_) {
        this.field_135032_a.clear();
        for (final String var4 : p_135022_2_) {
            final String var5 = String.format("lang/%s.lang", var4);
            for (final String var7 : p_135022_1_.getResourceDomains()) {
                try {
                    this.loadLocaleData(p_135022_1_.getAllResources(new ResourceLocation(var7, var5)));
                }
                catch (IOException ex) {}
            }
        }
        this.checkUnicode();
    }
    
    public boolean isUnicode() {
        return this.field_135029_d;
    }
    
    private void checkUnicode() {
        this.field_135029_d = false;
        int var1 = 0;
        int var2 = 0;
        for (final String var4 : this.field_135032_a.values()) {
            final int var5 = var4.length();
            var2 += var5;
            for (int var6 = 0; var6 < var5; ++var6) {
                if (var4.charAt(var6) >= '\u0100') {
                    ++var1;
                }
            }
        }
        final float var7 = var1 / (float)var2;
        this.field_135029_d = (var7 > 0.1);
    }
    
    private void loadLocaleData(final List p_135028_1_) throws IOException {
        for (final IResource var3 : p_135028_1_) {
            final InputStream var4 = var3.getInputStream();
            try {
                this.loadLocaleData(var4);
            }
            finally {
                IOUtils.closeQuietly(var4);
            }
        }
    }
    
    private void loadLocaleData(final InputStream p_135021_1_) throws IOException {
        for (final String var3 : IOUtils.readLines(p_135021_1_, Charsets.UTF_8)) {
            if (!var3.isEmpty() && var3.charAt(0) != '#') {
                final String[] var4 = (String[])Iterables.toArray(Locale.splitter.split((CharSequence)var3), (Class)String.class);
                if (var4 == null || var4.length != 2) {
                    continue;
                }
                final String var5 = var4[0];
                final String var6 = Locale.field_135031_c.matcher(var4[1]).replaceAll("%$1s");
                this.field_135032_a.put(var5, var6);
            }
        }
    }
    
    private String translateKeyPrivate(final String p_135026_1_) {
        final String var2 = this.field_135032_a.get(p_135026_1_);
        return (var2 == null) ? p_135026_1_ : var2;
    }
    
    public String formatMessage(final String p_135023_1_, final Object[] p_135023_2_) {
        final String var3 = this.translateKeyPrivate(p_135023_1_);
        try {
            return String.format(var3, p_135023_2_);
        }
        catch (IllegalFormatException var4) {
            return "Format error: " + var3;
        }
    }
    
    static {
        splitter = Splitter.on('=').limit(2);
        field_135031_c = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    }
}
