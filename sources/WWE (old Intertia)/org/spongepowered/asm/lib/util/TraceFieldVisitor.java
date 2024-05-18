package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.*;

public final class TraceFieldVisitor extends FieldVisitor
{
    public final Printer p;
    
    public TraceFieldVisitor(final Printer printer) {
        this(null, printer);
    }
    
    public TraceFieldVisitor(final FieldVisitor fieldVisitor, final Printer p2) {
        super(327680, fieldVisitor);
        this.p = p2;
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String s, final boolean b) {
        return new TraceAnnotationVisitor((this.fv == null) ? null : this.fv.visitAnnotation(s, b), this.p.visitFieldAnnotation(s, b));
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        return new TraceAnnotationVisitor((this.fv == null) ? null : this.fv.visitTypeAnnotation(n, typePath, s, b), this.p.visitFieldTypeAnnotation(n, typePath, s, b));
    }
    
    @Override
    public void visitAttribute(final Attribute attribute) {
        this.p.visitFieldAttribute(attribute);
        super.visitAttribute(attribute);
    }
    
    @Override
    public void visitEnd() {
        this.p.visitFieldEnd();
        super.visitEnd();
    }
}
