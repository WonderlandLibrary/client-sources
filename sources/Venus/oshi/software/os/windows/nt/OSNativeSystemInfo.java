/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.software.os.windows.nt;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;

public class OSNativeSystemInfo {
    private WinBase.SYSTEM_INFO _si = null;

    public OSNativeSystemInfo() {
        WinBase.SYSTEM_INFO sYSTEM_INFO = new WinBase.SYSTEM_INFO();
        Kernel32.INSTANCE.GetSystemInfo(sYSTEM_INFO);
        try {
            IntByReference intByReference = new IntByReference();
            WinNT.HANDLE hANDLE = Kernel32.INSTANCE.GetCurrentProcess();
            if (Kernel32.INSTANCE.IsWow64Process(hANDLE, intByReference) && intByReference.getValue() > 0) {
                Kernel32.INSTANCE.GetNativeSystemInfo(sYSTEM_INFO);
            }
        } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            // empty catch block
        }
        this._si = sYSTEM_INFO;
    }

    public OSNativeSystemInfo(WinBase.SYSTEM_INFO sYSTEM_INFO) {
        this._si = sYSTEM_INFO;
    }

    public int getNumberOfProcessors() {
        return this._si.dwNumberOfProcessors.intValue();
    }
}

