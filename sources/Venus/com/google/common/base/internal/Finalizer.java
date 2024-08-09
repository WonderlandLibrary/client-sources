/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public class Finalizer
implements Runnable {
    private static final Logger logger = Logger.getLogger(Finalizer.class.getName());
    private static final String FINALIZABLE_REFERENCE = "com.google.common.base.FinalizableReference";
    private final WeakReference<Class<?>> finalizableReferenceClassReference;
    private final PhantomReference<Object> frqReference;
    private final ReferenceQueue<Object> queue;
    private static final Field inheritableThreadLocals = Finalizer.getInheritableThreadLocalsField();

    public static void startFinalizer(Class<?> clazz, ReferenceQueue<Object> referenceQueue, PhantomReference<Object> phantomReference) {
        if (!clazz.getName().equals(FINALIZABLE_REFERENCE)) {
            throw new IllegalArgumentException("Expected com.google.common.base.FinalizableReference.");
        }
        Finalizer finalizer = new Finalizer(clazz, referenceQueue, phantomReference);
        Thread thread2 = new Thread(finalizer);
        thread2.setName(Finalizer.class.getName());
        thread2.setDaemon(false);
        try {
            if (inheritableThreadLocals != null) {
                inheritableThreadLocals.set(thread2, null);
            }
        } catch (Throwable throwable) {
            logger.log(Level.INFO, "Failed to clear thread local values inherited by reference finalizer thread.", throwable);
        }
        thread2.start();
    }

    private Finalizer(Class<?> clazz, ReferenceQueue<Object> referenceQueue, PhantomReference<Object> phantomReference) {
        this.queue = referenceQueue;
        this.finalizableReferenceClassReference = new WeakReference(clazz);
        this.frqReference = phantomReference;
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (this.cleanUp(this.queue.remove())) {
                }
            } catch (InterruptedException interruptedException) {
                continue;
            }
            break;
        }
    }

    private boolean cleanUp(Reference<?> reference) {
        Method method = this.getFinalizeReferentMethod();
        if (method == null) {
            return true;
        }
        do {
            reference.clear();
            if (reference == this.frqReference) {
                return true;
            }
            try {
                method.invoke(reference, new Object[0]);
            } catch (Throwable throwable) {
                logger.log(Level.SEVERE, "Error cleaning up after reference.", throwable);
            }
        } while ((reference = this.queue.poll()) != null);
        return false;
    }

    @Nullable
    private Method getFinalizeReferentMethod() {
        Class clazz = (Class)this.finalizableReferenceClassReference.get();
        if (clazz == null) {
            return null;
        }
        try {
            return clazz.getMethod("finalizeReferent", new Class[0]);
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new AssertionError((Object)noSuchMethodException);
        }
    }

    @Nullable
    public static Field getInheritableThreadLocalsField() {
        try {
            Field field = Thread.class.getDeclaredField("inheritableThreadLocals");
            field.setAccessible(false);
            return field;
        } catch (Throwable throwable) {
            logger.log(Level.INFO, "Couldn't access Thread.inheritableThreadLocals. Reference finalizer threads will inherit thread local values.");
            return null;
        }
    }
}

