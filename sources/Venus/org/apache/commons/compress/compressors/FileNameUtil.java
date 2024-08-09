/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class FileNameUtil {
    private final Map<String, String> compressSuffix = new HashMap<String, String>();
    private final Map<String, String> uncompressSuffix;
    private final int longestCompressedSuffix;
    private final int shortestCompressedSuffix;
    private final int longestUncompressedSuffix;
    private final int shortestUncompressedSuffix;
    private final String defaultExtension;

    public FileNameUtil(Map<String, String> map, String string) {
        this.uncompressSuffix = Collections.unmodifiableMap(map);
        int n = Integer.MIN_VALUE;
        int n2 = Integer.MAX_VALUE;
        int n3 = Integer.MIN_VALUE;
        int n4 = Integer.MAX_VALUE;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String string2;
            int n5;
            int n6 = entry.getKey().length();
            if (n6 > n) {
                n = n6;
            }
            if (n6 < n2) {
                n2 = n6;
            }
            if ((n5 = (string2 = entry.getValue()).length()) <= 0) continue;
            if (!this.compressSuffix.containsKey(string2)) {
                this.compressSuffix.put(string2, entry.getKey());
            }
            if (n5 > n3) {
                n3 = n5;
            }
            if (n5 >= n4) continue;
            n4 = n5;
        }
        this.longestCompressedSuffix = n;
        this.longestUncompressedSuffix = n3;
        this.shortestCompressedSuffix = n2;
        this.shortestUncompressedSuffix = n4;
        this.defaultExtension = string;
    }

    public boolean isCompressedFilename(String string) {
        String string2 = string.toLowerCase(Locale.ENGLISH);
        int n = string2.length();
        for (int i = this.shortestCompressedSuffix; i <= this.longestCompressedSuffix && i < n; ++i) {
            if (!this.uncompressSuffix.containsKey(string2.substring(n - i))) continue;
            return false;
        }
        return true;
    }

    public String getUncompressedFilename(String string) {
        String string2 = string.toLowerCase(Locale.ENGLISH);
        int n = string2.length();
        for (int i = this.shortestCompressedSuffix; i <= this.longestCompressedSuffix && i < n; ++i) {
            String string3 = this.uncompressSuffix.get(string2.substring(n - i));
            if (string3 == null) continue;
            return string.substring(0, n - i) + string3;
        }
        return string;
    }

    public String getCompressedFilename(String string) {
        String string2 = string.toLowerCase(Locale.ENGLISH);
        int n = string2.length();
        for (int i = this.shortestUncompressedSuffix; i <= this.longestUncompressedSuffix && i < n; ++i) {
            String string3 = this.compressSuffix.get(string2.substring(n - i));
            if (string3 == null) continue;
            return string.substring(0, n - i) + string3;
        }
        return string + this.defaultExtension;
    }
}

