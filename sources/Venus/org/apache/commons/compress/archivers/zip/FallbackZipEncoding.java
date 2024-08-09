/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.apache.commons.compress.archivers.zip.ZipEncoding;

class FallbackZipEncoding
implements ZipEncoding {
    private final String charsetName;

    public FallbackZipEncoding() {
        this.charsetName = null;
    }

    public FallbackZipEncoding(String string) {
        this.charsetName = string;
    }

    public boolean canEncode(String string) {
        return false;
    }

    public ByteBuffer encode(String string) throws IOException {
        if (this.charsetName == null) {
            return ByteBuffer.wrap(string.getBytes());
        }
        return ByteBuffer.wrap(string.getBytes(this.charsetName));
    }

    public String decode(byte[] byArray) throws IOException {
        if (this.charsetName == null) {
            return new String(byArray);
        }
        return new String(byArray, this.charsetName);
    }
}

