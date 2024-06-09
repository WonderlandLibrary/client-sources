/*
 * Decompiled with CFR 0.143.
 */
package javassist.compiler.ast;

import javassist.compiler.CompileError;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.Visitor;

public class Symbol
extends ASTree {
    protected String identifier;

    public Symbol(String sym) {
        this.identifier = sym;
    }

    public String get() {
        return this.identifier;
    }

    @Override
    public String toString() {
        return this.identifier;
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atSymbol(this);
    }
}

