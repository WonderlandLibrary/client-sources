/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.windows;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;
import org.lwjgl.system.windows.WindowProcI;

public abstract class WindowProc
extends Callback
implements WindowProcI {
    public static WindowProc create(long l) {
        WindowProcI windowProcI = (WindowProcI)Callback.get(l);
        return windowProcI instanceof WindowProc ? (WindowProc)windowProcI : new Container(l, windowProcI);
    }

    @Nullable
    public static WindowProc createSafe(long l) {
        return l == 0L ? null : WindowProc.create(l);
    }

    public static WindowProc create(WindowProcI windowProcI) {
        return windowProcI instanceof WindowProc ? (WindowProc)windowProcI : new Container(windowProcI.address(), windowProcI);
    }

    protected WindowProc() {
        super(SIGNATURE);
    }

    WindowProc(long l) {
        super(l);
    }

    private static final class Container
    extends WindowProc {
        private final WindowProcI delegate;

        Container(long l, WindowProcI windowProcI) {
            super(l);
            this.delegate = windowProcI;
        }

        @Override
        public long invoke(long l, int n, long l2, long l3) {
            return this.delegate.invoke(l, n, l2, l3);
        }
    }
}

