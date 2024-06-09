package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.*;

public class AnnotationRemapper extends AnnotationVisitor
{
    protected final Remapper remapper;
    
    public AnnotationRemapper(final AnnotationVisitor annotationVisitor, final Remapper remapper) {
        this(327680, annotationVisitor, remapper);
    }
    
    protected AnnotationRemapper(final int n, final AnnotationVisitor annotationVisitor, final Remapper remapper) {
        super(n, annotationVisitor);
        this.remapper = remapper;
    }
    
    @Override
    public void visit(final String s, final Object o) {
        this.av.visit(s, this.remapper.mapValue(o));
    }
    
    @Override
    public void visitEnum(final String s, final String s2, final String s3) {
        this.av.visitEnum(s, this.remapper.mapDesc(s2), s3);
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String s, final String s2) {
        final AnnotationVisitor visitAnnotation = this.av.visitAnnotation(s, this.remapper.mapDesc(s2));
        return (visitAnnotation == null) ? null : ((visitAnnotation == this.av) ? this : new AnnotationRemapper(visitAnnotation, this.remapper));
    }
    
    @Override
    public AnnotationVisitor visitArray(final String s) {
        final AnnotationVisitor visitArray = this.av.visitArray(s);
        return (visitArray == null) ? null : ((visitArray == this.av) ? this : new AnnotationRemapper(visitArray, this.remapper));
    }
}
