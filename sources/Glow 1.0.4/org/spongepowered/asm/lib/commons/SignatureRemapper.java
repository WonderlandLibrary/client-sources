package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.signature.*;
import java.util.*;

public class SignatureRemapper extends SignatureVisitor
{
    private final SignatureVisitor v;
    private final Remapper remapper;
    private Stack<String> classNames;
    
    public SignatureRemapper(final SignatureVisitor signatureVisitor, final Remapper remapper) {
        this(327680, signatureVisitor, remapper);
    }
    
    protected SignatureRemapper(final int n, final SignatureVisitor v, final Remapper remapper) {
        super(n);
        this.classNames = new Stack<String>();
        this.v = v;
        this.remapper = remapper;
    }
    
    @Override
    public void visitClassType(final String s) {
        this.classNames.push(s);
        this.v.visitClassType(this.remapper.mapType(s));
    }
    
    @Override
    public void visitInnerClassType(final String s) {
        final String s2 = this.classNames.pop();
        final String string = s2 + '$' + s;
        this.classNames.push(string);
        final String string2 = this.remapper.mapType(s2) + '$';
        final String mapType = this.remapper.mapType(string);
        this.v.visitInnerClassType(mapType.substring(mapType.startsWith(string2) ? string2.length() : (mapType.lastIndexOf(36) + 1)));
    }
    
    @Override
    public void visitFormalTypeParameter(final String s) {
        this.v.visitFormalTypeParameter(s);
    }
    
    @Override
    public void visitTypeVariable(final String s) {
        this.v.visitTypeVariable(s);
    }
    
    @Override
    public SignatureVisitor visitArrayType() {
        this.v.visitArrayType();
        return this;
    }
    
    @Override
    public void visitBaseType(final char c) {
        this.v.visitBaseType(c);
    }
    
    @Override
    public SignatureVisitor visitClassBound() {
        this.v.visitClassBound();
        return this;
    }
    
    @Override
    public SignatureVisitor visitExceptionType() {
        this.v.visitExceptionType();
        return this;
    }
    
    @Override
    public SignatureVisitor visitInterface() {
        this.v.visitInterface();
        return this;
    }
    
    @Override
    public SignatureVisitor visitInterfaceBound() {
        this.v.visitInterfaceBound();
        return this;
    }
    
    @Override
    public SignatureVisitor visitParameterType() {
        this.v.visitParameterType();
        return this;
    }
    
    @Override
    public SignatureVisitor visitReturnType() {
        this.v.visitReturnType();
        return this;
    }
    
    @Override
    public SignatureVisitor visitSuperclass() {
        this.v.visitSuperclass();
        return this;
    }
    
    @Override
    public void visitTypeArgument() {
        this.v.visitTypeArgument();
    }
    
    @Override
    public SignatureVisitor visitTypeArgument(final char c) {
        this.v.visitTypeArgument(c);
        return this;
    }
    
    @Override
    public void visitEnd() {
        this.v.visitEnd();
        this.classNames.pop();
    }
}
