/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.jemalloc;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;
import org.lwjgl.system.jemalloc.ExtentDecommitI;

public abstract class ExtentDecommit
extends Callback
implements ExtentDecommitI {
    public static ExtentDecommit create(long l) {
        ExtentDecommitI extentDecommitI = (ExtentDecommitI)Callback.get(l);
        return extentDecommitI instanceof ExtentDecommit ? (ExtentDecommit)extentDecommitI : new Container(l, extentDecommitI);
    }

    @Nullable
    public static ExtentDecommit createSafe(long l) {
        return l == 0L ? null : ExtentDecommit.create(l);
    }

    public static ExtentDecommit create(ExtentDecommitI extentDecommitI) {
        return extentDecommitI instanceof ExtentDecommit ? (ExtentDecommit)extentDecommitI : new Container(extentDecommitI.address(), extentDecommitI);
    }

    protected ExtentDecommit() {
        super("(pppppi)B");
    }

    ExtentDecommit(long l) {
        super(l);
    }

    private static final class Container
    extends ExtentDecommit {
        private final ExtentDecommitI delegate;

        Container(long l, ExtentDecommitI extentDecommitI) {
            super(l);
            this.delegate = extentDecommitI;
        }

        @Override
        public boolean invoke(long l, long l2, long l3, long l4, long l5, int n) {
            return this.delegate.invoke(l, l2, l3, l4, l5, n);
        }
    }
}

