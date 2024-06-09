package org.spongepowered.asm.lib;

public class TypePath
{
    public static final int ARRAY_ELEMENT = 0;
    public static final int INNER_TYPE = 1;
    public static final int WILDCARD_BOUND = 2;
    public static final int TYPE_ARGUMENT = 3;
    byte[] b;
    int offset;
    
    TypePath(final byte[] b, final int offset) {
        super();
        this.b = b;
        this.offset = offset;
    }
    
    public int getLength() {
        return this.b[this.offset];
    }
    
    public int getStep(final int n) {
        return this.b[this.offset + 2 * n + 1];
    }
    
    public int getStepArgument(final int n) {
        return this.b[this.offset + 2 * n + 2];
    }
    
    public static TypePath fromString(final String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        final int length = s.length();
        final ByteVector byteVector = new ByteVector(length);
        byteVector.putByte(0);
        int i = 0;
        while (i < length) {
            final char char1 = s.charAt(i++);
            if (char1 == '[') {
                byteVector.put11(0, 0);
            }
            else if (char1 == '.') {
                byteVector.put11(1, 0);
            }
            else if (char1 == '*') {
                byteVector.put11(2, 0);
            }
            else {
                if (char1 < '0' || char1 > '9') {
                    continue;
                }
                int n = char1 - '0';
                char char2;
                while (i < length && (char2 = s.charAt(i)) >= '0' && char2 <= '9') {
                    n = n * 10 + char2 - 48;
                    ++i;
                }
                if (i < length && s.charAt(i) == ';') {
                    ++i;
                }
                byteVector.put11(3, n);
            }
        }
        byteVector.data[0] = (byte)(byteVector.length / 2);
        return new TypePath(byteVector.data, 0);
    }
    
    @Override
    public String toString() {
        final int length = this.getLength();
        final StringBuilder sb = new StringBuilder(length * 2);
        for (int i = 0; i < length; ++i) {
            switch (this.getStep(i)) {
                case 0: {
                    sb.append('[');
                    break;
                }
                case 1: {
                    sb.append('.');
                    break;
                }
                case 2: {
                    sb.append('*');
                    break;
                }
                case 3: {
                    sb.append(this.getStepArgument(i)).append(';');
                    break;
                }
                default: {
                    sb.append('_');
                    break;
                }
            }
        }
        return sb.toString();
    }
}
