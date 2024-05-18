package org.spongepowered.asm.lib;

final class AnnotationWriter extends AnnotationVisitor
{
    private final ClassWriter cw;
    private int size;
    private final boolean named;
    private final ByteVector bv;
    private final ByteVector parent;
    private final int offset;
    AnnotationWriter next;
    AnnotationWriter prev;
    
    AnnotationWriter(final ClassWriter cw, final boolean named, final ByteVector bv, final ByteVector parent, final int offset) {
        super(327680);
        this.cw = cw;
        this.named = named;
        this.bv = bv;
        this.parent = parent;
        this.offset = offset;
    }
    
    @Override
    public void visit(final String s, final Object o) {
        ++this.size;
        if (this.named) {
            this.bv.putShort(this.cw.newUTF8(s));
        }
        if (o instanceof String) {
            this.bv.put12(115, this.cw.newUTF8((String)o));
        }
        else if (o instanceof Byte) {
            this.bv.put12(66, this.cw.newInteger((byte)o).index);
        }
        else if (o instanceof Boolean) {
            this.bv.put12(90, this.cw.newInteger(((boolean)o) ? 1 : 0).index);
        }
        else if (o instanceof Character) {
            this.bv.put12(67, this.cw.newInteger((char)o).index);
        }
        else if (o instanceof Short) {
            this.bv.put12(83, this.cw.newInteger((short)o).index);
        }
        else if (o instanceof Type) {
            this.bv.put12(99, this.cw.newUTF8(((Type)o).getDescriptor()));
        }
        else if (o instanceof byte[]) {
            final byte[] array = (byte[])o;
            this.bv.put12(91, array.length);
            for (int i = 0; i < array.length; ++i) {
                this.bv.put12(66, this.cw.newInteger(array[i]).index);
            }
        }
        else if (o instanceof boolean[]) {
            final boolean[] array2 = (boolean[])o;
            this.bv.put12(91, array2.length);
            for (int j = 0; j < array2.length; ++j) {
                this.bv.put12(90, this.cw.newInteger(array2[j] ? 1 : 0).index);
            }
        }
        else if (o instanceof short[]) {
            final short[] array3 = (short[])o;
            this.bv.put12(91, array3.length);
            for (int k = 0; k < array3.length; ++k) {
                this.bv.put12(83, this.cw.newInteger(array3[k]).index);
            }
        }
        else if (o instanceof char[]) {
            final char[] array4 = (char[])o;
            this.bv.put12(91, array4.length);
            for (int l = 0; l < array4.length; ++l) {
                this.bv.put12(67, this.cw.newInteger(array4[l]).index);
            }
        }
        else if (o instanceof int[]) {
            final int[] array5 = (int[])o;
            this.bv.put12(91, array5.length);
            for (int n = 0; n < array5.length; ++n) {
                this.bv.put12(73, this.cw.newInteger(array5[n]).index);
            }
        }
        else if (o instanceof long[]) {
            final long[] array6 = (long[])o;
            this.bv.put12(91, array6.length);
            for (int n2 = 0; n2 < array6.length; ++n2) {
                this.bv.put12(74, this.cw.newLong(array6[n2]).index);
            }
        }
        else if (o instanceof float[]) {
            final float[] array7 = (float[])o;
            this.bv.put12(91, array7.length);
            for (int n3 = 0; n3 < array7.length; ++n3) {
                this.bv.put12(70, this.cw.newFloat(array7[n3]).index);
            }
        }
        else if (o instanceof double[]) {
            final double[] array8 = (double[])o;
            this.bv.put12(91, array8.length);
            for (int n4 = 0; n4 < array8.length; ++n4) {
                this.bv.put12(68, this.cw.newDouble(array8[n4]).index);
            }
        }
        else {
            final Item constItem = this.cw.newConstItem(o);
            this.bv.put12(".s.IFJDCS".charAt(constItem.type), constItem.index);
        }
    }
    
    @Override
    public void visitEnum(final String s, final String s2, final String s3) {
        ++this.size;
        if (this.named) {
            this.bv.putShort(this.cw.newUTF8(s));
        }
        this.bv.put12(101, this.cw.newUTF8(s2)).putShort(this.cw.newUTF8(s3));
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String s, final String s2) {
        ++this.size;
        if (this.named) {
            this.bv.putShort(this.cw.newUTF8(s));
        }
        this.bv.put12(64, this.cw.newUTF8(s2)).putShort(0);
        return new AnnotationWriter(this.cw, true, this.bv, this.bv, this.bv.length - 2);
    }
    
    @Override
    public AnnotationVisitor visitArray(final String s) {
        ++this.size;
        if (this.named) {
            this.bv.putShort(this.cw.newUTF8(s));
        }
        this.bv.put12(91, 0);
        return new AnnotationWriter(this.cw, false, this.bv, this.bv, this.bv.length - 2);
    }
    
    @Override
    public void visitEnd() {
        if (this.parent != null) {
            final byte[] data = this.parent.data;
            data[this.offset] = (byte)(this.size >>> 8);
            data[this.offset + 1] = (byte)this.size;
        }
    }
    
    int getSize() {
        int n = 0;
        for (AnnotationWriter next = this; next != null; next = next.next) {
            n += next.bv.length;
        }
        return n;
    }
    
    void put(final ByteVector byteVector) {
        int n = 0;
        int n2 = 2;
        AnnotationWriter next = this;
        AnnotationWriter prev = null;
        while (next != null) {
            ++n;
            n2 += next.bv.length;
            next.visitEnd();
            next.prev = prev;
            prev = next;
            next = next.next;
        }
        byteVector.putInt(n2);
        byteVector.putShort(n);
        for (AnnotationWriter prev2 = prev; prev2 != null; prev2 = prev2.prev) {
            byteVector.putByteArray(prev2.bv.data, 0, prev2.bv.length);
        }
    }
    
    static void put(final AnnotationWriter[] array, final int n, final ByteVector byteVector) {
        int n2 = 1 + 2 * (array.length - n);
        for (int i = n; i < array.length; ++i) {
            n2 += ((array[i] == null) ? 0 : array[i].getSize());
        }
        byteVector.putInt(n2).putByte(array.length - n);
        for (int j = n; j < array.length; ++j) {
            AnnotationWriter next = array[j];
            AnnotationWriter prev = null;
            int n3 = 0;
            while (next != null) {
                ++n3;
                next.visitEnd();
                next.prev = prev;
                prev = next;
                next = next.next;
            }
            byteVector.putShort(n3);
            for (AnnotationWriter prev2 = prev; prev2 != null; prev2 = prev2.prev) {
                byteVector.putByteArray(prev2.bv.data, 0, prev2.bv.length);
            }
        }
    }
    
    static void putTarget(final int n, final TypePath typePath, final ByteVector byteVector) {
        switch (n >>> 24) {
            case 0:
            case 1:
            case 22: {
                byteVector.putShort(n >>> 16);
                break;
            }
            case 19:
            case 20:
            case 21: {
                byteVector.putByte(n >>> 24);
                break;
            }
            case 71:
            case 72:
            case 73:
            case 74:
            case 75: {
                byteVector.putInt(n);
                break;
            }
            default: {
                byteVector.put12(n >>> 24, (n & 0xFFFF00) >> 8);
                break;
            }
        }
        if (typePath == null) {
            byteVector.putByte(0);
        }
        else {
            byteVector.putByteArray(typePath.b, typePath.offset, typePath.b[typePath.offset] * 2 + 1);
        }
    }
}
