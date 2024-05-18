/*
 * Decompiled with CFR 0.143.
 */
package javassist.compiler.ast;

import javassist.compiler.CompileError;
import javassist.compiler.MemberResolver;
import javassist.compiler.ast.ASTList;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.Expr;
import javassist.compiler.ast.Visitor;

public class CallExpr
extends Expr {
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

