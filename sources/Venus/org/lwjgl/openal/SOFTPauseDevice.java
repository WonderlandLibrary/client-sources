/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class SOFTPauseDevice {
    protected SOFTPauseDevice() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCCapabilities aLCCapabilities) {
        return Checks.checkFunctions(aLCCapabilities.alcDevicePauseSOFT, aLCCapabilities.alcDeviceResumeSOFT);
    }

    @NativeType(value="ALCvoid")
    public static void alcDevicePauseSOFT(@NativeType(value="ALCdevice *") long l) {
        long l2 = ALC.getICD().alcDevicePauseSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        JNI.invokePV(l, l2);
    }

    @NativeType(value="ALCvoid")
    public static void alcDeviceResumeSOFT(@NativeType(value="ALCdevice *") long l) {
        long l2 = ALC.getICD().alcDeviceResumeSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        JNI.invokePV(l, l2);
    }
}

