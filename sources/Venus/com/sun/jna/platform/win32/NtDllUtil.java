/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.platform.win32.NtDll;
import com.sun.jna.platform.win32.Wdm;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinReg;
import com.sun.jna.ptr.IntByReference;

public abstract class NtDllUtil {
    public static String getKeyName(WinReg.HKEY hKEY) {
        IntByReference intByReference = new IntByReference();
        int n = NtDll.INSTANCE.ZwQueryKey(hKEY, 0, null, 0, intByReference);
        if (n != -1073741789 || intByReference.getValue() <= 0) {
            throw new Win32Exception(n);
        }
        Wdm.KEY_BASIC_INFORMATION kEY_BASIC_INFORMATION = new Wdm.KEY_BASIC_INFORMATION(intByReference.getValue());
        n = NtDll.INSTANCE.ZwQueryKey(hKEY, 0, kEY_BASIC_INFORMATION, intByReference.getValue(), intByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        return kEY_BASIC_INFORMATION.getName();
    }
}

