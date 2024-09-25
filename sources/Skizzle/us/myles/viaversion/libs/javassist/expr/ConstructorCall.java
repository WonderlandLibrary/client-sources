/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.expr;

import us.myles.viaversion.libs.javassist.CtClass;
import us.myles.viaversion.libs.javassist.CtConstructor;
import us.myles.viaversion.libs.javassist.CtMethod;
import us.myles.viaversion.libs.javassist.NotFoundException;
import us.myles.viaversion.libs.javassist.bytecode.CodeIterator;
import us.myles.viaversion.libs.javassist.bytecode.MethodInfo;
import us.myles.viaversion.libs.javassist.expr.MethodCall;

public class ConstructorCall
extends MethodCall {
    protected ConstructorCall(int pos, CodeIterator i, CtClass decl, MethodInfo m) {
        super(pos, i, decl, m);
    }

    @Override
    public String getMethodName() {
        return this.isSuper() ? "super" : "this";
    }

    @Override
    public CtMethod getMethod() throws NotFoundException {
        throw new NotFoundException("this is a constructor call.  Call getConstructor().");
    }

    public CtConstructor getConstructor() throws NotFoundException {
        return this.getCtClass().getConstructor(this.getSignature());
    }

    @Override
    public boolean isSuper() {
        return super.isSuper();
    }
}

