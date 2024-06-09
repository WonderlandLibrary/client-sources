package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.*;

public class CheckAnnotationAdapter extends AnnotationVisitor
{
    private final boolean named;
    private boolean end;
    
    public CheckAnnotationAdapter(final AnnotationVisitor annotationVisitor) {
        this(annotationVisitor, true);
    }
    
    CheckAnnotationAdapter(final AnnotationVisitor annotationVisitor, final boolean named) {
        super(327680, annotationVisitor);
        this.named = named;
    }
    
    @Override
    public void visit(final String s, final Object o) {
        this.checkEnd();
        this.checkName(s);
        if (!(o instanceof Byte) && !(o instanceof Boolean) && !(o instanceof Character) && !(o instanceof Short) && !(o instanceof Integer) && !(o instanceof Long) && !(o instanceof Float) && !(o instanceof Double) && !(o instanceof String) && !(o instanceof Type) && !(o instanceof byte[]) && !(o instanceof boolean[]) && !(o instanceof char[]) && !(o instanceof short[]) && !(o instanceof int[]) && !(o instanceof long[]) && !(o instanceof float[]) && !(o instanceof double[])) {
            throw new IllegalArgumentException("Invalid annotation value");
        }
        if (o instanceof Type && ((Type)o).getSort() == 11) {
            throw new IllegalArgumentException("Invalid annotation value");
        }
        if (this.av != null) {
            this.av.visit(s, o);
        }
    }
    
    @Override
    public void visitEnum(final String s, final String s2, final String s3) {
        this.checkEnd();
        this.checkName(s);
        CheckMethodAdapter.checkDesc(s2, false);
        if (s3 == null) {
            throw new IllegalArgumentException("Invalid enum value");
        }
        if (this.av != null) {
            this.av.visitEnum(s, s2, s3);
        }
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String s, final String s2) {
        this.checkEnd();
        this.checkName(s);
        CheckMethodAdapter.checkDesc(s2, false);
        return new CheckAnnotationAdapter((this.av == null) ? null : this.av.visitAnnotation(s, s2));
    }
    
    @Override
    public AnnotationVisitor visitArray(final String s) {
        this.checkEnd();
        this.checkName(s);
        return new CheckAnnotationAdapter((this.av == null) ? null : this.av.visitArray(s), false);
    }
    
    @Override
    public void visitEnd() {
        this.checkEnd();
        this.end = true;
        if (this.av != null) {
            this.av.visitEnd();
        }
    }
    
    private void checkEnd() {
        if (this.end) {
            throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
        }
    }
    
    private void checkName(final String s) {
        if (this.named && s == null) {
            throw new IllegalArgumentException("Annotation value name must not be null");
        }
    }
}
