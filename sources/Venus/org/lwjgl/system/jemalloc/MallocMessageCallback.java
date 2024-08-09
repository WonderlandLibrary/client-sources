/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.jemalloc;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.jemalloc.MallocMessageCallbackI;

public abstract class MallocMessageCallback
extends Callback
implements MallocMessageCallbackI {
    public static MallocMessageCallback create(long l) {
        MallocMessageCallbackI mallocMessageCallbackI = (MallocMessageCallbackI)Callback.get(l);
        return mallocMessageCallbackI instanceof MallocMessageCallback ? (MallocMessageCallback)mallocMessageCallbackI : new Container(l, mallocMessageCallbackI);
    }

    @Nullable
    public static MallocMessageCallback createSafe(long l) {
        return l == 0L ? null : MallocMessageCallback.create(l);
    }

    public static MallocMessageCallback create(MallocMessageCallbackI mallocMessageCallbackI) {
        return mallocMessageCallbackI instanceof MallocMessageCallback ? (MallocMessageCallback)mallocMessageCallbackI : new Container(mallocMessageCallbackI.address(), mallocMessageCallbackI);
    }

    protected MallocMessageCallback() {
        super("(pp)v");
    }

    MallocMessageCallback(long l) {
        super(l);
    }

    public static String getMessage(long l) {
        return MemoryUtil.memASCII(l);
    }

    private static final class Container
    extends MallocMessageCallback {
        private final MallocMessageCallbackI delegate;

        Container(long l, MallocMessageCallbackI mallocMessageCallbackI) {
            super(l);
            this.delegate = mallocMessageCallbackI;
        }

        @Override
        public void invoke(long l, long l2) {
            this.delegate.invoke(l, l2);
        }
    }
}

