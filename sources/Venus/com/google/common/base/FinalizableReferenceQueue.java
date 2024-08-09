/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.FinalizableReference;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

@GwtIncompatible
public class FinalizableReferenceQueue
implements Closeable {
    private static final Logger logger = Logger.getLogger(FinalizableReferenceQueue.class.getName());
    private static final String FINALIZER_CLASS_NAME = "com.google.common.base.internal.Finalizer";
    private static final Method startFinalizer;
    final ReferenceQueue<Object> queue = new ReferenceQueue();
    final PhantomReference<Object> frqRef = new PhantomReference<Object>(this, this.queue);
    final boolean threadStarted;

    public FinalizableReferenceQueue() {
        boolean bl = false;
        try {
            startFinalizer.invoke(null, FinalizableReference.class, this.queue, this.frqRef);
            bl = true;
        } catch (IllegalAccessException illegalAccessException) {
            throw new AssertionError((Object)illegalAccessException);
        } catch (Throwable throwable) {
            logger.log(Level.INFO, "Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.", throwable);
        }
        this.threadStarted = bl;
    }

    @Override
    public void close() {
        this.frqRef.enqueue();
        this.cleanUp();
    }

    void cleanUp() {
        Reference<Object> reference;
        if (this.threadStarted) {
            return;
        }
        while ((reference = this.queue.poll()) != null) {
            reference.clear();
            try {
                ((FinalizableReference)((Object)reference)).finalizeReferent();
            } catch (Throwable throwable) {
                logger.log(Level.SEVERE, "Error cleaning up after reference.", throwable);
            }
        }
    }

    private static Class<?> loadFinalizer(FinalizerLoader ... finalizerLoaderArray) {
        for (FinalizerLoader finalizerLoader : finalizerLoaderArray) {
            Class<?> clazz = finalizerLoader.loadFinalizer();
            if (clazz == null) continue;
            return clazz;
        }
        throw new AssertionError();
    }

    static Method getStartFinalizer(Class<?> clazz) {
        try {
            return clazz.getMethod("startFinalizer", Class.class, ReferenceQueue.class, PhantomReference.class);
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new AssertionError((Object)noSuchMethodException);
        }
    }

    static Logger access$000() {
        return logger;
    }

    static {
        Class<?> clazz = FinalizableReferenceQueue.loadFinalizer(new SystemLoader(), new DecoupledLoader(), new DirectLoader());
        startFinalizer = FinalizableReferenceQueue.getStartFinalizer(clazz);
    }

    static class DirectLoader
    implements FinalizerLoader {
        DirectLoader() {
        }

        @Override
        public Class<?> loadFinalizer() {
            try {
                return Class.forName(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
            } catch (ClassNotFoundException classNotFoundException) {
                throw new AssertionError((Object)classNotFoundException);
            }
        }
    }

    static class DecoupledLoader
    implements FinalizerLoader {
        private static final String LOADING_ERROR = "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Guava to your system class path.";

        DecoupledLoader() {
        }

        @Override
        @Nullable
        public Class<?> loadFinalizer() {
            try {
                URLClassLoader uRLClassLoader = this.newLoader(this.getBaseUrl());
                return uRLClassLoader.loadClass(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
            } catch (Exception exception) {
                FinalizableReferenceQueue.access$000().log(Level.WARNING, LOADING_ERROR, exception);
                return null;
            }
        }

        URL getBaseUrl() throws IOException {
            String string = FinalizableReferenceQueue.FINALIZER_CLASS_NAME.replace('.', '/') + ".class";
            URL uRL = this.getClass().getClassLoader().getResource(string);
            if (uRL == null) {
                throw new FileNotFoundException(string);
            }
            String string2 = uRL.toString();
            if (!string2.endsWith(string)) {
                throw new IOException("Unsupported path style: " + string2);
            }
            string2 = string2.substring(0, string2.length() - string.length());
            return new URL(uRL, string2);
        }

        URLClassLoader newLoader(URL uRL) {
            return new URLClassLoader(new URL[]{uRL}, null);
        }
    }

    static class SystemLoader
    implements FinalizerLoader {
        @VisibleForTesting
        static boolean disabled;

        SystemLoader() {
        }

        @Override
        @Nullable
        public Class<?> loadFinalizer() {
            ClassLoader classLoader;
            if (disabled) {
                return null;
            }
            try {
                classLoader = ClassLoader.getSystemClassLoader();
            } catch (SecurityException securityException) {
                FinalizableReferenceQueue.access$000().info("Not allowed to access system class loader.");
                return null;
            }
            if (classLoader != null) {
                try {
                    return classLoader.loadClass(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
                } catch (ClassNotFoundException classNotFoundException) {
                    return null;
                }
            }
            return null;
        }
    }

    static interface FinalizerLoader {
        @Nullable
        public Class<?> loadFinalizer();
    }
}

