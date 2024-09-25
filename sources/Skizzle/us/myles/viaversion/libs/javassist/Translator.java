/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist;

import us.myles.viaversion.libs.javassist.CannotCompileException;
import us.myles.viaversion.libs.javassist.ClassPool;
import us.myles.viaversion.libs.javassist.NotFoundException;

public interface Translator {
    public void start(ClassPool var1) throws NotFoundException, CannotCompileException;

    public void onLoad(ClassPool var1, String var2) throws NotFoundException, CannotCompileException;
}

