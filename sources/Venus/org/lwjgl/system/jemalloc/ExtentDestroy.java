/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.jemalloc;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;
import org.lwjgl.system.jemalloc.ExtentDestroyI;

public abstract class ExtentDestroy
extends Callback
implements ExtentDestroyI {
    public static ExtentDestroy create(long l) {
        ExtentDestroyI extentDestroyI = (ExtentDestroyI)Callback.get(l);
        return extentDestroyI instanceof ExtentDestroy ? (ExtentDestroy)extentDestroyI : new Container(l, extentDestroyI);
    }

    @Nullable
    public static ExtentDestroy createSafe(long l) {
        return l == 0L ? null : ExtentDestroy.create(l);
    }

    public static ExtentDestroy create(ExtentDestroyI extentDestroyI) {
        return extentDestroyI instanceof ExtentDestroy ? (ExtentDestroy)extentDestroyI : new Container(extentDestroyI.address(), extentDestroyI);
    }

    protected ExtentDestroy() {
        super("(pppBi)B");
    }

    ExtentDestroy(long l) {
        super(l);
    }

    private static final class Container
    extends ExtentDestroy {
        private final ExtentDestroyI delegate;

        Container(long l, ExtentDestroyI extentDestroyI) {
            super(l);
            this.delegate = extentDestroyI;
        }

        @Override
        public boolean invoke(long l, long l2, long l3, boolean bl, int n) {
            return this.delegate.invoke(l, l2, l3, bl, n);
        }
    }
}

