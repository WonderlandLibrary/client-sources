/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.scopedpool;

import java.util.Map;
import us.myles.viaversion.libs.javassist.ClassPool;
import us.myles.viaversion.libs.javassist.scopedpool.ScopedClassPool;
import us.myles.viaversion.libs.javassist.scopedpool.ScopedClassPoolFactory;

public interface ScopedClassPoolRepository {
    public void setClassPoolFactory(ScopedClassPoolFactory var1);

    public ScopedClassPoolFactory getClassPoolFactory();

    public boolean isPrune();

    public void setPrune(boolean var1);

    public ScopedClassPool createScopedClassPool(ClassLoader var1, ClassPool var2);

    public ClassPool findClassPool(ClassLoader var1);

    public ClassPool registerClassLoader(ClassLoader var1);

    public Map<ClassLoader, ScopedClassPool> getRegisteredCLs();

    public void clearUnregisteredClassLoaders();

    public void unregisterClassLoader(ClassLoader var1);
}

