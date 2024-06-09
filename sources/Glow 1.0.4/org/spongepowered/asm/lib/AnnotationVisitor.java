package org.spongepowered.asm.lib;

public abstract class AnnotationVisitor
{
    protected final int api;
    protected AnnotationVisitor av;
    
    public AnnotationVisitor(final int n) {
        this(n, null);
    }
    
    public AnnotationVisitor(final int api, final AnnotationVisitor av) {
        super();
        if (api != 262144 && api != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = api;
        this.av = av;
    }
    
    public void visit(final String s, final Object o) {
        if (this.av != null) {
            this.av.visit(s, o);
        }
    }
    
    public void visitEnum(final String s, final String s2, final String s3) {
        if (this.av != null) {
            this.av.visitEnum(s, s2, s3);
        }
    }
    
    public AnnotationVisitor visitAnnotation(final String s, final String s2) {
        if (this.av != null) {
            return this.av.visitAnnotation(s, s2);
        }
        return null;
    }
    
    public AnnotationVisitor visitArray(final String s) {
        if (this.av != null) {
            return this.av.visitArray(s);
        }
        return null;
    }
    
    public void visitEnd() {
        if (this.av != null) {
            this.av.visitEnd();
        }
    }
}
