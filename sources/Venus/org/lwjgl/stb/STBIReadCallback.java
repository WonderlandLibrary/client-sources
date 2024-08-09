/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.stb.STBIReadCallbackI;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryUtil;

public abstract class STBIReadCallback
extends Callback
implements STBIReadCallbackI {
    public static STBIReadCallback create(long l) {
        STBIReadCallbackI sTBIReadCallbackI = (STBIReadCallbackI)Callback.get(l);
        return sTBIReadCallbackI instanceof STBIReadCallback ? (STBIReadCallback)sTBIReadCallbackI : new Container(l, sTBIReadCallbackI);
    }

    @Nullable
    public static STBIReadCallback createSafe(long l) {
        return l == 0L ? null : STBIReadCallback.create(l);
    }

    public static STBIReadCallback create(STBIReadCallbackI sTBIReadCallbackI) {
        return sTBIReadCallbackI instanceof STBIReadCallback ? (STBIReadCallback)sTBIReadCallbackI : new Container(sTBIReadCallbackI.address(), sTBIReadCallbackI);
    }

    protected STBIReadCallback() {
        super("(ppi)i");
    }

    STBIReadCallback(long l) {
        super(l);
    }

    public static ByteBuffer getData(long l, int n) {
        return MemoryUtil.memByteBuffer(l, n);
    }

    private static final class Container
    extends STBIReadCallback {
        private final STBIReadCallbackI delegate;

        Container(long l, STBIReadCallbackI sTBIReadCallbackI) {
            super(l);
            this.delegate = sTBIReadCallbackI;
        }

        @Override
        public int invoke(long l, long l2, int n) {
            return this.delegate.invoke(l, l2, n);
        }
    }
}

