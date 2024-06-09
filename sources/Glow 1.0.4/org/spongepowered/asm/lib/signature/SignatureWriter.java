package org.spongepowered.asm.lib.signature;

public class SignatureWriter extends SignatureVisitor
{
    private final StringBuilder buf;
    private boolean hasFormals;
    private boolean hasParameters;
    private int argumentStack;
    
    public SignatureWriter() {
        super(327680);
        this.buf = new StringBuilder();
    }
    
    @Override
    public void visitFormalTypeParameter(final String s) {
        if (!this.hasFormals) {
            this.hasFormals = true;
            this.buf.append('<');
        }
        this.buf.append(s);
        this.buf.append(':');
    }
    
    @Override
    public SignatureVisitor visitClassBound() {
        return this;
    }
    
    @Override
    public SignatureVisitor visitInterfaceBound() {
        this.buf.append(':');
        return this;
    }
    
    @Override
    public SignatureVisitor visitSuperclass() {
        this.endFormals();
        return this;
    }
    
    @Override
    public SignatureVisitor visitInterface() {
        return this;
    }
    
    @Override
    public SignatureVisitor visitParameterType() {
        this.endFormals();
        if (!this.hasParameters) {
            this.hasParameters = true;
            this.buf.append('(');
        }
        return this;
    }
    
    @Override
    public SignatureVisitor visitReturnType() {
        this.endFormals();
        if (!this.hasParameters) {
            this.buf.append('(');
        }
        this.buf.append(')');
        return this;
    }
    
    @Override
    public SignatureVisitor visitExceptionType() {
        this.buf.append('^');
        return this;
    }
    
    @Override
    public void visitBaseType(final char c) {
        this.buf.append(c);
    }
    
    @Override
    public void visitTypeVariable(final String s) {
        this.buf.append('T');
        this.buf.append(s);
        this.buf.append(';');
    }
    
    @Override
    public SignatureVisitor visitArrayType() {
        this.buf.append('[');
        return this;
    }
    
    @Override
    public void visitClassType(final String s) {
        this.buf.append('L');
        this.buf.append(s);
        this.argumentStack *= 2;
    }
    
    @Override
    public void visitInnerClassType(final String s) {
        this.endArguments();
        this.buf.append('.');
        this.buf.append(s);
        this.argumentStack *= 2;
    }
    
    @Override
    public void visitTypeArgument() {
        if (this.argumentStack % 2 == 0) {
            ++this.argumentStack;
            this.buf.append('<');
        }
        this.buf.append('*');
    }
    
    @Override
    public SignatureVisitor visitTypeArgument(final char c) {
        if (this.argumentStack % 2 == 0) {
            ++this.argumentStack;
            this.buf.append('<');
        }
        if (c != '=') {
            this.buf.append(c);
        }
        return this;
    }
    
    @Override
    public void visitEnd() {
        this.endArguments();
        this.buf.append(';');
    }
    
    @Override
    public String toString() {
        return this.buf.toString();
    }
    
    private void endFormals() {
        if (this.hasFormals) {
            this.hasFormals = false;
            this.buf.append('>');
        }
    }
    
    private void endArguments() {
        if (this.argumentStack % 2 != 0) {
            this.buf.append('>');
        }
        this.argumentStack /= 2;
    }
}
