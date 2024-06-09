package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.*;

public class MethodRemapper extends MethodVisitor
{
    protected final Remapper remapper;
    
    public MethodRemapper(final MethodVisitor methodVisitor, final Remapper remapper) {
        this(327680, methodVisitor, remapper);
    }
    
    protected MethodRemapper(final int n, final MethodVisitor methodVisitor, final Remapper remapper) {
        super(n, methodVisitor);
        this.remapper = remapper;
    }
    
    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        final AnnotationVisitor visitAnnotationDefault = super.visitAnnotationDefault();
        return (visitAnnotationDefault == null) ? visitAnnotationDefault : new AnnotationRemapper(visitAnnotationDefault, this.remapper);
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String s, final boolean b) {
        final AnnotationVisitor visitAnnotation = super.visitAnnotation(this.remapper.mapDesc(s), b);
        return (visitAnnotation == null) ? visitAnnotation : new AnnotationRemapper(visitAnnotation, this.remapper);
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        final AnnotationVisitor visitTypeAnnotation = super.visitTypeAnnotation(n, typePath, this.remapper.mapDesc(s), b);
        return (visitTypeAnnotation == null) ? visitTypeAnnotation : new AnnotationRemapper(visitTypeAnnotation, this.remapper);
    }
    
    @Override
    public AnnotationVisitor visitParameterAnnotation(final int n, final String s, final boolean b) {
        final AnnotationVisitor visitParameterAnnotation = super.visitParameterAnnotation(n, this.remapper.mapDesc(s), b);
        return (visitParameterAnnotation == null) ? visitParameterAnnotation : new AnnotationRemapper(visitParameterAnnotation, this.remapper);
    }
    
    @Override
    public void visitFrame(final int n, final int n2, final Object[] array, final int n3, final Object[] array2) {
        super.visitFrame(n, n2, this.remapEntries(n2, array), n3, this.remapEntries(n3, array2));
    }
    
    private Object[] remapEntries(final int n, final Object[] array) {
        for (int i = 0; i < n; ++i) {
            if (array[i] instanceof String) {
                final Object[] array2 = new Object[n];
                if (i > 0) {
                    System.arraycopy(array, 0, array2, 0, i);
                }
                do {
                    final Object o = array[i];
                    array2[i++] = ((o instanceof String) ? this.remapper.mapType((String)o) : o);
                } while (i < n);
                return array2;
            }
        }
        return array;
    }
    
    @Override
    public void visitFieldInsn(final int n, final String s, final String s2, final String s3) {
        super.visitFieldInsn(n, this.remapper.mapType(s), this.remapper.mapFieldName(s, s2, s3), this.remapper.mapDesc(s3));
    }
    
    @Deprecated
    @Override
    public void visitMethodInsn(final int n, final String s, final String s2, final String s3) {
        if (this.api >= 327680) {
            super.visitMethodInsn(n, s, s2, s3);
            return;
        }
        this.doVisitMethodInsn(n, s, s2, s3, n == 185);
    }
    
    @Override
    public void visitMethodInsn(final int n, final String s, final String s2, final String s3, final boolean b) {
        if (this.api < 327680) {
            super.visitMethodInsn(n, s, s2, s3, b);
            return;
        }
        this.doVisitMethodInsn(n, s, s2, s3, b);
    }
    
    private void doVisitMethodInsn(final int n, final String s, final String s2, final String s3, final boolean b) {
        if (this.mv != null) {
            this.mv.visitMethodInsn(n, this.remapper.mapType(s), this.remapper.mapMethodName(s, s2, s3), this.remapper.mapMethodDesc(s3), b);
        }
    }
    
    @Override
    public void visitInvokeDynamicInsn(final String s, final String s2, final Handle handle, final Object... array) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = this.remapper.mapValue(array[i]);
        }
        super.visitInvokeDynamicInsn(this.remapper.mapInvokeDynamicMethodName(s, s2), this.remapper.mapMethodDesc(s2), (Handle)this.remapper.mapValue(handle), array);
    }
    
    @Override
    public void visitTypeInsn(final int n, final String s) {
        super.visitTypeInsn(n, this.remapper.mapType(s));
    }
    
    @Override
    public void visitLdcInsn(final Object o) {
        super.visitLdcInsn(this.remapper.mapValue(o));
    }
    
    @Override
    public void visitMultiANewArrayInsn(final String s, final int n) {
        super.visitMultiANewArrayInsn(this.remapper.mapDesc(s), n);
    }
    
    @Override
    public AnnotationVisitor visitInsnAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        final AnnotationVisitor visitInsnAnnotation = super.visitInsnAnnotation(n, typePath, this.remapper.mapDesc(s), b);
        return (visitInsnAnnotation == null) ? visitInsnAnnotation : new AnnotationRemapper(visitInsnAnnotation, this.remapper);
    }
    
    @Override
    public void visitTryCatchBlock(final Label label, final Label label2, final Label label3, final String s) {
        super.visitTryCatchBlock(label, label2, label3, (s == null) ? null : this.remapper.mapType(s));
    }
    
    @Override
    public AnnotationVisitor visitTryCatchAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        final AnnotationVisitor visitTryCatchAnnotation = super.visitTryCatchAnnotation(n, typePath, this.remapper.mapDesc(s), b);
        return (visitTryCatchAnnotation == null) ? visitTryCatchAnnotation : new AnnotationRemapper(visitTryCatchAnnotation, this.remapper);
    }
    
    @Override
    public void visitLocalVariable(final String s, final String s2, final String s3, final Label label, final Label label2, final int n) {
        super.visitLocalVariable(s, this.remapper.mapDesc(s2), this.remapper.mapSignature(s3, true), label, label2, n);
    }
    
    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(final int n, final TypePath typePath, final Label[] array, final Label[] array2, final int[] array3, final String s, final boolean b) {
        final AnnotationVisitor visitLocalVariableAnnotation = super.visitLocalVariableAnnotation(n, typePath, array, array2, array3, this.remapper.mapDesc(s), b);
        return (visitLocalVariableAnnotation == null) ? visitLocalVariableAnnotation : new AnnotationRemapper(visitLocalVariableAnnotation, this.remapper);
    }
}
