/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.util.Set;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALC11;
import org.lwjgl.openal.EXTCapture;
import org.lwjgl.openal.EXTThreadLocalContext;
import org.lwjgl.openal.SOFTDeviceClock;
import org.lwjgl.openal.SOFTHRTF;
import org.lwjgl.openal.SOFTLoopback;
import org.lwjgl.openal.SOFTPauseDevice;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.FunctionProviderLocal;

public final class ALCCapabilities {
    public final long alcOpenDevice;
    public final long alcCloseDevice;
    public final long alcCreateContext;
    public final long alcMakeContextCurrent;
    public final long alcProcessContext;
    public final long alcSuspendContext;
    public final long alcDestroyContext;
    public final long alcGetCurrentContext;
    public final long alcGetContextsDevice;
    public final long alcIsExtensionPresent;
    public final long alcGetProcAddress;
    public final long alcGetEnumValue;
    public final long alcGetError;
    public final long alcGetString;
    public final long alcGetIntegerv;
    public final long alcCaptureOpenDevice;
    public final long alcCaptureCloseDevice;
    public final long alcCaptureStart;
    public final long alcCaptureStop;
    public final long alcCaptureSamples;
    public final long alcSetThreadContext;
    public final long alcGetThreadContext;
    public final long alcGetInteger64vSOFT;
    public final long alcGetStringiSOFT;
    public final long alcResetDeviceSOFT;
    public final long alcLoopbackOpenDeviceSOFT;
    public final long alcIsRenderFormatSupportedSOFT;
    public final long alcRenderSamplesSOFT;
    public final long alcDevicePauseSOFT;
    public final long alcDeviceResumeSOFT;
    public final boolean OpenALC10;
    public final boolean OpenALC11;
    public final boolean ALC_ENUMERATE_ALL_EXT;
    public final boolean ALC_ENUMERATION_EXT;
    public final boolean ALC_EXT_CAPTURE;
    public final boolean ALC_EXT_DEDICATED;
    public final boolean ALC_EXT_DEFAULT_FILTER_ORDER;
    public final boolean ALC_EXT_disconnect;
    public final boolean ALC_EXT_EFX;
    public final boolean ALC_EXT_thread_local_context;
    public final boolean ALC_LOKI_audio_channel;
    public final boolean ALC_SOFT_device_clock;
    public final boolean ALC_SOFT_HRTF;
    public final boolean ALC_SOFT_loopback;
    public final boolean ALC_SOFT_output_limiter;
    public final boolean ALC_SOFT_pause_device;

    ALCCapabilities(FunctionProviderLocal functionProviderLocal, long l, Set<String> set) {
        this.alcOpenDevice = functionProviderLocal.getFunctionAddress("alcOpenDevice");
        this.alcCloseDevice = functionProviderLocal.getFunctionAddress("alcCloseDevice");
        this.alcCreateContext = functionProviderLocal.getFunctionAddress("alcCreateContext");
        this.alcMakeContextCurrent = functionProviderLocal.getFunctionAddress("alcMakeContextCurrent");
        this.alcProcessContext = functionProviderLocal.getFunctionAddress("alcProcessContext");
        this.alcSuspendContext = functionProviderLocal.getFunctionAddress("alcSuspendContext");
        this.alcDestroyContext = functionProviderLocal.getFunctionAddress("alcDestroyContext");
        this.alcGetCurrentContext = functionProviderLocal.getFunctionAddress("alcGetCurrentContext");
        this.alcGetContextsDevice = functionProviderLocal.getFunctionAddress("alcGetContextsDevice");
        this.alcIsExtensionPresent = functionProviderLocal.getFunctionAddress("alcIsExtensionPresent");
        this.alcGetProcAddress = functionProviderLocal.getFunctionAddress("alcGetProcAddress");
        this.alcGetEnumValue = functionProviderLocal.getFunctionAddress("alcGetEnumValue");
        this.alcGetError = functionProviderLocal.getFunctionAddress("alcGetError");
        this.alcGetString = functionProviderLocal.getFunctionAddress("alcGetString");
        this.alcGetIntegerv = functionProviderLocal.getFunctionAddress("alcGetIntegerv");
        this.alcCaptureOpenDevice = functionProviderLocal.getFunctionAddress("alcCaptureOpenDevice");
        this.alcCaptureCloseDevice = functionProviderLocal.getFunctionAddress("alcCaptureCloseDevice");
        this.alcCaptureStart = functionProviderLocal.getFunctionAddress("alcCaptureStart");
        this.alcCaptureStop = functionProviderLocal.getFunctionAddress("alcCaptureStop");
        this.alcCaptureSamples = functionProviderLocal.getFunctionAddress("alcCaptureSamples");
        this.alcSetThreadContext = functionProviderLocal.getFunctionAddress(l, "alcSetThreadContext");
        this.alcGetThreadContext = functionProviderLocal.getFunctionAddress(l, "alcGetThreadContext");
        this.alcGetInteger64vSOFT = functionProviderLocal.getFunctionAddress(l, "alcGetInteger64vSOFT");
        this.alcGetStringiSOFT = functionProviderLocal.getFunctionAddress(l, "alcGetStringiSOFT");
        this.alcResetDeviceSOFT = functionProviderLocal.getFunctionAddress(l, "alcResetDeviceSOFT");
        this.alcLoopbackOpenDeviceSOFT = functionProviderLocal.getFunctionAddress(l, "alcLoopbackOpenDeviceSOFT");
        this.alcIsRenderFormatSupportedSOFT = functionProviderLocal.getFunctionAddress(l, "alcIsRenderFormatSupportedSOFT");
        this.alcRenderSamplesSOFT = functionProviderLocal.getFunctionAddress(l, "alcRenderSamplesSOFT");
        this.alcDevicePauseSOFT = functionProviderLocal.getFunctionAddress(l, "alcDevicePauseSOFT");
        this.alcDeviceResumeSOFT = functionProviderLocal.getFunctionAddress(l, "alcDeviceResumeSOFT");
        this.OpenALC10 = set.contains("OpenALC10") && ALCCapabilities.checkExtension("OpenALC10", ALC10.isAvailable(this));
        this.OpenALC11 = set.contains("OpenALC11") && ALCCapabilities.checkExtension("OpenALC11", ALC11.isAvailable(this));
        this.ALC_ENUMERATE_ALL_EXT = set.contains("ALC_ENUMERATE_ALL_EXT");
        this.ALC_ENUMERATION_EXT = set.contains("ALC_ENUMERATION_EXT");
        this.ALC_EXT_CAPTURE = set.contains("ALC_EXT_CAPTURE") && ALCCapabilities.checkExtension("ALC_EXT_CAPTURE", EXTCapture.isAvailable(this));
        this.ALC_EXT_DEDICATED = set.contains("ALC_EXT_DEDICATED");
        this.ALC_EXT_DEFAULT_FILTER_ORDER = set.contains("ALC_EXT_DEFAULT_FILTER_ORDER");
        this.ALC_EXT_disconnect = set.contains("ALC_EXT_disconnect");
        this.ALC_EXT_EFX = set.contains("ALC_EXT_EFX");
        this.ALC_EXT_thread_local_context = set.contains("ALC_EXT_thread_local_context") && ALCCapabilities.checkExtension("ALC_EXT_thread_local_context", EXTThreadLocalContext.isAvailable(this));
        this.ALC_LOKI_audio_channel = set.contains("ALC_LOKI_audio_channel");
        this.ALC_SOFT_device_clock = set.contains("ALC_SOFT_device_clock") && ALCCapabilities.checkExtension("ALC_SOFT_device_clock", SOFTDeviceClock.isAvailable(this));
        this.ALC_SOFT_HRTF = set.contains("ALC_SOFT_HRTF") && ALCCapabilities.checkExtension("ALC_SOFT_HRTF", SOFTHRTF.isAvailable(this));
        this.ALC_SOFT_loopback = set.contains("ALC_SOFT_loopback") && ALCCapabilities.checkExtension("ALC_SOFT_loopback", SOFTLoopback.isAvailable(this));
        this.ALC_SOFT_output_limiter = set.contains("ALC_SOFT_output_limiter");
        this.ALC_SOFT_pause_device = set.contains("ALC_SOFT_pause_device") && ALCCapabilities.checkExtension("ALC_SOFT_pause_device", SOFTPauseDevice.isAvailable(this));
    }

    private static boolean checkExtension(String string, boolean bl) {
        if (bl) {
            return false;
        }
        APIUtil.apiLog("[ALC] " + string + " was reported as available but an entry point is missing.");
        return true;
    }
}

