/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.compiler.ast;

import us.myles.viaversion.libs.javassist.compiler.CompileError;
import us.myles.viaversion.libs.javassist.compiler.ast.ASTList;
import us.myles.viaversion.libs.javassist.compiler.ast.ASTree;
import us.myles.viaversion.libs.javassist.compiler.ast.Expr;
import us.myles.viaversion.libs.javassist.compiler.ast.Visitor;

public class BinExpr
extends Expr {
    private static final long serialVersionUID = 1L;

    private BinExpr(int op, ASTree _head, ASTList _tail) {
        super(op, _head, _tail);
    }

    public static BinExpr makeBin(int op, ASTree oprand1, ASTree oprand2) {
        return new BinExpr(op, oprand1, new ASTList(oprand2));
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atBinExpr(this);
    }
}

