/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.stb.STBIWriteCallbackI;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryUtil;

public abstract class STBIWriteCallback
extends Callback
implements STBIWriteCallbackI {
    public static STBIWriteCallback create(long l) {
        STBIWriteCallbackI sTBIWriteCallbackI = (STBIWriteCallbackI)Callback.get(l);
        return sTBIWriteCallbackI instanceof STBIWriteCallback ? (STBIWriteCallback)sTBIWriteCallbackI : new Container(l, sTBIWriteCallbackI);
    }

    @Nullable
    public static STBIWriteCallback createSafe(long l) {
        return l == 0L ? null : STBIWriteCallback.create(l);
    }

    public static STBIWriteCallback create(STBIWriteCallbackI sTBIWriteCallbackI) {
        return sTBIWriteCallbackI instanceof STBIWriteCallback ? (STBIWriteCallback)sTBIWriteCallbackI : new Container(sTBIWriteCallbackI.address(), sTBIWriteCallbackI);
    }

    protected STBIWriteCallback() {
        super("(ppi)v");
    }

    STBIWriteCallback(long l) {
        super(l);
    }

    public static ByteBuffer getData(long l, int n) {
        return MemoryUtil.memByteBuffer(l, n);
    }

    private static final class Container
    extends STBIWriteCallback {
        private final STBIWriteCallbackI delegate;

        Container(long l, STBIWriteCallbackI sTBIWriteCallbackI) {
            super(l);
            this.delegate = sTBIWriteCallbackI;
        }

        @Override
        public void invoke(long l, long l2, int n) {
            this.delegate.invoke(l, l2, n);
        }
    }
}

