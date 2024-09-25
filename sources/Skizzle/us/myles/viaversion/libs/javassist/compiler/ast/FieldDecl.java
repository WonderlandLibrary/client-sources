/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.compiler.ast;

import us.myles.viaversion.libs.javassist.compiler.CompileError;
import us.myles.viaversion.libs.javassist.compiler.ast.ASTList;
import us.myles.viaversion.libs.javassist.compiler.ast.ASTree;
import us.myles.viaversion.libs.javassist.compiler.ast.Declarator;
import us.myles.viaversion.libs.javassist.compiler.ast.Visitor;

public class FieldDecl
extends ASTList {
    private static final long serialVersionUID = 1L;

    public FieldDecl(ASTree _head, ASTList _tail) {
        super(_head, _tail);
    }

    public ASTList getModifiers() {
        return (ASTList)this.getLeft();
    }

    public Declarator getDeclarator() {
        return (Declarator)this.tail().head();
    }

    public ASTree getInit() {
        return this.sublist(2).head();
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atFieldDecl(this);
    }
}

