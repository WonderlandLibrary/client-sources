package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.commons.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;

static class InnerClassAdapter extends ClassRemapper
{
    private final InnerClassInfo info;
    
    public InnerClassAdapter(final ClassVisitor classVisitor, final InnerClassInfo info) {
        super(327680, classVisitor, info);
        this.info = info;
    }
    
    @Override
    public void visitSource(final String s, final String s2) {
        super.visitSource(s, s2);
        final AnnotationVisitor visitAnnotation = this.cv.visitAnnotation("Lorg/spongepowered/asm/mixin/transformer/meta/MixinInner;", false);
        visitAnnotation.visit("mixin", this.info.getOwner().toString());
        visitAnnotation.visit("name", this.info.getOriginalName().substring(this.info.getOriginalName().lastIndexOf(47) + 1));
        visitAnnotation.visitEnd();
    }
    
    @Override
    public void visitInnerClass(final String s, final String s2, final String s3, final int n) {
        if (s.startsWith(this.info.getOriginalName() + "$")) {
            throw new InvalidMixinException(this.info.getOwner(), "Found unsupported nested inner class " + s + " in " + this.info.getOriginalName());
        }
        super.visitInnerClass(s, s2, s3, n);
    }
}
