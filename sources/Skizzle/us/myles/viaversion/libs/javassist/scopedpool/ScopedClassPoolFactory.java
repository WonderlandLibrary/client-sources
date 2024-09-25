/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.scopedpool;

import us.myles.viaversion.libs.javassist.ClassPool;
import us.myles.viaversion.libs.javassist.scopedpool.ScopedClassPool;
import us.myles.viaversion.libs.javassist.scopedpool.ScopedClassPoolRepository;

public interface ScopedClassPoolFactory {
    public ScopedClassPool create(ClassLoader var1, ClassPool var2, ScopedClassPoolRepository var3);

    public ScopedClassPool create(ClassPool var1, ScopedClassPoolRepository var2);
}

