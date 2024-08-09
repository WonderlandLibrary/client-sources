/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.NativeMapped;
import com.sun.jna.Pointer;

public abstract class PointerType
implements NativeMapped {
    private Pointer pointer;

    protected PointerType() {
        this.pointer = Pointer.NULL;
    }

    protected PointerType(Pointer pointer) {
        this.pointer = pointer;
    }

    @Override
    public Class<?> nativeType() {
        return Pointer.class;
    }

    @Override
    public Object toNative() {
        return this.getPointer();
    }

    public Pointer getPointer() {
        return this.pointer;
    }

    public void setPointer(Pointer pointer) {
        this.pointer = pointer;
    }

    @Override
    public Object fromNative(Object object, FromNativeContext fromNativeContext) {
        if (object == null) {
            return null;
        }
        try {
            PointerType pointerType = (PointerType)this.getClass().newInstance();
            pointerType.pointer = (Pointer)object;
            return pointerType;
        } catch (InstantiationException instantiationException) {
            throw new IllegalArgumentException("Can't instantiate " + this.getClass());
        } catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException("Not allowed to instantiate " + this.getClass());
        }
    }

    public int hashCode() {
        return this.pointer != null ? this.pointer.hashCode() : 0;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof PointerType) {
            Pointer pointer = ((PointerType)object).getPointer();
            if (this.pointer == null) {
                return pointer == null;
            }
            return this.pointer.equals(pointer);
        }
        return true;
    }

    public String toString() {
        return this.pointer == null ? "NULL" : this.pointer.toString() + " (" + super.toString() + ")";
    }
}

