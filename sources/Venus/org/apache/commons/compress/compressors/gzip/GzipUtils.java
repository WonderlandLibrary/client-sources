/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.gzip;

import java.util.LinkedHashMap;
import org.apache.commons.compress.compressors.FileNameUtil;

public class GzipUtils {
    private static final FileNameUtil fileNameUtil;

    private GzipUtils() {
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
        linkedHashMap.put(".tgz", ".tar");
        linkedHashMap.put(".taz", ".tar");
        linkedHashMap.put(".svgz", ".svg");
        linkedHashMap.put(".cpgz", ".cpio");
        linkedHashMap.put(".wmz", ".wmf");
        linkedHashMap.put(".emz", ".emf");
        linkedHashMap.put(".gz", "");
        linkedHashMap.put(".z", "");
        linkedHashMap.put("-gz", "");
        linkedHashMap.put("-z", "");
        linkedHashMap.put("_z", "");
        fileNameUtil = new FileNameUtil(linkedHashMap, ".gz");
    }
}

