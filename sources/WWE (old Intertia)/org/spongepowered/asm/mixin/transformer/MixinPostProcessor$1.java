package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.*;

class MixinPostProcessor$1 extends ClassVisitor {
    final MixinPostProcessor this$0;
    
    MixinPostProcessor$1(final MixinPostProcessor this$0, final int n, final ClassVisitor classVisitor) {
        this.this$0 = this$0;
        super(n, classVisitor);
    }
    
    @Override
    public void visit(final int n, final int n2, final String s, final String s2, final String s3, final String[] array) {
        super.visit(n, n2 | 0x1, s, s2, s3, array);
    }
    
    @Override
    public FieldVisitor visitField(int n, final String s, final String s2, final String s3, final Object o) {
        if ((n & 0x6) == 0x0) {
            n |= 0x1;
        }
        return super.visitField(n, s, s2, s3, o);
    }
    
    @Override
    public MethodVisitor visitMethod(int n, final String s, final String s2, final String s3, final String[] array) {
        if ((n & 0x6) == 0x0) {
            n |= 0x1;
        }
        return super.visitMethod(n, s, s2, s3, array);
    }
}