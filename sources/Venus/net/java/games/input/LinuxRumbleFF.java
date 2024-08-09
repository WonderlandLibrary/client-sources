/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.LinuxEventDevice;
import net.java.games.input.LinuxForceFeedbackEffect;

final class LinuxRumbleFF
extends LinuxForceFeedbackEffect {
    public LinuxRumbleFF(LinuxEventDevice linuxEventDevice) throws IOException {
        super(linuxEventDevice);
    }

    protected final int upload(int n, float f) throws IOException {
        int n2;
        int n3;
        if (f > 0.666666f) {
            n3 = (int)(32768.0f * f);
            n2 = (int)(49152.0f * f);
        } else if (f > 0.3333333f) {
            n3 = (int)(32768.0f * f);
            n2 = 0;
        } else {
            n3 = 0;
            n2 = (int)(49152.0f * f);
        }
        return this.getDevice().uploadRumbleEffect(n, 0, 0, 0, -1, 0, n3, n2);
    }
}

