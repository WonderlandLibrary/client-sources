/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.jemalloc;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;
import org.lwjgl.system.jemalloc.ExtentDallocI;

public abstract class ExtentDalloc
extends Callback
implements ExtentDallocI {
    public static ExtentDalloc create(long l) {
        ExtentDallocI extentDallocI = (ExtentDallocI)Callback.get(l);
        return extentDallocI instanceof ExtentDalloc ? (ExtentDalloc)extentDallocI : new Container(l, extentDallocI);
    }

    @Nullable
    public static ExtentDalloc createSafe(long l) {
        return l == 0L ? null : ExtentDalloc.create(l);
    }

    public static ExtentDalloc create(ExtentDallocI extentDallocI) {
        return extentDallocI instanceof ExtentDalloc ? (ExtentDalloc)extentDallocI : new Container(extentDallocI.address(), extentDallocI);
    }

    protected ExtentDalloc() {
        super("(pppBi)B");
    }

    ExtentDalloc(long l) {
        super(l);
    }

    private static final class Container
    extends ExtentDalloc {
        private final ExtentDallocI delegate;

        Container(long l, ExtentDallocI extentDallocI) {
            super(l);
            this.delegate = extentDallocI;
        }

        @Override
        public boolean invoke(long l, long l2, long l3, boolean bl, int n) {
            return this.delegate.invoke(l, l2, l3, bl, n);
        }
    }
}

