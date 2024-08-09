/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import javax.annotation.Nullable;
import org.lwjgl.stb.STBISkipCallbackI;
import org.lwjgl.system.Callback;

public abstract class STBISkipCallback
extends Callback
implements STBISkipCallbackI {
    public static STBISkipCallback create(long l) {
        STBISkipCallbackI sTBISkipCallbackI = (STBISkipCallbackI)Callback.get(l);
        return sTBISkipCallbackI instanceof STBISkipCallback ? (STBISkipCallback)sTBISkipCallbackI : new Container(l, sTBISkipCallbackI);
    }

    @Nullable
    public static STBISkipCallback createSafe(long l) {
        return l == 0L ? null : STBISkipCallback.create(l);
    }

    public static STBISkipCallback create(STBISkipCallbackI sTBISkipCallbackI) {
        return sTBISkipCallbackI instanceof STBISkipCallback ? (STBISkipCallback)sTBISkipCallbackI : new Container(sTBISkipCallbackI.address(), sTBISkipCallbackI);
    }

    protected STBISkipCallback() {
        super("(pi)v");
    }

    STBISkipCallback(long l) {
        super(l);
    }

    private static final class Container
    extends STBISkipCallback {
        private final STBISkipCallbackI delegate;

        Container(long l, STBISkipCallbackI sTBISkipCallbackI) {
            super(l);
            this.delegate = sTBISkipCallbackI;
        }

        @Override
        public void invoke(long l, int n) {
            this.delegate.invoke(l, n);
        }
    }
}

