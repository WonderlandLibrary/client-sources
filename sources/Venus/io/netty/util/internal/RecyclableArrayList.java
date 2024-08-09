/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.Recycler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

public final class RecyclableArrayList
extends ArrayList<Object> {
    private static final long serialVersionUID = -8605125654176467947L;
    private static final int DEFAULT_INITIAL_CAPACITY = 8;
    private static final Recycler<RecyclableArrayList> RECYCLER = new Recycler<RecyclableArrayList>(){

        @Override
        protected RecyclableArrayList newObject(Recycler.Handle<RecyclableArrayList> handle) {
            return new RecyclableArrayList(handle, null);
        }

        @Override
        protected Object newObject(Recycler.Handle handle) {
            return this.newObject(handle);
        }
    };
    private boolean insertSinceRecycled;
    private final Recycler.Handle<RecyclableArrayList> handle;

    public static RecyclableArrayList newInstance() {
        return RecyclableArrayList.newInstance(8);
    }

    public static RecyclableArrayList newInstance(int n) {
        RecyclableArrayList recyclableArrayList = RECYCLER.get();
        recyclableArrayList.ensureCapacity(n);
        return recyclableArrayList;
    }

    private RecyclableArrayList(Recycler.Handle<RecyclableArrayList> handle) {
        this(handle, 8);
    }

    private RecyclableArrayList(Recycler.Handle<RecyclableArrayList> handle, int n) {
        super(n);
        this.handle = handle;
    }

    @Override
    public boolean addAll(Collection<?> collection) {
        RecyclableArrayList.checkNullElements(collection);
        if (super.addAll(collection)) {
            this.insertSinceRecycled = true;
            return false;
        }
        return true;
    }

    @Override
    public boolean addAll(int n, Collection<?> collection) {
        RecyclableArrayList.checkNullElements(collection);
        if (super.addAll(n, collection)) {
            this.insertSinceRecycled = true;
            return false;
        }
        return true;
    }

    private static void checkNullElements(Collection<?> collection) {
        if (collection instanceof RandomAccess && collection instanceof List) {
            List list = (List)collection;
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                if (list.get(i) != null) continue;
                throw new IllegalArgumentException("c contains null values");
            }
        } else {
            for (Object obj : collection) {
                if (obj != null) continue;
                throw new IllegalArgumentException("c contains null values");
            }
        }
    }

    @Override
    public boolean add(Object object) {
        if (object == null) {
            throw new NullPointerException("element");
        }
        if (super.add(object)) {
            this.insertSinceRecycled = true;
            return false;
        }
        return true;
    }

    @Override
    public void add(int n, Object object) {
        if (object == null) {
            throw new NullPointerException("element");
        }
        super.add(n, object);
        this.insertSinceRecycled = true;
    }

    @Override
    public Object set(int n, Object object) {
        if (object == null) {
            throw new NullPointerException("element");
        }
        Object object2 = super.set(n, object);
        this.insertSinceRecycled = true;
        return object2;
    }

    public boolean insertSinceRecycled() {
        return this.insertSinceRecycled;
    }

    public boolean recycle() {
        this.clear();
        this.insertSinceRecycled = false;
        this.handle.recycle(this);
        return false;
    }

    RecyclableArrayList(Recycler.Handle handle, 1 var2_2) {
        this(handle);
    }
}

