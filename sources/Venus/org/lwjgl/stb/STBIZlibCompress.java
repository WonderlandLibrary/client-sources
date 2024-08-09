/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import javax.annotation.Nullable;
import org.lwjgl.stb.STBIZlibCompressI;
import org.lwjgl.system.Callback;

public abstract class STBIZlibCompress
extends Callback
implements STBIZlibCompressI {
    public static STBIZlibCompress create(long l) {
        STBIZlibCompressI sTBIZlibCompressI = (STBIZlibCompressI)Callback.get(l);
        return sTBIZlibCompressI instanceof STBIZlibCompress ? (STBIZlibCompress)sTBIZlibCompressI : new Container(l, sTBIZlibCompressI);
    }

    @Nullable
    public static STBIZlibCompress createSafe(long l) {
        return l == 0L ? null : STBIZlibCompress.create(l);
    }

    public static STBIZlibCompress create(STBIZlibCompressI sTBIZlibCompressI) {
        return sTBIZlibCompressI instanceof STBIZlibCompress ? (STBIZlibCompress)sTBIZlibCompressI : new Container(sTBIZlibCompressI.address(), sTBIZlibCompressI);
    }

    protected STBIZlibCompress() {
        super("(pipi)p");
    }

    STBIZlibCompress(long l) {
        super(l);
    }

    private static final class Container
    extends STBIZlibCompress {
        private final STBIZlibCompressI delegate;

        Container(long l, STBIZlibCompressI sTBIZlibCompressI) {
            super(l);
            this.delegate = sTBIZlibCompressI;
        }

        @Override
        public long invoke(long l, int n, long l2, int n2) {
            return this.delegate.invoke(l, n, l2, n2);
        }
    }
}

