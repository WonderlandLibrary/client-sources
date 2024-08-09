/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.platform.win32.Crypt32;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinCrypt;
import com.sun.jna.ptr.PointerByReference;

public abstract class Crypt32Util {
    public static byte[] cryptProtectData(byte[] byArray) {
        return Crypt32Util.cryptProtectData(byArray, 0);
    }

    public static byte[] cryptProtectData(byte[] byArray, int n) {
        return Crypt32Util.cryptProtectData(byArray, null, n, "", null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] cryptProtectData(byte[] byArray, byte[] byArray2, int n, String string, WinCrypt.CRYPTPROTECT_PROMPTSTRUCT cRYPTPROTECT_PROMPTSTRUCT) {
        WinCrypt.DATA_BLOB dATA_BLOB = new WinCrypt.DATA_BLOB(byArray);
        WinCrypt.DATA_BLOB dATA_BLOB2 = new WinCrypt.DATA_BLOB();
        WinCrypt.DATA_BLOB dATA_BLOB3 = byArray2 == null ? null : new WinCrypt.DATA_BLOB(byArray2);
        try {
            if (!Crypt32.INSTANCE.CryptProtectData(dATA_BLOB, string, dATA_BLOB3, null, cRYPTPROTECT_PROMPTSTRUCT, n, dATA_BLOB2)) {
                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
            }
            byte[] byArray3 = dATA_BLOB2.getData();
            return byArray3;
        } finally {
            if (dATA_BLOB2.pbData != null) {
                Kernel32.INSTANCE.LocalFree(dATA_BLOB2.pbData);
            }
        }
    }

    public static byte[] cryptUnprotectData(byte[] byArray) {
        return Crypt32Util.cryptUnprotectData(byArray, 0);
    }

    public static byte[] cryptUnprotectData(byte[] byArray, int n) {
        return Crypt32Util.cryptUnprotectData(byArray, null, n, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] cryptUnprotectData(byte[] byArray, byte[] byArray2, int n, WinCrypt.CRYPTPROTECT_PROMPTSTRUCT cRYPTPROTECT_PROMPTSTRUCT) {
        WinCrypt.DATA_BLOB dATA_BLOB = new WinCrypt.DATA_BLOB(byArray);
        WinCrypt.DATA_BLOB dATA_BLOB2 = new WinCrypt.DATA_BLOB();
        WinCrypt.DATA_BLOB dATA_BLOB3 = byArray2 == null ? null : new WinCrypt.DATA_BLOB(byArray2);
        PointerByReference pointerByReference = new PointerByReference();
        try {
            if (!Crypt32.INSTANCE.CryptUnprotectData(dATA_BLOB, pointerByReference, dATA_BLOB3, null, cRYPTPROTECT_PROMPTSTRUCT, n, dATA_BLOB2)) {
                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
            }
            byte[] byArray3 = dATA_BLOB2.getData();
            return byArray3;
        } finally {
            if (dATA_BLOB2.pbData != null) {
                Kernel32.INSTANCE.LocalFree(dATA_BLOB2.pbData);
            }
            if (pointerByReference.getValue() != null) {
                Kernel32.INSTANCE.LocalFree(pointerByReference.getValue());
            }
        }
    }
}

