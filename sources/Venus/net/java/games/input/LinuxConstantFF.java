/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.LinuxEventDevice;
import net.java.games.input.LinuxForceFeedbackEffect;

final class LinuxConstantFF
extends LinuxForceFeedbackEffect {
    public LinuxConstantFF(LinuxEventDevice linuxEventDevice) throws IOException {
        super(linuxEventDevice);
    }

    protected final int upload(int n, float f) throws IOException {
        int n2 = Math.round(f * 32767.0f);
        return this.getDevice().uploadConstantEffect(n, 0, 0, 0, 0, 0, n2, 0, 0, 0, 0);
    }
}

