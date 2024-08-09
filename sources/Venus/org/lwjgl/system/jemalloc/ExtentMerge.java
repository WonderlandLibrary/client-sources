/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.jemalloc;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;
import org.lwjgl.system.jemalloc.ExtentMergeI;

public abstract class ExtentMerge
extends Callback
implements ExtentMergeI {
    public static ExtentMerge create(long l) {
        ExtentMergeI extentMergeI = (ExtentMergeI)Callback.get(l);
        return extentMergeI instanceof ExtentMerge ? (ExtentMerge)extentMergeI : new Container(l, extentMergeI);
    }

    @Nullable
    public static ExtentMerge createSafe(long l) {
        return l == 0L ? null : ExtentMerge.create(l);
    }

    public static ExtentMerge create(ExtentMergeI extentMergeI) {
        return extentMergeI instanceof ExtentMerge ? (ExtentMerge)extentMergeI : new Container(extentMergeI.address(), extentMergeI);
    }

    protected ExtentMerge() {
        super("(pppppBi)B");
    }

    ExtentMerge(long l) {
        super(l);
    }

    private static final class Container
    extends ExtentMerge {
        private final ExtentMergeI delegate;

        Container(long l, ExtentMergeI extentMergeI) {
            super(l);
            this.delegate = extentMergeI;
        }

        @Override
        public boolean invoke(long l, long l2, long l3, long l4, long l5, boolean bl, int n) {
            return this.delegate.invoke(l, l2, l3, l4, l5, bl, n);
        }
    }
}

