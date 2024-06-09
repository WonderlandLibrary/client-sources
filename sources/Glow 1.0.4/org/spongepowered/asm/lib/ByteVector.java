package org.spongepowered.asm.lib;

public class ByteVector
{
    byte[] data;
    int length;
    
    public ByteVector() {
        super();
        this.data = new byte[64];
    }
    
    public ByteVector(final int n) {
        super();
        this.data = new byte[n];
    }
    
    public ByteVector putByte(final int n) {
        int length = this.length;
        if (length + 1 > this.data.length) {
            this.enlarge(1);
        }
        this.data[length++] = (byte)n;
        this.length = length;
        return this;
    }
    
    ByteVector put11(final int n, final int n2) {
        int length = this.length;
        if (length + 2 > this.data.length) {
            this.enlarge(2);
        }
        final byte[] data = this.data;
        data[length++] = (byte)n;
        data[length++] = (byte)n2;
        this.length = length;
        return this;
    }
    
    public ByteVector putShort(final int n) {
        int length = this.length;
        if (length + 2 > this.data.length) {
            this.enlarge(2);
        }
        final byte[] data = this.data;
        data[length++] = (byte)(n >>> 8);
        data[length++] = (byte)n;
        this.length = length;
        return this;
    }
    
    ByteVector put12(final int n, final int n2) {
        int length = this.length;
        if (length + 3 > this.data.length) {
            this.enlarge(3);
        }
        final byte[] data = this.data;
        data[length++] = (byte)n;
        data[length++] = (byte)(n2 >>> 8);
        data[length++] = (byte)n2;
        this.length = length;
        return this;
    }
    
    public ByteVector putInt(final int n) {
        int length = this.length;
        if (length + 4 > this.data.length) {
            this.enlarge(4);
        }
        final byte[] data = this.data;
        data[length++] = (byte)(n >>> 24);
        data[length++] = (byte)(n >>> 16);
        data[length++] = (byte)(n >>> 8);
        data[length++] = (byte)n;
        this.length = length;
        return this;
    }
    
    public ByteVector putLong(final long n) {
        int length = this.length;
        if (length + 8 > this.data.length) {
            this.enlarge(8);
        }
        final byte[] data = this.data;
        final int n2 = (int)(n >>> 32);
        data[length++] = (byte)(n2 >>> 24);
        data[length++] = (byte)(n2 >>> 16);
        data[length++] = (byte)(n2 >>> 8);
        data[length++] = (byte)n2;
        final int n3 = (int)n;
        data[length++] = (byte)(n3 >>> 24);
        data[length++] = (byte)(n3 >>> 16);
        data[length++] = (byte)(n3 >>> 8);
        data[length++] = (byte)n3;
        this.length = length;
        return this;
    }
    
    public ByteVector putUTF8(final String s) {
        final int length = s.length();
        if (length > 65535) {
            throw new IllegalArgumentException();
        }
        int length2 = this.length;
        if (length2 + 2 + length > this.data.length) {
            this.enlarge(2 + length);
        }
        final byte[] data = this.data;
        data[length2++] = (byte)(length >>> 8);
        data[length2++] = (byte)length;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 < '\u0001' || char1 > '\u007f') {
                this.length = length2;
                return this.encodeUTF8(s, i, 65535);
            }
            data[length2++] = (byte)char1;
        }
        this.length = length2;
        return this;
    }
    
    ByteVector encodeUTF8(final String s, final int n, final int n2) {
        final int length = s.length();
        int n3 = n;
        for (int i = n; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 >= '\u0001' && char1 <= '\u007f') {
                ++n3;
            }
            else if (char1 > '\u07ff') {
                n3 += 3;
            }
            else {
                n3 += 2;
            }
        }
        if (n3 > n2) {
            throw new IllegalArgumentException();
        }
        final int n4 = this.length - n - 2;
        if (n4 >= 0) {
            this.data[n4] = (byte)(n3 >>> 8);
            this.data[n4 + 1] = (byte)n3;
        }
        if (this.length + n3 - n > this.data.length) {
            this.enlarge(n3 - n);
        }
        int length2 = this.length;
        for (int j = n; j < length; ++j) {
            final char char2 = s.charAt(j);
            if (char2 >= '\u0001' && char2 <= '\u007f') {
                this.data[length2++] = (byte)char2;
            }
            else if (char2 > '\u07ff') {
                this.data[length2++] = (byte)(0xE0 | (char2 >> 12 & 0xF));
                this.data[length2++] = (byte)(0x80 | (char2 >> 6 & 0x3F));
                this.data[length2++] = (byte)(0x80 | (char2 & '?'));
            }
            else {
                this.data[length2++] = (byte)(0xC0 | (char2 >> 6 & 0x1F));
                this.data[length2++] = (byte)(0x80 | (char2 & '?'));
            }
        }
        this.length = length2;
        return this;
    }
    
    public ByteVector putByteArray(final byte[] array, final int n, final int n2) {
        if (this.length + n2 > this.data.length) {
            this.enlarge(n2);
        }
        if (array != null) {
            System.arraycopy(array, n, this.data, this.length, n2);
        }
        this.length += n2;
        return this;
    }
    
    private void enlarge(final int n) {
        final int n2 = 2 * this.data.length;
        final int n3 = this.length + n;
        final byte[] data = new byte[(n2 > n3) ? n2 : n3];
        System.arraycopy(this.data, 0, data, 0, this.length);
        this.data = data;
    }
}
