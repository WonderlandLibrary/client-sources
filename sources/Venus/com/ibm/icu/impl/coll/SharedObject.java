/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.util.ICUCloneNotSupportedException;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SharedObject
implements Cloneable {
    private AtomicInteger refCount = new AtomicInteger();

    public SharedObject clone() {
        SharedObject sharedObject;
        try {
            sharedObject = (SharedObject)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
        sharedObject.refCount = new AtomicInteger();
        return sharedObject;
    }

    public final void addRef() {
        this.refCount.incrementAndGet();
    }

    public final void removeRef() {
        this.refCount.decrementAndGet();
    }

    public final int getRefCount() {
        return this.refCount.get();
    }

    public final void deleteIfZeroRefCount() {
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    public static final class Reference<T extends SharedObject>
    implements Cloneable {
        private T ref;

        public Reference(T t) {
            this.ref = t;
            if (t != null) {
                ((SharedObject)t).addRef();
            }
        }

        public Reference<T> clone() {
            Reference reference;
            try {
                reference = (Reference)super.clone();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new ICUCloneNotSupportedException(cloneNotSupportedException);
            }
            if (this.ref != null) {
                ((SharedObject)this.ref).addRef();
            }
            return reference;
        }

        public T readOnly() {
            return this.ref;
        }

        public T copyOnWrite() {
            T t = this.ref;
            if (((SharedObject)t).getRefCount() <= 1) {
                return t;
            }
            SharedObject sharedObject = ((SharedObject)t).clone();
            ((SharedObject)t).removeRef();
            this.ref = sharedObject;
            sharedObject.addRef();
            return (T)sharedObject;
        }

        public void clear() {
            if (this.ref != null) {
                ((SharedObject)this.ref).removeRef();
                this.ref = null;
            }
        }

        protected void finalize() throws Throwable {
            super.finalize();
            this.clear();
        }

        public Object clone() throws CloneNotSupportedException {
            return this.clone();
        }
    }
}

