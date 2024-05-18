package org.spongepowered.asm.lib;

final class FieldWriter extends FieldVisitor
{
    private final ClassWriter cw;
    private final int access;
    private final int name;
    private final int desc;
    private int signature;
    private int value;
    private AnnotationWriter anns;
    private AnnotationWriter ianns;
    private AnnotationWriter tanns;
    private AnnotationWriter itanns;
    private Attribute attrs;
    
    FieldWriter(final ClassWriter cw, final int access, final String s, final String s2, final String s3, final Object o) {
        super(327680);
        if (cw.firstField == null) {
            cw.firstField = this;
        }
        else {
            cw.lastField.fv = this;
        }
        cw.lastField = this;
        this.cw = cw;
        this.access = access;
        this.name = cw.newUTF8(s);
        this.desc = cw.newUTF8(s2);
        if (s3 != null) {
            this.signature = cw.newUTF8(s3);
        }
        if (o != null) {
            this.value = cw.newConstItem(o).index;
        }
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String s, final boolean b) {
        final ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.cw.newUTF8(s)).putShort(0);
        final AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, 2);
        if (b) {
            annotationWriter.next = this.anns;
            this.anns = annotationWriter;
        }
        else {
            annotationWriter.next = this.ianns;
            this.ianns = annotationWriter;
        }
        return annotationWriter;
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        final ByteVector byteVector = new ByteVector();
        AnnotationWriter.putTarget(n, typePath, byteVector);
        byteVector.putShort(this.cw.newUTF8(s)).putShort(0);
        final AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
        if (b) {
            annotationWriter.next = this.tanns;
            this.tanns = annotationWriter;
        }
        else {
            annotationWriter.next = this.itanns;
            this.itanns = annotationWriter;
        }
        return annotationWriter;
    }
    
    @Override
    public void visitAttribute(final Attribute attrs) {
        attrs.next = this.attrs;
        this.attrs = attrs;
    }
    
    @Override
    public void visitEnd() {
    }
    
    int getSize() {
        int n = 8;
        if (this.value != 0) {
            this.cw.newUTF8("ConstantValue");
            n += 8;
        }
        if ((this.access & 0x1000) != 0x0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            this.cw.newUTF8("Synthetic");
            n += 6;
        }
        if ((this.access & 0x20000) != 0x0) {
            this.cw.newUTF8("Deprecated");
            n += 6;
        }
        if (this.signature != 0) {
            this.cw.newUTF8("Signature");
            n += 8;
        }
        if (this.anns != null) {
            this.cw.newUTF8("RuntimeVisibleAnnotations");
            n += 8 + this.anns.getSize();
        }
        if (this.ianns != null) {
            this.cw.newUTF8("RuntimeInvisibleAnnotations");
            n += 8 + this.ianns.getSize();
        }
        if (this.tanns != null) {
            this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
            n += 8 + this.tanns.getSize();
        }
        if (this.itanns != null) {
            this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
            n += 8 + this.itanns.getSize();
        }
        if (this.attrs != null) {
            n += this.attrs.getSize(this.cw, null, 0, -1, -1);
        }
        return n;
    }
    
    void put(final ByteVector byteVector) {
        byteVector.putShort(this.access & ~(0x60000 | (this.access & 0x40000) / 64)).putShort(this.name).putShort(this.desc);
        int n = 0;
        if (this.value != 0) {
            ++n;
        }
        if ((this.access & 0x1000) != 0x0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            ++n;
        }
        if ((this.access & 0x20000) != 0x0) {
            ++n;
        }
        if (this.signature != 0) {
            ++n;
        }
        if (this.anns != null) {
            ++n;
        }
        if (this.ianns != null) {
            ++n;
        }
        if (this.tanns != null) {
            ++n;
        }
        if (this.itanns != null) {
            ++n;
        }
        if (this.attrs != null) {
            n += this.attrs.getCount();
        }
        byteVector.putShort(n);
        if (this.value != 0) {
            byteVector.putShort(this.cw.newUTF8("ConstantValue"));
            byteVector.putInt(2).putShort(this.value);
        }
        if ((this.access & 0x1000) != 0x0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            byteVector.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
        }
        if ((this.access & 0x20000) != 0x0) {
            byteVector.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
        }
        if (this.signature != 0) {
            byteVector.putShort(this.cw.newUTF8("Signature"));
            byteVector.putInt(2).putShort(this.signature);
        }
        if (this.anns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
            this.anns.put(byteVector);
        }
        if (this.ianns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
            this.ianns.put(byteVector);
        }
        if (this.tanns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.tanns.put(byteVector);
        }
        if (this.itanns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.itanns.put(byteVector);
        }
        if (this.attrs != null) {
            this.attrs.put(this.cw, null, 0, -1, -1, byteVector);
        }
    }
}
