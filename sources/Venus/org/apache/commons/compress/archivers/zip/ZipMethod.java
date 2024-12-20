/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum ZipMethod {
    STORED(0),
    UNSHRINKING(1),
    EXPANDING_LEVEL_1(2),
    EXPANDING_LEVEL_2(3),
    EXPANDING_LEVEL_3(4),
    EXPANDING_LEVEL_4(5),
    IMPLODING(6),
    TOKENIZATION(7),
    DEFLATED(8),
    ENHANCED_DEFLATED(9),
    PKWARE_IMPLODING(10),
    BZIP2(12),
    LZMA(14),
    JPEG(96),
    WAVPACK(97),
    PPMD(98),
    AES_ENCRYPTED(99),
    UNKNOWN(-1);

    private final int code;
    private static final Map<Integer, ZipMethod> codeToEnum;

    private ZipMethod(int n2) {
        this.code = n2;
    }

    public int getCode() {
        return this.code;
    }

    public static ZipMethod getMethodByCode(int n) {
        return codeToEnum.get(n);
    }

    static {
        HashMap<Integer, ZipMethod> hashMap = new HashMap<Integer, ZipMethod>();
        for (ZipMethod zipMethod : ZipMethod.values()) {
            hashMap.put(zipMethod.getCode(), zipMethod);
        }
        codeToEnum = Collections.unmodifiableMap(hashMap);
    }
}

