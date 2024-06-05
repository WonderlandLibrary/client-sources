package com.google.common.base;

import java.lang.ref.*;

public abstract class FinalizableWeakReference<T> extends WeakReference<T> implements FinalizableReference
{
    protected FinalizableWeakReference(final T referent, final FinalizableReferenceQueue queue) {
        super(referent, queue.queue);
        queue.cleanUp();
    }
}
