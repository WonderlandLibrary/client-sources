/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.compiler.ast;

import us.myles.viaversion.libs.javassist.compiler.CompileError;
import us.myles.viaversion.libs.javassist.compiler.TokenId;
import us.myles.viaversion.libs.javassist.compiler.ast.ASTList;
import us.myles.viaversion.libs.javassist.compiler.ast.ASTree;
import us.myles.viaversion.libs.javassist.compiler.ast.ArrayInit;
import us.myles.viaversion.libs.javassist.compiler.ast.Visitor;

public class NewExpr
extends ASTList
implements TokenId {
    private static final long serialVersionUID = 1L;
    protected boolean newArray;
    protected int arrayType;

    public NewExpr(ASTList className, ASTList args) {
        super(className, new ASTList(args));
        this.newArray = false;
        this.arrayType = 307;
    }

    public NewExpr(int type, ASTList arraySize, ArrayInit init) {
        super(null, new ASTList(arraySize));
        this.newArray = true;
        this.arrayType = type;
        if (init != null) {
            NewExpr.append(this, init);
        }
    }

    public static NewExpr makeObjectArray(ASTList className, ASTList arraySize, ArrayInit init) {
        NewExpr e = new NewExpr(className, arraySize);
        e.newArray = true;
        if (init != null) {
            NewExpr.append(e, init);
        }
        return e;
    }

    public boolean isArray() {
        return this.newArray;
    }

    public int getArrayType() {
        return this.arrayType;
    }

    public ASTList getClassName() {
        return (ASTList)this.getLeft();
    }

    public ASTList getArguments() {
        return (ASTList)this.getRight().getLeft();
    }

    public ASTList getArraySize() {
        return this.getArguments();
    }

    public ArrayInit getInitializer() {
        ASTree t = this.getRight().getRight();
        if (t == null) {
            return null;
        }
        return (ArrayInit)t.getLeft();
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atNewExpr(this);
    }

    @Override
    protected String getTag() {
        return this.newArray ? "new[]" : "new";
    }
}

