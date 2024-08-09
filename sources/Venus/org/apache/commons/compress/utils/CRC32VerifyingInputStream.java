/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.utils;

import java.io.InputStream;
import java.util.zip.CRC32;
import org.apache.commons.compress.utils.ChecksumVerifyingInputStream;

public class CRC32VerifyingInputStream
extends ChecksumVerifyingInputStream {
    public CRC32VerifyingInputStream(InputStream inputStream, long l, int n) {
        this(inputStream, l, (long)n & 0xFFFFFFFFL);
    }

    public CRC32VerifyingInputStream(InputStream inputStream, long l, long l2) {
        super(new CRC32(), inputStream, l, l2);
    }
}

