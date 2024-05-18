package net.minecraft.util;

import com.google.common.base.*;
import java.util.regex.*;
import org.apache.commons.io.*;
import com.google.common.collect.*;
import java.io.*;
import java.util.*;

public class StringTranslate
{
    private static final String[] I;
    private final Map<String, String> languageList;
    private static final Splitter equalSignSplitter;
    private long lastUpdateTimeInMilliseconds;
    private static final Pattern numericVariablePattern;
    private static StringTranslate instance;
    
    private String tryTranslateKey(final String s) {
        final String s2 = this.languageList.get(s);
        String s3;
        if (s2 == null) {
            s3 = s;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            s3 = s2;
        }
        return s3;
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public StringTranslate() {
        this.languageList = (Map<String, String>)Maps.newHashMap();
        try {
            final Iterator<String> iterator = IOUtils.readLines(StringTranslate.class.getResourceAsStream(StringTranslate.I[" ".length()]), Charsets.UTF_8).iterator();
            "".length();
            if (1 < 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final String s = iterator.next();
                if (!s.isEmpty() && s.charAt("".length()) != (0x26 ^ 0x5)) {
                    final String[] array = (String[])Iterables.toArray(StringTranslate.equalSignSplitter.split((CharSequence)s), (Class)String.class);
                    if (array == null || array.length != "  ".length()) {
                        continue;
                    }
                    this.languageList.put(array["".length()], StringTranslate.numericVariablePattern.matcher(array[" ".length()]).replaceAll(StringTranslate.I["  ".length()]));
                }
            }
            this.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (IOException ex) {}
    }
    
    private static void I() {
        (I = new String[0x87 ^ 0x83])["".length()] = I("IE,-x0IYv\b0\t,g\u000eF6\u0014/\u000e", "lmpIS");
        StringTranslate.I[" ".length()] = I("Z\"9\u000b?\u00010e\u00153\u001b&)\n;\u00137e\u0014;\u001b$e\u001d4*\u0016\u0019V6\u0014--", "uCJxZ");
        StringTranslate.I["  ".length()] = I("Ogy*", "jCHYK");
        StringTranslate.I["   ".length()] = I("'\u0019\u0001\u0007'\u0015V\u0016\u00184\u000e\u0004IJ", "avsjF");
    }
    
    public synchronized String translateKey(final String s) {
        return this.tryTranslateKey(s);
    }
    
    public long getLastUpdateTimeInMilliseconds() {
        return this.lastUpdateTimeInMilliseconds;
    }
    
    static {
        I();
        numericVariablePattern = Pattern.compile(StringTranslate.I["".length()]);
        equalSignSplitter = Splitter.on((char)(0x2A ^ 0x17)).limit("  ".length());
        StringTranslate.instance = new StringTranslate();
    }
    
    static StringTranslate getInstance() {
        return StringTranslate.instance;
    }
    
    public synchronized String translateKeyFormat(final String s, final Object... array) {
        final String tryTranslateKey = this.tryTranslateKey(s);
        try {
            return String.format(tryTranslateKey, array);
        }
        catch (IllegalFormatException ex) {
            return StringTranslate.I["   ".length()] + tryTranslateKey;
        }
    }
    
    public synchronized boolean isKeyTranslated(final String s) {
        return this.languageList.containsKey(s);
    }
    
    public static synchronized void replaceWith(final Map<String, String> map) {
        StringTranslate.instance.languageList.clear();
        StringTranslate.instance.languageList.putAll(map);
        StringTranslate.instance.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
    }
}
