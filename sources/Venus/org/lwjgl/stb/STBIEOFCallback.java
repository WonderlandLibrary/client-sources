/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import javax.annotation.Nullable;
import org.lwjgl.stb.STBIEOFCallbackI;
import org.lwjgl.system.Callback;

public abstract class STBIEOFCallback
extends Callback
implements STBIEOFCallbackI {
    public static STBIEOFCallback create(long l) {
        STBIEOFCallbackI sTBIEOFCallbackI = (STBIEOFCallbackI)Callback.get(l);
        return sTBIEOFCallbackI instanceof STBIEOFCallback ? (STBIEOFCallback)sTBIEOFCallbackI : new Container(l, sTBIEOFCallbackI);
    }

    @Nullable
    public static STBIEOFCallback createSafe(long l) {
        return l == 0L ? null : STBIEOFCallback.create(l);
    }

    public static STBIEOFCallback create(STBIEOFCallbackI sTBIEOFCallbackI) {
        return sTBIEOFCallbackI instanceof STBIEOFCallback ? (STBIEOFCallback)sTBIEOFCallbackI : new Container(sTBIEOFCallbackI.address(), sTBIEOFCallbackI);
    }

    protected STBIEOFCallback() {
        super("(p)i");
    }

    STBIEOFCallback(long l) {
        super(l);
    }

    private static final class Container
    extends STBIEOFCallback {
        private final STBIEOFCallbackI delegate;

        Container(long l, STBIEOFCallbackI sTBIEOFCallbackI) {
            super(l);
            this.delegate = sTBIEOFCallbackI;
        }

        @Override
        public int invoke(long l) {
            return this.delegate.invoke(l);
        }
    }
}

