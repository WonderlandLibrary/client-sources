/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;

class Simple8BitZipEncoding
implements ZipEncoding {
    private final char[] highChars;
    private final List<Simple8BitChar> reverseMapping;

    public Simple8BitZipEncoding(char[] cArray) {
        this.highChars = (char[])cArray.clone();
        ArrayList<Simple8BitChar> arrayList = new ArrayList<Simple8BitChar>(this.highChars.length);
        byte by = 127;
        for (char c : this.highChars) {
            by = (byte)(by + 1);
            arrayList.add(new Simple8BitChar(by, c));
        }
        Collections.sort(arrayList);
        this.reverseMapping = Collections.unmodifiableList(arrayList);
    }

    public char decodeByte(byte by) {
        if (by >= 0) {
            return (char)by;
        }
        return this.highChars[128 + by];
    }

    public boolean canEncodeChar(char c) {
        if (c >= '\u0000' && c < '\u0080') {
            return false;
        }
        Simple8BitChar simple8BitChar = this.encodeHighChar(c);
        return simple8BitChar != null;
    }

    public boolean pushEncodedChar(ByteBuffer byteBuffer, char c) {
        if (c >= '\u0000' && c < '\u0080') {
            byteBuffer.put((byte)c);
            return false;
        }
        Simple8BitChar simple8BitChar = this.encodeHighChar(c);
        if (simple8BitChar == null) {
            return true;
        }
        byteBuffer.put(simple8BitChar.code);
        return false;
    }

    private Simple8BitChar encodeHighChar(char c) {
        int n = 0;
        int n2 = this.reverseMapping.size();
        while (n2 > n) {
            int n3 = n + (n2 - n) / 2;
            Simple8BitChar simple8BitChar = this.reverseMapping.get(n3);
            if (simple8BitChar.unicode == c) {
                return simple8BitChar;
            }
            if (simple8BitChar.unicode < c) {
                n = n3 + 1;
                continue;
            }
            n2 = n3;
        }
        if (n >= this.reverseMapping.size()) {
            return null;
        }
        Simple8BitChar simple8BitChar = this.reverseMapping.get(n);
        if (simple8BitChar.unicode != c) {
            return null;
        }
        return simple8BitChar;
    }

    public boolean canEncode(String string) {
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (this.canEncodeChar(c)) continue;
            return true;
        }
        return false;
    }

    public ByteBuffer encode(String string) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(string.length() + 6 + (string.length() + 1) / 2);
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (byteBuffer.remaining() < 6) {
                byteBuffer = ZipEncodingHelper.growBuffer(byteBuffer, byteBuffer.position() + 6);
            }
            if (this.pushEncodedChar(byteBuffer, c)) continue;
            ZipEncodingHelper.appendSurrogate(byteBuffer, c);
        }
        byteBuffer.limit(byteBuffer.position());
        byteBuffer.rewind();
        return byteBuffer;
    }

    public String decode(byte[] byArray) throws IOException {
        char[] cArray = new char[byArray.length];
        for (int i = 0; i < byArray.length; ++i) {
            cArray[i] = this.decodeByte(byArray[i]);
        }
        return new String(cArray);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static final class Simple8BitChar
    implements Comparable<Simple8BitChar> {
        public final char unicode;
        public final byte code;

        Simple8BitChar(byte by, char c) {
            this.code = by;
            this.unicode = c;
        }

        @Override
        public int compareTo(Simple8BitChar simple8BitChar) {
            return this.unicode - simple8BitChar.unicode;
        }

        public String toString() {
            return "0x" + Integer.toHexString(0xFFFF & this.unicode) + "->0x" + Integer.toHexString(0xFF & this.code);
        }

        public boolean equals(Object object) {
            if (object instanceof Simple8BitChar) {
                Simple8BitChar simple8BitChar = (Simple8BitChar)object;
                return this.unicode == simple8BitChar.unicode && this.code == simple8BitChar.code;
            }
            return true;
        }

        public int hashCode() {
            return this.unicode;
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((Simple8BitChar)object);
        }
    }
}

