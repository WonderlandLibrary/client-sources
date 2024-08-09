/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.bzip2;

import java.util.LinkedHashMap;
import org.apache.commons.compress.compressors.FileNameUtil;

public abstract class BZip2Utils {
    private static final FileNameUtil fileNameUtil;

    private BZip2Utils() {
    }

    public static boolean isCompressedFilename(String string) {
        return fileNameUtil.isCompressedFilename(string);
    }

    public static String getUncompressedFilename(String string) {
        return fileNameUtil.getUncompressedFilename(string);
    }

    public static String getCompressedFilename(String string) {
        return fileNameUtil.getCompressedFilename(string);
    }

    static {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        linkedHashMap.put(".tar.bz2", ".tar");
        linkedHashMap.put(".tbz2", ".tar");
        linkedHashMap.put(".tbz", ".tar");
        linkedHashMap.put(".bz2", "");
        linkedHashMap.put(".bz", "");
        fileNameUtil = new FileNameUtil(linkedHashMap, ".bz2");
    }
}

