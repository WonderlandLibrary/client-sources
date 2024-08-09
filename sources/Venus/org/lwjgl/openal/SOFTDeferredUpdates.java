/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class SOFTDeferredUpdates {
    public static final int AL_DEFERRED_UPDATES_SOFT = 49154;

    protected SOFTDeferredUpdates() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCapabilities aLCapabilities) {
        return Checks.checkFunctions(aLCapabilities.alDeferUpdatesSOFT, aLCapabilities.alProcessUpdatesSOFT);
    }

    @NativeType(value="ALvoid")
    public static void alDeferUpdatesSOFT() {
        long l = AL.getICD().alDeferUpdatesSOFT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(l);
    }

    @NativeType(value="ALvoid")
    public static void alProcessUpdatesSOFT() {
        long l = AL.getICD().alProcessUpdatesSOFT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(l);
    }
}

