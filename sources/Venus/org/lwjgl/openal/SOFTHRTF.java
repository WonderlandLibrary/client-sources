/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class SOFTHRTF {
    public static final int ALC_HRTF_SOFT = 6546;
    public static final int ALC_HRTF_ID_SOFT = 6550;
    public static final int ALC_DONT_CARE_SOFT = 2;
    public static final int ALC_HRTF_STATUS_SOFT = 6547;
    public static final int ALC_NUM_HRTF_SPECIFIERS_SOFT = 6548;
    public static final int ALC_HRTF_SPECIFIER_SOFT = 6549;
    public static final int ALC_HRTF_DISABLED_SOFT = 0;
    public static final int ALC_HRTF_ENABLED_SOFT = 1;
    public static final int ALC_HRTF_DENIED_SOFT = 2;
    public static final int ALC_HRTF_REQUIRED_SOFT = 3;
    public static final int ALC_HRTF_HEADPHONES_DETECTED_SOFT = 4;
    public static final int ALC_HRTF_UNSUPPORTED_FORMAT_SOFT = 5;

    protected SOFTHRTF() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCCapabilities aLCCapabilities) {
        return Checks.checkFunctions(aLCCapabilities.alcGetStringiSOFT, aLCCapabilities.alcResetDeviceSOFT);
    }

    public static long nalcGetStringiSOFT(long l, int n, int n2) {
        long l2 = ALC.getICD().alcGetStringiSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.invokePP(l, n, n2, l2);
    }

    @Nullable
    @NativeType(value="ALCchar const *")
    public static String alcGetStringiSOFT(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCenum") int n, @NativeType(value="ALCsizei") int n2) {
        long l2 = SOFTHRTF.nalcGetStringiSOFT(l, n, n2);
        return MemoryUtil.memUTF8Safe(l2);
    }

    public static boolean nalcResetDeviceSOFT(long l, long l2) {
        long l3 = ALC.getICD().alcResetDeviceSOFT;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.invokePPZ(l, l2, l3);
    }

    @NativeType(value="ALCboolean")
    public static boolean alcResetDeviceSOFT(@NativeType(value="ALCdevice *") long l, @Nullable @NativeType(value="ALCint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        return SOFTHRTF.nalcResetDeviceSOFT(l, MemoryUtil.memAddressSafe(intBuffer));
    }

    @NativeType(value="ALCboolean")
    public static boolean alcResetDeviceSOFT(@NativeType(value="ALCdevice *") long l, @Nullable @NativeType(value="ALCint const *") int[] nArray) {
        long l2 = ALC.getICD().alcResetDeviceSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.checkNTSafe(nArray);
        }
        return JNI.invokePPZ(l, nArray, l2);
    }
}

