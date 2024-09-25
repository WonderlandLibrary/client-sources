/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.scopedpool;

import us.myles.viaversion.libs.javassist.ClassPool;
import us.myles.viaversion.libs.javassist.scopedpool.ScopedClassPool;
import us.myles.viaversion.libs.javassist.scopedpool.ScopedClassPoolFactory;
import us.myles.viaversion.libs.javassist.scopedpool.ScopedClassPoolRepository;

public class ScopedClassPoolFactoryImpl
implements ScopedClassPoolFactory {
    @Override
    public ScopedClassPool create(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository) {
        return new ScopedClassPool(cl, src, repository, false);
    }

    @Override
    public ScopedClassPool create(ClassPool src, ScopedClassPoolRepository repository) {
        return new ScopedClassPool(null, src, repository, true);
    }
}

