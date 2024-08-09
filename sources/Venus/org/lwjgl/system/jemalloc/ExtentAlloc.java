/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.jemalloc;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;
import org.lwjgl.system.jemalloc.ExtentAllocI;

public abstract class ExtentAlloc
extends Callback
implements ExtentAllocI {
    public static ExtentAlloc create(long l) {
        ExtentAllocI extentAllocI = (ExtentAllocI)Callback.get(l);
        return extentAllocI instanceof ExtentAlloc ? (ExtentAlloc)extentAllocI : new Container(l, extentAllocI);
    }

    @Nullable
    public static ExtentAlloc createSafe(long l) {
        return l == 0L ? null : ExtentAlloc.create(l);
    }

    public static ExtentAlloc create(ExtentAllocI extentAllocI) {
        return extentAllocI instanceof ExtentAlloc ? (ExtentAlloc)extentAllocI : new Container(extentAllocI.address(), extentAllocI);
    }

    protected ExtentAlloc() {
        super("(ppppppi)p");
    }

    ExtentAlloc(long l) {
        super(l);
    }

    private static final class Container
    extends ExtentAlloc {
        private final ExtentAllocI delegate;

        Container(long l, ExtentAllocI extentAllocI) {
            super(l);
            this.delegate = extentAllocI;
        }

        @Override
        public long invoke(long l, long l2, long l3, long l4, long l5, long l6, int n) {
            return this.delegate.invoke(l, l2, l3, l4, l5, l6, n);
        }
    }
}

