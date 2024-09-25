/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.compiler.ast;

import us.myles.viaversion.libs.javassist.compiler.CompileError;
import us.myles.viaversion.libs.javassist.compiler.ast.ASTList;
import us.myles.viaversion.libs.javassist.compiler.ast.ASTree;
import us.myles.viaversion.libs.javassist.compiler.ast.Visitor;

public class CondExpr
extends ASTList {
    private static final long serialVersionUID = 1L;

    public CondExpr(ASTree cond, ASTree thenp, ASTree elsep) {
        super(cond, new ASTList(thenp, new ASTList(elsep)));
    }

    public ASTree condExpr() {
        return this.head();
    }

    public void setCond(ASTree t) {
        this.setHead(t);
    }

    public ASTree thenExpr() {
        return this.tail().head();
    }

    public void setThen(ASTree t) {
        this.tail().setHead(t);
    }

    public ASTree elseExpr() {
        return this.tail().tail().head();
    }

    public void setElse(ASTree t) {
        this.tail().tail().setHead(t);
    }

    @Override
    public String getTag() {
        return "?:";
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atCondExpr(this);
    }
}

