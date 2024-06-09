/*
 * Decompiled with CFR 0.143.
 */
package javassist.compiler.ast;

import javassist.compiler.CompileError;
import javassist.compiler.ast.ASTList;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.Visitor;

public class ArrayInit
extends ASTList {
    public ArrayInit(ASTree firstElement) {
        super(firstElement);
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atArrayInit(this);
    }

    @Override
    public String getTag() {
        return "array";
    }
}

