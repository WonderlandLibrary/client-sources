package net.minecraft.client.resources;

import com.google.common.base.*;
import java.util.regex.*;
import java.io.*;
import net.minecraft.util.*;
import java.util.*;
import org.apache.commons.io.*;
import com.google.common.collect.*;

public class Locale
{
    private static final Splitter splitter;
    Map<String, String> properties;
    private boolean unicode;
    private static final Pattern pattern;
    private static final String[] I;
    
    private String translateKeyPrivate(final String s) {
        final String s2 = this.properties.get(s);
        String s3;
        if (s2 == null) {
            s3 = s;
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            s3 = s2;
        }
        return s3;
    }
    
    static {
        I();
        splitter = Splitter.on((char)(0xA0 ^ 0x9D)).limit("  ".length());
        pattern = Pattern.compile(Locale.I["".length()]);
    }
    
    private void loadLocaleData(final List<IResource> list) throws IOException {
        final Iterator<IResource> iterator = list.iterator();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final InputStream inputStream = iterator.next().getInputStream();
            try {
                this.loadLocaleData(inputStream);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            finally {
                IOUtils.closeQuietly(inputStream);
            }
            IOUtils.closeQuietly(inputStream);
        }
    }
    
    public synchronized void loadLocaleDataFiles(final IResourceManager resourceManager, final List<String> list) {
        this.properties.clear();
        final Iterator<String> iterator = list.iterator();
        "".length();
        if (true != true) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            final String s2 = Locale.I[" ".length()];
            final Object[] array = new Object[" ".length()];
            array["".length()] = s;
            final String format = String.format(s2, array);
            final Iterator<String> iterator2 = resourceManager.getResourceDomains().iterator();
            "".length();
            if (4 == 1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final String s3 = iterator2.next();
                try {
                    this.loadLocaleData(resourceManager.getAllResources(new ResourceLocation(s3, format)));
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                    continue;
                }
                catch (IOException ex) {}
            }
        }
        this.checkUnicode();
    }
    
    public boolean isUnicode() {
        return this.unicode;
    }
    
    public Locale() {
        this.properties = (Map<String, String>)Maps.newHashMap();
    }
    
    public String formatMessage(final String s, final Object[] array) {
        final String translateKeyPrivate = this.translateKeyPrivate(s);
        try {
            return String.format(translateKeyPrivate, array);
        }
        catch (IllegalFormatException ex) {
            return Locale.I["   ".length()] + translateKeyPrivate;
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void checkUnicode() {
        this.unicode = ("".length() != 0);
        int length = "".length();
        int length2 = "".length();
        final Iterator<String> iterator = this.properties.values().iterator();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            final int length3 = s.length();
            length2 += length3;
            int i = "".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (i < length3) {
                if (s.charAt(i) >= 196 + 67 - 142 + 135) {
                    ++length;
                }
                ++i;
            }
        }
        int unicode;
        if (length / length2 > 0.1) {
            unicode = " ".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            unicode = "".length();
        }
        this.unicode = (unicode != 0);
    }
    
    private static void I() {
        (I = new String[0xAC ^ 0xA8])["".length()] = I("cB\u0015>@\u001aN`e0\u001a\u000e\u0015t6l1-<6", "FjIZk");
        Locale.I[" ".length()] = I("\u001a\n4\nlS\u0018t\u0001\"\u0018\f", "vkZmC");
        Locale.I["  ".length()] = I("Jac\u000b", "oERxi");
        Locale.I["   ".length()] = I("\u001e!\u0007%\u0002,n\u0010:\u00117<Oh", "XNuHc");
    }
    
    private void loadLocaleData(final InputStream inputStream) throws IOException {
        final Iterator<String> iterator = IOUtils.readLines(inputStream, Charsets.UTF_8).iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            if (!s.isEmpty() && s.charAt("".length()) != (0x33 ^ 0x10)) {
                final String[] array = (String[])Iterables.toArray(Locale.splitter.split((CharSequence)s), (Class)String.class);
                if (array == null || array.length != "  ".length()) {
                    continue;
                }
                this.properties.put(array["".length()], Locale.pattern.matcher(array[" ".length()]).replaceAll(Locale.I["  ".length()]));
            }
        }
    }
}
