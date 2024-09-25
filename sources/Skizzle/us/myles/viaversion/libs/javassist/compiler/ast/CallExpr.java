/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.compiler.ast;

import us.myles.viaversion.libs.javassist.compiler.CompileError;
import us.myles.viaversion.libs.javassist.compiler.MemberResolver;
import us.myles.viaversion.libs.javassist.compiler.ast.ASTList;
import us.myles.viaversion.libs.javassist.compiler.ast.ASTree;
import us.myles.viaversion.libs.javassist.compiler.ast.Expr;
import us.myles.viaversion.libs.javassist.compiler.ast.Visitor;

public class CallExpr
extends Expr {
    private static final long serialVersionUID = 1L;
    private MemberResolver.Method method = null;

    private CallExpr(ASTree _head, ASTList _tail) {
        super(67, _head, _tail);
    }

    public void setMethod(MemberResolver.Method m) {
        this.method = m;
    }

    public MemberResolver.Method getMethod() {
        return this.method;
    }

    public static CallExpr makeCall(ASTree target, ASTree args) {
        return new CallExpr(target, new ASTList(args));
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atCallExpr(this);
    }
}

