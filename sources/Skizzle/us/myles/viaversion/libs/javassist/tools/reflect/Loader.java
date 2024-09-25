/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.tools.reflect;

import us.myles.viaversion.libs.javassist.CannotCompileException;
import us.myles.viaversion.libs.javassist.ClassPool;
import us.myles.viaversion.libs.javassist.NotFoundException;
import us.myles.viaversion.libs.javassist.tools.reflect.Reflection;

public class Loader
extends us.myles.viaversion.libs.javassist.Loader {
    protected Reflection reflection;

    public static void main(String[] args) throws Throwable {
        Loader cl = new Loader();
        cl.run(args);
    }

    public Loader() throws CannotCompileException, NotFoundException {
        this.delegateLoadingOf("us.myles.viaversion.libs.javassist.tools.reflect.Loader");
        this.reflection = new Reflection();
        ClassPool pool = ClassPool.getDefault();
        this.addTranslator(pool, this.reflection);
    }

    public boolean makeReflective(String clazz, String metaobject, String metaclass) throws CannotCompileException, NotFoundException {
        return this.reflection.makeReflective(clazz, metaobject, metaclass);
    }
}

