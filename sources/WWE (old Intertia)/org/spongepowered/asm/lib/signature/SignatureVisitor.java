package org.spongepowered.asm.lib.signature;

public abstract class SignatureVisitor
{
    public static final char EXTENDS = '+';
    public static final char SUPER = '-';
    public static final char INSTANCEOF = '=';
    protected final int api;
    
    public SignatureVisitor(final int api) {
        super();
        if (api != 262144 && api != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = api;
    }
    
    public void visitFormalTypeParameter(final String s) {
    }
    
    public SignatureVisitor visitClassBound() {
        return this;
    }
    
    public SignatureVisitor visitInterfaceBound() {
        return this;
    }
    
    public SignatureVisitor visitSuperclass() {
        return this;
    }
    
    public SignatureVisitor visitInterface() {
        return this;
    }
    
    public SignatureVisitor visitParameterType() {
        return this;
    }
    
    public SignatureVisitor visitReturnType() {
        return this;
    }
    
    public SignatureVisitor visitExceptionType() {
        return this;
    }
    
    public void visitBaseType(final char c) {
    }
    
    public void visitTypeVariable(final String s) {
    }
    
    public SignatureVisitor visitArrayType() {
        return this;
    }
    
    public void visitClassType(final String s) {
    }
    
    public void visitInnerClassType(final String s) {
    }
    
    public void visitTypeArgument() {
    }
    
    public SignatureVisitor visitTypeArgument(final char c) {
        return this;
    }
    
    public void visitEnd() {
    }
}
