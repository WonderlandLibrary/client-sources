package com.ibm.icu.impl.coll;

import java.util.concurrent.atomic.*;
import com.ibm.icu.util.*;

public class SharedObject implements Cloneable
{
    private AtomicInteger refCount;
    
    public SharedObject() {
        this.refCount = new AtomicInteger();
    }
    
    public SharedObject clone() {
        SharedObject c;
        try {
            c = (SharedObject)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
        c.refCount = new AtomicInteger();
        return c;
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
    
    public static final class Reference<T extends SharedObject> implements Cloneable
    {
        private T ref;
        
        public Reference(final T r) {
            this.ref = r;
            if (r != null) {
                r.addRef();
            }
        }
        
        public Reference<T> clone() {
            Reference<T> c;
            try {
                c = (Reference)super.clone();
            }
            catch (CloneNotSupportedException e) {
                throw new ICUCloneNotSupportedException(e);
            }
            if (this.ref != null) {
                this.ref.addRef();
            }
            return c;
        }
        
        public T readOnly() {
            return this.ref;
        }
        
        public T copyOnWrite() {
            final T r = this.ref;
            if (r.getRefCount() <= 1) {
                return r;
            }
            final T r2 = (T)r.clone();
            r.removeRef();
            (this.ref = r2).addRef();
            return r2;
        }
        
        public void clear() {
            if (this.ref != null) {
                this.ref.removeRef();
                this.ref = null;
            }
        }
        
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            this.clear();
        }
    }
}
