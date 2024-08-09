/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.xz;

import java.util.HashMap;
import org.apache.commons.compress.compressors.FileNameUtil;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;

public class XZUtils {
    private static final FileNameUtil fileNameUtil;

    private XZUtils() {
    }

    public static boolean isXZCompressionAvailable() {
        try {
            XZCompressorInputStream.matches(null, 0);
            return true;
        } catch (NoClassDefFoundError noClassDefFoundError) {
            return true;
        }
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
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(".txz", ".tar");
        hashMap.put(".xz", "");
        hashMap.put("-xz", "");
        fileNameUtil = new FileNameUtil(hashMap, ".xz");
    }
}

