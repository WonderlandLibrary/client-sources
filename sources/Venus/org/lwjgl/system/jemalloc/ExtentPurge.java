/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.jemalloc;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;
import org.lwjgl.system.jemalloc.ExtentPurgeI;

public abstract class ExtentPurge
extends Callback
implements ExtentPurgeI {
    public static ExtentPurge create(long l) {
        ExtentPurgeI extentPurgeI = (ExtentPurgeI)Callback.get(l);
        return extentPurgeI instanceof ExtentPurge ? (ExtentPurge)extentPurgeI : new Container(l, extentPurgeI);
    }

    @Nullable
    public static ExtentPurge createSafe(long l) {
        return l == 0L ? null : ExtentPurge.create(l);
    }

    public static ExtentPurge create(ExtentPurgeI extentPurgeI) {
        return extentPurgeI instanceof ExtentPurge ? (ExtentPurge)extentPurgeI : new Container(extentPurgeI.address(), extentPurgeI);
    }

    protected ExtentPurge() {
        super("(pppppi)B");
    }

    ExtentPurge(long l) {
        super(l);
    }

    private static final class Container
    extends ExtentPurge {
        private final ExtentPurgeI delegate;

        Container(long l, ExtentPurgeI extentPurgeI) {
            super(l);
            this.delegate = extentPurgeI;
        }

        @Override
        public boolean invoke(long l, long l2, long l3, long l4, long l5, int n) {
            return this.delegate.invoke(l, l2, l3, l4, l5, n);
        }
    }
}

