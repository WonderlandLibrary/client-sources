/*
 * Decompiled with CFR 0.143.
 */
package javassist.scopedpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.LoaderClassPath;
import javassist.scopedpool.ScopedClassPool;
import javassist.scopedpool.ScopedClassPoolFactory;
import javassist.scopedpool.ScopedClassPoolFactoryImpl;
import javassist.scopedpool.ScopedClassPoolRepository;

public class ScopedClassPoolRepositoryImpl
implements ScopedClassPoolRepository {
    private static final ScopedClassPoolRepositoryImpl instance = new ScopedClassPoolRepositoryImpl();
    private boolean prune = true;
    boolean pruneWhenCached;
    protected Map registeredCLs = Collections.synchronizedMap(new WeakHashMap());
    protected ClassPool classpool = ClassPool.getDefault();
    protected ScopedClassPoolFactory factory = new ScopedClassPoolFactoryImpl();

    public static ScopedClassPoolRepository getInstance() {
        return instance;
    }

    private ScopedClassPoolRepositoryImpl() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        this.classpool.insertClassPath(new LoaderClassPath(cl));
    }

    @Override
    public boolean isPrune() {
        return this.prune;
    }

    @Override
    public void setPrune(boolean prune) {
        this.prune = prune;
    }

    @Override
    public ScopedClassPool createScopedClassPool(ClassLoader cl, ClassPool src) {
        return this.factory.create(cl, src, this);
    }

    @Override
    public ClassPool findClassPool(ClassLoader cl) {
        if (cl == null) {
            return this.registerClassLoader(ClassLoader.getSystemClassLoader());
        }
        return this.registerClassLoader(cl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ClassPool registerClassLoader(ClassLoader ucl) {
        Map map = this.registeredCLs;
        synchronized (map) {
            if (this.registeredCLs.containsKey(ucl)) {
                return (ClassPool)this.registeredCLs.get(ucl);
            }
            ScopedClassPool pool = this.createScopedClassPool(ucl, this.classpool);
            this.registeredCLs.put(ucl, pool);
            return pool;
        }
    }

    @Override
    public Map getRegisteredCLs() {
        this.clearUnregisteredClassLoaders();
        return this.registeredCLs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clearUnregisteredClassLoaders() {
        ArrayList<ClassLoader> toUnregister = null;
        Map map = this.registeredCLs;
        synchronized (map) {
            Iterator it = this.registeredCLs.values().iterator();
            while (it.hasNext()) {
                ScopedClassPool pool = (ScopedClassPool)it.next();
                if (!pool.isUnloadedClassLoader()) continue;
                it.remove();
                ClassLoader cl = pool.getClassLoader();
                if (cl == null) continue;
                if (toUnregister == null) {
                    toUnregister = new ArrayList<ClassLoader>();
                }
                toUnregister.add(cl);
            }
            if (toUnregister != null) {
                for (int i = 0; i < toUnregister.size(); ++i) {
                    this.unregisterClassLoader((ClassLoader)toUnregister.get(i));
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void unregisterClassLoader(ClassLoader cl) {
        Map map = this.registeredCLs;
        synchronized (map) {
            ScopedClassPool pool = (ScopedClassPool)this.registeredCLs.remove(cl);
            if (pool != null) {
                pool.close();
            }
        }
    }

    public void insertDelegate(ScopedClassPoolRepository delegate) {
    }

    @Override
    public void setClassPoolFactory(ScopedClassPoolFactory factory) {
        this.factory = factory;
    }

    @Override
    public ScopedClassPoolFactory getClassPoolFactory() {
        return this.factory;
    }
}

