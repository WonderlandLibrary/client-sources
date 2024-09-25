/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.compiler;

import us.myles.viaversion.libs.javassist.bytecode.Bytecode;
import us.myles.viaversion.libs.javassist.compiler.CompileError;
import us.myles.viaversion.libs.javassist.compiler.JvstCodeGen;
import us.myles.viaversion.libs.javassist.compiler.JvstTypeChecker;
import us.myles.viaversion.libs.javassist.compiler.ast.ASTList;

public interface ProceedHandler {
    public void doit(JvstCodeGen var1, Bytecode var2, ASTList var3) throws CompileError;

    public void setReturnType(JvstTypeChecker var1, ASTList var2) throws CompileError;
}

