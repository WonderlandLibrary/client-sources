/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.jemalloc;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;
import org.lwjgl.system.jemalloc.ExtentSplitI;

public abstract class ExtentSplit
extends Callback
implements ExtentSplitI {
    public static ExtentSplit create(long l) {
        ExtentSplitI extentSplitI = (ExtentSplitI)Callback.get(l);
        return extentSplitI instanceof ExtentSplit ? (ExtentSplit)extentSplitI : new Container(l, extentSplitI);
    }

    @Nullable
    public static ExtentSplit createSafe(long l) {
        return l == 0L ? null : ExtentSplit.create(l);
    }

    public static ExtentSplit create(ExtentSplitI extentSplitI) {
        return extentSplitI instanceof ExtentSplit ? (ExtentSplit)extentSplitI : new Container(extentSplitI.address(), extentSplitI);
    }

    protected ExtentSplit() {
        super("(pppppBi)B");
    }

    ExtentSplit(long l) {
        super(l);
    }

    private static final class Container
    extends ExtentSplit {
        private final ExtentSplitI delegate;

        Container(long l, ExtentSplitI extentSplitI) {
            super(l);
            this.delegate = extentSplitI;
        }

        @Override
        public boolean invoke(long l, long l2, long l3, long l4, long l5, boolean bl, int n) {
            return this.delegate.invoke(l, l2, l3, l4, l5, bl, n);
        }
    }
}

