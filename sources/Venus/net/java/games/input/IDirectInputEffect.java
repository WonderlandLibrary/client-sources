/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.Component;
import net.java.games.input.DIEffectInfo;
import net.java.games.input.DirectInputEnvironmentPlugin;
import net.java.games.input.Rumbler;

final class IDirectInputEffect
implements Rumbler {
    private final long address;
    private final DIEffectInfo info;
    private boolean released;

    public IDirectInputEffect(long l, DIEffectInfo dIEffectInfo) {
        this.address = l;
        this.info = dIEffectInfo;
    }

    public final synchronized void rumble(float f) {
        try {
            this.checkReleased();
            if (f > 0.0f) {
                int n = Math.round(f * 10000.0f);
                this.setGain(n);
                this.start(1, 0);
            } else {
                this.stop();
            }
        } catch (IOException iOException) {
            DirectInputEnvironmentPlugin.logln("Failed to set rumbler gain: " + iOException.getMessage());
        }
    }

    public final Component.Identifier getAxisIdentifier() {
        return null;
    }

    public final String getAxisName() {
        return null;
    }

    public final synchronized void release() {
        if (!this.released) {
            this.released = true;
            IDirectInputEffect.nRelease(this.address);
        }
    }

    private static final native void nRelease(long var0);

    private final void checkReleased() throws IOException {
        if (this.released) {
            throw new IOException();
        }
    }

    private final void setGain(int n) throws IOException {
        int n2 = IDirectInputEffect.nSetGain(this.address, n);
        if (n2 != 3 && n2 != 4 && n2 != 0 && n2 != 8 && n2 != 12) {
            throw new IOException("Failed to set effect gain (0x" + Integer.toHexString(n2) + ")");
        }
    }

    private static final native int nSetGain(long var0, int var2);

    private final void start(int n, int n2) throws IOException {
        int n3 = IDirectInputEffect.nStart(this.address, n, n2);
        if (n3 != 0) {
            throw new IOException("Failed to start effect (0x" + Integer.toHexString(n3) + ")");
        }
    }

    private static final native int nStart(long var0, int var2, int var3);

    private final void stop() throws IOException {
        int n = IDirectInputEffect.nStop(this.address);
        if (n != 0) {
            throw new IOException("Failed to stop effect (0x" + Integer.toHexString(n) + ")");
        }
    }

    private static final native int nStop(long var0);

    protected void finalize() {
        this.release();
    }
}

