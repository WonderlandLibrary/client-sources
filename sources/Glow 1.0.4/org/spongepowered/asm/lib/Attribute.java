package org.spongepowered.asm.lib;

public class Attribute
{
    public final String type;
    byte[] value;
    Attribute next;
    
    protected Attribute(final String type) {
        super();
        this.type = type;
    }
    
    public boolean isUnknown() {
        return true;
    }
    
    public boolean isCodeAttribute() {
        return false;
    }
    
    protected Label[] getLabels() {
        return null;
    }
    
    protected Attribute read(final ClassReader classReader, final int n, final int n2, final char[] array, final int n3, final Label[] array2) {
        final Attribute attribute = new Attribute(this.type);
        attribute.value = new byte[n2];
        System.arraycopy(classReader.b, n, attribute.value, 0, n2);
        return attribute;
    }
    
    protected ByteVector write(final ClassWriter classWriter, final byte[] array, final int n, final int n2, final int n3) {
        final ByteVector byteVector = new ByteVector();
        byteVector.data = this.value;
        byteVector.length = this.value.length;
        return byteVector;
    }
    
    final int getCount() {
        int n = 0;
        for (Attribute next = this; next != null; next = next.next) {
            ++n;
        }
        return n;
    }
    
    final int getSize(final ClassWriter classWriter, final byte[] array, final int n, final int n2, final int n3) {
        Attribute next = this;
        int n4 = 0;
        while (next != null) {
            classWriter.newUTF8(next.type);
            n4 += next.write(classWriter, array, n, n2, n3).length + 6;
            next = next.next;
        }
        return n4;
    }
    
    final void put(final ClassWriter classWriter, final byte[] array, final int n, final int n2, final int n3, final ByteVector byteVector) {
        for (Attribute next = this; next != null; next = next.next) {
            final ByteVector write = next.write(classWriter, array, n, n2, n3);
            byteVector.putShort(classWriter.newUTF8(next.type)).putInt(write.length);
            byteVector.putByteArray(write.data, 0, write.length);
        }
    }
}
