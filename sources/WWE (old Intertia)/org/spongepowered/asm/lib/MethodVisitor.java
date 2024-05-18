package org.spongepowered.asm.lib;

public abstract class MethodVisitor
{
    protected final int api;
    protected MethodVisitor mv;
    
    public MethodVisitor(final int n) {
        this(n, null);
    }
    
    public MethodVisitor(final int api, final MethodVisitor mv) {
        super();
        if (api != 262144 && api != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = api;
        this.mv = mv;
    }
    
    public void visitParameter(final String s, final int n) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            this.mv.visitParameter(s, n);
        }
    }
    
    public AnnotationVisitor visitAnnotationDefault() {
        if (this.mv != null) {
            return this.mv.visitAnnotationDefault();
        }
        return null;
    }
    
    public AnnotationVisitor visitAnnotation(final String s, final boolean b) {
        if (this.mv != null) {
            return this.mv.visitAnnotation(s, b);
        }
        return null;
    }
    
    public AnnotationVisitor visitTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            return this.mv.visitTypeAnnotation(n, typePath, s, b);
        }
        return null;
    }
    
    public AnnotationVisitor visitParameterAnnotation(final int n, final String s, final boolean b) {
        if (this.mv != null) {
            return this.mv.visitParameterAnnotation(n, s, b);
        }
        return null;
    }
    
    public void visitAttribute(final Attribute attribute) {
        if (this.mv != null) {
            this.mv.visitAttribute(attribute);
        }
    }
    
    public void visitCode() {
        if (this.mv != null) {
            this.mv.visitCode();
        }
    }
    
    public void visitFrame(final int n, final int n2, final Object[] array, final int n3, final Object[] array2) {
        if (this.mv != null) {
            this.mv.visitFrame(n, n2, array, n3, array2);
        }
    }
    
    public void visitInsn(final int n) {
        if (this.mv != null) {
            this.mv.visitInsn(n);
        }
    }
    
    public void visitIntInsn(final int n, final int n2) {
        if (this.mv != null) {
            this.mv.visitIntInsn(n, n2);
        }
    }
    
    public void visitVarInsn(final int n, final int n2) {
        if (this.mv != null) {
            this.mv.visitVarInsn(n, n2);
        }
    }
    
    public void visitTypeInsn(final int n, final String s) {
        if (this.mv != null) {
            this.mv.visitTypeInsn(n, s);
        }
    }
    
    public void visitFieldInsn(final int n, final String s, final String s2, final String s3) {
        if (this.mv != null) {
            this.mv.visitFieldInsn(n, s, s2, s3);
        }
    }
    
    @Deprecated
    public void visitMethodInsn(final int n, final String s, final String s2, final String s3) {
        if (this.api >= 327680) {
            this.visitMethodInsn(n, s, s2, s3, n == 185);
            return;
        }
        if (this.mv != null) {
            this.mv.visitMethodInsn(n, s, s2, s3);
        }
    }
    
    public void visitMethodInsn(final int n, final String s, final String s2, final String s3, final boolean b) {
        if (this.api >= 327680) {
            if (this.mv != null) {
                this.mv.visitMethodInsn(n, s, s2, s3, b);
            }
            return;
        }
        if (b != (n == 185)) {
            throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
        }
        this.visitMethodInsn(n, s, s2, s3);
    }
    
    public void visitInvokeDynamicInsn(final String s, final String s2, final Handle handle, final Object... array) {
        if (this.mv != null) {
            this.mv.visitInvokeDynamicInsn(s, s2, handle, array);
        }
    }
    
    public void visitJumpInsn(final int n, final Label label) {
        if (this.mv != null) {
            this.mv.visitJumpInsn(n, label);
        }
    }
    
    public void visitLabel(final Label label) {
        if (this.mv != null) {
            this.mv.visitLabel(label);
        }
    }
    
    public void visitLdcInsn(final Object o) {
        if (this.mv != null) {
            this.mv.visitLdcInsn(o);
        }
    }
    
    public void visitIincInsn(final int n, final int n2) {
        if (this.mv != null) {
            this.mv.visitIincInsn(n, n2);
        }
    }
    
    public void visitTableSwitchInsn(final int n, final int n2, final Label label, final Label... array) {
        if (this.mv != null) {
            this.mv.visitTableSwitchInsn(n, n2, label, array);
        }
    }
    
    public void visitLookupSwitchInsn(final Label label, final int[] array, final Label[] array2) {
        if (this.mv != null) {
            this.mv.visitLookupSwitchInsn(label, array, array2);
        }
    }
    
    public void visitMultiANewArrayInsn(final String s, final int n) {
        if (this.mv != null) {
            this.mv.visitMultiANewArrayInsn(s, n);
        }
    }
    
    public AnnotationVisitor visitInsnAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            return this.mv.visitInsnAnnotation(n, typePath, s, b);
        }
        return null;
    }
    
    public void visitTryCatchBlock(final Label label, final Label label2, final Label label3, final String s) {
        if (this.mv != null) {
            this.mv.visitTryCatchBlock(label, label2, label3, s);
        }
    }
    
    public AnnotationVisitor visitTryCatchAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            return this.mv.visitTryCatchAnnotation(n, typePath, s, b);
        }
        return null;
    }
    
    public void visitLocalVariable(final String s, final String s2, final String s3, final Label label, final Label label2, final int n) {
        if (this.mv != null) {
            this.mv.visitLocalVariable(s, s2, s3, label, label2, n);
        }
    }
    
    public AnnotationVisitor visitLocalVariableAnnotation(final int n, final TypePath typePath, final Label[] array, final Label[] array2, final int[] array3, final String s, final boolean b) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            return this.mv.visitLocalVariableAnnotation(n, typePath, array, array2, array3, s, b);
        }
        return null;
    }
    
    public void visitLineNumber(final int n, final Label label) {
        if (this.mv != null) {
            this.mv.visitLineNumber(n, label);
        }
    }
    
    public void visitMaxs(final int n, final int n2) {
        if (this.mv != null) {
            this.mv.visitMaxs(n, n2);
        }
    }
    
    public void visitEnd() {
        if (this.mv != null) {
            this.mv.visitEnd();
        }
    }
}
